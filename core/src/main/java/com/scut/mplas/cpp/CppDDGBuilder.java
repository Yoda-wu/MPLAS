package com.scut.mplas.cpp;
/*** In The Name of Allah ***/


import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.CFPathTraversal;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.graphs.pdg.DDEdge;
import com.scut.mplas.graphs.pdg.DataDependenceGraph;
import com.scut.mplas.graphs.pdg.PDNode;
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
/**
 * Data Dependence Graph (DDG) builder for Cpp programs.
 * The DDG is actually a subgraph of the Program Dependence Graph (PDG).
 * This implementation is based on ANTLRv4's Visitor pattern.
 *
 */
public class CppDDGBuilder {

    // Just for debugging
    private static String currentFile;

    // NOTE: 在不同namespace的相同class name暂不支持
    //       This doesn't handle duplicate class names;
    //       yet assuming no duplicate class names is fair enough.
    //       To handle that, we should use 'Map<String, List<CppaClass>>'
    private static Map<String, CppClass> allClassInfos;
    // NOTE: 这个Map记录了所有那些非class成员函数的函数的信息，支持不同namespace的相同function name和
    //       函数重载
    private static Map<String, List<CppMethod>> allNonClassFunctionInfos;

    private static Map<String, List<MethodDefInfo>> methodDEFs;

