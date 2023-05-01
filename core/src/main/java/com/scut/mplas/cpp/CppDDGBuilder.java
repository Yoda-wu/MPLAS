package com.scut.mplas.cpp;
/*** In The Name of Allah ***/


import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import com.scut.mplas.cpp.parser.CppVisitor;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.CFPathTraversal;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.graphs.pdg.DDEdge;
import com.scut.mplas.graphs.pdg.DataDependenceGraph;
import com.scut.mplas.graphs.pdg.PDNode;
import com.scut.mplas.java.JavaField;

import ghaffarian.graphs.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import ghaffarian.nanologger.Logger;
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
    private static Map<String,List<CppMethod>> allNonClassFunctionInfos;

    private static Map<String, List<MethodDefInfo>> methodDEFs;

    public static DataDependenceGraph[] buildForAll(File[] files) throws IOException
    {
        // Parse all Java source files
        Logger.info("Parsing all source files ... ");
        ParseTree[] parseTrees = new ParseTree[files.length];
        for (int i = 0; i < files.length; ++i)
        {
            InputStream inFile = new FileInputStream(files[i]);
            ANTLRInputStream input = new ANTLRInputStream(inFile);
            CppLexer lexer = new CppLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CppParser parser = new CppParser(tokens);
            parseTrees[i] = parser.translationUnit();
        }
        Logger.info("Done.");

        DataDependenceGraph[] ddgs=new DataDependenceGraph[files.length];
        for(int i=0;i< ddgs.length;++i)
            ddgs[i]=new DataDependenceGraph(files[i].getName());

        Map<ParserRuleContext, Object>[] pdNodes = new Map[parseTrees.length];
        for (int i = 0; i < parseTrees.length; ++i)
            pdNodes[i] = new IdentityHashMap<>();

        // Build ddg for each Cpp file
        Logger.info("\nbegin ddg build for each file ... ");
        for(int i=0;i<ddgs.length;++i)
            build(files[i],parseTrees[i],ddgs[i],pdNodes[i]);
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

    // 为每一个源代码文件都进行DDG分析
    private static void build(File file,ParseTree parseTree,
                              DataDependenceGraph ddg,
                              Map<ParserRuleContext, Object> pdNodes) throws IOException
    {
        // Extract the information of all given Cpp classes and NonClass Function
        Logger.info("\nExtracting class-infos and nonClass-Func-infos ... ");
        allClassInfos = new HashMap<>();
        allNonClassFunctionInfos =new HashMap<>();
        List<CppClass> classesList=new LinkedList<>();
        List<CppMethod> functiopnsList=new LinkedList<>();

        CppExtractor.extractInfo(file.getPath(),parseTree,classesList,functiopnsList);
        for(CppClass cls:classesList)
            allClassInfos.put(cls.NAME,cls);
        Logger.info("Done.");

        // Initialize method DEF information
        Logger.info("\nInitializing method-DEF infos ... ");
        // 增加非class函数信息
        for(CppMethod func:functiopnsList)
        {
            List<MethodDefInfo> list=methodDEFs.get(func.NAME);
            if(list==null)
            {
                list=new ArrayList<>();
                list.add(new MethodDefInfo(func.RET_TYPE,func.NAME,func.NAMESPACE,null,func.ARG_TYPES));
                methodDEFs.put(func.NAME, list);
            }
            else
                list.add(new MethodDefInfo(func.RET_TYPE,func.NAME,func.NAMESPACE,null,func.ARG_TYPES));
        }
        // 增加class 成员函数信息
        for(CppClass cls:classesList)
            for(CppMethod func:cls.getAllMethods())
            {
                List<MethodDefInfo> list=methodDEFs.get(func.NAME);
                if(list==null)
                {
                    list=new ArrayList<>();
                    list.add(new MethodDefInfo(func.RET_TYPE,func.NAME,func.NAMESPACE,cls.NAME,func.ARG_TYPES));
                    methodDEFs.put(func.NAME, list);
                }
                else
                    list.add(new MethodDefInfo(func.RET_TYPE,func.NAME,func.NAMESPACE,cls.NAME,func.ARG_TYPES));
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
    // TODO: 2023/4/23 待完成Def-Use分析
    /**
     * Visitor class which performs iterative DEF-USE analysis for all program statements.
     */
    private static class DefUseVisitor extends CppBaseVisitor<String> {
        /**
         * 表示某个命名空间中的全局变量，如果要支持在一个命名空间中使用其他命名空间的变量的话
         * 需要设置出一个命名空间的表来保存这些命名空间信息，暂时不支持这种方式
         * 目前仅考虑，一个命名空间只有唯一一个定义，然后一个命名空间中的全局变量只在这个命名空间中使用
         */
        private class Scope{
            public Scope parent;
            public List<CppField> vars;
            public Scope()
            {
                parent=null;
                vars=new ArrayList<>();
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
            namespaces=new ArrayDeque<>();
            // 全局命名空间
            namespaces.push("");
            currentScope=new Scope();
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
            for (CppField lv: localVars)
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
            for (String def: defList) {
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
                }
                else
                    Logger.debug(def + " is not defined!");
            }
            Logger.debug("Changed = " + changed);
            Logger.debug("DEFs = " + Arrays.toString(node.getAllDEFs()));
            //
            for (String use: useList) {
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
            for (String flow: selfFlowList) {
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
            for (CppField local: localVars)
                if (local.NAME.equals(id))
                    return LOCAL;
            // 函数参数
            for (int i = 0; i < methodParams.length; ++i)
                if (methodParams[i].NAME.equals(id))
                    return i;
            // 当前所在命名空间中的全局变量
            for(CppField global:currentScope.vars)
                if(global.NAME.equals(id))
                    return GLOBAL;
            if (id.startsWith("this->"))
                id = id.substring(6);
            for (CppField field: activeClasses.peek().getAllFields())
                if (field.NAME.equals(id))
                    return FIELD;
            for (CppClass cls: activeClasses)
                for (CppField field: cls.getAllFields())
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
                for (CppField param: methodParams)
                    if (param.NAME.equals(id))
                        return param.TYPE;
                for (CppField local: localVars)
                    if (local.NAME.equals(id))
                        return local.TYPE;
                if (id.startsWith("this->"))
                    id = id.substring(6);
                for (CppField field: activeClasses.peek().getAllFields())
                    if (field.NAME.equals(id))
                        return field.TYPE;
                for (CppClass cls: activeClasses)
                    for (CppField field: cls.getAllFields())
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
        private CppClass findClass(String className)
        {
            for(CppClass cls:classInfos)
                if(cls.NAME.equals(className) && cls.NAMESPACE.equals(namespaces.peek()))
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
        private MethodDefInfo findDefInfo(String name, String type, CppField[] params) {
            List<MethodDefInfo> infoList = methodDEFs.get(name);
            if (infoList.size() > 1) {
                forEachInfo:
                for (MethodDefInfo info: infoList) {
                    if(!info.NAMESPACE.equals(namespaces.peek()))
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
            } else
            if (infoList.size() == 1)
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
        @Override public String visitTranslationUnit(CppParser.TranslationUnitContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPrimaryExpression(CppParser.PrimaryExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitIdExpression(CppParser.IdExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitUnqualifiedId(CppParser.UnqualifiedIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitQualifiedId(CppParser.QualifiedIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNestedNameSpecifier(CppParser.NestedNameSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLambdaExpression(CppParser.LambdaExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLambdaIntroducer(CppParser.LambdaIntroducerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLambdaCapture(CppParser.LambdaCaptureContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCaptureDefault(CppParser.CaptureDefaultContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCaptureList(CppParser.CaptureListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCapture(CppParser.CaptureContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSimpleCapture(CppParser.SimpleCaptureContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInitcapture(CppParser.InitcaptureContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLambdaDeclarator(CppParser.LambdaDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPostfixExpression(CppParser.PostfixExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTypeIdOfTheTypeId(CppParser.TypeIdOfTheTypeIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExpressionList(CppParser.ExpressionListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPseudoDestructorName(CppParser.PseudoDestructorNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitUnaryExpression(CppParser.UnaryExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitUnaryOperator(CppParser.UnaryOperatorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNewExpression(CppParser.NewExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNewPlacement(CppParser.NewPlacementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNewTypeId(CppParser.NewTypeIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNewDeclarator(CppParser.NewDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNoPointerNewDeclarator(CppParser.NoPointerNewDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNewInitializer(CppParser.NewInitializerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeleteExpression(CppParser.DeleteExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNoExceptExpression(CppParser.NoExceptExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCastExpression(CppParser.CastExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPointerMemberExpression(CppParser.PointerMemberExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMultiplicativeExpression(CppParser.MultiplicativeExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAdditiveExpression(CppParser.AdditiveExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitShiftExpression(CppParser.ShiftExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitShiftOperator(CppParser.ShiftOperatorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitRelationalExpression(CppParser.RelationalExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEqualityExpression(CppParser.EqualityExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAndExpression(CppParser.AndExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExclusiveOrExpression(CppParser.ExclusiveOrExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInclusiveOrExpression(CppParser.InclusiveOrExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLogicalAndExpression(CppParser.LogicalAndExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLogicalOrExpression(CppParser.LogicalOrExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitConditionalExpression(CppParser.ConditionalExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAssignmentExpression(CppParser.AssignmentExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAssignmentOperator(CppParser.AssignmentOperatorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExpression(CppParser.ExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitConstantExpression(CppParser.ConstantExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitStatement(CppParser.StatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLabeledStatement(CppParser.LabeledStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExpressionStatement(CppParser.ExpressionStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCompoundStatement(CppParser.CompoundStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitStatementSeq(CppParser.StatementSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSelectionStatement(CppParser.SelectionStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCondition(CppParser.ConditionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitIterationStatement(CppParser.IterationStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForInitStatement(CppParser.ForInitStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForRangeDeclaration(CppParser.ForRangeDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForRangeInitializer(CppParser.ForRangeInitializerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitJumpStatement(CppParser.JumpStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclarationStatement(CppParser.DeclarationStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclarationseq(CppParser.DeclarationseqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclaration(CppParser.DeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBlockDeclaration(CppParser.BlockDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAliasDeclaration(CppParser.AliasDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSimpleDeclaration(CppParser.SimpleDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitStaticAssertDeclaration(CppParser.StaticAssertDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEmptyDeclaration(CppParser.EmptyDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttributeDeclaration(CppParser.AttributeDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclSpecifier(CppParser.DeclSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitStorageClassSpecifier(CppParser.StorageClassSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionSpecifier(CppParser.FunctionSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTypedefName(CppParser.TypedefNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTypeSpecifier(CppParser.TypeSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTrailingTypeSpecifier(CppParser.TrailingTypeSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTypeSpecifierSeq(CppParser.TypeSpecifierSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTrailingTypeSpecifierSeq(CppParser.TrailingTypeSpecifierSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSimpleTypeLengthModifier(CppParser.SimpleTypeLengthModifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSimpleTypeSignednessModifier(CppParser.SimpleTypeSignednessModifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSimpleTypeSpecifier(CppParser.SimpleTypeSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTheTypeName(CppParser.TheTypeNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDecltypeSpecifier(CppParser.DecltypeSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitElaboratedTypeSpecifier(CppParser.ElaboratedTypeSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumName(CppParser.EnumNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumSpecifier(CppParser.EnumSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumHead(CppParser.EnumHeadContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitOpaqueEnumDeclaration(CppParser.OpaqueEnumDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumkey(CppParser.EnumkeyContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumbase(CppParser.EnumbaseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumeratorList(CppParser.EnumeratorListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumeratorDefinition(CppParser.EnumeratorDefinitionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEnumerator(CppParser.EnumeratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNamespaceName(CppParser.NamespaceNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitOriginalNamespaceName(CppParser.OriginalNamespaceNameContext ctx) { return visitChildren(ctx); }

        @Override public String visitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx) {
            if(ctx.Identifier()!=null)
                namespaces.push(namespaces.peek()+"::"+ctx.Identifier().getText());
            else if(ctx.originalNamespaceName()!=null)
                namespaces.push(namespaces.peek()+"::"+getOriginalCodeText(ctx.originalNamespaceName()));
            else
                namespaces.push(namespaces.peek()+"::"+"");
            Scope newScope=new Scope();
            newScope.parent=currentScope;
            currentScope=newScope;
            visit(ctx.declarationseq());
            currentScope=newScope.parent;
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNamespaceAlias(CppParser.NamespaceAliasContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitQualifiednamespacespecifier(CppParser.QualifiednamespacespecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitUsingDeclaration(CppParser.UsingDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitUsingDirective(CppParser.UsingDirectiveContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAsmDefinition(CppParser.AsmDefinitionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLinkageSpecification(CppParser.LinkageSpecificationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttributeSpecifierSeq(CppParser.AttributeSpecifierSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttributeSpecifier(CppParser.AttributeSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAlignmentspecifier(CppParser.AlignmentspecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttributeList(CppParser.AttributeListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttribute(CppParser.AttributeContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttributeNamespace(CppParser.AttributeNamespaceContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttributeArgumentClause(CppParser.AttributeArgumentClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBalancedTokenSeq(CppParser.BalancedTokenSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBalancedtoken(CppParser.BalancedtokenContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInitDeclaratorList(CppParser.InitDeclaratorListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInitDeclarator(CppParser.InitDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclarator(CppParser.DeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPointerDeclarator(CppParser.PointerDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNoPointerDeclarator(CppParser.NoPointerDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitParametersAndQualifiers(CppParser.ParametersAndQualifiersContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTrailingReturnType(CppParser.TrailingReturnTypeContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPointerOperator(CppParser.PointerOperatorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCvqualifierseq(CppParser.CvqualifierseqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCvQualifier(CppParser.CvQualifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitRefqualifier(CppParser.RefqualifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclaratorid(CppParser.DeclaratoridContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTheTypeId(CppParser.TheTypeIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAbstractDeclarator(CppParser.AbstractDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPointerAbstractDeclarator(CppParser.PointerAbstractDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNoPointerAbstractDeclarator(CppParser.NoPointerAbstractDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAbstractPackDeclarator(CppParser.AbstractPackDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNoPointerAbstractPackDeclarator(CppParser.NoPointerAbstractPackDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitParameterDeclarationClause(CppParser.ParameterDeclarationClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitParameterDeclarationList(CppParser.ParameterDeclarationListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitParameterDeclaration(CppParser.ParameterDeclarationContext ctx) { return visitChildren(ctx); }

        @Override public String visitFunctionDefinition(CppParser.FunctionDefinitionContext ctx) {
            //functionDefinition:
            //	attributeSpecifierSeq? declSpecifierSeq? declarator virtualSpecifierSeq? functionBody;
            PDNode entry;
            if(iteration==1)
            {
                // 对于嵌套名来说只允许有一个并且是某一个类，这就要求对于非成员函数来说，这个函数的定义式
                // 一定要在对应的命名空间中，而不可以在函数所属命名空间中声明在其他命名空间中定义。暂不支持对
                // 这样子的函数进行处理
                entry=new PDNode();
                entry.setLineOfCode(ctx.getStart().getLine());
                entry.setCode(getOriginalCodeText(ctx.declSpecifierSeq())+" "
                        +getOriginalCodeText(ctx.declarator())
                        +getOriginalCodeText(ctx.virtualSpecifierSeq()));

                parseDeclSpecifierSeq(ctx.declSpecifierSeq());
                String funcSpec=specifier;
                String funcRet=type;

                parseDeclarator(ctx.declarator());
                funcRet+=pointOp;
                // 应该是类名
                String nested=nestedName;
                String funcName=varName;

                entry.setProperty("name",funcName);
                entry.setProperty("type",funcRet);
                entry.setProperty("class",findClass(nested));
                ddg.addVertex(entry);
                pdNodes.put(ctx,entry);

                CppParser.NoPointerDeclaratorContext npdCtx=ctx.declarator().pointerDeclarator().noPointerDeclarator();
                //parametersAndQualifiers:
                //	LeftParen parameterDeclarationClause? RightParen cvqualifierseq? refqualifier?
                //		exceptionSpecification? attributeSpecifierSeq?;
                //
                //parameterDeclarationClause:
                //	parameterDeclarationList (Comma? Ellipsis)?;
                List<String> argsList=new ArrayList<>();
                List<String> argsNameList=new ArrayList<>();
                if(npdCtx.parametersAndQualifiers().parameterDeclarationClause()!=null)
                {
                    //parameterDeclarationList:
                    //	parameterDeclaration (Comma parameterDeclaration)*;
                    for(CppParser.ParameterDeclarationContext parmCtx:npdCtx.parametersAndQualifiers()
                            .parameterDeclarationClause()
                            .parameterDeclarationList()
                            .parameterDeclaration())
                    {
                        //parameterDeclaration:
                        //	attributeSpecifierSeq? declSpecifierSeq (
                        //		(declarator | abstractDeclarator?) (
                        //			Assign initializerClause
                        //		)?
                        //	);
                        parseDeclSpecifierSeq(parmCtx.declSpecifierSeq());
                        String argRet=type;

                        parseDeclarator(parmCtx.declarator());
                        argRet+=pointOp;
                        String argName=varName;
                        argsList.add(argRet);
                        argsNameList.add(argName);
                    }
                }

                methodParams=new CppField[argsList.size()];
                for(int i=0;i<methodParams.length;++i)
                    methodParams[i]=new CppField(null,argsList.get(i),argsNameList.get(i));
                entry.setProperty("params",methodParams);
                //
                // Add initial DEF info: method entry nodes define the input-parameters
                for (String pid: argsNameList)
                    changed |= entry.addDEF(pid);
            }
            else
            {
                entry=(PDNode) pdNodes.get(ctx);
                methodParams=(CppField[]) entry.getProperty("params");
            }

            methodDefInfo=findDefInfo((String)entry.getProperty("name"),
                                      (String) entry.getProperty("type"),methodParams);

            if (methodDefInfo == null) {
                Logger.error("Method NOT FOUND!");
                Logger.error("NAME = " + (String) entry.getProperty("name"));
                Logger.error("TYPE = " + (String) entry.getProperty("type"));
                Logger.error("PARAMS = " + Arrays.toString(methodParams));
                Logger.error("CLASS = " + activeClasses.peek().NAME);
                List list = methodDEFs.get((String) entry.getProperty("name"));
                for (int i = 0; i < list.size(); ++i)
                    Logger.error(list.get(i).toString());
            }
            // 表示这个函数是否某个类的定义式的话
            boolean isInClass=false;
            if(entry.getProperty("class")!=null)
            {
                isInClass=true;
                activeClasses.push((CppClass) entry.getProperty("class"));
            }
            localVars.clear();
            visit(ctx.functionBody());
            localVars.clear();
            methodParams=new CppField[0];
            if(isInClass)
                activeClasses.pop();
            return null;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionBody(CppParser.FunctionBodyContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInitializer(CppParser.InitializerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBraceOrEqualInitializer(CppParser.BraceOrEqualInitializerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInitializerClause(CppParser.InitializerClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInitializerList(CppParser.InitializerListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBracedInitList(CppParser.BracedInitListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassName(CppParser.ClassNameContext ctx) { return visitChildren(ctx); }

        @Override public String visitClassSpecifier(CppParser.ClassSpecifierContext ctx) {
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
            String className=getOriginalCodeText(ctx.classHead());
            CppClass cls=findClass(className);
            if(cls!=null)
            {
                activeClasses.push(cls);
                if(ctx.memberSpecification()!=null)
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
        @Override public String visitClassHead(CppParser.ClassHeadContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassHeadName(CppParser.ClassHeadNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassVirtSpecifier(CppParser.ClassVirtSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassKey(CppParser.ClassKeyContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMemberSpecification(CppParser.MemberSpecificationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMemberdeclaration(CppParser.MemberdeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMemberDeclaratorList(CppParser.MemberDeclaratorListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMemberDeclarator(CppParser.MemberDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitVirtualSpecifierSeq(CppParser.VirtualSpecifierSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitVirtualSpecifier(CppParser.VirtualSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPureSpecifier(CppParser.PureSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBaseClause(CppParser.BaseClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBaseSpecifierList(CppParser.BaseSpecifierListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBaseSpecifier(CppParser.BaseSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassOrDeclType(CppParser.ClassOrDeclTypeContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBaseTypeSpecifier(CppParser.BaseTypeSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAccessSpecifier(CppParser.AccessSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitConversionFunctionId(CppParser.ConversionFunctionIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitConversionTypeId(CppParser.ConversionTypeIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitConversionDeclarator(CppParser.ConversionDeclaratorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitConstructorInitializer(CppParser.ConstructorInitializerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMemInitializerList(CppParser.MemInitializerListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMemInitializer(CppParser.MemInitializerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMeminitializerid(CppParser.MeminitializeridContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitOperatorFunctionId(CppParser.OperatorFunctionIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLiteralOperatorId(CppParser.LiteralOperatorIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateDeclaration(CppParser.TemplateDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateparameterList(CppParser.TemplateparameterListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateParameter(CppParser.TemplateParameterContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTypeParameter(CppParser.TypeParameterContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSimpleTemplateId(CppParser.SimpleTemplateIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateId(CppParser.TemplateIdContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateName(CppParser.TemplateNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateArgumentList(CppParser.TemplateArgumentListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateArgument(CppParser.TemplateArgumentContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTypeNameSpecifier(CppParser.TypeNameSpecifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExplicitInstantiation(CppParser.ExplicitInstantiationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExplicitSpecialization(CppParser.ExplicitSpecializationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTryBlock(CppParser.TryBlockContext ctx) { return visitChildren(ctx); }

        @Override public String visitFunctionTryBlock(CppParser.FunctionTryBlockContext ctx) {
            //functionTryBlock:
            //	Try constructorInitializer? compoundStatement handlerSeq;
            if(ctx.constructorInitializer()!=null)
            {
                PDNode funcTryNode;
                if(iteration==1)
                {
                    funcTryNode=new PDNode();
                    funcTryNode.setLineOfCode(ctx.getStart().getLine());
                    funcTryNode.setCode("try "+getOriginalCodeText(ctx.constructorInitializer()));
                    ddg.addVertex(funcTryNode);
                    pdNodes.put(ctx,funcTryNode);
                }
                else
                    funcTryNode=(PDNode) pdNodes.get(ctx);
                analyseDefUse(funcTryNode,ctx.constructorInitializer());
            }
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitHandlerSeq(CppParser.HandlerSeqContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitHandler(CppParser.HandlerContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExceptionDeclaration(CppParser.ExceptionDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitThrowExpression(CppParser.ThrowExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExceptionSpecification(CppParser.ExceptionSpecificationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDynamicExceptionSpecification(CppParser.DynamicExceptionSpecificationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTypeIdList(CppParser.TypeIdListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNoeExceptSpecification(CppParser.NoeExceptSpecificationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTheOperator(CppParser.TheOperatorContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLiteral(CppParser.LiteralContext ctx) { return visitChildren(ctx); }

        /**
         * 用于解析declSpecifierSeq从中获取到specifier和typename
         */
        private void parseDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx)
        {
            //declSpecifierSeq: declSpecifier+? attributeSpecifierSeq?;
            specifier="";
            type="";
            if(ctx!=null && ctx.declSpecifier()!=null)
            {
                for(CppParser.DeclSpecifierContext decCtx:ctx.declSpecifier())
                {
                    //declSpecifier:
                    //	storageClassSpecifier
                    //	| typeSpecifier
                    //	| functionSpecifier
                    //	| Friend
                    //	| Typedef
                    //	| Constexpr;
                    if(decCtx.typeSpecifier()!=null)
                        type+=getOriginalCodeText(decCtx)+" ";
                    else
                        specifier+=getOriginalCodeText(decCtx)+" ";
                }
            }
        }

        /**
         * 用于解析declarator从中获取到pointOp、nestedName和varName
         */
        private void parseDeclarator(CppParser.DeclaratorContext ctx)
        {

            //declarator:
            //	pointerDeclarator
            //	| noPointerDeclarator parametersAndQualifiers trailingReturnType;
            //
            //pointerDeclarator: (pointerOperator Const?)* noPointerDeclarator;
            CppParser.PointerDeclaratorContext pdCtx=ctx.pointerDeclarator();
            pointOp="";
            if(pdCtx.pointerOperator()!=null)
            {
                for(int i=0;i<pdCtx.pointerOperator().size();++i)
                {
                    pointOp+=getOriginalCodeText(pdCtx.pointerOperator(i));
                    if(pdCtx.Const(i)!=null)
                        pointOp+=pdCtx.Const(i).getText();
                }
            }

            CppParser.DeclaratoridContext decIdCtx=pdCtx.noPointerDeclarator().noPointerDeclarator().declaratorid();
            //declaratorid: Ellipsis? idExpression;
            //
            //idExpression: unqualifiedId | qualifiedId;
            nestedName="";
            varName="";
            if(decIdCtx.idExpression().qualifiedId()!=null)
            {
                // 存在嵌套名，当前只允许有一个
                varName=getOriginalCodeText(decIdCtx.idExpression().qualifiedId().unqualifiedId());
                nestedName=getOriginalCodeText(decIdCtx.idExpression().qualifiedId().nestedNameSpecifier().theTypeName());
            }
            else
                varName=getOriginalCodeText(decIdCtx.idExpression().qualifiedId().unqualifiedId());
        }

        private String getOriginalCodeText(ParserRuleContext ctx) {
            if (ctx == null)
                return "";
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
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
            for (CppField lv: localVars)
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
            for (String def: defList) {
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
                }
                else
                    Logger.debug(def + " is not defined!");
            }
            Logger.debug("Changed = " + changed);
            Logger.debug("DEFs = " + Arrays.toString(node.getAllDEFs()));
            //
            for (String use: useList) {
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
            for (String flow: selfFlowList) {
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
            for (CppField local: localVars)
                if (local.NAME.equals(id))
                    return LOCAL;
            if (id.startsWith("this."))
                id = id.substring(5);
            for (CppField field: activeClasses.peek().getAllFields())
                if (field.NAME.equals(id))
                    return FIELD;
            for (CppClass cls: activeClasses)
                for (CppField field: cls.getAllFields())
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
                for (CppField param: methodParams)
                    if (param.NAME.equals(id))
                        return param.TYPE;
                for (CppField local: localVars)
                    if (local.NAME.equals(id))
                        return local.TYPE;
                if (id.startsWith("this."))
                    id = id.substring(4);
                for (CppField field: activeClasses.peek().getAllFields())
                    if (field.NAME.equals(id))
                        return field.TYPE;
                for (CppClass cls: activeClasses)
                    for (CppField field: cls.getAllFields())
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

        private CppClass findClass(String type) {
            return null;
        }

        /**
         * Find and return matching method-definition-info.
         * Returns null if not found.
         */
        private MethodDefInfo findDefInfo(String callee, String name, CppParser.InitializerListContext ctx) {
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
                        mtd.PARAM_TYPES.length != ctx.initializerClause().size())
                    return null;
                Logger.debug("WITH MATCHING PARAMS COUNT");
                return mtd;
            }
            //
            if (callee == null) { // no callee; so search for self methods
                Logger.debug("NO CALLEE");
                forEachDefInfo:
                for (MethodDefInfo mtd : list) {
                    // check class-name
                    boolean classNameMatch = false;
                    for (CppClass cls: activeClasses) {
                        if (mtd.CLASS_NAME.equals(cls.NAME)) {
                            classNameMatch = true;
                            break;
                        }
                    }
                    if (!classNameMatch)
                        continue;
                    // check params-count
                    if (ctx != null && mtd.PARAM_TYPES != null &&
                            mtd.PARAM_TYPES.length != ctx.initializerClause().size())
                        continue;
                    // check params-types
                    if (ctx != null) {
                        String[] argTypes = new String[ctx.initializerClause().size()];
                        for (int i = 0; i < argTypes.length; ++i) {
                            String arg = visit(ctx.initializerClause(i));
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
                CppClass cls = allClassInfos.get(type);
                if (cls != null && cls.hasMethod(name)) {
                    forEachDefInfo:
                    for (MethodDefInfo mtd : list) {

                        // check class-name
                        if (!mtd.CLASS_NAME.equals(cls.NAME))
                            continue;
                        // check params-count
                        if (ctx != null && mtd.PARAM_TYPES != null &&
                                mtd.PARAM_TYPES.length != ctx.initializerClause().size())
                            continue;
                        // check params-types
                        if (ctx != null) {
                            String[] argTypes = new String[ctx.initializerClause().size()];
                            for (int i = 0; i < argTypes.length; ++i) {
                                String arg = visit(ctx.initializerClause(i));
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
        private MethodDefInfo findDefInfo(String name, String type, JavaField[] params) {
            List<MethodDefInfo> infoList = methodDEFs.get(name);
            if (infoList.size() > 1) {
                forEachInfo:
                for (MethodDefInfo info: infoList) {
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
            } else
            if (infoList.size() == 1)
                return infoList.get(0);
            return null;
        }

        /**************************************
         ***          DECLARATIONS          ***
         **************************************/
        @Override
        public String visitClassHead(CppParser.ClassHeadContext ctx)
//        classHead:
//        classKey attributeSpecifierSeq? (
//        classHeadName classVirtSpecifier?
//                )? baseClause?
//                | Union attributeSpecifierSeq? (
//        classHeadName classVirtSpecifier?
//                )?;
        { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitLambdaDeclarator(CppParser.LambdaDeclaratorContext ctx) { return visitChildren(ctx); }
//        lambdaDeclarator:
//        LeftParen parameterDeclarationClause? RightParen Mutable? exceptionSpecification?
//        attributeSpecifierSeq? trailingReturnType?;
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateDeclaration(CppParser.TemplateDeclarationContext ctx) { return visitChildren(ctx); }
//        templateDeclaration:
//        Template Less templateparameterList Greater declaration;
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAliasDeclaration(CppParser.AliasDeclarationContext ctx)
//        aliasDeclaration:
//        Using Identifier attributeSpecifierSeq? Assign theTypeId Semi;
        { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         */
        @Override public String visitEnumeratorDefinition(CppParser.EnumeratorDefinitionContext ctx) { return visitChildren(ctx); }
//
//        enumeratorDefinition: enumerator (Assign constantExpression)?;
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx) { return visitChildren(ctx); }
//        namespaceDefinition:
//        Inline? Namespace (Identifier | originalNamespaceName)? LeftBrace namespaceBody = declarationseq
//                ? RightBrace;
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionDefinition(CppParser.FunctionDefinitionContext ctx) { return visitChildren(ctx); }
//        functionDefinition:
//        attributeSpecifierSeq? declSpecifierSeq? declarator virtualSpecifierSeq? functionBody;
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
//        namespaceAliasDefinition:
//        Namespace Identifier Assign qualifiednamespacespecifier Semi;
        @Override public String visitParameterDeclarationClause(CppParser.ParameterDeclarationClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
//        parameterDeclaration:
//        attributeSpecifierSeq? declSpecifierSeq (
//		(declarator | abstractDeclarator?) (
//        Assign initializerClause
//		)?
//                );
        @Override public String visitOpaqueEnumDeclaration(CppParser.OpaqueEnumDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
//        opaqueEnumDeclaration:
//        enumkey attributeSpecifierSeq? Identifier enumbase? Semi;

        /*****************************************************
         行列式表达式（返回对象
         *****************************************************/

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



    /**
     * A simple structure to store DEF information about a Cpp Function.
     */
    static class MethodDefInfo{
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

        public MethodDefInfo(String ret,String name,String namespace,String cls,String[] args)
        {
            NAME=name;
            NAMESPACE=namespace;
            CLASS_NAME=(cls==null?"":cls);
            RET_TYPE=ret;
            PARAM_TYPES=(args==null?new String[0]:args);

            fieldDEFs=new ArrayList<>();
            stateDEF=guessByTypeOrName();
            argDEFs=new boolean[PARAM_TYPES.length];  // all initialized to 'false'
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

        public void addFieldDEF(String fieldName)
        {
            if (!fieldDEFs.contains(fieldName))
            {
                fieldDEFs.add(fieldName);
                stateDEF = true;
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof MethodDefInfo))
                return false;
            MethodDefInfo info = (MethodDefInfo) obj;
            return this.NAMESPACE.equals(info.NAMESPACE) && this.NAME.equals(info.NAME)
                    && this.CLASS_NAME.equals(info.CLASS_NAME)
                    &&  this.RET_TYPE.equals(info.RET_TYPE)
                    && Arrays.equals(this.PARAM_TYPES, info.PARAM_TYPES);
        }

        @Override
        public String toString()
        {
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

