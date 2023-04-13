package com.scut.mplas.cpp;

import com.scut.mplas.cpp.parser.CppVisitor;
import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import ghaffarian.nanologger.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;


import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * Abstract Syntax Tree (AST) builder for CPP programs.
 * A CPP parser generated via ANTLRv4 is used for this purpose.
 * This implementation is based on ANTLRv4's Visitor pattern.
 */

public class CppASTBuilder {
    public static AbstractSyntaxTree build(String cppFile) throws IOException {
        return build(new File(cppFile));
    }

    /**
     * Build and return the Abstract Syntax Tree (AST) for the given Cpp source file.
     */

    public static AbstractSyntaxTree build(File cppFile) throws IOException {
        if (!cppFile.getName().endsWith(".cpp"))
            throw new IOException("Not a Cpp File!");
        InputStream inFile = new FileInputStream(cppFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser.translationUnit();
        return build(cppFile.getPath(), tree, null, null);
    }

    public static AbstractSyntaxTree build(String fileName, InputStream inputStream) throws IOException {

        InputStream inFile = inputStream;
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser.translationUnit();
        return build(fileName, tree, null, null);
    }

    /**
     * Build and return the Abstract Syntax Tree (AST) for the given Parse-Tree.
     * The 'ctxProps' map includes contextual-properties for particular nodes
     * in the parse-tree, which can be used for linking this graph with other
     * graphs by using the same parse-tree and the same contextual-properties.
     */

    public static AbstractSyntaxTree build(String filePath, ParseTree tree,
                                           String propKey, Map<ParserRuleContext, Object> ctxProps) {
        CppASTBuilder.AbstractSyntaxVisitor visitor = new CppASTBuilder.AbstractSyntaxVisitor(filePath, propKey, ctxProps);
        Logger.debug("Visitor building AST of: " + filePath);
        return visitor.build(tree);
    }

    /**
     * Visitor class which constructs the AST for a given ParseTree.
     */
    private static class AbstractSyntaxVisitor extends CppBaseVisitor<String> {
        private String propKey;
        private String typeModifier;
        private String memberModifier;
        private String specifier;
        private String type;
        private String attribute;
        private String pointerOp;
        private String varName;
        private String parameters;
        private boolean isHasAccess=false;
        private Deque<ASNode> parentStack;
        private final AbstractSyntaxTree AST;
        private Map<String, String> vars, fields, methods;
        private int varsCounter, fieldsCounter, methodsCounter;
        private Map<ParserRuleContext, Object> contexutalProperties;

        public AbstractSyntaxVisitor(String filePath, String propKey, Map<ParserRuleContext, Object> ctxProps) {
            parentStack = new ArrayDeque<>();
            AST = new AbstractSyntaxTree(filePath);
            this.propKey = propKey;
            contexutalProperties = ctxProps;
            vars = new LinkedHashMap<>();
            fields = new LinkedHashMap<>();
            methods = new LinkedHashMap<>();
            varsCounter = 0;
            fieldsCounter = 0;
            methodsCounter = 0;
        }

        public AbstractSyntaxTree build(ParseTree tree) {
            CppParser.TranslationUnitContext rootCntx = (CppParser.TranslationUnitContext) tree;
            AST.root.setCode(new File(AST.filePath).getName());
            parentStack.push(AST.root);
            if (rootCntx.declarationseq() != null) {
                visit(rootCntx.declarationseq());
            }
            parentStack.pop();
            vars.clear();
            fields.clear();
            methods.clear();
            return AST;
        }


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


        @Override public String visitNestedNameSpecifier(CppParser.NestedNameSpecifierContext ctx) {
            if(ctx.nestedNameSpecifier()!=null)
            {
                if(ctx.simpleTemplateId()!=null)
                {
                    return visit(ctx.nestedNameSpecifier())+"template "+visit(ctx.simpleTemplateId());
                }
                return visit(ctx.nestedNameSpecifier())+ctx.Identifier().getText()+ctx.Doublecolon().getText();
            }

            return visitChildren(ctx);
        }
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

        // TODO: 2023/4/11   完善statement
        @Override public String visitCompoundStatement(CppParser.CompoundStatementContext ctx) {
            //compoundStatement: LeftBrace statementSeq? RightBrace;
            //
            //statementSeq: statement+;
            //
            //statement:
            //	labeledStatement
            //	| declarationStatement
            //	| attributeSpecifierSeq? (
            //		expressionStatement
            //		| compoundStatement
            //		| selectionStatement
            //		| iterationStatement
            //		| jumpStatement
            //		| tryBlock
            //	);
            if(ctx.statementSeq()!=null)
            {
                for(CppParser.StatementContext statCtx:ctx.statementSeq().statement())
                {
                    visit(statCtx);
                }
            }
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitStatementSeq(CppParser.StatementSeqContext ctx) { return visitChildren(ctx); }

        @Override public String visitSelectionStatement(CppParser.SelectionStatementContext ctx) {
            //selectionStatement:
            //	If LeftParen condition RightParen statement (Else statement)?
            //	| Switch LeftParen condition RightParen statement;
            if(ctx.If()!=null)
            {
                ASNode ifNode=new ASNode(ASNode.Type.IF);
                ifNode.setLineOfCode(ctx.getStart().getLine());
                AST.addVertex(ifNode);
                AST.addEdge(parentStack.peek(),ifNode);

                ASNode condNode=new ASNode(ASNode.Type.CONDITION);
                condNode.setLineOfCode(ctx.condition().getStart().getLine());
                condNode.setCode(getOriginalCodeText(ctx.condition()));
                condNode.setNormalizedCode(visit(ctx.condition()));
                AST.addVertex(condNode);
                AST.addEdge(ifNode,condNode);

                ASNode thenNode=new ASNode(ASNode.Type.THEN);
                thenNode.setLineOfCode(ctx.statement(0).getStart().getLine());
                AST.addVertex(thenNode);
                AST.addEdge(ifNode,thenNode);
                parentStack.push(thenNode);
                visit(ctx.statement(0));
                parentStack.pop();

                if(ctx.statement(1)!=null)
                {
                    ASNode elseNode=new ASNode(ASNode.Type.ELSE);
                    elseNode.setLineOfCode(ctx.statement(1).getStart().getLine());
                    AST.addVertex(elseNode);
                    AST.addEdge(ifNode, elseNode);
                    parentStack.push(elseNode);
                    visit(ctx.statement(1));
                    parentStack.pop();
                }
            }
            else
            {
                //	| Switch LeftParen condition RightParen statement;
                ASNode switchNode=new ASNode(ASNode.Type.SWITCH);
                switchNode.setLineOfCode(ctx.getStart().getLine());
                AST.addVertex(switchNode);
                AST.addEdge(parentStack.peek(),switchNode);

                ASNode condNode=new ASNode(ASNode.Type.CONDITION);
                condNode.setLineOfCode(ctx.condition().getStart().getLine());
                condNode.setCode(getOriginalCodeText(ctx.condition()));
                condNode.setNormalizedCode(visit(ctx.condition()));
                AST.addVertex(condNode);
                AST.addEdge(switchNode,condNode);

                if(ctx.statement(0)!=null)
                {
                    ASNode statNode=new ASNode(ASNode.Type.BLOCK);
                    statNode.setLineOfCode(ctx.statement(0).getStart().getLine());
                    AST.addVertex(statNode);
                    AST.addEdge(switchNode,statNode);
                    parentStack.push(statNode);
                    visit(ctx.statement(0));
                    parentStack.pop();
                }
            }
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCondition(CppParser.ConditionContext ctx) { return visitChildren(ctx); }

        @Override public String visitIterationStatement(CppParser.IterationStatementContext ctx) {
            //iterationStatement:
            //	While LeftParen condition RightParen statement
            //	| Do statement While LeftParen expression RightParen Semi
            //	| For LeftParen (
            //		forInitStatement condition? Semi expression?
            //		| forRangeDeclaration Colon forRangeInitializer
            //	) RightParen statement;
            if(ctx.While()!=null)
            {
                //While LeftParen condition RightParen statement
                ASNode whileNode=new ASNode(ASNode.Type.WHILE);
                whileNode.setLineOfCode(ctx.getStart().getLine());
                AST.addVertex(whileNode);
                AST.addEdge(parentStack.peek(),whileNode);
                parentStack.push(whileNode);

                ASNode condNode=new ASNode(ASNode.Type.CONDITION);
                condNode.setLineOfCode(ctx.condition().getStart().getLine());
                condNode.setCode(getOriginalCodeText(ctx.condition()));
                AST.addVertex(condNode);
                AST.addEdge(parentStack.peek(),condNode);

                visit(ctx.statement());

                parentStack.pop();
            }
            else if(ctx.Do()!=null)
            {
                //Do statement While LeftParen expression RightParen Semi
                ASNode doNode=new ASNode(ASNode.Type.DO);
                doNode.setLineOfCode(ctx.getStart().getLine());
                AST.addVertex(doNode);
                AST.addEdge(parentStack.peek(),doNode);
                parentStack.push(doNode);

                visit(ctx.statement());

                ASNode condNode=new ASNode(ASNode.Type.CONDITION);
                condNode.setLineOfCode(ctx.condition().getStart().getLine());
                condNode.setCode(getOriginalCodeText(ctx.condition()));
                AST.addVertex(condNode);
                AST.addEdge(parentStack.peek(),condNode);

                parentStack.pop();
            }
            else
            {
                //For LeftParen (
                //            //		forInitStatement condition? Semi expression?
                //            //		| forRangeDeclaration Colon forRangeInitializer
                //            //	) RightParen statement;
                ASNode forNode=new ASNode(ASNode.Type.FOR);
                forNode.setLineOfCode(ctx.getStart().getLine());
                AST.addVertex(forNode);
                AST.addEdge(parentStack.peek(),forNode);
                parentStack.push(forNode);

                if(ctx.forInitStatement()!=null)
                {
                    visit(ctx.forInitStatement());

                    if(ctx.condition()!=null)
                    {
                        ASNode condNode=new ASNode(ASNode.Type.CONDITION);
                        condNode.setLineOfCode(ctx.condition().getStart().getLine());
                        condNode.setCode(getOriginalCodeText(ctx.condition()));
                        AST.addVertex(condNode);
                        AST.addEdge(parentStack.peek(),condNode);
                    }

                    if(ctx.expression()!=null)
                    {
                        ASNode experNode=new ASNode(ASNode.Type.EXPRESSION);
                        experNode.setLineOfCode(ctx.expression().getStart().getLine());
                        experNode.setCode(getOriginalCodeText(ctx.expression()));
                        AST.addVertex(experNode);
                        AST.addEdge(parentStack.peek(),experNode);
                    }
                }
                else
                {
                    //forRangeDeclaration Colon forRangeInitializer
                    //
                    //forRangeDeclaration:
                    //	attributeSpecifierSeq? declSpecifierSeq declarator;
                    //
                    //forRangeInitializer: expression | bracedInitList;
                    visit(ctx.forRangeDeclaration().declSpecifierSeq());
                    ASNode varNode=new ASNode(ASNode.Type.FOR_RANGE_INIT);
                    varNode.setLineOfCode(ctx.forRangeDeclaration().getStart().getLine());
                    AST.addVertex(varNode);
                    AST.addEdge(parentStack.peek(),varNode);

                    if(specifier!=null)
                    {
                        ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                        specNode.setLineOfCode(ctx.forRangeDeclaration().getStart().getLine());
                        specNode.setCode(specifier);
                        AST.addVertex(specNode);
                        AST.addEdge(varNode,specNode);
                    }
                    visit(ctx.forRangeDeclaration().declarator());
                    ASNode typeNode=new ASNode(ASNode.Type.TYPE);
                    typeNode.setLineOfCode(ctx.forRangeDeclaration().declarator().getStart().getLine());
                    typeNode.setCode(type+pointerOp);
                    AST.addVertex(typeNode);
                    AST.addEdge(varNode,typeNode);

                    ASNode nameNode=new ASNode(ASNode.Type.NAME);
                    nameNode.setLineOfCode(ctx.forRangeDeclaration().declarator().getStart().getLine());
                    nameNode.setCode(varName);
                    AST.addVertex(nameNode);
                    AST.addEdge(varNode,nameNode);

                    ASNode initerNode=new ASNode(ASNode.Type.FOR_RANGE_INITER);
                    initerNode.setLineOfCode(ctx.forRangeInitializer().getStart().getLine());
                    initerNode.setCode(getOriginalCodeText(ctx.forRangeInitializer()));
                    AST.addVertex(initerNode);
                    AST.addEdge(varNode,initerNode);
                }
                visit(ctx.statement());
                parentStack.pop();
            }
            return "";
        }

        @Override public String visitForInitStatement(CppParser.ForInitStatementContext ctx) {
            //forInitStatement: expressionStatement | simpleDeclaration;
            if(ctx.expressionStatement()!=null)
            {
                ASNode initNode=new ASNode(ASNode.Type.FOR_INIT);
                initNode.setLineOfCode(ctx.getStart().getLine());
                initNode.setCode(getOriginalCodeText(ctx.expressionStatement()));
                AST.addVertex(initNode);
                AST.addEdge(parentStack.peek(),initNode);
            }
            else
                visit(ctx.simpleDeclaration());
            return "";
        }


        @Override public String visitForRangeDeclaration(CppParser.ForRangeDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForRangeInitializer(CppParser.ForRangeInitializerContext ctx)
        //forRangeInitializer: expression | bracedInitList;
        {
            if (ctx.expression() != null) {
                return ctx.expression().getText();
            } else
                return ctx.bracedInitList().getText();
        }
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
        @Override public String visitDeclaration(CppParser.DeclarationContext ctx) {
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBlockDeclaration(CppParser.BlockDeclarationContext ctx) {return visitChildren(ctx);}


        @Override public String visitAliasDeclaration(CppParser.AliasDeclarationContext ctx) {
            //aliasDeclaration:
            //	Using Identifier attributeSpecifierSeq? Assign theTypeId Semi;
            ASNode aliasNode=new ASNode(ASNode.Type.ALIAS);
            aliasNode.setLineOfCode(ctx.getStart().getLine());
            aliasNode.setCode(getOriginalCodeText(ctx));
            AST.addVertex(aliasNode);
            AST.addEdge(parentStack.peek(),aliasNode);
            return "";
        }

        // TODO: 2023/4/13 待区分存在参数列表时是属于函数声明还是变量定义，目前同一为函数声明
        @Override public String visitSimpleDeclaration(CppParser.SimpleDeclarationContext ctx) {
            //simpleDeclaration:
            //	declSpecifierSeq? initDeclaratorList? Semi
            //	| attributeSpecifierSeq declSpecifierSeq? initDeclaratorList Semi;v
            if(ctx.declSpecifierSeq()!=null)
            {
                // 变量或函数声明
                //initDeclaratorList: initDeclarator (Comma initDeclarator)*;
                //
                //initDeclarator: declarator initializer?;
                visit(ctx.declSpecifierSeq());
                for(CppParser.InitDeclaratorContext initCtx:ctx.initDeclaratorList().initDeclarator())
                {
                    visit(initCtx);
                    boolean isFunc=false;
                    if(parameters!="")
                    {
                        ASNode funcNode=new ASNode(ASNode.Type.FUNCTION);
                        funcNode.setLineOfCode(initCtx.getStart().getLine());
                        AST.addVertex(funcNode);
                        AST.addEdge(parentStack.peek(),funcNode);
                        parentStack.push(funcNode);
                        isFunc=true;
                    }
                    else
                    {
                        ASNode varNode=new ASNode(ASNode.Type.VARIABLE);
                        varNode.setLineOfCode(initCtx.getStart().getLine());
                        AST.addVertex(varNode);
                        AST.addEdge(parentStack.peek(),varNode);
                        parentStack.push(varNode);
                    }
                    if(specifier!="")
                    {
                        ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                        specNode.setLineOfCode(ctx.getStart().getLine());
                        specNode.setCode(specifier);
                        AST.addVertex(specNode);
                        AST.addEdge(parentStack.peek(),specNode);
                    }
                    ASNode typeNode=new ASNode((!isFunc? ASNode.Type.TYPE:ASNode.Type.RETURN));
                    typeNode.setLineOfCode(initCtx.getStart().getLine());
                    typeNode.setCode(type+pointerOp);
                    AST.addVertex(typeNode);
                    AST.addEdge(parentStack.peek(),typeNode);

                    ASNode nameNode=new ASNode(ASNode.Type.NAME);
                    nameNode.setLineOfCode(initCtx.getStart().getLine());
                    nameNode.setCode(varName);
                    AST.addVertex(nameNode);
                    AST.addEdge(parentStack.peek(),nameNode);

                    if(isFunc)
                    {
                        ASNode parmsNode=new ASNode(ASNode.Type.PARAMS);
                        parmsNode.setLineOfCode(initCtx.getStart().getLine());
                        parmsNode.setCode(parameters);
                        AST.addVertex(parmsNode);
                        AST.addEdge(parentStack.peek(),parmsNode);
                    }

                    if(initCtx.initializer()!=null)
                    {
                        //initializer:
                        //	braceOrEqualInitializer
                        //	| LeftParen expressionList RightParen;
                        ASNode initNode=new ASNode(ASNode.Type.INIT_VALUE);
                        initNode.setLineOfCode(initCtx.initializer().getStart().getLine());
                        initNode.setCode(getOriginalCodeText(initCtx.initializer()));
                        AST.addVertex(initNode);
                        AST.addEdge(parentStack.peek(),initNode);
                    }
                    parentStack.pop();
                }
            }
            else if(ctx.initDeclaratorList()!=null)
            {
                //函数调用或变量赋值
                ASNode experNode=new ASNode(ASNode.Type.EXPRESSION);
                experNode.setLineOfCode(ctx.getStart().getLine());
                experNode.setCode(getOriginalCodeText(ctx.initDeclaratorList()));
                AST.addVertex(experNode);
                AST.addEdge(parentStack.peek(),experNode);
            }
            return "";
        }

        @Override public String visitStaticAssertDeclaration(CppParser.StaticAssertDeclarationContext ctx) {
            //staticAssertDeclaration:
            //	Static_assert LeftParen constantExpression Comma StringLiteral RightParen Semi;
            ASNode stAssNode=new ASNode(ASNode.Type.STATIC_ASSERT);
            stAssNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(stAssNode);
            AST.addEdge(parentStack.peek(),stAssNode);

            ASNode condNode=new ASNode(ASNode.Type.CONDITION);
            condNode.setLineOfCode(ctx.constantExpression().getStart().getLine());
            condNode.setCode(getOriginalCodeText(ctx.constantExpression()));
            AST.addVertex(condNode);
            AST.addEdge(stAssNode,condNode);

            ASNode messageNode=new ASNode(ASNode.Type.MESSAGE);
            messageNode.setLineOfCode(ctx.constantExpression().getStart().getLine());
            messageNode.setCode(ctx.StringLiteral().getText());
            AST.addVertex(messageNode);
            AST.addEdge(stAssNode,messageNode);
            return "";
        }
        @Override public String visitEmptyDeclaration(CppParser.EmptyDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAttributeDeclaration(CppParser.AttributeDeclarationContext ctx)
        //attributeDeclaration: attributeSpecifierSeq Semi;
        {
            return visit(ctx.attributeSpecifierSeq())+" "+visit(ctx.Semi());
        }

        @Override public String visitDeclSpecifier(CppParser.DeclSpecifierContext ctx) { return visitChildren(ctx); }

        @Override public String visitDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx) {
            // declSpecifierSeq: declSpecifier+? attributeSpecifierSeq?;
            //
            // declSpecifier:storageClassSpecifier | typeSpecifier| functionSpecifier| Friend| Typedef | Constexpr;
            if(ctx.declSpecifier()!=null)
            {
                specifier="";
                type="";
                for(CppParser.DeclSpecifierContext decSpCtx:ctx.declSpecifier())
                {
                    if(decSpCtx.storageClassSpecifier()!=null)
                    {
                        specifier+=visit(decSpCtx.storageClassSpecifier())+" ";
                    }
                    else if(decSpCtx.typeSpecifier()!=null)
                    {
                        type+=visit(decSpCtx.typeSpecifier());
                    }
                    else if(decSpCtx.functionSpecifier()!=null)
                    {
                        specifier+=visit(decSpCtx.functionSpecifier())+" ";
                    }
                    else
                    {
                        specifier+=getOriginalCodeText(decSpCtx)+" ";
                    }
                }
            }

            return "";
        }

        @Override public String visitStorageClassSpecifier(CppParser.StorageClassSpecifierContext ctx) {
            return getOriginalCodeText(ctx);
        }

        @Override public String visitFunctionSpecifier(CppParser.FunctionSpecifierContext ctx) {
            return getOriginalCodeText(ctx);
        }
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

        @Override public String visitSimpleTypeSpecifier(CppParser.SimpleTypeSpecifierContext ctx) {
            if(ctx.theTypeName()!=null)
            {
                return visit(ctx.nestedNameSpecifier())+visit(ctx.theTypeName());
            }
            else if(ctx.simpleTemplateId()!=null)
                return visit(ctx.nestedNameSpecifier())+"template "+visit(ctx.simpleTemplateId());
            else if(ctx.decltypeSpecifier()!=null)
                return visit(ctx.decltypeSpecifier());

            return getOriginalCodeText(ctx);
        }

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

        @Override public String visitEnumSpecifier(CppParser.EnumSpecifierContext ctx) {
            //enumSpecifier:
            //	enumHead LeftBrace (enumeratorList Comma?)? RightBrace;
            ASNode enumNode=new ASNode(ASNode.Type.ENUM);
            enumNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(enumNode);
            AST.addEdge(parentStack.peek(),enumNode);
            parentStack.push(enumNode);

            visit(ctx.enumHead());

            ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
            specNode.setLineOfCode(ctx.getStart().getLine());
            specNode.setCode(specifier);
            AST.addVertex(specNode);
            AST.addEdge(parentStack.peek(),specNode);

            ASNode nameNode=new ASNode(ASNode.Type.NAME);
            nameNode.setLineOfCode(ctx.getStart().getLine());
            nameNode.setCode(varName);
            AST.addVertex(nameNode);
            AST.addEdge(parentStack.peek(),nameNode);

            if(ctx.enumeratorList()!=null)
                visit(ctx.enumeratorList());

            parentStack.pop();
            return "";
        }

        @Override public String visitEnumHead(CppParser.EnumHeadContext ctx) {
            //enumHead:
            //	enumkey attributeSpecifierSeq? (
            //		nestedNameSpecifier? Identifier
            //	)? enumbase?;

            specifier=getOriginalCodeText(ctx.enumkey());
            varName="";
            if(ctx.nestedNameSpecifier()!=null)
                varName=visit(ctx.nestedNameSpecifier());
            varName+=ctx.Identifier().getText();
            return "";
        }
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

        @Override public String visitEnumeratorList(CppParser.EnumeratorListContext ctx) {
            //enumeratorList:
            //	enumeratorDefinition (Comma enumeratorDefinition)*;
            //
            //enumeratorDefinition: enumerator (Assign constantExpression)?;
            //
            //enumerator: Identifier;
            for(CppParser.EnumeratorDefinitionContext enumCtx:ctx.enumeratorDefinition())
            {
                ASNode varNode=new ASNode(ASNode.Type.VARIABLE);
                varNode.setLineOfCode(enumCtx.getStart().getLine());
                varNode.setCode(getOriginalCodeText(enumCtx));
                AST.addVertex(varNode);
                AST.addEdge(parentStack.peek(),varNode);
            }
            return "";
        }
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

        @Override public String visitNamespaceName(CppParser.NamespaceNameContext ctx) {
            return getOriginalCodeText(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitOriginalNamespaceName(CppParser.OriginalNamespaceNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx) {
            ASNode namespaceNode=new ASNode(ASNode.Type.NAMESPACE);
            namespaceNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding namespace definition");
            AST.addVertex(namespaceNode);
            AST.addEdge(parentStack.peek(),namespaceNode);

            if(ctx.Inline()!=null)
            {
                ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                specNode.setLineOfCode(ctx.getStart().getLine());
                specNode.setCode(ctx.Inline().getText());
                AST.addVertex(specNode);
                AST.addEdge(namespaceNode,specNode);
            }

            ASNode nameNode=new ASNode(ASNode.Type.NAME);
            nameNode.setLineOfCode(ctx.getStart().getLine());
            String spaceName=ctx.Identifier().getText();
            if(!ctx.originalNamespaceName().isEmpty())
                spaceName=ctx.originalNamespaceName().getText();
            nameNode.setCode(spaceName);
            AST.addVertex(nameNode);
            AST.addEdge(namespaceNode,nameNode);

            parentStack.push(namespaceNode);
            visit(ctx.declarationseq());
            parentStack.pop();
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNamespaceAlias(CppParser.NamespaceAliasContext ctx) { return visitChildren(ctx); }

        @Override public String visitNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx) {
            ASNode aliasNode=new ASNode(ASNode.Type.ALIAS);
            aliasNode.setLineOfCode(ctx.getStart().getLine());
            aliasNode.setCode(getOriginalCodeText(ctx));
            AST.addVertex(aliasNode);
            AST.addEdge(parentStack.peek(),aliasNode);
            return "";
        }

        @Override public String visitQualifiednamespacespecifier(CppParser.QualifiednamespacespecifierContext ctx) { return visitChildren(ctx); }

        @Override public String visitUsingDeclaration(CppParser.UsingDeclarationContext ctx) {
            ASNode usingNode=new ASNode(ASNode.Type.USING);
            usingNode.setLineOfCode(ctx.getStart().getLine());
            usingNode.setCode(getOriginalCodeText(ctx));
            AST.addVertex(usingNode);
            AST.addEdge(parentStack.peek(),usingNode);
            return "";
        }

        @Override public String visitUsingDirective(CppParser.UsingDirectiveContext ctx) {
            ASNode usingNode=new ASNode(ASNode.Type.USING);
            usingNode.setLineOfCode(ctx.getStart().getLine());
            usingNode.setCode(getOriginalCodeText(ctx));
            AST.addVertex(usingNode);
            AST.addEdge(parentStack.peek(),usingNode);
            return "";
        }

        @Override public String visitAsmDefinition(CppParser.AsmDefinitionContext ctx) {
            ASNode asmNode=new ASNode(ASNode.Type.ASM);
            asmNode.setLineOfCode(ctx.getStart().getLine());
            asmNode.setCode(getOriginalCodeText(ctx));
            AST.addVertex(asmNode);
            AST.addEdge(parentStack.peek(),asmNode);
            return "";
        }

        @Override public String visitLinkageSpecification(CppParser.LinkageSpecificationContext ctx) {
            //linkageSpecification:
            //	Extern StringLiteral (
            //		LeftBrace declarationseq? RightBrace
            //		| declaration
            //	);
            ASNode externNode=new ASNode(ASNode.Type.EXTERN);
            externNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(externNode);
            AST.addEdge(parentStack.peek(),externNode);
            parentStack.push(externNode);

            String str=ctx.StringLiteral().getText();
            if(!str.isEmpty())
            {
                ASNode nameNode=new ASNode(ASNode.Type.NAME);
                nameNode.setLineOfCode(ctx.getStart().getLine());
                nameNode.setCode(str);
                AST.addVertex(nameNode);
                AST.addEdge(parentStack.peek(),nameNode);
            }

            if(ctx.declarationseq()!=null)
                visit(ctx.declarationseq());
            else
                visit(ctx.declaration());

            parentStack.pop();
            return "";
        }
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
        @Override public String visitBalancedTokenSeq(CppParser.BalancedTokenSeqContext ctx) {
            //balancedTokenSeq: balancedtoken+;
            ASNode balancedTokenSeqNode = new ASNode(ASNode.Type.BALANCEDTOKENSEQ);
            balancedTokenSeqNode.setLineOfCode(ctx.getStart().getLine());
            balancedTokenSeqNode.setCode(ctx.getText());
            AST.addVertex(balancedTokenSeqNode);
            AST.addEdge(parentStack.peek(), balancedTokenSeqNode);
            parentStack.push(balancedTokenSeqNode);
            for (CppParser.BalancedtokenContext balancedContext : ctx.balancedtoken()) {

                visitBalancedtoken(balancedContext);
            }
            parentStack.pop();
            return "";


      //todo
      //private void visitBalancedToken(ASNode visitBalancedTokenSeqNode, List<CppParser.BalancedtokenContext> BalancedTokenSeq)
       //{
           /*
           balancedtoken:
            LeftParen balancedTokenSeq RightParen
                    | LeftBracket balancedTokenSeq RightBracket
	                | LeftBrace balancedTokenSeq RightBrace
                    | ~(
                    LeftParen
                            | RightParen
                            | LeftBrace
                            | RightBrace
                            | LeftBracket
                            | RightBracket )+;
       */
                   //for (CppParser.BalancedtokenContext balancedToken : BalancedTokenSeq) {

                    //}


                //}
            }
        @Override public String visitBalancedtoken(CppParser.BalancedtokenContext ctx) {/*
            balancedtoken:
            LeftParen balancedTokenSeq RightParen
                    | LeftBracket balancedTokenSeq RightBracket
	                | LeftBrace balancedTokenSeq RightBrace
                    | ~(
                    LeftParen
                            | RightParen
                            | LeftBrace
                            | RightBrace
                            | LeftBracket
                            | RightBracket
            )+;
            */
            if (ctx.LeftParen() != null) {
                return ctx.LeftParen() + " " + visit(ctx.balancedTokenSeq()) + " " + ctx.RightParen();
            } else if (ctx.LeftBracket() != null) {
                return ctx.LeftBracket() + " " + visit(ctx.balancedTokenSeq()) + " " + ctx.RightParen();
            } else if (ctx.LeftBrace() != null) {
                return ctx.LeftBrace() + " " + visit(ctx.balancedTokenSeq()) + " " + ctx.RightBrace();

            /*todo
           ~(
                    LeftParen
                            | RightParen
                            | LeftBrace
                            | RightBrace
                            | LeftBracket
                            | RightBracket
            )+;*/
            }
            return"";
        }

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

        @Override public String visitDeclarator(CppParser.DeclaratorContext ctx) {
            //declarator:
            //	pointerDeclarator
            //	| noPointerDeclarator parametersAndQualifiers trailingReturnType;
            //
            // pointerDeclarator: (pointerOperator Const?)* noPointerDeclarator;
            pointerOp="";
            varName="";
            CppParser.PointerDeclaratorContext pdCtx=ctx.pointerDeclarator();
            if(pdCtx.pointerOperator()!=null)
            {
                for(int i=0;i<pdCtx.pointerOperator().size();++i)
                {
                    pointerOp+=getOriginalCodeText(pdCtx.pointerOperator(i));
                    if(pdCtx.Const(i)!=null)
                        pointerOp+=pdCtx.Const(i).getText();
                }
            }

            // noPointerDeclarator:
            //	declaratorid attributeSpecifierSeq?
            //	| noPointerDeclarator (
            //		parametersAndQualifiers
            //		| LeftBracket constantExpression? RightBracket attributeSpecifierSeq?
            //	)
            //	| LeftParen pointerDeclarator RightParen;
            CppParser.NoPointerDeclaratorContext npdCtx=pdCtx.noPointerDeclarator();
            varName=getOriginalCodeText(npdCtx.noPointerDeclarator());

            //parametersAndQualifiers:
            //	LeftParen parameterDeclarationClause? RightParen cvqualifierseq? refqualifier?
            //		exceptionSpecification? attributeSpecifierSeq?;
            parameters="";
            if(npdCtx.parametersAndQualifiers()!=null && npdCtx.parametersAndQualifiers().parameterDeclarationClause()!=null)
                parameters=getOriginalCodeText(npdCtx.parametersAndQualifiers().parameterDeclarationClause());
            return "";
        }
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

        @Override public String visitDeclaratorid(CppParser.DeclaratoridContext ctx) {
            return getOriginalCodeText(ctx);
        }
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
            // functionDefinition:
            // attributeSpecifierSeq? declSpecifierSeq? declarator virtualSpecifierSeq? functionBody;

            ASNode funcNode=new ASNode(ASNode.Type.FUNCTION);
            funcNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding function node");
            AST.addVertex(funcNode);
            AST.addEdge(parentStack.peek(),funcNode);
            parentStack.push(funcNode);

//            if(ctx.attributeSpecifierSeq()!=null)
//            {
//                visit(ctx.attributeSpecifierSeq());
//
//            }
            if(ctx.declSpecifierSeq()!=null)
            {
                visit(ctx.declSpecifierSeq());
                if(specifier!="")
                {
                    ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                    specNode.setLineOfCode(ctx.getStart().getLine());
                    specNode.setCode(specifier);
                    AST.addVertex(specNode);
                    AST.addEdge(parentStack.peek(),specNode);
                }


            }

            // declarator:
            //	 pointerDeclarator
            //	 | noPointerDeclarator parametersAndQualifiers trailingReturnType;
            //
            // pointerDeclarator: (pointerOperator Const?)* noPointerDeclarator;
            CppParser.PointerDeclaratorContext pdCtx=ctx.declarator().pointerDeclarator();
            if(pdCtx.pointerOperator()!=null)
            {
                for(int i=0;i<pdCtx.pointerOperator().size();++i)
                {
                    type+=getOriginalCodeText(pdCtx.pointerOperator(i));
                    if(pdCtx.Const(i)!=null)
                        type+=pdCtx.Const(i).getText();
                }
            }

            ASNode retNode=new ASNode(ASNode.Type.RETURN);
            retNode.setLineOfCode(ctx.getStart().getLine());
            retNode.setCode(type);
            AST.addVertex(retNode);
            AST.addEdge(parentStack.peek(),retNode);

            // noPointerDeclarator:
            //	declaratorid attributeSpecifierSeq?
            //	| noPointerDeclarator (
            //		parametersAndQualifiers
            //		| LeftBracket constantExpression? RightBracket attributeSpecifierSeq?
            //	)
            //	| LeftParen pointerDeclarator RightParen;
            CppParser.NoPointerDeclaratorContext npdCtx=pdCtx.noPointerDeclarator();
            ASNode nameNode=new ASNode(ASNode.Type.NAME);
            nameNode.setLineOfCode(npdCtx.getStart().getLine());
            nameNode.setCode(getOriginalCodeText(npdCtx.noPointerDeclarator().declaratorid()));
            AST.addVertex(nameNode);
            AST.addEdge(parentStack.peek(),nameNode);

            // parametersAndQualifiers:
            //	    LeftParen parameterDeclarationClause? RightParen cvqualifierseq? refqualifier?
            //		    exceptionSpecification? attributeSpecifierSeq?;
            CppParser.ParametersAndQualifiersContext parmCtx=npdCtx.parametersAndQualifiers();
            if(parmCtx.parameterDeclarationClause()!=null)
            {
                ASNode parmNode=new ASNode(ASNode.Type.PARAMS);
                parmNode.setLineOfCode(parmCtx.getStart().getLine());
                parmNode.setCode(getOriginalCodeText(parmCtx.parameterDeclarationClause()));
                AST.addVertex(parmNode);
                AST.addEdge(parentStack.peek(),parmNode);
            }

            if(parmCtx.cvqualifierseq()!=null || parmCtx.refqualifier()!=null)
            {
                ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                specNode.setLineOfCode(parmCtx.getStart().getLine());
                specNode.setCode(getOriginalCodeText(parmCtx.cvqualifierseq())+" "+
                        getOriginalCodeText(parmCtx.refqualifier()));
                AST.addVertex(specNode);
                AST.addEdge(parentStack.peek(),specNode);
            }

            if(parmCtx.exceptionSpecification()!=null)
            {
                ASNode excpNode=new ASNode(ASNode.Type.EXCEPT);
                excpNode.setLineOfCode(parmCtx.exceptionSpecification().getStart().getLine());
                excpNode.setCode(getOriginalCodeText(parmCtx.exceptionSpecification()));
                AST.addVertex(excpNode);
                AST.addEdge(parentStack.peek(),excpNode);
            }

            if(ctx.virtualSpecifierSeq()!=null)
            {
                String virSpec="";
                for(CppParser.VirtualSpecifierContext virCtx:ctx.virtualSpecifierSeq().virtualSpecifier())
                    virSpec+=getOriginalCodeText(virCtx)+" ";
                ASNode virSpecNode=new ASNode(ASNode.Type.VIRTUAL_SPECIFIER);
                virSpecNode.setLineOfCode(ctx.virtualSpecifierSeq().getStart().getLine());
                virSpecNode.setCode(virSpec);
                AST.addVertex(virSpecNode);
                AST.addEdge(parentStack.peek(),virSpecNode);
            }

            //functionBody:
            //	constructorInitializer? compoundStatement
            //	| functionTryBlock
            //	| Assign (Default | Delete) Semi;
            CppParser.FunctionBodyContext bodyCtx=ctx.functionBody();
            ASNode bodyNode=new ASNode(ASNode.Type.BLOCK);
            bodyNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(bodyNode);
            AST.addEdge(parentStack.peek(),bodyNode);
            parentStack.push(bodyNode);
            if(bodyCtx.compoundStatement()!=null)
            {
                boolean isInit=false;
                if(bodyCtx.constructorInitializer()!=null)
                {
                    visit(bodyCtx.constructorInitializer());
                    isInit=true;

                    ASNode blockNode=new ASNode(ASNode.Type.BLOCK);
                    blockNode.setLineOfCode(bodyCtx.compoundStatement().getStart().getLine());
                    AST.addVertex(blockNode);
                    AST.addEdge(parentStack.peek(),blockNode);
                    parentStack.push(blockNode);
                }

                visit(bodyCtx.compoundStatement());
                if(isInit)
                    parentStack.pop();

            }
            else if(bodyCtx.functionTryBlock()!=null)
            {
                //functionTryBlock:
                //	Try constructorInitializer? compoundStatement handlerSeq;
                CppParser.FunctionTryBlockContext tryCtx=bodyCtx.functionTryBlock();

                boolean isInit=false;
                if(tryCtx.constructorInitializer()!=null)
                {
                    isInit=true;
                    ASNode initNode=new ASNode(ASNode.Type.INITIALIZER);
                    initNode.setLineOfCode(tryCtx.compoundStatement().getStart().getLine());
                    initNode.setCode("try "+getOriginalCodeText(tryCtx.compoundStatement()));
                    AST.addVertex(initNode);
                    AST.addEdge(parentStack.peek(),initNode);
                    isInit=true;

                    ASNode blockNode=new ASNode(ASNode.Type.BLOCK);
                    blockNode.setLineOfCode(tryCtx.compoundStatement().getStart().getLine());
                    AST.addVertex(blockNode);
                    AST.addEdge(parentStack.peek(),blockNode);
                    parentStack.push(blockNode);
                }

                visit(tryCtx.compoundStatement());
                if(isInit)
                    parentStack.pop();


                visit(tryCtx.handlerSeq());

            }
            else
            {
                bodyNode.setCode(getOriginalCodeText(bodyCtx));
            }
            parentStack.pop();



            parentStack.pop();

            return "";
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

        @Override public String visitClassName(CppParser.ClassNameContext ctx) {
            if(ctx.simpleTemplateId()!=null)
                return visit(ctx.simpleTemplateId());
            return getOriginalCodeText(ctx);
        }

        @Override public String visitClassSpecifier(CppParser.ClassSpecifierContext ctx) {
            //classSpecifier:
            //	classHead LeftBrace memberSpecification? RightBrace;
            //
            //classHeadName: nestedNameSpecifier? className;
            //
            //classVirtSpecifier: Final;
            //
            //classKey: Class | Struct;
            ASNode classNode=new ASNode(ASNode.Type.CLASS);
            classNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(classNode);
            AST.addEdge(parentStack.peek(),classNode);
            parentStack.push(classNode);

            visit(ctx.classHead());

            if(ctx.memberSpecification()!=null)
                visit(ctx.memberSpecification());

            parentStack.pop();
            return "";
        }

        @Override public String visitClassHead(CppParser.ClassHeadContext ctx) {
            //classHead:
            //	classKey attributeSpecifierSeq? (
            //		classHeadName classVirtSpecifier?
            //	)? baseClause?
            //	| Union attributeSpecifierSeq? (
            //		classHeadName classVirtSpecifier?
            //	)?;

            ASNode typeNode=new ASNode(ASNode.Type.TYPE);
            typeNode.setLineOfCode(ctx.getStart().getLine());
            if(ctx.Union()!=null)
                typeNode.setCode(ctx.Union().getText());
            else
                typeNode.setCode(getOriginalCodeText(ctx.classKey()));
            AST.addVertex(typeNode);
            AST.addEdge(parentStack.peek(),typeNode);

            if(ctx.classHeadName()!=null)
            {
                ASNode nameNode=new ASNode(ASNode.Type.NAME);
                nameNode.setLineOfCode(ctx.getStart().getLine());
                nameNode.setCode(getOriginalCodeText(ctx.classHeadName()));
                AST.addVertex(nameNode);
                AST.addEdge(parentStack.peek(),nameNode);

                if( ctx.classVirtSpecifier()!=null)
                {
                    ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                    specNode.setLineOfCode(ctx.classVirtSpecifier().getStart().getLine());
                    specNode.setCode(getOriginalCodeText(ctx.classVirtSpecifier()));
                    AST.addVertex(specNode);
                    AST.addEdge(parentStack.peek(),specNode);
                }
            }

            if(ctx.baseClause()!=null)
            {
                isHasAccess=false;
                ASNode baseNode=new ASNode(ASNode.Type.BASE);
                baseNode.setLineOfCode(ctx.baseClause().getStart().getLine());
                baseNode.setCode(getOriginalCodeText(ctx.baseClause()));
                AST.addVertex(baseNode);
                AST.addEdge(parentStack.peek(),baseNode);
                if(isHasAccess)
                    parentStack.pop();
                isHasAccess=false;
            }
            return "";
        }
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

        @Override public String visitMemberSpecification(CppParser.MemberSpecificationContext ctx) {
            //memberSpecification:
            //	(memberdeclaration | accessSpecifier Colon)+;
            for(int i=0;i<ctx.children.size();++i)
            {
                CppParser.AccessSpecifierContext accCtx=ctx.accessSpecifier(i);
                if(accCtx!=null)
                {
                    //accessSpecifier: Private | Protected | Public;
                    if(isHasAccess)
                        parentStack.pop();
                    ASNode accessNode;
                    if(accCtx.Private()!=null)
                        accessNode=new ASNode(ASNode.Type.PRIVATE);
                    else if(accCtx.Protected()!=null)
                        accessNode=new ASNode(ASNode.Type.PROTECTED);
                    else
                        accessNode=new ASNode(ASNode.Type.PUBILC);

                    accessNode.setLineOfCode(accCtx.getStart().getLine());
                    AST.addVertex(accessNode);
                    AST.addEdge(parentStack.peek(),accessNode);
                    parentStack.push(accessNode);
                    isHasAccess=true;
                }
                else
                    visit(ctx.memberdeclaration(i));
            }
            return visitChildren(ctx);
        }

        @Override public String visitMemberdeclaration(CppParser.MemberdeclarationContext ctx) {
            //memberdeclaration:
            //	attributeSpecifierSeq? declSpecifierSeq? memberDeclaratorList? Semi
            //	| functionDefinition
            //	| usingDeclaration
            //	| staticAssertDeclaration
            //	| templateDeclaration
            //	| aliasDeclaration
            //	| emptyDeclaration;
            if(ctx.memberDeclaratorList()!=null)
            {
                visit(ctx.declSpecifierSeq());
                for(CppParser.MemberDeclaratorContext memCtx:ctx.memberDeclaratorList().memberDeclarator())
                {
                    //memberDeclarator:
                    //	declarator (
                    //		virtualSpecifierSeq? pureSpecifier?
                    //		| braceOrEqualInitializer?
                    //	)
                    //	| Identifier? attributeSpecifierSeq? Colon constantExpression;
                    if(memCtx.declarator()!=null)
                    {
                        visit(memCtx.declarator());
                        boolean isFunc=false;
                        if(parameters!="" || memCtx.declarator().pointerDeclarator().noPointerDeclarator().parametersAndQualifiers()!=null)
                        {
                            ASNode funcNode=new ASNode(ASNode.Type.FUNCTION);
                            funcNode.setLineOfCode(memCtx.getStart().getLine());
                            AST.addVertex(funcNode);
                            AST.addEdge(parentStack.peek(),funcNode);
                            parentStack.push(funcNode);
                            isFunc=true;
                        }
                        else
                        {
                            ASNode varNode=new ASNode(ASNode.Type.VARIABLE);
                            varNode.setLineOfCode(ctx.getStart().getLine());
                            AST.addVertex(varNode);
                            AST.addEdge(parentStack.peek(),varNode);
                            parentStack.push(varNode);
                        }

                        if(specifier!="")
                        {
                            ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                            specNode.setLineOfCode(ctx.declSpecifierSeq().getStart().getLine());
                            specNode.setCode(specifier);
                            AST.addVertex(specNode);
                            AST.addEdge(parentStack.peek(),specNode);
                        }

                        ASNode typeNode=new ASNode((!isFunc?ASNode.Type.TYPE:ASNode.Type.RETURN));
                        typeNode.setLineOfCode(ctx.declSpecifierSeq().getStart().getLine());
                        typeNode.setCode(type+pointerOp);
                        AST.addVertex(typeNode);
                        AST.addEdge(parentStack.peek(),typeNode);

                        ASNode nameNode=new ASNode(ASNode.Type.NAME);
                        nameNode.setLineOfCode(memCtx.declarator().getStart().getLine());
                        nameNode.setCode(varName);
                        if(!isFunc)
                        {
                            ++varsCounter;
                            String normalized="$VARL_"+varsCounter;
                            nameNode.setNormalizedCode(normalized);
                            vars.put(varName,normalized);
                        }
                        else
                        {
                            ++methodsCounter;
                            String normalized="$FUNCTION_"+methodsCounter;
                            nameNode.setNormalizedCode(normalized);
                            methods.put(varName,normalized);
                        }
                        AST.addVertex(nameNode);
                        AST.addEdge(parentStack.peek(),nameNode);

                        if(parameters!="")
                        {
                            ASNode parmsNode=new ASNode(ASNode.Type.PARAMS);
                            parmsNode.setLineOfCode(memCtx.getStart().getLine());
                            parmsNode.setCode(parameters);
                            AST.addVertex(parmsNode);
                            AST.addEdge(parentStack.peek(),parmsNode);
                        }

                        if(memCtx.virtualSpecifierSeq()!=null)
                        {
                            ASNode specNode=new ASNode(ASNode.Type.SPECIFIER);
                            specNode.setLineOfCode(ctx.declSpecifierSeq().getStart().getLine());
                            specNode.setCode(getOriginalCodeText(memCtx.virtualSpecifierSeq()));
                            AST.addVertex(specNode);
                            AST.addEdge(parentStack.peek(),specNode);
                        }
                        else if(memCtx.braceOrEqualInitializer()!=null)
                        {
                            ASNode initNode=new ASNode(ASNode.Type.INIT_VALUE);
                            initNode.setLineOfCode(memCtx.braceOrEqualInitializer().getStart().getLine());
                            initNode.setCode(getOriginalCodeText(memCtx.braceOrEqualInitializer()));
                            AST.addVertex(initNode);
                            AST.addEdge(parentStack.peek(),initNode);
                        }
                        parentStack.pop();
                    }
                    else
                    {
                        ASNode varNode=new ASNode(ASNode.Type.VARIABLE);
                        varNode.setLineOfCode(ctx.getStart().getLine());
                        AST.addVertex(varNode);
                        AST.addEdge(parentStack.peek(),varNode);

                        ASNode typeNode=new ASNode(ASNode.Type.TYPE);
                        typeNode.setLineOfCode(ctx.declSpecifierSeq().getStart().getLine());
                        typeNode.setCode(type);
                        AST.addVertex(typeNode);
                        AST.addEdge(varNode,typeNode);

                        ASNode nameNode=new ASNode(ASNode.Type.NAME);
                        nameNode.setLineOfCode(memCtx.getStart().getLine());
                        nameNode.setCode(memCtx.Identifier().getText());
                        ++varsCounter;
                        String normalized="$VARL_"+varsCounter;
                        nameNode.setNormalizedCode(normalized);
                        vars.put(memCtx.Identifier().getText(),normalized);
                        AST.addVertex(nameNode);
                        AST.addEdge(varNode,nameNode);

                        ASNode bitNode=new ASNode(ASNode.Type.BIT_FIELD);
                        bitNode.setLineOfCode(memCtx.constantExpression().getStart().getLine());
                        bitNode.setCode(getOriginalCodeText(memCtx.constantExpression()));
                        AST.addVertex(bitNode);
                        AST.addEdge(varNode,bitNode);
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

        @Override public String visitConstructorInitializer(CppParser.ConstructorInitializerContext ctx) {
            ASNode initNode=new ASNode(ASNode.Type.INITIALIZER);
            initNode.setLineOfCode(ctx.getStart().getLine());
            initNode.setCode(getOriginalCodeText(ctx));
            AST.addVertex(initNode);
            AST.addEdge(parentStack.peek(),initNode);
            return "";
        }
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

        @Override public String visitTemplateDeclaration(CppParser.TemplateDeclarationContext ctx) {
            //templateDeclaration:
            //	Template Less templateparameterList Greater declaration;templateDeclaration:
            //	Template Less templateparameterList Greater declaration;
            ASNode tepNode=new ASNode(ASNode.Type.TEMPLATE);
            tepNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(tepNode);
            AST.addEdge(parentStack.peek(),tepNode);
            parentStack.push(tepNode);

            ASNode parmNode=new ASNode(ASNode.Type.PARAMS);
            parmNode.setLineOfCode(ctx.templateparameterList().getStart().getLine());
            parmNode.setCode(getOriginalCodeText(ctx.templateparameterList()));
            AST.addVertex(parmNode);
            AST.addEdge(parentStack.peek(),parmNode);

            //visit(ctx.templateparameterList());

            visit(ctx.declaration());
            parentStack.pop();
            return "";
        }

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
        @Override public String visitExplicitSpecialization(CppParser.ExplicitSpecializationContext ctx) {
            //explicitSpecialization: Template Less Greater declaration;
            ASNode specNode=new ASNode(ASNode.Type.TEMPLATE_SPECIALIZATION);
            specNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(specNode);
            AST.addEdge(parentStack.peek(),specNode);
            parentStack.push(specNode);
            visit(ctx.declaration());
            parentStack.pop();
            return "";
        }

        @Override public String visitTryBlock(CppParser.TryBlockContext ctx) {
            //tryBlock: Try compoundStatement handlerSeq;
            ASNode tryNode=new ASNode(ASNode.Type.TRY);
            tryNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(tryNode);
            AST.addEdge(parentStack.peek(),tryNode);
            parentStack.push(tryNode);

            ASNode blockNode=new ASNode(ASNode.Type.BLOCK);
            blockNode.setLineOfCode(ctx.compoundStatement().getStart().getLine());
            AST.addVertex(blockNode);
            AST.addEdge(tryNode,blockNode);
            parentStack.push(blockNode);
            visit(ctx.compoundStatement());
            parentStack.pop();

            visit(ctx.handlerSeq());
            parentStack.pop();
            return "";
        }


        @Override public String visitHandlerSeq(CppParser.HandlerSeqContext ctx) {
            //handlerSeq: handler+;
            //
            //handler:
            //	Catch LeftParen exceptionDeclaration RightParen compoundStatement;
            //
            //exceptionDeclaration:
            //	attributeSpecifierSeq? typeSpecifierSeq (
            //		declarator
            //		| abstractDeclarator
            //	)?
            //	| Ellipsis;
            Logger.debug("Adding a HanelerSeqNode");
            ASNode handlerNode=new ASNode(ASNode.Type.HANDLERSEQ);
            handlerNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(handlerNode);
            AST.addEdge(parentStack.peek(),handlerNode);
            parentStack.push(handlerNode);

            for(CppParser.HandlerContext hdlCtx:ctx.handler())
            {
                visit(hdlCtx);
            }
            parentStack.pop();
            return "";
        }

        @Override public String visitHandler(CppParser.HandlerContext ctx) {
            //handler:
            //	Catch LeftParen exceptionDeclaration RightParen compoundStatement;
            ASNode hdNode=new ASNode(ASNode.Type.HANDLER);
            hdNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(hdNode);
            AST.addEdge(parentStack.peek(),hdNode);
            parentStack.push(hdNode);

            ASNode expeNode=new ASNode(ASNode.Type.EXCEPT);
            expeNode.setLineOfCode(ctx.exceptionDeclaration().getStart().getLine());
            expeNode.setCode(getOriginalCodeText(ctx.exceptionDeclaration()));
            AST.addVertex(expeNode);
            AST.addEdge(parentStack.peek(),expeNode);

            visit(ctx.compoundStatement());
            parentStack.pop();

            return "";
        }
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
        @Override public String visitNoeExceptSpecification(CppParser.NoeExceptSpecificationContext ctx)
        //Noexcept LeftParen constantExpression RightParen| Noexcept;
        {
            if(ctx.Noexcept()!=null){
                return ctx.Noexcept().getText();
            }
            return ctx.Noexcept().getText()+" "+ctx.LeftParen().getText()+" "+visit(ctx.constantExpression())+" "+ctx.RightParen().getText();
        }
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
        @Override public String visitLiteral(CppParser.LiteralContext ctx)
        {
            if (ctx.IntegerLiteral() != null) {
                return ctx.IntegerLiteral().getText();
            } else if (ctx.CharacterLiteral() != null)
            {return ctx.CharacterLiteral().getText();}
            else if (ctx.FloatingLiteral() != null) {
                return ctx.FloatingLiteral().getText();
            } else if (ctx.StringLiteral() != null) {
                return ctx.StringLiteral().getText();
            } else if (ctx.BooleanLiteral() != null) {
                return ctx.BooleanLiteral().getText();
            } else if (ctx.PointerLiteral() != null) {
                return ctx.PointerLiteral().getText();
            }
            return ctx.UserDefinedLiteral().getText();
        }



        //=====================================================================//
        //                          PRIVATE METHODS                            //
        //=====================================================================//

        /**
         * Get the original program text for the given parser-rule context.
         * This is required for preserving white-spaces.
         */
        private String getOriginalCodeText(ParserRuleContext ctx) {
            if (ctx == null)
                return "";
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
        }

    }
}