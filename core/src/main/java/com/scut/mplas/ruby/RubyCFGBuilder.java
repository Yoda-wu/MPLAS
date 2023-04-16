package com.scut.mplas.ruby;

import com.scut.mplas.graphs.cfg.CFEdge;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.java.JavaCFGBuilder;
import com.scut.mplas.java.parser.JavaBaseVisitor;
import com.scut.mplas.java.parser.JavaLexer;
import com.scut.mplas.java.parser.JavaParser;
import com.scut.mplas.ruby.parser.RubyBaseVisitor;
import com.scut.mplas.ruby.parser.RubyLexer;
import com.scut.mplas.ruby.parser.RubyParser;
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

public class RubyCFGBuilder {

    /**
     * ‌Build and return the Control Flow Graph (CFG) for the given ruby source file.
     */
    public static ControlFlowGraph build(String javaFile) throws IOException {
        return build(new File(javaFile));
    }

    /**
     * ‌Build and return the Control Flow Graph (CFG) for the given ruby source file.
     */
    public static ControlFlowGraph build(File javaFile) throws IOException {
        if (!javaFile.getName().endsWith(".java"))
            throw new IOException("Not a Java File!");
        InputStream inFile = new FileInputStream(javaFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        RubyLexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RubyParser parser = new RubyParser(tokens);
        ParseTree tree = parser.prog();
        return build(javaFile.getName(), tree, null, null);
    }

    public static ControlFlowGraph build(String fileName, InputStream inputStream) throws IOException {

        InputStream inFile = inputStream;
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        RubyLexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RubyParser parser = new RubyParser(tokens);
        ParseTree tree = parser.prog();
        return build(fileName, tree, null, null);
    }


    /**
     * Build and return the Control Flow Graph (CFG) for the given Parse-Tree.
     * The 'ctxProps' map includes contextual-properties for particular nodes
     * in the parse-tree, which can be used for linking this graph with other
     * graphs by using the same parse-tree and the same contextual-properties.
     */
    public static ControlFlowGraph build(String javaFileName, ParseTree tree,
                                         String propKey, Map<ParserRuleContext, Object> ctxProps) {
        ControlFlowGraph cfg = new ControlFlowGraph(javaFileName);
        RubyCFGBuilder.ControlFlowVisitor visitor = new RubyCFGBuilder.ControlFlowVisitor(cfg, propKey, ctxProps);
        visitor.visit(tree);
        return cfg;
    }
    private static class ControlFlowVisitor extends RubyBaseVisitor<Void> {
        private ControlFlowGraph cfg;
        private Deque<CFNode> preNodes;
        private Deque<CFEdge.Type> preEdges;
        private Queue<CFNode> casesQueue;
        private Deque<Block> loopBlocks;
        private boolean dontPop;
        private String propKey;
        private Map<ParserRuleContext, Object> contexutalProperties;
        private Deque<String> classNames;

        public ControlFlowVisitor(ControlFlowGraph cfg, String propKey, Map<ParserRuleContext, Object> ctxProps) {
            preNodes = new ArrayDeque<>();
            preEdges = new ArrayDeque<>();
            loopBlocks = new ArrayDeque<>();
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
//            labeledBlocks.clear();
//            tryBlocks.clear();
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
         *
         * @param ctx
         */
        @Override
        public Void visitProg(RubyParser.ProgContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public Void visitExpression_list(RubyParser.Expression_listContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public Void visitFunction_definition(RubyParser.Function_definitionContext ctx) {
            // function_definition : function_definition_header function_definition_body END;
            init();

            CFNode entry = new CFNode();
            entry.setLineOfCode(ctx.getStart().getLine());
            String parameters =getOriginalCodeText(ctx.function_definition_header().function_definition_params());
            String funcName = getOriginalCodeText(ctx.function_definition_header().function_name());
            entry.setCode(funcName + " "+parameters);
            addContextualProperty(entry, ctx);
            cfg.addVertex(entry);

            entry.setProperty("name", funcName);
            entry.setProperty("args",parameters);
            cfg.addMethodEntry(entry);

            preNodes.push(entry);
            preEdges.push(CFEdge.Type.EPSILON);
            return visitChildren(ctx);
        }


        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public Void visitReturn_statement(RubyParser.Return_statementContext ctx) {
            // return_statement : RETURN all_result;
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addContextualProperty(ret,ctx);
            addNodeAndPreEdge(ret);
            dontPop = true;
            return null;
        }


        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public Void visitWhile_statement(RubyParser.While_statementContext ctx) {
            CFNode whileNode = new CFNode();
            whileNode.setLineOfCode(ctx.getStart().getLine());
            whileNode.setCode("while "+getOriginalCodeText(ctx.cond_expression()));
            addContextualProperty(whileNode,ctx);
            addNodeAndPreEdge(whileNode);

            CFNode endWhile = new CFNode();
            endWhile.setLineOfCode(0);
            endWhile.setCode("END WHILE");

            cfg.addVertex(endWhile);
            cfg.addEdge(new Edge<>(whileNode, new CFEdge(CFEdge.Type.FALSE), endWhile));

            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(whileNode);
            loopBlocks.push(new Block(whileNode, endWhile));
            visit(ctx.statement_body());
            loopBlocks.pop();
            popAddPreEdgeTo(whileNode);

            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endWhile);
            return null ;
        }


        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public Void visitFor_statement(RubyParser.For_statementContext ctx) {
            // for_statement : FOR LEFT_RBRACKET init_expression SEMICOLON cond_expression SEMICOLON loop_expression RIGHT_RBRACKET crlf statement_body END
            //              | FOR init_expression SEMICOLON cond_expression SEMICOLON loop_expression crlf statement_body END
            //              ;

            // init_expression
            CFNode forInit = new CFNode();
            forInit.setLineOfCode(ctx.init_expression().for_init_list().getStart().getLine());
            forInit.setCode(getOriginalCodeText(ctx.init_expression()));
            addContextualProperty(forInit, ctx.init_expression());
            addNodeAndPreEdge(forInit);

            // cond_expression

            // loop_expression


            // statement
            return null;
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