    public static DataDependenceGraph[] buildForAll(File[] files) throws IOException {
        // Parse all Java source files
        Logger.info("Parsing all source files ... ");
        ParseTree[] parseTrees = new ParseTree[files.length];
        for (int i = 0; i < files.length; ++i) {
            InputStream inFile = new FileInputStream(files[i]);
            ANTLRInputStream input = new ANTLRInputStream(inFile);
            CppLexer lexer = new CppLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CppParser parser = new CppParser(tokens);
            parseTrees[i] = parser.translationUnit();
        }
        Logger.info("Done.");

        DataDependenceGraph[] ddgs = new DataDependenceGraph[files.length];
        for (int i = 0; i < ddgs.length; ++i)
            ddgs[i] = new DataDependenceGraph(files[i].getName());

        Map<ParserRuleContext, Object>[] pdNodes = new Map[parseTrees.length];
        for (int i = 0; i < parseTrees.length; ++i)
            pdNodes[i] = new IdentityHashMap<>();

        // Build ddg for each Cpp file
        Logger.info("\nbegin ddg build for each file ... ");
        for (int i = 0; i < ddgs.length; ++i)
            build(files[i], parseTrees[i], ddgs[i], pdNodes[i]);
        Logger.info("Done.");

        // Build control-flow graphs for all Cpp files including the extracted DEF-USE info ...
        Logger.info("\nExtracting CFGs ... ");
        ControlFlowGraph[] cfgs = new ControlFlowGraph[files.length];
        for (int i = 0; i < files.length; ++i)
            cfgs[i] = CppCFGBuilder.build(files[i].getName(), parseTrees[i], "pdnode", pdNodes[i]);
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

    public static DataDependenceGraph buildForOne(String fileName, InputStream inputStream) throws IOException {
        // Parse all Java source files
        Logger.info("Parsing source file : "+fileName);
        InputStream inFile = inputStream;
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree parseTree = parser.translationUnit();
        Logger.info("Done.");

        DataDependenceGraph ddg = new DataDependenceGraph(fileName);
        Map<ParserRuleContext, Object> pdNodes = new IdentityHashMap<>();

        // Build ddg for each Cpp file
        Logger.info("\nbegin ddg build for one file ... ");
        build(fileName, parseTree, ddg, pdNodes);
        Logger.info("Done.");

        // Build control-flow graphs for all Cpp files including the extracted DEF-USE info ...
        Logger.info("\nExtracting CFG ... ");
        ControlFlowGraph cfg = CppCFGBuilder.build(fileName, parseTree, "pdnode", pdNodes);
        Logger.info("Done.");

        // Finally, traverse all control-flow paths and draw data-flow dependency edges ...
        Logger.info("\nAdding data-flow edge ... ");
        addDataFlowEdges(cfg, ddg);
        ddg.attachCFG(cfg);
        Logger.info("Done.\n");

        return ddg;
    }

    // 为每一个源代码文件都进行DDG分析
    private static void build(File file, ParseTree parseTree,
                              DataDependenceGraph ddg,
                              Map<ParserRuleContext, Object> pdNodes) throws IOException {
        // Extract the information of all given Cpp classes and NonClass Function
        Logger.info("\nstart to def-use analyse file : "+file.getName());
        Logger.info("\nExtracting class-infos and nonClass-Func-infos ... ");
        allClassInfos = new HashMap<>();
        allNonClassFunctionInfos = new HashMap<>();
        methodDEFs = new HashMap<>();
        List<CppClass> classesList = new LinkedList<>();
        List<CppMethod> functiopnsList = new LinkedList<>();

        CppExtractor.extractInfo(file.getPath(), parseTree, classesList, functiopnsList);
        for (CppClass cls : classesList)
            allClassInfos.put(cls.NAME, cls);
        Logger.info("Done.");

        // Initialize method DEF information
        Logger.info("\nInitializing method-DEF infos ... ");
        // 增加非class函数信息
        for (CppMethod func : functiopnsList) {
            List<MethodDefInfo> list = methodDEFs.get(func.NAME);
            if (list == null) {
                list = new ArrayList<>();
                list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, null, func.ARG_TYPES));
                methodDEFs.put(func.NAME, list);
            } else
                list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, null, func.ARG_TYPES));
        }
        // 增加class 成员函数信息
        for (CppClass cls : classesList)
            for (CppMethod func : cls.getAllMethods()) {
                List<MethodDefInfo> list = methodDEFs.get(func.NAME);
                if (list == null) {
                    list = new ArrayList<>();
                    list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, cls.NAME, func.ARG_TYPES));
                    methodDEFs.put(func.NAME, list);
                } else
                    list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, cls.NAME, func.ARG_TYPES));
            }
        Logger.info("Done.");

        Logger.info("\nIterative DEF-USE analysis ... ");
        boolean changed;
        int iteration = 0;
        do {
            ++iteration;
            changed = false;

            currentFile = file.getName();
            DefUseVisitor defUse = new DefUseVisitor(iteration, classesList.toArray(new CppClass[classesList.size()]), ddg, pdNodes);
            defUse.visit(parseTree);
            changed |= defUse.changed;

            Logger.debug("Iteration #" + iteration + ": " + (changed ? "CHANGED" : "NO-CHANGE"));
            Logger.debug("\n========================================\n");
        } while (changed);
        Logger.info("Done.");

    }

    private static void build(String fileName, ParseTree parseTree,
                              DataDependenceGraph ddg,
                              Map<ParserRuleContext, Object> pdNodes) throws IOException {
        // Extract the information of all given Cpp classes and NonClass Function
        Logger.info("\nExtracting class-infos and nonClass-Func-infos ... ");
        allClassInfos = new HashMap<>();
        allNonClassFunctionInfos = new HashMap<>();
        methodDEFs = new HashMap<>();
        List<CppClass> classesList = new LinkedList<>();
        List<CppMethod> functiopnsList = new LinkedList<>();

        CppExtractor.extractInfo(fileName, parseTree, classesList, functiopnsList);
        for (CppClass cls : classesList)
            allClassInfos.put(cls.NAME, cls);
        Logger.info("Done.");

        // Initialize method DEF information
        Logger.info("\nInitializing method-DEF infos ... ");
        // 增加非class函数信息
        for (CppMethod func : functiopnsList) {
            List<MethodDefInfo> list = methodDEFs.get(func.NAME);
            if (list == null) {
                list = new ArrayList<>();
                list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, null, func.ARG_TYPES));
                methodDEFs.put(func.NAME, list);
            } else
                list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, null, func.ARG_TYPES));
        }
        // 增加class 成员函数信息
        for (CppClass cls : classesList)
            for (CppMethod func : cls.getAllMethods()) {
                List<MethodDefInfo> list = methodDEFs.get(func.NAME);
                if (list == null) {
                    list = new ArrayList<>();
                    list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, cls.NAME, func.ARG_TYPES));
                    methodDEFs.put(func.NAME, list);
                } else
                    list.add(new MethodDefInfo(func.RET_TYPE, func.NAME, func.NAMESPACE, cls.NAME, func.ARG_TYPES));
            }
        Logger.info("Done.");

        Logger.info("\nIterative DEF-USE analysis ... ");
        boolean changed;
        int iteration = 0;
        do {
            ++iteration;
            changed = false;

            currentFile = fileName;
            DefUseVisitor defUse = new DefUseVisitor(iteration, classesList.toArray(new CppClass[classesList.size()]), ddg, pdNodes);
            defUse.visit(parseTree);
            changed |= defUse.changed;

            Logger.debug("Iteration #" + iteration + ": " + (changed ? "CHANGED" : "NO-CHANGE"));
            Logger.debug("\n========================================\n");
        } while (changed);
        Logger.info("Done.");

    }

    /**
     * Traverses each CFG and uses the extracted DEF-USE info
     * to add Flow-dependence edges to the corresponding DDG.
     */
    private static void addDataFlowEdges(ControlFlowGraph cfg, DataDependenceGraph ddg) {
        Set<CFNode> visitedDefs = new LinkedHashSet<>();
        for (CFNode entry : cfg.getAllMethodEntries()) {
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
                for (String flow : defNode.getAllSelfFlows()) {
                    ddg.addEdge(new Edge<>(defNode, new DDEdge(DDEdge.Type.FLOW, flow), defNode));
                }
                // now traverse the CFG for any USEs till a DEF
                Set<CFNode> visitedUses = new LinkedHashSet<>();
                for (String def : defNode.getAllDEFs()) {
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
                        else if (useNode.hasUSE(def))
                            ddg.addEdge(new Edge<>(defNode, new DDEdge(DDEdge.Type.FLOW, def), useNode));
                    }
                }
            }
        }
    }
    /**
     * Visitor class which performs iterative DEF-USE analysis for all program statements.
     */
    private static class DefUseVisitor extends CppBaseVisitor<String> {
        /**
         * 表示某个命名空间中的全局变量，如果要支持在一个命名空间中使用其他命名空间的变量的话
         * 需要设置出一个命名空间的表来保存这些命名空间信息，暂时不支持这种方式
         * 目前仅考虑，一个命名空间只有唯一一个定义，然后一个命名空间中的全局变量只在这个命名空间中使用
         */
        private class Scope {
            public Scope parent;
            public List<CppField> vars;

            public Scope() {
                parent = null;
                vars = new ArrayList<>();
            }
        }

        private static final int PARAM = 1;
        private static final int FIELD = 101;
        private static final int LOCAL = 202;
        private static final int OUTER = 303;
        private static final int GLOBAL = 404;

        private int iteration;
        private boolean changed;
        private boolean analysisVisit;
        private CppClass[] classInfos;
        private DataDependenceGraph ddg;
        private Set<String> defList, useList, selfFlowList;
        private Map<ParserRuleContext, Object> pdNodes;
        private Deque<CppClass> activeClasses;
        private MethodDefInfo methodDefInfo;
        private CppField[] methodParams;
        private List<CppField> localVars;
        private Deque<String> namespaces;
        private Scope currentScope;

        private String specifier;
        private String type;
        private String pointOp;
        private String nestedName;
        private String varName;

        private boolean isGlobal;
        private boolean isOnlyPointerDecl;
        private boolean isHasParameters;

        public DefUseVisitor(int iter, CppClass[] classInfos,
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
            methodParams = new CppField[0];
            localVars = new ArrayList<>();
            namespaces = new ArrayDeque<>();
            // 全局命名空间
            namespaces.push("");
            currentScope = new Scope();
            clearAllFlags();
        }

        private void clearAllFlags() {
            isGlobal = false;
            isOnlyPointerDecl = false;
            isHasParameters = false;
        }

        private void analyseDefUse(PDNode node, ParseTree expression) {
            Logger.debug("--- ANALYSIS ---");
            Logger.debug(node.toString());
            analysisVisit = true;
            String expr = "";
            if (expression != null)
                expr = visit(expression);
            Logger.debug(expr);
            //
            StringBuilder locVarsStr = new StringBuilder(256);
            locVarsStr.append("LOCAL VARS = [");
            for (CppField lv : localVars)
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
                        if (def.startsWith("this->"))
                            def = def.substring(6);
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
                        if (use.startsWith("this->"))
                            use = use.substring(6);
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
                        if (flow.startsWith("this->"))
                            flow = flow.substring(6);
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
            // 查找局部变量
            for (CppField local : localVars)
                if (local.NAME.equals(id))
                    return LOCAL;
            // 函数参数
            for (int i = 0; i < methodParams.length; ++i)
                if (methodParams[i].NAME.equals(id))
                    return i;
            // 当前所在命名空间中的全局变量
            for (CppField global : currentScope.vars)
                if (global.NAME.equals(id))
                    return GLOBAL;
            if (id.startsWith("this->"))
                id = id.substring(6);
            if (!activeClasses.isEmpty()) {
                for (CppField field : activeClasses.peek().getAllFields())
                    if (field.NAME.equals(id))
                        return FIELD;
                for (CppClass cls : activeClasses)
                    for (CppField field : cls.getAllFields())
                        if (field.NAME.equals(id))
                            return OUTER;
            }

            return -1;
        }

        /**
         * Return type of a given symbol.
         * Returns null if symbol is not found.
         */
        private String getType(String id) {
            if (isUsableExpression(id)) {
                for (CppField param : methodParams)
                    if (param.NAME.equals(id))
                        return param.TYPE;
                for (CppField local : localVars)
                    if (local.NAME.equals(id))
                        return local.TYPE;
                if (id.startsWith("this->"))
                    id = id.substring(6);
                for (CppField field : activeClasses.peek().getAllFields())
                    if (field.NAME.equals(id))
                        return field.TYPE;
                for (CppClass cls : activeClasses)
                    for (CppField field : cls.getAllFields())
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

        /**
         * 根据给定的类名，查找当前命名空间中相应的类
         * 如果没找到返回null
         */
        private CppClass findClass(String className) {
            for (CppClass cls : classInfos)
                if (cls.NAME.equals(className) && cls.NAMESPACE.equals(namespaces.peek()))
                    return cls;
            return null;
        }

        /**
         * Find and return matching method-definition-info.
         * Returns null if not found.
         */
//        private MethodDefInfo findDefInfo(String callee, String name, CppParser.ExpressionListContext ctx) {
//            List<MethodDefInfo> list = methodDEFs.get(name);
//            Logger.debug("METHOD NAME: " + name);
//            Logger.debug("# found = " + (list == null ? 0 : list.size()));
//            //
//            if (list == null)
//                return null;
//            //
//            if (list.size() == 1) { // only one candidate
//                Logger.debug("SINGLE CANDIDATE");
//                MethodDefInfo mtd = list.get(0);
//                // just check params-count to make sure
//                if (ctx != null && mtd.PARAM_TYPES != null &&
//                        mtd.PARAM_TYPES.length != ctx.expression().size())
//                    return null;
//                Logger.debug("WITH MATCHING PARAMS COUNT");
//                return mtd;
//            }
//            //
//            if (callee == null) { // no callee; so search for self methods
//                Logger.debug("NO CALLEE");
//                forEachDefInfo:
//                for (MethodDefInfo mtd : list) {
//                    // check package-name
//                    if (!mtd.PACKAGE.equals(activeClasses.peek().PACKAGE))
//                        continue;
//                    // check class-name
//                    boolean classNameMatch = false;
//                    for (JavaClass cls: activeClasses) {
//                        if (mtd.CLASS_NAME.equals(cls.NAME)) {
//                            classNameMatch = true;
//                            break;
//                        }
//                    }
//                    if (!classNameMatch)
//                        continue;
//                    // check params-count
//                    if (ctx != null && mtd.PARAM_TYPES != null &&
//                            mtd.PARAM_TYPES.length != ctx.expression().size())
//                        continue;
//                    // check params-types
//                    if (ctx != null) {
//                        String[] argTypes = new String[ctx.expression().size()];
//                        for (int i = 0; i < argTypes.length; ++i) {
//                            String arg = visit(ctx.expression(i));
//                            argTypes[i] = getType(arg);
//                        }
//                        if (mtd.PARAM_TYPES != null) {
//                            for (int i = 0; i < argTypes.length; ++i) {
//                                if (argTypes[i] == null)
//                                    continue;
//                                if (!argTypes[i].equals(mtd.PARAM_TYPES[i]))
//                                    continue forEachDefInfo;
//                            }
//                        }
//                    }
//                    return mtd;
//                }
//            } else if (isDefined(callee) > -1) { // has a defined callee
//                Logger.debug("DEFINED CALLEE");
//                String type = getType(callee);
//                JavaClass cls = allClassInfos.get(type);
//                if (cls != null && cls.hasMethod(name)) {
//                    forEachDefInfo:
//                    for (MethodDefInfo mtd : list) {
//                        // check package-name
//                        if (!mtd.PACKAGE.equals(cls.PACKAGE))
//                            continue;
//                        // check class-name
//                        if (!mtd.CLASS_NAME.equals(cls.NAME))
//                            continue;
//                        // check params-count
//                        if (ctx != null && mtd.PARAM_TYPES != null &&
//                                mtd.PARAM_TYPES.length != ctx.expression().size())
//                            continue;
//                        // check params-types
//                        if (ctx != null) {
//                            String[] argTypes = new String[ctx.expression().size()];
//                            for (int i = 0; i < argTypes.length; ++i) {
//                                String arg = visit(ctx.expression(i));
//                                argTypes[i] = getType(arg);
//                            }
//                            if (mtd.PARAM_TYPES != null) {
//                                for (int i = 0; i < argTypes.length; ++i) {
//                                    if (argTypes[i] == null)
//                                        continue;
//                                    if (!argTypes[i].equals(mtd.PARAM_TYPES[i]))
//                                        continue forEachDefInfo;
//                                }
//                            }
//                        }
//                        return mtd;
//                    }
//                    Logger.debug("METHOD DEF INFO NOT FOUND!");
//                } else {
//                    Logger.debug((cls == null ?
//                            "CLASS OF TYPE " + type + " NOT FOUND!" :
//                            "CLASS HAS NO SUCH METHOD!"));
//                }
//            } else { // has an undefined callee
//                Logger.debug("UNDEFINED CALLEE.");
//                //
//                // TODO: use a global retType for visiting expressions
//                //
//            }
//            return null;
//        }

        /**
         * Find and return matching method-definition-info.
         * Returns null if not found.
         */
        private MethodDefInfo findDefInfo(String name, String type, CppField[] params, String className) {
            List<MethodDefInfo> infoList = methodDEFs.get(name);
            if (infoList == null)
                return null;
            if (infoList.size() > 1) {
                forEachInfo:
                for (MethodDefInfo info : infoList) {
                    if (!info.NAMESPACE.equals(namespaces.peek()))
                        continue;
                    if (!info.CLASS_NAME.equals(className))
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
            if (expr == null || expr == "")
                return false;
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
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTranslationUnit(CppParser.TranslationUnitContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitPrimaryExpression(CppParser.PrimaryExpressionContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitIdExpression(CppParser.IdExpressionContext ctx) {
            if (analysisVisit) {
                //idExpression: unqualifiedId | qualifiedId;
                //
                //unqualifiedId:
                //	Identifier
                //	| operatorFunctionId
                //	| conversionFunctionId
                //	| literalOperatorId
                //	| Tilde (className | decltypeSpecifier)
                //	| templateId;
                //
                //qualifiedId: nestedNameSpecifier Template? unqualifiedId;
                String curName;
                if (ctx.unqualifiedId() != null)
                    curName = getOriginalCodeText(ctx.unqualifiedId());
                else
                    curName = getOriginalCodeText(ctx.qualifiedId().unqualifiedId());
                useList.add(curName);
                return "";
            }
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitUnqualifiedId(CppParser.UnqualifiedIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitQualifiedId(CppParser.QualifiedIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNestedNameSpecifier(CppParser.NestedNameSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitLambdaExpression(CppParser.LambdaExpressionContext ctx) {
            if (analysisVisit) {
                visit(ctx.lambdaIntroducer());
                return "";
            }
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLambdaIntroducer(CppParser.LambdaIntroducerContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLambdaCapture(CppParser.LambdaCaptureContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitCaptureDefault(CppParser.CaptureDefaultContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitCaptureList(CppParser.CaptureListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitCapture(CppParser.CaptureContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitSimpleCapture(CppParser.SimpleCaptureContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitInitcapture(CppParser.InitcaptureContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLambdaDeclarator(CppParser.LambdaDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitPostfixExpression(CppParser.PostfixExpressionContext ctx) {
            //postfixExpression:
            //	primaryExpression
            //	| postfixExpression LeftBracket (expression | bracedInitList) RightBracket
            //	| postfixExpression LeftParen expressionList? RightParen
            //	| (simpleTypeSpecifier | typeNameSpecifier) (
            //		LeftParen expressionList? RightParen
            //		| bracedInitList
            //	)
            //	| postfixExpression (Dot | Arrow) (
            //		Template? idExpression
            //		| pseudoDestructorName
            //	)
            //	| postfixExpression (PlusPlus | MinusMinus)
            //	| (
            //		Dynamic_cast
            //		| Static_cast
            //		| Reinterpret_cast
            //		| Const_cast
            //	) Less theTypeId Greater LeftParen expression RightParen
            //	| typeIdOfTheTypeId LeftParen (expression | theTypeId) RightParen;
            if (analysisVisit && (ctx.Dot() != null || ctx.Arrow() != null)) {
                List<String> idList = new ArrayList<>();
                CppParser.PostfixExpressionContext posCtx = ctx;
                while (posCtx.postfixExpression().Dot() != null || posCtx.postfixExpression().Arrow() != null) {
                    posCtx = posCtx.postfixExpression();
                }
                String var = getOriginalCodeText(posCtx.postfixExpression());
                if (var.equals("this"))
                    useList.add(getOriginalCodeText(posCtx));
                else
                    useList.add(var);
                return "";
            }
            else if(analysisVisit && ctx.postfixExpression()!=null && ctx.LeftParen()!=null)
            {
                //	| postfixExpression LeftParen expressionList? RightParen
                // 可能是函数调用，或者构造匿名对象，或者是构造函数调用

                if(ctx.postfixExpression().primaryExpression()==null)
                {
                    // 可能是某个对象的函数， var->func();
                    visit(ctx.postfixExpression());
                }


                if(ctx.expressionList()!=null)
                    visit(ctx.expressionList());
                return "";
            }
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTypeIdOfTheTypeId(CppParser.TypeIdOfTheTypeIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitExpressionList(CppParser.ExpressionListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitPseudoDestructorName(CppParser.PseudoDestructorNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitUnaryExpression(CppParser.UnaryExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitUnaryOperator(CppParser.UnaryOperatorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNewExpression(CppParser.NewExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNewPlacement(CppParser.NewPlacementContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNewTypeId(CppParser.NewTypeIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNewDeclarator(CppParser.NewDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNoPointerNewDeclarator(CppParser.NoPointerNewDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNewInitializer(CppParser.NewInitializerContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeleteExpression(CppParser.DeleteExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNoExceptExpression(CppParser.NoExceptExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitCastExpression(CppParser.CastExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitPointerMemberExpression(CppParser.PointerMemberExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitMultiplicativeExpression(CppParser.MultiplicativeExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAdditiveExpression(CppParser.AdditiveExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitShiftExpression(CppParser.ShiftExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitShiftOperator(CppParser.ShiftOperatorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitRelationalExpression(CppParser.RelationalExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEqualityExpression(CppParser.EqualityExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAndExpression(CppParser.AndExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitExclusiveOrExpression(CppParser.ExclusiveOrExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitInclusiveOrExpression(CppParser.InclusiveOrExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLogicalAndExpression(CppParser.LogicalAndExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLogicalOrExpression(CppParser.LogicalOrExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitConditionalExpression(CppParser.ConditionalExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAssignmentExpression(CppParser.AssignmentExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAssignmentOperator(CppParser.AssignmentOperatorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitExpression(CppParser.ExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitConstantExpression(CppParser.ConstantExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitStatement(CppParser.StatementContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitLabeledStatement(CppParser.LabeledStatementContext ctx) {
            if (ctx.Case() != null) {
                PDNode caseNode;
                if (iteration == 1) {
                    caseNode = new PDNode();
                    caseNode.setLineOfCode(ctx.getStart().getLine());
                    caseNode.setCode(getOriginalCodeText(ctx));
                    ddg.addVertex(caseNode);
                    pdNodes.put(ctx, caseNode);
                } else
                    caseNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(caseNode, ctx.constantExpression());
            }
            return visit(ctx.statement());
        }

        @Override
        public String visitExpressionStatement(CppParser.ExpressionStatementContext ctx) {
            //expressionStatement: expression? Semi;
            if (ctx.expression() != null) {
                if (analysisVisit)
                    return visit(ctx.expression());

                PDNode expNode;
                if (iteration == 1) {
                    expNode = new PDNode();
                    expNode.setLineOfCode(ctx.getStart().getLine());
                    expNode.setCode(getOriginalCodeText(ctx));
                    ddg.addVertex(expNode);
                    pdNodes.put(ctx, expNode);
                } else
                    expNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(expNode, ctx.expression());
            }
            return "";
        }

        @Override
        public String visitCompoundStatement(CppParser.CompoundStatementContext ctx) {
            //compoundStatement: LeftBrace statementSeq? RightBrace;
            if (ctx.statementSeq() != null) {
                int entrySize = localVars.size();
                visit(ctx.statementSeq());
                if (localVars.size() > entrySize)
                    localVars.subList(entrySize, localVars.size()).clear();
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitStatementSeq(CppParser.StatementSeqContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitSelectionStatement(CppParser.SelectionStatementContext ctx) {
            //selectionStatement:
            //	If LeftParen condition RightParen statement (Else statement)?
            //	| Switch LeftParen condition RightParen statement;
            PDNode selectNode;
            if (iteration == 1) {
                selectNode = new PDNode();
                selectNode.setLineOfCode(ctx.getStart().getLine());
                if (ctx.If() != null)
                    selectNode.setCode("if( " + getOriginalCodeText(ctx.condition()) + " )");
                else
                    selectNode.setCode("switch " + getOriginalCodeText(ctx.condition()));
                ddg.addVertex(selectNode);
                pdNodes.put(ctx, selectNode);
            } else
                selectNode = (PDNode) pdNodes.get(ctx);
            analyseDefUse(selectNode, ctx.condition());
            visit(ctx.statement(0));
            if (ctx.statement(1) != null)
                visit(ctx.statement(1));
            return "";
        }

        @Override
        public String visitCondition(CppParser.ConditionContext ctx) {
            //condition:
            //	expression
            //	| attributeSpecifierSeq? declSpecifierSeq declarator (
            //		Assign initializerClause
            //		| bracedInitList
            //	);
            return visitChildren(ctx);
        }

        @Override
        public String visitIterationStatement(CppParser.IterationStatementContext ctx) {
            //iterationStatement:
            //	While LeftParen condition RightParen statement
            //	| Do statement While LeftParen expression RightParen Semi
            //	| For LeftParen (
            //		forInitStatement condition? Semi expression?
            //		| forRangeDeclaration Colon forRangeInitializer
            //	) RightParen statement;
            if (ctx.Do() != null)// do Statement
            {
                PDNode doNode;
                if (iteration == 1) {
                    doNode = new PDNode();
                    doNode.setLineOfCode(ctx.getStart().getLine());
                    doNode.setCode("while (" + getOriginalCodeText(ctx.expression()) + ")");
                    ddg.addVertex(doNode);
                    pdNodes.put(ctx, doNode);
                } else
                    doNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(doNode, ctx.expression());
                int entrySize = localVars.size();
                visit(ctx.statement());
                if (localVars.size() > entrySize)
                    localVars.subList(entrySize, localVars.size()).clear();
            } else if (ctx.While() != null)// while Statement
            {
                PDNode whileNode;
                if (iteration == 1) {
                    whileNode = new PDNode();
                    whileNode.setLineOfCode(ctx.getStart().getLine());
                    whileNode.setCode("while (" + getOriginalCodeText(ctx.condition()) + ")");
                    ddg.addVertex(whileNode);
                    pdNodes.put(ctx, whileNode);
                } else
                    whileNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(whileNode, ctx.condition());
                int entrySize = localVars.size();
                visit(ctx.statement());
                if (localVars.size() > entrySize)
                    localVars.subList(entrySize, localVars.size()).clear();

            } else// for Statement
            {
                //	| For LeftParen (
                //		forInitStatement condition? Semi expression?
                //		| forRangeDeclaration Colon forRangeInitializer
                //	) RightParen statement;
                if (ctx.forRangeDeclaration() != null) {
                    PDNode forRangeNode;
                    if (iteration == 1) {
                        forRangeNode = new PDNode();
                        forRangeNode.setLineOfCode(ctx.getStart().getLine());
                        forRangeNode.setCode("For (" + getOriginalCodeText(ctx.forRangeDeclaration()) + ":" + getOriginalCodeText(ctx.forRangeInitializer()) + ")");
                        ddg.addVertex(forRangeNode);
                        pdNodes.put(ctx.forRangeDeclaration(), forRangeNode);
                    } else
                        forRangeNode = (PDNode) pdNodes.get(ctx.forRangeDeclaration());
                    int entrySize = localVars.size();
                    analyseDefUse(forRangeNode, ctx.forRangeDeclaration());
                    analyseDefUse(forRangeNode, ctx.forRangeInitializer());
                    visit(ctx.statement());
                    if (localVars.size() > entrySize)
                        localVars.subList(entrySize, localVars.size()).clear();
                } else {
                    //		forInitStatement condition? Semi expression?
                    int entrySize = localVars.size();

                    // for init
                    PDNode initNode;
                    if (iteration == 1) {
                        initNode = new PDNode();
                        initNode.setLineOfCode(ctx.forInitStatement().getStart().getLine());
                        initNode.setCode(getOriginalCodeText(ctx.forInitStatement()));
                        ddg.addVertex(initNode);
                        pdNodes.put(ctx.forInitStatement(), initNode);
                    } else
                        initNode = (PDNode) pdNodes.get(ctx.forInitStatement());
                    analyseDefUse(initNode, ctx.forInitStatement());

                    // for condition
                    if (ctx.condition() != null) {
                        PDNode conNode;
                        if (iteration == 1) {
                            conNode = new PDNode();
                            conNode.setLineOfCode(ctx.condition().getStart().getLine());
                            conNode.setCode("for (" + getOriginalCodeText(ctx.condition()) + ")");
                            ddg.addVertex(conNode);
                            pdNodes.put(ctx.condition(), conNode);
                        } else
                            conNode = (PDNode) pdNodes.get(ctx.condition());
                        analyseDefUse(conNode, ctx.condition());
                    }

                    // for expression
                    if (ctx.expression() != null) {
                        PDNode expNode;
                        if (iteration == 1) {
                            expNode = new PDNode();
                            expNode.setLineOfCode(ctx.expression().getStart().getLine());
                            expNode.setCode(getOriginalCodeText(ctx.expression()));
                            ddg.addVertex(expNode);
                            pdNodes.put(ctx.expression(), expNode);
                        } else
                            expNode = (PDNode) pdNodes.get(ctx.expression());
                        analyseDefUse(expNode, ctx.expression());
                    }

                    visit(ctx.statement());
                    if (localVars.size() > entrySize)
                        localVars.subList(entrySize, localVars.size()).clear();
                }
            }
            return "";
        }

        @Override
        public String visitForInitStatement(CppParser.ForInitStatementContext ctx) {
            //forInitStatement: expressionStatement | simpleDeclaration;
            return visitChildren(ctx);
        }

        @Override
        public String visitForRangeDeclaration(CppParser.ForRangeDeclarationContext ctx) {
            //forRangeDeclaration:
            //	attributeSpecifierSeq? declSpecifierSeq declarator;
            parseDeclSpecifierSeq(ctx.declSpecifierSeq());
            String curSpec = specifier;
            String curType = type;

            parseDeclarator(ctx.declarator());
            curType += pointOp;
            String curName = varName;

            localVars.add(new CppField(curSpec, curType, curName));
            defList.add(varName);
            return "";
        }

        @Override
        public String visitForRangeInitializer(CppParser.ForRangeInitializerContext ctx) {
            //forRangeInitializer: expression | bracedInitList;
            return visitChildren(ctx);
        }

        @Override
        public String visitJumpStatement(CppParser.JumpStatementContext ctx) {
            //jumpStatement:
            //	(
            //		Break
            //		| Continue
            //		| Return (expression | bracedInitList)?
            //		| Goto Identifier
            //	) Semi;
            if (ctx.Return() != null && ctx.children.size() > 1) {
                PDNode returnNode;
                if (iteration == 1) {
                    returnNode = new PDNode();
                    returnNode.setLineOfCode(ctx.getStart().getLine());
                    returnNode.setCode(getOriginalCodeText(ctx));
                    ddg.addVertex(returnNode);
                    pdNodes.put(ctx, returnNode);
                } else
                    returnNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(returnNode, ctx.getChild(1));
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeclarationStatement(CppParser.DeclarationStatementContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeclarationseq(CppParser.DeclarationseqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeclaration(CppParser.DeclarationContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitBlockDeclaration(CppParser.BlockDeclarationContext ctx) {
            if (ctx.simpleDeclaration() != null)
                return visit(ctx.simpleDeclaration());
            if (ctx.staticAssertDeclaration() != null) {
                //staticAssertDeclaration:
                //	Static_assert LeftParen constantExpression Comma StringLiteral RightParen Semi;
                PDNode assertNode;
                if (iteration == 1) {
                    assertNode = new PDNode();
                    assertNode.setLineOfCode(ctx.getStart().getLine());
                    assertNode.setCode(getOriginalCodeText(ctx));
                    ddg.addVertex(assertNode);
                    pdNodes.put(ctx, assertNode);
                } else
                    assertNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(assertNode, ctx.staticAssertDeclaration().constantExpression());
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAliasDeclaration(CppParser.AliasDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitSimpleDeclaration(CppParser.SimpleDeclarationContext ctx) {
            //simpleDeclaration:
            //	declSpecifierSeq? initDeclaratorList? Semi
            //	| attributeSpecifierSeq declSpecifierSeq? initDeclaratorList Semi;
            if (ctx.declSpecifierSeq() != null) {
                if (ctx.initDeclaratorList() == null) {
                    // 可能类定义
                    visit(ctx.declSpecifierSeq());
                    return "";
                }
                // 变量或函数声明
                // 还有可能是函数调用(是有用命名空间指定的函数调用，如std::min())，这个和变量声明无法区分
                //initDeclaratorList: initDeclarator (Comma initDeclarator)*;
                //
                //initDeclarator: declarator initializer?;
                parseDeclSpecifierSeq(ctx.declSpecifierSeq());
                String spec = specifier;
                String curType = type; // 可能不是类型，是函数调用的命名空间声明,也有可能是完整的函数调用名

                for (int i = 0; i < ctx.initDeclaratorList().initDeclarator().size(); ++i) {
                    CppParser.InitDeclaratorContext initCtx = ctx.initDeclaratorList().initDeclarator(i);
                    //initDeclarator: declarator initializer?;
                    parseDeclarator(initCtx.declarator());
                    if (isGlobal) {
                        // 函数调用(是有用命名空间指定的函数调用，如std::min())
                        // 目前不支持有用命名空间指定的变量
                        if (initCtx.initializer() != null) {
                            // 有调用参数
                            PDNode entryNode;
                            if (iteration == 1) {
                                entryNode = new PDNode();
                                entryNode.setLineOfCode(ctx.getStart().getLine());
                                entryNode.setCode(getOriginalCodeText(ctx));
                                ddg.addVertex(entryNode);
                                pdNodes.put(ctx, entryNode);
                            } else
                                entryNode = (PDNode) pdNodes.get(ctx);
                            analyseDefUse(entryNode, initCtx.initializer());
                        } else if (initCtx.declarator().pointerDeclarator().noPointerDeclarator().parametersAndQualifiers() != null) {
                            // 有调用参数
                            PDNode entryNode;
                            if (iteration == 1) {
                                entryNode = new PDNode();
                                entryNode.setLineOfCode(ctx.getStart().getLine());
                                entryNode.setCode(getOriginalCodeText(ctx));
                                ddg.addVertex(entryNode);
                                pdNodes.put(ctx, entryNode);
                            } else
                                entryNode = (PDNode) pdNodes.get(ctx);
                            analyseDefUse(entryNode, initCtx.declarator().pointerDeclarator().noPointerDeclarator().parametersAndQualifiers());
                        }
                        return "";
                    } else if (isOnlyPointerDecl) {
                        // 单一参数的函数调用
                        PDNode entryNode;
                        if (iteration == 1) {
                            entryNode = new PDNode();
                            entryNode.setLineOfCode(ctx.getStart().getLine());
                            entryNode.setCode(getOriginalCodeText(ctx));
                            ddg.addVertex(entryNode);
                            pdNodes.put(ctx, entryNode);
                        } else
                            entryNode = (PDNode) pdNodes.get(ctx);
                        analyseDefUse(entryNode, initCtx.declarator().pointerDeclarator().noPointerDeclarator().pointerDeclarator().noPointerDeclarator().declaratorid());
                        return "";
                    } else if (isHasParameters) {
                        // 函数声明或者是有参构造，难以区分，暂时不支持区分
                    } else {
                        String tmpType = curType + pointOp;
                        String tmpName = varName;
                        // 变量声明
                        if (methodDefInfo != null) {
                            PDNode entryNode;
                            if (iteration == 1 && i == 0) {
                                entryNode = new PDNode();
                                entryNode.setLineOfCode(ctx.getStart().getLine());
                                entryNode.setCode(getOriginalCodeText(ctx));
                                ddg.addVertex(entryNode);
                                pdNodes.put(ctx, entryNode);
                            } else
                                entryNode = (PDNode) pdNodes.get(ctx);
                            localVars.add(new CppField(spec, tmpType, tmpName));
                            defList.add(tmpName);
                            analyseDefUse(entryNode, initCtx.initializer());
                        } else {
                            currentScope.vars.add(new CppField(spec, tmpType, tmpName));
                        }
                    }

                }

            } else if (ctx.initDeclaratorList() != null && methodDefInfo != null) {
                //变量赋值或者函数调用
                //initDeclaratorList: initDeclarator (Comma initDeclarator)*;
                PDNode entryNode;
                if (iteration == 1) {
                    entryNode = new PDNode();
                    entryNode.setLineOfCode(ctx.getStart().getLine());
                    entryNode.setCode(getOriginalCodeText(ctx));
                    ddg.addVertex(entryNode);
                    pdNodes.put(ctx, entryNode);
                } else
                    entryNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(entryNode, ctx.initDeclaratorList());
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitStaticAssertDeclaration(CppParser.StaticAssertDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEmptyDeclaration(CppParser.EmptyDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAttributeDeclaration(CppParser.AttributeDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeclSpecifier(CppParser.DeclSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitStorageClassSpecifier(CppParser.StorageClassSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitFunctionSpecifier(CppParser.FunctionSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTypedefName(CppParser.TypedefNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTypeSpecifier(CppParser.TypeSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTrailingTypeSpecifier(CppParser.TrailingTypeSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTypeSpecifierSeq(CppParser.TypeSpecifierSeqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTrailingTypeSpecifierSeq(CppParser.TrailingTypeSpecifierSeqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitSimpleTypeLengthModifier(CppParser.SimpleTypeLengthModifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitSimpleTypeSignednessModifier(CppParser.SimpleTypeSignednessModifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitSimpleTypeSpecifier(CppParser.SimpleTypeSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTheTypeName(CppParser.TheTypeNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDecltypeSpecifier(CppParser.DecltypeSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitElaboratedTypeSpecifier(CppParser.ElaboratedTypeSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumName(CppParser.EnumNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumSpecifier(CppParser.EnumSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumHead(CppParser.EnumHeadContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitOpaqueEnumDeclaration(CppParser.OpaqueEnumDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumkey(CppParser.EnumkeyContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumbase(CppParser.EnumbaseContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumeratorList(CppParser.EnumeratorListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumeratorDefinition(CppParser.EnumeratorDefinitionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitEnumerator(CppParser.EnumeratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNamespaceName(CppParser.NamespaceNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitOriginalNamespaceName(CppParser.OriginalNamespaceNameContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx) {
            if (ctx.Identifier() != null)
                namespaces.push(namespaces.peek() + "::" + ctx.Identifier().getText());
            else if (ctx.originalNamespaceName() != null)
                namespaces.push(namespaces.peek() + "::" + getOriginalCodeText(ctx.originalNamespaceName()));
            else
                namespaces.push(namespaces.peek() + "::" + "");
            Scope newScope = new Scope();
            newScope.parent = currentScope;
            currentScope = newScope;
            visit(ctx.declarationseq());
            currentScope = newScope.parent;
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNamespaceAlias(CppParser.NamespaceAliasContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitQualifiednamespacespecifier(CppParser.QualifiednamespacespecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitUsingDeclaration(CppParser.UsingDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitUsingDirective(CppParser.UsingDirectiveContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAsmDefinition(CppParser.AsmDefinitionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLinkageSpecification(CppParser.LinkageSpecificationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAttributeSpecifierSeq(CppParser.AttributeSpecifierSeqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAttributeSpecifier(CppParser.AttributeSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAlignmentspecifier(CppParser.AlignmentspecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAttributeList(CppParser.AttributeListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAttribute(CppParser.AttributeContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAttributeNamespace(CppParser.AttributeNamespaceContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAttributeArgumentClause(CppParser.AttributeArgumentClauseContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBalancedTokenSeq(CppParser.BalancedTokenSeqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBalancedtoken(CppParser.BalancedtokenContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitInitDeclaratorList(CppParser.InitDeclaratorListContext ctx) {
            if (analysisVisit) {
                //变量赋值或者函数调用
                //initDeclaratorList: initDeclarator (Comma initDeclarator)*;
                for (CppParser.InitDeclaratorContext initCtx : ctx.initDeclarator()) {
                    //initDeclarator: declarator initializer?;
                    parseDeclarator(initCtx.declarator());
                    if (initCtx.initializer() != null) {
                        // 函数调用或者有进行赋值操作的变量变量（ var=1 ）
                        //initializer:
                        //	braceOrEqualInitializer
                        //	| LeftParen expressionList RightParen;
                        if (initCtx.initializer().expressionList() != null) {
                            // 函数调用
                            visit(initCtx.initializer().expressionList());
                        } else {
                            // 有进行赋值操作的变量变量（ var=1 ）
                            defList.add(varName);
                            visit(initCtx.initializer().braceOrEqualInitializer());
                        }
                    } else {
                        // 函数调用或者没有进行赋值操作的变量变量（ var ）
                        if (isHasParameters) {
                            // 参函数调用
                            visit(initCtx.declarator().pointerDeclarator().noPointerDeclarator().parametersAndQualifiers());
                        } else {
                            // 没有进行赋值操作的变量变量（ var ）
                            useList.add(varName);
                        }
                    }
                }
                return "";
            }
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitInitDeclarator(CppParser.InitDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeclarator(CppParser.DeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitPointerDeclarator(CppParser.PointerDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNoPointerDeclarator(CppParser.NoPointerDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitParametersAndQualifiers(CppParser.ParametersAndQualifiersContext ctx) {
            //parametersAndQualifiers:
            //	LeftParen parameterDeclarationClause? RightParen cvqualifierseq? refqualifier?
            //		exceptionSpecification? attributeSpecifierSeq?;
            if (analysisVisit) {
                if (ctx.parameterDeclarationClause() != null) {
                    //parameterDeclarationClause:
                    //	parameterDeclarationList (Comma? Ellipsis)?;
                    //
                    //parameterDeclarationList:
                    //	parameterDeclaration (Comma parameterDeclaration)*;
                    //
                    //parameterDeclaration:
                    //	attributeSpecifierSeq? declSpecifierSeq (
                    //		(declarator | abstractDeclarator?) (
                    //			Assign initializerClause
                    //		)?
                    //	);
                    for (CppParser.ParameterDeclarationContext paramCtx : ctx.parameterDeclarationClause().parameterDeclarationList().parameterDeclaration()) {
                        String curName = getOriginalCodeText(paramCtx);
                        useList.add(curName);
                    }
                }
                return "";
            }
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTrailingReturnType(CppParser.TrailingReturnTypeContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitPointerOperator(CppParser.PointerOperatorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitCvqualifierseq(CppParser.CvqualifierseqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitCvQualifier(CppParser.CvQualifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitRefqualifier(CppParser.RefqualifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDeclaratorid(CppParser.DeclaratoridContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTheTypeId(CppParser.TheTypeIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAbstractDeclarator(CppParser.AbstractDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitPointerAbstractDeclarator(CppParser.PointerAbstractDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNoPointerAbstractDeclarator(CppParser.NoPointerAbstractDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAbstractPackDeclarator(CppParser.AbstractPackDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNoPointerAbstractPackDeclarator(CppParser.NoPointerAbstractPackDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitParameterDeclarationClause(CppParser.ParameterDeclarationClauseContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitParameterDeclarationList(CppParser.ParameterDeclarationListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitParameterDeclaration(CppParser.ParameterDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitFunctionDefinition(CppParser.FunctionDefinitionContext ctx) {
            //functionDefinition:
            //	attributeSpecifierSeq? declSpecifierSeq? declarator virtualSpecifierSeq? functionBody;
            PDNode entry;
            if (iteration == 1) {
                // 对于嵌套名来说只允许有一个并且是某一个类，这就要求对于非成员函数来说，这个函数的定义式
                // 一定要在对应的命名空间中，而不可以在函数所属命名空间中声明在其他命名空间中定义。暂不支持对
                // 这样子的函数进行处理
                entry = new PDNode();
                entry.setLineOfCode(ctx.getStart().getLine());
                entry.setCode(getOriginalCodeText(ctx.declSpecifierSeq()) + " "
                        + getOriginalCodeText(ctx.declarator())
                        + getOriginalCodeText(ctx.virtualSpecifierSeq()));

                parseDeclSpecifierSeq(ctx.declSpecifierSeq());
                String funcSpec = specifier;
                String funcRet = type;

                parseDeclarator(ctx.declarator());
                funcRet += pointOp;
                // 应该是类名
                String nested = nestedName;
                String funcName = varName;
                if (isGlobal) {
                    // 构造函数或析构函数
                    nested = type;
                    funcRet = "";
                }
                CppParser.ParametersAndQualifiersContext paCtx=null;
                if(ctx.declarator().trailingReturnType()!=null)
                {
                    // 函数参数列表后指出函数返回类型的方式
                    funcRet=getOriginalCodeText(ctx.declarator().trailingReturnType().trailingTypeSpecifierSeq());
                    paCtx=ctx.declarator().parametersAndQualifiers();
                }
                else
                {
                    CppParser.NoPointerDeclaratorContext npdCtx = ctx.declarator().pointerDeclarator().noPointerDeclarator();
                    paCtx=npdCtx.parametersAndQualifiers();
                }

                entry.setProperty("name", funcName);
                entry.setProperty("type", funcRet);
                entry.setProperty("class", findClass(nested));
                ddg.addVertex(entry);
                pdNodes.put(ctx, entry);

                //parametersAndQualifiers:
                //	LeftParen parameterDeclarationClause? RightParen cvqualifierseq? refqualifier?
                //		exceptionSpecification? attributeSpecifierSeq?;
                //
                //parameterDeclarationClause:
                //	parameterDeclarationList (Comma? Ellipsis)?;
                List<String> argsList = new ArrayList<>();
                List<String> argsNameList = new ArrayList<>();
                if (paCtx.parameterDeclarationClause() != null) {
                    //parameterDeclarationList:
                    //	parameterDeclaration (Comma parameterDeclaration)*;
                    for (CppParser.ParameterDeclarationContext parmCtx : paCtx
                            .parameterDeclarationClause()
                            .parameterDeclarationList()
                            .parameterDeclaration()) {
                        //parameterDeclaration:
                        //	attributeSpecifierSeq? declSpecifierSeq (
                        //		(declarator | abstractDeclarator?) (
                        //			Assign initializerClause
                        //		)?
                        //	);
                        parseDeclSpecifierSeq(parmCtx.declSpecifierSeq());
                        String argRet = type;
                        if(parmCtx.declarator()==null)
                        {
                            // 函数参数应该（void）
                            break;
                        }
                        parseDeclarator(parmCtx.declarator());
                        argRet += pointOp;
                        String argName = varName;
                        argsList.add(argRet);
                        argsNameList.add(argName);
                    }
                }

                methodParams = new CppField[argsList.size()];
                for (int i = 0; i < methodParams.length; ++i)
                    methodParams[i] = new CppField(null, argsList.get(i), argsNameList.get(i));
                entry.setProperty("params", methodParams);
                //
                // Add initial DEF info: method entry nodes define the input-parameters
                for (String pid : argsNameList)
                    changed |= entry.addDEF(pid);
            } else {
                entry = (PDNode) pdNodes.get(ctx);
                methodParams = (CppField[]) entry.getProperty("params");
            }
            String className;
            if (activeClasses.isEmpty()) {
                CppClass cls = (CppClass) entry.getProperty("class");
                if (cls != null)
                    className = cls.NAME;
                else
                    className = null;
            } else
                className = activeClasses.peek().NAME;
            methodDefInfo = findDefInfo((String) entry.getProperty("name"),
                    (String) entry.getProperty("type"), methodParams, className);

            if (methodDefInfo == null) {
                Logger.error("Method NOT FOUND!");
                Logger.error("NAME = " + (String) entry.getProperty("name"));
                if(className!=null)
                    Logger.error("CLASS = " + className);
                Logger.error("TYPE = " + (String) entry.getProperty("type"));
                Logger.error("PARAMS = " + Arrays.toString(methodParams));
                List list = methodDEFs.get((String) entry.getProperty("name"));
                if (list != null)
                    for (int i = 0; i < list.size(); ++i)
                        Logger.error(list.get(i).toString());
            }
            // 表示这个函数是否某个类的定义式的话
            boolean isInClass = false;
            if (entry.getProperty("class") != null) {
                isInClass = true;
                activeClasses.push((CppClass) entry.getProperty("class"));
            }
            localVars.clear();
            visit(ctx.functionBody());
            localVars.clear();
            methodParams = new CppField[0];
            if (isInClass)
                activeClasses.pop();
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitFunctionBody(CppParser.FunctionBodyContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitInitializer(CppParser.InitializerContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBraceOrEqualInitializer(CppParser.BraceOrEqualInitializerContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitInitializerClause(CppParser.InitializerClauseContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitInitializerList(CppParser.InitializerListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBracedInitList(CppParser.BracedInitListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitClassName(CppParser.ClassNameContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitClassSpecifier(CppParser.ClassSpecifierContext ctx) {
            //classSpecifier:
            //	classHead LeftBrace memberSpecification? RightBrace;
            //
            //classHead:
            //	classKey attributeSpecifierSeq? (
            //		classHeadName classVirtSpecifier?
            //	)? baseClause?
            //	| Union attributeSpecifierSeq? (
            //		classHeadName classVirtSpecifier?
            //	)?;
            String className = getOriginalCodeText(ctx.classHead().classHeadName());
            CppClass cls = findClass(className);
            if (cls != null) {
                activeClasses.push(cls);
                if (ctx.memberSpecification() != null)
                    visit(ctx.memberSpecification());
                activeClasses.pop();
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitClassHead(CppParser.ClassHeadContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitClassHeadName(CppParser.ClassHeadNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitClassVirtSpecifier(CppParser.ClassVirtSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitClassKey(CppParser.ClassKeyContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitMemberSpecification(CppParser.MemberSpecificationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitMemberdeclaration(CppParser.MemberdeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitMemberDeclaratorList(CppParser.MemberDeclaratorListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitMemberDeclarator(CppParser.MemberDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitVirtualSpecifierSeq(CppParser.VirtualSpecifierSeqContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitVirtualSpecifier(CppParser.VirtualSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitPureSpecifier(CppParser.PureSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBaseClause(CppParser.BaseClauseContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBaseSpecifierList(CppParser.BaseSpecifierListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBaseSpecifier(CppParser.BaseSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitClassOrDeclType(CppParser.ClassOrDeclTypeContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitBaseTypeSpecifier(CppParser.BaseTypeSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAccessSpecifier(CppParser.AccessSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitConversionFunctionId(CppParser.ConversionFunctionIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitConversionTypeId(CppParser.ConversionTypeIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitConversionDeclarator(CppParser.ConversionDeclaratorContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitConstructorInitializer(CppParser.ConstructorInitializerContext ctx) {
            //constructorInitializer: Colon memInitializerList;
            //
            //memInitializerList:
            //	memInitializer Ellipsis? (Comma memInitializer Ellipsis?)*;
            //
            //memInitializer:
            //	meminitializerid (
            //		LeftParen expressionList? RightParen
            //		| bracedInitList
            //	);
            PDNode entry;
            if (iteration == 1) {
                entry = new PDNode();
                entry.setLineOfCode(ctx.getStart().getLine());
                entry.setCode(getOriginalCodeText(ctx));
                ddg.addVertex(entry);
                pdNodes.put(ctx, entry);
            } else
                entry = (PDNode) pdNodes.get(ctx);
            analyseDefUse(entry, ctx.memInitializerList());
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitMemInitializerList(CppParser.MemInitializerListContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitMemInitializer(CppParser.MemInitializerContext ctx) {
            //memInitializer:
            //	meminitializerid (
            //		LeftParen expressionList? RightParen
            //		| bracedInitList
            //	);
            //
            //meminitializerid: classOrDeclType | Identifier;
            if (analysisVisit) {
                defList.add(getOriginalCodeText(ctx.meminitializerid()));
                if (ctx.bracedInitList() != null)
                    visit(ctx.bracedInitList());
                else if (ctx.expressionList() != null)
                    visit(ctx.expressionList());
                return "";
            }
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitMeminitializerid(CppParser.MeminitializeridContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitOperatorFunctionId(CppParser.OperatorFunctionIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLiteralOperatorId(CppParser.LiteralOperatorIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTemplateDeclaration(CppParser.TemplateDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTemplateparameterList(CppParser.TemplateparameterListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTemplateParameter(CppParser.TemplateParameterContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTypeParameter(CppParser.TypeParameterContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitSimpleTemplateId(CppParser.SimpleTemplateIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTemplateId(CppParser.TemplateIdContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTemplateName(CppParser.TemplateNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTemplateArgumentList(CppParser.TemplateArgumentListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTemplateArgument(CppParser.TemplateArgumentContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTypeNameSpecifier(CppParser.TypeNameSpecifierContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitExplicitInstantiation(CppParser.ExplicitInstantiationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitExplicitSpecialization(CppParser.ExplicitSpecializationContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitTryBlock(CppParser.TryBlockContext ctx) {
            //tryBlock: Try compoundStatement handlerSeq;
            return visitChildren(ctx);
        }


        @Override
        public String visitFunctionTryBlock(CppParser.FunctionTryBlockContext ctx) {
            //functionTryBlock:
            //	Try constructorInitializer? compoundStatement handlerSeq;
            if (ctx.constructorInitializer() != null) {
                PDNode funcTryNode;
                if (iteration == 1) {
                    funcTryNode = new PDNode();
                    funcTryNode.setLineOfCode(ctx.getStart().getLine());
                    funcTryNode.setCode("try " + getOriginalCodeText(ctx.constructorInitializer()));
                    ddg.addVertex(funcTryNode);
                    pdNodes.put(ctx, funcTryNode);
                } else
                    funcTryNode = (PDNode) pdNodes.get(ctx);
                analyseDefUse(funcTryNode, ctx.constructorInitializer());
            }
            visit(ctx.compoundStatement());
            localVars.clear();
            visit(ctx.handlerSeq());
            localVars.clear();
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitHandlerSeq(CppParser.HandlerSeqContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitHandler(CppParser.HandlerContext ctx) {
            //handler:
            //	Catch LeftParen exceptionDeclaration RightParen compoundStatement;
            int entrySize = localVars.size();

            PDNode catchNode;
            if (iteration == 1) {
                catchNode = new PDNode();
                catchNode.setLineOfCode(ctx.getStart().getLine());
                catchNode.setCode("catch(" + getOriginalCodeText(ctx.exceptionDeclaration()) + ")");
                ddg.addVertex(catchNode);
                pdNodes.put(ctx, catchNode);
            } else
                catchNode = (PDNode) pdNodes.get(ctx);
            analyseDefUse(catchNode, ctx.exceptionDeclaration());
            visit(ctx.compoundStatement());

            if (localVars.size() > entrySize)
                localVars.subList(entrySize, localVars.size()).clear();
            return "";
        }

        @Override
        public String visitExceptionDeclaration(CppParser.ExceptionDeclarationContext ctx) {
            //exceptionDeclaration:
            //	attributeSpecifierSeq? typeSpecifierSeq (
            //		declarator
            //		| abstractDeclarator
            //	)?
            //	| Ellipsis;
            if(ctx.Ellipsis()!=null)
                return "";
            if (ctx.declarator() != null) {
                // 变量名在ctx.declarator()中
                // ctx.typeSpecifierSeq()只有变量类型
                String excepType = getOriginalCodeText(ctx.typeSpecifierSeq());
                parseDeclarator(ctx.declarator());
                excepType += pointOp;
                String excepName = varName;

                localVars.add(new CppField(null, excepType, excepName));
                defList.add(excepName);
            } else // 忽略ctx.abstractDeclarator()
            {
                // 变量类型和变量名都在ctx.typeSpecifierSeq()中
                // typeSpecifierSeq: typeSpecifier+ attributeSpecifierSeq?;
                // 其中最后一个typeSpecifier是变量名
                String excepType = "";
                int size = ctx.typeSpecifierSeq().typeSpecifier().size();
                for (int i = 0; i < size - 1; ++i) {
                    excepType += getOriginalCodeText(ctx.typeSpecifierSeq().typeSpecifier(i));
                }
                String excepName = getOriginalCodeText(ctx.typeSpecifierSeq().typeSpecifier(size - 1));
                localVars.add(new CppField(null, excepType, excepName));
                defList.add(excepName);
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitThrowExpression(CppParser.ThrowExpressionContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitExceptionSpecification(CppParser.ExceptionSpecificationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitDynamicExceptionSpecification(CppParser.DynamicExceptionSpecificationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTypeIdList(CppParser.TypeIdListContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitNoeExceptSpecification(CppParser.NoeExceptSpecificationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitTheOperator(CppParser.TheOperatorContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLiteral(CppParser.LiteralContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * 用于解析declSpecifierSeq从中获取到specifier和typename
         */
        private void parseDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx) {
            //declSpecifierSeq: declSpecifier+? attributeSpecifierSeq?;
            specifier = "";
            type = "";
            if (ctx != null && ctx.declSpecifier() != null) {
                for (CppParser.DeclSpecifierContext decCtx : ctx.declSpecifier()) {
                    //declSpecifier:
                    //	storageClassSpecifier
                    //	| typeSpecifier
                    //	| functionSpecifier
                    //	| Friend
                    //	| Typedef
                    //	| Constexpr;
                    if (decCtx.typeSpecifier() != null)
                        type += getOriginalCodeText(decCtx) + " ";
                    else
                        specifier += getOriginalCodeText(decCtx) + " ";
                }
                if (!type.equals(""))
                    type = type.substring(0, type.length() - 1);
            }
        }

        /**
         * 用于解析declarator从中获取到pointOp、nestedName和varName
         */
        private void parseDeclarator(CppParser.DeclaratorContext ctx) {
            //declarator:
            //	pointerDeclarator
            //	| noPointerDeclarator parametersAndQualifiers trailingReturnType;
            //
            //pointerDeclarator: (pointerOperator Const?)* noPointerDeclarator;
            clearAllFlags();
            pointOp = "";
            CppParser.DeclaratoridContext decIdCtx=null;
            if(ctx.trailingReturnType()!=null)
            {
                decIdCtx=ctx.noPointerDeclarator().declaratorid();
            }
            else
            {
                CppParser.PointerDeclaratorContext pdCtx = ctx.pointerDeclarator();
                if (pdCtx.pointerOperator() != null) {
                    for (int i = 0; i < pdCtx.pointerOperator().size(); ++i) {
                        pointOp += getOriginalCodeText(pdCtx.pointerOperator(i));
                        if (pdCtx.Const(i) != null)
                            pointOp += pdCtx.Const(i).getText();
                    }
                }

                //noPointerDeclarator:
                //	declaratorid attributeSpecifierSeq?
                //	| noPointerDeclarator (
                //		parametersAndQualifiers
                //		| LeftBracket constantExpression? RightBracket attributeSpecifierSeq?
                //	)
                //	| LeftParen pointerDeclarator RightParen;
                if (pdCtx.noPointerDeclarator().LeftParen() != null) {
                    //	| LeftParen pointerDeclarator RightParen;
                    nestedName = "";
                    varName = "";
                    isOnlyPointerDecl=true;
                    return;
                }

                if (pdCtx.noPointerDeclarator().parametersAndQualifiers() != null || pdCtx.noPointerDeclarator().LeftParen() != null) {
                    isHasParameters=true;
                    if(pdCtx.noPointerDeclarator().parametersAndQualifiers() != null && pdCtx.noPointerDeclarator().noPointerDeclarator().LeftParen()!=null)
                    {
                        // 可能函数签名
                        CppParser.PointerDeclaratorContext tmpPdCtx=pdCtx.noPointerDeclarator().noPointerDeclarator().pointerDeclarator();
                        decIdCtx=tmpPdCtx.noPointerDeclarator().declaratorid();
                        if (tmpPdCtx.pointerOperator() != null) {
                            for (int i = 0; i < tmpPdCtx.pointerOperator().size(); ++i) {
                                pointOp += getOriginalCodeText(tmpPdCtx.pointerOperator(i));
                                if (tmpPdCtx.Const(i) != null)
                                    pointOp += tmpPdCtx.Const(i).getText();
                            }
                        }
                        pointOp+=getOriginalCodeText(pdCtx.noPointerDeclarator().parametersAndQualifiers());

                    }
                    else
                    {
                        CppParser.NoPointerDeclaratorContext npdCtx = pdCtx.noPointerDeclarator().noPointerDeclarator();
                        while (npdCtx.LeftParen() != null) {
                            //noPointerDeclarator:
                            //	| LeftParen pointerDeclarator RightParen;
                            npdCtx = npdCtx.pointerDeclarator().noPointerDeclarator();
                        }
                        decIdCtx = npdCtx.declaratorid();
                    }
                }
                else if(pdCtx.noPointerDeclarator().LeftBracket()!=null)
                {
                    CppParser.NoPointerDeclaratorContext npdCtx = pdCtx.noPointerDeclarator().noPointerDeclarator();
                    while (npdCtx.LeftBracket() != null) {
                        //noPointerDeclarator:
                        //  noPointerDeclarator
                        //		 LeftBracket constantExpression? RightBracket attributeSpecifierSeq?
                        npdCtx = npdCtx.noPointerDeclarator();
                    }
                    decIdCtx = npdCtx.declaratorid();
                }else
                    decIdCtx = pdCtx.noPointerDeclarator().declaratorid();
            }



            if(decIdCtx==null)
            {
                Logger.error("decIdCtx==null");
                Logger.error(currentFile+" line : "+ctx.getStart().getLine());
                Logger.error("code : "+getOriginalCodeText(ctx));
            }

            //declaratorid: Ellipsis? idExpression;
            //
            //idExpression: unqualifiedId | qualifiedId;
            nestedName = "";
            varName = "";
            if (decIdCtx.idExpression().qualifiedId() != null) {
                // 存在嵌套名，当前只允许有一个
                varName = getOriginalCodeText(decIdCtx.idExpression().qualifiedId().unqualifiedId());
                nestedName = getOriginalCodeText(decIdCtx.idExpression().qualifiedId().nestedNameSpecifier().theTypeName());
                if (nestedName == "")
                    isGlobal = true;
            } else
                varName = getOriginalCodeText(decIdCtx.idExpression().unqualifiedId());
        }

        private String getOriginalCodeText(ParserRuleContext ctx) {
            if (ctx == null)
                return "";
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
        }

    }

    /**
     * A simple structure to store DEF information about a Cpp Function.
     */
    static class MethodDefInfo {
        public final String NAME;
        public final String NAMESPACE;
        public final String RET_TYPE;
        // 如果这个function不是某个class中的话，CLASS_NAME为“”
        public final String CLASS_NAME;
        public final String[] PARAM_TYPES;

        // DEF Info
        private boolean stateDEF;
        private boolean[] argDEFs;
        private List<String> fieldDEFs;

        public MethodDefInfo(String ret, String name, String namespace, String cls, String[] args) {
            NAME = name;
            NAMESPACE = namespace;
            CLASS_NAME = (cls == null ? "" : cls);
            RET_TYPE = ret;
            PARAM_TYPES = (args == null ? new String[0] : args);

            fieldDEFs = new ArrayList<>();
            stateDEF = guessByTypeOrName();
            argDEFs = new boolean[PARAM_TYPES.length];  // all initialized to 'false'
        }

        private boolean guessByTypeOrName() {
            // First check if this method is a constructor ...
            if (RET_TYPE == null)
                return true;
//            // If not, then try to guess by method-name ...
//            String[] prefixes = { "set", "put", "add", "insert", "push", "append" };
//            for (String pre: prefixes)
//                if (NAME.toLowerCase().startsWith(pre))
//                    return true;
            return false;
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
            return this.NAMESPACE.equals(info.NAMESPACE) && this.NAME.equals(info.NAME)
                    && this.CLASS_NAME.equals(info.CLASS_NAME)
                    && this.RET_TYPE.equals(info.RET_TYPE)
                    && Arrays.equals(this.PARAM_TYPES, info.PARAM_TYPES);
        }

        @Override
        public String toString() {
            String retType = RET_TYPE == null ? "null" : RET_TYPE;
            String args = PARAM_TYPES == null ? "null" : Arrays.toString(PARAM_TYPES);
            StringBuilder str = new StringBuilder();
            str.append("{ TYPE : \"").append(retType).append("\", ");
            str.append("NAME : \"").append(NAME).append("\", ");
            str.append("NAMESPACE : \"").append(NAMESPACE).append("\", ");
            str.append("ARGS : ").append(args).append(", ");
            str.append("CLASS : \"").append(CLASS_NAME).append("\" }");
            return str.toString();
        }
    }
}

