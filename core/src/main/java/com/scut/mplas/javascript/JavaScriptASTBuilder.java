package com.scut.mplas.javascript;

import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
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
    public static AbstractSyntaxTree build(String jsFile) throws IOException {
        return build(new File(jsFile));
    }

    /**
     * ‌Build and return the Abstract Syntax Tree (AST) for the given JavaScript source file.
     */
    public static AbstractSyntaxTree build(File jsFile) throws IOException {
        if (!jsFile.getName().endsWith(".js"))
            throw new IOException("Not a JavaScript File!");
        InputStream inFile = new FileInputStream(jsFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        JavaScriptLexer lexer = new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();
//        ParseTree tree=parser.sourceElements();
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
            System.out.println(rootCntx.EOF());
            if (rootCntx.sourceElements() != null) {
                System.out.println("sourceElement:" + rootCntx.sourceElements());
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
            if (ctx == null) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.SOURCEELEMENTS);
            node.setCode(ctx.sourceElements().getText());
            node.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding sourceElements");
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.add(node);
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.HashBangLine() != null) {
                stringBuffer.append(ctx.HashBangLine().getText() + " ");
            }
            if(ctx.sourceElements()!=null&&!ctx.sourceElements().isEmpty()){
                stringBuffer.append(visitSourceElements(ctx.sourceElements())+" ");
            }
            stringBuffer.append(ctx.EOF().getText());
            parentStack.pop();
            return stringBuffer.toString();
        }

        @Override public String visitSourceElement(JavaScriptParser.SourceElementContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.SOURCEELEMENT);
            node.setLineOfCode(ctx.getStart().getLine());
            node.setCode(ctx.statement().getText());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.add(node);
            String res = visitStatement(ctx.statement());
            parentStack.peek();
            return res;
        }

        @Override public String visitStatement(JavaScriptParser.StatementContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.STATEMENT);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.add(node);
            String res = null;
            if (ctx.block() != null && !ctx.block().isEmpty()) {
                res = visitBlock(ctx.block());
            } else if (ctx.importStatement() != null && !ctx.importStatement().isEmpty()) {
                res = visitImportStatement(ctx.importStatement());
            } else if (ctx.variableStatement() != null && !ctx.variableStatement().isEmpty()) {
                res = visitVariableStatement(ctx.variableStatement());
            } else if (ctx.exportStatement() != null && !ctx.exportStatement().isEmpty()) {
                res = visitExpressionStatement(ctx.expressionStatement());
            } else if (ctx.emptyStatement_() != null && !ctx.emptyStatement_().isEmpty()) {
                res = visitEmptyStatement_(ctx.emptyStatement_());
            } else if (ctx.classDeclaration() != null && !ctx.classDeclaration().isEmpty()) {
                res = visitClassDeclaration(ctx.classDeclaration());
            } else if (ctx.expressionStatement() != null && !ctx.expressionStatement().isEmpty()) {
                res = visitExpressionStatement(ctx.expressionStatement());
            } else if (ctx.ifStatement() != null && !ctx.ifStatement().isEmpty()) {
                res = visitIfStatement(ctx.ifStatement());
            } else if (ctx.iterationStatement() != null && !ctx.iterationStatement().isEmpty()) {
                res= visitIterationStatement(ctx.iterationStatement());
            }else if (ctx.continueStatement()!=null&&!ctx.continueStatement().isEmpty()){
                res= visitContinueStatement(ctx.continueStatement());
            }else if (ctx.breakStatement()!=null&&!ctx.breakStatement().isEmpty()){
                res= visitBreakStatement(ctx.breakStatement());
            }else if (ctx.returnStatement()!=null&&!ctx.returnStatement().isEmpty()){
                res= visitReturnStatement(ctx.returnStatement());
            }else if (ctx.yieldStatement()!=null&&!ctx.yieldStatement().isEmpty()){
                res= visitYieldStatement(ctx.yieldStatement());
            }else if (ctx.withStatement()!=null&&!ctx.withStatement().isEmpty()){
                res= visitWithStatement(ctx.withStatement());
            }else if (ctx.labelledStatement()!=null&&!ctx.labelledStatement().isEmpty()){
                res= visitLabelledStatement(ctx.labelledStatement());
            }else if (ctx.switchStatement()!=null&&!ctx.switchStatement().isEmpty()){
                res= visitSwitchStatement(ctx.switchStatement());
            }else if (ctx.throwStatement()!=null&&!ctx.throwStatement().isEmpty()){
                res= visitThrowStatement(ctx.throwStatement());
            }else if (ctx.tryStatement()!=null&&!ctx.tryStatement().isEmpty()){
                res= visitTryStatement(ctx.tryStatement());
            }else if (ctx.debuggerStatement()!=null&&!ctx.debuggerStatement().isEmpty()){
                res= visitDebuggerStatement(ctx.debuggerStatement());
            }else{
                res=visitFunctionDeclaration(ctx.functionDeclaration());
            }
            parentStack.pop();
            return res;
        }

        private String visitIterationStatement(JavaScriptParser.IterationStatementContext iterationStatement) {
            //TODO
            // : Do statement While '(' expressionSequence ')' eos                                                                       # DoStatement
            //    | While '(' expressionSequence ')' statement                                                                              # WhileStatement
            //    | For '(' (expressionSequence | variableDeclarationList)? ';' expressionSequence? ';' expressionSequence? ')' statement   # ForStatement
            //    | For '(' (singleExpression | variableDeclarationList) In expressionSequence ')' statement                                # ForInStatement
            //    // strange, 'of' is an identifier. and this.p("of") not work in sometime.
            //    | For Await? '(' (singleExpression | variableDeclarationList) identifier{this.p("of")}? expressionSequence ')' statement  # ForOfStatement
            //    ;
            return "";
        }


        @Override public String visitBlock(JavaScriptParser.BlockContext ctx) {
            if (ctx == null) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.BLOCK);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.add(node);
            String res = "{" + visitStatementList(ctx.statementList()) + "}";
            parentStack.pop();
            return res;
        }

        @Override public String visitStatementList(JavaScriptParser.StatementListContext ctx) {
            if (ctx == null) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.STATEMENTLIST);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.add(node);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < ctx.statement().size(); i++) {
                stringBuffer.append(visitStatement(ctx.statement(i)));
            }
            parentStack.pop();
            return stringBuffer.toString();
        }

        @Override public String visitImportStatement(JavaScriptParser.ImportStatementContext ctx) {
            if (ctx == null) {
                return "";
            }
            //Import importFromBlock
            ASNode node = new ASNode(ASNode.Type.IMPORT);
            node.setLineOfCode(ctx.getStart().getLine());
            node.setCode(ctx.Import().getText());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.push(node);
            String res = visit(ctx.Import()) + " " + visit(ctx.importFromBlock());
            parentStack.pop();
            return res;
        }

        @Override public String visitImportFromBlock(JavaScriptParser.ImportFromBlockContext ctx) {
            if (ctx == null) {
                return "";
            }
            System.out.println("default:" + ctx.importDefault());
            System.out.println("namespace:" + ctx.importNamespace());
            System.out.println("importFrom:" + ctx.importFrom());
            //importDefault? (importNamespace | moduleItems) importFrom eos
            //    | StringLiteral eos
            ASNode node = new ASNode(ASNode.Type.IMPORTFROMBLOCK);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.push(node);
            StringBuffer res = new StringBuffer();
            if (ctx.importFrom() != null && !ctx.importFrom().isEmpty()) {
                if (ctx.importDefault() != null && !ctx.importDefault().isEmpty()) {
                    res.append(visitImportDefault(ctx.importDefault()));
                }
                if (ctx.importNamespace() != null && !ctx.importNamespace().isEmpty()) {
                    res.append(" " + visitImportNamespace(ctx.importNamespace()));
                }
                if (ctx.moduleItems() != null && !ctx.moduleItems().isEmpty()) {
                    res.append(" " + visitModuleItems(ctx.moduleItems()));
                }
                res.append(" " + visitImportFrom(ctx.importFrom()));
                res.append(" " + visitEos(ctx.eos()));
            }else{
                res.append(ctx.StringLiteral().getText() + " " + visitEos(ctx.eos()));
            }
            parentStack.pop();
            return res.toString();
        }

        @Override public String visitModuleItems(JavaScriptParser.ModuleItemsContext ctx) {
            if (ctx == null) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("{");
            ASNode node = new ASNode(ASNode.Type.MODULEITEMS);
            node.setLineOfCode(ctx.getStart().getLine());
            parentStack.add(node);
            stringBuffer.append(visitAliasName(ctx.aliasName(0)));
            for (int i = 1; i < ctx.aliasName().size(); i++) {
                stringBuffer.append("," + visitAliasName(ctx.aliasName(i)));
            }
            parentStack.pop();
            stringBuffer.append("}");
            return stringBuffer.toString();
        }

        @Override public String visitImportDefault(JavaScriptParser.ImportDefaultContext ctx) {
            if (ctx == null) {
                return "";
            }
            return visit(ctx.aliasName()) + " ,";
        }

        @Override public String visitImportNamespace(JavaScriptParser.ImportNamespaceContext ctx) {
            if (ctx == null) {
                return "";
            }
            //importNamespaceDeclration:     : ('*' | identifierName) (As identifierName)?
            ASNode node = new ASNode(ASNode.Type.IMPORTNAMESPACE);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.push(node);
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.identifierName() != null) {
                stringBuffer.append(visit(ctx.identifierName(0)));
            } else {
                stringBuffer.append("* ");
            }
            if (ctx.identifierName().size()>1){
                stringBuffer.append(visit(ctx.As()));
                stringBuffer.append(" ");
                stringBuffer.append(visit(ctx.identifierName(1)));
            }
            parentStack.peek();
            return stringBuffer.toString();
        }

        @Override public String visitImportFrom(JavaScriptParser.ImportFromContext ctx) {
            if (ctx == null) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.IMPORTFROM);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            return ctx.From().getText() + " " + ctx.StringLiteral().getText();
        }

        @Override public String visitAliasName(JavaScriptParser.AliasNameContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.ALIASNAME);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.add(node);
            String res = visitIdentifierName(ctx.identifierName(0)) + " " + ctx.As().getText() + " " + visitIdentifierName(ctx.identifierName(1));
            parentStack.pop();
            return res;
        }

        @Override public String visitExportDeclaration(JavaScriptParser.ExportDeclarationContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode exportNode = new ASNode(ASNode.Type.EXPORT);
            exportNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(exportNode);
            AST.addEdge(parentStack.peek(), exportNode);
            parentStack.add(exportNode);
            String res = "";
            if (ctx.exportFromBlock() != null && !ctx.exportFromBlock().isEmpty()) {
                res = ctx.Export().getText() + " " + visitExportFromBlock(ctx.exportFromBlock()) + " " + visitEos(ctx.eos());
            } else {
                res = ctx.Export().getText() + " " + visitDeclaration(ctx.declaration()) + " " + visitEos(ctx.eos());
            }
            parentStack.pop();
            return res;
        }

        @Override public String visitExportDefaultDeclaration(JavaScriptParser.ExportDefaultDeclarationContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode exportNode = new ASNode(ASNode.Type.EXPORT);
            exportNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(exportNode);
            AST.addEdge(parentStack.peek(), exportNode);
            return ctx.Export().getText() + " " + ctx.Default().getText() + " " + visit(ctx.singleExpression()) + " " + visitEos(ctx.eos());
        }

        @Override public String visitExportFromBlock(JavaScriptParser.ExportFromBlockContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.EXPORTFROMBLOCK);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            parentStack.push(node);
            String res = null;
            if (ctx.importNamespace() != null && !ctx.importNamespace().isEmpty()) {
                res = visitImportNamespace(ctx.importNamespace()) + " " + visitImportFrom(ctx.importFrom()) + " " + visitEos(ctx.eos());
            } else {
                res = visitModuleItems(ctx.moduleItems()) + " " + visitImportFrom(ctx.importFrom()) + " " + visitEos(ctx.eos());
            }
            parentStack.pop();
            return res;
        }

        @Override public String visitDeclaration(JavaScriptParser.DeclarationContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.variableStatement() != null && !ctx.variableStatement().isEmpty()) {
                return visitVariableStatement((ctx.variableStatement()));
            } else if (ctx.classDeclaration() != null && !ctx.classDeclaration().isEmpty()) {
                return visitClassDeclaration(ctx.classDeclaration());
            }
            return visitFunctionDeclaration(ctx.functionDeclaration());
        }

        @Override public String visitVariableStatement(JavaScriptParser.VariableStatementContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode expressionNode = new ASNode(ASNode.Type.STATEMENT);
            expressionNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(expressionNode);
            AST.addEdge(parentStack.peek(), expressionNode);
            if (ctx.variableDeclarationList() != null && !ctx.variableDeclarationList().isEmpty()) {
                return visitVariableDeclarationList(ctx.variableDeclarationList());
            }
            return visitEos(ctx.eos());
        }

        @Override public String visitVariableDeclarationList(JavaScriptParser.VariableDeclarationListContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(visitVarModifier(ctx.varModifier()) + " ");
            stringBuffer.append(visitVariableDeclaration(ctx.variableDeclaration(0)));
            for (int i = 1; i < ctx.variableDeclaration().size(); i++) {
                stringBuffer.append("," + visitVariableDeclaration(ctx.variableDeclaration(i)));
            }
            return stringBuffer.toString();
        }

        @Override public String visitVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitAssignable(ctx.assignable()) + "=" + visit(ctx.singleExpression());
        }

        @Override public String visitEmptyStatement_(JavaScriptParser.EmptyStatement_Context ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode emptyNode = new ASNode(ASNode.Type.EMPTY);
            emptyNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(emptyNode);
            AST.addEdge(parentStack.peek(), emptyNode);
            return ctx.SemiColon().getText();
        }

        @Override public String visitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //    : {this.notOpenBraceAndNotFunction()}? expressionSequence eos
            ASNode expressionNode = new ASNode(ASNode.Type.STATEMENT);
            expressionNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(expressionNode);
            AST.addEdge(parentStack.peek(), expressionNode);
            if (ctx.expressionSequence() != null && !ctx.expressionSequence().isEmpty()) {
                return visitExpressionSequence(ctx.expressionSequence());
            }
            return visitEos(ctx.eos());
        }

        @Override public String visitIfStatement(JavaScriptParser.IfStatementContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //     | While '(' expressionSequence ')' statement                                                                              # WhileStatement
            ASNode whileNode = new ASNode(ASNode.Type.WHILE);
            whileNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(whileNode);
            AST.addEdge(parentStack.peek(), whileNode);

            //while条件
            ASNode cond = new ASNode(ASNode.Type.CONDITION);
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode forNode;
            forNode = new ASNode(ASNode.Type.FOR);
            forNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(forNode);
            AST.addEdge(parentStack.peek(), forNode);
            //for初始化
            ASNode forInit = new ASNode(ASNode.Type.FOR_INIT);
            AST.addVertex(forInit);
            AST.addEdge(forNode, forInit);

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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode forNode = new ASNode(ASNode.Type.FOR_EACH);
            if (ctx.singleExpression() != null) {
                ASNode expr = new ASNode(ASNode.Type.STATEMENT);
                expr.setCode(getOriginalCodeText(ctx.singleExpression()));
                expr.setNormalizedCode(visit(ctx.singleExpression()));
                expr.setLineOfCode(ctx.singleExpression().getStart().getLine());
                AST.addVertex(expr);
                AST.addEdge(forNode, expr);
            } else {
                ASNode expr = new ASNode(ASNode.Type.STATEMENT);
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

        @Override public String visitForOfStatement(JavaScriptParser.ForOfStatementContext ctx) {
            //    | For Await? '(' (singleExpression | variableDeclarationList) identifier{this.p("of")}? expressionSequence ')' statement  # ForOfStatement
            //TODO
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(ctx.For().getText() + " " + ctx.Await().getText());
            stringBuffer.append("(");
            if (ctx.singleExpression() != null && !ctx.singleExpression().isEmpty()) {
                stringBuffer.append(visit(ctx.singleExpression()));
            }
            if (ctx.variableDeclarationList() != null && !ctx.variableDeclarationList().isEmpty()) {
                stringBuffer.append(visit(ctx.variableDeclarationList()));
            }
            stringBuffer.append(visitIdentifier(ctx.identifier()));
            stringBuffer.append(visitExpressionSequence(ctx.expressionSequence()));
            stringBuffer.append(")");
            stringBuffer.append(visitStatement(ctx.statement()));
            return stringBuffer.toString();
        }

        @Override public String visitVarModifier(JavaScriptParser.VarModifierContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.Var() != null) {
                return ctx.Var().getText();
            } else if (ctx.let_() != null && !ctx.let_().isEmpty()) {
                return visitLet_(ctx.let_());
            }
            return ctx.Const().getText();
        }

        @Override public String visitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) {
            // TODO   : Continue ({this.notLineTerminator()}? identifier)? eos
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.CONTINUE);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            if (ctx.Continue() == null) {
                visit(ctx.eos());
                return "";
            }
            visitStatement(ctx.identifier(), "continue $LABEL");
            return "";
        }

        @Override public String visitBreakStatement(JavaScriptParser.BreakStatementContext ctx) {
            //:    : Break ({this.notLineTerminator()}? identifier)? eos
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.BREAK);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            if (ctx.Break() == null) {
                visit(ctx.eos());
                return "";
            }
            visitStatement(ctx.identifier(), "continue $LABEL");
            return "";
        }

        @Override public String visitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) {
            //:        : Return ({this.notLineTerminator()}? expressionSequence)? eos
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.RETURN);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            if (ctx.Return() == null) {
                visit(ctx.eos());
                return "";
            }

            visitStatement(ctx, "return " + visit(ctx.expressionSequence()));
            return "";
        }

        @Override public String visitYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
            //:         : Yield ({this.notLineTerminator()}? expressionSequence)? eos
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.YIELD);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            if (ctx.Yield() == null) {
                visit(ctx.eos());
                return "";
            }

            visitStatement(ctx, "yield " + visit(ctx.expressionSequence()));
            return "";
        }

        @Override public String visitWithStatement(JavaScriptParser.WithStatementContext ctx) {
            //:      : With '(' expressionSequence ')' statement
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode withNode = new ASNode(ASNode.Type.WITH);
            withNode.setCode(getOriginalCodeText(ctx.expressionSequence()));
            withNode.setNormalizedCode(visit(ctx.expressionSequence()));
            withNode.setLineOfCode(ctx.expressionSequence().getStart().getLine());
            AST.addVertex(withNode);
            ASNode exprNode = new ASNode(ASNode.Type.STATEMENT);
            exprNode.setLineOfCode(ctx.statement().getStart().getLine());
            exprNode.setCode(ctx.statement().getText());
            AST.addVertex(exprNode);
            AST.addEdge(withNode, exprNode);
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode switchNode = new ASNode(ASNode.Type.SWITCH);
            switchNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(switchNode);
            AST.addEdge(parentStack.peek(), switchNode);
            //
            ASNode statementNode = new ASNode(ASNode.Type.STATEMENT);
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode caseBlockNode = new ASNode(ASNode.Type.CASEBLOCK);
            caseBlockNode.setLineOfCode(ctx.getStart().getLine());
            caseBlockNode.setCode(ctx.getText());
            AST.addVertex(caseBlockNode);
            if (ctx.caseClauses() == null) {
                parentStack.push(caseBlockNode);
                visitDefaultClause(ctx.defaultClause());
                parentStack.pop();
            } else {
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode caseClausesNode = new ASNode(ASNode.Type.CASECLAUSES);
            caseClausesNode.setLineOfCode(ctx.getStart().getLine());
            caseClausesNode.setCode(ctx.getText());
            AST.addVertex(caseClausesNode);
            AST.addEdge(parentStack.peek(), caseClausesNode);
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode caseNode = new ASNode(ASNode.Type.CASE);
            caseNode.setLineOfCode(ctx.getStart().getLine());
            caseNode.setCode(ctx.getText());
            AST.addVertex(caseNode);
            AST.addEdge(parentStack.peek(), caseNode);
            ASNode exprNode = new ASNode(ASNode.Type.STATEMENT);
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.Default() != null) {
                ASNode caseNode = new ASNode(ASNode.Type.CASE);
                caseNode.setLineOfCode(ctx.getStart().getLine());
                caseNode.setCode(ctx.getText());
                AST.addVertex(caseNode);
                AST.addEdge(parentStack.peek(), caseNode);
                return "Default : " + visit(ctx.statementList());
            }
            return "";
        }


        @Override public String visitLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) {
            //    : identifier ':' statement
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.identifier().Identifier().getText() + ":" + visit(ctx.statement());
        }

        @Override public String visitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) {
            //:      : Throw {this.notLineTerminator()}? expressionSequence eos
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode node = new ASNode(ASNode.Type.THROW);
            node.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            if (ctx.Throw() == null) {
                visit(ctx.eos());
                return "";
            }
            visitStatement(ctx, "throw " + visit(ctx.expressionSequence()));
            return "";
        }

        @Override public String visitTryStatement(JavaScriptParser.TryStatementContext ctx) {
            //  : Try block (catchProduction finallyProduction? | finallyProduction)
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode catchNode = new ASNode(ASNode.Type.CATCH);
            catchNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(catchNode);
            if (ctx.assignable() != null) {
                ASNode expr = new ASNode(ASNode.Type.STATEMENT);
                expr.setLineOfCode(ctx.assignable().getStart().getLine());
                AST.addVertex(expr);
                AST.addEdge(catchNode, expr);
            }
            ASNode blockNode = new ASNode(ASNode.Type.BLOCK);
            blockNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(blockNode);
            AST.addEdge(catchNode, blockNode);
            parentStack.push(catchNode);
            return visitChildren(ctx);
        }

        @Override public String visitFinallyProduction(JavaScriptParser.FinallyProductionContext ctx) {
            //: : Finally block
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            ASNode finalNode = new ASNode(ASNode.Type.FINALLY);
            finalNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(finalNode);
            ASNode blockNode = new ASNode(ASNode.Type.BLOCK);
            blockNode.setLineOfCode(ctx.block().getStart().getLine());
            AST.addVertex(blockNode);
            AST.addEdge(finalNode, blockNode);
            parentStack.push(finalNode);
            return visitChildren(ctx);
        }

        @Override public String visitDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) {
            //    : Debugger eos
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.Debugger() != null) {
                visit(ctx.eos());
            }
            return "";
        }

        @Override public String visitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
            //      : Async? Function_ '*'? identifier '(' formalParameterList? ')' functionBody
            //TODO
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.Async() != null) {
                stringBuffer.append("Async ");
            }
            if (ctx.Function_() != null) {
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.Extends() != null) {
                ASNode extendsNode = new ASNode(ASNode.Type.EXTENDS);
                extendsNode.setCode(ctx.singleExpression().getText());
                extendsNode.setLineOfCode(ctx.singleExpression().getStart().getLine());
                Logger.debug("Adding extends " + ctx.singleExpression().getText());
                AST.addVertex(extendsNode);
                for (JavaScriptParser.ClassElementContext classElement : ctx.classElement()) {
                    ASNode classNode = new ASNode(ASNode.Type.CLASS);
                    extendsNode.setCode(classElement.getText());
                    extendsNode.setLineOfCode(classElement.getStart().getLine());
                    Logger.debug("Adding class name: " + classElement.getText());
                    AST.addVertex(classNode);
                    AST.addEdge(extendsNode,classNode);
                }
            }
            return "";
        }

        @Override public String visitClassElement(JavaScriptParser.ClassElementContext ctx) {
            //    : (Static | {this.n("static")}? identifier | Async)* (methodDefinition | assignable '=' objectLiteral ';')
            //    | emptyStatement_
            //    | '#'? propertyName '=' singleExpression
            //TODO
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.emptyStatement_() != null && !ctx.emptyStatement_().isEmpty()) {
                return visitEmptyStatement_(ctx.emptyStatement_());
            } else if (ctx.propertyName() != null && !ctx.propertyName().isEmpty()) {
                return "#" + visitPropertyName(ctx.propertyName()) + "=" + visit(ctx.singleExpression());
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < ctx.Static().size(); i++) {
                stringBuffer.append(ctx.Static(i).getText() + " ");
                stringBuffer.append(visitIdentifier(ctx.identifier(i)) + " ");
                stringBuffer.append(ctx.Async(i).getText() + " ");
            }
            if(ctx.methodDefinition()!=null&&!ctx.methodDefinition().isEmpty()){
                stringBuffer.append(visitMethodDefinition(ctx.methodDefinition()));
            }
            if(ctx.assignable()!=null&&!ctx.assignable().isEmpty()){
                stringBuffer.append(visitAssignable(ctx.assignable())+" ");
            }
            stringBuffer.append(visitObjectLiteral(ctx.objectLiteral())+";");
            return stringBuffer.toString();
        }

        @Override public String visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {
            //TODO
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.propertyName() != null && !ctx.propertyName().isEmpty()) {
                return "*#" + visitPropertyName(ctx.propertyName()) + " (" + visitFormalParameterList(ctx.formalParameterList()) + ")" + visitFunctionBody(ctx.functionBody());
            } else if (ctx.getter() != null && !ctx.getter().isEmpty()) {
                return "*#" + visitGetter(ctx.getter()) + "()" + visitFunctionBody(ctx.functionBody());
            }
            return "*#" + visitSetter(ctx.setter()) + " (" + visitFormalParameterList(ctx.formalParameterList()) + ")" + visitFunctionBody(ctx.functionBody());
        }

        @Override public String visitFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) {
            if (ctx.formalParameterArg()!=null&&!ctx.formalParameterArg().isEmpty()){
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer.append(ctx.formalParameterArg(0));
                for(int i=1;i<ctx.formalParameterArg().size();i++){
                    stringBuffer.append(",");
                    stringBuffer.append(visitFormalParameterArg(ctx.formalParameterArg(i)));
                }
                stringBuffer.append(",");
                stringBuffer.append(visitLastFormalParameterArg(ctx.lastFormalParameterArg()));
                return stringBuffer.toString();
            }
           return visitLastFormalParameterArg(ctx.lastFormalParameterArg());
        }

        @Override public String visitFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) {
            //    : assignable ('=' singleExpression)?      // ECMAScript 6: Initialization
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitAssignable(ctx.assignable()) + "=" + visit(ctx.singleExpression());
        }

        @Override public String visitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.Ellipsis().getText() + " " + visit(ctx.singleExpression());
        }


        @Override public String visitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "{" + visitSourceElements(ctx.sourceElements()) + "}";
        }

        @Override public String visitSourceElements(JavaScriptParser.SourceElementsContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.sourceElement() != null && ctx.sourceElement().size() > 0) {
                for (JavaScriptParser.SourceElementContext sourceContext : ctx.sourceElement()) {
                    stringBuffer.append(visitSourceElement(sourceContext) + " ");
                }
            }
            return stringBuffer.toString();
        }

        @Override public String visitArrayLiteral(JavaScriptParser.ArrayLiteralContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "[" + visitElementList(ctx.elementList()) + "]";
        }

        @Override public String visitElementList(JavaScriptParser.ElementListContext ctx) {
            //TODO
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(",");
            stringBuffer.append(visitArrayElement(ctx.arrayElement(0)));
            stringBuffer.append(",");
            for (int i = 1; i < ctx.arrayElement().size(); i++) {
                stringBuffer.append(",");
                stringBuffer.append(visitArrayElement(ctx.arrayElement(i)));
            }
            stringBuffer.append(",");
            return stringBuffer.toString();
        }

        @Override public String visitArrayElement(JavaScriptParser.ArrayElementContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.Ellipsis() != null) {
                return ctx.Ellipsis().getText() + " " + visit(ctx.singleExpression());
            }
            return visit(ctx.singleExpression());
        }

        @Override public String visitPropertyExpressionAssignment(JavaScriptParser.PropertyExpressionAssignmentContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitPropertyName(ctx.propertyName()) + ":" + visit(ctx.singleExpression());
        }

        @Override public String visitComputedPropertyExpressionAssignment(JavaScriptParser.ComputedPropertyExpressionAssignmentContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "[" + visit(ctx.singleExpression(0)) + "]:" + visit(ctx.singleExpression(1));
        }

        @Override public String visitFunctionProperty(JavaScriptParser.FunctionPropertyContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //TODO
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.Async() != null) {
                stringBuffer.append(ctx.Async());
            }
            stringBuffer.append("*");
            stringBuffer.append(visitPropertyName(ctx.propertyName()));
            stringBuffer.append("(");
            if (ctx.formalParameterList() != null && !ctx.formalParameterList().isEmpty()) {
                stringBuffer.append(visitFormalParameterList(ctx.formalParameterList()));
            }
            stringBuffer.append(")");
            stringBuffer.append(visitFunctionBody(ctx.functionBody()));
            return stringBuffer.toString();
        }

        @Override public String visitPropertyGetter(JavaScriptParser.PropertyGetterContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitGetter(ctx.getter()) + "()" + visitFunctionBody(ctx.functionBody());
        }

        @Override public String visitPropertySetter(JavaScriptParser.PropertySetterContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitSetter(ctx.setter()) + "(" + visitFormalParameterArg(ctx.formalParameterArg()) + ")" + visitFunctionBody(ctx.functionBody());
        }

        @Override public String visitPropertyShorthand(JavaScriptParser.PropertyShorthandContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.Ellipsis() != null) {
                return ctx.Ellipsis().getText() + " " + visit(ctx.singleExpression());
            }
            return visit(ctx.singleExpression());
        }

        @Override public String visitPropertyName(JavaScriptParser.PropertyNameContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.identifierName() != null) {
                return visitIdentifierName(ctx.identifierName());
            } else if (ctx.numericLiteral() != null) {
                return visitNumericLiteral(ctx.numericLiteral());
            } else if (ctx.StringLiteral() != null) {
                return ctx.StringLiteral().toString();
            }
            return "[" + visit(ctx.singleExpression()) + "]";
        }

        @Override public String visitArguments(JavaScriptParser.ArgumentsContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            // arguments :  '(' expressionList? ')'
            if (ctx.argument() == null)
                return "()";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("(");
            for (JavaScriptParser.ArgumentContext argument : ctx.argument()) {
                stringBuffer.append(visit(argument));
            }
            stringBuffer.append(")");
            return stringBuffer.toString();
        }

        @Override public String visitArgument(JavaScriptParser.ArgumentContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.Ellipsis() != null) {
                stringBuffer.append(ctx.Ellipsis().getText() + " ");
            }
            if (ctx.singleExpression() != null) {
                stringBuffer.append(visit(ctx.singleExpression()));
            } else {
                stringBuffer.append(visitIdentifier(ctx.identifier()));
            }
            return stringBuffer.toString();
        }

        @Override public String visitExpressionSequence(JavaScriptParser.ExpressionSequenceContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //TODO
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(visit(ctx.singleExpression(0)));
            for (int i = 1; i < ctx.singleExpression().size(); i++) {
                stringBuffer.append(",");
                stringBuffer.append(visit(ctx.singleExpression(i)));
            }
            return stringBuffer.toString();
        }

        @Override public String visitTemplateStringExpression(JavaScriptParser.TemplateStringExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression()) + " " + visitTemplateStringLiteral(ctx.templateStringLiteral());
        }

        @Override public String visitTernaryExpression(JavaScriptParser.TernaryExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + "?" + visit(ctx.singleExpression(1)) + ":" + visit(ctx.singleExpression(2));
        }

        @Override public String visitLogicalAndExpression(JavaScriptParser.LogicalAndExpressionContext ctx) {
            // exprBitAnd :  expression '&&' expression
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " && " + visit(ctx.singleExpression(1));
        }

        @Override public String visitPowerExpression(JavaScriptParser.PowerExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " ** " + visit(ctx.singleExpression(1));
        }

        @Override public String visitPreIncrementExpression(JavaScriptParser.PreIncrementExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "++" + visit(ctx.singleExpression());
        }

        @Override public String visitObjectLiteralExpression(JavaScriptParser.ObjectLiteralExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitObjectLiteral(ctx.objectLiteral());
        }

        @Override public String visitMetaExpression(JavaScriptParser.MetaExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.New().getText() + "." + visitIdentifier(ctx.identifier());
        }

        @Override public String visitInExpression(JavaScriptParser.InExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " " + ctx.In().getText() + " " + visit(ctx.singleExpression(1));
        }

        @Override public String visitLogicalOrExpression(JavaScriptParser.LogicalOrExpressionContext ctx) {
            // exprBitAnd :  expression '||' expression
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " || " + visit(ctx.singleExpression(1));
        }

        @Override public String visitOptionalChainExpression(JavaScriptParser.OptionalChainExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + "?." + visit(ctx.singleExpression(1));
        }

        @Override public String visitNotExpression(JavaScriptParser.NotExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "!" + visit(ctx.singleExpression());
        }

        @Override public String visitPreDecreaseExpression(JavaScriptParser.PreDecreaseExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "--" + visit(ctx.singleExpression());
        }

        @Override public String visitArgumentsExpression(JavaScriptParser.ArgumentsExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            // arguments :  '(' expressionList? ')'
            if (ctx.arguments() == null)
                return "()";
            return "(" + visit(ctx.arguments()) + ")";
        }

        @Override public String visitAwaitExpression(JavaScriptParser.AwaitExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.Await().getText() + " " + visit(ctx.singleExpression());
        }

        @Override public String visitThisExpression(JavaScriptParser.ThisExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.This().getText();
        }

        @Override public String visitFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.anonymousFunction().getText();
        }

        @Override public String visitUnaryMinusExpression(JavaScriptParser.UnaryMinusExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "- " + visit(ctx.singleExpression());
        }

        @Override public String visitAssignmentExpression(JavaScriptParser.AssignmentExpressionContext ctx) {
            // exprAssignment :  expression  ( '='  | '+='  | '-='   | '*='  | '/=' | '&=' |
            //                                 '|=' | '^=' | '>>=' | '>>>=' | '<<=' | '%=' )  expression
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " ?= " + visit(ctx.singleExpression(1));
        }

        @Override public String visitPostDecreaseExpression(JavaScriptParser.PostDecreaseExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression()) + "--";
        }

        @Override public String visitTypeofExpression(JavaScriptParser.TypeofExpressionContext ctx) {
            // exprTypeOf: typeof(expression)
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.Typeof()) + "(" + visit(ctx.singleExpression()) + ")";
        }

        @Override public String visitInstanceofExpression(JavaScriptParser.InstanceofExpressionContext ctx) {
            // exprInstanceOf :  expression 'instanceof' typeType
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " instanceof " + getOriginalCodeText(ctx.singleExpression(1));
        }


        @Override public String visitUnaryPlusExpression(JavaScriptParser.UnaryPlusExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "+" + visit(ctx.singleExpression());
        }

        @Override public String visitDeleteExpression(JavaScriptParser.DeleteExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.Delete().getText() + " " + visit(ctx.singleExpression());
        }

        @Override public String visitImportExpression(JavaScriptParser.ImportExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "(" + visit(ctx.singleExpression()) + ")";
        }

        @Override public String visitEqualityExpression(JavaScriptParser.EqualityExpressionContext ctx) {
            // exprEquality :  expression ('==' | '!=') expression
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
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
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " ^ " + visit(ctx.singleExpression(1));
        }

        @Override public String visitSuperExpression(JavaScriptParser.SuperExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.Super().getText();
        }

        @Override public String visitMultiplicativeExpression(JavaScriptParser.MultiplicativeExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " * " + visit(ctx.singleExpression(1));
        }

        @Override public String visitBitShiftExpression(JavaScriptParser.BitShiftExpressionContext ctx) {
            // exprBitShift :  expression ('<' '<' | '>' '>' '>' | '>' '>') expression
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            String sub = ctx.getText().substring(ctx.singleExpression(0).getText().length());
            String op;
            if (sub.startsWith(">>>"))
                op = ">>>";
            else
                op = sub.substring(0, 2);
            return visit(ctx.singleExpression(0)) + " " + op + " " + visit(ctx.singleExpression(1));
        }

        @Override public String visitParenthesizedExpression(JavaScriptParser.ParenthesizedExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "(" + visitExpressionSequence(ctx.expressionSequence()) + ")";
        }

        @Override public String visitAdditiveExpression(JavaScriptParser.AdditiveExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " + " + visit(ctx.singleExpression(1));
        }

        @Override public String visitRelationalExpression(JavaScriptParser.RelationalExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + " " + ctx.getText() + " " + visit(ctx.singleExpression(1));
        }

        @Override public String visitPostIncrementExpression(JavaScriptParser.PostIncrementExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression()) + "++";
        }

        @Override public String visitYieldExpression(JavaScriptParser.YieldExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitYieldStatement(ctx.yieldStatement());
        }

        @Override public String visitBitNotExpression(JavaScriptParser.BitNotExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return "~" + visit(ctx.singleExpression());
        }

        @Override public String visitNewExpression(JavaScriptParser.NewExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.New().getText() + " " + visit(ctx.singleExpression()) + " " + visitArguments(ctx.arguments());
        }

        @Override public String visitLiteralExpression(JavaScriptParser.LiteralExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitLiteral(ctx.literal());
        }

        @Override public String visitArrayLiteralExpression(JavaScriptParser.ArrayLiteralExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitArrayLiteral(ctx.arrayLiteral());
        }

        //TODO 待确认
        @Override public String visitMemberDotExpression(JavaScriptParser.MemberDotExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.singleExpression() != null) {
                return visit(ctx.singleExpression()) + "?";
            } else if (ctx.identifierName() != null) {
                return visitIdentifierName(ctx.identifierName());
            }
            return ".";
        }

        @Override public String visitClassExpression(JavaScriptParser.ClassExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.identifier() != null) {
                return ctx.Class().getText() + " " + visitIdentifier(ctx.identifier());
            }
            return ctx.Class().getText() + " " + visitClassTail(ctx.classTail());
        }

        @Override public String visitMemberIndexExpression(JavaScriptParser.MemberIndexExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.singleExpression().getText() + "?." + visitExpressionSequence(ctx.expressionSequence());
        }

        @Override public String visitIdentifierExpression(JavaScriptParser.IdentifierExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitIdentifier(ctx.identifier());
        }

        @Override public String visitBitAndExpression(JavaScriptParser.BitAndExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            // exprBitAnd :  expression '&' expression
            return visit(ctx.singleExpression(0)) + " & " + visit(ctx.singleExpression(1));
        }

        @Override public String visitBitOrExpression(JavaScriptParser.BitOrExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            // exprBitAnd :  expression '|' expression
            return visit(ctx.singleExpression(0)) + " | " + visit(ctx.singleExpression(1));
        }

        @Override public String visitAssignmentOperatorExpression(JavaScriptParser.AssignmentOperatorExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.getText();
        }

        @Override public String visitVoidExpression(JavaScriptParser.VoidExpressionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //    | Void singleExpression
            ASNode voidNode = new ASNode(ASNode.Type.VOID);
            voidNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(voidNode);
            AST.addEdge(parentStack.peek(), voidNode);
            ASNode block = new ASNode(ASNode.Type.BLOCK);
            block.setLineOfCode(ctx.singleExpression().getStart().getLine());
            AST.addVertex(block);
            AST.addEdge(voidNode, block);
            parentStack.push(block);
            visit(ctx.singleExpression());
            parentStack.pop();
            return "";
        }

        @Override public String visitCoalesceExpression(JavaScriptParser.CoalesceExpressionContext ctx) {
            //    | singleExpression '??' singleExpression                                # CoalesceExpression
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visit(ctx.singleExpression(0)) + "??" + visit(ctx.singleExpression(1));
        }

        @Override public String visitAssignable(JavaScriptParser.AssignableContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.identifier() != null) {
                return ctx.identifier().getText();
            } else if (ctx.arrayLiteral() != null) {
                return ctx.arrayLiteral().getText();
            }
            return ctx.objectLiteral().getText();
        }

        @Override public String visitObjectLiteral(JavaScriptParser.ObjectLiteralContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("{");
            stringBuffer.append(visit(ctx.propertyAssignment(0)));
            for (int i = 1; i < ctx.propertyAssignment().size(); i++) {
                stringBuffer.append(",");
                stringBuffer.append(visit(ctx.propertyAssignment(i)));
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        }


        @Override public String visitFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return visitFunctionDeclaration(ctx.functionDeclaration());
        }

        @Override public String visitAnonymousFunctionDecl(JavaScriptParser.AnonymousFunctionDeclContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.Async() != null) {
                stringBuffer.append(ctx.Async().getText() + " ");
            }
            stringBuffer.append(ctx.Function_() + "*(");
            stringBuffer.append(visitFormalParameterList(ctx.formalParameterList()) + ")");
            stringBuffer.append(visitFunctionBody(ctx.functionBody()));
            return stringBuffer.toString();
        }

        @Override public String visitArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //TODO
            StringBuffer stringBuffer = new StringBuffer();
            if (ctx.Async() != null) {
                stringBuffer.append(ctx.Async().getText() + " ");
            }
            stringBuffer.append(visitArrowFunctionParameters(ctx.arrowFunctionParameters()));
            stringBuffer.append("=>");
            stringBuffer.append(visitArrowFunctionBody(ctx.arrowFunctionBody()));
            return stringBuffer.toString();
        }

        @Override public String visitArrowFunctionParameters(JavaScriptParser.ArrowFunctionParametersContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.identifier() != null) {
                return ctx.identifier().getText();
            }
            return visitFormalParameterList(ctx.formalParameterList());
        }

        @Override public String visitArrowFunctionBody(JavaScriptParser.ArrowFunctionBodyContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.singleExpression() != null) {
                return visit(ctx.singleExpression());
            }
            return visitFunctionBody(ctx.functionBody());
        }

        @Override public String visitAssignmentOperator(JavaScriptParser.AssignmentOperatorContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            return ctx.getText();
        }

        @Override public String visitLiteral(JavaScriptParser.LiteralContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.NullLiteral() != null) {
                return ctx.NullLiteral().getText();
            } else if (ctx.BooleanLiteral() != null) {
                return ctx.BooleanLiteral().getText();
            } else if (ctx.RegularExpressionLiteral() != null) {
                return ctx.RegularExpressionLiteral().getText();
            } else if (ctx.templateStringLiteral() != null) {
                return visitTemplateStringLiteral(ctx.templateStringLiteral());
            } else if (ctx.numericLiteral() != null) {
                return visitNumericLiteral(ctx.numericLiteral());
            }
            return visitBigintLiteral(ctx.bigintLiteral());
        }

        @Override public String visitTemplateStringLiteral(JavaScriptParser.TemplateStringLiteralContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(ctx.BackTick().get(0).getText() + " ");
            for (JavaScriptParser.TemplateStringAtomContext atomContent : ctx.templateStringAtom()) {
                stringBuffer.append(visitTemplateStringAtom(atomContent));
                stringBuffer.append(" ");
            }
            for (int i = 1; i < ctx.BackTick().size(); i++) {
                stringBuffer.append(ctx.BackTick().get(i).getText() + " ");
            }
            return stringBuffer.toString();
        }

        @Override public String visitTemplateStringAtom(JavaScriptParser.TemplateStringAtomContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.TemplateStringAtom() != null) {
                return ctx.TemplateStringAtom().getText();
            }
            return ctx.TemplateStringStartExpression().getText() + " " + visit(ctx.singleExpression()) + " " + ctx.TemplateCloseBrace().getText();
        }

        @Override public String  visitNumericLiteral(JavaScriptParser.NumericLiteralContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.DecimalLiteral() != null) {
                return ctx.DecimalLiteral().getText();
            } else if (ctx.HexIntegerLiteral() != null) {
                return ctx.HexIntegerLiteral().getText();
            } else if (ctx.OctalIntegerLiteral() != null) {
                return ctx.OctalIntegerLiteral().getText();
            } else if (ctx.OctalIntegerLiteral2() != null) {
                return ctx.OctalIntegerLiteral2().getText();
            }
            return ctx.BinaryIntegerLiteral().getText();
        }

        @Override public String visitBigintLiteral(JavaScriptParser.BigintLiteralContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.BigBinaryIntegerLiteral() != null) {
                return ctx.BigBinaryIntegerLiteral().getText();
            } else if (ctx.BigHexIntegerLiteral() != null) {
                return ctx.BigHexIntegerLiteral().getText();
            } else if (ctx.BigOctalIntegerLiteral() != null) {
                return ctx.BigOctalIntegerLiteral().getText();
            }
            return ctx.BigBinaryIntegerLiteral().getText();
        }

        @Override public String visitGetter(JavaScriptParser.GetterContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //TODO
            //    : {this.n("get")}? identifier propertyName
            return visitIdentifier(ctx.identifier()) + " " + visitPropertyName(ctx.propertyName());
        }

        @Override public String visitSetter(JavaScriptParser.SetterContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            //TODO
            //       : {this.n("set")}? identifier propertyName
            return visitIdentifier(ctx.identifier()) + " " + visitPropertyName(ctx.propertyName());
        }

        @Override public String visitIdentifierName(JavaScriptParser.IdentifierNameContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.identifier() != null) {
                return visitIdentifier(ctx.identifier());
            }
            return visitReservedWord(ctx.reservedWord());
        }

        @Override public String visitIdentifier(JavaScriptParser.IdentifierContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.Identifier() != null) {
                return ctx.Identifier().getText();
            } else if (ctx.NonStrictLet() != null) {
                return ctx.NonStrictLet().getText();
            } else if (ctx.Async() != null) {
                return ctx.Async().getText();
            }
            return ctx.As().getText();
        }

        @Override public String visitReservedWord(JavaScriptParser.ReservedWordContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.keyword() != null) {
                return visitKeyword(ctx.keyword());
            } else if (ctx.NullLiteral() != null) {
                return ctx.NullLiteral().getText();
            } else if (ctx.BooleanLiteral() != null) {
                return ctx.BooleanLiteral().getText();
            }
            return "";
        }

        @Override public String visitKeyword(JavaScriptParser.KeywordContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
            if (ctx.let_() != null && !ctx.let_().isEmpty()) {
                return visitLet_(ctx.let_());
            }
            return getOriginalCodeText(ctx);
        }

        @Override public String visitLet_(JavaScriptParser.Let_Context ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
//            if (ctx.NonStrictLet()!=null){
//                return ctx.NonStrictLet().getText();
//            }
//            return ctx.StrictLet().getText();
            return getOriginalCodeText(ctx);
        }

        @Override public String visitEos(JavaScriptParser.EosContext ctx) {
            if (ctx == null || ctx.isEmpty()) {
                return "";
            }
//            return "";
            if (ctx.SemiColon() != null) {
                return ctx.SemiColon().getText();
            } else if (ctx.EOF() != null) {
                return ctx.EOF().getText();
            }
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
            if (ctx == null || ctx.isEmpty()) {
                return;
            }
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
