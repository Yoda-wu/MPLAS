// Generated from D:/school/github/mplas/core/src/main/java/com/scut/mplas/ruby/parser\Ruby.g4 by ANTLR 4.12.0
package com.scut.mplas.ruby.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RubyParser}.
 */
public interface RubyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RubyParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(RubyParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(RubyParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(RubyParser.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(RubyParser.Expression_listContext ctx);
	/**
     * Enter a parse tree produced by {@link RubyParser#expression}.
     * @param ctx the parse tree
     */
    void enterExpression(RubyParser.ExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitExpression(RubyParser.ExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#begin_expression}.
     *
     * @param ctx the parse tree
     */
    void enterBegin_expression(RubyParser.Begin_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#begin_expression}.
     *
     * @param ctx the parse tree
     */
    void exitBegin_expression(RubyParser.Begin_expressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#end_expression}.
     *
     * @param ctx the parse tree
     */
    void enterEnd_expression(RubyParser.End_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#end_expression}.
     *
     * @param ctx the parse tree
     */
    void exitEnd_expression(RubyParser.End_expressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#begin_rescue_expression}.
     *
     * @param ctx the parse tree
     */
    void enterBegin_rescue_expression(RubyParser.Begin_rescue_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#begin_rescue_expression}.
     *
     * @param ctx the parse tree
     */
    void exitBegin_rescue_expression(RubyParser.Begin_rescue_expressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#error_type}.
     *
     * @param ctx the parse tree
     */
    void enterError_type(RubyParser.Error_typeContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#error_type}.
     *
     * @param ctx the parse tree
     */
    void exitError_type(RubyParser.Error_typeContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#rescue_expression}.
     *
     * @param ctx the parse tree
     */
    void enterRescue_expression(RubyParser.Rescue_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#rescue_expression}.
     *
     * @param ctx the parse tree
     */
    void exitRescue_expression(RubyParser.Rescue_expressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#ensure_expression}.
     *
     * @param ctx the parse tree
     */
    void enterEnsure_expression(RubyParser.Ensure_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#ensure_expression}.
     *
     * @param ctx the parse tree
     */
    void exitEnsure_expression(RubyParser.Ensure_expressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#global_get}.
     *
     * @param ctx the parse tree
     */
    void enterGlobal_get(RubyParser.Global_getContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#global_get}.
     *
     * @param ctx the parse tree
     */
    void exitGlobal_get(RubyParser.Global_getContext ctx);

    /**
	 * Enter a parse tree produced by {@link RubyParser#global_set}.
	 * @param ctx the parse tree
     */
    void enterGlobal_set(RubyParser.Global_setContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#global_set}.
     *
     * @param ctx the parse tree
     */
    void exitGlobal_set(RubyParser.Global_setContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#global_result}.
     *
     * @param ctx the parse tree
     */
    void enterGlobal_result(RubyParser.Global_resultContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#global_result}.
     *
     * @param ctx the parse tree
     */
    void exitGlobal_result(RubyParser.Global_resultContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#instance_get}.
     *
     * @param ctx the parse tree
     */
    void enterInstance_get(RubyParser.Instance_getContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#instance_get}.
     *
     * @param ctx the parse tree
     */
    void exitInstance_get(RubyParser.Instance_getContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#instance_set}.
     *
     * @param ctx the parse tree
     */
    void enterInstance_set(RubyParser.Instance_setContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#instance_set}.
     *
     * @param ctx the parse tree
     */
    void exitInstance_set(RubyParser.Instance_setContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#instance_result}.
     *
     * @param ctx the parse tree
     */
    void enterInstance_result(RubyParser.Instance_resultContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#instance_result}.
     *
     * @param ctx the parse tree
     */
    void exitInstance_result(RubyParser.Instance_resultContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#const_set}.
     *
     * @param ctx the parse tree
     */
    void enterConst_set(RubyParser.Const_setContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#const_set}.
     *
     * @param ctx the parse tree
     */
    void exitConst_set(RubyParser.Const_setContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#function_inline_call}.
     *
     * @param ctx the parse tree
     */
    void enterFunction_inline_call(RubyParser.Function_inline_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_inline_call}.
	 * @param ctx the parse tree
	 */
	void exitFunction_inline_call(RubyParser.Function_inline_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#require_block}.
	 * @param ctx the parse tree
	 */
	void enterRequire_block(RubyParser.Require_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#require_block}.
     * @param ctx the parse tree
     */
    void exitRequire_block(RubyParser.Require_blockContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#pir_inline}.
     *
     * @param ctx the parse tree
     */
    void enterPir_inline(RubyParser.Pir_inlineContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#pir_inline}.
     *
     * @param ctx the parse tree
     */
    void exitPir_inline(RubyParser.Pir_inlineContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#pir_expression_list}.
     *
     * @param ctx the parse tree
     */
    void enterPir_expression_list(RubyParser.Pir_expression_listContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#pir_expression_list}.
     *
     * @param ctx the parse tree
     */
    void exitPir_expression_list(RubyParser.Pir_expression_listContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#class_definition}.
     *
     * @param ctx the parse tree
     */
    void enterClass_definition(RubyParser.Class_definitionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#class_definition}.
     *
     * @param ctx the parse tree
     */
    void exitClass_definition(RubyParser.Class_definitionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#module_definition}.
     *
     * @param ctx the parse tree
     */
    void enterModule_definition(RubyParser.Module_definitionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#module_definition}.
     *
     * @param ctx the parse tree
     */
    void exitModule_definition(RubyParser.Module_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(RubyParser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(RubyParser.Function_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_definition_body}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_body(RubyParser.Function_definition_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_definition_body}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_body(RubyParser.Function_definition_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_definition_header}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_header(RubyParser.Function_definition_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_definition_header}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_header(RubyParser.Function_definition_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_name}.
	 * @param ctx the parse tree
	 */
	void enterFunction_name(RubyParser.Function_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_name}.
	 * @param ctx the parse tree
	 */
	void exitFunction_name(RubyParser.Function_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_definition_params}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_params(RubyParser.Function_definition_paramsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_definition_params}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_params(RubyParser.Function_definition_paramsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_definition_params_list}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_params_list(RubyParser.Function_definition_params_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_definition_params_list}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_params_list(RubyParser.Function_definition_params_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_definition_param_id}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_param_id(RubyParser.Function_definition_param_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_definition_param_id}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_param_id(RubyParser.Function_definition_param_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(RubyParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(RubyParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_call}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call(RubyParser.Function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_call}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call(RubyParser.Function_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_call_param_list}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_param_list(RubyParser.Function_call_param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_call_param_list}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_param_list(RubyParser.Function_call_param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_call_params}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_params(RubyParser.Function_call_paramsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_call_params}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_params(RubyParser.Function_call_paramsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_param(RubyParser.Function_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_param(RubyParser.Function_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_unnamed_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_unnamed_param(RubyParser.Function_unnamed_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_unnamed_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_unnamed_param(RubyParser.Function_unnamed_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_named_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_named_param(RubyParser.Function_named_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_named_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_named_param(RubyParser.Function_named_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#function_call_assignment}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_assignment(RubyParser.Function_call_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#function_call_assignment}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_assignment(RubyParser.Function_call_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#all_result}.
	 * @param ctx the parse tree
	 */
	void enterAll_result(RubyParser.All_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#all_result}.
	 * @param ctx the parse tree
	 */
	void exitAll_result(RubyParser.All_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#elsif_statement}.
	 * @param ctx the parse tree
	 */
	void enterElsif_statement(RubyParser.Elsif_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#elsif_statement}.
	 * @param ctx the parse tree
	 */
	void exitElsif_statement(RubyParser.Elsif_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#if_elsif_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_elsif_statement(RubyParser.If_elsif_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#if_elsif_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_elsif_statement(RubyParser.If_elsif_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(RubyParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(RubyParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#unless_statement}.
	 * @param ctx the parse tree
	 */
	void enterUnless_statement(RubyParser.Unless_statementContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#unless_statement}.
     *
     * @param ctx the parse tree
     */
    void exitUnless_statement(RubyParser.Unless_statementContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#while_statement}.
     *
     * @param ctx the parse tree
     */
    void enterWhile_statement(RubyParser.While_statementContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#while_statement}.
     *
     * @param ctx the parse tree
     */
    void exitWhile_statement(RubyParser.While_statementContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#all_assignment}.
     *
     * @param ctx the parse tree
     */
    void enterAll_assignment(RubyParser.All_assignmentContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#all_assignment}.
     *
     * @param ctx the parse tree
     */
    void exitAll_assignment(RubyParser.All_assignmentContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#for_statement}.
     *
     * @param ctx the parse tree
     */
    void enterFor_statement(RubyParser.For_statementContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#for_statement}.
     *
     * @param ctx the parse tree
     */
    void exitFor_statement(RubyParser.For_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#for_each_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_each_statement(RubyParser.For_each_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#for_each_statement}.
     * @param ctx the parse tree
     */
    void exitFor_each_statement(RubyParser.For_each_statementContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#cond_expression}.
     *
     * @param ctx the parse tree
     */
    void enterCond_expression(RubyParser.Cond_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#cond_expression}.
     *
     * @param ctx the parse tree
     */
    void exitCond_expression(RubyParser.Cond_expressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#loop_expression}.
     *
     * @param ctx the parse tree
     */
    void enterLoop_expression(RubyParser.Loop_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#loop_expression}.
	 * @param ctx the parse tree
	 */
	void exitLoop_expression(RubyParser.Loop_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#hash_expression}.
	 * @param ctx the parse tree
	 */
	void enterHash_expression(RubyParser.Hash_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#hash_expression}.
	 * @param ctx the parse tree
	 */
	void exitHash_expression(RubyParser.Hash_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#statement_body}.
	 * @param ctx the parse tree
	 */
	void enterStatement_body(RubyParser.Statement_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#statement_body}.
	 * @param ctx the parse tree
	 */
	void exitStatement_body(RubyParser.Statement_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#statement_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_expression_list(RubyParser.Statement_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#statement_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_expression_list(RubyParser.Statement_expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(RubyParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(RubyParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#dynamic_assignment}.
	 * @param ctx the parse tree
	 */
	void enterDynamic_assignment(RubyParser.Dynamic_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#dynamic_assignment}.
	 * @param ctx the parse tree
	 */
	void exitDynamic_assignment(RubyParser.Dynamic_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#int_assignment}.
	 * @param ctx the parse tree
	 */
	void enterInt_assignment(RubyParser.Int_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#int_assignment}.
	 * @param ctx the parse tree
	 */
	void exitInt_assignment(RubyParser.Int_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#float_assignment}.
	 * @param ctx the parse tree
	 */
	void enterFloat_assignment(RubyParser.Float_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#float_assignment}.
	 * @param ctx the parse tree
	 */
	void exitFloat_assignment(RubyParser.Float_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#string_assignment}.
	 * @param ctx the parse tree
	 */
	void enterString_assignment(RubyParser.String_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#string_assignment}.
	 * @param ctx the parse tree
	 */
	void exitString_assignment(RubyParser.String_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#initial_array_assignment}.
	 * @param ctx the parse tree
	 */
	void enterInitial_array_assignment(RubyParser.Initial_array_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#initial_array_assignment}.
	 * @param ctx the parse tree
	 */
	void exitInitial_array_assignment(RubyParser.Initial_array_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#array_assignment}.
	 * @param ctx the parse tree
	 */
	void enterArray_assignment(RubyParser.Array_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#array_assignment}.
	 * @param ctx the parse tree
	 */
	void exitArray_assignment(RubyParser.Array_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#array_definition}.
	 * @param ctx the parse tree
	 */
	void enterArray_definition(RubyParser.Array_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#array_definition}.
	 * @param ctx the parse tree
	 */
	void exitArray_definition(RubyParser.Array_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#array_definition_elements}.
     * @param ctx the parse tree
     */
    void enterArray_definition_elements(RubyParser.Array_definition_elementsContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#array_definition_elements}.
     *
     * @param ctx the parse tree
     */
    void exitArray_definition_elements(RubyParser.Array_definition_elementsContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#array_selector}.
     *
     * @param ctx the parse tree
     */
    void enterArray_selector(RubyParser.Array_selectorContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#array_selector}.
     *
     * @param ctx the parse tree
     */
    void exitArray_selector(RubyParser.Array_selectorContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#dynamic_result}.
     *
     * @param ctx the parse tree
     */
    void enterDynamic_result(RubyParser.Dynamic_resultContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#dynamic_result}.
     * @param ctx the parse tree
	 */
	void exitDynamic_result(RubyParser.Dynamic_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#map_result}.
	 * @param ctx the parse tree
	 */
	void enterMap_result(RubyParser.Map_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#map_result}.
	 * @param ctx the parse tree
	 */
	void exitMap_result(RubyParser.Map_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#dynamic_}.
	 * @param ctx the parse tree
	 */
	void enterDynamic_(RubyParser.Dynamic_Context ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#dynamic_}.
	 * @param ctx the parse tree
	 */
	void exitDynamic_(RubyParser.Dynamic_Context ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#int_result}.
	 * @param ctx the parse tree
	 */
	void enterInt_result(RubyParser.Int_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#int_result}.
	 * @param ctx the parse tree
	 */
	void exitInt_result(RubyParser.Int_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#float_result}.
	 * @param ctx the parse tree
	 */
	void enterFloat_result(RubyParser.Float_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#float_result}.
	 * @param ctx the parse tree
	 */
	void exitFloat_result(RubyParser.Float_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#string_result}.
	 * @param ctx the parse tree
	 */
	void enterString_result(RubyParser.String_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#string_result}.
	 * @param ctx the parse tree
	 */
	void exitString_result(RubyParser.String_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#comparison_list}.
	 * @param ctx the parse tree
	 */
	void enterComparison_list(RubyParser.Comparison_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#comparison_list}.
	 * @param ctx the parse tree
	 */
	void exitComparison_list(RubyParser.Comparison_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(RubyParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(RubyParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#comp_var}.
	 * @param ctx the parse tree
	 */
	void enterComp_var(RubyParser.Comp_varContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#comp_var}.
     *
     * @param ctx the parse tree
     */
    void exitComp_var(RubyParser.Comp_varContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#lvalue}.
     *
     * @param ctx the parse tree
     */
    void enterLvalue(RubyParser.LvalueContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#lvalue}.
     *
     * @param ctx the parse tree
     */
    void exitLvalue(RubyParser.LvalueContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#rvalue}.
     *
     * @param ctx the parse tree
     */
    void enterRvalue(RubyParser.RvalueContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#rvalue}.
     *
     * @param ctx the parse tree
     */
    void exitRvalue(RubyParser.RvalueContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#break_expression}.
     *
     * @param ctx the parse tree
     */
    void enterBreak_expression(RubyParser.Break_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#break_expression}.
     *
     * @param ctx the parse tree
     */
    void exitBreak_expression(RubyParser.Break_expressionContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#raise_expression}.
     *
     * @param ctx the parse tree
     */
    void enterRaise_expression(RubyParser.Raise_expressionContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#raise_expression}.
     * @param ctx the parse tree
	 */
	void exitRaise_expression(RubyParser.Raise_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#yield_expression}.
	 * @param ctx the parse tree
	 */
	void enterYield_expression(RubyParser.Yield_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#yield_expression}.
	 * @param ctx the parse tree
	 */
	void exitYield_expression(RubyParser.Yield_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#literal_t}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_t(RubyParser.Literal_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#literal_t}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_t(RubyParser.Literal_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#float_t}.
	 * @param ctx the parse tree
	 */
	void enterFloat_t(RubyParser.Float_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#float_t}.
	 * @param ctx the parse tree
	 */
	void exitFloat_t(RubyParser.Float_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#int_t}.
	 * @param ctx the parse tree
	 */
	void enterInt_t(RubyParser.Int_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#int_t}.
	 * @param ctx the parse tree
	 */
	void exitInt_t(RubyParser.Int_tContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#bool_t}.
     *
     * @param ctx the parse tree
     */
    void enterBool_t(RubyParser.Bool_tContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#bool_t}.
     *
     * @param ctx the parse tree
     */
    void exitBool_t(RubyParser.Bool_tContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#nil_t}.
     *
     * @param ctx the parse tree
     */
    void enterNil_t(RubyParser.Nil_tContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#nil_t}.
     *
     * @param ctx the parse tree
     */
    void exitNil_t(RubyParser.Nil_tContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#id_}.
     *
     * @param ctx the parse tree
     */
    void enterId_(RubyParser.Id_Context ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#id_}.
     *
     * @param ctx the parse tree
     */
    void exitId_(RubyParser.Id_Context ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#id_global}.
     *
     * @param ctx the parse tree
     */
    void enterId_global(RubyParser.Id_globalContext ctx);

    /**
     * Exit a parse tree produced by {@link RubyParser#id_global}.
     *
     * @param ctx the parse tree
     */
    void exitId_global(RubyParser.Id_globalContext ctx);

    /**
     * Enter a parse tree produced by {@link RubyParser#id_instance}.
     * @param ctx the parse tree
	 */
	void enterId_instance(RubyParser.Id_instanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#id_instance}.
	 * @param ctx the parse tree
	 */
	void exitId_instance(RubyParser.Id_instanceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#id_constence}.
	 * @param ctx the parse tree
	 */
	void enterId_constence(RubyParser.Id_constenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#id_constence}.
	 * @param ctx the parse tree
	 */
	void exitId_constence(RubyParser.Id_constenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#id_function}.
	 * @param ctx the parse tree
	 */
	void enterId_function(RubyParser.Id_functionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#id_function}.
	 * @param ctx the parse tree
	 */
	void exitId_function(RubyParser.Id_functionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#terminator}.
	 * @param ctx the parse tree
	 */
	void enterTerminator(RubyParser.TerminatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#terminator}.
	 * @param ctx the parse tree
	 */
	void exitTerminator(RubyParser.TerminatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#else_token}.
	 * @param ctx the parse tree
	 */
	void enterElse_token(RubyParser.Else_tokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#else_token}.
	 * @param ctx the parse tree
	 */
	void exitElse_token(RubyParser.Else_tokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link RubyParser#crlf}.
	 * @param ctx the parse tree
	 */
	void enterCrlf(RubyParser.CrlfContext ctx);
	/**
	 * Exit a parse tree produced by {@link RubyParser#crlf}.
	 * @param ctx the parse tree
	 */
	void exitCrlf(RubyParser.CrlfContext ctx);
}