package com.scut.mplas.javascript;

import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.CFPathTraversal;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.graphs.pdg.DDEdge;
import com.scut.mplas.graphs.pdg.DataDependenceGraph;
import com.scut.mplas.graphs.pdg.PDNode;
import com.scut.mplas.java.*;
import com.scut.mplas.java.parser.JavaBaseVisitor;
import com.scut.mplas.java.parser.JavaLexer;
import com.scut.mplas.java.parser.JavaParser;
import com.scut.mplas.javascript.parser.JavaScriptBaseVisitor;
import com.scut.mplas.javascript.parser.JavaScriptLexer;
import com.scut.mplas.javascript.parser.JavaScriptParser;
import ghaffarian.graphs.Edge;
import ghaffarian.nanologger.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JavaScriptDDGBuilder {

    // Just for debugging
    private static String currentFile;

    // NOTE: This doesn't handle duplicate class names;
    //       yet assuming no duplicate class names is fair enough.
    //       To handle that, we should use 'Map<String, List<JavaClass>>'
    private static Map<String, JavaScriptClass> allClassInfos;

    private static Map<String, List<MethodDefInfo>> methodDEFs;

    public static DataDependenceGraph[] buildForAll(File[] files) throws IOException {
        // Parse all JavaScript source files
        Logger.info("Parsing all source files ... ");
        ParseTree[] parseTrees = new ParseTree[files.length];
        for (int i = 0; i < files.length; ++i) {
            InputStream inFile = new FileInputStream(files[i]);
            ANTLRInputStream input = new ANTLRInputStream(inFile);
            JavaScriptLexer lexer = new JavaScriptLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaScriptParser parser = new JavaScriptParser(tokens);
            parseTrees[i] = parser.program();
        }
        Logger.info("Done.");

        // Extract the information of all given Java classes
        Logger.info("\nExtracting class-infos ... ");
        allClassInfos = new HashMap<>();
        List<JavaScriptClass[]> filesClasses = new ArrayList<>();
        for (int i = 0; i < files.length; ++i) {
            List<JavaScriptClass> classesList = JavaScriptClassExtractor.extractInfo(files[i].getPath(), parseTrees[i]);
            filesClasses.add(classesList.toArray(new JavaScriptClass[classesList.size()]));
            for (JavaScriptClass cls: classesList)
                allClassInfos.put(cls.NAME, cls);
        }
        Logger.info("Done.");

        // Initialize method DEF information
        Logger.info("\nInitializing method-DEF infos ... ");
        methodDEFs = new HashMap<>();
        for (JavaScriptClass[] classArray: filesClasses) {
            for (JavaScriptClass cls : classArray) {
                for (JavaScriptMethod mtd : cls.getAllMethods()) {
                    List<MethodDefInfo> list = methodDEFs.get(mtd.NAME);
                    if (list == null) {
                        list = new ArrayList<>();
                        list.add(new MethodDefInfo(mtd.RET_TYPE, mtd.NAME, cls.PACKAGE, cls.NAME, mtd.ARG_TYPES));
                        methodDEFs.put(mtd.NAME, list);
                    } else {
                        list.add(new MethodDefInfo(mtd.RET_TYPE, mtd.NAME, cls.PACKAGE, cls.NAME, mtd.ARG_TYPES));
                        // no need to do 'methodDEFs.put(...)' again
                    }
                }
            }
        }
        Logger.info("Done.");

        // Analyze method DEF information for imported libraries
        analyzeImportsDEF(filesClasses);

        // Iteratively, extract USE-DEF info for all program statements ...
        DataDependenceGraph[] ddgs = new DataDependenceGraph[files.length];
        for (int i = 0; i < ddgs.length; ++i)
            ddgs[i] = new DataDependenceGraph(files[i].getName());
        //
        Map<ParserRuleContext, Object>[] pdNodes = new Map[parseTrees.length];
        for (int i = 0; i < parseTrees.length; ++i)
            pdNodes[i] = new IdentityHashMap<>();
        //
        Logger.info("\nIterative DEF-USE analysis ... ");
        boolean changed;
        int iteration = 0;
        do {
            ++iteration;
            changed = false;
            for (int i = 0; i < files.length; ++i) {
                currentFile = files[i].getName();
                DefUseVisitor defUse = new DefUseVisitor(iteration, filesClasses.get(i), ddgs[i], pdNodes[i]);
                defUse.visit(parseTrees[i]);
                changed |= defUse.changed;
            }
            Logger.debug("Iteration #" + iteration + ": " + (changed ? "CHANGED" : "NO-CHANGE"));
            Logger.debug("\n========================================\n");
        } while (changed);
        Logger.info("Done.");

        // Build control-flow graphs for all Java files including the extracted DEF-USE info ...
        Logger.info("\nExtracting CFGs ... ");
        ControlFlowGraph[] cfgs = new ControlFlowGraph[files.length];
        for (int i = 0; i < files.length; ++i)
            cfgs[i] = JavaCFGBuilder.build(files[i].getName(), parseTrees[i], "pdnode", pdNodes[i]);
        Logger.info("Done.");

        // Finally, traverse all control-flow paths and draw data-flow dependency edges ...
        Logger.info("\nAdding data-flow edges ... ");
        for (int i = 0; i < files.length; ++i) {
            addDataFlowEdges(cfgs[i], ddgs[i]);
            ddgs[i].attachCFG(cfgs[i]);
        }
        Logger.info("Done.\n");

        return ddgs;
    }

    /**
     * Analyze method DEF information for imported libraries.
     */
    //TODO
    private static void analyzeImportsDEF(List<JavaScriptClass[]> filesClasses) throws IOException {
        // Extract the import strings
        Logger.info("\nExtracting & Parsing imports ... ");
        Set<String> rawImports = new LinkedHashSet<>();
//        rawImports.add("java.lang.*");
        for (JavaScriptClass[] classes: filesClasses)
            for (JavaScriptClass cls: classes)
                for (String qualifiedName: cls.IMPORTS)
                    rawImports.add(qualifiedName);
        // NOTE: imports can contain specific or whole package imports;
        //       so, we need to extract specific ZIP-entries for all imports.
        // For each import find the ZIP-entries,
        // and extract the ParseTree and JavaClass[] infos
        ZipFile zip = new ZipFile("res/jdk7-src.zip");
        Set<String> imports = new LinkedHashSet<>();
        List<ParseTree> importsParseTrees = new ArrayList<>();
        List<JavaScriptClass[]> importsClassInfos = new ArrayList<>();
        for (String qualifiedName: rawImports) {
            if (qualifiedName.endsWith(".*")) {
                for (ZipEntry ent: getPackageEntries(zip, qualifiedName)) {
                    if (imports.add(ent.getName())) {
                        ANTLRInputStream input = new ANTLRInputStream(zip.getInputStream(ent));
                        JavaScriptLexer lexer = new JavaScriptLexer(input);
                        CommonTokenStream tokens = new CommonTokenStream(lexer);
                        JavaScriptParser parser = new JavaScriptParser(tokens);
                        ParseTree tree = parser.program();
                        //
                        importsParseTrees.add(tree);
                        List<JavaScriptClass> list = JavaScriptClassExtractor.extractInfo("src.zip/" + ent.getName(), tree);
                        importsClassInfos.add(list.toArray(new JavaScriptClass[list.size()]));
                        for (JavaScriptClass cls: list)
                            allClassInfos.put(cls.NAME, cls);
                    }
                }
            } else {
                String path = qualifiedName.replace('.', '/') + ".java";
                if (imports.add(path)) {
                    ZipEntry entry = zip.getEntry(path);
                    if (entry == null) {
                        imports.remove(path);
                        continue;
                    }
                    //
                    ANTLRInputStream input = new ANTLRInputStream(zip.getInputStream(entry));
                    JavaScriptLexer lexer = new JavaScriptLexer(input);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    JavaScriptParser parser = new JavaScriptParser(tokens);
                    ParseTree tree = parser.program();
                    //
                    importsParseTrees.add(tree);
                    List<JavaScriptClass> list = JavaScriptClassExtractor.extractInfo("src.zip/" + path, tree);
                    importsClassInfos.add(list.toArray(new JavaScriptClass[list.size()]));
                }
            }
        }
        Logger.info("Done.");
        //
        for (JavaScriptClass[] classArray: importsClassInfos) {
            for (JavaScriptClass cls : classArray) {
                for (JavaScriptMethod mtd : cls.getAllMethods()) {
                    List<MethodDefInfo> list = methodDEFs.get(mtd.NAME);
                    if (list == null) {
                        list = new ArrayList<>();
                        list.add(new MethodDefInfo(mtd.RET_TYPE, mtd.NAME, cls.PACKAGE, cls.NAME, mtd.ARG_TYPES));
                        methodDEFs.put(mtd.NAME, list);
                    } else {
                        list.add(new MethodDefInfo(mtd.RET_TYPE, mtd.NAME, cls.PACKAGE, cls.NAME, mtd.ARG_TYPES));
                        // no need to do 'methodDEFs.put(...)' again
                    }
                }
            }
        }
        //
        Logger.info("\nAnalyzing imports DEF-USE ... ");
        Map<ParserRuleContext, Object> dummyMap = new HashMap<>();
        DataDependenceGraph dummyDDG = new DataDependenceGraph("Dummy.java");
        boolean changed;
        int iteration = 0;
        do {
            ++iteration;
            changed = false;
            int i = 0;
            for (String imprt: imports) {
                currentFile = "src.zip/" + imprt;
                JavaScriptDDGBuilder.DefUseVisitor defUse = new JavaScriptDDGBuilder.DefUseVisitor(iteration, importsClassInfos.get(i), dummyDDG, dummyMap);
                defUse.visit(importsParseTrees.get(i));
                changed |= defUse.changed;
                ++i;
            }
        } while (changed);
        Logger.info("Done.");
        //
        dummyDDG = null;
        dummyMap.clear();
    }

    /**
     * Returns an array of ZipEntry for a given wildcard package import.
     */
    private static ZipEntry[] getPackageEntries(ZipFile zip, String qualifiedName) {
        // qualifiedName ends with ".*"
        String pkg = qualifiedName.replace('.', '/').substring(0, qualifiedName.length() - 1);
        int slashCount = countSlashes(pkg);
        ArrayList<ZipEntry> entries = new ArrayList<>();
        Enumeration<? extends ZipEntry> zipEntries = zip.entries();
        while (zipEntries.hasMoreElements()) {
            ZipEntry entry = zipEntries.nextElement();
            if (entry.getName().startsWith(pkg)
                    && !entry.isDirectory()
                    && slashCount == countSlashes(entry.getName())) {
                entries.add(entry);
            }
        }
        return entries.toArray(new ZipEntry[entries.size()]);
    }

    /**
     * Returns the number of forward-slash ('/') characters in a given string.
     */
    private static int countSlashes(String str) {
        int slashCount = 0;
        for (char chr: str.toCharArray())
            if (chr == '/')
                ++slashCount;
        return slashCount;
    }

    /**
     * Traverses each CFG and uses the extracted DEF-USE info
     * to add Flow-dependence edges to the corresponding DDG.
     */
    private static void addDataFlowEdges(ControlFlowGraph cfg, DataDependenceGraph ddg) {
        Set<CFNode> visitedDefs = new LinkedHashSet<>();
        for (CFNode entry: cfg.getAllMethodEntries()) {
            visitedDefs.clear();
            CFPathTraversal defTraversal = new CFPathTraversal(cfg, entry);
            while (defTraversal.hasNext()) {
                CFNode defCFNode = defTraversal.next();
                if (!visitedDefs.add(defCFNode)) {
                    defTraversal.continueNextPath();
                    continue;
                }
                PDNode defNode = (PDNode) defCFNode.getProperty("pdnode");
                if (defNode == null) {
                    //Logger.debug("No PDNode: " + defCFNode);
                    continue;
                }
                if (defNode.getAllDEFs().length == 0)
                    continue;
                // first add any self-flows of this node
                for (String flow: defNode.getAllSelfFlows()) {
                    ddg.addEdge(new Edge<>(defNode, new DDEdge(DDEdge.Type.FLOW, flow), defNode));
                }
                // now traverse the CFG for any USEs till a DEF
                Set<CFNode> visitedUses = new LinkedHashSet<>();
                for (String def: defNode.getAllDEFs()) {
                    CFPathTraversal useTraversal = new CFPathTraversal(cfg, defCFNode);
                    visitedUses.clear();
                    CFNode useCFNode = useTraversal.next(); // skip start node
                    visitedUses.add(useCFNode);
                    while (useTraversal.hasNext()) {
                        useCFNode = useTraversal.next();
                        PDNode useNode = (PDNode) useCFNode.getProperty("pdnode");
                        if (useNode == null) {
                            //Logger.debug("No PDNode: " + useCFNode);
                            continue;
                        }
                        if (useNode.hasDEF(def))
                            useTraversal.continueNextPath(); // no need to continue this path
                        if (!visitedUses.add(useCFNode))
                            useTraversal.continueNextPath(); // no need to continue this path
                        else
                        if (useNode.hasUSE(def))
                            ddg.addEdge(new Edge<>(defNode, new DDEdge(DDEdge.Type.FLOW, def), useNode));
                    }
                }
            }
        }
    }
    /**
     * Visitor class which performs iterative DEF-USE analysis for all program statements.
     */
    private static class DefUseVisitor extends JavaScriptBaseVisitor<String> {

        private static final int PARAM = 1;
        private static final int FIELD = 101;
        private static final int LOCAL = 202;
        private static final int OUTER = 303;

        private int iteration;
        private boolean changed;
        private boolean analysisVisit;
        private JavaScriptClass[] classInfos;
        private DataDependenceGraph ddg;
        private Set<String> defList, useList, selfFlowList;
        private Map<ParserRuleContext, Object> pdNodes;
        private Deque<JavaScriptClass> activeClasses;
        private MethodDefInfo methodDefInfo;
        private JavaScriptField[] methodParams;
        private List<JavaScriptField> localVars;

        public DefUseVisitor(int iter, JavaScriptClass[] classInfos,
                             DataDependenceGraph ddg, Map<ParserRuleContext, Object> pdNodes) {
            Logger.debug("FILE IS: " + currentFile);
            this.ddg = ddg;
            changed = false;
            iteration = iter;
            analysisVisit = false;
            this.pdNodes = pdNodes;
            this.classInfos = classInfos;
            defList = new LinkedHashSet<>();
            useList = new LinkedHashSet<>();
            selfFlowList = new LinkedHashSet<>();
            activeClasses = new ArrayDeque<>();
            methodDefInfo = null;
            methodParams = new JavaScriptField[0];
            localVars = new ArrayList<>();
        }

        private void analyseDefUse(PDNode node, ParseTree expression) {
            Logger.debug("--- ANALYSIS ---");
            Logger.debug(node.toString());
            analysisVisit = true;
            String expr = visit(expression);
            Logger.debug(expr);
            //
            StringBuilder locVarsStr = new StringBuilder(256);
            locVarsStr.append("LOCAL VARS = [");
            for (JavaScriptField lv : localVars)
                locVarsStr.append(lv.TYPE).append(' ').append(lv.NAME).append(", ");
            locVarsStr.append("]");
            Logger.debug(locVarsStr.toString());
            //
            if (isUsableExpression(expr)) {
                useList.add(expr);
                Logger.debug("USABLE");
            }
            analysisVisit = false;
            Logger.debug("Changed = " + changed);
            Logger.debug("DEFs = " + Arrays.toString(node.getAllDEFs()));
            Logger.debug("USEs = " + Arrays.toString(node.getAllUSEs()));
            for (String def : defList) {
                int status = isDefined(def);
                if (status > -1) {
                    if (status < 100) {
                        methodDefInfo.setArgDEF(status, true);
                        Logger.debug("Method defines argument #" + status);
                    } else if (status == FIELD) {
                        methodDefInfo.setStateDEF(true);
                        if (def.startsWith("this."))
                            def = def.substring(5);
                        def = "$THIS." + def;
                        Logger.debug("Method defines object state.");
                    }
                    changed |= node.addDEF(def);
                } else
                    Logger.debug(def + " is not defined!");
            }
            Logger.debug("Changed = " + changed);
            Logger.debug("DEFs = " + Arrays.toString(node.getAllDEFs()));
            //
            for (String use : useList) {
                int status = isDefined(use);
                if (status > -1) {
                    if (status == FIELD) {
                        if (use.startsWith("this."))
                            use = use.substring(5);
                        use = "$THIS." + use;
                    }
                    changed |= node.addUSE(use);
                } else
                    Logger.debug(use + " is not defined!");
            }
            Logger.debug("Changed = " + changed);
            Logger.debug("USEs = " + Arrays.toString(node.getAllUSEs()));
            //
            for (String flow : selfFlowList) {
                int status = isDefined(flow);
                if (status > -1) {
                    if (status == FIELD) {
                        if (flow.startsWith("this."))
                            flow = flow.substring(5);
                        flow = "$THIS." + flow;
                    }
                    changed |= node.addSelfFlow(flow);
                } else
                    Logger.debug(flow + " is not defined!");
            }
            Logger.debug("Changed = " + changed);
            Logger.debug("SELF_FLOWS = " + Arrays.toString(node.getAllSelfFlows()));
            defList.clear();
            useList.clear();
            selfFlowList.clear();
            Logger.debug("----------------");
        }

        /**
         * Check if a given symbol is a defined variable.
         * This returns -1 if the symbol is not defined; otherwise,
         * it returns 101 if the symbol is a class field,
         * or returns 202 if the symbol is a local variable,
         * or returns 303 if the symbol is an outer class field,
         * or if the symbol is a method parameter, returns the index of the parameter.
         */
        private int isDefined(String id) {
            for (int i = 0; i < methodParams.length; ++i)
                if (methodParams[i].NAME.equals(id))
                    return i;
            for (JavaScriptField local : localVars)
                if (local.NAME.equals(id))
                    return LOCAL;
            if (id.startsWith("this."))
                id = id.substring(5);
            for (JavaScriptField field : activeClasses.peek().getAllFields())
                if (field.NAME.equals(id))
                    return FIELD;
            for (JavaScriptClass cls : activeClasses)
                for (JavaScriptField field : cls.getAllFields())
                    if (field.NAME.equals(id))
                        return OUTER;
            return -1;
        }

        /**
         * Return type of a given symbol.
         * Returns null if symbol is not found.
         */
        private String getType(String id) {
            if (isUsableExpression(id)) {
                for (JavaScriptField param : methodParams)
                    if (param.NAME.equals(id))
                        return param.TYPE;
                for (JavaScriptField local : localVars)
                    if (local.NAME.equals(id))
                        return local.TYPE;
                if (id.startsWith("this."))
                    id = id.substring(4);
                for (JavaScriptField field : activeClasses.peek().getAllFields())
                    if (field.NAME.equals(id))
                        return field.TYPE;
                for (JavaScriptClass cls : activeClasses)
                    for (JavaScriptField field : cls.getAllFields())
                        if (field.NAME.equals(id))
                            return field.TYPE;
                Logger.debug("getType(" + id + ") : is USABLE but NOT DEFINED");
                return null;
            } else {
                Logger.debug("getType(" + id + ") : is NOT USABLE");
                // might be:
                // 'this'
                // 'super'
                // literal ($INT, $DBL, $CHR, $STR, $BOL)
                // class-name  [ ID ]
                // constructor-call [ $NEW creator ]
                // method-call [ expr(exprList) ]
                // casting [ $CAST(type) expr ]
                // array-indexing  [ expr[expr] ]
                // unary-op [ ++, --, !, ~ ]
                // paren-expr [ (...) ]
                // array-init [ {...} ]
                return null;
            }
        }

        private JavaScriptClass findClass(String type) {
            return null;
        }

        /**
         * Find and return matching method-definition-info.
         * Returns null if not found.
         */
        private MethodDefInfo findDefInfo(String callee, String name, JavaParser.ExpressionListContext ctx) {
            List<MethodDefInfo> list = methodDEFs.get(name);
            Logger.debug("METHOD NAME: " + name);
            Logger.debug("# found = " + (list == null ? 0 : list.size()));
            //
            if (list == null)
                return null;
            //
            if (list.size() == 1) { // only one candidate
                Logger.debug("SINGLE CANDIDATE");
                MethodDefInfo mtd = list.get(0);
                // just check params-count to make sure
                if (ctx != null && mtd.PARAM_TYPES != null &&
                        mtd.PARAM_TYPES.length != ctx.expression().size())
                    return null;
                Logger.debug("WITH MATCHING PARAMS COUNT");
                return mtd;
            }
            //
            if (callee == null) { // no callee; so search for self methods
                Logger.debug("NO CALLEE");
                forEachDefInfo:
                for (MethodDefInfo mtd : list) {
                    // check package-name
                    if (!mtd.PACKAGE.equals(activeClasses.peek().PACKAGE))
                        continue;
                    // check class-name
                    boolean classNameMatch = false;
                    for (JavaScriptClass cls : activeClasses) {
                        if (mtd.CLASS_NAME.equals(cls.NAME)) {
                            classNameMatch = true;
                            break;
                        }
                    }
                    if (!classNameMatch)
                        continue;
                    // check params-count
                    if (ctx != null && mtd.PARAM_TYPES != null &&
                            mtd.PARAM_TYPES.length != ctx.expression().size())
                        continue;
                    // check params-types
                    if (ctx != null) {
                        String[] argTypes = new String[ctx.expression().size()];
                        for (int i = 0; i < argTypes.length; ++i) {
                            String arg = visit(ctx.expression(i));
                            argTypes[i] = getType(arg);
                        }
                        if (mtd.PARAM_TYPES != null) {
                            for (int i = 0; i < argTypes.length; ++i) {
                                if (argTypes[i] == null)
                                    continue;
                                if (!argTypes[i].equals(mtd.PARAM_TYPES[i]))
                                    continue forEachDefInfo;
                            }
                        }
                    }
                    return mtd;
                }
            } else if (isDefined(callee) > -1) { // has a defined callee
                Logger.debug("DEFINED CALLEE");
                String type = getType(callee);
                JavaScriptClass cls = allClassInfos.get(type);
                if (cls != null && cls.hasMethod(name)) {
                    forEachDefInfo:
                    for (MethodDefInfo mtd : list) {
                        // check package-name
                        if (!mtd.PACKAGE.equals(cls.PACKAGE))
                            continue;
                        // check class-name
                        if (!mtd.CLASS_NAME.equals(cls.NAME))
                            continue;
                        // check params-count
                        if (ctx != null && mtd.PARAM_TYPES != null &&
                                mtd.PARAM_TYPES.length != ctx.expression().size())
                            continue;
                        // check params-types
                        if (ctx != null) {
                            String[] argTypes = new String[ctx.expression().size()];
                            for (int i = 0; i < argTypes.length; ++i) {
                                String arg = visit(ctx.expression(i));
                                argTypes[i] = getType(arg);
                            }
                            if (mtd.PARAM_TYPES != null) {
                                for (int i = 0; i < argTypes.length; ++i) {
                                    if (argTypes[i] == null)
                                        continue;
                                    if (!argTypes[i].equals(mtd.PARAM_TYPES[i]))
                                        continue forEachDefInfo;
                                }
                            }
                        }
                        return mtd;
                    }
                    Logger.debug("METHOD DEF INFO NOT FOUND!");
                } else {
                    Logger.debug((cls == null ?
                            "CLASS OF TYPE " + type + " NOT FOUND!" :
                            "CLASS HAS NO SUCH METHOD!"));
                }
            } else { // has an undefined callee
                Logger.debug("UNDEFINED CALLEE.");
                //
                // TODO: use a global retType for visiting expressions
                //
            }
            return null;
        }

        /**
         * Find and return matching method-definition-info.
         * Returns null if not found.
         */
        private MethodDefInfo findDefInfo(String name, String type, JavaScriptField[] params) {
            List<MethodDefInfo> infoList = methodDEFs.get(name);
            if (infoList.size() > 1) {
                forEachInfo:
                for (MethodDefInfo info : infoList) {
                    if (!info.PACKAGE.equals(activeClasses.peek().PACKAGE))
                        continue;
                    if (!info.CLASS_NAME.equals(activeClasses.peek().NAME))
                        continue;
                    if ((info.RET_TYPE == null && type != null) ||
                            (info.RET_TYPE != null && type == null))
                        continue;
                    if (type != null && !type.startsWith(info.RET_TYPE))
                        continue;
                    if (info.PARAM_TYPES != null) {
                        if (info.PARAM_TYPES.length != params.length)
                            continue;
                        for (int i = 0; i < params.length; ++i)
                            if (!params[i].TYPE.startsWith(info.PARAM_TYPES[i]))
                                continue forEachInfo;
                    } else if (params.length > 0)
                        continue;
                    return info;
                }
            } else if (infoList.size() == 1)
                return infoList.get(0);
            return null;
        }

        /**************************************
         **************************************
         ***          DECLARATIONS          ***
         **************************************
         **************************************/

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) { return visitChildren(ctx); }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassTail(JavaScriptParser.ClassTailContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassElement(JavaScriptParser.ClassElementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) { return visitChildren(ctx); }


        /************************************
         ************************************
         ***          STATEMENTS          ***
         ************************************
         ************************************/


        @Override public String visitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) {
            //expressionStatement
            //    : {this.notOpenBraceAndNotFunction()}? expressionSequence eos
            //    ;
            if (analysisVisit)
                return visit(ctx.expressionSequence());
            //
            PDNode expr;
            if (iteration == 1) {
                expr = new PDNode();
                expr.setLineOfCode(ctx.getStart().getLine());
                expr.setCode(getOriginalCodeText(ctx));
                ddg.addVertex(expr);
                pdNodes.put(ctx, expr);
            } else
                expr = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            analyseDefUse(expr, ctx.expressionSequence());
            return null;
        }

        @Override public String visitIfStatement(JavaScriptParser.IfStatementContext ctx) {
            //
//            ifStatement
//            : If '(' expressionSequence ')' statement (Else statement)?
//            ;
            PDNode ifNode;
            if (iteration == 1) {
                ifNode = new PDNode();
                ifNode.setLineOfCode(ctx.getStart().getLine());
                ifNode.setCode("if " + getOriginalCodeText(ctx.expressionSequence()));
                ddg.addVertex(ifNode);
                pdNodes.put(ctx, ifNode);
            } else
                ifNode = (PDNode) pdNodes.get(ctx);
            //
            for (JavaScriptParser.SingleExpressionContext expressionContext : ctx.expressionSequence().singleExpression()) {
                // Now analyse DEF-USE by visiting the expression ...
                analyseDefUse(ifNode, expressionContext);
            }
            //
            for (JavaScriptParser.StatementContext stmnt: ctx.statement())
                visit(stmnt);
            return null;
        }

        @Override public String visitDoStatement(JavaScriptParser.DoStatementContext ctx) {
            //     : Do statement While '(' expressionSequence ')' eos                                                                       # DoStatement
            visit(ctx.statement());
            //
            PDNode whileNode;
            if (iteration == 1) {
                whileNode = new PDNode();
                whileNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
                whileNode.setCode("while " + getOriginalCodeText(ctx.expressionSequence()));
                ddg.addVertex(whileNode);
                pdNodes.put(ctx, whileNode);
            } else
                whileNode = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            analyseDefUse(whileNode, ctx.expressionSequence());
            return null;
        }

        @Override public String visitWhileStatement(JavaScriptParser.WhileStatementContext ctx) {
            //     | While '(' expressionSequence ')' statement                                                                              # WhileStatement
            PDNode whileNode;
            if (iteration == 1) {
                whileNode = new PDNode();
                whileNode.setLineOfCode(ctx.getStart().getLine());
                whileNode.setCode("while " + getOriginalCodeText(ctx.expressionSequence()));
                ddg.addVertex(whileNode);
                pdNodes.put(ctx, whileNode);
            } else
                whileNode = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            for (JavaScriptParser.SingleExpressionContext singleExpression : ctx.expressionSequence().singleExpression()) {
                analyseDefUse(whileNode, singleExpression);
            }
            //
            return visit(ctx.statement());
        }

        @Override public String visitForStatement(JavaScriptParser.ForStatementContext ctx) {
            //     | For '(' (expressionSequence | variableDeclarationList)? ';' expressionSequence? ';' expressionSequence? ')' statement   # ForStatement
            int entrySize = localVars.size();
            //  First, we should check type of for-loop ...
            // It's a traditional for-loop:
            int index=0;
            if(ctx.expressionSequence().size()==3){
                ++index;
                PDNode forInit;
                if (iteration == 1) {
                    forInit = new PDNode();
                    forInit.setLineOfCode(ctx.expressionSequence(0).getStart().getLine());
                    forInit.setCode(getOriginalCodeText(ctx.expressionSequence(0)));
                    ddg.addVertex(forInit);
                    pdNodes.put(ctx.expressionSequence(0), forInit);
                } else
                    forInit = (PDNode) pdNodes.get(ctx.expressionSequence(0));
                //
                // Now analyse DEF-USE by visiting the expression ...
                if (ctx.variableDeclarationList()!= null)
                    analyseDefUse(forInit, ctx.variableDeclarationList());
            }else{
                //variableDeclarationList不为空或为空
                if (ctx.variableDeclarationList()!=null){
                    PDNode forInit;
                    if (iteration == 1) {
                        forInit = new PDNode();
                        forInit.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                        forInit.setCode(getOriginalCodeText(ctx.variableDeclarationList()));
                        ddg.addVertex(forInit);
                        pdNodes.put(ctx.variableDeclarationList(), forInit);
                    } else
                        forInit = (PDNode) pdNodes.get(ctx.variableDeclarationList());
                    //
                    // Now analyse DEF-USE by visiting the expression ...
                    if (ctx.variableDeclarationList()!= null)
                        analyseDefUse(forInit, ctx.variableDeclarationList());
                }
            }
            // for-expression
            if (ctx.expressionSequence(index) != null) { // non-empty predicate-expression
                PDNode forExpr;
                if (iteration == 1) {
                    forExpr = new PDNode();
                    forExpr.setLineOfCode(ctx.expressionSequence(index).getStart().getLine());
                    forExpr.setCode("for (" + getOriginalCodeText(ctx.expressionSequence(index)) + ")");
                    ddg.addVertex(forExpr);
                    pdNodes.put(ctx.expressionSequence(index), forExpr);
                } else
                    forExpr = (PDNode) pdNodes.get(ctx.expressionSequence(index));
                //
                // Now analyse DEF-USE by visiting the expression ...
                analyseDefUse(forExpr, ctx.expressionSequence(index));
            }
            // for-update
            if (ctx.expressionSequence(index+1) != null) { // non-empty for-update
                PDNode forUpdate;
                if (iteration == 1) {
                    forUpdate = new PDNode();
                    forUpdate.setCode(getOriginalCodeText(ctx.expressionSequence(index+1)));
                    forUpdate.setLineOfCode(ctx.expressionSequence(index+1).getStart().getLine());
                    ddg.addVertex(forUpdate);
                    pdNodes.put(ctx.expressionSequence(index+1), forUpdate);
                } else
                    forUpdate = (PDNode) pdNodes.get(ctx.expressionSequence(index+1));
                //
                // Now analyse DEF-USE by visiting the expression ...
                analyseDefUse(forUpdate, ctx.expressionSequence(index+1));
            }
            // visit for loop body
            String visit = visit(ctx.statement());
            // clear any local vars defined in the for loop
            if (localVars.size() > entrySize)
                localVars.subList(entrySize, localVars.size()).clear();
            return visit;
        }

        @Override public String visitForInStatement(JavaScriptParser.ForInStatementContext ctx) {
            //    | For '(' (singleExpression | variableDeclarationList) In expressionSequence ')' statement                                # ForInStatement
            int entrySize = localVars.size();
            PDNode forExpr;
            if (iteration == 1) {
                forExpr = new PDNode();
                forExpr.setLineOfCode(ctx.getStart().getLine());
                forExpr.setCode("for (" + getOriginalCodeText(ctx) + ")");
                ddg.addVertex(forExpr);
                pdNodes.put(ctx, forExpr);
            } else
                forExpr = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            String type = null;
            String var=null;
            if (ctx.singleExpression()!=null){
                var=getOriginalCodeText(ctx.singleExpression());
            }else if(ctx.variableDeclarationList()!=null){
                var=getOriginalCodeText(ctx.variableDeclarationList());
            }
            localVars.add(new JavaScriptField(null, false, type, var));
            changed |= forExpr.addDEF(var);
            analyseDefUse(forExpr, ctx);
            // visit for loop body
            String visit = visit(ctx.statement());
            // clear any local vars defined in the for loop
            if (localVars.size() > entrySize)
                localVars.subList(entrySize, localVars.size()).clear();
            return visit;
        }

        @Override public String visitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) {
            //continueStatement
            //    : Continue ({this.notLineTerminator()}? identifier)? eos
            //    ;
            PDNode ret;
            if (iteration == 1) {
                ret = new PDNode();
                ret.setLineOfCode(ctx.getStart().getLine());
                ret.setCode(getOriginalCodeText(ctx));
                ddg.addVertex(ret);
                pdNodes.put(ctx, ret);
            } else
                ret = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            if (ctx.identifier() != null)
                analyseDefUse(ret, ctx.identifier());
            return null;
        }

        @Override public String visitBreakStatement(JavaScriptParser.BreakStatementContext ctx) {
            //breakStatement
            //    : Break ({this.notLineTerminator()}? identifier)? eos
            //    ;
            PDNode ret;
            if (iteration == 1) {
                ret = new PDNode();
                ret.setLineOfCode(ctx.getStart().getLine());
                ret.setCode(getOriginalCodeText(ctx));
                ddg.addVertex(ret);
                pdNodes.put(ctx, ret);
            } else
                ret = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            if (ctx.identifier() != null)
                analyseDefUse(ret, ctx.identifier());
            return null;
        }

        @Override public String visitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) {
            //returnStatement
            //    : Return ({this.notLineTerminator()}? expressionSequence)? eos
            //    ;
            PDNode ret;
            if (iteration == 1) {
                ret = new PDNode();
                ret.setLineOfCode(ctx.getStart().getLine());
                ret.setCode(getOriginalCodeText(ctx));
                ddg.addVertex(ret);
                pdNodes.put(ctx, ret);
            } else
                ret = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            if (ctx.expressionSequence() != null)
                analyseDefUse(ret, ctx.expressionSequence());
            return null;
        }

        @Override public String visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
           //yieldStatement
            //    : Yield ({this.notLineTerminator()}? expressionSequence)? eos
            //    ;
            PDNode ret;
            if (iteration == 1) {
                ret = new PDNode();
                ret.setLineOfCode(ctx.getStart().getLine());
                ret.setCode(getOriginalCodeText(ctx));
                ddg.addVertex(ret);
                pdNodes.put(ctx, ret);
            } else
                ret = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            if (ctx.expressionSequence() != null)
                analyseDefUse(ret, ctx.expressionSequence());
            return null;
        }

        @Override public String visitWithStatement(JavaScriptParser.WithStatementContext ctx) {
            //withStatement
            //    : With '(' expressionSequence ')' statement
            //    ;
            PDNode ret;
            if (iteration == 1) {
                ret = new PDNode();
                ret.setLineOfCode(ctx.getStart().getLine());
                ret.setCode(getOriginalCodeText(ctx));
                ddg.addVertex(ret);
                pdNodes.put(ctx, ret);
            } else
                ret = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            if (ctx.expressionSequence() != null)
                analyseDefUse(ret, ctx.expressionSequence());
            return null;
        }

        @Override public String visitSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) {
            //  switchStatement
            //    : Switch '(' expressionSequence ')' caseBlock
            //    ;
            // caseBlock
            //    : '{' caseClauses? (defaultClause caseClauses?)? '}'
            //    ;
            //
            //caseClauses
            //    : caseClause+
            //    ;
            //
            //caseClause
            //    : Case expressionSequence ':' statementList?
            //    ;
            //
            //defaultClause
            //    : Defa
            PDNode switchNode;
            if (iteration == 1) {
                switchNode = new PDNode();
                switchNode.setLineOfCode(ctx.getStart().getLine());
                switchNode.setCode("switch " + getOriginalCodeText(ctx.expressionSequence()));
                ddg.addVertex(switchNode);
                pdNodes.put(ctx, switchNode);
            } else
                switchNode = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            analyseDefUse(switchNode, ctx.expressionSequence());
            //
            for (JavaScriptParser.CaseClausesContext caseClause : ctx.caseBlock().caseClauses()) {
                for (JavaScriptParser.CaseClauseContext caseContext : caseClause.caseClause()) {
                    visit(caseContext);
                }
            }
            if (ctx.caseBlock().defaultClause()!=null){
                visit(ctx.caseBlock().defaultClause());
            }
            return null;
        }


        @Override public String visitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) {
            //     : Throw {this.notLineTerminator()}? expressionSequence eos
            PDNode throwNode;
            if (iteration == 1) {
                throwNode = new PDNode();
                throwNode.setLineOfCode(ctx.getStart().getLine());
                throwNode.setCode("throw " + getOriginalCodeText(ctx.expressionSequence()));
                ddg.addVertex(throwNode);
                pdNodes.put(ctx, throwNode);
            } else
                throwNode = (PDNode) pdNodes.get(ctx);
            //
            // Now analyse DEF-USE by visiting the expression ...
            analyseDefUse(throwNode, ctx.expressionSequence());
            return null;
        }

        @Override public String visitTryStatement(JavaScriptParser.TryStatementContext ctx) {
            //tryStatement
            //    : Try block (catchProduction finallyProduction? | finallyProduction)
            //    ;
            // The 'try' block has no DEF-USE effect, so no need for PDNodes;
            // just visit the 'block'
            visit(ctx.block());
            //
            // But the 'catchClause' define a local exception variable;
            // so we need to visit any available catch clauses
            if (ctx.catchProduction() != null ) {
                //catchProduction
                //    : Catch ('(' assignable? ')')? block
                JavaScriptParser.CatchProductionContext cx = ctx.catchProduction();
                PDNode catchNode;
                if (iteration == 1) {
                    catchNode = new PDNode();
                    catchNode.setLineOfCode(cx.getStart().getLine());
                    catchNode.setCode("catch (" + getOriginalCodeText(cx.assignable()) +  ")");
                    ddg.addVertex(catchNode);
                    pdNodes.put(cx, catchNode);
                } else
                    catchNode = (PDNode) pdNodes.get(cx);
                //
                // Define the exception var
                String type = getOriginalCodeText(cx);
                String var = getOriginalCodeText(cx.assignable());
                JavaScriptField exceptionVar = new JavaScriptField(null, false, type, var);
                localVars.add(exceptionVar);
                changed |= catchNode.addDEF(var);
                //
                visit(cx.block());
                localVars.remove(exceptionVar);
            }
            if (ctx.finallyProduction() != null)
                // 'finally' block
                visit(ctx.finallyProduction().block());

            return null;
        }



        /***********************************************
         ***********************************************
         ***       NON-DETERMINANT EXPRESSIONS       ***
         ***********************************************
         ***********************************************/



        /**
         * Check to see if the given expression is USABLE.
         * An expression is usable if we are required to add it to the USE-list.
         * Any expression who is DEFINABLE should be added to the USE-list.
         * An expression is definable, if it holds a value which can be modified in the program.
         * For example, Class names and Class types are not definable.
         * Method invocations are not definable.
         * Literals are also not definable.
         */
        private boolean isUsableExpression(String expr) {
            // must not be a literal or of type 'class'.
            if (expr.startsWith("$"))
                return false;
            // must not be a method-call or parenthesized expression
            if (expr.endsWith(")"))
                return false;
            // must not be an array-indexing expression
            if (expr.endsWith("]"))
                return false;
            // must not be post unary operation expression
            if (expr.endsWith("++") || expr.endsWith("--"))
                return false;
            // must not be a pre unary operation expression
            if (expr.startsWith("+") || expr.startsWith("-") || expr.startsWith("!") || expr.startsWith("~"))
                return false;
            // must not be an array initialization expression
            if (expr.endsWith("}"))
                return false;
            // must not be an explicit generic invocation expression
            if (expr.startsWith("<"))
                return false;
            //
            return true;
        }

        /**
         * Visit the list of arguments of a method call, and return a proper string.
         * This method will also add usable expressions to the USE-list.
         */
        private String visitMethodArgs(JavaParser.ExpressionListContext ctx, MethodDefInfo defInfo) {
            // expressionList :  expression (',' expression)*
//            if (ctx != null) {
//                StringBuilder args = new StringBuilder();
//                List<JavaScriptParser.ExpressionContext> argsList = ctx.expression();
//                String arg = visit(argsList.get(0));
//                args.append(arg);
//                if (isUsableExpression(arg)) {
//                    useList.add(arg);
//                    if (defInfo != null && defInfo.argDEFs()[0])
//                        defList.add(arg);
//                }
//                for (int i = 1; i < argsList.size(); ++i) {
//                    arg = visit(argsList.get(i));
//                    args.append(", ").append(arg);
//                    if (isUsableExpression(arg)) {
//                        useList.add(arg);
//                        if (defInfo != null && defInfo.argDEFs()[i])
//                            defList.add(arg);
//                    }
//                }
//                return args.toString();
//            } else
//                return "";
            return "";
        }


        /*****************************************************
         *****************************************************
         *****************************************************/

        /**
         * Get the original program text for the given parser-rule context.
         * This is required for preserving whitespaces.
         */
        private String getOriginalCodeText(ParserRuleContext ctx) {
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
        }
    }
}

