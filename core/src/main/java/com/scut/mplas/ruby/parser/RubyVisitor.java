// Generated from D:/school/github/mplas/core/src/main/java/com/scut/mplas/ruby/parser\Ruby.g4 by ANTLR 4.12.0
package com.scut.mplas.ruby.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RubyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface RubyVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link RubyParser#prog}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProg(RubyParser.ProgContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#expression_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExpression_list(RubyParser.Expression_listContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExpression(RubyParser.ExpressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#case_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCase_expression(RubyParser.Case_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#case_exp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCase_exp(RubyParser.Case_expContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#when_cond}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhen_cond(RubyParser.When_condContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#begin_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBegin_expression(RubyParser.Begin_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#end_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEnd_expression(RubyParser.End_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#begin_rescue_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBegin_rescue_expression(RubyParser.Begin_rescue_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#error_type}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitError_type(RubyParser.Error_typeContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#rescue_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRescue_expression(RubyParser.Rescue_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#ensure_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEnsure_expression(RubyParser.Ensure_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#global_get}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitGlobal_get(RubyParser.Global_getContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#global_set}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitGlobal_set(RubyParser.Global_setContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#global_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitGlobal_result(RubyParser.Global_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#instance_get}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstance_get(RubyParser.Instance_getContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#instance_set}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstance_set(RubyParser.Instance_setContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#instance_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstance_result(RubyParser.Instance_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#const_set}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConst_set(RubyParser.Const_setContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_inline_call}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_inline_call(RubyParser.Function_inline_callContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#require_block}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRequire_block(RubyParser.Require_blockContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#pir_inline}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPir_inline(RubyParser.Pir_inlineContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#pir_expression_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPir_expression_list(RubyParser.Pir_expression_listContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#class_definition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitClass_definition(RubyParser.Class_definitionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#module_definition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitModule_definition(RubyParser.Module_definitionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_definition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_definition(RubyParser.Function_definitionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_definition_body}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_definition_body(RubyParser.Function_definition_bodyContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_definition_header}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_definition_header(RubyParser.Function_definition_headerContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_name(RubyParser.Function_nameContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_definition_params}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_definition_params(RubyParser.Function_definition_paramsContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_definition_params_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_definition_params_list(RubyParser.Function_definition_params_listContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_definition_param_id}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_definition_param_id(RubyParser.Function_definition_param_idContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#return_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReturn_statement(RubyParser.Return_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_call}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_call(RubyParser.Function_callContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_call_param_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_call_param_list(RubyParser.Function_call_param_listContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_call_params}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_call_params(RubyParser.Function_call_paramsContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_param}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_param(RubyParser.Function_paramContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_unnamed_param}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_unnamed_param(RubyParser.Function_unnamed_paramContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_named_param}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_named_param(RubyParser.Function_named_paramContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#function_call_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFunction_call_assignment(RubyParser.Function_call_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#all_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAll_result(RubyParser.All_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#elsif_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElsif_statement(RubyParser.Elsif_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#if_elsif_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIf_elsif_statement(RubyParser.If_elsif_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#if_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIf_statement(RubyParser.If_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#unless_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUnless_statement(RubyParser.Unless_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#while_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhile_statement(RubyParser.While_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#all_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAll_assignment(RubyParser.All_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#for_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFor_statement(RubyParser.For_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#for_each_statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFor_each_statement(RubyParser.For_each_statementContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#cond_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCond_expression(RubyParser.Cond_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#loop_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLoop_expression(RubyParser.Loop_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#hash_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHash_expression(RubyParser.Hash_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#statement_body}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement_body(RubyParser.Statement_bodyContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#statement_expression_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement_expression_list(RubyParser.Statement_expression_listContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignment(RubyParser.AssignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#dynamic_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDynamic_assignment(RubyParser.Dynamic_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#int_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInt_assignment(RubyParser.Int_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#float_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFloat_assignment(RubyParser.Float_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#string_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitString_assignment(RubyParser.String_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#initial_array_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitial_array_assignment(RubyParser.Initial_array_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#array_assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArray_assignment(RubyParser.Array_assignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#array_definition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArray_definition(RubyParser.Array_definitionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#array_definition_elements}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArray_definition_elements(RubyParser.Array_definition_elementsContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#array_selector}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArray_selector(RubyParser.Array_selectorContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#dynamic_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDynamic_result(RubyParser.Dynamic_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#map_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMap_result(RubyParser.Map_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#dynamic_}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDynamic_(RubyParser.Dynamic_Context ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#int_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInt_result(RubyParser.Int_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#float_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFloat_result(RubyParser.Float_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#string_result}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitString_result(RubyParser.String_resultContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#comparison_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComparison_list(RubyParser.Comparison_listContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#comparison}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComparison(RubyParser.ComparisonContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#comp_var}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComp_var(RubyParser.Comp_varContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#lvalue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLvalue(RubyParser.LvalueContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#rvalue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRvalue(RubyParser.RvalueContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#break_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBreak_expression(RubyParser.Break_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#raise_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRaise_expression(RubyParser.Raise_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#yield_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitYield_expression(RubyParser.Yield_expressionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#literal_t}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLiteral_t(RubyParser.Literal_tContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#float_t}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFloat_t(RubyParser.Float_tContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#int_t}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInt_t(RubyParser.Int_tContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#bool_t}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBool_t(RubyParser.Bool_tContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#nil_t}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNil_t(RubyParser.Nil_tContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#id_}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitId_(RubyParser.Id_Context ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#id_global}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitId_global(RubyParser.Id_globalContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#id_instance}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitId_instance(RubyParser.Id_instanceContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#id_constence}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitId_constence(RubyParser.Id_constenceContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#id_function}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitId_function(RubyParser.Id_functionContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#terminator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTerminator(RubyParser.TerminatorContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#else_token}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElse_token(RubyParser.Else_tokenContext ctx);

    /**
     * Visit a parse tree produced by {@link RubyParser#crlf}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCrlf(RubyParser.CrlfContext ctx);
}