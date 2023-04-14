package com.scut.mplas.cpp;

import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import com.scut.mplas.cpp.parser.CppVisitor;
import com.scut.mplas.graphs.cfg.CFEdge;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.java.JavaCFGBuilder;
import com.scut.mplas.java.parser.JavaParser;
import ghaffarian.graphs.*;
import ghaffarian.nanologger.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
        if (!cppFile.getName().endsWith(".java"))
            throw new IOException("Not a Cpp File!");
        InputStream inFile = new FileInputStream(cppFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser.translationUnit();
        return build(cppFile.getPath(), tree, null, null);
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
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitExpression(CppParser.ExpressionContext ctx) { return visitChildren(ctx); }
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
            @Override public Void visitLabeledStatement(CppParser.LabeledStatementContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitExpressionStatement(CppParser.ExpressionStatementContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
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
                    if (ctx.If() != null){
                    CFNode IfNode = new CFNode();
                        IfNode.setLineOfCode(ctx.getStart().getLine());
                        IfNode.setCode(ctx.If().getText()+ctx.LeftParen().getText()+getOriginalCodeText(ctx.condition())+ctx.RightParen().getText());
                    addContextualProperty(IfNode, ctx);
                    addNodeAndPreEdge(IfNode);
                    //
                    preEdges.push(CFEdge.Type.TRUE);
                    preNodes.push(IfNode);
                    //
                    visit(ctx.statement(0));
                    //
                    CFNode endIf= new CFNode();
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
                if (ctx.Switch() != null) {
                    //
                    //Switch LeftParen condition RightParen statement;
                    CFNode switchNode = new CFNode();
                    switchNode.setLineOfCode(ctx.getStart().getLine());
                    switchNode.setCode(ctx.Switch().getText() + ctx.LeftParen().getText() + getOriginalCodeText(ctx.condition()) + ctx.RightParen().getText());
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
                        visit(ctx.statement(0));
                    loopBlocks.pop();
                    popAddPreEdgeTo(endSwitch);

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
            @Override public Void visitIterationStatement(CppParser.IterationStatementContext ctx) { return visitChildren(ctx); }
            //iterationStatement:
            //	While LeftParen condition RightParen statement
            //	| Do statement While LeftParen expression RightParen Semi
            //	| For LeftParen (
            //		forInitStatement condition? Semi expression?
            //		| forRangeDeclaration Colon forRangeInitializer
            //	) RightParen statement;

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
                gotoNode.setCode(getOriginalCodeText(ctx));
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
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitBlockDeclaration(CppParser.BlockDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitAliasDeclaration(CppParser.AliasDeclarationContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitSimpleDeclaration(CppParser.SimpleDeclarationContext ctx) { return visitChildren(ctx); }
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
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx) { return visitChildren(ctx); }
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
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx) { return visitChildren(ctx); }
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
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitFunctionDefinition(CppParser.FunctionDefinitionContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitFunctionBody(CppParser.FunctionBodyContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
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
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitClassSpecifier(CppParser.ClassSpecifierContext ctx) { return visitChildren(ctx); }
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
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitTryBlock(CppParser.TryBlockContext ctx) {
                //tryBlock: Try compoundStatement handlerSeq;
                //compoundStatement: LeftBrace statementSeq? RightBrace;
                //handlerSeq: handler+;
                //handler:Catch LeftParen exceptionDeclaration RightParen compoundStatement;
                CFNode tryNode = new CFNode();
                tryNode.setLineOfCode(ctx.getStart().getLine());
                tryNode.setCode(ctx.Try().getText()+getOriginalCodeText(ctx.compoundStatement()));
                addContextualProperty(tryNode, ctx);
                addNodeAndPreEdge(tryNode);
                //
                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(tryNode);
                visit(ctx.handlerSeq());
                //
                CFNode endTry = new CFNode();
                endTry.setLineOfCode(0);
                endTry.setCode("end-try");
                addNodeAndPreEdge(endTry);

                //
                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(endTry);
                return null;
            }
            @Override public Void visitFunctionTryBlock(CppParser.FunctionTryBlockContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitHandlerSeq(CppParser.HandlerSeqContext ctx) { return visitChildren(ctx); }
            /**
             * {@inheritDoc}
             *
             * <p>The default implementation returns the result of calling
             * {@link #visitChildren} on {@code ctx}.</p>
             */
            @Override public Void visitHandler(CppParser.HandlerContext ctx) { return visitChildren(ctx); }
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
                throwNode.setCode(ctx.Throw() + getOriginalCodeText(ctx.assignmentExpression()));
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
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
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