/**
 * A simple structure to store DEF information about a Java method.
 *
 * @author Seyed Mohammad Ghaffarian
 */
class MethodDefInfo {

    // Method ID
    public final String NAME;
    public final String PACKAGE;
    public final String RET_TYPE;
    public final String CLASS_NAME;
    public final String[] PARAM_TYPES;

    // DEF Info
    private boolean stateDEF;
    private boolean[] argDEFs;
    private List<String> fieldDEFs;

    public MethodDefInfo(String ret, String name, String pkg, String cls, String[] args) {
        NAME = name;
        RET_TYPE = ret;
        CLASS_NAME = cls;
        PACKAGE = pkg == null ? "" : pkg;
        PARAM_TYPES = args == null ? new String[0] : args;
        //
        fieldDEFs = new ArrayList<>();
        stateDEF = guessByTypeOrName();
        argDEFs = new boolean[PARAM_TYPES.length];  // all initialized to 'false'
    }

    private boolean guessByTypeOrName() {
        // First check if this method is a constructor ...
        if (RET_TYPE == null)
            return true;
        // If not, then try to guess by method-name ...
        String[] prefixes = { "set", "put", "add", "insert", "push", "append" };
        for (String pre: prefixes)
            if (NAME.toLowerCase().startsWith(pre))
                return true;
        return false;
    }

