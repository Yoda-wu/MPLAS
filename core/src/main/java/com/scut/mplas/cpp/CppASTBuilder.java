package com.scut.mplas.cpp;

import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
import com.scut.mplas.java.JavaASTBuilder;
import com.scut.mplas.java.parser.JavaBaseVisitor;
import com.scut.mplas.java.parser.JavaLexer;
import com.scut.mplas.java.parser.JavaParser;
import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import ghaffarian.nanologger.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;


import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * Abstract Syntax Tree (AST) builder for CPP programs.
 * A CPP parser generated via ANTLRv4 is used for this purpose.
 * This implementation is based on ANTLRv4's Visitor pattern.
 */

public class CppASTBuilder {
    public static AbstractSyntaxTree build(String javaFile) throws IOException {
        return build(new File(javaFile));
    }

    /**
     * Build and return the Abstract Syntax Tree (AST) for the given Cpp source file.
     */

    public static AbstractSyntaxTree build(File jsFile) throws IOException {
        if (!jsFile.getName().endsWith(".java"))
            throw new IOException("Not a Cpp File!");
        InputStream inFile = new FileInputStream(jsFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser. translationUnit();
        return build(jsFile.getPath(), tree, null, null);
    }

    public static AbstractSyntaxTree build(String fileName, InputStream inputStream) throws IOException {

        InputStream inFile = inputStream;
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser.translationUnit();
        return build(fileName, tree, null, null);
    }

    /**
     * Build and return the Abstract Syntax Tree (AST) for the given Parse-Tree.
     * The 'ctxProps' map includes contextual-properties for particular nodes
     * in the parse-tree, which can be used for linking this graph with other
     * graphs by using the same parse-tree and the same contextual-properties.
     */

    public static AbstractSyntaxTree build(String filePath, ParseTree tree,
                                           String propKey, Map<ParserRuleContext, Object> ctxProps) {
        CppASTBuilder.AbstractSyntaxVisitor visitor = new CppASTBuilder.AbstractSyntaxVisitor(filePath, propKey, ctxProps);
        Logger.debug("Visitor building AST of: " + filePath);
        return visitor.build(tree);
    }
    /**
     * Visitor class which constructs the AST for a given ParseTree.
     */
    private static class AbstractSyntaxVisitor extends CppBaseVisitor<String> {
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
            CppParser.TranslationUnitContext rootCntx = (CppParser.TranslationUnitContext) tree;
            AST.root.setCode(new File(AST.filePath).getName());
            parentStack.push(AST.root);
            if (rootCntx.declarationseq() != null) {
                visit(rootCntx.declarationseq());
            }
            parentStack.pop();
            vars.clear();
            fields.clear();
            methods.clear();
            return AST;
        }


    }}