package com.scut.mplas.cpp;
/*** In The Name of Allah ***/


import com.scut.mplas.cpp.parser.CppBaseVisitor;
import com.scut.mplas.cpp.parser.CppLexer;
import com.scut.mplas.cpp.parser.CppParser;
import com.scut.mplas.graphs.cfg.CFNode;
import com.scut.mplas.graphs.cfg.CFPathTraversal;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.graphs.pdg.DDEdge;
import com.scut.mplas.graphs.pdg.DataDependenceGraph;
import com.scut.mplas.graphs.pdg.PDNode;
import com.scut.mplas.java.*;
import com.scut.mplas.java.parser.JavaLexer;
import com.scut.mplas.java.parser.JavaParser;
import ghaffarian.graphs.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import ghaffarian.nanologger.Logger;
/**
 * Data Dependence Graph (DDG) builder for Cpp programs.
 * The DDG is actually a subgraph of the Program Dependence Graph (PDG).
 * This implementation is based on ANTLRv4's Visitor pattern.
 *
 */
public class CppDDGBuilder {

    // Just for debugging
    private static String currentFile;

    // NOTE: This doesn't handle duplicate class names;
    //       yet assuming no duplicate class names is fair enough.
    //       To handle that, we should use 'Map<String, List<JavaClass>>'
   // private static Map<String, JavaClass> allClassInfos;

    //private static Map<String, List<MethodDefInfo>> methodDEFs;

    public static DataDependenceGraph[] buildForAll(File[] files) throws IOException {
        // Parse all Java source files
        Logger.info("Parsing all source files ... ");
        ParseTree[] parseTrees = new ParseTree[files.length];
        for (int i = 0; i < files.length; ++i) {
            InputStream inFile = new FileInputStream(files[i]);
            ANTLRInputStream input = new ANTLRInputStream(inFile);
            JavaLexer lexer = new JavaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            parseTrees[i] = parser.compilationUnit();
        }
        Logger.info("Done.");
        //
        return null;
    }

}

