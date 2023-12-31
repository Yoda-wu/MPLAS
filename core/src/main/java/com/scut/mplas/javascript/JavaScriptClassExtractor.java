package com.scut.mplas.javascript;

import com.scut.mplas.javascript.parser.JavaScriptBaseVisitor;
import com.scut.mplas.javascript.parser.JavaScriptLexer;
import com.scut.mplas.javascript.parser.JavaScriptParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JavaScriptClassExtractor {

    public static List<JavaScriptClass> extractInfo(String jsFile) throws IOException {
        return extractInfo(new File(jsFile));
    }

    public static List<JavaScriptClass> extractInfo(File jsFile) throws IOException {
        return extractInfo(jsFile.getAbsolutePath(), new FileInputStream(jsFile));
    }

    public static List<JavaScriptClass> extractInfo(String jsFilePath, InputStream inStream) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(inStream);
        JavaScriptLexer lexer = new JavaScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();
        return extractInfo(jsFilePath, tree);
    }

    public static List<JavaScriptClass> extractInfo(String jsFilePath, ParseTree tree) {
        JavaScriptClassVisitor visitor = new JavaScriptClassVisitor(jsFilePath);
        return visitor.build(tree);
    }

    public static void extractInfo(String jsFilePath, ParseTree tree, List<JavaScriptClass> classs, List<JavaScriptMethod> functions) {
        JavaScriptClassVisitor visitor = new JavaScriptClassVisitor(jsFilePath);
        visitor.build(tree, classs, functions);
    }


    public static List<JavaScriptClass> extractJavaScriptLangInfo() throws IOException {
        ZipFile zip = new ZipFile("lib/src.zip");
        ArrayList<JavaScriptClass> jsLangClasses = new ArrayList<>();
        String qualifiedName = "java.lang.*";
        for (ZipEntry ent : getPackageEntries(zip, qualifiedName))
            jsLangClasses.addAll(JavaScriptClassExtractor.extractInfo("src.zip/" + ent.getName(), zip.getInputStream(ent)));
        return jsLangClasses;
    }

    public static List<JavaScriptClass> extractImportsInfo(String[] imports) throws IOException {
        ZipFile zip = new ZipFile("lib/src.zip");
        ArrayList<JavaScriptClass> classes = new ArrayList<>();
        for (String qualifiedName : imports) {
            if (qualifiedName.endsWith(".*")) {
                for (ZipEntry ent : getPackageEntries(zip, qualifiedName))
                    classes.addAll(JavaScriptClassExtractor.extractInfo("src.zip/" + ent.getName(), zip.getInputStream(ent)));
            } else {
                ZipEntry entry = getZipEntry(zip, qualifiedName);
                if (entry == null)
                    continue;
                classes.addAll(JavaScriptClassExtractor.extractInfo("src.zip/" + entry.getName(), zip.getInputStream(entry)));
            }
        }
        return classes;
    }

    private static ZipEntry getZipEntry(ZipFile zip, String qualifiedName) {
        // qualifiedName does not end with ".*"
        return zip.getEntry(qualifiedName.replace('.', '/') + ".java");
    }

    private static ZipEntry[] getPackageEntries(ZipFile zip, String qualifiedName) {
        // qualifiedName ends with ".*"
        String pkg = qualifiedName.replace('.', '/').substring(0, qualifiedName.length() - 1);
        int slashCount = countSlashes(pkg);
        ArrayList<ZipEntry> entries = new ArrayList<>();
        Enumeration<? extends ZipEntry> zipEntries = zip.entries();
        while (zipEntries.hasMoreElements()) {
            ZipEntry entry = zipEntries.nextElement();
            if (entry.getName().startsWith(pkg)
                    && !entry.isDirectory()
                    && slashCount == countSlashes(entry.getName())) {
                entries.add(entry);
            }
        }
        return entries.toArray(new ZipEntry[entries.size()]);
    }

    private static int countSlashes(String str) {
        int slashCount = 0;
        for (char chr : str.toCharArray())
            if (chr == '/')
                ++slashCount;
        return slashCount;
    }
    private static class JavaScriptClassVisitor extends JavaScriptBaseVisitor<String> {

        private String filePath;
        private boolean isStatic;
        private boolean isAbstract;
        private String packageName;
        private String lastModifier;
        private List<String> importsList;
        private List<JavaScriptClass> jsClasses;
        private List<JavaScriptMethod> jsFunctions;
        private Deque<JavaScriptClass> activeClasses;
        private Deque<String> namespaces;
        private String specifier;
        private String type;
        private String pointOp;
        private String nestedName;
        private String varName;

        public JavaScriptClassVisitor(String path) {
            filePath = path;
        }

        public List<JavaScriptClass> build(ParseTree tree) {
            packageName = "";
            isStatic = false;
            isAbstract = false;
            jsClasses = new ArrayList<>();
            importsList = new ArrayList<>();
            activeClasses = new ArrayDeque<>();
            visit(tree);
            return jsClasses;
        }

        public void build(ParseTree tree, List<JavaScriptClass> classs, List<JavaScriptMethod> functions) {
            jsClasses = classs;
            jsFunctions = functions;
            activeClasses = new ArrayDeque<>();
            namespaces = new ArrayDeque<>();
            namespaces.push("");
            specifier = "";
            type = "";
            pointOp = "";
            nestedName = "";
            varName = "";
            clearFlags();
            visit(tree);
        }

        private void clearFlags() {
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitAliasName(JavaScriptParser.AliasNameContext ctx) {
            return visitChildren(ctx);
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
            return visitChildren(ctx);
        }

        @Override
        public String visitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
            // classDeclaration
            //    : Class identifier classTail
            //    ;
            String extend = null;
            if (ctx.classTail() != null && ctx.classTail().singleExpression() != null) {
                extend = visit(ctx.classTail().singleExpression());
            }
            String[] imports = importsList.toArray(new String[importsList.size()]);

            JavaScriptClass cls = new JavaScriptClass(ctx.identifier().getText(), packageName, extend, filePath, imports);

            activeClasses.push(cls);

            visit(ctx.classTail());
            jsClasses.add(activeClasses.pop());
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitClassTail(JavaScriptParser.ClassTailContext ctx) {
            //classTail
            //    : (Extends singleExpression)? '{' classElement* '}'
            //    ;
            //classElement
            //    : (Static | {this.n("static")}? identifier | Async)* (methodDefinition | assignable '=' objectLiteral ';')
            //    | emptyStatement_
            //    | '#'? propertyName '=' singleExpression
            //    ;

            return null;
        }


        @Override
        public String visitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {
            //methodDefinition
            //    : '*'? '#'? propertyName '(' formalParameterList? ')' functionBody
            //    | '*'? '#'? getter '(' ')' functionBody
            //    | '*'? '#'? setter '(' formalParameterList? ')' functionBody
            //    ;
            //
            //formalParameterList
            //    : formalParameterArg (',' formalParameterArg)* (',' lastFormalParameterArg)?
            //    | lastFormalParameterArg
            //    ;
            //
            //formalParameterArg
            //    : assignable ('=' singleExpression)?      // ECMAScript 6: Initialization
            //    ;
            //
            //lastFormalParameterArg                        // ECMAScript 6: Rest Parameter
            //    : Ellipsis singleExpression
            //    ;
            String name = null;
            if (ctx.propertyName() != null) {
                name = ctx.propertyName().getText();
            } else if (ctx.setter() != null) {
                name = ctx.setter().getText();
            } else {
                name = ctx.getter().getText();
            }
            String[] args = null;
            List<String> argsList = new ArrayList<>();
            if (ctx.formalParameterList() != null) {
                for (JavaScriptParser.FormalParameterArgContext param : ctx.formalParameterList().formalParameterArg()) {
                    argsList.add(visit(param.assignable()));
                }
                if (ctx.formalParameterList().lastFormalParameterArg() != null) {
                    argsList.add(visit(ctx.formalParameterList().lastFormalParameterArg().singleExpression()));
                }
            }
            if (argsList.size() > 0)
                args = argsList.toArray(new String[argsList.size()]);
            int line = ctx.getStart().getLine();
            activeClasses.peek().addMethod(new JavaScriptMethod(lastModifier, isStatic, isAbstract, type, name, args, line));
            return null;
        }

        @Override
        public String visitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) {
            return ctx.getText();
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         */
        @Override
        public String visitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) {
            return visitChildren(ctx);
        }

    }
}
