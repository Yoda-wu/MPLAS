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
        private static final int PARAM = 1;
        private static final int FIELD = 101;
        private static final int LOCAL = 202;
        private static final int OUTER = 303;

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

