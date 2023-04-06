// Generated from D:/Application/IDEA/Project/mplas/core/src/main/java/ghaffarian/progex/cpp/parser\Cpp.g4 by ANTLR 4.12.0
package com.scut.mplas.cpp.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CppParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CppVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CppParser#translationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationUnit(CppParser.TranslationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(CppParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#idExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpression(CppParser.IdExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#unqualifiedId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnqualifiedId(CppParser.UnqualifiedIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#qualifiedId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedId(CppParser.QualifiedIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#nestedNameSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedNameSpecifier(CppParser.NestedNameSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(CppParser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#lambdaIntroducer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaIntroducer(CppParser.LambdaIntroducerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#lambdaCapture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaCapture(CppParser.LambdaCaptureContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#captureDefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaptureDefault(CppParser.CaptureDefaultContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#captureList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaptureList(CppParser.CaptureListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#capture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCapture(CppParser.CaptureContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#simpleCapture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCapture(CppParser.SimpleCaptureContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#initcapture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitcapture(CppParser.InitcaptureContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#lambdaDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaDeclarator(CppParser.LambdaDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(CppParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#typeIdOfTheTypeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeIdOfTheTypeId(CppParser.TypeIdOfTheTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(CppParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#pseudoDestructorName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudoDestructorName(CppParser.PseudoDestructorNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(CppParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(CppParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpression(CppParser.NewExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#newPlacement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewPlacement(CppParser.NewPlacementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#newTypeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewTypeId(CppParser.NewTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#newDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewDeclarator(CppParser.NewDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#noPointerNewDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoPointerNewDeclarator(CppParser.NoPointerNewDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#newInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewInitializer(CppParser.NewInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#deleteExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteExpression(CppParser.DeleteExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#noExceptExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoExceptExpression(CppParser.NoExceptExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(CppParser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#pointerMemberExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointerMemberExpression(CppParser.PointerMemberExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(CppParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(CppParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(CppParser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#shiftOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftOperator(CppParser.ShiftOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(CppParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(CppParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(CppParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOrExpression(CppParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveOrExpression(CppParser.InclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(CppParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(CppParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(CppParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(CppParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(CppParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CppParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#constantExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantExpression(CppParser.ConstantExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CppParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#labeledStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledStatement(CppParser.LabeledStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(CppParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(CppParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#statementSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementSeq(CppParser.StatementSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#selectionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionStatement(CppParser.SelectionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(CppParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#iterationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterationStatement(CppParser.IterationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#forInitStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitStatement(CppParser.ForInitStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#forRangeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForRangeDeclaration(CppParser.ForRangeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#forRangeInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForRangeInitializer(CppParser.ForRangeInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStatement(CppParser.JumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#declarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationStatement(CppParser.DeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#declarationseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationseq(CppParser.DeclarationseqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(CppParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#blockDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockDeclaration(CppParser.BlockDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#aliasDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasDeclaration(CppParser.AliasDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleDeclaration(CppParser.SimpleDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#staticAssertDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticAssertDeclaration(CppParser.StaticAssertDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#emptyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyDeclaration(CppParser.EmptyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#attributeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeDeclaration(CppParser.AttributeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#declSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclSpecifier(CppParser.DeclSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#declSpecifierSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclSpecifierSeq(CppParser.DeclSpecifierSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#storageClassSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStorageClassSpecifier(CppParser.StorageClassSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#functionSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionSpecifier(CppParser.FunctionSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#typedefName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedefName(CppParser.TypedefNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#typeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeSpecifier(CppParser.TypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#trailingTypeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailingTypeSpecifier(CppParser.TrailingTypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#typeSpecifierSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeSpecifierSeq(CppParser.TypeSpecifierSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#trailingTypeSpecifierSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailingTypeSpecifierSeq(CppParser.TrailingTypeSpecifierSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#simpleTypeLengthModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeLengthModifier(CppParser.SimpleTypeLengthModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#simpleTypeSignednessModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeSignednessModifier(CppParser.SimpleTypeSignednessModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#simpleTypeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeSpecifier(CppParser.SimpleTypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#theTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTheTypeName(CppParser.TheTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#decltypeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecltypeSpecifier(CppParser.DecltypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#elaboratedTypeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElaboratedTypeSpecifier(CppParser.ElaboratedTypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumName(CppParser.EnumNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumSpecifier(CppParser.EnumSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumHead}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumHead(CppParser.EnumHeadContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#opaqueEnumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpaqueEnumDeclaration(CppParser.OpaqueEnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumkey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumkey(CppParser.EnumkeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumbase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumbase(CppParser.EnumbaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumeratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumeratorList(CppParser.EnumeratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumeratorDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumeratorDefinition(CppParser.EnumeratorDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#enumerator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumerator(CppParser.EnumeratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#namespaceName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespaceName(CppParser.NamespaceNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#originalNamespaceName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOriginalNamespaceName(CppParser.OriginalNamespaceNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#namespaceDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespaceDefinition(CppParser.NamespaceDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#namespaceAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespaceAlias(CppParser.NamespaceAliasContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#namespaceAliasDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespaceAliasDefinition(CppParser.NamespaceAliasDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#qualifiednamespacespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiednamespacespecifier(CppParser.QualifiednamespacespecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#usingDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsingDeclaration(CppParser.UsingDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#usingDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsingDirective(CppParser.UsingDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#asmDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsmDefinition(CppParser.AsmDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#linkageSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinkageSpecification(CppParser.LinkageSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#attributeSpecifierSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeSpecifierSeq(CppParser.AttributeSpecifierSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#attributeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeSpecifier(CppParser.AttributeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#alignmentspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlignmentspecifier(CppParser.AlignmentspecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#attributeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeList(CppParser.AttributeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(CppParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#attributeNamespace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeNamespace(CppParser.AttributeNamespaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#attributeArgumentClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeArgumentClause(CppParser.AttributeArgumentClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#balancedTokenSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBalancedTokenSeq(CppParser.BalancedTokenSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#balancedtoken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBalancedtoken(CppParser.BalancedtokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclaratorList(CppParser.InitDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#initDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclarator(CppParser.InitDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(CppParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#pointerDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointerDeclarator(CppParser.PointerDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#noPointerDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoPointerDeclarator(CppParser.NoPointerDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#parametersAndQualifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametersAndQualifiers(CppParser.ParametersAndQualifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#trailingReturnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailingReturnType(CppParser.TrailingReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#pointerOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointerOperator(CppParser.PointerOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#cvqualifierseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCvqualifierseq(CppParser.CvqualifierseqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#cvQualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCvQualifier(CppParser.CvQualifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#refqualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRefqualifier(CppParser.RefqualifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#declaratorid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaratorid(CppParser.DeclaratoridContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#theTypeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTheTypeId(CppParser.TheTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#abstractDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbstractDeclarator(CppParser.AbstractDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#pointerAbstractDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointerAbstractDeclarator(CppParser.PointerAbstractDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#noPointerAbstractDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoPointerAbstractDeclarator(CppParser.NoPointerAbstractDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#abstractPackDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbstractPackDeclarator(CppParser.AbstractPackDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#noPointerAbstractPackDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoPointerAbstractPackDeclarator(CppParser.NoPointerAbstractPackDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#parameterDeclarationClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclarationClause(CppParser.ParameterDeclarationClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#parameterDeclarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclarationList(CppParser.ParameterDeclarationListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(CppParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(CppParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(CppParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(CppParser.InitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#braceOrEqualInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceOrEqualInitializer(CppParser.BraceOrEqualInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#initializerClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializerClause(CppParser.InitializerClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#initializerList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializerList(CppParser.InitializerListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#bracedInitList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracedInitList(CppParser.BracedInitListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassName(CppParser.ClassNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#classSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassSpecifier(CppParser.ClassSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#classHead}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassHead(CppParser.ClassHeadContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#classHeadName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassHeadName(CppParser.ClassHeadNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#classVirtSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassVirtSpecifier(CppParser.ClassVirtSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#classKey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassKey(CppParser.ClassKeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#memberSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberSpecification(CppParser.MemberSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#memberdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberdeclaration(CppParser.MemberdeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#memberDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaratorList(CppParser.MemberDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#memberDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclarator(CppParser.MemberDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#virtualSpecifierSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVirtualSpecifierSeq(CppParser.VirtualSpecifierSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#virtualSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVirtualSpecifier(CppParser.VirtualSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#pureSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPureSpecifier(CppParser.PureSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#baseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseClause(CppParser.BaseClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#baseSpecifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseSpecifierList(CppParser.BaseSpecifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#baseSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseSpecifier(CppParser.BaseSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#classOrDeclType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrDeclType(CppParser.ClassOrDeclTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#baseTypeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseTypeSpecifier(CppParser.BaseTypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#accessSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessSpecifier(CppParser.AccessSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#conversionFunctionId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConversionFunctionId(CppParser.ConversionFunctionIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#conversionTypeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConversionTypeId(CppParser.ConversionTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#conversionDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConversionDeclarator(CppParser.ConversionDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#constructorInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorInitializer(CppParser.ConstructorInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#memInitializerList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemInitializerList(CppParser.MemInitializerListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#memInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemInitializer(CppParser.MemInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#meminitializerid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeminitializerid(CppParser.MeminitializeridContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#operatorFunctionId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorFunctionId(CppParser.OperatorFunctionIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#literalOperatorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralOperatorId(CppParser.LiteralOperatorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#templateDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateDeclaration(CppParser.TemplateDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#templateparameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateparameterList(CppParser.TemplateparameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#templateParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateParameter(CppParser.TemplateParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(CppParser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#simpleTemplateId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTemplateId(CppParser.SimpleTemplateIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#templateId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateId(CppParser.TemplateIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#templateName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateName(CppParser.TemplateNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#templateArgumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateArgumentList(CppParser.TemplateArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#templateArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateArgument(CppParser.TemplateArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#typeNameSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeNameSpecifier(CppParser.TypeNameSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#explicitInstantiation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitInstantiation(CppParser.ExplicitInstantiationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#explicitSpecialization}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitSpecialization(CppParser.ExplicitSpecializationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#tryBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryBlock(CppParser.TryBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#functionTryBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionTryBlock(CppParser.FunctionTryBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#handlerSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandlerSeq(CppParser.HandlerSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#handler}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandler(CppParser.HandlerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#exceptionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionDeclaration(CppParser.ExceptionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#throwExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowExpression(CppParser.ThrowExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#exceptionSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionSpecification(CppParser.ExceptionSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#dynamicExceptionSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDynamicExceptionSpecification(CppParser.DynamicExceptionSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#typeIdList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeIdList(CppParser.TypeIdListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#noeExceptSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoeExceptSpecification(CppParser.NoeExceptSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#theOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTheOperator(CppParser.TheOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CppParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(CppParser.LiteralContext ctx);
}