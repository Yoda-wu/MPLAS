package com.scut.mplas.javascript;

import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.cfg.CFEdge;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.java.JavaCFGBuilder;
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

public class JavaScriptCFGBuilder {

    /**
     * ‌Build and return the Control Flow Graph (CFG) for the given Java source file.
     */
    public static ControlFlowGraph build(String jsFile) throws IOException {
        return build(new File(jsFile));
    }

    /**
     * ‌Build and return the Control Flow Graph (CFG) for the given Java source file.
     */
    public static ControlFlowGraph build(File jsFile) throws IOException {
        if (!jsFile.getName().endsWith(".js"))
            throw new IOException("Not a JavaScript File!");
        InputStream inFile = new FileInputStream(jsFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        JavaScriptLexer lexer = new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();
        return build(jsFile.getName(), tree, null, null);
    }

    public static ControlFlowGraph build(String fileName, InputStream inputStream) throws IOException {

        InputStream inFile = inputStream;
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        JavaScriptLexer lexer=new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser=new JavaScriptParser(tokens);
        ParseTree tree = parser.program();
        return build(fileName, tree, null, null);
    }

    /**
     * ‌Build and return the Control Flow Graph (CFG) for the given Parse-Tree.
     * The 'ctxProps' map includes contextual-properties for particular nodes
     * in the parse-tree, which can be used for linking this graph with other
     * graphs by using the same parse-tree and the same contextual-properties.
     */
    public static ControlFlowGraph build(String jsFileName, ParseTree tree,
                                         String propKey, Map<ParserRuleContext, Object> ctxProps) {
        ControlFlowGraph cfg = new ControlFlowGraph(jsFileName);
        JavaScriptCFGBuilder.ControlFlowVisitor visitor = new JavaScriptCFGBuilder.ControlFlowVisitor(cfg, propKey, ctxProps);
        visitor.visit(tree);
        return cfg;
    }

    /**
     * Visitor-class which constructs the CFG by walking the parse-tree.
     */
    private static class ControlFlowVisitor extends JavaScriptBaseVisitor<Void>{

        private ControlFlowGraph cfg;
        private Deque<CFNode> preNodes;
        private Deque<CFEdge.Type> preEdges;
        private Deque<Block> loopBlocks;
        private List<Block> labeledBlocks;
        private Deque<Block> tryBlocks;
        private Queue<CFNode> casesQueue;
        private boolean dontPop;
        private String propKey;
        private Map<ParserRuleContext, Object> contexutalProperties;
        private Deque<String> classNames;

        public ControlFlowVisitor(ControlFlowGraph cfg, String propKey, Map<ParserRuleContext, Object> ctxProps) {
            preNodes = new ArrayDeque<>();
            preEdges = new ArrayDeque<>();
            loopBlocks = new ArrayDeque<>();
            labeledBlocks = new ArrayList<>();
            tryBlocks = new ArrayDeque<>();
            casesQueue = new ArrayDeque<>();
            classNames = new ArrayDeque<>();
            dontPop = false;
            this.cfg = cfg;
            //
            this.propKey = propKey;
            contexutalProperties = ctxProps;
        }

        /**
         * Reset all data-structures and flags for visiting a new method declaration.
         */
        private void init() {
            preNodes.clear();
            preEdges.clear();
            loopBlocks.clear();
            labeledBlocks.clear();
            tryBlocks.clear();
            dontPop = false;
        }

        /**
         * Add contextual properties to the given node.
         * This will first check to see if there is any property for the
         * given context, and if so, the property will be added to the node.
         */
        private void addContextualProperty(CFNode node, ParserRuleContext ctx) {
            if (propKey != null && contexutalProperties != null) {
                Object prop = contexutalProperties.get(ctx);
                if (prop != null)
                    node.setProperty(propKey, prop);
            }
        }


//        @Override public Void visitProgram(JavaScriptParser.ProgramContext ctx) {
//           //program
//            //    : HashBangLine? sourceElements? EOF
//            System.out.println("进入visit=================");
//            cfg.setPackage(ctx.sourceElements().getText());
//            visitSourceElements(ctx.sourceElements());
//            return null;
//        }
//
//
//        @Override public Void visitSourceElements(JavaScriptParser.SourceElementsContext ctx) {
//            for (JavaScriptParser.SourceElementContext sourceElementContext : ctx.sourceElement()) {
//                visitSourceElement(sourceElementContext);
//            }
//            return null;
//        }
//
//
//        @Override public Void visitSourceElement(JavaScriptParser.SourceElementContext ctx) {
//            if (ctx==null||ctx.isEmpty()){
//                return null;
//            }
//            System.out.println("进入sourceElement======");
//            return visitStatement(ctx.statement());
//        }
//
//
//        @Override public Void visitStatement(JavaScriptParser.StatementContext ctx) {
//            if(ctx==null||ctx.isEmpty()){
//                return null;
//            }
//            String res=null;
//            if (ctx.block()!=null&&!ctx.block().isEmpty()){
//                System.out.println("block");
//                return visitBlock(ctx.block());
//            }else if(ctx.importStatement()!=null&&!ctx.importStatement().isEmpty()) {
//                System.out.println("import");
//                return visitImportStatement(ctx.importStatement());
//            }else if (ctx.variableStatement()!=null&&!ctx.variableStatement().isEmpty()){
//                System.out.println("variable");
//                return  visitVariableStatement(ctx.variableStatement());
//            }else if (ctx.exportStatement()!=null&&!ctx.exportStatement().isEmpty()){
//                System.out.println("export");
//                return  visitExpressionStatement(ctx.expressionStatement());
//            } else if (ctx.emptyStatement_()!=null&&!ctx.emptyStatement_().isEmpty()){
//                System.out.println("empty");
//                return  visitEmptyStatement_(ctx.emptyStatement_());
//            }else if (ctx.classDeclaration()!=null&&!ctx.classDeclaration().isEmpty()){
//                System.out.println("classDeclarataion");
//                return  visitClassDeclaration(ctx.classDeclaration());
//            }else if (ctx.expressionStatement()!=null&&!ctx.expressionStatement().isEmpty()){
//                System.out.println("expression");
//                return  visitExpressionStatement(ctx.expressionStatement());
//            }else if (ctx.ifStatement()!=null&&!ctx.ifStatement().isEmpty()){
//                System.out.println("if");
//                return  visitIfStatement(ctx.ifStatement());
//            }else if (ctx.iterationStatement()!=null&&!ctx.iterationStatement().isEmpty()){
//                return  null;
//            }else if (ctx.continueStatement()!=null&&!ctx.continueStatement().isEmpty()){
//                System.out.println("continue");
//                return  visitContinueStatement(ctx.continueStatement());
//            }else if (ctx.breakStatement()!=null&&!ctx.breakStatement().isEmpty()){
//                System.out.println("break;");
//                return  visitBreakStatement(ctx.breakStatement());
//            }else if (ctx.returnStatement()!=null&&!ctx.returnStatement().isEmpty()){
//                System.out.println("return");
//                return  visitReturnStatement(ctx.returnStatement());
//            }else if (ctx.yieldStatement()!=null&&!ctx.yieldStatement().isEmpty()){
//                System.out.println("yield");
//                return  visitYieldStatement(ctx.yieldStatement());
//            }else if (ctx.withStatement()!=null&&!ctx.withStatement().isEmpty()){
//                System.out.println("with");
//                return  visitWithStatement(ctx.withStatement());
//            }else if (ctx.labelledStatement()!=null&&!ctx.labelledStatement().isEmpty()){
//                System.out.println("label");
//                return  visitLabelledStatement(ctx.labelledStatement());
//            }else if (ctx.switchStatement()!=null&&!ctx.switchStatement().isEmpty()){
//                System.out.println("switch");
//                return  visitSwitchStatement(ctx.switchStatement());
//            }else if (ctx.throwStatement()!=null&&!ctx.throwStatement().isEmpty()){
//                System.out.println("throw");
//                return  visitThrowStatement(ctx.throwStatement());
//            }else if (ctx.tryStatement()!=null&&!ctx.tryStatement().isEmpty()){
//                System.out.println("try");
//                return  visitTryStatement(ctx.tryStatement());
//            }else if (ctx.debuggerStatement()!=null&&!ctx.debuggerStatement().isEmpty()){
//                System.out.println("debugg");
//                return  visitDebuggerStatement(ctx.debuggerStatement());
//            }else{
//                System.out.println("function");
//                return visitFunctionDeclaration(ctx.functionDeclaration());
//            }
//        }

        @Override public Void visitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) {
            //    : {this.notOpenBraceAndNotFunction()}? expressionSequence eos
            CFNode expr=new CFNode();
            expr.setLineOfCode(ctx.getStart().getLine());
            expr.setCode(getOriginalCodeText(ctx));
            Logger.debug(expr.getLineOfCode()+":"+expr.getCode());
            addContextualProperty(expr,ctx);
            addNodeAndPreEdge(expr);
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(expr);
            return null;
        }


        @Override public Void visitIfStatement(JavaScriptParser.IfStatementContext ctx) {
            //    : If '(' expressionSequence ')' statement (Else statement)?
            CFNode ifNode = new CFNode();
            ifNode.setLineOfCode(ctx.getStart().getLine());
            ifNode.setCode("if " + getOriginalCodeText(ctx.expressionSequence()));
            addContextualProperty(ifNode, ctx);
            addNodeAndPreEdge(ifNode);
            //
            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(ifNode);
            //
            visit(ctx.statement(0));
            //
            CFNode endif = new CFNode();
            endif.setLineOfCode(0);
            endif.setCode("endif");
            addNodeAndPreEdge(endif);
            //
            if (ctx.statement().size() == 1) { // if without else
                cfg.addEdge(new Edge<>(ifNode, new CFEdge(CFEdge.Type.FALSE), endif));
            } else {  //  if with else
                preEdges.push(CFEdge.Type.FALSE);
                preNodes.push(ifNode);
                visit(ctx.statement(1));
                popAddPreEdgeTo(endif);
            }
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endif);
            return null;
        }

