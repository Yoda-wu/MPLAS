package com.scut.mplas.javascript;

import com.scut.mplas.graphs.pdg.CDEdge;
import com.scut.mplas.graphs.pdg.ControlDependenceGraph;
import com.scut.mplas.graphs.pdg.PDNode;
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
        @Override
        public Void visitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public Void visitIfStatement(JavaScriptParser.IfStatementContext ctx) {
            //    : If '(' expressionSequence ')' statement (Else statement)?
            PDNode ifNode = new PDNode();
            ifNode.setLineOfCode(ctx.getStart().getLine());
            ifNode.setCode("if " + getOriginalCodeText(ctx.expressionSequence()));
            addNodeEdge(ifNode);
            //
            PDNode thenRegion = new PDNode();
            thenRegion.setLineOfCode(0);
            thenRegion.setCode("THEN");
            cdg.addVertex(thenRegion);
            cdg.addEdge(new Edge<>(ifNode, new CDEdge(CDEdge.Type.TRUE), thenRegion));
            //
            PDNode elseRegion = new PDNode();
            elseRegion.setLineOfCode(0);
            elseRegion.setCode("ELSE");
            //
            pushCtrlDep(thenRegion);
            negDeps.push(elseRegion);
            visit(ctx.statement(0));
            negDeps.pop();
            popCtrlDep(thenRegion);
            //
            if (ctx.statement().size() > 1) { // if with else
                follows = false;
                cdg.addVertex(elseRegion);
                cdg.addEdge(new Edge<>(ifNode, new CDEdge(CDEdge.Type.FALSE), elseRegion));
                //
                pushCtrlDep(elseRegion);
                negDeps.push(thenRegion);
                visit(ctx.statement(1));
                negDeps.pop();
                popCtrlDep(elseRegion);
            } else if (buildRegion) {
                // there is no else, but we need to add the ELSE region
                cdg.addVertex(elseRegion);
                cdg.addEdge(new Edge<>(ifNode, new CDEdge(CDEdge.Type.FALSE), elseRegion));
            }
            follows = true;
            return null;
        }

        @Override
        public Void visitDoStatement(JavaScriptParser.DoStatementContext ctx) {
            // : Do statement While '(' expressionSequence ')' eos
// 'do' statement 'while' parExpression ';'
            PDNode doRegion = new PDNode();
            doRegion.setLineOfCode(ctx.getStart().getLine());
            doRegion.setCode("do");
            addNodeEdge(doRegion);
            //
            pushLoopBlockDep(doRegion);
            visit(ctx.statement());
            // the while-node is treated as the last statement of the loop
            PDNode whileNode = new PDNode();
            whileNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            whileNode.setCode("while " + getOriginalCodeText(ctx.expressionSequence()));
            addNodeEdge(whileNode);
            //
            popLoopBlockDep(doRegion);
            // this TRUE edge was removed, because only the repitition of
            // the block statements is dependent on the while-predicate
            // cds.addEdge(whileNode, doRegion, new CDEdge(CDEdge.Type.TRUE));
            return null;
        }

        @Override
        public Void visitWhileStatement(JavaScriptParser.WhileStatementContext ctx) {
            //    | While '(' expressionSequence ')' statement                                                                              # WhileStatement
            // 'while' parExpression statement
            PDNode whileNode = new PDNode();
            whileNode.setLineOfCode(ctx.getStart().getLine());
            whileNode.setCode("while " + getOriginalCodeText(ctx.expressionSequence()));
            addNodeEdge(whileNode);
            //
            PDNode loopRegion = new PDNode();
            loopRegion.setLineOfCode(0);
            loopRegion.setCode("LOOP");
            cdg.addVertex(loopRegion);
            cdg.addEdge(new Edge<>(whileNode, new CDEdge(CDEdge.Type.TRUE), loopRegion));
            //
            pushLoopBlockDep(loopRegion);
            visit(ctx.statement());
            popLoopBlockDep(loopRegion);
            return null;
        }

        @Override
        public Void visitForStatement(JavaScriptParser.ForStatementContext ctx) {
            //    | For '(' (expressionSequence | variableDeclarationList)? ';' expressionSequence? ';' expressionSequence? ')' statement   # ForStatement
            //  First, we should check type of for-loop ...
            // It's a traditional for-loop:
            //   forInit? ';' expression? ';' forUpdate?
            PDNode forInit;
            PDNode forExpr, forUpdate;
            if (ctx.expressionSequence().size() == 3) {
                forInit = new PDNode();
                forInit.setLineOfCode(ctx.expressionSequence(0).getStart().getLine());
                forInit.setCode(getOriginalCodeText(ctx.expressionSequence(0)));
                addNodeEdge(forInit);
            }
            if (ctx.variableDeclarationList() != null && !ctx.variableDeclarationList().isEmpty()) {
                forInit = new PDNode();
                forInit.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                forInit.setCode(getOriginalCodeText(ctx.variableDeclarationList()));
                addNodeEdge(forInit);
            }
            int updateIndex = 0;
            if (ctx.expressionSequence().size() == 3) {
                updateIndex++;
            }
            int forExprLine;
            String forExprCode;
            if (ctx.expressionSequence(updateIndex) == null) { // empty for-loop-predicate
                forExprCode = ";";
                forExprLine = ctx.getStart().getLine();
            } else {
                forExprCode = getOriginalCodeText(ctx.expressionSequence(updateIndex));
                forExprLine = ctx.expressionSequence(updateIndex).getStart().getLine();
            }
            forExpr = new PDNode();
            forExpr.setLineOfCode(forExprLine);
            forExpr.setCode("for (" + forExprCode + ")");
            addNodeEdge(forExpr);
            //
            PDNode loopRegion = new PDNode();
            loopRegion.setLineOfCode(0);
            loopRegion.setCode("LOOP");
            cdg.addVertex(loopRegion);
            cdg.addEdge(new Edge<>(forExpr, new CDEdge(CDEdge.Type.TRUE), loopRegion));
            //
            pushLoopBlockDep(loopRegion);
            visit(ctx.statement());
            if (ctx.expressionSequence(updateIndex + 1) != null) { // non-empty for-update
                forUpdate = new PDNode();
                forUpdate.setLineOfCode(ctx.expressionSequence(updateIndex + 1).getStart().getLine());
                forUpdate.setCode(getOriginalCodeText(ctx.expressionSequence(updateIndex + 1)));
                // we don't use 'addNodeEdge(forUpdate)' because the behavior of for-update
                // step is different from other statements with regards to break/continue.
                cdg.addVertex(forUpdate);
                cdg.addEdge(new Edge<>(ctrlDeps.peek(), new CDEdge(CDEdge.Type.EPSILON), forUpdate));
                popLoopBlockDep(loopRegion);
            }
            return null;
        }

        @Override
        public Void visitForInStatement(JavaScriptParser.ForInStatementContext ctx) {
            //   | For '(' (singleExpression | variableDeclarationList) In expressionSequence ')' statement                                # ForInStatement
//  First, we should check type of for-loop ...
            // This is a for-each loop;
            //   enhancedForControl:
            //     variableModifier* typeType variableDeclaratorId ':' expression
            PDNode forExpr = new PDNode();
            if (ctx.singleExpression() != null && !ctx.singleExpression().isEmpty()) {
                forExpr.setLineOfCode(ctx.singleExpression().getStart().getLine());
                forExpr.setCode("for (" + getOriginalCodeText(ctx.singleExpression()) + ctx.In().getText() + getOriginalCodeText(ctx.expressionSequence()) + ")");
            } else {
                forExpr.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                forExpr.setCode("for (" + getOriginalCodeText(ctx.variableDeclarationList()) + ctx.In().getText() + getOriginalCodeText(ctx.expressionSequence()) + ")");
            }
            addNodeEdge(forExpr);
            //
            PDNode loopRegion = new PDNode();
            loopRegion.setLineOfCode(0);
            loopRegion.setCode("LOOP");
            cdg.addVertex(loopRegion);
            cdg.addEdge(new Edge<>(forExpr, new CDEdge(CDEdge.Type.TRUE), loopRegion));
            //
            pushLoopBlockDep(loopRegion);
            visit(ctx.statement());
            popLoopBlockDep(loopRegion);
            return null;
        }

        @Override
        public Void visitForOfStatement(JavaScriptParser.ForOfStatementContext ctx) {
            //  | For Await? '(' (singleExpression | variableDeclarationList) identifier{this.p("of")}? expressionSequence ')' statement  # ForOfStatement
            PDNode forExpr = new PDNode();
            if (ctx.singleExpression() != null && !ctx.singleExpression().isEmpty()) {
                forExpr.setLineOfCode(ctx.singleExpression().getStart().getLine());
                forExpr.setCode("for (" + getOriginalCodeText(ctx.singleExpression()) + getOriginalCodeText(ctx.identifier()) + getOriginalCodeText(ctx.expressionSequence()) + ")");
            } else {
                forExpr.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                forExpr.setCode("for (" + getOriginalCodeText(ctx.variableDeclarationList()) + getOriginalCodeText(ctx.identifier()) + getOriginalCodeText(ctx.expressionSequence()) + ")");
            }
            addNodeEdge(forExpr);
            //
            PDNode loopRegion = new PDNode();
            loopRegion.setLineOfCode(0);
            loopRegion.setCode("LOOP");
            cdg.addVertex(loopRegion);
            cdg.addEdge(new Edge<>(forExpr, new CDEdge(CDEdge.Type.TRUE), loopRegion));
            //
            pushLoopBlockDep(loopRegion);
            visit(ctx.statement());
            popLoopBlockDep(loopRegion);
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public Void visitVarModifier(JavaScriptParser.VarModifierContext ctx) {
            return visitChildren(ctx);
        }

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

        @Override
        public Void visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
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

        @Override
        public Void visitWithStatement(JavaScriptParser.WithStatementContext ctx) {
            //    : With '(' expressionSequence ')' statement
            PDNode ret = new PDNode();
            ret.setLineOfCode(ctx.getStart().getLine());
            ret.setCode(getOriginalCodeText(ctx));
            addNodeEdge(ret);
            // NOTE: an important assumption here is that 'with'
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

        @Override
        public Void visitSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) {
            //     : Switch '(' expressionSequence ')' caseBlock
            PDNode switchNode = new PDNode();
            switchNode.setLineOfCode(ctx.getStart().getLine());
            switchNode.setCode("switch " + getOriginalCodeText(ctx.expressionSequence()));
            addNodeEdge(switchNode);
            //
            pushLoopBlockDep(switchNode);
            visitCaseBlock(ctx.caseBlock());
            popLoopBlockDep(switchNode);
            return null;
        }

        @Override
        public Void visitCaseBlock(JavaScriptParser.CaseBlockContext ctx) {
            //caseBlock
            //    : '{' caseClauses? (defaultClause caseClauses?)? '}'
            if (ctx.caseClauses() == null || ctx.caseClauses().size() == 0) {
                PDNode defaultNode = new PDNode();
                defaultNode.setLineOfCode(ctx.defaultClause().getStart().getLine());
                defaultNode.setCode(getOriginalCodeText(ctx.defaultClause()));
                addNodeEdge(defaultNode);
                if (ctx.defaultClause().statementList() != null) {
                    negDeps.push(defaultNode);
                    visit(ctx.defaultClause().statementList());
                    negDeps.pop();
                }
                return null;
            }
            PDNode lastCase = new PDNode();
            lastCase.setLineOfCode(ctx.caseClauses(0).caseClause(0).getStart().getLine());
            lastCase.setCode(getOriginalCodeText(ctx.caseClauses(0).caseClause(0)));
            addNodeEdge(lastCase);
            PDNode thenRegion = null;
            if (ctx.caseClauses(0).caseClause(0).statementList() != null) {
                thenRegion = new PDNode();
                thenRegion.setLineOfCode(0);
                thenRegion.setCode("THEN");
                cdg.addVertex(thenRegion);
                cdg.addEdge(new Edge<>(lastCase, new CDEdge(CDEdge.Type.TRUE), thenRegion));
                for (int i = 1; i < ctx.caseClauses(0).caseClause().size(); i++) {
                    PDNode nextCase = new PDNode();
                    nextCase.setLineOfCode(ctx.caseClauses(0).caseClause(i).getStart().getLine());
                    nextCase.setCode(getOriginalCodeText(ctx.caseClauses(0).caseClause(i)));
                    cdg.addVertex(nextCase);
                    cdg.addEdge(new Edge<>(lastCase, new CDEdge(CDEdge.Type.FALSE), nextCase));
                    cdg.addEdge(new Edge<>(nextCase, new CDEdge(CDEdge.Type.TRUE), thenRegion));
                }
                for (int i = 1; i < ctx.caseClauses().size(); i++) {
                    for (JavaScriptParser.CaseClauseContext caseClauseContext : ctx.caseClauses(i).caseClause()) {
                        PDNode nextCase = new PDNode();
                        nextCase.setLineOfCode(caseClauseContext.getStart().getLine());
                        nextCase.setCode(getOriginalCodeText(caseClauseContext));
                        cdg.addVertex(nextCase);
                        cdg.addEdge(new Edge<>(lastCase, new CDEdge(CDEdge.Type.FALSE), nextCase));
                        cdg.addEdge(new Edge<>(nextCase, new CDEdge(CDEdge.Type.TRUE), thenRegion));
                    }
                }
                if (ctx.defaultClause() != null) {
                    PDNode nextCase = new PDNode();
                    nextCase.setLineOfCode(ctx.defaultClause().getStart().getLine());
                    nextCase.setCode(getOriginalCodeText(ctx.defaultClause()));
                    cdg.addVertex(nextCase);
                    cdg.addEdge(new Edge<>(lastCase, new CDEdge(CDEdge.Type.FALSE), nextCase));
                    cdg.addEdge(new Edge<>(nextCase, new CDEdge(CDEdge.Type.TRUE), thenRegion));
                }
                PDNode elseRegion = new PDNode();
                elseRegion.setLineOfCode(0);
                elseRegion.setCode("ELSE");
                cdg.addVertex(elseRegion);
                pushCtrlDep(thenRegion);
                negDeps.push(elseRegion);
                for (int i = 0; i < ctx.caseClauses().size(); i++) {
                    for (int j = 0; j < ctx.caseClauses(i).caseClause().size(); j++) {
                        visit(ctx.caseClauses(i).caseClause(j).statementList());
                    }
                }
                if (ctx.defaultClause() != null) {
                    visit(ctx.defaultClause().statementList());
                }
                negDeps.pop();
                popCtrlDep(thenRegion);
                if (buildRegion) {
                    // there was a 'break', so we need to keep the ELSE region
                    cdg.addEdge(new Edge<>(lastCase, new CDEdge(CDEdge.Type.FALSE), elseRegion));
                } else if (cdg.getOutDegree(elseRegion) == 0) {
                    // the ELSE region is not needed, so we remove it
                    cdg.removeVertex(elseRegion);
                }
            }
            return null;
        }

        @Override
        public Void visitLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) {
            //    : identifier ':' statement
            PDNode labelRegion = new PDNode();
            labelRegion.setLineOfCode(ctx.getStart().getLine());
            labelRegion.setCode(ctx.identifier().Identifier() + ": ");
            addNodeEdge(labelRegion);
            pushCtrlDep(labelRegion);
            visit(ctx.statement());
            popCtrlDep(labelRegion);
            return null;
        }

        @Override
        public Void visitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) {
            //    : Throw {this.notLineTerminator()}? expressionSequence eos
            PDNode thr = new PDNode();
            thr.setLineOfCode(ctx.getStart().getLine());
            thr.setCode(getOriginalCodeText(ctx));
            addNodeEdge(thr);
            // NOTE: an important assumption here is that 'throw'
            //       is the last statement inside an if-else body,
            //       or it's the last statement of a try-catch block,
            //       or it's the last statement of the entire method
            if (!negDeps.isEmpty() && ctrlDeps.size() >= lastFollowDepth) {
                jumpDeps.push(negDeps.peek());
                jumpDeps.peek().setProperty("isExit", Boolean.TRUE);
                jumpDeps.peek().setProperty("isJump", Boolean.FALSE);
                lastFollowDepth = ctrlDeps.size();
                buildRegion = true;
            }
            return null;
        }

        @Override
        public Void visitTryStatement(JavaScriptParser.TryStatementContext ctx) {
            //    : Try block (catchProduction finallyProduction? | finallyProduction)
            PDNode tryRegion = new PDNode();
            tryRegion.setLineOfCode(ctx.getStart().getLine());
            tryRegion.setCode("try");
            tryRegion.setProperty("isTry", Boolean.TRUE);
            addNodeEdge(tryRegion);
            pushCtrlDep(tryRegion);
            negDeps.push(tryRegion);
            visit(ctx.block());
            // visit any available catch clauses
            if (ctx.catchProduction() != null) {
                // 'catch' '(' variableModifier* catchType Identifier ')' block
                PDNode catchNode = new PDNode();
                catchNode.setLineOfCode(ctx.catchProduction().getStart().getLine());
                catchNode.setCode("catch(" + getOriginalCodeText(ctx.catchProduction().assignable()) + ")");
                cdg.addVertex(catchNode);
                cdg.addEdge(new Edge<>(tryRegion, new CDEdge(CDEdge.Type.THROWS), catchNode));
                pushCtrlDep(catchNode);
                visit(ctx.catchProduction().block());
                popCtrlDep(catchNode);
            }
            negDeps.pop(); // pop try-region
            popCtrlDep(tryRegion); // pop try-region
            //
            // If there is a finally-block
            if (ctx.finallyProduction() != null) {
                // 'finally' block
                PDNode finallyRegion = new PDNode();
                finallyRegion.setLineOfCode(ctx.finallyProduction().getStart().getLine());
                finallyRegion.setCode("finally");
                addNodeEdge(finallyRegion);
                pushCtrlDep(finallyRegion);
                visit(ctx.finallyProduction().block());
                popCtrlDep(finallyRegion);
            }
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public Void visitDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public Void visitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
            //    : Async? Function_ '*'? identifier '(' formalParameterList? ')' functionBody
            init();
            //
            PDNode entry = new PDNode();
            entry.setLineOfCode(ctx.getStart().getLine());
            String args = getOriginalCodeText(ctx.formalParameterList());
            entry.setCode(ctx.identifier().Identifier() + args);
            cdg.addVertex(entry);
            //
            pushCtrlDep(entry);
            if (ctx.functionBody() != null)
                visit(ctx.functionBody());
            //
            PDNode exit = new PDNode();
            exit.setLineOfCode(0);
            exit.setCode("exit");
            cdg.addVertex(exit);
            cdg.addEdge(new Edge<>(entry, new CDEdge(CDEdge.Type.EPSILON), exit));
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public Void visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public Void visitClassTail(JavaScriptParser.ClassTailContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public Void visitClassElement(JavaScriptParser.ClassElementContext ctx) {
            // : (Static | {this.n("static")}? identifier | Async)* (methodDefinition | assignable '=' objectLiteral ';')
            //    | emptyStatement_
            //    | '#'? propertyName '=' singleExpression
            //    ;
            init();
            //
            PDNode block = new PDNode();
            boolean flag = false;
            if (ctx.getChildCount() == 2 && ctx.getChild(0).getText().equals("static")) {
                block.setLineOfCode(ctx.getStart().getLine());
                block.setCode("static");
            } else {
                block.setLineOfCode(0);
                block.setCode("block");
            }
            if (ctx.emptyStatement_() != null) {
                block.setLineOfCode(ctx.getStart().getLine());
                block.setCode(getOriginalCodeText(ctx.emptyStatement_()));
                return null;
            } else if (ctx.getChild(0).getText().equals("#")) {
                block.setLineOfCode(ctx.getStart().getLine());
                block.setCode("#");
            } else if (ctx.propertyName() != null) {
                block.setLineOfCode(ctx.getStart().getLine());
                block.setCode(getOriginalCodeText(ctx.propertyName()) + "=" + getOriginalCodeText(ctx.singleExpression()));
            } else {
                flag = true;
                block.setLineOfCode(ctx.getStart().getLine());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("static ");
                for (JavaScriptParser.IdentifierContext identifierContext : ctx.identifier()) {
                    stringBuffer.append(getOriginalCodeText(identifierContext) + " ");
                }
                if (ctx.methodDefinition() != null && !ctx.methodDefinition().isEmpty()) {
                    stringBuffer.append(getOriginalCodeText(ctx.methodDefinition()));
                } else {
                    stringBuffer.append(getOriginalCodeText(ctx.assignable()));
                }
                stringBuffer.append("=" + getOriginalCodeText(ctx.objectLiteral()) + ";");
                block.setCode(stringBuffer.toString());
            }
            cdg.addVertex(block);
            pushCtrlDep(block);
            //
            PDNode exit = new PDNode();
            exit.setLineOfCode(0);
            exit.setCode("exit");
            cdg.addVertex(exit);
            cdg.addEdge(new Edge<>(block, new CDEdge(CDEdge.Type.EPSILON), exit));
            return null;
        }

        @Override
        public Void visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {
            // : '*'? '#'? propertyName '(' formalParameterList? ')' functionBody
            //    | '*'? '#'? getter '(' ')' functionBody
            //    | '*'? '#'? setter '(' formalParameterList? ')' functionBody
            init();
            //
            PDNode entry = new PDNode();
            entry.setLineOfCode(ctx.getStart().getLine());
            String retType;
            if (ctx.propertyName() != null) {
                String args = getOriginalCodeText(ctx.formalParameterList());
                entry.setCode(ctx.propertyName() + "(" + args + ")");
            } else if (ctx.getter() != null) {
                entry.setCode(ctx.getter() + "()");
            } else {
                entry.setCode(ctx.setter() + "()");
            }
            cdg.addVertex(entry);
            //
            pushCtrlDep(entry);
            if (ctx.functionBody() != null)
                visit(ctx.functionBody());
            //
            PDNode exit = new PDNode();
            exit.setLineOfCode(0);
            exit.setCode("exit");
            cdg.addVertex(exit);
            cdg.addEdge(new Edge<>(entry, new CDEdge(CDEdge.Type.EPSILON), exit));
            return null;
        }


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
                for (PDNode dep : jumpDeps)
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