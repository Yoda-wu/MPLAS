package com.scut.mplas.cpp;

import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 *  A utility class for building CppClass and CppMethod structures from a given Cpp source file.
 *  解析出来的class信息和function信息会保留到传进去的参数中
 */
public class CppExtractor {

    public static void extractInfo(String cppFile,List<CppClass> classs,List<CppMethod> functions) throws IOException {
        extractInfo(new File(cppFile),classs,functions);
    }

    public static void extractInfo(File cppFile,List<CppClass> classs,List<CppMethod> functions) throws IOException {
        extractInfo(cppFile.getAbsolutePath(), new FileInputStream(cppFile),classs,functions);
    }

    public static void extractInfo(String cppFilePath, InputStream inStream,List<CppClass> classs,List<CppMethod> functions) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(inStream);
        CppLexer lexer = new CppLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CppParser parser = new CppParser(tokens);
        ParseTree tree = parser.translationUnit();
        extractInfo(cppFilePath, tree,classs,functions);
    }

    public static void extractInfo(String cppFilePath, ParseTree tree,List<CppClass> classs,List<CppMethod> functions) {
        CppVisitor visitor = new CppVisitor(cppFilePath);
        visitor.build(tree,classs,functions);
    }

    // TODO: 2023/4/23 待完成对CppClass和CppNonFunction的分析 
    private static class CppVisitor extends CppBaseVisitor<String>
    {
        private String filePath;
        private List<CppClass> cppClasses;
        private List<CppMethod> cppFunctions;
        private Deque<CppClass> activeClasses;

        public CppVisitor(String path) {
            filePath = path;
        }

        public void build(ParseTree tree,List<CppClass> classs,List<CppMethod> functions)
        {
            cppClasses=classs;
            cppFunctions=functions;
            activeClasses=new ArrayDeque<>();
            visit(tree);
        }


    }

}
