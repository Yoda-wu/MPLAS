/*** In The Name of Allah ***/
package com.scut.mplas.java;

import com.scut.mplas.Execution;
import com.scut.mplas.Main;
import com.scut.mplas.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Tests for different types of analyses using basic test-cases.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class BasicAnalysisTests {

    private final String JAVA_SRC_DIR = "src/test/resources/java/basic/";
    private final String CPP_SRC_DIR = "src/test/resources/cpp/basic/";
    private final String JS_SRC_DIR = "src/test/resources/js/basic/";
    private final String RUBY_SRC_DIR = "src/test/resources/ruby/basic";
    private static final String OUTPUT_DIR = "../generate/java/basic/";
    private static final String CPP_OUTPUT_DIR = "../generate/cpp/basic/";
    private static final String JS_OUTPUT_DIR = "../generate/js/basic/";
    private static final String RUBY_OUTPUT_DIR = "../generate/ruby/basic/";

    @BeforeClass
    public static void cleanUp() {
        File out = new File(OUTPUT_DIR);
        if (out.exists()) {
            for (File file : out.listFiles()) {
                if (file.isFile())
                    file.delete();
                else
                    deleteDir(file);
            }
        }
    }

    private static void deleteDir(File dir) {
        if (dir.list().length > 0) {
            for (File file : dir.listFiles()) {
                if (file.isFile())
                    file.delete();
                else
                    deleteDir(file);
            }
        }
        dir.delete();
    }

    @Test
    public void javaASTreeDotTest() {
        String outDir = OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-AST.dot");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void javaCFGDotTest() {
        String outDir = OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-CFG.dot");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void javaPDGDotTest() {
        String outDir = OUTPUT_DIR + "PDG/";
        String[] args = {"-pdg", "-outdir", outDir, JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outDataFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-PDG-DATA.dot");
        String[] outCtrlFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-PDG-CTRL.dot");
        assertEquals(testFiles.length, outDataFiles.length);
        assertEquals(testFiles.length, outCtrlFiles.length);
    }

    @Test
    public void javaASTreeGmlTest() {
        String outDir = OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "gml", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-AST.gml");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void javaCFGGmlTest() {
        String outDir = OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "gml", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-CFG.gml");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void javaPDGGmlTest() {
        String outDir = OUTPUT_DIR + "PDG/";
        String[] args = {"-pdg", "-outdir", outDir, "-format", "gml", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outDataFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-PDG-DATA.gml");
        String[] outCtrlFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-PDG-CTRL.gml");
        assertEquals(testFiles.length, outDataFiles.length);
        assertEquals(testFiles.length, outCtrlFiles.length);
    }

    @Test
    public void javaASTreeJsonTest() {
        String outDir = OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "json", JAVA_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-AST.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void javaCFGJsonTest() {
        String outDir = OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "json", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void javaPDGJsonTest() {
        String outDir = OUTPUT_DIR + "PDG/";
        String[] args = {"-pdg", "-outdir", outDir, "-format", "json", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outDataFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-PDG-DATA.json");
        String[] outCtrlFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-PDG-CTRL.json");
        assertEquals(testFiles.length, outDataFiles.length);
        assertEquals(testFiles.length, outCtrlFiles.length);
    }

    @Test
    public void cppASTreeJsonTest() {
        String outDir = CPP_OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "json", "-lang", "cpp", CPP_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{CPP_SRC_DIR}, Execution.Languages.CPP.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-AST.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void cppCFGJsonTest() {
        String outDir = CPP_OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "json", "-lang", "cpp", CPP_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{CPP_SRC_DIR}, Execution.Languages.CPP.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void cppDDGJsonTest() {
        String outDir = CPP_OUTPUT_DIR + "DDG/";
        String[] args = {"-ddg", "-outdir", outDir, "-format", "json", "-lang", "cpp", CPP_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{CPP_SRC_DIR}, Execution.Languages.CPP.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-PDG-DATA.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void jsASTreeJsonTest() {
        String outDir = JS_OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "json", "-lang", "js", JS_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JS_SRC_DIR}, Execution.Languages.JAVASCRIPT.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-AST.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void jsCFGJsonTest() {
        String outDir = JS_OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "json", "-lang", "js", JS_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{JS_SRC_DIR}, Execution.Languages.JAVASCRIPT.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }


    @Test
    public void RubyASTreeJsonTest() {
        String outDir = RUBY_OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "json", "-lang", "ruby", RUBY_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{RUBY_SRC_DIR}, Execution.Languages.RUBY.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-AST.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void RubyCFGJsonTest() {
        String outDir = RUBY_OUTPUT_DIR + "AST/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "json", "-lang", "ruby", RUBY_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{RUBY_SRC_DIR}, Execution.Languages.RUBY.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void RubyCFGDotTest() {
        String outDir = RUBY_OUTPUT_DIR + "AST/";
        String[] args = {"-cfg", "-outdir", outDir, "-lang", "ruby", RUBY_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[]{RUBY_SRC_DIR}, Execution.Languages.RUBY.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[]{outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }
}
