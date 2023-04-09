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
import java.util.*;

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

        @Override public String visitEmptyStatement_(JavaScriptParser.EmptyStatement_Context ctx) {
            ASNode emptyNode=new ASNode(ASNode.Type.EMPTY);
            emptyNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(emptyNode);
            AST.addEdge(parentStack.peek(),emptyNode);
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) {
            return visitChildren(ctx);
        }

        @Override public String visitIfStatement(JavaScriptParser.IfStatementContext ctx) {
            //     : If '(' expressionSequence ')' statement (Else statement)?
            ASNode ifNode = new ASNode(ASNode.Type.IF);
            ifNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(ifNode);
            AST.addEdge(parentStack.peek(), ifNode);
            //
            ASNode cond = new ASNode(ASNode.Type.CONDITION);
            cond.setCode(getOriginalCodeText(ctx.expressionSequence()));
            cond.setNormalizedCode(visit(ctx.expressionSequence()));
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

        @Override public String visitDoStatement(JavaScriptParser.DoStatementContext ctx) {
            //     : Do statement While '(' expressionSequence ')' eos                                                                       # DoStatement
            ASNode doWhileNode = new ASNode(ASNode.Type.DO_WHILE);
            doWhileNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(doWhileNode);
            AST.addEdge(parentStack.peek(), doWhileNode);
            //
            ASNode cond = new ASNode(ASNode.Type.CONDITION);
            cond.setCode(getOriginalCodeText(ctx.expressionSequence()));
            cond.setNormalizedCode(visit(ctx.expressionSequence()));
            cond.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            AST.addVertex(cond);
            AST.addEdge(doWhileNode, cond);
            //
            ASNode block = new ASNode(ASNode.Type.BLOCK);
            block.setLineOfCode(ctx.statement().getStart().getLine());
            AST.addVertex(block);
            AST.addEdge(doWhileNode, block);
            parentStack.push(block);
            visit(ctx.statement());
            parentStack.pop();
            return "";
        }

        @Override public String visitWhileStatement(JavaScriptParser.WhileStatementContext ctx) {
            //     | While '(' expressionSequence ')' statement                                                                              # WhileStatement
            ASNode whileNode = new ASNode(ASNode.Type.WHILE);
            whileNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(whileNode);
            AST.addEdge(parentStack.peek(), whileNode);

            //while条件
            ASNode cond=new ASNode(ASNode.Type.CONDITION);
            cond.setCode(getOriginalCodeText(ctx.expressionSequence()));
            cond.setNormalizedCode(visit(ctx.expressionSequence()));
            cond.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            AST.addVertex(cond);
            AST.addEdge(whileNode,cond);

            //
            ASNode block = new ASNode(ASNode.Type.BLOCK);
            block.setLineOfCode(ctx.statement().getStart().getLine());
            AST.addVertex(block);
            AST.addEdge(whileNode, block);
            parentStack.push(block);
            visit(ctx.statement());
            parentStack.pop();
            return "";
        }

        @Override public String visitForStatement(JavaScriptParser.ForStatementContext ctx) {
           //ForStatement    | For '(' (expressionSequence | variableDeclarationList)? ';' expressionSequence? ';' expressionSequence? ')' statement   # ForStatement
            //expressionSequence: singleExpression (',' singleExpression)*
            //variableDeclarationList: varModifier variableDeclaration (',' variableDeclaration)*
            //varModifier: Var| let_ | Const
            //variableDeclaration: assignable ('=' singleExpression)?
            //assignable: identifier| arrayLiteral| objectLiteral
            ASNode forNode;
            forNode=new ASNode(ASNode.Type.FOR);
            forNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(forNode);
            AST.addEdge(parentStack.peek(),forNode);
            //for初始化
            ASNode forInit=new ASNode(ASNode.Type.FOR_INIT);
            AST.addVertex(forInit);
            AST.addEdge(forNode,forInit);

            //ForStatement    | For '(' (expressionSequence | variableDeclarationList)? ';' expressionSequence? ';' expressionSequence? ')' statement   # ForStatement
            //当expressionSequence长度为3时，说明for结构如下：
            //ForStatement    | For '(' (expressionSequence ？ ';' expressionSequence? ';' expressionSequence? ')' statement   # ForStatement
            //当expressionSequence长度为2时，说明for结构如下：
            //ForStatement    | For '('  variableDeclarationList? ';' expressionSequence? ';' expressionSequence? ')' statement   # ForStatement
            if (ctx.expressionSequence().size()==3){
                //for Init
                ASNode exprInit=new ASNode(ASNode.Type.STATEMENT);
                exprInit.setCode(getOriginalCodeText(ctx.expressionSequence().get(0)));
                exprInit.setNormalizedCode(visit(ctx.expressionSequence().get(0)));
                exprInit.setLineOfCode(ctx.expressionSequence().get(0).getStart().getLine());
                AST.addVertex(exprInit);
                AST.addEdge(forInit,exprInit);
                AST.addEdge(forNode,forInit);

                //for 判断
                ASNode expr=new ASNode(ASNode.Type.STATEMENT);
                expr.setCode(getOriginalCodeText(ctx.expressionSequence().get(1)));
                expr.setNormalizedCode(visit(ctx.expressionSequence().get(1)));
                expr.setLineOfCode(ctx.expressionSequence().get(1).getStart().getLine());
                AST.addVertex(expr);
                AST.addEdge(forNode,expr);

                //for Update
                ASNode exprUpdate=new ASNode(ASNode.Type.STATEMENT);
                exprUpdate.setCode(getOriginalCodeText(ctx.expressionSequence().get(2)));
                exprUpdate.setNormalizedCode(visit(ctx.expressionSequence().get(2)));
                exprUpdate.setLineOfCode(ctx.expressionSequence().get(2).getStart().getLine());
                AST.addVertex(exprUpdate);
                AST.addEdge(forNode,exprUpdate);

            }else{
               //forInit
                ASNode exprInit=new ASNode(ASNode.Type.STATEMENT);
                exprInit.setCode(getOriginalCodeText(ctx.variableDeclarationList()));
                exprInit.setNormalizedCode(visit(ctx.variableDeclarationList()));
                exprInit.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                AST.addVertex(exprInit);
                AST.addEdge(forInit,exprInit);
                AST.addEdge(forNode,forInit);

                //for 判断
                ASNode expr=new ASNode(ASNode.Type.STATEMENT);
                expr.setCode(getOriginalCodeText(ctx.expressionSequence().get(0)));
                expr.setNormalizedCode(visit(ctx.expressionSequence().get(0)));
                expr.setLineOfCode(ctx.expressionSequence().get(0).getStart().getLine());
                AST.addVertex(expr);
                AST.addEdge(forNode,expr);

                //for Update
                ASNode exprUpdate=new ASNode(ASNode.Type.STATEMENT);
                exprUpdate.setCode(getOriginalCodeText(ctx.expressionSequence().get(1)));
                exprUpdate.setNormalizedCode(visit(ctx.expressionSequence().get(1)));
                exprUpdate.setLineOfCode(ctx.expressionSequence().get(1).getStart().getLine());
                AST.addVertex(exprUpdate);
                AST.addEdge(forNode,exprUpdate);
            }

            //
            ASNode block = new ASNode(ASNode.Type.BLOCK);
            block.setLineOfCode(ctx.statement().getStart().getLine());
            AST.addVertex(block);
            AST.addEdge(forNode, block);
            parentStack.push(block);
            visit(ctx.statement());
            parentStack.pop();
            return "";
        }

        @Override public String visitForInStatement(JavaScriptParser.ForInStatementContext ctx) {
            //    | For '(' (singleExpression | variableDeclarationList) In expressionSequence ')' statement                                # ForInStatement
            //
            ASNode forNode=new ASNode(ASNode.Type.FOR_EACH);
            if (ctx.singleExpression()!=null){
                ASNode expr=new ASNode(ASNode.Type.STATEMENT);
                expr.setCode(getOriginalCodeText(ctx.singleExpression()));
                expr.setNormalizedCode(visit(ctx.singleExpression()));
                expr.setLineOfCode(ctx.singleExpression().getStart().getLine());
                AST.addVertex(expr);
                AST.addEdge(forNode,expr);
            }else{
                ASNode expr=new ASNode(ASNode.Type.STATEMENT);
                expr.setCode(getOriginalCodeText(ctx.variableDeclarationList()));
                expr.setNormalizedCode(visit(ctx.variableDeclarationList()));
                expr.setLineOfCode(ctx.variableDeclarationList().getStart().getLine());
                AST.addVertex(expr);
                AST.addEdge(forNode,expr);
            }

            ASNode expr=new ASNode(ASNode.Type.STATEMENT);
            expr.setCode(getOriginalCodeText(ctx.expressionSequence()));
            expr.setNormalizedCode(visit(ctx.expressionSequence()));
            expr.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            AST.addVertex(expr);
            AST.addEdge(forNode,expr);

            //
            ASNode block = new ASNode(ASNode.Type.BLOCK);
            block.setLineOfCode(ctx.statement().getStart().getLine());
            AST.addVertex(block);
            AST.addEdge(forNode, block);
            parentStack.push(block);
            visit(ctx.statement());
            parentStack.pop();
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitForOfStatement(JavaScriptParser.ForOfStatementContext ctx) {
            //    | For Await? '(' (singleExpression | variableDeclarationList) identifier{this.p("of")}? expressionSequence ')' statement  # ForOfStatement
            //TODO
            return visitChildren(ctx);
        }
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
        @Override public String visitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) {
            //    : Continue ({this.notLineTerminator()}? identifier)? eos
            if (ctx.Continue()==null){
                visit(ctx.eos());
                return "";
            }
            visitStatement(ctx.identifier(),"continue $LABEL");
            return "";
        }

        @Override public String visitBreakStatement(JavaScriptParser.BreakStatementContext ctx) {
            //:    : Break ({this.notLineTerminator()}? identifier)? eos
            if (ctx.Break()==null){
                visit(ctx.eos());
                return "";
            }
            visitStatement(ctx.identifier(),"continue $LABEL");
            return "";
        }

        @Override public String visitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) {
            //:        : Return ({this.notLineTerminator()}? expressionSequence)? eos
            if (ctx.Return()==null){
                visit(ctx.eos());
                return "";
            }

            visitStatement(ctx,"return "+visit(ctx.expressionSequence()));
            return "";
        }

        @Override public String visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
            //:         : Yield ({this.notLineTerminator()}? expressionSequence)? eos
            if (ctx.Yield()==null){
                visit(ctx.eos());
                return "";
            }

            visitStatement(ctx,"yield "+visit(ctx.expressionSequence()));
            return "";
        }

        @Override public String visitWithStatement(JavaScriptParser.WithStatementContext ctx) {
            //:      : With '(' expressionSequence ')' statement
            ASNode withNode=new ASNode(ASNode.Type.WITH);
            withNode.setCode(getOriginalCodeText(ctx.expressionSequence()));
            withNode.setNormalizedCode(visit(ctx.expressionSequence()));
            withNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            AST.addVertex(withNode);
            ASNode exprNode=new ASNode(ASNode.Type.STATEMENT);
            exprNode.setLineOfCode(ctx.statement().getStart().getLine());
            exprNode.setCode(ctx.statement().getText());
            AST.addVertex(exprNode);
            AST.addEdge(withNode,exprNode);
            return "";
        }

        @Override public String visitSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) {
            //    : Switch '(' expressionSequence ')' caseBlock
//            ASNode switchNode=new ASNode(ASNode.Type.SWITCH);
//            switchNode.setCode(getOriginalCodeText(ctx.expressionSequence()));
//            switchNode.setNormalizedCode(visit(ctx.expressionSequence()));
//            switchNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
//            AST.addVertex(switchNode);
//            ASNode caseBlockNode=new ASNode(ASNode.Type.CASEBLOCK);
//            caseBlockNode.setLineOfCode(ctx.caseBlock().getStart().getLine());
//            caseBlockNode.setCode(ctx.caseBlock().getText());
//            AST.addVertex(caseBlockNode);
//            AST.addEdge(switchNode,caseBlockNode);
//            return "";
            ASNode switchNode = new ASNode(ASNode.Type.SWITCH);
            switchNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(switchNode);
            AST.addEdge(parentStack.peek(), switchNode);
            //
            ASNode statementNode=new ASNode(ASNode.Type.STATEMENT);
            statementNode.setCode(getOriginalCodeText(ctx.expressionSequence()));
            statementNode.setNormalizedCode(visit(ctx.expressionSequence().singleExpression(0)));
            statementNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            AST.addVertex(statementNode);
            AST.addEdge(switchNode, statementNode);
            if (ctx.caseBlock().caseClauses()!=null){
                ASNode caseBlockNode=new ASNode(ASNode.Type.CASEBLOCK);
                caseBlockNode.setCode(getOriginalCodeText(ctx.caseBlock()));
                caseBlockNode.setNormalizedCode(visit(ctx.caseBlock()));
                caseBlockNode.setLineOfCode(ctx.caseBlock().getStart().getLine());
                AST.addVertex(caseBlockNode);
                AST.addEdge(switchNode, caseBlockNode);
            }
            return "";
        }

        @Override public String visitCaseBlock(JavaScriptParser.CaseBlockContext ctx) {
            //    : '{' caseClauses? (defaultClause caseClauses?)? '}'
            ASNode caseBlockNode=new ASNode(ASNode.Type.CASEBLOCK);
            caseBlockNode.setLineOfCode(ctx.getStart().getLine());
            caseBlockNode.setCode(ctx.getText());
            AST.addVertex(caseBlockNode);
            if (ctx.caseClauses()==null){
                parentStack.push(caseBlockNode);
                visitDefaultClause(ctx.defaultClause());
                parentStack.pop();
            }else{
                parentStack.push(caseBlockNode);
                for (JavaScriptParser.CaseClausesContext caseClause : ctx.caseClauses()) {
                    visitCaseClauses(caseClause);
                }
                parentStack.pop();
            }
            return "";
        }


        @Override public String visitCaseClauses(JavaScriptParser.CaseClausesContext ctx) {
            //caseClauses
            //    : caseClause+
            ASNode caseClausesNode=new ASNode(ASNode.Type.CASECLAUSES);
            caseClausesNode.setLineOfCode(ctx.getStart().getLine());
            caseClausesNode.setCode(ctx.getText());
            AST.addVertex(caseClausesNode);
            AST.addEdge(parentStack.peek(),caseClausesNode);
            parentStack.push(caseClausesNode);
            for (JavaScriptParser.CaseClauseContext caseContext : ctx.caseClause()) {
                visitCaseClause(caseContext);
            }
            parentStack.pop();
            return "";
        }

        private void visitCaseClause(ASNode caseClausesNode, List<JavaScriptParser.CaseClauseContext> caseClauses) {
            for (JavaScriptParser.CaseClauseContext caseClause : caseClauses) {
                ASNode caseNode=new ASNode(ASNode.Type.CASE);
                caseNode.setLineOfCode(caseClause.getStart().getLine());
                caseNode.setCode(caseClause.getText());
                AST.addVertex(caseNode);
                AST.addEdge(caseClausesNode,caseNode);
            }
        }

        @Override public String visitCaseClause(JavaScriptParser.CaseClauseContext ctx) {
            // : Case expressionSequence ':' statementList?
            ASNode caseNode=new ASNode(ASNode.Type.CASE);
            caseNode.setLineOfCode(ctx.getStart().getLine());
            caseNode.setCode(ctx.getText());
            AST.addVertex(caseNode);
            AST.addEdge(parentStack.peek(),caseNode);
            ASNode exprNode=new ASNode(ASNode.Type.STATEMENT);
            exprNode.setCode(ctx.expressionSequence().getText());
            exprNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            exprNode.setNormalizedCode(visit(ctx.expressionSequence()));
            AST.addVertex(exprNode);
            AST.addEdge(caseNode,exprNode);
            ASNode stateNode=new ASNode(ASNode.Type.STATEMENT);
            stateNode.setCode(ctx.statementList().getText());
            stateNode.setLineOfCode(ctx.statementList().getStart().getLine());
            stateNode.setNormalizedCode(visit(ctx.statementList()));
            AST.addVertex(stateNode);
            AST.addEdge(caseNode,stateNode);
            return "";
        }

        @Override public String visitDefaultClause(JavaScriptParser.DefaultClauseContext ctx) {
            if (ctx.Default()!=null){
                ASNode caseNode=new ASNode(ASNode.Type.CASE);
                caseNode.setLineOfCode(ctx.getStart().getLine());
                caseNode.setCode(ctx.getText());
                AST.addVertex(caseNode);
                AST.addEdge(parentStack.peek(),caseNode);
                return "Default : "+visit(ctx.statementList());
            }
            return "";
        }


        @Override public String visitLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) {
            //    : identifier ':' statement
            return ctx.identifier().Identifier().getText()+":"+visit(ctx.statement());
        }

        @Override public String visitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) {
            //:      : Throw {this.notLineTerminator()}? expressionSequence eos
            if (ctx.Throw()==null){
                visit(ctx.eos());
                return "";
            }
            visitStatement(ctx,"throw "+visit(ctx.expressionSequence()));
            return "";
        }

        @Override public String visitTryStatement(JavaScriptParser.TryStatementContext ctx) {
            //  : Try block (catchProduction finallyProduction? | finallyProduction)
            ASNode tryNode = new ASNode(ASNode.Type.TRY);
            tryNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(tryNode);
            AST.addEdge(parentStack.peek(), tryNode);
            //
            ASNode tryBlock = new ASNode(ASNode.Type.BLOCK);
            tryBlock.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(tryBlock);
            AST.addEdge(tryNode, tryBlock);
            parentStack.push(tryBlock);
            visit(ctx.block());
            parentStack.pop();

            // catchProduction : : Catch ('(' assignable? ')')? block
            if(ctx.catchProduction()!=null){
                ASNode catchNode=new ASNode(ASNode.Type.CATCH);
                AST.addVertex(catchNode);
                AST.addEdge(tryNode, catchNode);
                ++varsCounter;
                ASNode catchName = new ASNode(ASNode.Type.NAME);
                String normalized = "$VARL_" + varsCounter;
                vars.put(ctx.catchProduction().getText(), normalized);
                catchName.setCode(ctx.catchProduction().getText());
                catchName.setNormalizedCode(normalized);
                catchName.setLineOfCode(ctx.catchProduction().getStart().getLine());
                AST.addVertex(catchName);
                AST.addEdge(catchNode, catchName);
                //
                ASNode catchBlock = new ASNode(ASNode.Type.BLOCK);
                catchBlock.setLineOfCode(ctx.catchProduction().block().getStart().getLine());
                AST.addVertex(catchBlock);
                AST.addEdge(catchNode, catchBlock);
                parentStack.push(catchBlock);
                visit(ctx.catchProduction().block());
                parentStack.pop();
            }
            // finallyBlock :  'finally' block
            if (ctx.finallyProduction() != null) {
                ASNode finallyNode = new ASNode(ASNode.Type.FINALLY);
                finallyNode.setLineOfCode(ctx.finallyProduction().getStart().getLine());
                AST.addVertex(finallyNode);
                AST.addEdge(tryNode, finallyNode);
                parentStack.push(finallyNode);
                visit(ctx.finallyProduction().block());
                parentStack.pop();
            }
            return "";
        }

        @Override public String visitCatchProduction(JavaScriptParser.CatchProductionContext ctx) {
           //: Catch ('(' assignable? ')')? block
            ASNode catchNode=new ASNode(ASNode.Type.CATCH);
            catchNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(catchNode);
            if(ctx.assignable()!=null){
                ASNode expr=new ASNode(ASNode.Type.STATEMENT);
                expr.setLineOfCode(ctx.assignable().getStart().getLine());
                AST.addVertex(expr);
                AST.addEdge(catchNode,expr);
            }
            ASNode blockNode=new ASNode(ASNode.Type.BLOCK);
            blockNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(blockNode);
            AST.addEdge(catchNode, blockNode);
            parentStack.push(catchNode);
            return visitChildren(ctx);
        }

        @Override public String visitFinallyProduction(JavaScriptParser.FinallyProductionContext ctx) {
            //: : Finally block
            ASNode finalNode=new ASNode(ASNode.Type.FINALLY);
            finalNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(finalNode);
            ASNode blockNode=new ASNode(ASNode.Type.BLOCK);
            blockNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(blockNode);
            AST.addEdge(finalNode, blockNode);
            parentStack.push(finalNode);
            return visitChildren(ctx);
        }

        @Override public String visitDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) {
            //    : Debugger eos
            if (ctx.Debugger()!=null){
                visit(ctx.eos());
            }
            return "";
        }

        @Override public String visitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
            //      : Async? Function_ '*'? identifier '(' formalParameterList? ')' functionBody
            //TODO
            StringBuffer stringBuffer=new StringBuffer();
            if(ctx.Async()!=null){
                stringBuffer.append("Async ");
            }
            if (ctx.Function_()!=null){
                stringBuffer.append("Function_ *");
            }
            ASNode modifierNode = new ASNode(ASNode.Type.MODIFIER);
            modifierNode.setCode(memberModifier);
            modifierNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding method modifier");
            AST.addVertex(modifierNode);
            AST.addEdge(parentStack.peek(), modifierNode);
            //
            ASNode retNode = new ASNode(ASNode.Type.RETURN);
            retNode.setCode(ctx.getChild(0).getText());
            retNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding method type");
            AST.addVertex(retNode);
            AST.addEdge(parentStack.peek(), retNode);
            //
            ++methodsCounter;
            ASNode nameNode = new ASNode(ASNode.Type.NAME);
            String methodName = ctx.identifier().Identifier().getText();
            String normalized = "$METHOD_" + methodsCounter;
            methods.put(methodName, normalized);
            nameNode.setCode(methodName);
            nameNode.setNormalizedCode(normalized);
            nameNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding method name");
            AST.addVertex(nameNode);
            AST.addEdge(parentStack.peek(), nameNode);
            //
            if (ctx.formalParameterList() != null) {
                ASNode paramsNode = new ASNode(ASNode.Type.PARAMS);
                paramsNode.setLineOfCode(ctx.formalParameterList().getStart().getLine());
                Logger.debug("Adding method params node");
                AST.addVertex(paramsNode);
                AST.addEdge(parentStack.peek(), paramsNode);
                parentStack.push(paramsNode);
                for (JavaScriptParser.FormalParameterArgContext paramctx : ctx.formalParameterList().formalParameterArg()) {
                    ASNode varNode = new ASNode(ASNode.Type.VARIABLE);
                    varNode.setLineOfCode(paramctx.getStart().getLine());
                    AST.addVertex(varNode);
                    AST.addEdge(parentStack.peek(), varNode);
                    //
                    ++varsCounter;
                    ASNode name = new ASNode(ASNode.Type.NAME);
                    normalized = "$VARL_" + varsCounter;
                    vars.put(paramctx.assignable().getText(), normalized);
                    name.setCode(paramctx.assignable().getText());
                    name.setNormalizedCode(normalized);
                    name.setLineOfCode(paramctx.assignable().getStart().getLine());
                    AST.addVertex(name);
                    AST.addEdge(varNode, name);
                }
                if (ctx.formalParameterList().lastFormalParameterArg() != null) {
                    ASNode varNode = new ASNode(ASNode.Type.VARIABLE);
                    varNode.setLineOfCode(ctx.formalParameterList().lastFormalParameterArg().getStart().getLine());
                    AST.addVertex(varNode);
                    AST.addEdge(parentStack.peek(), varNode);
                    //
                    ++varsCounter;
                    ASNode name = new ASNode(ASNode.Type.NAME);
                    normalized = "$VARL_" + varsCounter;
                    vars.put(ctx.formalParameterList().lastFormalParameterArg().getText(), normalized);
                    name.setCode(ctx.formalParameterList().lastFormalParameterArg().getText());
                    name.setNormalizedCode(normalized);
                    name.setLineOfCode(ctx.formalParameterList().lastFormalParameterArg().getStart().getLine());
                    AST.addVertex(name);
                    AST.addEdge(varNode, name);
                }
                parentStack.pop();
            }
            //
            if (ctx.functionBody() != null) {
                ASNode functionBody = new ASNode(ASNode.Type.BLOCK);
                functionBody.setLineOfCode(ctx.functionBody().getStart().getLine());
                Logger.debug("Adding function block");
                AST.addVertex(functionBody);
                AST.addEdge(parentStack.peek(), functionBody);
                parentStack.push(functionBody);
                visitChildren(ctx.functionBody());
                parentStack.pop();
                resetLocalVars();
            }
            return "";
        }

        private void resetLocalVars() {
            vars.clear();
            varsCounter = 0;
        }

        @Override public String visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
            //    : Class identifier classTail
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

        @Override public String visitClassTail(JavaScriptParser.ClassTailContext ctx) {
           //: (Extends singleExpression)? '{' classElement* '}'
            if(ctx.Extends()!=null){
                ASNode extendsNode=new ASNode(ASNode.Type.EXTENDS);
                extendsNode.setCode(ctx.singleExpression().getText());
                extendsNode.setLineOfCode(ctx.singleExpression().getStart().getLine());
                Logger.debug("Adding extends " + ctx.singleExpression().getText());
                AST.addVertex(extendsNode);
                for (JavaScriptParser.ClassElementContext classElement : ctx.classElement()) {
                    ASNode classNode=new ASNode(ASNode.Type.CLASS);
                    extendsNode.setCode(classElement.getText());
                    extendsNode.setLineOfCode(classElement.getStart().getLine());
                    Logger.debug("Adding class name: " + classElement.getText());
                    AST.addVertex(classNode);
                    AST.addEdge(extendsNode,classNode);
                }
            }
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitClassElement(JavaScriptParser.ClassElementContext ctx) {
           //    : (Static | {this.n("static")}? identifier | Async)* (methodDefinition | assignable '=' objectLiteral ';')
            //    | emptyStatement_
            //    | '#'? propertyName '=' singleExpression
            //TODO
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {
           //TODO
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) {
           //TODO
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) {
            //TODO
            return visitChildren(ctx);
        }

        @Override public String visitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) {
            return ctx.Ellipsis().getText()+" "+visit(ctx.singleExpression());
        }


        @Override public String visitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) {
            return "{"+visitSourceElements(ctx.sourceElements())+"}";
        }

        @Override public String visitSourceElements(JavaScriptParser.SourceElementsContext ctx) {
            StringBuffer stringBuffer=new StringBuffer();
            if (ctx.sourceElement()!=null&&ctx.sourceElement().size()>0){
                for (JavaScriptParser.SourceElementContext sourceContext : ctx.sourceElement()) {
                    stringBuffer.append(visitSourceElement(sourceContext)+" ");
                }
            }
            return stringBuffer.toString();
        }

        @Override public String visitArrayLiteral(JavaScriptParser.ArrayLiteralContext ctx) {
            return "["+visitElementList(ctx.elementList())+"]";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitElementList(JavaScriptParser.ElementListContext ctx) {
           //TODO
            return visitChildren(ctx);
        }

        @Override public String visitArrayElement(JavaScriptParser.ArrayElementContext ctx) {
            if (ctx.Ellipsis()!=null){
                return ctx.Ellipsis().getText()+" "+visit(ctx.singleExpression());
            }
            return visit(ctx.singleExpression());
        }

        @Override public String visitPropertyExpressionAssignment(JavaScriptParser.PropertyExpressionAssignmentContext ctx) {
            return visitPropertyName(ctx.propertyName())+":"+visit(ctx.singleExpression());
        }

        @Override public String visitComputedPropertyExpressionAssignment(JavaScriptParser.ComputedPropertyExpressionAssignmentContext ctx) {
            return "["+visit(ctx.singleExpression(0))+"]:"+visit(ctx.singleExpression(1));
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionProperty(JavaScriptParser.FunctionPropertyContext ctx) {
            //TODO
            StringBuffer stringBuffer=new StringBuffer();
            if (ctx.Async()!=null){
                stringBuffer.append(ctx.Async());
            }
            stringBuffer.append("*");

            return visitChildren(ctx);
        }

        @Override public String visitPropertyGetter(JavaScriptParser.PropertyGetterContext ctx) {
            return visitGetter(ctx.getter())+"()"+visitFunctionBody(ctx.functionBody());
        }

        @Override public String visitPropertySetter(JavaScriptParser.PropertySetterContext ctx) {
            return  visitSetter(ctx.setter())+"("+visitFormalParameterArg(ctx.formalParameterArg())+")"+visitFunctionBody(ctx.functionBody());
        }

        @Override public String visitPropertyShorthand(JavaScriptParser.PropertyShorthandContext ctx) {
            if(ctx.Ellipsis()!=null){
                return ctx.Ellipsis().getText()+" "+visit(ctx.singleExpression());
            }
            return visit(ctx.singleExpression());
        }

        @Override public String visitPropertyName(JavaScriptParser.PropertyNameContext ctx) {
            if(ctx.identifierName()!=null){
                return visitIdentifierName(ctx.identifierName());
            }else if(ctx.numericLiteral()!=null){
                return visitNumericLiteral(ctx.numericLiteral());
            }else if(ctx.StringLiteral()!=null){
                return ctx.StringLiteral().toString();
            }
            return "["+visit(ctx.singleExpression())+"]";
        }

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

        @Override public String visitArgument(JavaScriptParser.ArgumentContext ctx) {
            StringBuffer stringBuffer=new StringBuffer();
            if(ctx.Ellipsis()!=null){
                stringBuffer.append(ctx.Ellipsis().getText()+" ");
            }
            if(ctx.singleExpression()!=null){
                stringBuffer.append(visit(ctx.singleExpression()));
            }else{
                stringBuffer.append(visitIdentifier(ctx.identifier()));
            }
            return stringBuffer.toString();
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitExpressionSequence(JavaScriptParser.ExpressionSequenceContext ctx) {
            //TODO
            return visitChildren(ctx);
        }

        @Override public String visitTemplateStringExpression(JavaScriptParser.TemplateStringExpressionContext ctx) {
            return visit(ctx.singleExpression())+" "+visitTemplateStringLiteral(ctx.templateStringLiteral());
        }

        @Override public String visitTernaryExpression(JavaScriptParser.TernaryExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+"?"+visit(ctx.singleExpression(1))+":"+visit(ctx.singleExpression(2));
        }

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

        @Override public String visitObjectLiteralExpression(JavaScriptParser.ObjectLiteralExpressionContext ctx) {
            return visitObjectLiteral(ctx.objectLiteral());
        }

        @Override public String visitMetaExpression(JavaScriptParser.MetaExpressionContext ctx) {
            return ctx.New().getText()+"."+visitIdentifier(ctx.identifier());
        }

        @Override public String visitInExpression(JavaScriptParser.InExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+" "+ctx.In().getText()+" "+visit(ctx.singleExpression(1));
        }

        @Override public String visitLogicalOrExpression(JavaScriptParser.LogicalOrExpressionContext ctx) {
            // exprBitAnd :  expression '||' expression
            return visit(ctx.singleExpression(0)) + " || " + visit(ctx.singleExpression(1));
        }

        @Override public String visitOptionalChainExpression(JavaScriptParser.OptionalChainExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+"?."+visit(ctx.singleExpression(1));
        }

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

        @Override public String visitAwaitExpression(JavaScriptParser.AwaitExpressionContext ctx) {
            return ctx.Await().getText()+" "+visit(ctx.singleExpression());
        }

        @Override public String visitThisExpression(JavaScriptParser.ThisExpressionContext ctx) {
            return ctx.This().getText();
        }

        @Override public String visitFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) {
            return ctx.anonymousFunction().getText();
        }

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


        @Override public String visitUnaryPlusExpression(JavaScriptParser.UnaryPlusExpressionContext ctx) {
            return "+"+visit(ctx.singleExpression());
        }

        @Override public String visitDeleteExpression(JavaScriptParser.DeleteExpressionContext ctx) {
            return ctx.Delete().getText()+" "+visit(ctx.singleExpression());
        }

        @Override public String visitImportExpression(JavaScriptParser.ImportExpressionContext ctx) {
            return "("+visit(ctx.singleExpression())+")";
        }

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

        @Override public String visitBitXOrExpression(JavaScriptParser.BitXOrExpressionContext ctx) {
            // exprBitAnd :  expression '^' expression
            return visit(ctx.singleExpression(0)) + " ^ " + visit(ctx.singleExpression(1));
        }

        @Override public String visitSuperExpression(JavaScriptParser.SuperExpressionContext ctx) {
            return ctx.Super().getText();
        }

        @Override public String visitMultiplicativeExpression(JavaScriptParser.MultiplicativeExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+" * "+visit(ctx.singleExpression(1));
        }

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

        @Override public String visitParenthesizedExpression(JavaScriptParser.ParenthesizedExpressionContext ctx) {
            return "(" + visitExpressionSequence(ctx.expressionSequence()) + ")";
        }

        @Override public String visitAdditiveExpression(JavaScriptParser.AdditiveExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+" + "+visit(ctx.singleExpression(1));
        }

        @Override public String visitRelationalExpression(JavaScriptParser.RelationalExpressionContext ctx) {
            return visit(ctx.singleExpression(0))+" "+ctx.getText()+" "+visit(ctx.singleExpression(1));
        }

        @Override public String visitPostIncrementExpression(JavaScriptParser.PostIncrementExpressionContext ctx) {
            return visit(ctx.singleExpression())+"++";
        }

        @Override public String visitYieldExpression(JavaScriptParser.YieldExpressionContext ctx) {
            return visitYieldStatement(ctx.yieldStatement());
        }

        @Override public String visitBitNotExpression(JavaScriptParser.BitNotExpressionContext ctx) {
            return "~"+visit(ctx.singleExpression());
        }

        @Override public String visitNewExpression(JavaScriptParser.NewExpressionContext ctx) {
            return ctx.New().getText()+" "+visit(ctx.singleExpression())+" "+visitArguments(ctx.arguments());
        }

        @Override public String visitLiteralExpression(JavaScriptParser.LiteralExpressionContext ctx) {
            return visitLiteral(ctx.literal());
        }

        @Override public String visitArrayLiteralExpression(JavaScriptParser.ArrayLiteralExpressionContext ctx) {
            return visitArrayLiteral(ctx.arrayLiteral());
        }

        //TODO 待确认
        @Override public String visitMemberDotExpression(JavaScriptParser.MemberDotExpressionContext ctx) {
            if(ctx.singleExpression()!=null){
                return visit(ctx.singleExpression())+"?";
            }else if(ctx.identifierName()!=null){
                return visitIdentifierName(ctx.identifierName());
            }
            return ".";
        }

        @Override public String visitClassExpression(JavaScriptParser.ClassExpressionContext ctx) {
            if (ctx.identifier()!=null){
                return ctx.Class().getText()+" "+visitIdentifier(ctx.identifier());
            }
            return ctx.Class().getText()+" "+visitClassTail(ctx.classTail());
        }

        @Override public String visitMemberIndexExpression(JavaScriptParser.MemberIndexExpressionContext ctx) {
            return ctx.singleExpression().getText()+"?."+visitExpressionSequence(ctx.expressionSequence());
        }

        @Override public String visitIdentifierExpression(JavaScriptParser.IdentifierExpressionContext ctx) {
            return visitIdentifier(ctx.identifier());
        }

        @Override public String visitBitAndExpression(JavaScriptParser.BitAndExpressionContext ctx) {
            // exprBitAnd :  expression '&' expression
            return visit(ctx.singleExpression(0)) + " & " + visit(ctx.singleExpression(1));
        }

        @Override public String visitBitOrExpression(JavaScriptParser.BitOrExpressionContext ctx) {
            // exprBitAnd :  expression '|' expression
            return visit(ctx.singleExpression(0)) + " | " + visit(ctx.singleExpression(1));
        }

        @Override public String visitAssignmentOperatorExpression(JavaScriptParser.AssignmentOperatorExpressionContext ctx) {
            return ctx.getText();
        }

        @Override public String visitVoidExpression(JavaScriptParser.VoidExpressionContext ctx) {
            //    | Void singleExpression
            ASNode voidNode=new ASNode(ASNode.Type.VOID);
            voidNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(voidNode);
            AST.addEdge(parentStack.peek(),voidNode);
            ASNode block=new ASNode(ASNode.Type.BLOCK);
            block.setLineOfCode(ctx.singleExpression().getStart().getLine());
            AST.addVertex(block);
            AST.addEdge(voidNode,block);
            parentStack.push(block);
            visit(ctx.singleExpression());
            parentStack.pop();
            return "";
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitCoalesceExpression(JavaScriptParser.CoalesceExpressionContext ctx) { return visitChildren(ctx); }

        @Override public String visitAssignable(JavaScriptParser.AssignableContext ctx) {
            if(ctx.identifier()!=null){
                return ctx.identifier().getText();
            }else if(ctx.arrayLiteral()!=null){
                return ctx.arrayLiteral().getText();
            }
            return ctx.objectLiteral().getText();
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitObjectLiteral(JavaScriptParser.ObjectLiteralContext ctx) {
            //TODO
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) {
            //TODO
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitAnonymousFunctionDecl(JavaScriptParser.AnonymousFunctionDeclContext ctx) {
            //TODO
            return visitChildren(ctx);
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override public String visitArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) {
            //TODO
            return visitChildren(ctx);
        }

        @Override public String visitArrowFunctionParameters(JavaScriptParser.ArrowFunctionParametersContext ctx) {
            if (ctx.identifier()!=null){
                return ctx.identifier().getText();
            }
            return visitFormalParameterList(ctx.formalParameterList());
        }

        @Override public String visitArrowFunctionBody(JavaScriptParser.ArrowFunctionBodyContext ctx) {
            if(ctx.singleExpression()!=null){
                return visit(ctx.singleExpression());
            }
            return visitFunctionBody(ctx.functionBody());
        }

        @Override public String visitAssignmentOperator(JavaScriptParser.AssignmentOperatorContext ctx) {
            return ctx.getText();
        }

        @Override public String visitLiteral(JavaScriptParser.LiteralContext ctx) {
            if (ctx.NullLiteral()!=null){
                return ctx.NullLiteral().getText();
            }else if(ctx.BooleanLiteral()!=null){
                return ctx.BooleanLiteral().getText();
            }else if(ctx.RegularExpressionLiteral()!=null){
                return ctx.RegularExpressionLiteral().getText();
            }else if(ctx.templateStringLiteral()!=null){
                return visitTemplateStringLiteral(ctx.templateStringLiteral());
            }else if(ctx.numericLiteral()!=null){
                return visitNumericLiteral(ctx.numericLiteral());
            }
            return visitBigintLiteral(ctx.bigintLiteral());
        }

        @Override public String visitTemplateStringLiteral(JavaScriptParser.TemplateStringLiteralContext ctx) {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(ctx.BackTick().get(0).getText()+" ");
            for (JavaScriptParser.TemplateStringAtomContext atomContent : ctx.templateStringAtom()) {
                stringBuffer.append(visitTemplateStringAtom(atomContent));
                stringBuffer.append(" ");
            }
            for(int i=1;i<ctx.BackTick().size();i++){
                stringBuffer.append(ctx.BackTick().get(i).getText()+" ");
            }
            return stringBuffer.toString();
        }

        @Override public String visitTemplateStringAtom(JavaScriptParser.TemplateStringAtomContext ctx) {
            if(ctx.TemplateStringAtom()!=null){
                return ctx.TemplateStringAtom().getText();
            }
            return ctx.TemplateStringStartExpression().getText()+" "+visit(ctx.singleExpression())+" "+ctx.TemplateCloseBrace().getText();
        }

        @Override public String  visitNumericLiteral(JavaScriptParser.NumericLiteralContext ctx) {
            if (ctx.DecimalLiteral()!=null){
                return ctx.DecimalLiteral().getText();
            }else if(ctx.HexIntegerLiteral()!=null){
                return ctx.HexIntegerLiteral().getText();
            }else if(ctx.OctalIntegerLiteral()!=null){
                return ctx.OctalIntegerLiteral().getText();
            }else if(ctx.OctalIntegerLiteral2()!=null){
                return ctx.OctalIntegerLiteral2().getText();
            }
            return ctx.BinaryIntegerLiteral().getText();
        }

        @Override public String visitBigintLiteral(JavaScriptParser.BigintLiteralContext ctx) {
            if (ctx.BigBinaryIntegerLiteral()!=null){
                return ctx.BigBinaryIntegerLiteral().getText();
            }else if(ctx.BigHexIntegerLiteral()!=null){
                return ctx.BigHexIntegerLiteral().getText();
            }else if(ctx.BigOctalIntegerLiteral()!=null){
                return ctx.BigOctalIntegerLiteral().getText();
            }
            return ctx.BigBinaryIntegerLiteral().getText();
        }
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

        @Override public String visitIdentifierName(JavaScriptParser.IdentifierNameContext ctx) {
            if (ctx.identifier()!=null){
                return visitIdentifier(ctx.identifier());
            }
            return visitReservedWord(ctx.reservedWord());
        }

        @Override public String visitIdentifier(JavaScriptParser.IdentifierContext ctx) {
            if(ctx.Identifier()!=null){
                return ctx.Identifier().getText();
            }else if(ctx.NonStrictLet()!=null){
                return ctx.NonStrictLet().getText();
            }else if(ctx.Async()!=null){
                return ctx.Async().getText();
            }
            return ctx.As().getText();
        }

        @Override public String visitReservedWord(JavaScriptParser.ReservedWordContext ctx) {
            if (ctx.keyword()!=null){
                return visitKeyword(ctx.keyword());
            }else if(ctx.NullLiteral()!=null){
                return ctx.NullLiteral().getText();
            }else if(ctx.BooleanLiteral()!=null){
                return ctx.BooleanLiteral().getText();
            }
            return "";
        }

        @Override public String visitKeyword(JavaScriptParser.KeywordContext ctx) {
            if (ctx.Break()!=null){
                return ctx.Break().getText();
            }else if(ctx.Do()!=null){
                return ctx.Do().getText();
            }else if(ctx.Instanceof()!=null){
                return ctx.Instanceof().getText();
            }else if(ctx.Typeof()!=null){
                return ctx.Typeof().getText();
            }else if(ctx.Case()!=null){
                return ctx.Case().getText();
            }else if(ctx.Else()!=null){
                return ctx.Else().getText();
            }else if(ctx.New()!=null){
                return ctx.New().getText();
            }else if(ctx.Var()!=null){
                return ctx.Var().getText();
            }else if(ctx.Catch()!=null){
                return ctx.Catch().getText();
            }else if(ctx.Finally()!=null){
                return ctx.Finally().getText();
            }else if(ctx.Return()!=null){
                return ctx.Return().getText();
            }else if(ctx.Void()!=null){
                return ctx.Void().getText();
            }else if(ctx.Continue()!=null){
                return ctx.Continue().getText();
            }else if(ctx.For()!=null){
                return ctx.For().getText();
            }else if(ctx.Switch()!=null){
                return ctx.Switch().getText();
            }else if(ctx.While()!=null){
                return ctx.While().getText();
            }else if(ctx.Debugger()!=null){
                return ctx.Debugger().getText();
            }else if(ctx.Function_()!=null){
                return ctx.Function_().getText();
            }else if(ctx.This()!=null){
                return ctx.This().getText();
            }else if(ctx.With()!=null){
                return ctx.With().getText();
            }else if(ctx.Default()!=null){
                return ctx.Default().getText();
            }else if(ctx.If()!=null){
                return ctx.If().getText();
            }else if(ctx.Throw()!=null){
                return ctx.Throw().getText();
            }else if(ctx.Delete()!=null){
                return ctx.Delete().getText();
            }else if(ctx.In()!=null){
                return ctx.In().getText();
            }else if(ctx.Try()!=null){
                return ctx.Try().getText();
            }else if(ctx.Class()!=null){
                return ctx.Class().getText();
            }else if(ctx.Enum()!=null){
                return ctx.Enum().getText();
            }else if(ctx.Extends()!=null){
                return ctx.Extends().getText();
            }else if(ctx.Super()!=null){
                return ctx.Super().getText();
            }else if(ctx.Const()!=null){
                return ctx.Const().getText();
            }else if(ctx.Export()!=null){
                return ctx.Export().getText();
            }else if(ctx.Implements()!=null){
                return ctx.Implements().getText();
            }else if(ctx.Import()!=null){
                return ctx.Import().getText();
            }else if(ctx.let_()!=null){
                return ctx.let_().getText();
            }else if(ctx.Private()!=null){
                return ctx.Private().getText();
            }else if(ctx.Public()!=null){
                return ctx.Public().getText();
            }else if(ctx.Interface()!=null){
                return ctx.Interface().getText();
            }else if(ctx.Package()!=null){
                return ctx.Package().getText();
            }else if(ctx.Protected()!=null){
                return ctx.Protected().getText();
            }else if(ctx.Static()!=null){
                return ctx.Static().getText();
            }else if(ctx.Yield()!=null){
                return ctx.Yield().getText();
            }else if(ctx.Async()!=null){
                return ctx.Async().getText();
            }else if(ctx.Await()!=null){
                return ctx.Await().getText();
            }else if(ctx.From()!=null){
                return ctx.From().getText();
            }else if(ctx.As()!=null){
                return ctx.As().getText();
            }
            return "";
        }

        @Override public String visitLet_(JavaScriptParser.Let_Context ctx) {
            if (ctx.NonStrictLet()!=null){
                return ctx.NonStrictLet().getText();
            }
            return ctx.StrictLet().getText();
        }

        @Override public String visitEos(JavaScriptParser.EosContext ctx) {
            //  : SemiColon
            //    | EOF
            //    | {this.lineTerminatorAhead()}?
            //    | {this.closeBrace()}?
            if(ctx.SemiColon()!=null){
                return ctx.SemiColon().getText();
            }else if(ctx.EOF()!=null){
                return ctx.EOF().getText();
            }
            //TODO
            return "";
        }

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
