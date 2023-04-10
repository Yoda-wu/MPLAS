package com.scut.mplas.ruby;

import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
import com.scut.mplas.ruby.parser.RubyBaseVisitor;
import com.scut.mplas.ruby.parser.RubyLexer;

import com.scut.mplas.ruby.parser.RubyParser;
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
    public static  AbstractSyntaxTree build(String fileName, InputStream inputStream) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(inputStream);
        RubyLexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RubyParser parser = new RubyParser(tokens);
        ParseTree tree = parser.prog();
        return build(fileName, tree);
    }
    public static AbstractSyntaxTree build(String fileName, ParseTree tree){
        AbstractSyntaxVisitor visitor = new AbstractSyntaxVisitor(fileName);
        return visitor.build(tree);
    }

    private static class AbstractSyntaxVisitor extends RubyBaseVisitor<String> {
        private Deque<ASNode> parentStack;
        private final AbstractSyntaxTree AST;

        private Map<String, String> vars, methods;

        private int varsCounter, methodsCounter;

        public AbstractSyntaxVisitor(String fileName ){
            AST = new AbstractSyntaxTree(fileName);
            parentStack = new ArrayDeque<>();
            vars = new LinkedHashMap<>();
            methods = new LinkedHashMap<>();
            varsCounter = 0; methodsCounter = 0;
        }
        public AbstractSyntaxTree build(ParseTree tree){
            RubyParser.ProgContext rootCtx =  (RubyParser.ProgContext) tree;
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
            if(ctx.expression() != null ){
                ASNode expNode = new ASNode(ASNode.Type.RUBY_EXPRESSION);
                expNode.setLineOfCode(ctx.expression().getStart().getLine());
                AST.addVertex(expNode);
                AST.addEdge(parentStack.peek(),expNode);
                parentStack.push(expNode);
                visitChildren(ctx.expression());
                parentStack.pop();
            }else if(ctx.terminator() != null ){
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
            if(ctx.function_definition() != null ){
                ASNode funcDefNode = new ASNode(ASNode.Type.RUBY_FUNCTION_DEF);
                funcDefNode.setLineOfCode(ctx.function_definition().getStart().getLine());
                AST.addVertex(funcDefNode);
                AST.addEdge(parentStack.peek(), funcDefNode);
                parentStack.push(funcDefNode);
                visit(ctx.function_definition());
                parentStack.pop();
            }else if(ctx.function_inline_call() != null ){
                ASNode funcInlineCallNode = new ASNode(ASNode.Type.RUBY_FUNCTION_INLINE);
                funcInlineCallNode.setLineOfCode(ctx.function_definition().getStart().getLine());
                AST.addVertex(funcInlineCallNode);
                AST.addEdge(parentStack.peek(), funcInlineCallNode);
                parentStack.push(funcInlineCallNode);
                visit(ctx.function_inline_call());
                parentStack.pop();
            } else if (ctx.require_block() != null ){
                ASNode requireBlockNode = new ASNode(ASNode.Type.RUBY_REQUIRE_BLOCK);
                requireBlockNode.setLineOfCode(ctx.require_block().getStart().getLine());
                AST.addVertex(requireBlockNode);
                AST.addEdge(parentStack.peek(), requireBlockNode);
                parentStack.push(requireBlockNode);
                visit(ctx.function_inline_call());
                parentStack.pop();
            } else if (ctx.pir_inline() != null ){
                ASNode pirNode = new ASNode(ASNode.Type.RUBY_PIR_INLINE);
                pirNode.setLineOfCode(ctx.pir_inline().getStart().getLine());
                pirNode.setCode(ctx.pir_inline().PIR().getText() + ctx.pir_inline().crlf().getText());
                AST.addVertex(pirNode);
                AST.addEdge(parentStack.peek(), pirNode);
                parentStack.push(pirNode);
                visit(ctx.pir_inline().pir_expression_list());
                parentStack.pop();
            }else {
                // 直接visit statement部分
                visitChildren(ctx);
            }
            return "";
        }

        @Override
        public String visitGlobal_get(RubyParser.Global_getContext ctx) {

            return super.visitGlobal_get(ctx);
        }

        @Override
        public String visitGlobal_set(RubyParser.Global_setContext ctx) {
            return super.visitGlobal_set(ctx);
        }

        @Override
        public String visitGlobal_result(RubyParser.Global_resultContext ctx) {
            return super.visitGlobal_result(ctx);
        }

        @Override
        public String visitFunction_inline_call(RubyParser.Function_inline_callContext ctx) {
            return super.visitFunction_inline_call(ctx);
        }

        @Override
        public String visitRequire_block(RubyParser.Require_blockContext ctx) {
            return super.visitRequire_block(ctx);
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
            return super.visitFunction_definition(ctx);
        }

        @Override
        public String visitFunction_definition_body(RubyParser.Function_definition_bodyContext ctx) {
            return super.visitFunction_definition_body(ctx);
        }

        @Override
        public String visitFunction_definition_header(RubyParser.Function_definition_headerContext ctx) {
            return super.visitFunction_definition_header(ctx);
        }

        @Override
        public String visitFunction_name(RubyParser.Function_nameContext ctx) {
            return super.visitFunction_name(ctx);
        }

        @Override
        public String visitFunction_definition_params(RubyParser.Function_definition_paramsContext ctx) {
            return super.visitFunction_definition_params(ctx);
        }

        @Override
        public String visitFunction_definition_params_list(RubyParser.Function_definition_params_listContext ctx) {
            return super.visitFunction_definition_params_list(ctx);
        }

        @Override
        public String visitFunction_definition_param_id(RubyParser.Function_definition_param_idContext ctx) {
            return super.visitFunction_definition_param_id(ctx);
        }

        @Override
        public String visitReturn_statement(RubyParser.Return_statementContext ctx) {
            return super.visitReturn_statement(ctx);
        }

        @Override
        public String visitFunction_call(RubyParser.Function_callContext ctx) {
            return super.visitFunction_call(ctx);
        }

        @Override
        public String visitFunction_call_param_list(RubyParser.Function_call_param_listContext ctx) {
            return super.visitFunction_call_param_list(ctx);
        }

        @Override
        public String visitFunction_call_params(RubyParser.Function_call_paramsContext ctx) {
            return super.visitFunction_call_params(ctx);
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
            return super.visitAll_result(ctx);
        }

        @Override
        public String visitElsif_statement(RubyParser.Elsif_statementContext ctx) {
            return super.visitElsif_statement(ctx);
        }

        @Override
        public String visitIf_elsif_statement(RubyParser.If_elsif_statementContext ctx) {
            return super.visitIf_elsif_statement(ctx);
        }

        @Override
        public String visitIf_statement(RubyParser.If_statementContext ctx) {
            return super.visitIf_statement(ctx);
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
            return super.visitStatement_body(ctx);
        }

        @Override
        public String visitStatement_expression_list(RubyParser.Statement_expression_listContext ctx) {
            return super.visitStatement_expression_list(ctx);
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
            return super.visitDynamic_result(ctx);
        }

        @Override
        public String visitDynamic_(RubyParser.Dynamic_Context ctx) {
            return super.visitDynamic_(ctx);
        }

        @Override
        public String visitInt_result(RubyParser.Int_resultContext ctx) {
            return super.visitInt_result(ctx);
        }

        @Override
        public String visitFloat_result(RubyParser.Float_resultContext ctx) {
            return super.visitFloat_result(ctx);
        }

        @Override
        public String visitString_result(RubyParser.String_resultContext ctx) {
            return super.visitString_result(ctx);
        }

        @Override
        public String visitComparison_list(RubyParser.Comparison_listContext ctx) {
            return super.visitComparison_list(ctx);
        }

        @Override
        public String visitComparison(RubyParser.ComparisonContext ctx) {
            return super.visitComparison(ctx);
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
            return super.visitId_global(ctx);
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
