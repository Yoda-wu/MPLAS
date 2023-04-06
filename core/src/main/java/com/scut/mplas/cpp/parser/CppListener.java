// Generated from D:/Application/IDEA/Project/mplas/core/src/main/java/ghaffarian/progex/cpp/parser\Cpp.g4 by ANTLR 4.12.0
package com.scut.mplas.cpp.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CppParser}.
 */
public interface CppListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CppParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(CppParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(CppParser.TranslationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(CppParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(CppParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#idExpression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpression(CppParser.IdExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#idExpression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpression(CppParser.IdExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#unqualifiedId}.
	 * @param ctx the parse tree
	 */
	void enterUnqualifiedId(CppParser.UnqualifiedIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#unqualifiedId}.
	 * @param ctx the parse tree
	 */
	void exitUnqualifiedId(CppParser.UnqualifiedIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#qualifiedId}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedId(CppParser.QualifiedIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#qualifiedId}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedId(CppParser.QualifiedIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#nestedNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterNestedNameSpecifier(CppParser.NestedNameSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#nestedNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitNestedNameSpecifier(CppParser.NestedNameSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(CppParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(CppParser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#lambdaIntroducer}.
	 * @param ctx the parse tree
	 */
	void enterLambdaIntroducer(CppParser.LambdaIntroducerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#lambdaIntroducer}.
	 * @param ctx the parse tree
	 */
	void exitLambdaIntroducer(CppParser.LambdaIntroducerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#lambdaCapture}.
	 * @param ctx the parse tree
	 */
	void enterLambdaCapture(CppParser.LambdaCaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#lambdaCapture}.
	 * @param ctx the parse tree
	 */
	void exitLambdaCapture(CppParser.LambdaCaptureContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#captureDefault}.
	 * @param ctx the parse tree
	 */
	void enterCaptureDefault(CppParser.CaptureDefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#captureDefault}.
	 * @param ctx the parse tree
	 */
	void exitCaptureDefault(CppParser.CaptureDefaultContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#captureList}.
	 * @param ctx the parse tree
	 */
	void enterCaptureList(CppParser.CaptureListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#captureList}.
	 * @param ctx the parse tree
	 */
	void exitCaptureList(CppParser.CaptureListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#capture}.
	 * @param ctx the parse tree
	 */
	void enterCapture(CppParser.CaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#capture}.
	 * @param ctx the parse tree
	 */
	void exitCapture(CppParser.CaptureContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#simpleCapture}.
	 * @param ctx the parse tree
	 */
	void enterSimpleCapture(CppParser.SimpleCaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#simpleCapture}.
	 * @param ctx the parse tree
	 */
	void exitSimpleCapture(CppParser.SimpleCaptureContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#initcapture}.
	 * @param ctx the parse tree
	 */
	void enterInitcapture(CppParser.InitcaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#initcapture}.
	 * @param ctx the parse tree
	 */
	void exitInitcapture(CppParser.InitcaptureContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#lambdaDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterLambdaDeclarator(CppParser.LambdaDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#lambdaDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitLambdaDeclarator(CppParser.LambdaDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(CppParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(CppParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#typeIdOfTheTypeId}.
	 * @param ctx the parse tree
	 */
	void enterTypeIdOfTheTypeId(CppParser.TypeIdOfTheTypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#typeIdOfTheTypeId}.
	 * @param ctx the parse tree
	 */
	void exitTypeIdOfTheTypeId(CppParser.TypeIdOfTheTypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(CppParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(CppParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#pseudoDestructorName}.
	 * @param ctx the parse tree
	 */
	void enterPseudoDestructorName(CppParser.PseudoDestructorNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#pseudoDestructorName}.
	 * @param ctx the parse tree
	 */
	void exitPseudoDestructorName(CppParser.PseudoDestructorNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(CppParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(CppParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(CppParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(CppParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpression(CppParser.NewExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpression(CppParser.NewExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#newPlacement}.
	 * @param ctx the parse tree
	 */
	void enterNewPlacement(CppParser.NewPlacementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#newPlacement}.
	 * @param ctx the parse tree
	 */
	void exitNewPlacement(CppParser.NewPlacementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#newTypeId}.
	 * @param ctx the parse tree
	 */
	void enterNewTypeId(CppParser.NewTypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#newTypeId}.
	 * @param ctx the parse tree
	 */
	void exitNewTypeId(CppParser.NewTypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#newDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNewDeclarator(CppParser.NewDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#newDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNewDeclarator(CppParser.NewDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#noPointerNewDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoPointerNewDeclarator(CppParser.NoPointerNewDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#noPointerNewDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoPointerNewDeclarator(CppParser.NoPointerNewDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#newInitializer}.
	 * @param ctx the parse tree
	 */
	void enterNewInitializer(CppParser.NewInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#newInitializer}.
	 * @param ctx the parse tree
	 */
	void exitNewInitializer(CppParser.NewInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#deleteExpression}.
	 * @param ctx the parse tree
	 */
	void enterDeleteExpression(CppParser.DeleteExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#deleteExpression}.
	 * @param ctx the parse tree
	 */
	void exitDeleteExpression(CppParser.DeleteExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#noExceptExpression}.
	 * @param ctx the parse tree
	 */
	void enterNoExceptExpression(CppParser.NoExceptExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#noExceptExpression}.
	 * @param ctx the parse tree
	 */
	void exitNoExceptExpression(CppParser.NoExceptExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(CppParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(CppParser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#pointerMemberExpression}.
	 * @param ctx the parse tree
	 */
	void enterPointerMemberExpression(CppParser.PointerMemberExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#pointerMemberExpression}.
	 * @param ctx the parse tree
	 */
	void exitPointerMemberExpression(CppParser.PointerMemberExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(CppParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(CppParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(CppParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(CppParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(CppParser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(CppParser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#shiftOperator}.
	 * @param ctx the parse tree
	 */
	void enterShiftOperator(CppParser.ShiftOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#shiftOperator}.
	 * @param ctx the parse tree
	 */
	void exitShiftOperator(CppParser.ShiftOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(CppParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(CppParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(CppParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(CppParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(CppParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(CppParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOrExpression(CppParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOrExpression(CppParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveOrExpression(CppParser.InclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveOrExpression(CppParser.InclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(CppParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(CppParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(CppParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(CppParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(CppParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(CppParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(CppParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(CppParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(CppParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(CppParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CppParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CppParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(CppParser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(CppParser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CppParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CppParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void enterLabeledStatement(CppParser.LabeledStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void exitLabeledStatement(CppParser.LabeledStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(CppParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(CppParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(CppParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(CppParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#statementSeq}.
	 * @param ctx the parse tree
	 */
	void enterStatementSeq(CppParser.StatementSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#statementSeq}.
	 * @param ctx the parse tree
	 */
	void exitStatementSeq(CppParser.StatementSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(CppParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(CppParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(CppParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(CppParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterationStatement(CppParser.IterationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterationStatement(CppParser.IterationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#forInitStatement}.
	 * @param ctx the parse tree
	 */
	void enterForInitStatement(CppParser.ForInitStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#forInitStatement}.
	 * @param ctx the parse tree
	 */
	void exitForInitStatement(CppParser.ForInitStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#forRangeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterForRangeDeclaration(CppParser.ForRangeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#forRangeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitForRangeDeclaration(CppParser.ForRangeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#forRangeInitializer}.
	 * @param ctx the parse tree
	 */
	void enterForRangeInitializer(CppParser.ForRangeInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#forRangeInitializer}.
	 * @param ctx the parse tree
	 */
	void exitForRangeInitializer(CppParser.ForRangeInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStatement(CppParser.JumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStatement(CppParser.JumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationStatement(CppParser.DeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationStatement(CppParser.DeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#declarationseq}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationseq(CppParser.DeclarationseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#declarationseq}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationseq(CppParser.DeclarationseqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CppParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CppParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#blockDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterBlockDeclaration(CppParser.BlockDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#blockDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitBlockDeclaration(CppParser.BlockDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#aliasDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAliasDeclaration(CppParser.AliasDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#aliasDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAliasDeclaration(CppParser.AliasDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSimpleDeclaration(CppParser.SimpleDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSimpleDeclaration(CppParser.SimpleDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#staticAssertDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStaticAssertDeclaration(CppParser.StaticAssertDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#staticAssertDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStaticAssertDeclaration(CppParser.StaticAssertDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#emptyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEmptyDeclaration(CppParser.EmptyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#emptyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEmptyDeclaration(CppParser.EmptyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#attributeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAttributeDeclaration(CppParser.AttributeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#attributeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAttributeDeclaration(CppParser.AttributeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#declSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDeclSpecifier(CppParser.DeclSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#declSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDeclSpecifier(CppParser.DeclSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#declSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#declSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#storageClassSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterStorageClassSpecifier(CppParser.StorageClassSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#storageClassSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitStorageClassSpecifier(CppParser.StorageClassSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#functionSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSpecifier(CppParser.FunctionSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#functionSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSpecifier(CppParser.FunctionSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#typedefName}.
	 * @param ctx the parse tree
	 */
	void enterTypedefName(CppParser.TypedefNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#typedefName}.
	 * @param ctx the parse tree
	 */
	void exitTypedefName(CppParser.TypedefNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeSpecifier(CppParser.TypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeSpecifier(CppParser.TypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#trailingTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTrailingTypeSpecifier(CppParser.TrailingTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#trailingTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTrailingTypeSpecifier(CppParser.TrailingTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#typeSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterTypeSpecifierSeq(CppParser.TypeSpecifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#typeSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitTypeSpecifierSeq(CppParser.TypeSpecifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#trailingTypeSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterTrailingTypeSpecifierSeq(CppParser.TrailingTypeSpecifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#trailingTypeSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitTrailingTypeSpecifierSeq(CppParser.TrailingTypeSpecifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#simpleTypeLengthModifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTypeLengthModifier(CppParser.SimpleTypeLengthModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#simpleTypeLengthModifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTypeLengthModifier(CppParser.SimpleTypeLengthModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#simpleTypeSignednessModifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTypeSignednessModifier(CppParser.SimpleTypeSignednessModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#simpleTypeSignednessModifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTypeSignednessModifier(CppParser.SimpleTypeSignednessModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#simpleTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTypeSpecifier(CppParser.SimpleTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#simpleTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTypeSpecifier(CppParser.SimpleTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#theTypeName}.
	 * @param ctx the parse tree
	 */
	void enterTheTypeName(CppParser.TheTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#theTypeName}.
	 * @param ctx the parse tree
	 */
	void exitTheTypeName(CppParser.TheTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#decltypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDecltypeSpecifier(CppParser.DecltypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#decltypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDecltypeSpecifier(CppParser.DecltypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#elaboratedTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterElaboratedTypeSpecifier(CppParser.ElaboratedTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#elaboratedTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitElaboratedTypeSpecifier(CppParser.ElaboratedTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumName}.
	 * @param ctx the parse tree
	 */
	void enterEnumName(CppParser.EnumNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumName}.
	 * @param ctx the parse tree
	 */
	void exitEnumName(CppParser.EnumNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterEnumSpecifier(CppParser.EnumSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitEnumSpecifier(CppParser.EnumSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumHead}.
	 * @param ctx the parse tree
	 */
	void enterEnumHead(CppParser.EnumHeadContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumHead}.
	 * @param ctx the parse tree
	 */
	void exitEnumHead(CppParser.EnumHeadContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#opaqueEnumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterOpaqueEnumDeclaration(CppParser.OpaqueEnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#opaqueEnumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitOpaqueEnumDeclaration(CppParser.OpaqueEnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumkey}.
	 * @param ctx the parse tree
	 */
	void enterEnumkey(CppParser.EnumkeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumkey}.
	 * @param ctx the parse tree
	 */
	void exitEnumkey(CppParser.EnumkeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumbase}.
	 * @param ctx the parse tree
	 */
	void enterEnumbase(CppParser.EnumbaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumbase}.
	 * @param ctx the parse tree
	 */
	void exitEnumbase(CppParser.EnumbaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumeratorList}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorList(CppParser.EnumeratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumeratorList}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorList(CppParser.EnumeratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumeratorDefinition}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorDefinition(CppParser.EnumeratorDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumeratorDefinition}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorDefinition(CppParser.EnumeratorDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void enterEnumerator(CppParser.EnumeratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void exitEnumerator(CppParser.EnumeratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#namespaceName}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceName(CppParser.NamespaceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#namespaceName}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceName(CppParser.NamespaceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#originalNamespaceName}.
	 * @param ctx the parse tree
	 */
	void enterOriginalNamespaceName(CppParser.OriginalNamespaceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#originalNamespaceName}.
	 * @param ctx the parse tree
	 */
	void exitOriginalNamespaceName(CppParser.OriginalNamespaceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#namespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#namespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#namespaceAlias}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceAlias(CppParser.NamespaceAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#namespaceAlias}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceAlias(CppParser.NamespaceAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#namespaceAliasDefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#namespaceAliasDefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#qualifiednamespacespecifier}.
	 * @param ctx the parse tree
	 */
	void enterQualifiednamespacespecifier(CppParser.QualifiednamespacespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#qualifiednamespacespecifier}.
	 * @param ctx the parse tree
	 */
	void exitQualifiednamespacespecifier(CppParser.QualifiednamespacespecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#usingDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterUsingDeclaration(CppParser.UsingDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#usingDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitUsingDeclaration(CppParser.UsingDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#usingDirective}.
	 * @param ctx the parse tree
	 */
	void enterUsingDirective(CppParser.UsingDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#usingDirective}.
	 * @param ctx the parse tree
	 */
	void exitUsingDirective(CppParser.UsingDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#asmDefinition}.
	 * @param ctx the parse tree
	 */
	void enterAsmDefinition(CppParser.AsmDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#asmDefinition}.
	 * @param ctx the parse tree
	 */
	void exitAsmDefinition(CppParser.AsmDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#linkageSpecification}.
	 * @param ctx the parse tree
	 */
	void enterLinkageSpecification(CppParser.LinkageSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#linkageSpecification}.
	 * @param ctx the parse tree
	 */
	void exitLinkageSpecification(CppParser.LinkageSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#attributeSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterAttributeSpecifierSeq(CppParser.AttributeSpecifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#attributeSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitAttributeSpecifierSeq(CppParser.AttributeSpecifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#attributeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAttributeSpecifier(CppParser.AttributeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#attributeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAttributeSpecifier(CppParser.AttributeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#alignmentspecifier}.
	 * @param ctx the parse tree
	 */
	void enterAlignmentspecifier(CppParser.AlignmentspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#alignmentspecifier}.
	 * @param ctx the parse tree
	 */
	void exitAlignmentspecifier(CppParser.AlignmentspecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#attributeList}.
	 * @param ctx the parse tree
	 */
	void enterAttributeList(CppParser.AttributeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#attributeList}.
	 * @param ctx the parse tree
	 */
	void exitAttributeList(CppParser.AttributeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(CppParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(CppParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#attributeNamespace}.
	 * @param ctx the parse tree
	 */
	void enterAttributeNamespace(CppParser.AttributeNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#attributeNamespace}.
	 * @param ctx the parse tree
	 */
	void exitAttributeNamespace(CppParser.AttributeNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#attributeArgumentClause}.
	 * @param ctx the parse tree
	 */
	void enterAttributeArgumentClause(CppParser.AttributeArgumentClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#attributeArgumentClause}.
	 * @param ctx the parse tree
	 */
	void exitAttributeArgumentClause(CppParser.AttributeArgumentClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#balancedTokenSeq}.
	 * @param ctx the parse tree
	 */
	void enterBalancedTokenSeq(CppParser.BalancedTokenSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#balancedTokenSeq}.
	 * @param ctx the parse tree
	 */
	void exitBalancedTokenSeq(CppParser.BalancedTokenSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#balancedtoken}.
	 * @param ctx the parse tree
	 */
	void enterBalancedtoken(CppParser.BalancedtokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#balancedtoken}.
	 * @param ctx the parse tree
	 */
	void exitBalancedtoken(CppParser.BalancedtokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclaratorList(CppParser.InitDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclaratorList(CppParser.InitDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclarator(CppParser.InitDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclarator(CppParser.InitDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(CppParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(CppParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#pointerDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterPointerDeclarator(CppParser.PointerDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#pointerDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitPointerDeclarator(CppParser.PointerDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#noPointerDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoPointerDeclarator(CppParser.NoPointerDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#noPointerDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoPointerDeclarator(CppParser.NoPointerDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#parametersAndQualifiers}.
	 * @param ctx the parse tree
	 */
	void enterParametersAndQualifiers(CppParser.ParametersAndQualifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#parametersAndQualifiers}.
	 * @param ctx the parse tree
	 */
	void exitParametersAndQualifiers(CppParser.ParametersAndQualifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#trailingReturnType}.
	 * @param ctx the parse tree
	 */
	void enterTrailingReturnType(CppParser.TrailingReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#trailingReturnType}.
	 * @param ctx the parse tree
	 */
	void exitTrailingReturnType(CppParser.TrailingReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#pointerOperator}.
	 * @param ctx the parse tree
	 */
	void enterPointerOperator(CppParser.PointerOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#pointerOperator}.
	 * @param ctx the parse tree
	 */
	void exitPointerOperator(CppParser.PointerOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#cvqualifierseq}.
	 * @param ctx the parse tree
	 */
	void enterCvqualifierseq(CppParser.CvqualifierseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#cvqualifierseq}.
	 * @param ctx the parse tree
	 */
	void exitCvqualifierseq(CppParser.CvqualifierseqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#cvQualifier}.
	 * @param ctx the parse tree
	 */
	void enterCvQualifier(CppParser.CvQualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#cvQualifier}.
	 * @param ctx the parse tree
	 */
	void exitCvQualifier(CppParser.CvQualifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#refqualifier}.
	 * @param ctx the parse tree
	 */
	void enterRefqualifier(CppParser.RefqualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#refqualifier}.
	 * @param ctx the parse tree
	 */
	void exitRefqualifier(CppParser.RefqualifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#declaratorid}.
	 * @param ctx the parse tree
	 */
	void enterDeclaratorid(CppParser.DeclaratoridContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#declaratorid}.
	 * @param ctx the parse tree
	 */
	void exitDeclaratorid(CppParser.DeclaratoridContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#theTypeId}.
	 * @param ctx the parse tree
	 */
	void enterTheTypeId(CppParser.TheTypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#theTypeId}.
	 * @param ctx the parse tree
	 */
	void exitTheTypeId(CppParser.TheTypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#abstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterAbstractDeclarator(CppParser.AbstractDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#abstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitAbstractDeclarator(CppParser.AbstractDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#pointerAbstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterPointerAbstractDeclarator(CppParser.PointerAbstractDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#pointerAbstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitPointerAbstractDeclarator(CppParser.PointerAbstractDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#noPointerAbstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoPointerAbstractDeclarator(CppParser.NoPointerAbstractDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#noPointerAbstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoPointerAbstractDeclarator(CppParser.NoPointerAbstractDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#abstractPackDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterAbstractPackDeclarator(CppParser.AbstractPackDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#abstractPackDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitAbstractPackDeclarator(CppParser.AbstractPackDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#noPointerAbstractPackDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoPointerAbstractPackDeclarator(CppParser.NoPointerAbstractPackDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#noPointerAbstractPackDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoPointerAbstractPackDeclarator(CppParser.NoPointerAbstractPackDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#parameterDeclarationClause}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclarationClause(CppParser.ParameterDeclarationClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#parameterDeclarationClause}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclarationClause(CppParser.ParameterDeclarationClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#parameterDeclarationList}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclarationList(CppParser.ParameterDeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#parameterDeclarationList}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclarationList(CppParser.ParameterDeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(CppParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(CppParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(CppParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(CppParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(CppParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(CppParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(CppParser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(CppParser.InitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#braceOrEqualInitializer}.
	 * @param ctx the parse tree
	 */
	void enterBraceOrEqualInitializer(CppParser.BraceOrEqualInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#braceOrEqualInitializer}.
	 * @param ctx the parse tree
	 */
	void exitBraceOrEqualInitializer(CppParser.BraceOrEqualInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#initializerClause}.
	 * @param ctx the parse tree
	 */
	void enterInitializerClause(CppParser.InitializerClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#initializerClause}.
	 * @param ctx the parse tree
	 */
	void exitInitializerClause(CppParser.InitializerClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void enterInitializerList(CppParser.InitializerListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void exitInitializerList(CppParser.InitializerListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#bracedInitList}.
	 * @param ctx the parse tree
	 */
	void enterBracedInitList(CppParser.BracedInitListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#bracedInitList}.
	 * @param ctx the parse tree
	 */
	void exitBracedInitList(CppParser.BracedInitListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(CppParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(CppParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#classSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterClassSpecifier(CppParser.ClassSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#classSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitClassSpecifier(CppParser.ClassSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#classHead}.
	 * @param ctx the parse tree
	 */
	void enterClassHead(CppParser.ClassHeadContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#classHead}.
	 * @param ctx the parse tree
	 */
	void exitClassHead(CppParser.ClassHeadContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#classHeadName}.
	 * @param ctx the parse tree
	 */
	void enterClassHeadName(CppParser.ClassHeadNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#classHeadName}.
	 * @param ctx the parse tree
	 */
	void exitClassHeadName(CppParser.ClassHeadNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#classVirtSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterClassVirtSpecifier(CppParser.ClassVirtSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#classVirtSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitClassVirtSpecifier(CppParser.ClassVirtSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#classKey}.
	 * @param ctx the parse tree
	 */
	void enterClassKey(CppParser.ClassKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#classKey}.
	 * @param ctx the parse tree
	 */
	void exitClassKey(CppParser.ClassKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#memberSpecification}.
	 * @param ctx the parse tree
	 */
	void enterMemberSpecification(CppParser.MemberSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#memberSpecification}.
	 * @param ctx the parse tree
	 */
	void exitMemberSpecification(CppParser.MemberSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#memberdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberdeclaration(CppParser.MemberdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#memberdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberdeclaration(CppParser.MemberdeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#memberDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaratorList(CppParser.MemberDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#memberDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaratorList(CppParser.MemberDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#memberDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclarator(CppParser.MemberDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#memberDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclarator(CppParser.MemberDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#virtualSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterVirtualSpecifierSeq(CppParser.VirtualSpecifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#virtualSpecifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitVirtualSpecifierSeq(CppParser.VirtualSpecifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#virtualSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterVirtualSpecifier(CppParser.VirtualSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#virtualSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitVirtualSpecifier(CppParser.VirtualSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#pureSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterPureSpecifier(CppParser.PureSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#pureSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitPureSpecifier(CppParser.PureSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#baseClause}.
	 * @param ctx the parse tree
	 */
	void enterBaseClause(CppParser.BaseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#baseClause}.
	 * @param ctx the parse tree
	 */
	void exitBaseClause(CppParser.BaseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#baseSpecifierList}.
	 * @param ctx the parse tree
	 */
	void enterBaseSpecifierList(CppParser.BaseSpecifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#baseSpecifierList}.
	 * @param ctx the parse tree
	 */
	void exitBaseSpecifierList(CppParser.BaseSpecifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#baseSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterBaseSpecifier(CppParser.BaseSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#baseSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitBaseSpecifier(CppParser.BaseSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#classOrDeclType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrDeclType(CppParser.ClassOrDeclTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#classOrDeclType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrDeclType(CppParser.ClassOrDeclTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#baseTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterBaseTypeSpecifier(CppParser.BaseTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#baseTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitBaseTypeSpecifier(CppParser.BaseTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#accessSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAccessSpecifier(CppParser.AccessSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#accessSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAccessSpecifier(CppParser.AccessSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#conversionFunctionId}.
	 * @param ctx the parse tree
	 */
	void enterConversionFunctionId(CppParser.ConversionFunctionIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#conversionFunctionId}.
	 * @param ctx the parse tree
	 */
	void exitConversionFunctionId(CppParser.ConversionFunctionIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#conversionTypeId}.
	 * @param ctx the parse tree
	 */
	void enterConversionTypeId(CppParser.ConversionTypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#conversionTypeId}.
	 * @param ctx the parse tree
	 */
	void exitConversionTypeId(CppParser.ConversionTypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#conversionDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterConversionDeclarator(CppParser.ConversionDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#conversionDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitConversionDeclarator(CppParser.ConversionDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#constructorInitializer}.
	 * @param ctx the parse tree
	 */
	void enterConstructorInitializer(CppParser.ConstructorInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#constructorInitializer}.
	 * @param ctx the parse tree
	 */
	void exitConstructorInitializer(CppParser.ConstructorInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#memInitializerList}.
	 * @param ctx the parse tree
	 */
	void enterMemInitializerList(CppParser.MemInitializerListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#memInitializerList}.
	 * @param ctx the parse tree
	 */
	void exitMemInitializerList(CppParser.MemInitializerListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#memInitializer}.
	 * @param ctx the parse tree
	 */
	void enterMemInitializer(CppParser.MemInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#memInitializer}.
	 * @param ctx the parse tree
	 */
	void exitMemInitializer(CppParser.MemInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#meminitializerid}.
	 * @param ctx the parse tree
	 */
	void enterMeminitializerid(CppParser.MeminitializeridContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#meminitializerid}.
	 * @param ctx the parse tree
	 */
	void exitMeminitializerid(CppParser.MeminitializeridContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#operatorFunctionId}.
	 * @param ctx the parse tree
	 */
	void enterOperatorFunctionId(CppParser.OperatorFunctionIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#operatorFunctionId}.
	 * @param ctx the parse tree
	 */
	void exitOperatorFunctionId(CppParser.OperatorFunctionIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#literalOperatorId}.
	 * @param ctx the parse tree
	 */
	void enterLiteralOperatorId(CppParser.LiteralOperatorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#literalOperatorId}.
	 * @param ctx the parse tree
	 */
	void exitLiteralOperatorId(CppParser.LiteralOperatorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#templateDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTemplateDeclaration(CppParser.TemplateDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#templateDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTemplateDeclaration(CppParser.TemplateDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#templateparameterList}.
	 * @param ctx the parse tree
	 */
	void enterTemplateparameterList(CppParser.TemplateparameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#templateparameterList}.
	 * @param ctx the parse tree
	 */
	void exitTemplateparameterList(CppParser.TemplateparameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#templateParameter}.
	 * @param ctx the parse tree
	 */
	void enterTemplateParameter(CppParser.TemplateParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#templateParameter}.
	 * @param ctx the parse tree
	 */
	void exitTemplateParameter(CppParser.TemplateParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(CppParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(CppParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#simpleTemplateId}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTemplateId(CppParser.SimpleTemplateIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#simpleTemplateId}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTemplateId(CppParser.SimpleTemplateIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#templateId}.
	 * @param ctx the parse tree
	 */
	void enterTemplateId(CppParser.TemplateIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#templateId}.
	 * @param ctx the parse tree
	 */
	void exitTemplateId(CppParser.TemplateIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#templateName}.
	 * @param ctx the parse tree
	 */
	void enterTemplateName(CppParser.TemplateNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#templateName}.
	 * @param ctx the parse tree
	 */
	void exitTemplateName(CppParser.TemplateNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#templateArgumentList}.
	 * @param ctx the parse tree
	 */
	void enterTemplateArgumentList(CppParser.TemplateArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#templateArgumentList}.
	 * @param ctx the parse tree
	 */
	void exitTemplateArgumentList(CppParser.TemplateArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#templateArgument}.
	 * @param ctx the parse tree
	 */
	void enterTemplateArgument(CppParser.TemplateArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#templateArgument}.
	 * @param ctx the parse tree
	 */
	void exitTemplateArgument(CppParser.TemplateArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#typeNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeNameSpecifier(CppParser.TypeNameSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#typeNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeNameSpecifier(CppParser.TypeNameSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#explicitInstantiation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitInstantiation(CppParser.ExplicitInstantiationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#explicitInstantiation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitInstantiation(CppParser.ExplicitInstantiationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#explicitSpecialization}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSpecialization(CppParser.ExplicitSpecializationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#explicitSpecialization}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSpecialization(CppParser.ExplicitSpecializationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#tryBlock}.
	 * @param ctx the parse tree
	 */
	void enterTryBlock(CppParser.TryBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#tryBlock}.
	 * @param ctx the parse tree
	 */
	void exitTryBlock(CppParser.TryBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#functionTryBlock}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTryBlock(CppParser.FunctionTryBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#functionTryBlock}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTryBlock(CppParser.FunctionTryBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#handlerSeq}.
	 * @param ctx the parse tree
	 */
	void enterHandlerSeq(CppParser.HandlerSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#handlerSeq}.
	 * @param ctx the parse tree
	 */
	void exitHandlerSeq(CppParser.HandlerSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#handler}.
	 * @param ctx the parse tree
	 */
	void enterHandler(CppParser.HandlerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#handler}.
	 * @param ctx the parse tree
	 */
	void exitHandler(CppParser.HandlerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#exceptionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExceptionDeclaration(CppParser.ExceptionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#exceptionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExceptionDeclaration(CppParser.ExceptionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#throwExpression}.
	 * @param ctx the parse tree
	 */
	void enterThrowExpression(CppParser.ThrowExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#throwExpression}.
	 * @param ctx the parse tree
	 */
	void exitThrowExpression(CppParser.ThrowExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#exceptionSpecification}.
	 * @param ctx the parse tree
	 */
	void enterExceptionSpecification(CppParser.ExceptionSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#exceptionSpecification}.
	 * @param ctx the parse tree
	 */
	void exitExceptionSpecification(CppParser.ExceptionSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#dynamicExceptionSpecification}.
	 * @param ctx the parse tree
	 */
	void enterDynamicExceptionSpecification(CppParser.DynamicExceptionSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#dynamicExceptionSpecification}.
	 * @param ctx the parse tree
	 */
	void exitDynamicExceptionSpecification(CppParser.DynamicExceptionSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#typeIdList}.
	 * @param ctx the parse tree
	 */
	void enterTypeIdList(CppParser.TypeIdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#typeIdList}.
	 * @param ctx the parse tree
	 */
	void exitTypeIdList(CppParser.TypeIdListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#noeExceptSpecification}.
	 * @param ctx the parse tree
	 */
	void enterNoeExceptSpecification(CppParser.NoeExceptSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#noeExceptSpecification}.
	 * @param ctx the parse tree
	 */
	void exitNoeExceptSpecification(CppParser.NoeExceptSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#theOperator}.
	 * @param ctx the parse tree
	 */
	void enterTheOperator(CppParser.TheOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#theOperator}.
	 * @param ctx the parse tree
	 */
	void exitTheOperator(CppParser.TheOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CppParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(CppParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CppParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(CppParser.LiteralContext ctx);
}