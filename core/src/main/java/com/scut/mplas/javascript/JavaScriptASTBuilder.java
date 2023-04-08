package com.scut.mplas.javascript;

import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
import com.scut.mplas.java.JavaASTBuilder;
import com.scut.mplas.java.parser.JavaBaseVisitor;
import com.scut.mplas.java.parser.JavaLexer;
import com.scut.mplas.java.parser.JavaParser;
import com.scut.mplas.javascript.parser.JavaScriptBaseVisitor;
import com.scut.mplas.javascript.parser.JavaScriptLexer;
import com.scut.mplas.javascript.parser.JavaScriptParser;
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
import java.util.LinkedHashMap;
import java.util.Map;

public class JavaScriptASTBuilder {
    public static AbstractSyntaxTree build(String javaFile) throws IOException {
        return build(new File(javaFile));
    }

    /**
     * ‌Build and return the Abstract Syntax Tree (AST) for the given JavaScript source file.
     */
    public static AbstractSyntaxTree build(File jsFile) throws IOException {
        if (!jsFile.getName().endsWith(".java"))
            throw new IOException("Not a JavaScript File!");
        InputStream inFile = new FileInputStream(jsFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        JavaScriptLexer lexer = new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();
        return build(jsFile.getPath(), tree, null, null);
    }

    public static AbstractSyntaxTree build(String fileName, InputStream inputStream) throws IOException {

        InputStream inFile = inputStream;
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        JavaScriptLexer lexer = new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();
        return build(fileName, tree, null, null);
    }

    /**
     * ‌Build and return the Abstract Syntax Tree (AST) for the given Parse-Tree.
     * The 'ctxProps' map includes contextual-properties for particular nodes
     * in the parse-tree, which can be used for linking this graph with other
     * graphs by using the same parse-tree and the same contextual-properties.
     */
    public static AbstractSyntaxTree build(String filePath, ParseTree tree,
                                           String propKey, Map<ParserRuleContext, Object> ctxProps) {
        JavaScriptASTBuilder.AbstractSyntaxVisitor visitor = new JavaScriptASTBuilder.AbstractSyntaxVisitor(filePath, propKey, ctxProps);
        Logger.debug("Visitor building AST of: " + filePath);
        return visitor.build(tree);
    }

    private static class AbstractSyntaxVisitor extends JavaScriptBaseVisitor<String> {
        private String propKey;
        private String typeModifier;
        private String memberModifier;
        private Deque<ASNode> parentStack;
        private final AbstractSyntaxTree AST;
        private Map<String, String> vars, fields, methods;
        private int varsCounter, fieldsCounter, methodsCounter;
        private Map<ParserRuleContext, Object> contexutalProperties;

        public AbstractSyntaxVisitor(String filePath, String propKey, Map<ParserRuleContext, Object> ctxProps) {
            parentStack = new ArrayDeque<>();
            AST = new AbstractSyntaxTree(filePath);
            this.propKey = propKey;
            contexutalProperties = ctxProps;
            vars = new LinkedHashMap<>();
            fields = new LinkedHashMap<>();
            methods = new LinkedHashMap<>();
            varsCounter = 0;
            fieldsCounter = 0;
            methodsCounter = 0;
        }

        public AbstractSyntaxTree build(ParseTree tree) {
            JavaScriptParser.ProgramContext rootCntx = (JavaScriptParser.ProgramContext) tree;
            AST.root.setCode(new File(AST.filePath).getName());
            parentStack.push(AST.root);
            if (rootCntx.sourceElements() != null) {
                visit(rootCntx.sourceElements());
            }
            parentStack.pop();
            vars.clear();
            fields.clear();
            methods.clear();
            return AST;
        }

        //TODO 待解决
        @Override public String visitProgram(JavaScriptParser.ProgramContext ctx) {
//            // packageDeclaration :  annotation* 'package' qualifiedName ';'
//            ASNode node = new ASNode(ASNode.Type.PACKAGE);
//            node.setCode(ctx.qualifiedName().getText());
//            node.setLineOfCode(ctx.getStart().getLine());
//            Logger.debug("Adding package");
//            AST.addVertex(node);
//            AST.addEdge(parentStack.peek(), node);
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSourceElement(JavaScriptParser.SourceElementContext ctx) {
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitStatement(JavaScriptParser.StatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBlock(JavaScriptParser.BlockContext ctx) {
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitStatementList(JavaScriptParser.StatementListContext ctx) { return visitChildren(ctx); }

        @Override public String visitImportStatement(JavaScriptParser.ImportStatementContext ctx) {
            //Import importFromBlock
            return visit(ctx.Import())+" "+visit(ctx.importFromBlock());
        }

        @Override public String visitImportFromBlock(JavaScriptParser.ImportFromBlockContext ctx) {
            //importDefault? (importNamespace | moduleItems) importFrom eos
            //    | StringLiteral eos
            return visit(ctx.importDefault())+"? ("+visit(ctx.importNamespace())+" | "
                    +visit(ctx.moduleItems())+" "+visit(ctx.importFrom())+" "+visit(ctx.eos())
                    +" | "+visit(ctx.StringLiteral())+ " "+visit(ctx.eos());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitModuleItems(JavaScriptParser.ModuleItemsContext ctx) { return visitChildren(ctx); }

        @Override public String visitImportDefault(JavaScriptParser.ImportDefaultContext ctx) {
            return visit(ctx.aliasName())+" ,";
        }

        @Override public String visitImportNamespace(JavaScriptParser.ImportNamespaceContext ctx) {
            //importNamespaceDeclration:     : ('*' | identifierName) (As identifierName)?
            StringBuffer stringBuffer=new StringBuffer();
            if (ctx.identifierName()!=null){
                stringBuffer.append(visit(ctx.identifierName(0)));
            }else{
                stringBuffer.append("* ");
            }
            if (ctx.identifierName().size()>1){
                stringBuffer.append(visit(ctx.As()));
                stringBuffer.append(" ");
                stringBuffer.append(visit(ctx.identifierName(1)));
            }
            return stringBuffer.toString();
        }

        @Override public String visitImportFrom(JavaScriptParser.ImportFromContext ctx) {
            return visit(ctx.From())+" "+visit(ctx.StringLiteral()) ;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAliasName(JavaScriptParser.AliasNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExportDeclaration(JavaScriptParser.ExportDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExportDefaultDeclaration(JavaScriptParser.ExportDefaultDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExportFromBlock(JavaScriptParser.ExportFromBlockContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeclaration(JavaScriptParser.DeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitVariableStatement(JavaScriptParser.VariableStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitVariableDeclarationList(JavaScriptParser.VariableDeclarationListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEmptyStatement_(JavaScriptParser.EmptyStatement_Context ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) { return visitChildren(ctx); }

        @Override public String visitIfStatement(JavaScriptParser.IfStatementContext ctx) {
            // 'if' parExpression statement ('else' statement)?
            ASNode ifNode = new ASNode(ASNode.Type.IF);
            ifNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(ifNode);
            AST.addEdge(parentStack.peek(), ifNode);
            //
            ASNode cond = new ASNode(ASNode.Type.CONDITION);
//            cond.setCode(getOriginalCodeText(ctx.parEexpression().expression());
//            cond.setNormalizedCode(visit(ctx.parExpression().expression()));
            cond.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(cond);
            AST.addEdge(ifNode, cond);
            //
            ASNode thenNode = new ASNode(ASNode.Type.THEN);
            thenNode.setLineOfCode(ctx.statement(0).getStart().getLine());
            AST.addVertex(thenNode);
            AST.addEdge(ifNode, thenNode);
            parentStack.push(thenNode);
            visit(ctx.statement(0));
            parentStack.pop();
            //
            if (ctx.statement(1) != null) {
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.statement(1).getStart().getLine());
                AST.addVertex(elseNode);
                AST.addEdge(ifNode, elseNode);
                parentStack.push(elseNode);
                visit(ctx.statement(1));
                parentStack.pop();
            }
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDoStatement(JavaScriptParser.DoStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitWhileStatement(JavaScriptParser.WhileStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForStatement(JavaScriptParser.ForStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForInStatement(JavaScriptParser.ForInStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForOfStatement(JavaScriptParser.ForOfStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitVarModifier(JavaScriptParser.VarModifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBreakStatement(JavaScriptParser.BreakStatementContext ctx) {
            if (ctx.identifier().Identifier()==null){
                visitStatement(ctx,null);
            }else{
                visitStatement(ctx,"break $LABEL");
            }
            return "";
        }

        @Override public String visitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) {
            return "return "+visit(ctx.expressionSequence());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitWithStatement(JavaScriptParser.WithStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCaseBlock(JavaScriptParser.CaseBlockContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCaseClauses(JavaScriptParser.CaseClausesContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCaseClause(JavaScriptParser.CaseClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDefaultClause(JavaScriptParser.DefaultClauseContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTryStatement(JavaScriptParser.TryStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCatchProduction(JavaScriptParser.CatchProductionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFinallyProduction(JavaScriptParser.FinallyProductionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) { return visitChildren(ctx); }

        @Override public String visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
            // classDeclaration
            //   :  'class' Identifier typeParameters?
            //      ('extends' typeType)? ('implements' typeList)? classBody
            ASNode classNode = new ASNode(ASNode.Type.CLASS);
            classNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding class node");
            AST.addVertex(classNode);
            AST.addEdge(parentStack.peek(), classNode);
            //
            ASNode modifierNode = new ASNode(ASNode.Type.MODIFIER);
            modifierNode.setCode(typeModifier);
            modifierNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding class modifier");
            AST.addVertex(modifierNode);
            AST.addEdge(classNode, modifierNode);
            //
            ASNode nameNode = new ASNode(ASNode.Type.NAME);
            String className=ctx.identifier().Identifier().getText();
            nameNode.setCode(className);
            nameNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding class name: " + className);
            AST.addVertex(nameNode);
            AST.addEdge(classNode, nameNode);
//            //TODO js中有type吗？
//            if (ctx.typeType() != null) {
//                ASNode extendsNode = new ASNode(ASNode.Type.EXTENDS);
//                extendsNode.setCode(ctx.typeType().getText());
//                extendsNode.setLineOfCode(ctx.typeType().getStart().getLine());
//                Logger.debug("Adding extends " + ctx.typeType().getText());
//                AST.addVertex(extendsNode);
//                AST.addEdge(classNode, extendsNode);
//            }
//            //
//            if (ctx.typeList() != null) {
//                ASNode implementsNode = new ASNode(ASNode.Type.IMPLEMENTS);
//                implementsNode.setLineOfCode(ctx.typeList().getStart().getLine());
//                Logger.debug("Adding implements node ");
//                AST.addVertex(implementsNode);
//                AST.addEdge(classNode, implementsNode);
//                for (JavaParser.TypeTypeContext type : ctx.typeList().typeType()) {
//                    ASNode node = new ASNode(ASNode.Type.INTERFACE);
//                    node.setCode(type.getText());
//                    node.setLineOfCode(type.getStart().getLine());
//                    Logger.debug("Adding interface " + type.getText());
//                    AST.addVertex(node);
//                    AST.addEdge(implementsNode, node);
//                }
//            }
            parentStack.push(classNode);

            visit(ctx.classTail());
            parentStack.pop();
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassTail(JavaScriptParser.ClassTailContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassElement(JavaScriptParser.ClassElementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSourceElements(JavaScriptParser.SourceElementsContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArrayLiteral(JavaScriptParser.ArrayLiteralContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitElementList(JavaScriptParser.ElementListContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArrayElement(JavaScriptParser.ArrayElementContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPropertyExpressionAssignment(JavaScriptParser.PropertyExpressionAssignmentContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitComputedPropertyExpressionAssignment(JavaScriptParser.ComputedPropertyExpressionAssignmentContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionProperty(JavaScriptParser.FunctionPropertyContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPropertyGetter(JavaScriptParser.PropertyGetterContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPropertySetter(JavaScriptParser.PropertySetterContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPropertyShorthand(JavaScriptParser.PropertyShorthandContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitPropertyName(JavaScriptParser.PropertyNameContext ctx) { return visitChildren(ctx); }

        @Override public String visitArguments(JavaScriptParser.ArgumentsContext ctx) {
            // arguments :  '(' expressionList? ')'
            if (ctx.argument() == null)
                return "()";
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("(");
            for (JavaScriptParser.ArgumentContext argument : ctx.argument()) {
                stringBuffer.append(visit(argument));
            }
            stringBuffer.append(")");
            return stringBuffer.toString();
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArgument(JavaScriptParser.ArgumentContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExpressionSequence(JavaScriptParser.ExpressionSequenceContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateStringExpression(JavaScriptParser.TemplateStringExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTernaryExpression(JavaScriptParser.TernaryExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitLogicalAndExpression(JavaScriptParser.LogicalAndExpressionContext ctx) {
            // exprBitAnd :  expression '&&' expression
            return visit(ctx.singleExpression(0)) + " && " + visit(ctx.singleExpression(1));
        }

        @Override public String visitPowerExpression(JavaScriptParser.PowerExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+" ** "+visit(ctx.singleExpression(1));
        }

        @Override public String visitPreIncrementExpression(JavaScriptParser.PreIncrementExpressionContext ctx) {
            return "++"+visit(ctx.singleExpression());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitObjectLiteralExpression(JavaScriptParser.ObjectLiteralExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMetaExpression(JavaScriptParser.MetaExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitInExpression(JavaScriptParser.InExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitLogicalOrExpression(JavaScriptParser.LogicalOrExpressionContext ctx) {
            // exprBitAnd :  expression '||' expression
            return visit(ctx.singleExpression(0)) + " || " + visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitOptionalChainExpression(JavaScriptParser.OptionalChainExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitNotExpression(JavaScriptParser.NotExpressionContext ctx) {
            return "!"+visit(ctx.singleExpression());
        }

        @Override public String visitPreDecreaseExpression(JavaScriptParser.PreDecreaseExpressionContext ctx) {
            return "--"+visit(ctx.singleExpression());
        }

        @Override public String visitArgumentsExpression(JavaScriptParser.ArgumentsExpressionContext ctx) {
            // arguments :  '(' expressionList? ')'
            if (ctx.arguments() == null)
                return "()";
            return "(" + visit(ctx.arguments()) + ")";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAwaitExpression(JavaScriptParser.AwaitExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitThisExpression(JavaScriptParser.ThisExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitUnaryMinusExpression(JavaScriptParser.UnaryMinusExpressionContext ctx) {
            return "- "+visit(ctx.singleExpression());
        }

        @Override public String visitAssignmentExpression(JavaScriptParser.AssignmentExpressionContext ctx) {
            // exprAssignment :  expression  ( '='  | '+='  | '-='   | '*='  | '/=' | '&=' |
            //                                 '|=' | '^=' | '>>=' | '>>>=' | '<<=' | '%=' )  expression
            return visit(ctx.singleExpression(0)) + " ?= " + visit(ctx.singleExpression(1));
        }

        @Override public String visitPostDecreaseExpression(JavaScriptParser.PostDecreaseExpressionContext ctx) {
            return visit(ctx.singleExpression())+"--";
        }

        @Override public String visitTypeofExpression(JavaScriptParser.TypeofExpressionContext ctx) {
            // exprTypeOf: typeof(expression)
            return visit(ctx.Typeof())+"("+visit(ctx.singleExpression())+")";
        }

        @Override public String visitInstanceofExpression(JavaScriptParser.InstanceofExpressionContext ctx) {
            // exprInstanceOf :  expression 'instanceof' typeType
            return visit(ctx.singleExpression(0)) + " instanceof " + getOriginalCodeText(ctx.singleExpression(1));
        }


        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitUnaryPlusExpression(JavaScriptParser.UnaryPlusExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitDeleteExpression(JavaScriptParser.DeleteExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitImportExpression(JavaScriptParser.ImportExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitEqualityExpression(JavaScriptParser.EqualityExpressionContext ctx) {
            // exprEquality :  expression ('==' | '!=') expression
            String sub = ctx.getText().substring(ctx.singleExpression(0).getText().length());
            String op;
            if (sub.startsWith("=="))
                op = " == ";
            else
                op = " != ";
            return visit(ctx.singleExpression(0)) + op + visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBitXOrExpression(JavaScriptParser.BitXOrExpressionContext ctx) {
            // exprBitAnd :  expression '^' expression
            return visit(ctx.singleExpression(0)) + " ^ " + visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSuperExpression(JavaScriptParser.SuperExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitMultiplicativeExpression(JavaScriptParser.MultiplicativeExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+" * "+visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBitShiftExpression(JavaScriptParser.BitShiftExpressionContext ctx) {
            // exprBitShift :  expression ('<' '<' | '>' '>' '>' | '>' '>') expression
            String sub = ctx.getText().substring(ctx.singleExpression(0).getText().length());
            String op;
            if (sub.startsWith(">>>"))
                op = ">>>";
            else
                op = sub.substring(0, 2);
            return visit(ctx.singleExpression(0)) + " " + op + " " + visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitParenthesizedExpression(JavaScriptParser.ParenthesizedExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitAdditiveExpression(JavaScriptParser.AdditiveExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+" + "+visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitRelationalExpression(JavaScriptParser.RelationalExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitPostIncrementExpression(JavaScriptParser.PostIncrementExpressionContext ctx) {
            return visit(ctx.singleExpression())+"++";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitYieldExpression(JavaScriptParser.YieldExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitBitNotExpression(JavaScriptParser.BitNotExpressionContext ctx) {
            return "~"+visit(ctx.singleExpression());
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitNewExpression(JavaScriptParser.NewExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLiteralExpression(JavaScriptParser.LiteralExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArrayLiteralExpression(JavaScriptParser.ArrayLiteralExpressionContext ctx) { return visitChildren(ctx); }

        //TODO 待确认
        @Override public String visitMemberDotExpression(JavaScriptParser.MemberDotExpressionContext ctx) {
            return visit(ctx.singleExpression())+".";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassExpression(JavaScriptParser.ClassExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMemberIndexExpression(JavaScriptParser.MemberIndexExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitIdentifierExpression(JavaScriptParser.IdentifierExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitBitAndExpression(JavaScriptParser.BitAndExpressionContext ctx) {
            // exprBitAnd :  expression '&' expression
            return visit(ctx.singleExpression(0)) + " & " + visit(ctx.singleExpression(1));
        }

        @Override public String visitBitOrExpression(JavaScriptParser.BitOrExpressionContext ctx) {
            // exprBitAnd :  expression '|' expression
            return visit(ctx.singleExpression(0)) + " | " + visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAssignmentOperatorExpression(JavaScriptParser.AssignmentOperatorExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitVoidExpression(JavaScriptParser.VoidExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCoalesceExpression(JavaScriptParser.CoalesceExpressionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAssignable(JavaScriptParser.AssignableContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitObjectLiteral(JavaScriptParser.ObjectLiteralContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAnonymousFunctionDecl(JavaScriptParser.AnonymousFunctionDeclContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArrowFunctionParameters(JavaScriptParser.ArrowFunctionParametersContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArrowFunctionBody(JavaScriptParser.ArrowFunctionBodyContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAssignmentOperator(JavaScriptParser.AssignmentOperatorContext ctx) {

            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLiteral(JavaScriptParser.LiteralContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateStringLiteral(JavaScriptParser.TemplateStringLiteralContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitTemplateStringAtom(JavaScriptParser.TemplateStringAtomContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String  visitNumericLiteral(JavaScriptParser.NumericLiteralContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitBigintLiteral(JavaScriptParser.BigintLiteralContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitGetter(JavaScriptParser.GetterContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitSetter(JavaScriptParser.SetterContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitIdentifierName(JavaScriptParser.IdentifierNameContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitIdentifier(JavaScriptParser.IdentifierContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitReservedWord(JavaScriptParser.ReservedWordContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitKeyword(JavaScriptParser.KeywordContext ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitLet_(JavaScriptParser.Let_Context ctx) { return visitChildren(ctx); }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitEos(JavaScriptParser.EosContext ctx) { return visitChildren(ctx); }

        private String getOriginalCodeText(ParserRuleContext ctx) {
            if (ctx == null)
                return "";
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
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
    }
}