        @Override public Void visitDoStatement(JavaScriptParser.DoStatementContext ctx) {
            //Do statement While '(' expressionSequence ')' eos
            CFNode doNode = new CFNode();
            doNode.setLineOfCode(ctx.getStart().getLine());
            doNode.setCode("do");
            addNodeAndPreEdge(doNode);
            //
            CFNode whileNode = new CFNode();
            whileNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            whileNode.setCode("while " + getOriginalCodeText(ctx.expressionSequence()));
            addContextualProperty(whileNode, ctx);
            cfg.addVertex(whileNode);
            //
            CFNode doWhileEnd = new CFNode();
            doWhileEnd.setLineOfCode(0);
            doWhileEnd.setCode("end-do-while");
            cfg.addVertex(doWhileEnd);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(doNode);
            loopBlocks.push(new Block(whileNode, doWhileEnd));
            visit(ctx.statement());
            loopBlocks.pop();
            popAddPreEdgeTo(whileNode);
            cfg.addEdge(new Edge<>(whileNode, new CFEdge(CFEdge.Type.TRUE), doNode));
            cfg.addEdge(new Edge<>(whileNode, new CFEdge(CFEdge.Type.FALSE), doWhileEnd));
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(doWhileEnd);
            return null;
        }

        @Override public Void visitWhileStatement(JavaScriptParser.WhileStatementContext ctx) {
            //While '(' expressionSequence ')' statement
            CFNode whileNode = new CFNode();
            whileNode.setLineOfCode(ctx.getStart().getLine());
            whileNode.setCode("while " + getOriginalCodeText(ctx.expressionSequence()));
            addContextualProperty(whileNode, ctx);
            addNodeAndPreEdge(whileNode);
            //
            CFNode endwhile = new CFNode();
            endwhile.setLineOfCode(0);
            endwhile.setCode("endwhile");
            cfg.addVertex(endwhile);
            cfg.addEdge(new Edge<>(whileNode, new CFEdge(CFEdge.Type.FALSE), endwhile));
            //
            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(whileNode);
            loopBlocks.push(new Block(whileNode, endwhile));
            visit(ctx.statement());
            loopBlocks.pop();
            popAddPreEdgeTo(whileNode);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endwhile);
            return null;
        }

        @Override public Void visitForStatement(JavaScriptParser.ForStatementContext ctx) {
            //For '(' (expressionSequence | variableDeclarationList)? ';' expressionSequence? ';' expressionSequence? ')' statement
            //forInit
            CFNode forInit=null;
            int forIndex=-1;
            if (ctx.variableDeclarationList()!=null){
                forIndex=0;
                forInit=new CFNode();
                forInit.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                forInit.setCode(getOriginalCodeText(ctx.variableDeclarationList()));
                addContextualProperty(forInit,ctx.variableDeclarationList());
                addNodeAndPreEdge(forInit);
            }else{
                forIndex=1;
                forInit=new CFNode();
                forInit.setLineOfCode(ctx.expressionSequence(0).getStart().getLine());
                forInit.setCode(getOriginalCodeText(ctx.expressionSequence(0)));
                addContextualProperty(forInit,ctx.expressionSequence(0));
                addNodeAndPreEdge(forInit);
            }
            //for-expression
            CFNode forExpr = new CFNode();
            if (ctx.expressionSequence(forIndex) == null) {
                forExpr.setLineOfCode(ctx.expressionSequence(forIndex).getStart().getLine());
                forExpr.setCode("for ( ; )");
            } else {
                forExpr.setLineOfCode(ctx.expressionSequence(forIndex).getStart().getLine());
                forExpr.setCode("for (" + getOriginalCodeText(ctx.expressionSequence(forIndex)) + ")");
            }
            addContextualProperty(forExpr, ctx.expressionSequence(forIndex));
            cfg.addVertex(forExpr);
            if (forInit != null)
                cfg.addEdge(new Edge<>(forInit, new CFEdge(CFEdge.Type.EPSILON), forExpr));
            else
                popAddPreEdgeTo(forExpr);
            // for-update
            CFNode forUpdate = new CFNode();
            if (ctx.expressionSequence(forIndex+1) == null) { // empty for-update
                forUpdate.setCode(" ; ");
                forUpdate.setLineOfCode(ctx.expressionSequence(forIndex+1).getStart().getLine());
            } else {
                forUpdate.setCode(getOriginalCodeText(ctx.expressionSequence(forIndex+1)));
                forUpdate.setLineOfCode(ctx.expressionSequence(forIndex+1).getStart().getLine());
            }
            addContextualProperty(forUpdate, ctx.expressionSequence(forIndex+1));
            cfg.addVertex(forUpdate);
            //
            CFNode forEnd = new CFNode();
            forEnd.setLineOfCode(0);
            forEnd.setCode("endfor");
            cfg.addVertex(forEnd);
            cfg.addEdge(new Edge<>(forExpr, new CFEdge(CFEdge.Type.FALSE), forEnd));
            //
            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(forExpr);
            loopBlocks.push(new Block(forUpdate, forEnd)); // NOTE: start is 'forUpdate'
            visit(ctx.statement());
            loopBlocks.pop();
            popAddPreEdgeTo(forUpdate);
            cfg.addEdge(new Edge<>(forUpdate, new CFEdge(CFEdge.Type.EPSILON), forExpr));
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(forEnd);
            return null;
        }

        @Override public Void visitForInStatement(JavaScriptParser.ForInStatementContext ctx) {
            //For '(' (singleExpression | variableDeclarationList) In expressionSequence ')' statement
            CFNode forExpr = new CFNode();
            if (ctx.singleExpression()!=null){
                forExpr.setLineOfCode(ctx.statement().getStart().getLine());
                forExpr.setCode("for("+getOriginalCodeText(ctx.statement())+")");
                addContextualProperty(forExpr, ctx.statement());
                addNodeAndPreEdge(forExpr);
            }else{
                forExpr.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                forExpr.setCode("for("+getOriginalCodeText(ctx.variableDeclarationList())+")");
                addContextualProperty(forExpr, ctx.statement());
                addNodeAndPreEdge(forExpr);
            }
            //
            CFNode forEnd = new CFNode();
            forEnd.setLineOfCode(0);
            forEnd.setCode("endfor");
            cfg.addVertex(forEnd);
            cfg.addEdge(new Edge<>(forExpr, new CFEdge(CFEdge.Type.FALSE), forEnd));
            //
            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(forExpr);
            //
            loopBlocks.push(new Block(forExpr, forEnd));
            visit(ctx.expressionSequence());
            loopBlocks.pop();
            popAddPreEdgeTo(forExpr);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(forEnd);
           return null;
        }

        @Override public Void visitForOfStatement(JavaScriptParser.ForOfStatementContext ctx) {
            //    | For Await? '(' (singleExpression | variableDeclarationList) identifier{this.p("of")}? expressionSequence ')' statement  # ForOfStatement
            CFNode forExpr = new CFNode();
            if (ctx.singleExpression()!=null){
                forExpr.setLineOfCode(ctx.statement().getStart().getLine());
                forExpr.setCode("for("+ctx.Await().getText()+getOriginalCodeText(ctx.singleExpression())+" "+
                        getOriginalCodeText(ctx.identifier())+" of "+getOriginalCodeText(ctx.expressionSequence())+")");
                addContextualProperty(forExpr, ctx.statement());
                addNodeAndPreEdge(forExpr);
            }else{
                forExpr.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                forExpr.setCode("for("+ctx.Await().getText()+getOriginalCodeText(ctx.variableDeclarationList())+" "+
                        getOriginalCodeText(ctx.identifier())+" of "+getOriginalCodeText(ctx.expressionSequence())+")");
                addContextualProperty(forExpr, ctx.statement());
                addNodeAndPreEdge(forExpr);
            }
            //
            CFNode forEnd = new CFNode();
            forEnd.setLineOfCode(0);
            forEnd.setCode("endfor");
            cfg.addVertex(forEnd);
            cfg.addEdge(new Edge<>(forExpr, new CFEdge(CFEdge.Type.FALSE), forEnd));
            //
            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(forExpr);
            //
            loopBlocks.push(new Block(forExpr, forEnd));
            visit(ctx.statement());
            loopBlocks.pop();
            popAddPreEdgeTo(forExpr);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(forEnd);
            return null;
        }

        @Override public Void visitVarModifier(JavaScriptParser.VarModifierContext ctx) {
            CFNode varNode=new CFNode();
            varNode.setLineOfCode(ctx.getStart().getLine());
            varNode.setCode(getOriginalCodeText(ctx));
            addContextualProperty(varNode, ctx);
            addNodeAndPreEdge(varNode);
            dontPop = true;
            return null;
        }

        @Override public Void visitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) {
            //    : Continue ({this.notLineTerminator()}? identifier)? eos
            CFNode continueNode = new CFNode();
            continueNode.setLineOfCode(ctx.getStart().getLine());
            continueNode.setCode(getOriginalCodeText(ctx));
            addContextualProperty(continueNode, ctx);
            addNodeAndPreEdge(continueNode);
            if (ctx.identifier() != null) {
                // a label is specified
                for (Block block: labeledBlocks) {
                    if (block.label.equals(ctx.identifier().Identifier().getText())) {
                        cfg.addEdge(new Edge<>(continueNode, new CFEdge(CFEdge.Type.EPSILON), block.start));
                        break;
                    }
                }
            } else {
                // no label
                Block block = loopBlocks.peek();
                cfg.addEdge(new Edge<>(continueNode, new CFEdge(CFEdge.Type.EPSILON), block.start));
            }
            dontPop = true;
            return null;
        }

        @Override public Void visitBreakStatement(JavaScriptParser.BreakStatementContext ctx) {
            //    : Break ({this.notLineTerminator()}? identifier)? eos
            CFNode breakNode = new CFNode();
            breakNode.setLineOfCode(ctx.getStart().getLine());
            breakNode.setCode(getOriginalCodeText(ctx));
            addContextualProperty(breakNode, ctx);
            addNodeAndPreEdge(breakNode);
            if (ctx.identifier()!= null) {
                // a label is specified
                for (Block block: labeledBlocks) {
                    if (block.label.equals(ctx.identifier().Identifier().getText())) {
                        cfg.addEdge(new Edge<>(breakNode, new CFEdge(CFEdge.Type.EPSILON), block.end));
                        break;
                    }
                }
            } else {
                // no label
                Block block = loopBlocks.peek();
                cfg.addEdge(new Edge<>(breakNode, new CFEdge(CFEdge.Type.EPSILON), block.end));
            }
            dontPop = true;
            return null;
        }

        @Override public Void visitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) {
            //    : Return ({this.notLineTerminator()}? expressionSequence)? eos
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addContextualProperty(ret, ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }

        @Override public Void visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
            CFNode yieldNode = new CFNode();
            yieldNode.setLineOfCode(ctx.getStart().getLine());
            yieldNode.setCode(getOriginalCodeText(ctx));
            addContextualProperty(yieldNode, ctx);
            addNodeAndPreEdge(yieldNode);
            dontPop = true;
            return null;
        }

        @Override public Void visitWithStatement(JavaScriptParser.WithStatementContext ctx) {
            //    : With '(' expressionSequence ')' statement
            CFNode withNode = new CFNode();
            withNode.setLineOfCode(ctx.getStart().getLine());
            withNode.setCode("with " + getOriginalCodeText(ctx.expressionSequence()));
            addContextualProperty(withNode, ctx);
            addNodeAndPreEdge(withNode);
            //
            CFNode endSwitch = new CFNode();
            endSwitch.setLineOfCode(0);
            endSwitch.setCode("end-with");
            cfg.addVertex(endSwitch);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(withNode);
            loopBlocks.push(new Block(withNode, endSwitch));
            //
            visit(ctx.statement());
            loopBlocks.pop();
            popAddPreEdgeTo(endSwitch);
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endSwitch);
            return null;
        }

        @Override public Void visitSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) {
            //    : Switch '(' expressionSequence ')' caseBlock
            CFNode switchNode = new CFNode();
            switchNode.setLineOfCode(ctx.getStart().getLine());
            switchNode.setCode("switch " + getOriginalCodeText(ctx.expressionSequence()));
            addContextualProperty(switchNode, ctx);
            addNodeAndPreEdge(switchNode);
            //
            CFNode endSwitch = new CFNode();
            endSwitch.setLineOfCode(0);
            endSwitch.setCode("end-switch");
            cfg.addVertex(endSwitch);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(switchNode);
            loopBlocks.push(new Block(switchNode, endSwitch));
            //
            CFNode preCase = null;
            for (JavaScriptParser.CaseClausesContext caseClauses : ctx.caseBlock().caseClauses()) {
                //            //    : '{' caseClauses? (defaultClause caseClauses?)? '}'
                preCase=visitSwitchCaseClauses(caseClauses,preCase);
                for (JavaScriptParser.CaseClauseContext caseClause : caseClauses.caseClause()) {
                    visit(caseClause);
                }
            }
            loopBlocks.pop();
            popAddPreEdgeTo(endSwitch);
            if (preCase != null)
                cfg.addEdge(new Edge<>(preCase, new CFEdge(CFEdge.Type.FALSE), endSwitch));
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endSwitch);
            return null;
        }

        private CFNode visitSwitchCaseClauses(JavaScriptParser.CaseClausesContext caseClauses, CFNode preCase) {
            //            //    : Case expressionSequence ':' statementList?
            CFNode caseStmnt=preCase;
            for (JavaScriptParser.CaseClauseContext ctx : caseClauses.caseClause()) {
                caseStmnt = new CFNode();
                caseStmnt.setLineOfCode(ctx.getStart().getLine());
                caseStmnt.setCode(getOriginalCodeText(ctx));
                cfg.addVertex(caseStmnt);
                if (dontPop)
                    dontPop = false;
                else
                    cfg.addEdge(new Edge<>(preNodes.pop(), new CFEdge(preEdges.pop()), caseStmnt));
                if (preCase != null)
                    cfg.addEdge(new Edge<>(preCase, new CFEdge(CFEdge.Type.FALSE), caseStmnt));
                if (ctx.getStart().getText().equals("default")) {
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(caseStmnt);
                    caseStmnt = null;
                } else { // any other case ...
                    dontPop = true;
                    casesQueue.add(caseStmnt);
                    preCase = caseStmnt;
                }
            }
            return caseStmnt;
        }

        @Override public Void visitLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) {
            //labelledStatement
            //    : identifier ':' statement
            CFNode labelNode = new CFNode();
            labelNode.setLineOfCode(ctx.getStart().getLine());
            labelNode.setCode(ctx.identifier().Identifier() + ": ");
            addContextualProperty(labelNode, ctx);
            addNodeAndPreEdge(labelNode);
            //
            CFNode endLabelNode = new CFNode();
            endLabelNode.setLineOfCode(0);
            endLabelNode.setCode("end-label");
            cfg.addVertex(endLabelNode);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(labelNode);
            labeledBlocks.add(new Block(labelNode, endLabelNode, ctx.identifier().Identifier().getText()));
            visit(ctx.statement());
            popAddPreEdgeTo(endLabelNode);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endLabelNode);
            return null;
        }

        @Override public Void visitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) {
            CFNode throwNode = new CFNode();
            throwNode.setLineOfCode(ctx.getStart().getLine());
            throwNode.setCode(getOriginalCodeText(ctx));
            addContextualProperty(throwNode, ctx);
            addNodeAndPreEdge(throwNode);
            dontPop = true;
            return null;
        }

        @Override public Void visitTryStatement(JavaScriptParser.TryStatementContext ctx) {
            //    : Try block (catchProduction finallyProduction? | finallyProduction)
            CFNode tryNode = new CFNode();
            tryNode.setLineOfCode(ctx.getStart().getLine());
            tryNode.setCode("try");
            addContextualProperty(tryNode, ctx);
            addNodeAndPreEdge(tryNode);
            //
            CFNode endTry = new CFNode();
            endTry.setLineOfCode(0);
            endTry.setCode("end-try");
            cfg.addVertex(endTry);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(tryNode);
            tryBlocks.push(new Block(tryNode, endTry));
            visit(ctx.block());
            popAddPreEdgeTo(endTry);

            // If there is a finally-block, visit it first
            CFNode finallyNode = null;
            CFNode endFinally = null;
            if (ctx.finallyProduction() != null) {
                // 'finally' block
                finallyNode = new CFNode();
                finallyNode.setLineOfCode(ctx.finallyProduction().getStart().getLine());
                finallyNode.setCode("finally");
                addContextualProperty(finallyNode, ctx.finallyProduction());
                cfg.addVertex(finallyNode);
                cfg.addEdge(new Edge<>(endTry, new CFEdge(CFEdge.Type.EPSILON), finallyNode));
                //
                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(finallyNode);
                visit(ctx.finallyProduction().block());
                //
                endFinally = new CFNode();
                endFinally.setLineOfCode(0);
                endFinally.setCode("end-finally");
                addNodeAndPreEdge(endFinally);
            }
            // Now visit any available catch clauses
            if (ctx.catchProduction() != null) {
                // 'catch' '(' variableModifier* catchType Identifier ')' block
                CFNode catchNode;
                CFNode endCatch = new CFNode();
                endCatch.setLineOfCode(0);
                endCatch.setCode("end-catch");
                cfg.addVertex(endCatch);
                catchNode=new CFNode();
                catchNode.setLineOfCode(ctx.catchProduction().getStart().getLine());
                catchNode.setCode("catch("+ctx.catchProduction().getText()+")");
                addContextualProperty(catchNode,ctx.catchProduction());
                cfg.addVertex(catchNode);
                cfg.addEdge(new Edge<>(endTry,new CFEdge(CFEdge.Type.THROWS),catchNode));
                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(catchNode);
                visit(ctx.catchProduction().block());
                popAddPreEdgeTo(endCatch);
                if (finallyNode != null) {
                    // connect end-catch node to finally-node,
                    // and push end-finally to the stack ...
                    cfg.addEdge(new Edge<>(endCatch, new CFEdge(CFEdge.Type.EPSILON), finallyNode));
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(endFinally);
                } else {
                    // connect end-catch node to end-try,
                    // and push end-try to the the stack ...
                    cfg.addEdge(new Edge<>(endCatch, new CFEdge(CFEdge.Type.EPSILON), endTry));
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(endTry);
                }
            } else {
                // No catch-clause; it's a try-finally
                // push end-finally to the stack ...
                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(endFinally);
            }
            // NOTE that Java does not allow a singular try-block (without catch or finally)
            return null;
        }

        @Override public Void visitCatchProduction(JavaScriptParser.CatchProductionContext ctx) {
            CFNode catchNode = new CFNode();
            catchNode.setLineOfCode(ctx.getStart().getLine());
            catchNode.setCode("catch("+getOriginalCodeText(ctx.assignable())+")");
            addContextualProperty(catchNode, ctx);
            addNodeAndPreEdge(catchNode);
            CFNode endCatch=new CFNode();
            endCatch.setLineOfCode(0);
            endCatch.setCode("end-catch");
            cfg.addVertex(endCatch);
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endCatch);
            labeledBlocks.add(new Block(catchNode,endCatch));
            visit(ctx.block());
            popAddPreEdgeTo(endCatch);
            dontPop = true;
            return null;
        }

        @Override public Void visitFinallyProduction(JavaScriptParser.FinallyProductionContext ctx) {
            CFNode finalNode = new CFNode();
            finalNode.setLineOfCode(ctx.getStart().getLine());
            finalNode.setCode(getOriginalCodeText(ctx));
            addContextualProperty(finalNode, ctx);
            addNodeAndPreEdge(finalNode);
            CFNode endFinal=new CFNode();
            endFinal.setLineOfCode(0);
            endFinal.setCode("end-finally");
            cfg.addVertex(endFinal);
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endFinal);
            labeledBlocks.add(new Block(finalNode,endFinal));
            visit(ctx.block());
            popAddPreEdgeTo(endFinal);
            dontPop = true;
            return null;
        }

        @Override public Void visitDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) {
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addContextualProperty(ret, ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }

        @Override public Void visitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
            //    : Async? Function_ '*'? identifier '(' formalParameterList? ')' functionBody
            init();
            //
            CFNode entry = new CFNode();
            entry.setLineOfCode(ctx.getStart().getLine());
            String args ="";
            if(ctx.formalParameterList()!=null&&!ctx.formalParameterList().isEmpty()){
                args=  getOriginalCodeText(ctx.formalParameterList());
            }
            entry.setCode( ctx.identifier().Identifier() + args);
            addContextualProperty(entry, ctx);
            cfg.addVertex(entry);
            //
            entry.setProperty("name", ctx.identifier().Identifier().getText());
            entry.setProperty("class", classNames.peek());
            cfg.addMethodEntry(entry);
            //
            preNodes.push(entry);
            preEdges.push(CFEdge.Type.EPSILON);
            return visitChildren(ctx);
        }

        @Override public Void visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
            //Class identifier classTail
            classNames.push(getOriginalCodeText(ctx.identifier()));
            visit(ctx.classTail());
            classNames.pop();
            CFNode classNode=new CFNode();
            classNode.setCode(getOriginalCodeText(ctx));
            classNode.setLineOfCode(ctx.getStart().getLine());
            cfg.addVertex(classNode);
            cfg.addMethodEntry(classNode);
            preNodes.push(classNode);
            preEdges.push(CFEdge.Type.EPSILON);
            return null;
        }

        @Override public Void visitClassTail(JavaScriptParser.ClassTailContext ctx) {
            //    : (Extends singleExpression)? '{' classElement* '}'
            CFNode classTail=new CFNode();
            String extend="";
            if (ctx.singleExpression()!=null){
                extend=getOriginalCodeText(ctx.singleExpression());
            }
            for (JavaScriptParser.ClassElementContext classElement : ctx.classElement()) {
                extend+=" ";
                extend+=getOriginalCodeText(classElement);
            }
            classTail.setCode(extend);
            classTail.setLineOfCode(ctx.getStart().getLine());
            cfg.addVertex(classTail);
            cfg.addMethodEntry(classTail);
            preNodes.push(classTail);
            preEdges.push(CFEdge.Type.EPSILON);
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitClassElement(JavaScriptParser.ClassElementContext ctx) {
            //classElement
            //    : (Static | {this.n("static")}? identifier | Async)* (methodDefinition | assignable '=' objectLiteral ';')
            //    | emptyStatement_
            //    | '#'? propertyName '=' singleExpression
            //    ;
            return visitChildren(ctx);
        }

        @Override public Void visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {
            //    : '*'? '#'? propertyName '(' formalParameterList? ')' functionBody
            //    | '*'? '#'? getter '(' ')' functionBody
            //    | '*'? '#'? setter '(' formalParameterList? ')' functionBody
            CFNode mdNode=new CFNode();
            mdNode.setLineOfCode(ctx.getStart().getLine());
            if (ctx.propertyName()!=null){
                mdNode.setCode(getOriginalCodeText(ctx.propertyName())+"("+getOriginalCodeText(ctx.formalParameterList())+")");
                addContextualProperty(mdNode,ctx.propertyName());
            }else if(ctx.getter()!=null){
                mdNode.setCode(getOriginalCodeText(ctx.getter())+"()");
                addContextualProperty(mdNode,ctx.getter());
            }else{
                mdNode.setCode(getOriginalCodeText(ctx.setter())+"("+getOriginalCodeText(ctx.formalParameterList())+")");
                addContextualProperty(mdNode,ctx.setter());
            }
            addNodeAndPreEdge(mdNode);
            CFNode mdEnd=new CFNode();
            mdEnd.setLineOfCode(0);
            cfg.addVertex(mdNode);
            cfg.addVertex(mdEnd);
            cfg.addEdge(new Edge<>(mdNode,new CFEdge(CFEdge.Type.EPSILON),mdEnd));
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(mdNode);
            labeledBlocks.add(new Block(mdNode,mdEnd));
            visit(ctx.functionBody());
            labeledBlocks.remove(labeledBlocks.size()-1);
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(mdEnd);
            return null;
        }


        @Override public Void visitLet_(JavaScriptParser.Let_Context ctx) {
            //    : Let ({this.notLineTerminator()}? expressionSequence)? eos
            System.out.println("let");
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addContextualProperty(ret, ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }

        @Override public Void visitFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) {
            if (ctx.formalParameterArg()==null||ctx.formalParameterArg().size()==0){
                return visitLastFormalParameterArg(ctx.lastFormalParameterArg());
            }
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getOriginalCodeText(ctx.formalParameterArg(0)));
            for(int i=1;i<ctx.formalParameterArg().size();i++){
                stringBuffer.append(",");
                stringBuffer.append(getOriginalCodeText(ctx.formalParameterArg(i)));
            }
            stringBuffer.append(",");
            if (ctx.lastFormalParameterArg()!=null&&!ctx.lastFormalParameterArg().isEmpty()){
                stringBuffer.append(getOriginalCodeText(ctx.lastFormalParameterArg()));
            }
            ret.setCode(stringBuffer.toString());
            addContextualProperty(ret, ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }

        @Override public Void visitFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) {
            //    : assignable ('=' singleExpression)?      // ECMAScript 6: Initialization
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx.assignable())+"="+getOriginalCodeText(ctx.singleExpression()));
            addContextualProperty(ret, ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }

        @Override public Void visitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) {
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx.singleExpression()));
            addContextualProperty(ret, ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }

        @Override public Void visitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) {
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx.sourceElements()));
            addContextualProperty(ret, ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }

        /**
         * Get resulting Control-Flow-Graph of this CFG-Builder.
         */
        public ControlFlowGraph getCFG() {
            return cfg;
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


        private void addNodeAndPreEdge(CFNode node) {
            cfg.addVertex(node);
            popAddPreEdgeTo(node);
        }

        /**
         * Add a new edge to the given node, by poping the edge-type of the stack.
         */
        private void popAddPreEdgeTo(CFNode node) {
            if (dontPop)
                dontPop = false;
            else {
                Logger.debug("\nPRE-NODES = " + preNodes.size());
                Logger.debug("PRE-EDGES = " + preEdges.size() + '\n');
                if (preNodes.size()>0){
                    cfg.addEdge(new Edge<>(preNodes.pop(), new CFEdge(preEdges.pop()), node));
                }
            }
            //
            for (int i = casesQueue.size(); i > 0; --i)
                cfg.addEdge(new Edge<>(casesQueue.remove(), new CFEdge(CFEdge.Type.TRUE), node));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        /**
         * A simple structure for holding the start, end, and label of code blocks.
         * These are used to handle 'break' and 'continue' statements.
         */

        private class Block {

            public final String label;
            public final CFNode start, end;

            Block(CFNode start, CFNode end, String label) {
                this.start = start;
                this.end = end;
                this.label = label;
            }

            Block(CFNode start, CFNode end) {
                this(start, end, "");
            }
        }
    }
}
