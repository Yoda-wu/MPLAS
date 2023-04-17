package com.scut.mplas.javascript;

import com.scut.mplas.graphs.pdg.CDEdge;
import com.scut.mplas.graphs.pdg.ControlDependenceGraph;
import com.scut.mplas.graphs.pdg.PDNode;
import com.scut.mplas.java.parser.JavaBaseVisitor;
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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class JavaScriptCDGBuilder {

    public static ControlDependenceGraph build(File jsFile) throws IOException {
        if (!jsFile.getName().endsWith(".js"))
            throw new IOException("Not a JavaScript File!");
        InputStream inFile = new FileInputStream(jsFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        JavaScriptLexer lexer = new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();
        Logger.debug("CTRL DEP ANALYSIS: " + jsFile.getPath());
        ControlDependenceGraph cdg = new ControlDependenceGraph(jsFile.getName());
        ControlDependencyVisitor visitor = new ControlDependencyVisitor(cdg);
        visitor.visit(tree);
        return cdg;
    }


    private static class ControlDependencyVisitor extends JavaScriptBaseVisitor<Void> {

        private ControlDependenceGraph cdg;
        private Deque<PDNode> ctrlDeps;
        private Deque<PDNode> negDeps;
        private Deque<Integer> jmpCounts;
        private Deque<PDNode> jumpDeps;
        private boolean buildRegion;
        private boolean follows;
        private int lastFollowDepth;
        private int regionCounter;
        private int jmpCounter;

        public ControlDependencyVisitor(ControlDependenceGraph cdg) {
            this.cdg = cdg;
            ctrlDeps = new ArrayDeque<>();
            negDeps = new ArrayDeque<>();
            jumpDeps = new ArrayDeque<>();
            jmpCounts = new ArrayDeque<>();
            buildRegion = false;
            follows = true;
            lastFollowDepth = 0;
            regionCounter = 1;
            jmpCounter = 0;
        }

        private void init() {
            ctrlDeps.clear();
            negDeps.clear();
            jumpDeps.clear();
            jmpCounts.clear();
            buildRegion = false;
            follows = true;
            lastFollowDepth = 0;
            regionCounter = 1;
            jmpCounter = 0;
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

        @Override
        public Void visitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) {
            //    : Continue ({this.notLineTerminator()}? identifier)? eos
            PDNode cnt = new PDNode();
            cnt.setLineOfCode(ctx.getStart().getLine());
            cnt.setCode(getOriginalCodeText(ctx));
            addNodeEdge(cnt);
            // NOTE: an important assumption here is that 'continue'
            //       is the last statement inside an if-else body
            if (!negDeps.isEmpty() && ctrlDeps.size() >= lastFollowDepth) {
                jumpDeps.push(negDeps.peek());
                jumpDeps.peek().setProperty("isExit", Boolean.FALSE);
                jumpDeps.peek().setProperty("isJump", Boolean.TRUE);
                lastFollowDepth = ctrlDeps.size();
                buildRegion = true;
            }
            return null;
        }

        @Override
        public Void visitBreakStatement(JavaScriptParser.BreakStatementContext ctx) {
            //: Break ({this.notLineTerminator()}? identifier)? eos
            PDNode brk = new PDNode();
            brk.setLineOfCode(ctx.getStart().getLine());
            brk.setCode(getOriginalCodeText(ctx));
            addNodeEdge(brk);
            //
            // Check for the special case of a 'break' inside a 'default' switch-block:
            if (!negDeps.isEmpty() && negDeps.peek().getCode().startsWith("default"))
                return null; // just ignore it, and do nothing!
            //
            // NOTE: an important assumption here is that 'break'
            //       is the last statement inside an if-else body,
            //       or it's the last statement inside a case-block
            if (!negDeps.isEmpty() && ctrlDeps.size() >= lastFollowDepth) {
                jumpDeps.push(negDeps.peek());
                jumpDeps.peek().setProperty("isExit", Boolean.FALSE);
                jumpDeps.peek().setProperty("isJump", Boolean.TRUE);
                lastFollowDepth = ctrlDeps.size();
                buildRegion = true;
            }
            return null;
        }

        @Override
        public Void visitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) {
            //    : Return ({this.notLineTerminator()}? expressionSequence)? eos
            PDNode ret = new PDNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addNodeEdge(ret);
            // NOTE: an important assumption here is that 'return'
            //       is the last statement inside an if-else body
            //       or it's the last statement of the entire method
            if (!negDeps.isEmpty() && ctrlDeps.size() >= lastFollowDepth) {
                jumpDeps.push(negDeps.peek());
                jumpDeps.peek().setProperty("isExit", Boolean.TRUE);
                jumpDeps.peek().setProperty("isJump", Boolean.FALSE);
                lastFollowDepth = ctrlDeps.size();
                buildRegion = true;
            }
            return visitChildren(ctx);
        }

        @Override public Void visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
            //       : Yield ({this.notLineTerminator()}? expressionSequence)? eos
            PDNode ret = new PDNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addNodeEdge(ret);
            // NOTE: an important assumption here is that 'yield'
            //       is the last statement inside an if-else body
            //       or it's the last statement of the entire method
            if (!negDeps.isEmpty() && ctrlDeps.size() >= lastFollowDepth) {
                jumpDeps.push(negDeps.peek());
                jumpDeps.peek().setProperty("isExit", Boolean.TRUE);
                jumpDeps.peek().setProperty("isJump", Boolean.FALSE);
                lastFollowDepth = ctrlDeps.size();
                buildRegion = true;
            }
            return visitChildren(ctx);
        }
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
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public Void visitClassTail(JavaScriptParser.ClassTailContext ctx) { return visitChildren(ctx); }
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
         * Add given node to the CD-subgraph and
         * create a new CD-edge based on the last control-dependency.
         */
        private void addNodeEdge(PDNode node) {
            checkBuildFollowRegion();
            cdg.addVertex(node);
            cdg.addEdge(new Edge<>(ctrlDeps.peek(), new CDEdge(CDEdge.Type.EPSILON), node));
        }

        /**
         * Check if a follow-region must be created;
         * if so, create it and push it on the CTRL-dependence stack.
         */
        private void checkBuildFollowRegion() {
            Logger.debug("FOLLOWS = " + follows);
            Logger.debug("BUILD-REGION = " + buildRegion);
            if (buildRegion && follows) {
                PDNode followRegion = new PDNode();
                followRegion.setLineOfCode(0);
                followRegion.setCode("FOLLOW-" + regionCounter++);
                cdg.addVertex(followRegion);
                // check to see if there are any exit-jumps in the current chain
                followRegion.setProperty("isJump", Boolean.TRUE);
                for (PDNode dep: jumpDeps)
                    if ((Boolean) dep.getProperty("isExit")) {
                        followRegion.setProperty("isJump", Boolean.FALSE);
                        followRegion.setProperty("isExit", Boolean.TRUE);
                    }
                if ((Boolean) followRegion.getProperty("isJump"))
                    ++jmpCounter;
                // connect the follow-region
                if (Boolean.TRUE.equals(jumpDeps.peek().getProperty("isTry"))) {
                    PDNode jmpDep = jumpDeps.pop();
                    if (!cdg.containsVertex(jmpDep))
                        cdg.addVertex(jmpDep);
                    cdg.addEdge(new Edge<>(jmpDep, new CDEdge(CDEdge.Type.NOT_THROWS), followRegion));
                } else {
                    PDNode jmpDep = jumpDeps.pop();
                    if (!cdg.containsVertex(jmpDep))
                        cdg.addVertex(jmpDep);
                    cdg.addEdge(new Edge<>(jmpDep, new CDEdge(CDEdge.Type.EPSILON), followRegion));
                }
                // if the jump-chain is not empty, remove all non-exit jumps
                if (!jumpDeps.isEmpty()) {
                    for (Iterator<PDNode> itr = jumpDeps.iterator(); itr.hasNext(); ) {
                        PDNode dep = itr.next();
                        if (Boolean.FALSE.equals(dep.getProperty("isExit")))
                            itr.remove();
                    }
                }
                lastFollowDepth = 0;
                pushCtrlDep(followRegion);
            }
        }

        /**
         * Push given node to the control-dependency stack.
         */
        private void pushCtrlDep(PDNode dep) {
            ctrlDeps.push(dep);
            buildRegion = false;
        }

        /**
         * Push this loop block region to the control-dependency stack
         * and reset the jumps-counter for this loop-block.
         */
        private void pushLoopBlockDep(PDNode region) {
            pushCtrlDep(region);
            jmpCounts.push(jmpCounter);
            jmpCounter = 0;
        }

        /**
         * Pop out the last dependency off the stack and
         * set the 'buildRegion' flag if necessary.
         */
        private void popCtrlDep(PDNode dep) {
            ctrlDeps.remove(dep); //ctrlDeps.pop();
            buildRegion = !jumpDeps.isEmpty();
        }

        /**
         * Pop out this loop-block region off the control stack
         * and also pop off all jump-dependencies of this block.
         */
        private void popLoopBlockDep(PDNode region) {
            for (Iterator<PDNode> itr = ctrlDeps.iterator(); jmpCounter > 0 && itr.hasNext(); ) {
                // NOTE: This iteration works correctly, even though ctrlDeps is a stack.
                //       This is due to the Deque implementation, which removes in LIFO.
                PDNode dep = itr.next();
                if (Boolean.TRUE.equals(dep.getProperty("isJump"))) {
                    itr.remove();
                    --jmpCounter;
                }
            }
            jmpCounter = jmpCounts.pop();
            lastFollowDepth = 0;
            popCtrlDep(region);
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
}