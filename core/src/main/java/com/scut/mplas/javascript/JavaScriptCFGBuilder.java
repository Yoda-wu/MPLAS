package com.scut.mplas.javascript;

import com.scut.mplas.graphs.cfg.CFEdge;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.java.JavaCFGBuilder;
import com.scut.mplas.java.parser.JavaLexer;
import com.scut.mplas.java.parser.JavaParser;
import com.scut.mplas.javascript.parser.JavaScriptBaseVisitor;
import com.scut.mplas.javascript.parser.JavaScriptLexer;
import com.scut.mplas.javascript.parser.JavaScriptParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
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
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
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
    public static ControlFlowGraph build(String javaFileName, ParseTree tree,
                                         String propKey, Map<ParserRuleContext, Object> ctxProps) {
        ControlFlowGraph cfg = new ControlFlowGraph(javaFileName);
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

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitIfStatement(JavaScriptParser.IfStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitDoStatement(JavaScriptParser.DoStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitWhileStatement(JavaScriptParser.WhileStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitForStatement(JavaScriptParser.ForStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitForInStatement(JavaScriptParser.ForInStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitForOfStatement(JavaScriptParser.ForOfStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitVarModifier(JavaScriptParser.VarModifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitBreakStatement(JavaScriptParser.BreakStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitWithStatement(JavaScriptParser.WithStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitCaseBlock(JavaScriptParser.CaseBlockContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitCaseClauses(JavaScriptParser.CaseClausesContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitCaseClause(JavaScriptParser.CaseClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitDefaultClause(JavaScriptParser.DefaultClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitTryStatement(JavaScriptParser.TryStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitCatchProduction(JavaScriptParser.CatchProductionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitFinallyProduction(JavaScriptParser.FinallyProductionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) { return visitChildren(ctx); }

        @Override public Void visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
            //Class identifier classTail
            classNames.push(ctx.identifier().Identifier().getText());
            visit(ctx.classTail());
            classNames.pop();
            return null;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitClassTail(JavaScriptParser.ClassTailContext ctx) {
            //    : (Extends singleExpression)? '{' classElement* '}'

            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitClassElement(JavaScriptParser.ClassElementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) { return visitChildren(ctx); }
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
