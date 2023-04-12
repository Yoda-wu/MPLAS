package com.scut.mplas.ruby;

import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
import com.scut.mplas.ruby.parser.RubyBaseVisitor;
import com.scut.mplas.ruby.parser.RubyLexer;

import com.scut.mplas.ruby.parser.RubyParser;
import ghaffarian.nanologger.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

public class RubyASTBuilder {
    public static AbstractSyntaxTree build(String fileName, InputStream inputStream) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(inputStream);
        RubyLexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RubyParser parser = new RubyParser(tokens);
        ParseTree tree = parser.prog();
        return build(fileName, tree);
    }

    public static AbstractSyntaxTree build(String fileName, ParseTree tree) {
        AbstractSyntaxVisitor visitor = new AbstractSyntaxVisitor(fileName);
        return visitor.build(tree);
    }

    private static class AbstractSyntaxVisitor extends RubyBaseVisitor<String> {
        private Deque<ASNode> parentStack;
        private final AbstractSyntaxTree AST;

        private Map<String, String> vars, methods;

        private int varsCounter, methodsCounter;

        public AbstractSyntaxVisitor(String fileName) {
            AST = new AbstractSyntaxTree(fileName);
            parentStack = new ArrayDeque<>();
            vars = new LinkedHashMap<>();
            methods = new LinkedHashMap<>();
            varsCounter = 0;
            methodsCounter = 0;
        }

        public AbstractSyntaxTree build(ParseTree tree) {
            RubyParser.ProgContext rootCtx = (RubyParser.ProgContext) tree;
            AST.root.setCode(AST.fileName);
            parentStack.push(AST.root);
            // prog : expression_list EOF;
            visit(rootCtx.expression_list());

            parentStack.pop();
            vars.clear();
            methods.clear();
            return AST;
        }

        @Override
        public String visitExpression_list(RubyParser.Expression_listContext ctx) {
            // expression_list : expression terminator
            //                | expression_list expression terminator
            //                | terminator
            //                ;
            if (ctx.expression() != null) {
                ASNode expNode = new ASNode(ASNode.Type.RUBY_EXPRESSION);
                expNode.setLineOfCode(ctx.expression().getStart().getLine());
                AST.addVertex(expNode);
                AST.addEdge(parentStack.peek(), expNode);
                parentStack.push(expNode);
                visitChildren(ctx.expression());
                parentStack.pop();
            } else if (ctx.terminator() != null) {
                ASNode terminatorNode = new ASNode(ASNode.Type.RUBY_TERMINATOR);
                terminatorNode.setLineOfCode(ctx.terminator().getStart().getLine());
                terminatorNode.setCode(ctx.terminator().getText());
                AST.addVertex(terminatorNode);
                AST.addEdge(parentStack.peek(), terminatorNode);
                parentStack.push(terminatorNode);
                visitChildren(ctx.terminator());
                parentStack.pop();
            }
            return "";
        }

        @Override
        public String visitExpression(RubyParser.ExpressionContext ctx) {
            // expression : function_definition
            //           | function_inline_call
            //           | require_block
            //           | rvalue
            //           | unless_statement
            //           | if_statement
            //           | return_statement
            //           | while_statement
            //           | for_statement
            //           | pir_inline
            //           ;
            if (ctx.function_definition() != null) {
                ASNode funcDefNode = new ASNode(ASNode.Type.RUBY_FUNCTION);
                funcDefNode.setLineOfCode(ctx.function_definition().getStart().getLine());
                AST.addVertex(funcDefNode);
                AST.addEdge(parentStack.peek(), funcDefNode);
                parentStack.push(funcDefNode);
                visit(ctx.function_definition());
                parentStack.pop();
            } else if (ctx.function_inline_call() != null) {
                ASNode funcInlineCallNode = new ASNode(ASNode.Type.RUBY_FUNCTION_CALL);
                funcInlineCallNode.setLineOfCode(ctx.function_definition().getStart().getLine());
                funcInlineCallNode.setCode(visit(ctx.function_inline_call()));
                AST.addVertex(funcInlineCallNode);
                AST.addEdge(parentStack.peek(), funcInlineCallNode);
//                parentStack.push(funcInlineCallNode);
//                visit(ctx.function_inline_call());
//                parentStack.pop();
            } else if (ctx.require_block() != null) {
                ASNode requireBlockNode = new ASNode(ASNode.Type.RUBY_REQUIRE_BLOCK);
                requireBlockNode.setLineOfCode(ctx.require_block().getStart().getLine());
                requireBlockNode.setCode(visitRequire_block(ctx.require_block()));
                AST.addVertex(requireBlockNode);
                AST.addEdge(parentStack.peek(), requireBlockNode);
//                parentStack.push(requireBlockNode);
//                visit(ctx.function_inline_call());
//                parentStack.pop();
            } else if (ctx.pir_inline() != null) {
                ASNode pirNode = new ASNode(ASNode.Type.RUBY_PIR_INLINE);
                pirNode.setLineOfCode(ctx.pir_inline().getStart().getLine());
                pirNode.setCode(ctx.pir_inline().PIR().getText() + ctx.pir_inline().crlf().getText());
                AST.addVertex(pirNode);
                AST.addEdge(parentStack.peek(), pirNode);
                parentStack.push(pirNode);
                visit(ctx.pir_inline().pir_expression_list());
                parentStack.pop();
            } else if (ctx.rvalue() != null) {
                ASNode rvalueNode = new ASNode(ASNode.Type.RUBY_RVALUE);
                rvalueNode.setLineOfCode(ctx.rvalue().getStart().getLine());
                AST.addVertex(rvalueNode);
                AST.addEdge(parentStack.peek(), rvalueNode);
                parentStack.push(rvalueNode);
                visitChildren(ctx.rvalue());
                parentStack.pop();
            } else {
                // 直接visit statement部分
                visitChildren(ctx);
            }
            return "";
        }

        @Override
        public String visitGlobal_get(RubyParser.Global_getContext ctx) {
            ASNode globalGet = new ASNode(ASNode.Type.RUBY_GLOBAL_GET);
            globalGet.setLineOfCode(ctx.getStart().getLine());
            globalGet.setCode(visitLvalue(ctx.var_name) + ctx.op.getText() + ctx.global_name.getText());
            AST.addVertex(globalGet);
            AST.addEdge(parentStack.peek(), globalGet);
            return globalGet.getCode();
        }

        @Override
        public String visitGlobal_set(RubyParser.Global_setContext ctx) {
            ASNode globalSet = new ASNode(ASNode.Type.RUBY_GLOBAL_GET);
            globalSet.setLineOfCode(ctx.getStart().getLine());
            globalSet.setCode(ctx.global_name.getText() + ctx.op.getInputStream().toString() + visitAll_result(ctx.all_result()));
            AST.addVertex(globalSet);
            AST.addEdge(parentStack.peek(), globalSet);
            return globalSet.getCode();
        }

        @Override
        public String visitGlobal_result(RubyParser.Global_resultContext ctx) {
            ASNode globalResult = new ASNode(ASNode.Type.RUBY_GLOBAL_RESULT);
            globalResult.setLineOfCode(ctx.getStart().getLine());
            globalResult.setCode(visitId_global(ctx.id_global()));
            AST.addVertex(globalResult);
            AST.addEdge(parentStack.peek(), globalResult);
            return globalResult.getCode();
        }

        @Override
        public String visitFunction_inline_call(RubyParser.Function_inline_callContext ctx) {
            return visitFunction_call(ctx.function_call());
        }

        @Override
        public String visitRequire_block(RubyParser.Require_blockContext ctx) {

            return ctx.REQUIRE().getText() + ctx.literal_t().LITERAL().getText();
        }

        @Override
        public String visitPir_inline(RubyParser.Pir_inlineContext ctx) {
            return super.visitPir_inline(ctx);
        }

        @Override
        public String visitPir_expression_list(RubyParser.Pir_expression_listContext ctx) {
            return super.visitPir_expression_list(ctx);
        }

        @Override
        public String visitFunction_definition(RubyParser.Function_definitionContext ctx) {
            // function_definition : function_definition_header function_definition_body END;
            visit(ctx.function_definition_header());
            visit(ctx.function_definition_body());
            ASNode funcEND = new ASNode(ASNode.Type.RUBY_END);
            funcEND.setLineOfCode(ctx.getStart().getLine());
            funcEND.setCode("END");
            AST.addVertex(funcEND);
            AST.addEdge(parentStack.peek(), funcEND);
            return "";
        }

        @Override
        public String visitFunction_definition_body(RubyParser.Function_definition_bodyContext ctx) {
            // function_definition_body : expression_list;
            ASNode funcBody = new ASNode(ASNode.Type.BLOCK);
            funcBody.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(funcBody);
            AST.addEdge(parentStack.peek(), funcBody);
            parentStack.push(funcBody);
            visit(ctx.expression_list());
            parentStack.pop();
            resetLocalVars();
            return "";
        }

        @Override
        public String visitFunction_definition_header(RubyParser.Function_definition_headerContext ctx) {
            // function_definition_header : DEF function_name crlf
            //                           | DEF function_name function_definition_params crlf
            //                           ;
            ++methodsCounter;
            ASNode funcDEF = new ASNode(ASNode.Type.RUBY_FUNCTION_DEF);
            funcDEF.setLineOfCode(ctx.getStart().getLine());
            funcDEF.setCode("DEF");
            AST.addVertex(funcDEF);
            AST.addEdge(parentStack.peek(), funcDEF);

            ASNode funcName = new ASNode(ASNode.Type.NAME);
            funcName.setCode(visitFunction_name(ctx.function_name()));
            funcName.setLineOfCode(ctx.function_name().getStart().getLine());
            String normalized = "$FUNC_" + methodsCounter;
            funcName.setNormalizedCode(normalized);
            methods.put(funcName.getCode(), normalized);
            AST.addVertex(funcName);
            AST.addEdge(parentStack.peek(), funcName);

            if (ctx.function_definition_params() != null) {
                visit(ctx.function_definition_params());
            }
            return "";
        }

        @Override
        public String visitFunction_name(RubyParser.Function_nameContext ctx) {
            // function_name : id_function
            //              | id_
            //              ;
            if (ctx.id_function() != null) {
                return ctx.id_function().getText();
            }
            return ctx.id_().getText();
        }

        @Override
        public String visitFunction_definition_params(RubyParser.Function_definition_paramsContext ctx) {
            if (ctx.function_definition_params_list() != null) {
                visit(ctx.function_definition_params_list());
            }
            return "";
        }

        @Override
        public String visitFunction_definition_params_list(RubyParser.Function_definition_params_listContext ctx) {
            // function_definition_params_list : function_definition_param_id
            //                                | function_definition_params_list COMMA function_definition_param_id
            //                                ;
            // function_definition_param_id : id_;
            ++varsCounter;
            String id = visitFunction_definition_param_id(ctx.function_definition_param_id());
            String normalized = "$VARL_" + varsCounter;
            vars.put(id, normalized);
            ASNode param = new ASNode(ASNode.Type.RUBY_VAR);
            param.setLineOfCode(ctx.function_definition_param_id().getStart().getLine());
            param.setCode(id);
            param.setNormalizedCode(normalized);
            AST.addVertex(param);
            AST.addEdge(parentStack.peek(), param);
            return "";
        }

        @Override
        public String visitFunction_definition_param_id(RubyParser.Function_definition_param_idContext ctx) {
            return ctx.id_().getText();
        }

        @Override
        public String visitReturn_statement(RubyParser.Return_statementContext ctx) {
            ASNode returnNode = new ASNode(ASNode.Type.RETURN);
            returnNode.setLineOfCode(ctx.start.getLine());
            AST.addVertex(returnNode);
            AST.addEdge(parentStack.peek(), returnNode);

            ASNode result = new ASNode(ASNode.Type.RUBY_RESULT);
            result.setLineOfCode(ctx.all_result().start.getLine());
            String normalize = visitAll_result(ctx.all_result());
            result.setCode(normalize);
            result.setNormalizedCode(normalize);
            AST.addVertex(result);
            AST.addEdge(parentStack.peek(), result);
            return "";
        }

        @Override
        public String visitFunction_call(RubyParser.Function_callContext ctx) {
            return visitFunction_name(ctx.function_name()) + '(' + visitFunction_call_param_list(ctx.function_call_param_list()) + ')';
        }

        @Override
        public String visitFunction_call_param_list(RubyParser.Function_call_param_listContext ctx) {
            return visit(ctx.function_call_params());
        }

        @Override
        public String visitFunction_call_params(RubyParser.Function_call_paramsContext ctx) {
            if (ctx.function_param() != null) {
                return visit(ctx.function_param());
            }

            return visit(ctx.function_call_params()) + ',' + visit(ctx.function_param());
        }

        @Override
        public String visitFunction_param(RubyParser.Function_paramContext ctx) {
            return super.visitFunction_param(ctx);
        }

        @Override
        public String visitFunction_unnamed_param(RubyParser.Function_unnamed_paramContext ctx) {
            return super.visitFunction_unnamed_param(ctx);
        }

        @Override
        public String visitFunction_named_param(RubyParser.Function_named_paramContext ctx) {
            return super.visitFunction_named_param(ctx);
        }

        @Override
        public String visitFunction_call_assignment(RubyParser.Function_call_assignmentContext ctx) {
            return super.visitFunction_call_assignment(ctx);
        }

        @Override
        public String visitAll_result(RubyParser.All_resultContext ctx) {
            if(ctx.int_result() != null ){
                return visit(ctx.int_result());
            }else if (ctx.float_result() != null ){
                return  visit(ctx.float_result());
            }else if(ctx.string_result() != null ){
                return visit(ctx.string_result());
            }else if(ctx.dynamic_result() != null ){
                return visit(ctx.dynamic_result());
            }else {
                return visit(ctx.global_result());
            }
        }


        private void visitStatement(ParserRuleContext ctx, String normalized) {
            Logger.printf(Logger.Level.DEBUG, "Visiting: (%d)  %s", ctx.getStart().getLine(), getOriginalCodeText(ctx));
            ASNode statementNode = new ASNode(ASNode.Type.STATEMENT);
            statementNode.setCode(getOriginalCodeText(ctx));
            statementNode.setNormalizedCode(normalized);
            statementNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding statement " + ctx.getStart().getLine());
            AST.addVertex(statementNode);
            AST.addEdge(parentStack.peek(), statementNode);
        }

        @Override
        public String visitElsif_statement(RubyParser.Elsif_statementContext ctx) {
            return visit(ctx.if_elsif_statement());
        }

        @Override
        public String visitIf_elsif_statement(RubyParser.If_elsif_statementContext ctx) {
            ASNode elsIFNode = new ASNode(ASNode.Type.CONDITION);
            ASNode condNode = new ASNode(ASNode.Type.CONDITION);
            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
            condNode.setCode(getOriginalCodeText(ctx.cond_expression().comparison_list()));
            condNode.setNormalizedCode(visit(ctx.cond_expression().comparison_list()));
            AST.addVertex(condNode);
            AST.addEdge(parentStack.peek(), condNode);

            ASNode thenNode = new ASNode(ASNode.Type.THEN);
            thenNode.setLineOfCode(ctx.statement_body(0).getStart().getLine());
            AST.addVertex(thenNode);
            AST.addEdge(parentStack.peek(), thenNode);
            parentStack.push(thenNode);
            visit(ctx.statement_body(0));
            parentStack.pop();

            if(ctx.else_token() != null ){
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.statement_body(1).getStart().getLine());
                AST.addVertex(elseNode);
                AST.addEdge(parentStack.peek(), elseNode);
                parentStack.push(elseNode);
                visit(ctx.statement_body(1));
                parentStack.pop();
            }
            if(ctx.if_elsif_statement() != null ){
                ASNode elsifNode = new ASNode(ASNode.Type.RUBY_ELSEIF);
                elsifNode.setLineOfCode( ctx.if_elsif_statement().getStart().getLine());
                AST.addVertex(elsifNode);
                AST.addEdge(parentStack.peek(), elsifNode);
                visit(ctx.if_elsif_statement());
            }

            return "";
        }

        @Override
        public String visitIf_statement(RubyParser.If_statementContext ctx) {
            ASNode ifNode = new ASNode(ASNode.Type.IF);
            ifNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(ifNode);
            AST.addEdge(parentStack.peek(), ifNode);

            ASNode condNode = new ASNode(ASNode.Type.CONDITION);
            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
            condNode.setCode(getOriginalCodeText(ctx.cond_expression().comparison_list()));
            condNode.setNormalizedCode(visit(ctx.cond_expression().comparison_list()));
            AST.addVertex(condNode);
            AST.addEdge(parentStack.peek(), condNode);


            ASNode thenNode = new ASNode(ASNode.Type.THEN);
            thenNode.setLineOfCode(ctx.statement_body(0).getStart().getLine());
            AST.addVertex(thenNode);
            AST.addEdge(parentStack.peek(), thenNode);
            parentStack.push(thenNode);
            visit(ctx.statement_body(0));
            parentStack.pop();

            if(ctx.else_token() != null ){
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.statement_body(1).getStart().getLine());
                AST.addVertex(elseNode);
                AST.addEdge(parentStack.peek(), elseNode);
                parentStack.push(elseNode);
                visit(ctx.statement_body(1));
                parentStack.pop();
            }
            if(ctx.elsif_statement() != null ){
                ASNode elsifNode = new ASNode(ASNode.Type.RUBY_ELSEIF);
                elsifNode.setLineOfCode( ctx.elsif_statement().getStart().getLine());
                AST.addVertex(elsifNode);
                AST.addEdge(parentStack.peek(), elsifNode);
                visit(ctx.elsif_statement());
            }

            ASNode endNode = new ASNode(ASNode.Type.RUBY_END);
            endNode.setLineOfCode(ctx.getStop().getLine());
            endNode.setCode(endNode.getType().labels);
            AST.addVertex(endNode);
            AST.addEdge(parentStack.peek(), endNode);

            return "";
        }

        @Override
        public String visitUnless_statement(RubyParser.Unless_statementContext ctx) {
            return super.visitUnless_statement(ctx);
        }

        @Override
        public String visitWhile_statement(RubyParser.While_statementContext ctx) {
            return super.visitWhile_statement(ctx);
        }

        @Override
        public String visitFor_statement(RubyParser.For_statementContext ctx) {
            return super.visitFor_statement(ctx);
        }

        @Override
        public String visitInit_expression(RubyParser.Init_expressionContext ctx) {
            return super.visitInit_expression(ctx);
        }

        @Override
        public String visitAll_assignment(RubyParser.All_assignmentContext ctx) {
            return super.visitAll_assignment(ctx);
        }

        @Override
        public String visitFor_init_list(RubyParser.For_init_listContext ctx) {
            return super.visitFor_init_list(ctx);
        }

        @Override
        public String visitCond_expression(RubyParser.Cond_expressionContext ctx) {
            return super.visitCond_expression(ctx);
        }

        @Override
        public String visitLoop_expression(RubyParser.Loop_expressionContext ctx) {
            return super.visitLoop_expression(ctx);
        }

        @Override
        public String visitFor_loop_list(RubyParser.For_loop_listContext ctx) {
            return super.visitFor_loop_list(ctx);
        }

        @Override
        public String visitStatement_body(RubyParser.Statement_bodyContext ctx) {
            return visit(ctx.statement_expression_list());
        }

        @Override
        public String visitStatement_expression_list(RubyParser.Statement_expression_listContext ctx) {
            if(ctx.RETRY() != null ){
                ASNode retryNode = new ASNode(ASNode.Type.RUBY_RETRY);
                retryNode.setLineOfCode(ctx.getStart().getLine());
                retryNode.setCode(retryNode.getType().label);
                AST.addVertex(retryNode);
                AST.addEdge(parentStack.peek(), retryNode);
            }else if (ctx.expression() != null ){
                visit(ctx.expression());
            }
            return visit(ctx.statement_expression_list());
        }

        @Override
        public String visitAssignment(RubyParser.AssignmentContext ctx) {
            return super.visitAssignment(ctx);
        }

        @Override
        public String visitDynamic_assignment(RubyParser.Dynamic_assignmentContext ctx) {
            return super.visitDynamic_assignment(ctx);
        }

        @Override
        public String visitInt_assignment(RubyParser.Int_assignmentContext ctx) {
            return super.visitInt_assignment(ctx);
        }

        @Override
        public String visitFloat_assignment(RubyParser.Float_assignmentContext ctx) {
            return super.visitFloat_assignment(ctx);
        }

        @Override
        public String visitString_assignment(RubyParser.String_assignmentContext ctx) {
            return super.visitString_assignment(ctx);
        }

        @Override
        public String visitInitial_array_assignment(RubyParser.Initial_array_assignmentContext ctx) {
            return super.visitInitial_array_assignment(ctx);
        }

        @Override
        public String visitArray_assignment(RubyParser.Array_assignmentContext ctx) {
            return super.visitArray_assignment(ctx);
        }

        @Override
        public String visitArray_definition(RubyParser.Array_definitionContext ctx) {
            return super.visitArray_definition(ctx);
        }

        @Override
        public String visitArray_definition_elements(RubyParser.Array_definition_elementsContext ctx) {
            return super.visitArray_definition_elements(ctx);
        }

        @Override
        public String visitArray_selector(RubyParser.Array_selectorContext ctx) {
            return super.visitArray_selector(ctx);
        }

        @Override
        public String visitDynamic_result(RubyParser.Dynamic_resultContext ctx) {
            return getOriginalCodeText(ctx);
        }

        @Override
        public String visitDynamic_(RubyParser.Dynamic_Context ctx) {
            return super.visitDynamic_(ctx);
        }

        @Override
        public String visitInt_result(RubyParser.Int_resultContext ctx) {

            return getOriginalCodeText(ctx);
        }

        @Override
        public String visitFloat_result(RubyParser.Float_resultContext ctx) {
            return getOriginalCodeText(ctx);
        }

        @Override
        public String visitString_result(RubyParser.String_resultContext ctx) {
            return getOriginalCodeText(ctx);
        }

        @Override
        public String visitComparison_list(RubyParser.Comparison_listContext ctx) {

            if(ctx.LEFT_RBRACKET() != null && ctx.RIGHT_RBRACKET() != null ) {
                return '(' + visit(ctx.comparison_list()) + ')';
            }
            return visit(ctx.comparison()) + ctx.op.getInputStream().toString() + visit(ctx.comparison_list());
        }

        @Override
        public String visitComparison(RubyParser.ComparisonContext ctx) {
            return ctx.left.getText() + ctx.op.getInputStream().toString() + ctx.right.getText();
        }

        @Override
        public String visitComp_var(RubyParser.Comp_varContext ctx) {
            return super.visitComp_var(ctx);
        }

        @Override
        public String visitLvalue(RubyParser.LvalueContext ctx) {
            return super.visitLvalue(ctx);
        }

        @Override
        public String visitRvalue(RubyParser.RvalueContext ctx) {
            return super.visitRvalue(ctx);
        }

        @Override
        public String visitBreak_expression(RubyParser.Break_expressionContext ctx) {
            return super.visitBreak_expression(ctx);
        }

        @Override
        public String visitLiteral_t(RubyParser.Literal_tContext ctx) {
            return super.visitLiteral_t(ctx);
        }

        @Override
        public String visitFloat_t(RubyParser.Float_tContext ctx) {
            return super.visitFloat_t(ctx);
        }

        @Override
        public String visitInt_t(RubyParser.Int_tContext ctx) {
            return super.visitInt_t(ctx);
        }

        @Override
        public String visitBool_t(RubyParser.Bool_tContext ctx) {
            return super.visitBool_t(ctx);
        }

        @Override
        public String visitNil_t(RubyParser.Nil_tContext ctx) {
            return super.visitNil_t(ctx);
        }

        @Override
        public String visitId_(RubyParser.Id_Context ctx) {
            return super.visitId_(ctx);
        }

        @Override
        public String visitId_global(RubyParser.Id_globalContext ctx) {
            ASNode globalID = new ASNode(ASNode.Type.RUBY_GLOBAL_ID);
            globalID.setLineOfCode(ctx.getStart().getLine());
            globalID.setCode(ctx.ID_GLOBAL().getSymbol().getInputStream().toString());
            return globalID.getCode();
        }

        @Override
        public String visitId_function(RubyParser.Id_functionContext ctx) {
            return super.visitId_function(ctx);
        }

        @Override
        public String visitTerminator(RubyParser.TerminatorContext ctx) {
            return super.visitTerminator(ctx);
        }

        @Override
        public String visitElse_token(RubyParser.Else_tokenContext ctx) {
            return super.visitElse_token(ctx);
        }

        @Override
        public String visitCrlf(RubyParser.CrlfContext ctx) {
            return super.visitCrlf(ctx);
        }

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

        private void resetLocalVars() {
            vars.clear();
            varsCounter = 0;
        }

        private String normalizedIdentifier(TerminalNode id) {
            String normalized = vars.get(id.getText());
//            if (normalized == null || normalized.isEmpty())
//                normalized = fields.get(id.getText());
            if (normalized == null || normalized.isEmpty())
                normalized = methods.get(id.getText());
            if (normalized == null || normalized.isEmpty())
                normalized = id.getText();
            return normalized;
        }

    }


}
