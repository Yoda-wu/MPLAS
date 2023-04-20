package com.scut.mplas.cpp;

import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import com.scut.mplas.graphs.cfg.CFEdge;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.java.parser.JavaParser;
import ghaffarian.graphs.*;
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
 * A Control Flow Graph (CFG) builder for Cpp programs.
 * A Java parser generated via ANTLRv4 is used for this purpose.
 * This implementation is based on ANTLRv4's Visitor pattern.

 */

public class CppCFGBuilder {
    /**
     * Build and return the Control Flow Graph (CFG) for the given Cpp source file.
     */
    public static ControlFlowGraph build(String cppFile) throws IOException {
        return build(new File(cppFile));
    }

    public static ControlFlowGraph build(File cppFile) throws IOException {
        if (!cppFile.getName().endsWith(".cpp"))
            throw new IOException("Not a Cpp File!");
        InputStream inFile = new FileInputStream(cppFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser.translationUnit();
        return build(cppFile.getName(), tree, null, null);
    }
    public static ControlFlowGraph build(String fileName, InputStream inputStream) throws IOException {

        InputStream inFile = inputStream;
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser.translationUnit();
        return build(fileName, tree, null, null);
    }
    /**
     * Build and return the Control Flow Graph (CFG) for the given Parse-Tree.
     * The 'ctxProps' map includes contextual-properties for particular nodes
     * in the parse-tree, which can be used for linking this graph with other
     * graphs by using the same parse-tree and the same contextual-properties.
     */

    public static ControlFlowGraph build(String cppFileName, ParseTree tree,
                                         String propKey, Map<ParserRuleContext, Object> ctxProps) {
        ControlFlowGraph cfg = new ControlFlowGraph(cppFileName);
        ControlFlowVisitor visitor = new ControlFlowVisitor(cfg, propKey, ctxProps);
        visitor.visit(tree);
        return cfg;
    }

    /**
     * Visitor-class which constructs the CFG by walking the parse-tree.
     */
    private static class ControlFlowVisitor extends CppBaseVisitor<Void> {

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
        private Deque<String> nameSpaces;
        private String type;
        private boolean isInClass;
        private boolean isInFunction;
        private CFNode endCatchNode;

        public ControlFlowVisitor(ControlFlowGraph cfg, String propKey, Map<ParserRuleContext, Object> ctxProps) {
            preNodes = new ArrayDeque<>();
            preEdges = new ArrayDeque<>();
            loopBlocks = new ArrayDeque<>();
            labeledBlocks = new ArrayList<>();
            tryBlocks = new ArrayDeque<>();
            casesQueue = new ArrayDeque<>();
            classNames = new ArrayDeque<>();
            nameSpaces = new ArrayDeque<>();
            nameSpaces.push("");
            dontPop = false;
            isInClass=false;
            isInFunction=false;
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


            @Override public Void visitPrimaryExpression(CppParser.PrimaryExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitIdExpression(CppParser.IdExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitUnqualifiedId(CppParser.UnqualifiedIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitQualifiedId(CppParser.QualifiedIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNestedNameSpecifier(CppParser.NestedNameSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLambdaExpression(CppParser.LambdaExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLambdaIntroducer(CppParser.LambdaIntroducerContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLambdaCapture(CppParser.LambdaCaptureContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitCaptureDefault(CppParser.CaptureDefaultContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitCaptureList(CppParser.CaptureListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitCapture(CppParser.CaptureContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitSimpleCapture(CppParser.SimpleCaptureContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitInitcapture(CppParser.InitcaptureContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLambdaDeclarator(CppParser.LambdaDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitPostfixExpression(CppParser.PostfixExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTypeIdOfTheTypeId(CppParser.TypeIdOfTheTypeIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitExpressionList(CppParser.ExpressionListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitPseudoDestructorName(CppParser.PseudoDestructorNameContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitUnaryExpression(CppParser.UnaryExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitUnaryOperator(CppParser.UnaryOperatorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNewExpression(CppParser.NewExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNewPlacement(CppParser.NewPlacementContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNewTypeId(CppParser.NewTypeIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNewDeclarator(CppParser.NewDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNoPointerNewDeclarator(CppParser.NoPointerNewDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNewInitializer(CppParser.NewInitializerContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDeleteExpression(CppParser.DeleteExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNoExceptExpression(CppParser.NoExceptExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitCastExpression(CppParser.CastExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitPointerMemberExpression(CppParser.PointerMemberExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMultiplicativeExpression(CppParser.MultiplicativeExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAdditiveExpression(CppParser.AdditiveExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitShiftExpression(CppParser.ShiftExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitShiftOperator(CppParser.ShiftOperatorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitRelationalExpression(CppParser.RelationalExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEqualityExpression(CppParser.EqualityExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAndExpression(CppParser.AndExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitExclusiveOrExpression(CppParser.ExclusiveOrExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitInclusiveOrExpression(CppParser.InclusiveOrExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLogicalAndExpression(CppParser.LogicalAndExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLogicalOrExpression(CppParser.LogicalOrExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitConditionalExpression(CppParser.ConditionalExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAssignmentExpression(CppParser.AssignmentExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAssignmentOperator(CppParser.AssignmentOperatorContext ctx) { return visitChildren(ctx); }

            @Override public Void visitExpression(CppParser.ExpressionContext ctx) {
                //expression: assignmentExpression (Comma assignmentExpression)*;
                //
                //assignmentExpression:
                //	conditionalExpression
                //	| logicalOrExpression assignmentOperator initializerClause
                //	| throwExpression;
                if(ctx.assignmentExpression(0).throwExpression()!=null)
                    return visit(ctx.assignmentExpression(0).throwExpression());
                CFNode statNode=new CFNode();
                statNode.setLineOfCode(ctx.getStart().getLine());
                statNode.setCode(getOriginalCodeText(ctx));
                addNodeAndPreEdge(statNode);

                preNodes.push(statNode);
                preEdges.push(CFEdge.Type.EPSILON);
                return null;
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitConstantExpression(CppParser.ConstantExpressionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitStatement(CppParser.StatementContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLabeledStatement(CppParser.LabeledStatementContext ctx)
            //labeledStatement:
            //	attributeSpecifierSeq? (
            //		Identifier
            //		| Case constantExpression
            //		| Default
            //	) Colon statement;
            {
                // For each visited label-block, a Block object is created with
                // the the current node as the start, and a dummy node as the end.
                // The newly created label-block is stored in an ArrayList of Blocks.
                    CFNode labelNode = new CFNode();
                    labelNode.setLineOfCode(ctx.getStart().getLine());
                if (ctx.Identifier()!=null) {
                    labelNode.setCode(getOriginalCodeText(ctx.attributeSpecifierSeq())+ctx.Identifier().getText()+ ": ");
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
                    labeledBlocks.add(new ControlFlowVisitor.Block(labelNode, endLabelNode, ctx.Identifier().getText()));
                    visit(ctx.statement());
                    popAddPreEdgeTo(endLabelNode);
                    //
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(endLabelNode);
                    return null;}
                else if(ctx.Case()!=null)
                {
                    labelNode.setCode(getOriginalCodeText
                            (ctx.attributeSpecifierSeq())+ctx.Case()+ getOriginalCodeText(ctx.constantExpression())+ ": ");
                    addContextualProperty(labelNode, ctx);
                    addNodeAndPreEdge(labelNode);
                    //
                    CFNode endLabelNode = new CFNode();
                    endLabelNode.setLineOfCode(0);
                    endLabelNode.setCode(" ");
                    cfg.addVertex(endLabelNode);
                    //
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(labelNode);
                    labeledBlocks.add(new ControlFlowVisitor.Block(labelNode, endLabelNode, getOriginalCodeText(ctx.constantExpression())));
                    visit(ctx.statement());
                    popAddPreEdgeTo(endLabelNode);
                    //
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(endLabelNode);
                    return null;
                }
                else if(ctx.Default()!=null)
                {
                    labelNode.setCode(ctx.Default() + ": ");}

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
                    labeledBlocks.add(new ControlFlowVisitor.Block(labelNode, endLabelNode, ctx.Default().getText()));
                    visit(ctx.statement());
                    popAddPreEdgeTo(endLabelNode);
                    //
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(endLabelNode);
                    return null;
                }

            @Override public Void visitExpressionStatement(CppParser.ExpressionStatementContext ctx)
            //expressionStatement: expression? Semi;
            {
                if(ctx.expression()!=null && ctx.expression().assignmentExpression(0).throwExpression()!=null)
                    return visit(ctx.expression());
                CFNode expr = new CFNode();
                expr.setLineOfCode(ctx.getStart().getLine());
                expr.setCode(getOriginalCodeText(ctx));
                //
                Logger.debug(expr.getLineOfCode() + ": " + expr.getCode());
                //
                addContextualProperty(expr, ctx);
                addNodeAndPreEdge(expr);
                //
                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(expr);
                return null;
            }
            @Override public Void visitCompoundStatement(CppParser.CompoundStatementContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitStatementSeq(CppParser.StatementSeqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitSelectionStatement(CppParser.SelectionStatementContext ctx)
            //	If LeftParen condition RightParen statement (Else statement)?
            //	| Switch LeftParen condition RightParen statement;
            {
                {
                    //If LeftParen condition RightParen statement (Else statement)?
                    if (ctx.If() != null) {
                        CFNode IfNode = new CFNode();
                        IfNode.setLineOfCode(ctx.getStart().getLine());
                        IfNode.setCode(ctx.If().getText() + ctx.LeftParen().getText() + getOriginalCodeText(ctx.condition()) + ctx.RightParen().getText());
                        addContextualProperty(IfNode, ctx);
                        addNodeAndPreEdge(IfNode);
                        //
                        preEdges.push(CFEdge.Type.TRUE);
                        preNodes.push(IfNode);
                        //
                        visit(ctx.statement(0));
                        //
                        CFNode endIf = new CFNode();
                        endIf.setLineOfCode(0);
                        endIf.setCode("endif");
                        addNodeAndPreEdge(endIf);
                        //
                        if (ctx.statement().size() == 1) { // if without else
                            cfg.addEdge(new Edge<>(IfNode, new CFEdge(CFEdge.Type.FALSE), endIf));
                        } else {  //  if with else
                            preEdges.push(CFEdge.Type.FALSE);
                            preNodes.push(IfNode);
                            visit(ctx.statement(1));
                            popAddPreEdgeTo(endIf);
                        }
                        preEdges.push(CFEdge.Type.EPSILON);
                        preNodes.push(endIf);
                        return null;
                    }
                }
                if (ctx.Switch() != null)
                {
                    //
                    //Switch LeftParen condition RightParen statement;
                    CFNode switchNode = new CFNode();
                    switchNode.setLineOfCode(ctx.getStart().getLine());
                    switchNode.setCode("switch " + getOriginalCodeText(ctx.condition()));
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
                    loopBlocks.push(new ControlFlowVisitor.Block(switchNode, endSwitch));
                    //
                    CFNode preCase = null;
                    List<CFNode> nullCase=new LinkedList<>();
                    boolean lastIsCase=false;
                    if(ctx.statement(0).compoundStatement()==null)
                        visit(ctx.statement(0));
                    else
                    {
                        // 只能支持case语句要么空要么存在break的CFG分析
                        for (CppParser.StatementContext Selec : ctx.statement(0).compoundStatement().statementSeq().statement())
                        {
                            if(Selec.labeledStatement()!=null && Selec.labeledStatement().Default()!=null)
                            {
                                //labeledStatement:
                                //	attributeSpecifierSeq? (
                                //		| Default
                                //	) Colon statement;
                                CFNode defNode=new CFNode();
                                defNode.setLineOfCode(Selec.getStart().getLine());
                                defNode.setCode("default");
                                if(preCase==null)
                                    addNodeAndPreEdge(defNode);
                                else
                                {
                                    cfg.addVertex(defNode);
                                    cfg.addEdge(new Edge<>(preCase,new CFEdge(CFEdge.Type.FALSE),defNode));
                                }
                                preNodes.push(defNode);
                                preEdges.push(CFEdge.Type.EPSILON);
                                visit(Selec.labeledStatement().statement());
                                lastIsCase=false;
                            }
                            else if(Selec.labeledStatement()!=null && Selec.labeledStatement().Case()!=null)
                            {
                                //labeledStatement:
                                //	attributeSpecifierSeq? (
                                //		| Case constantExpression
                                //	) Colon statement;
                                CppParser.LabeledStatementContext caseCtx=Selec.labeledStatement();
                                Selec=null;
                                while(caseCtx.Case()!=null)
                                {
                                    CFNode caseNode=new CFNode();
                                    caseNode.setLineOfCode(caseCtx.getStart().getLine());
                                    caseNode.setCode("case "+getOriginalCodeText(caseCtx.constantExpression()));
                                    if(preCase==null)
                                        addNodeAndPreEdge(caseNode);
                                    else
                                    {
                                        cfg.addVertex(caseNode);
                                        cfg.addEdge(new Edge<>(preCase,new CFEdge(CFEdge.Type.FALSE),caseNode));
                                    }

                                    preCase=caseNode;
                                    lastIsCase=true;

                                    if(caseCtx.statement().labeledStatement()!=null && caseCtx.statement().labeledStatement().Case()!=null)
                                    {
                                        // case的下一个语句还是case
                                        preNodes.push(caseNode);
                                        preEdges.push(CFEdge.Type.FALSE);
                                        nullCase.add(caseNode);
                                        caseCtx=caseCtx.statement().labeledStatement();
                                    }
                                    else if(caseCtx.statement().labeledStatement()!=null && caseCtx.statement().labeledStatement().Default()!=null)
                                    {
                                        // case的下一个语句是default
                                        preNodes.push(caseNode);
                                        preEdges.push(CFEdge.Type.FALSE);
                                        nullCase.add(caseNode);
                                        caseCtx=caseCtx.statement().labeledStatement();
                                    }
                                    else
                                    {
                                        // case的下一个语句是非case,default语句
                                        preNodes.push(caseNode);
                                        preEdges.push(CFEdge.Type.TRUE);
                                        nullCase.add(caseNode);
                                        Selec=caseCtx.statement();
                                        break;
                                    }
                                }

                                if(Selec!=null)
                                {
                                    // 非case,default语句
                                    if(!nullCase.isEmpty())
                                    {
                                        pushNodeToCaseList(nullCase);
                                        nullCase.clear();
                                        dontPop=true;
                                    }
                                    visit(Selec);
                                }
                                else
                                {
                                    // default语句
                                    CFNode defNode=new CFNode();
                                    defNode.setLineOfCode(caseCtx.getStart().getLine());
                                    defNode.setCode("default");
                                    if(preCase==null)
                                        addNodeAndPreEdge(defNode);
                                    else
                                    {
                                        cfg.addVertex(defNode);
                                        cfg.addEdge(new Edge<>(preCase,new CFEdge(CFEdge.Type.FALSE),defNode));
                                    }
                                    preNodes.push(defNode);
                                    preEdges.push(CFEdge.Type.EPSILON);
                                    visit(caseCtx.statement());
                                }
                                lastIsCase=false;
                            }
                            else
                            {
                                //非case,default语句
                                if(!nullCase.isEmpty())
                                {
                                    pushNodeToCaseList(nullCase);
                                    nullCase.clear();
                                    dontPop=true;
                                }
                                visit(Selec);
                                lastIsCase=false;
                            }

                        }
                    }

                    loopBlocks.pop();
                    popAddPreEdgeTo(endSwitch);
                    //
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(endSwitch);
                    return null;
                }
                return null;
            }


            @Override public Void visitCondition(CppParser.ConditionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            //todo
            @Override public Void visitIterationStatement(CppParser.IterationStatementContext ctx)
            //iterationStatement:
            //	While LeftParen condition RightParen statement
            //	| Do statement While LeftParen expression RightParen Semi
            //	| For LeftParen (
            //		forInitStatement condition? Semi expression?
            //		| forRangeDeclaration Colon forRangeInitializer
            //	) RightParen statement;
            {
                if (ctx.While() != null && ctx.Do()==null) {
                    //While LeftParen condition RightParen statement
                    CFNode whileNode = new CFNode();
                    whileNode.setLineOfCode(ctx.getStart().getLine());
                    whileNode.setCode("while (" + getOriginalCodeText(ctx.condition())+")");
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
                    loopBlocks.push(new ControlFlowVisitor.Block(whileNode, endwhile));
                    visit(ctx.statement());
                    loopBlocks.pop();
                    popAddPreEdgeTo(whileNode);
                    //
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(endwhile);
                    return null;
                }
                if (ctx.Do() != null) {
                    // 'do' statement 'while' parExpression ';'
                    //Do statement While LeftParen expression RightParen Semi
                    CFNode doNode = new CFNode();
                    doNode.setLineOfCode(ctx.getStart().getLine());
                    doNode.setCode("Do");
                    addNodeAndPreEdge(doNode);
                    //
                    CFNode whileNode = new CFNode();
                    whileNode.setLineOfCode(ctx.expression().getStart().getLine());
                    whileNode.setCode("while (" + getOriginalCodeText(ctx.expression())+")"+ctx.Semi());
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
                    loopBlocks.push(new ControlFlowVisitor.Block(whileNode, doWhileEnd));
                    visit(ctx.statement());
                    loopBlocks.pop();
                    popAddPreEdgeTo(whileNode);
                    cfg.addEdge(new Edge<>(whileNode, new CFEdge(CFEdge.Type.TRUE), doNode));
                    cfg.addEdge(new Edge<>(whileNode, new CFEdge(CFEdge.Type.FALSE), doWhileEnd));
                    //
                    preEdges.push(CFEdge.Type.EPSILON);
                    preNodes.push(doWhileEnd);
                    return null;
                } else if (ctx.For() != null) {
                    //For LeftParen (
                    //		forInitStatement condition? Semi expression?
                    //		| forRangeDeclaration Colon forRangeInitializer
                    //	) RightParen statement;


                    //  First, we should check forRange ...
                    //forRangeDeclaration Colon forRangeInitializer
                    if (ctx.forRangeDeclaration() != null) {
                        CFNode forRange = new CFNode();
                        forRange.setLineOfCode(ctx.forRangeDeclaration().getStart().getLine());
                        forRange.setCode("For (" + getOriginalCodeText(ctx.forRangeDeclaration())+":"+getOriginalCodeText(ctx.forRangeInitializer()) + ")");
                        addContextualProperty(forRange, ctx.forRangeDeclaration());
                        addNodeAndPreEdge(forRange);
                        //
                        CFNode forEnd = new CFNode();
                        forEnd.setLineOfCode(0);
                        forEnd.setCode("endfor");
                        cfg.addVertex(forEnd);
                        cfg.addEdge(new Edge<>(forRange, new CFEdge(CFEdge.Type.FALSE), forEnd));
                        //
                        preEdges.push(CFEdge.Type.TRUE);
                        preNodes.push(forRange);
                        //
                        loopBlocks.push(new ControlFlowVisitor.Block(forRange, forEnd));
                        visit(ctx.statement());
                        loopBlocks.pop();
                        popAddPreEdgeTo(forRange);
                        //
                        preEdges.push(CFEdge.Type.EPSILON);
                        preNodes.push(forEnd);
                    } else {
                        // It's a traditional for-loop:
                        // forInitStatement condition? Semi expression?

                        CFNode forInit = null;
                        if (ctx.forInitStatement() != null) { // non-empty init
                            forInit = new CFNode();
                            forInit.setLineOfCode(ctx.forInitStatement().getStart().getLine());
                            forInit.setCode(getOriginalCodeText(ctx.forInitStatement()));
                            addContextualProperty(forInit, ctx.forInitStatement());
                            addNodeAndPreEdge(forInit);
                        }
                        // for-condition
                        CFNode forcondition = new CFNode();
                        if (ctx.condition() == null) {
                            forcondition.setLineOfCode(ctx.forInitStatement().getStart().getLine());
                            forcondition.setCode("for ( ; )");
                        } else {
                            forcondition.setLineOfCode(ctx.condition().getStart().getLine());
                            forcondition.setCode("for (" + getOriginalCodeText(ctx.condition()) + ")");
                        }
                        addContextualProperty(forcondition, ctx.condition());
                        cfg.addVertex(forcondition);
                        if (forInit != null)
                            cfg.addEdge(new Edge<>(forInit, new CFEdge(CFEdge.Type.EPSILON), forcondition));
                        else
                            popAddPreEdgeTo(forcondition);
                        // expression
                        CFNode forExpr = new CFNode();
                        if (ctx.expression() == null) { // empty forExprpression
                            forExpr.setCode(" ; ");
                            forExpr.setLineOfCode(ctx.forInitStatement().getStart().getLine());
                        } else {
                            forExpr.setCode(getOriginalCodeText(ctx.expression()));
                            forExpr.setLineOfCode(ctx.expression().getStart().getLine());
                        }
                        addContextualProperty(forExpr, ctx.expression());
                        cfg.addVertex(forExpr);
                        //
                        CFNode forEnd = new CFNode();
                        forEnd.setLineOfCode(0);
                        forEnd.setCode("endfor");
                        cfg.addVertex(forEnd);
                        cfg.addEdge(new Edge<>(forcondition, new CFEdge(CFEdge.Type.FALSE), forEnd));
                        //
                        preEdges.push(CFEdge.Type.TRUE);
                        preNodes.push(forcondition);
                        loopBlocks.push(new ControlFlowVisitor.Block(forExpr, forEnd)); // NOTE: start is 'forexpr'
                        visit(ctx.statement());
                        loopBlocks.pop();
                        popAddPreEdgeTo(forExpr);
                        cfg.addEdge(new Edge<>(forExpr, new CFEdge(CFEdge.Type.EPSILON), forcondition));
                        //
                        preEdges.push(CFEdge.Type.EPSILON);
                        preNodes.push(forEnd);
                    }
                    return null;
                }
                return null;
            }

            @Override public Void visitForInitStatement(CppParser.ForInitStatementContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitForRangeDeclaration(CppParser.ForRangeDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitForRangeInitializer(CppParser.ForRangeInitializerContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitJumpStatement(CppParser.JumpStatementContext ctx)
            //jumpStatement:
            //	(
            //		Break
            //		| Continue
            //		| Return (expression | bracedInitList)?
            //		| Goto Identifier
            //	) Semi;
            //
            {if (ctx.Break() != null) {
                // if a label is specified, search for the corresponding block in the labels-list,
                // and create an epsilon edge to the end of the labeled-block; else
                // create an epsilon edge to the end of the loop-block on top of the loopBlocks stack.
                CFNode breakNode = new CFNode();
                breakNode.setLineOfCode(ctx.getStart().getLine());
                breakNode.setCode(getOriginalCodeText(ctx));
                addContextualProperty(breakNode, ctx);
                addNodeAndPreEdge(breakNode);
                    // no label
                    ControlFlowVisitor.Block block = loopBlocks.peek();
                    cfg.addEdge(new Edge<>(breakNode, new CFEdge(CFEdge.Type.EPSILON), block.end));

                dontPop = true;
                return null;
            }
            //Continue
                else if (ctx.Continue() != null) {
                // if a label is specified, search for the corresponding block in the labels-list,
                // and create an epsilon edge to the start of the labeled-block; else
                // create an epsilon edge to the start of the loop-block on top of the loopBlocks stack.
                CFNode continueNode = new CFNode();
                continueNode.setLineOfCode(ctx.getStart().getLine());
                continueNode.setCode(getOriginalCodeText(ctx));
                addContextualProperty(continueNode, ctx);
                addNodeAndPreEdge(continueNode);
                    // no label
                    ControlFlowVisitor.Block block = loopBlocks.peek();
                    cfg.addEdge(new Edge<>(continueNode, new CFEdge(CFEdge.Type.EPSILON), block.start));

                dontPop = true;
                return null;
            }
                //Return (expression | bracedInitList)?
            else if (ctx.Return() != null){
                CFNode ret = new CFNode();
                ret.setLineOfCode(ctx.getStart().getLine());
                ret.setCode(getOriginalCodeText(ctx));
                addContextualProperty(ret, ctx);
                addNodeAndPreEdge(ret);
                dontPop = true;
                return null;
            }

                // Goto Identifier
            else if (ctx.Goto() != null){
                CFNode gotoNode = new CFNode();
                gotoNode.setLineOfCode(ctx.getStart().getLine());
                gotoNode.setCode("goto:  "+ctx.Identifier().getText());
                addContextualProperty(gotoNode, ctx);
                addNodeAndPreEdge(gotoNode);
                    // a label is specified
                    for (ControlFlowVisitor.Block block: labeledBlocks) {
                        if (block.label.equals(ctx.Identifier().getText())) {
                            cfg.addEdge(new Edge<>(gotoNode, new CFEdge(CFEdge.Type.EPSILON), block.end));
                            break;
                        }
                    }
                dontPop = true;
                return null;
            }
                return null;
    }
            @Override public Void visitDeclarationStatement(CppParser.DeclarationStatementContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDeclarationseq(CppParser.DeclarationseqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDeclaration(CppParser.DeclarationContext ctx) { return visitChildren(ctx); }

            @Override public Void visitBlockDeclaration(CppParser.BlockDeclarationContext ctx) {
                if(ctx.simpleDeclaration()!=null)
                    return visit(ctx.simpleDeclaration());
                if(isInFunction)
                {
                    CFNode statNode=new CFNode();
                    statNode.setLineOfCode(ctx.getStart().getLine());
                    statNode.setCode(getOriginalCodeText(ctx));
                    addNodeAndPreEdge(statNode);

                    preNodes.push(statNode);
                    preEdges.push(CFEdge.Type.EPSILON);
                }
                return null;
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAliasDeclaration(CppParser.AliasDeclarationContext ctx) { return visitChildren(ctx); }

            @Override public Void visitSimpleDeclaration(CppParser.SimpleDeclarationContext ctx) {
                //simpleDeclaration:
                //	declSpecifierSeq? initDeclaratorList? Semi
                //	| attributeSpecifierSeq declSpecifierSeq? initDeclaratorList Semi;
                if(ctx.declSpecifierSeq()!=null && ctx.initDeclaratorList()==null)
                {
                    // 可能类定义
                    visit(ctx.declSpecifierSeq());
                    return null;
                }
                else if(isInFunction)
                {
                    // 变量或函数声明
                    // 还有可能是函数调用(是有用命名空间指定的函数调用，如std::min())
                    // or
                    //变量赋值或者函数调用

                    CFNode statNode=new CFNode();
                    statNode.setLineOfCode(ctx.getStart().getLine());
                    statNode.setCode(getOriginalCodeText(ctx));
                    addNodeAndPreEdge(statNode);

                    preNodes.push(statNode);
                    preEdges.push(CFEdge.Type.EPSILON);
                }
                return null;
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitStaticAssertDeclaration(CppParser.StaticAssertDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEmptyDeclaration(CppParser.EmptyDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAttributeDeclaration(CppParser.AttributeDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDeclSpecifier(CppParser.DeclSpecifierContext ctx) { return visitChildren(ctx); }

            @Override public Void visitDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx) {
                // declSpecifierSeq: declSpecifier+? attributeSpecifierSeq?;
                //
                // declSpecifier:storageClassSpecifier | typeSpecifier| functionSpecifier| Friend| Typedef | Constexpr;
                return visitChildren(ctx);
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitStorageClassSpecifier(CppParser.StorageClassSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitFunctionSpecifier(CppParser.FunctionSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTypedefName(CppParser.TypedefNameContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTypeSpecifier(CppParser.TypeSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTrailingTypeSpecifier(CppParser.TrailingTypeSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTypeSpecifierSeq(CppParser.TypeSpecifierSeqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTrailingTypeSpecifierSeq(CppParser.TrailingTypeSpecifierSeqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitSimpleTypeLengthModifier(CppParser.SimpleTypeLengthModifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitSimpleTypeSignednessModifier(CppParser.SimpleTypeSignednessModifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitSimpleTypeSpecifier(CppParser.SimpleTypeSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTheTypeName(CppParser.TheTypeNameContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDecltypeSpecifier(CppParser.DecltypeSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitElaboratedTypeSpecifier(CppParser.ElaboratedTypeSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumName(CppParser.EnumNameContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumSpecifier(CppParser.EnumSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumHead(CppParser.EnumHeadContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitOpaqueEnumDeclaration(CppParser.OpaqueEnumDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumkey(CppParser.EnumkeyContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumbase(CppParser.EnumbaseContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumeratorList(CppParser.EnumeratorListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumeratorDefinition(CppParser.EnumeratorDefinitionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitEnumerator(CppParser.EnumeratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNamespaceName(CppParser.NamespaceNameContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitOriginalNamespaceName(CppParser.OriginalNamespaceNameContext ctx) { return visitChildren(ctx); }

            @Override public Void visitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx) {
                //namespaceDefinition:
                //	Inline? Namespace (Identifier | originalNamespaceName)? LeftBrace namespaceBody = declarationseq
                //		? RightBrace;
                if(ctx.Identifier()!=null || ctx.originalNamespaceName()!=null)
                {
                    nameSpaces.push(nameSpaces.peek()+"::"+ (ctx.Identifier()!=null?ctx.Identifier().getText()
                            :ctx.originalNamespaceName().Identifier().getText()));
                    visitChildren(ctx);
                    nameSpaces.pop();
                }
                else
                    visitChildren(ctx);

                return null;
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNamespaceAlias(CppParser.NamespaceAliasContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitQualifiednamespacespecifier(CppParser.QualifiednamespacespecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitUsingDeclaration(CppParser.UsingDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitUsingDirective(CppParser.UsingDirectiveContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAsmDefinition(CppParser.AsmDefinitionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLinkageSpecification(CppParser.LinkageSpecificationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAttributeSpecifierSeq(CppParser.AttributeSpecifierSeqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAttributeSpecifier(CppParser.AttributeSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAlignmentspecifier(CppParser.AlignmentspecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAttributeList(CppParser.AttributeListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAttribute(CppParser.AttributeContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAttributeNamespace(CppParser.AttributeNamespaceContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAttributeArgumentClause(CppParser.AttributeArgumentClauseContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBalancedTokenSeq(CppParser.BalancedTokenSeqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBalancedtoken(CppParser.BalancedtokenContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitInitDeclaratorList(CppParser.InitDeclaratorListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitInitDeclarator(CppParser.InitDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDeclarator(CppParser.DeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitPointerDeclarator(CppParser.PointerDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNoPointerDeclarator(CppParser.NoPointerDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitParametersAndQualifiers(CppParser.ParametersAndQualifiersContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTrailingReturnType(CppParser.TrailingReturnTypeContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitPointerOperator(CppParser.PointerOperatorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitCvqualifierseq(CppParser.CvqualifierseqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitCvQualifier(CppParser.CvQualifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitRefqualifier(CppParser.RefqualifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDeclaratorid(CppParser.DeclaratoridContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTheTypeId(CppParser.TheTypeIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAbstractDeclarator(CppParser.AbstractDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitPointerAbstractDeclarator(CppParser.PointerAbstractDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNoPointerAbstractDeclarator(CppParser.NoPointerAbstractDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAbstractPackDeclarator(CppParser.AbstractPackDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNoPointerAbstractPackDeclarator(CppParser.NoPointerAbstractPackDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitParameterDeclarationClause(CppParser.ParameterDeclarationClauseContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitParameterDeclarationList(CppParser.ParameterDeclarationListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitParameterDeclaration(CppParser.ParameterDeclarationContext ctx) { return visitChildren(ctx); }

            @Override public Void visitFunctionDefinition(CppParser.FunctionDefinitionContext ctx){
                //functionDefinition:
                //	attributeSpecifierSeq? declSpecifierSeq? declarator virtualSpecifierSeq? functionBody;
                init();
                if(ctx.functionBody().Assign()!=null)
                    return null;
                isInFunction=true;

                CFNode funcNode=new CFNode();
                funcNode.setLineOfCode(ctx.getStart().getLine());

                addContextualProperty(funcNode,ctx);
                cfg.addVertex(funcNode);

                String retype="void";
                String name="";
                String args="";
                String func="";
                if(ctx.declSpecifierSeq()!=null)
                {
                    getTypeForDeclSpecifierSeq(ctx.declSpecifierSeq());
                    if(type!="")
                        retype=type;
                    func=getOriginalCodeText(ctx.declSpecifierSeq());
                }

                //declarator:
                //	pointerDeclarator
                //	| noPointerDeclarator parametersAndQualifiers trailingReturnType;
                CppParser.DeclaratorContext declCtx=ctx.declarator();
                if(declCtx.trailingReturnType()!=null)
                {
                    //trailingReturnType:
                    //	Arrow trailingTypeSpecifierSeq abstractDeclarator?;
                    retype=getOriginalCodeText(declCtx.trailingReturnType().trailingTypeSpecifierSeq());
                    name=getOriginalCodeText(declCtx.pointerDeclarator());
                    args=getOriginalCodeText(declCtx.parametersAndQualifiers());
                }
                else
                {
                    //pointerDeclarator: (pointerOperator Const?)* noPointerDeclarator;
                    for(int i=0;i<declCtx.children.size()-1;++i)
                        retype+=declCtx.getChild(i).getText();
                    name=getOriginalCodeText(declCtx.pointerDeclarator().noPointerDeclarator().noPointerDeclarator());
                    args=getOriginalCodeText(declCtx.pointerDeclarator().noPointerDeclarator().parametersAndQualifiers());
                }
                funcNode.setCode(func+" "+getOriginalCodeText(ctx.declarator())+
                        getOriginalCodeText(ctx.virtualSpecifierSeq()));
                funcNode.setProperty("name",name);
                funcNode.setProperty("namespace",nameSpaces.peek());
                funcNode.setProperty("type",retype);
                if(isInClass)
                    funcNode.setProperty("class",classNames.peek());

                cfg.addMethodEntry(funcNode);

                preNodes.push(funcNode);
                preEdges.push(CFEdge.Type.EPSILON);
                visit(ctx.functionBody());
                isInFunction=false;
                return null;
            }


            @Override public Void visitFunctionBody(CppParser.FunctionBodyContext ctx) { return visitChildren(ctx); }
            //functionBody:
            //	constructorInitializer? compoundStatement
            //	| functionTryBlock
            //	| Assign (Default | Delete) Semi;

            @Override public Void visitInitializer(CppParser.InitializerContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBraceOrEqualInitializer(CppParser.BraceOrEqualInitializerContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitInitializerClause(CppParser.InitializerClauseContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitInitializerList(CppParser.InitializerListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBracedInitList(CppParser.BracedInitListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitClassName(CppParser.ClassNameContext ctx) { return visitChildren(ctx); }

            @Override public Void visitClassSpecifier(CppParser.ClassSpecifierContext ctx) {
                boolean old=isInClass;
                if(ctx.classHead().classHeadName()!=null)
                    classNames.push(getOriginalCodeText(ctx.classHead().classHeadName()));
                else
                    classNames.push("");
                isInClass=true;
                visitChildren(ctx);
                classNames.pop();
                isInClass=old;
                return null;
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitClassHead(CppParser.ClassHeadContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitClassHeadName(CppParser.ClassHeadNameContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitClassVirtSpecifier(CppParser.ClassVirtSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitClassKey(CppParser.ClassKeyContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMemberSpecification(CppParser.MemberSpecificationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMemberdeclaration(CppParser.MemberdeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMemberDeclaratorList(CppParser.MemberDeclaratorListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMemberDeclarator(CppParser.MemberDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitVirtualSpecifierSeq(CppParser.VirtualSpecifierSeqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitVirtualSpecifier(CppParser.VirtualSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitPureSpecifier(CppParser.PureSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBaseClause(CppParser.BaseClauseContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBaseSpecifierList(CppParser.BaseSpecifierListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBaseSpecifier(CppParser.BaseSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitClassOrDeclType(CppParser.ClassOrDeclTypeContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBaseTypeSpecifier(CppParser.BaseTypeSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAccessSpecifier(CppParser.AccessSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitConversionFunctionId(CppParser.ConversionFunctionIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitConversionTypeId(CppParser.ConversionTypeIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitConversionDeclarator(CppParser.ConversionDeclaratorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitConstructorInitializer(CppParser.ConstructorInitializerContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMemInitializerList(CppParser.MemInitializerListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMemInitializer(CppParser.MemInitializerContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitMeminitializerid(CppParser.MeminitializeridContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitOperatorFunctionId(CppParser.OperatorFunctionIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLiteralOperatorId(CppParser.LiteralOperatorIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTemplateDeclaration(CppParser.TemplateDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTemplateparameterList(CppParser.TemplateparameterListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTemplateParameter(CppParser.TemplateParameterContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTypeParameter(CppParser.TypeParameterContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitSimpleTemplateId(CppParser.SimpleTemplateIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTemplateId(CppParser.TemplateIdContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTemplateName(CppParser.TemplateNameContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTemplateArgumentList(CppParser.TemplateArgumentListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTemplateArgument(CppParser.TemplateArgumentContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTypeNameSpecifier(CppParser.TypeNameSpecifierContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitExplicitInstantiation(CppParser.ExplicitInstantiationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitExplicitSpecialization(CppParser.ExplicitSpecializationContext ctx) { return visitChildren(ctx); }

            @Override public Void visitTryBlock(CppParser.TryBlockContext ctx) {
                //tryBlock: Try compoundStatement handlerSeq;
                //compoundStatement: LeftBrace statementSeq? RightBrace;
                //handlerSeq: handler+;
                //handler:Catch LeftParen exceptionDeclaration RightParen compoundStatement;
                CFNode tryNode = new CFNode();
                tryNode.setLineOfCode(ctx.getStart().getLine());
                tryNode.setCode("try");
                addContextualProperty(tryNode, ctx);
                addNodeAndPreEdge(tryNode);

                CFNode endTry = new CFNode();
                endTry.setLineOfCode(0);
                endTry.setCode("end-try");
                cfg.addVertex(endTry);

                tryBlocks.push(new Block(tryNode,endTry));
                preNodes.push(tryNode);
                preEdges.push(CFEdge.Type.EPSILON);
                visit(ctx.compoundStatement());
                popAddPreEdgeTo(endTry);
                tryBlocks.pop();

                endCatchNode=new CFNode();
                endCatchNode.setLineOfCode(0);
                endCatchNode.setCode("end-catch");
                cfg.addVertex(endCatchNode);

                preEdges.push(CFEdge.Type.THROWS);
                preNodes.push(tryNode);
                visit(ctx.handlerSeq());
                preEdges.pop();
                preNodes.pop();

                cfg.addEdge(new Edge<>(endCatchNode,new CFEdge(CFEdge.Type.EPSILON),endTry));
                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(endTry);
                return null;
            }
            @Override public Void visitFunctionTryBlock(CppParser.FunctionTryBlockContext ctx) {
                //functionTryBlock:
                //	Try constructorInitializer? compoundStatement handlerSeq;
                CFNode tryNode=new CFNode();
                tryNode.setLineOfCode(ctx.getStart().getLine());
                tryNode.setCode("try "+getOriginalCodeText(ctx.constructorInitializer()));
                addContextualProperty(tryNode,ctx);
                addNodeAndPreEdge(tryNode);
                preNodes.push(tryNode);
                preEdges.push(CFEdge.Type.EPSILON);

                CFNode endTryNode=new CFNode();
                endTryNode.setLineOfCode(0);
                endTryNode.setCode("end-try");
                addNodeAndPreEdge(endTryNode);

                endCatchNode=new CFNode();
                endCatchNode.setLineOfCode(0);
                endCatchNode.setCode("end-catch");
                cfg.addVertex(endCatchNode);

                preNodes.push(tryNode);
                preEdges.push(CFEdge.Type.THROWS);
                visit(ctx.handlerSeq());
                cfg.addEdge(new Edge<>(endCatchNode,new CFEdge(CFEdge.Type.EPSILON),endTryNode));

                preNodes.push(endTryNode);
                preEdges.push(CFEdge.Type.EPSILON);

                visit(ctx.compoundStatement());


                return null;
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitHandlerSeq(CppParser.HandlerSeqContext ctx) { return visitChildren(ctx); }

            @Override public Void visitHandler(CppParser.HandlerContext ctx) {
                //handler:
                //	Catch LeftParen exceptionDeclaration RightParen compoundStatement;
                CFNode catchNode=new CFNode();
                catchNode.setLineOfCode(ctx.getStart().getLine());
                catchNode.setCode("catch("+getOriginalCodeText(ctx.exceptionDeclaration())+")");
                addContextualProperty(catchNode,ctx);

                cfg.addVertex(catchNode);
                cfg.addEdge(new Edge<>(preNodes.peek(),new CFEdge(preEdges.peek()),catchNode));
                preNodes.push(catchNode);
                preEdges.push(CFEdge.Type.EPSILON);

                visit(ctx.compoundStatement());
                popAddPreEdgeTo(endCatchNode);
                return null;
            }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitExceptionDeclaration(CppParser.ExceptionDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitThrowExpression(CppParser.ThrowExpressionContext ctx)
            //throwExpression: Throw assignmentExpression?;
            {
                CFNode throwNode = new CFNode();
                throwNode.setLineOfCode(ctx.getStart().getLine());
                throwNode.setCode(ctx.Throw() + " " + getOriginalCodeText(ctx.assignmentExpression()));
                addContextualProperty(throwNode, ctx);
                addNodeAndPreEdge(throwNode);
                //
                if (!tryBlocks.isEmpty()) {
                    ControlFlowVisitor.Block tryBlock = tryBlocks.peek();
                    cfg.addEdge(new Edge<>(throwNode, new CFEdge(CFEdge.Type.THROWS), tryBlock.end));
                } else {
                    // do something when it's a throw not in a try-catch block ...
                    // in such a situation, the method declaration has a throws clause;
                    // so we should create a special node for the method-throws,
                    // and create an edge from this throw-statement to that throws-node.
                }
                dontPop = true;
                return null;}

            @Override public Void visitExceptionSpecification(CppParser.ExceptionSpecificationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDynamicExceptionSpecification(CppParser.DynamicExceptionSpecificationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTypeIdList(CppParser.TypeIdListContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNoeExceptSpecification(CppParser.NoeExceptSpecificationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTheOperator(CppParser.TheOperatorContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitLiteral(CppParser.LiteralContext ctx) { return visitChildren(ctx); }



        private void pushNodeToCaseList(List<CFNode> nullCase)
        {
            casesQueue.clear();
            for(CFNode caseNode:nullCase)
                casesQueue.add(caseNode);
        }

        /**
         * Get resulting Control-Flow-Graph of this CFG-Builder.
         */
        public ControlFlowGraph getCFG() {
            return cfg;
        }

        /**
         * Add this node to the CFG and create edge from pre-node to this node.
         */
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
                cfg.addEdge(new Edge<>(preNodes.pop(), new CFEdge(preEdges.pop()), node));
            }
            //
            for (int i = casesQueue.size(); i > 0; --i)
                cfg.addEdge(new Edge<>(casesQueue.remove(), new CFEdge(CFEdge.Type.TRUE), node));
        }

        /**
         * Get the original program text for the given parser-rule context.
         * This is required for preserving whitespaces.
         */
        private String getOriginalCodeText(ParserRuleContext ctx) {
            if(ctx==null)
                return "";
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
        }

        private void getTypeForDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx)
        {
            type="";
            if(ctx.declSpecifier()!=null)
            {
                for(CppParser.DeclSpecifierContext decSpCtx:ctx.declSpecifier())
                {
                    if(decSpCtx.typeSpecifier()!=null)
                    {
                        type+=getOriginalCodeText(decSpCtx.typeSpecifier())+" ";
                    }
                }
            }
        }

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