    public boolean doesStateDEF() {
        return stateDEF;
    }

    public void setStateDEF(boolean stateDef) {
        stateDEF = stateDef;
    }

    public boolean[] argDEFs() {
        return argDEFs;
    }

    public void setArgDEF(int argIndex, boolean def) {
        argDEFs[argIndex] = def;
    }

    public void setAllArgDEFs(boolean[] argDefs) {
        argDEFs = argDefs;
    }

    public String[] fieldDEFs() {
        return fieldDEFs.toArray(new String[fieldDEFs.size()]);
    }

    public void addFieldDEF(String fieldName) {
        if (!fieldDEFs.contains(fieldName)) {
            fieldDEFs.add(fieldName);
            stateDEF = true;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MethodDefInfo))
            return false;
        MethodDefInfo info = (MethodDefInfo) obj;
        return this.NAME.equals(info.NAME) && this.CLASS_NAME.equals(info.CLASS_NAME)
                && this.PACKAGE.equals(info.PACKAGE) && this.RET_TYPE.equals(info.RET_TYPE)
                && Arrays.equals(this.PARAM_TYPES, info.PARAM_TYPES);
    }

    @Override
    public String toString() {
        String retType = RET_TYPE == null ? "null" : RET_TYPE;
        String args = PARAM_TYPES == null ? "null" : Arrays.toString(PARAM_TYPES);
        StringBuilder str = new StringBuilder();
        str.append("{ TYPE : \"").append(retType).append("\", ");
        str.append("NAME : \"").append(NAME).append("\", ");
        str.append("ARGS : ").append(args).append(", ");
        str.append("CLASS : \"").append(CLASS_NAME).append("\", ");
        str.append("PACKAGE : \"").append(PACKAGE).append("\" }");
        return str.toString();
    }
}
