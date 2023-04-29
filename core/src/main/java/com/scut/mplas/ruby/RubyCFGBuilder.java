package com.scut.mplas.ruby;

import com.scut.mplas.graphs.cfg.CFEdge;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.ruby.parser.old.RubyBaseVisitor;
import com.scut.mplas.ruby.parser.old.RubyLexer;
import com.scut.mplas.ruby.parser.old.RubyParser;
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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Queue;

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
        public Void visitExpression(RubyParser.ExpressionContext ctx) {
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
            String parameters = getOriginalCodeText(ctx.function_definition_header().function_definition_params());
            String funcName = getOriginalCodeText(ctx.function_definition_header().function_name());
            entry.setCode(funcName + " " + parameters);
            addContextualProperty(entry, ctx);
            cfg.addVertex(entry);

            entry.setProperty("name", funcName);
            entry.setProperty("args", parameters);
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
        public Void visitFunction_inline_call(RubyParser.Function_inline_callContext ctx) {
            CFNode func = new CFNode();
            func.setLineOfCode(ctx.getStart().getLine());
            String parameters = getOriginalCodeText(ctx.function_call().function_call_param_list());
            String funcName = getOriginalCodeText(ctx.function_call().function_name());
            func.setCode(funcName + " " + parameters);
            addContextualProperty(func, ctx);
            cfg.addVertex(func);

            func.setProperty("name", funcName);
            func.setProperty("args", parameters);
            cfg.addMethodEntry(func);

            preNodes.push(func);
            preEdges.push(CFEdge.Type.EPSILON);
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
        public Void visitRequire_block(RubyParser.Require_blockContext ctx) {

            CFNode requireBlock = new CFNode();
            requireBlock.setLineOfCode(ctx.getStart().getLine());
            requireBlock.setCode("require" + getOriginalCodeText(ctx.literal_t()));
            addContextualProperty(requireBlock, ctx);
            addNodeAndPreEdge(requireBlock);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(requireBlock);
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
        public Void visitIf_statement(RubyParser.If_statementContext ctx) {
            //if_statement : IF cond_expression crlf statement_body END
            //             | IF cond_expression crlf statement_body else_token crlf statement_body END
            //             | IF cond_expression crlf statement_body elsif_statement END
            //             ;
            CFNode ifNode = new CFNode();
            ifNode.setLineOfCode(ctx.getStart().getLine());
            ifNode.setCode("if " + getOriginalCodeText(ctx.cond_expression()));
            addContextualProperty(ifNode, ctx);
            addNodeAndPreEdge(ifNode);

            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(ifNode);
            visit(ctx.statement_body(0));
            CFNode endIf = new CFNode();
            endIf.setLineOfCode(0);
            endIf.setCode("endif");
            addNodeAndPreEdge(endIf);

            if (ctx.else_token() != null) {
                preEdges.push(CFEdge.Type.FALSE);
                preNodes.push(ifNode);
                visit(ctx.statement_body(1));
                popAddPreEdgeTo(endIf);
            } else if (ctx.elsif_statement() != null) {
                preEdges.push(CFEdge.Type.FALSE);
                preNodes.push(ifNode);
                visit(ctx.elsif_statement());
                popAddPreEdgeTo(endIf);
            } else {
                cfg.addEdge(new Edge<>(ifNode, new CFEdge(CFEdge.Type.FALSE), endIf));
            }
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endIf);
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
        public Void visitElsif_statement(RubyParser.Elsif_statementContext ctx) {
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
        public Void visitIf_elsif_statement(RubyParser.If_elsif_statementContext ctx) {
            // if_elsif_statement : ELSIF cond_expression crlf statement_body
            //                   | ELSIF cond_expression crlf statement_body else_token crlf statement_body
            //                   | ELSIF cond_expression crlf statement_body if_elsif_statement
            //                   ;
            CFNode elseIf = new CFNode();
            elseIf.setLineOfCode(ctx.getStart().getLine());
            elseIf.setCode("elseif " + getOriginalCodeText(ctx.cond_expression()));
            addContextualProperty(elseIf, ctx);
            addNodeAndPreEdge(elseIf);

            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(elseIf);
            visit(ctx.statement_body(0));

            CFNode endIf = new CFNode();
            endIf.setLineOfCode(0);
            endIf.setCode("endif");
            addNodeAndPreEdge(endIf);
            if (ctx.else_token() != null) {
                preEdges.push(CFEdge.Type.FALSE);
                preNodes.push(elseIf);
                visit(ctx.statement_body(1));
                popAddPreEdgeTo(endIf);
            } else if (ctx.if_elsif_statement() != null) {
                preEdges.push(CFEdge.Type.FALSE);
                preNodes.push(elseIf);
                visit(ctx.if_elsif_statement());
                popAddPreEdgeTo(endIf);
            } else {
                cfg.addEdge(new Edge<>(elseIf, new CFEdge(CFEdge.Type.FALSE), endIf));
            }
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endIf);
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
        public Void visitUnless_statement(RubyParser.Unless_statementContext ctx) {
            CFNode unless = new CFNode();
            unless.setLineOfCode(ctx.getStart().getLine());
            unless.setCode("unless " + getOriginalCodeText(ctx.cond_expression()));
            addContextualProperty(unless, ctx);
            addNodeAndPreEdge(unless);

            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(unless);
            visit(ctx.statement_body(0));
            CFNode endUnless = new CFNode();
            endUnless.setLineOfCode(0);
            endUnless.setCode("endif");
            addNodeAndPreEdge(endUnless);

            if (ctx.else_token() != null) {
                preEdges.push(CFEdge.Type.FALSE);
                preNodes.push(unless);
                visit(ctx.statement_body(1));
                popAddPreEdgeTo(endUnless);
            } else if (ctx.elsif_statement() != null) {
                preEdges.push(CFEdge.Type.FALSE);
                preNodes.push(unless);
                visit(ctx.elsif_statement());
                popAddPreEdgeTo(endUnless);
            } else {
                cfg.addEdge(new Edge<>(unless, new CFEdge(CFEdge.Type.FALSE), endUnless));
            }
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(endUnless);
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
        public Void visitRvalue(RubyParser.RvalueContext ctx) {
            CFNode rvalue = new CFNode();
            rvalue.setLineOfCode(ctx.getStart().getLine());
            rvalue.setCode(getOriginalCodeText(ctx));
            addContextualProperty(rvalue, ctx);
            addNodeAndPreEdge(rvalue);
            //
            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(rvalue);
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
        public Void visitReturn_statement(RubyParser.Return_statementContext ctx) {
            // return_statement : RETURN all_result;
            CFNode ret = new CFNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addContextualProperty(ret, ctx);
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
            whileNode.setCode("while " + getOriginalCodeText(ctx.cond_expression()));
            addContextualProperty(whileNode, ctx);
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
            CFNode condNode = new CFNode();
            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
            condNode.setCode("for (" + getOriginalCodeText(ctx.cond_expression()) + ")");
            addContextualProperty(condNode, ctx.cond_expression());
            cfg.addVertex(condNode);
            cfg.addEdge(new Edge<>(forInit, new CFEdge(CFEdge.Type.EPSILON), condNode));
            // loop_expression
            CFNode forUpdate = new CFNode();
            forUpdate.setCode(getOriginalCodeText(ctx.loop_expression()));
            forUpdate.setLineOfCode(ctx.loop_expression().getStart().getLine());
            addContextualProperty(forUpdate, ctx.loop_expression());
            cfg.addVertex(forUpdate);

            // for-end
            CFNode forEnd = new CFNode();
            forEnd.setLineOfCode(0);
            forEnd.setCode("end for");
            cfg.addVertex(forEnd);
            cfg.addEdge(new Edge<>(condNode, new CFEdge(CFEdge.Type.FALSE), forEnd));
            // for-body-statement

            preEdges.push(CFEdge.Type.TRUE);
            preNodes.push(condNode);
            loopBlocks.push(new Block(forUpdate, forEnd));
            visit(ctx.statement_body());
            loopBlocks.pop();

            popAddPreEdgeTo(forUpdate);
            cfg.addEdge(new Edge<>(forUpdate, new CFEdge(CFEdge.Type.EPSILON), condNode));

            preEdges.push(CFEdge.Type.EPSILON);
            preNodes.push(forEnd);
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
        public Void visitStatement_body(RubyParser.Statement_bodyContext ctx) {
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
        public Void visitStatement_expression_list(RubyParser.Statement_expression_listContext ctx) {
            if (ctx.statement_expression_list() != null) {
                return visit(ctx.statement_expression_list());
            }
            if (ctx.expression() != null) {
                return visit(ctx.expression());
            }
            if (ctx.break_expression() != null) {
                return visit(ctx.break_expression());
            }
            if (ctx.RETRY() != null) {
                CFNode retry = new CFNode();
                retry.setCode("retry");
                retry.setLineOfCode(ctx.getStart().getLine());
                addContextualProperty(retry, ctx);
                addNodeAndPreEdge(retry);

                preEdges.push(CFEdge.Type.EPSILON);
                preNodes.push(retry);
            }

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
        public Void visitBreak_expression(RubyParser.Break_expressionContext ctx) {
            CFNode breakNode = new CFNode();
            breakNode.setLineOfCode(ctx.getStart().getLine());
            breakNode.setCode(getOriginalCodeText(ctx));
            addContextualProperty(breakNode, ctx);
            addNodeAndPreEdge(breakNode);

            Block block = loopBlocks.peek();
            cfg.addEdge(new Edge<>(breakNode, new CFEdge(CFEdge.Type.EPSILON), block.end));

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
