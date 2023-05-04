/*** In The Name of Allah ***/
package com.scut.mplas.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut.mplas.Execution;
import com.scut.mplas.Main;
import com.scut.mplas.utils.FileUtils;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Tests for different types of analyses using basic test-cases.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class BasicAnalysisTests {
    
    private final String JAVA_SRC_DIR = "src/test/resources/java/basic/";
    private final String CPP_SRC_DIR="src/test/resources/cpp/basic/";
    private final String JS_SRC_DIR="src/test/resources/js/basic/";
    private static final String OUTPUT_DIR = "../generate/java/basic/";
    private static final String CPP_OUTPUT_DIR="../generate/cpp/basic/";
    private static final String JS_OUTPUT_DIR="../generate/js/basic/";
    
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
            for (File file: dir.listFiles()) {
                if (file.isFile())
                    file.delete();
                else
                    deleteDir(file);
            }
        }
        dir.delete();
    }

    // 将指定的目录下的所有代码文件，转换为一个jsonl，每一个源代码文件对应生成的jsonl文件中的一条json对象
    // 每一个代码文件都会转换为一个json对象,json对象格式如下：
    // { test：“文件名”，code：“源代码文件里的文本”}
    private static final String CPP_JSONL_DIR="src/test/resources/cpp/testJSONL/";
    private static final String CPP_SUFFIX=".cpp";

    private static final String JS_JSONL_DIR="src/test/resources/js/testJSONL/";
    private static final String JS_SUFFIX=".js";

    private final String JAVA_JSONL_DIR = "src/test/resources/java/testJSONL/";
    private final String JAVA_SUFFIX=".java";

    @Test
    public void produceJSONL() throws IOException {
        // 源代码文件所在目录
        String inputDir="";
        // 生成的jsonl文件输出目录
        String outDir="";
        // 指定源代码文件的后缀名
        String suffix="";

        //String lang="cpp";
        //String lang="java";
        String lang="js";
        //String lang="ruby";
        switch(lang)
        {
            case "cpp":
                inputDir=CPP_SRC_DIR;
                outDir=CPP_JSONL_DIR;
                suffix=CPP_SUFFIX;
                break;
            case "java":
                inputDir=JAVA_SRC_DIR;
                outDir=JAVA_JSONL_DIR;
                suffix=JAVA_SUFFIX;
                break;
            case "js":
                inputDir=JS_SRC_DIR;
                outDir=JS_JSONL_DIR;
                suffix=JS_SUFFIX;
                break;
            case "ruby":
                break;
            default:
                return;
        }

        String[] filePaths=new String[0];
        filePaths=FileUtils.listFilesWithSuffix(new String[]{inputDir},suffix);

        String outputName=outDir+lang+"Test.jsonl";
        File outputFile=new File(outputName);
        FileWriter writer=new FileWriter(outputFile);

        for(String srcFile:filePaths)
        {
            File f=new File(srcFile);
            Map<String,String> srcJson=new HashMap<>();
            Path path = Paths.get(srcFile);
            String content = Files.readString(path, StandardCharsets.UTF_8);
            srcJson.put("text",f.getName());
            srcJson.put("code",content);

            // 创建 ObjectMapper 对象
            ObjectMapper mapper = new ObjectMapper();
            // 将 Map 对象转换为 JSON 字符串
            String json = mapper.writeValueAsString(srcJson);
            writer.write(json+"\n");
        }
        writer.close();
    }
    
    @Test
    public void javaASTreeDotTest() {
        String outDir = OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-AST.dot");
        assertEquals(testFiles.length, outFiles.length);
    }
    
    @Test
    public void javaCFGDotTest() {
        String outDir = OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-CFG.dot");
        assertEquals(testFiles.length, outFiles.length);
    }
    
    @Test
    public void javaPDGDotTest() {
        String outDir = OUTPUT_DIR + "PDG/";
        String[] args = {"-pdg", "-outdir", outDir, JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outDataFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-PDG-DATA.dot");
        String[] outCtrlFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-PDG-CTRL.dot");
        assertEquals(testFiles.length, outDataFiles.length);
        assertEquals(testFiles.length, outCtrlFiles.length);
    }
    
    @Test
    public void javaASTreeGmlTest() {
        String outDir = OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "gml", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-AST.gml");
        assertEquals(testFiles.length, outFiles.length);
    }
    
    @Test
    public void javaCFGGmlTest() {
        String outDir = OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "gml", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-CFG.gml");
        assertEquals(testFiles.length, outFiles.length);
    }
    
    @Test
    public void javaPDGGmlTest() {
        String outDir = OUTPUT_DIR + "PDG/";
        String[] args = {"-pdg", "-outdir", outDir, "-format", "gml", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outDataFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-PDG-DATA.gml");
        String[] outCtrlFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-PDG-CTRL.gml");
        assertEquals(testFiles.length, outDataFiles.length);
        assertEquals(testFiles.length, outCtrlFiles.length);
    }
    
    @Test
    public void javaASTreeJsonTest() {
        String outDir = OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "json", JAVA_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-AST.json");
        assertEquals(testFiles.length, outFiles.length);
    }
    
    @Test
    public void javaCFGJsonTest() {
        String outDir = OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "json", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }
    
    @Test
    public void javaPDGJsonTest() {
        String outDir = OUTPUT_DIR + "PDG/";
        String[] args = {"-pdg", "-outdir", outDir, "-format", "json", JAVA_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JAVA_SRC_DIR}, Execution.Languages.JAVA.suffix);
        String[] outDataFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-PDG-DATA.json");
        String[] outCtrlFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-PDG-CTRL.json");
        assertEquals(testFiles.length, outDataFiles.length);
        assertEquals(testFiles.length, outCtrlFiles.length);
    }

    @Test
    public void cppASTreeJsonTest() {
        String outDir = CPP_OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "json","-lang","cpp", CPP_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {CPP_SRC_DIR}, Execution.Languages.CPP.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-AST.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void cppCFGJsonTest() {
        String outDir = CPP_OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "json", "-lang","cpp",CPP_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {CPP_SRC_DIR}, Execution.Languages.CPP.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void cppDDGJsonTest() {
        String outDir = CPP_OUTPUT_DIR + "DDG/";
        //String CPP_SRC_DIR="src/test/resources/cpp/test/";
        String[] args = {"-ddg", "-outdir", outDir, "-format", "json", "-lang","cpp",CPP_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {CPP_SRC_DIR}, Execution.Languages.CPP.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-PDG-DATA.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void jsASTreeJsonTest() {
        String outDir = JS_OUTPUT_DIR + "AST/";
        String[] args = {"-ast", "-outdir", outDir, "-format", "json","-lang","js", JS_SRC_DIR};
        Main.main(args);
        //

        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JS_SRC_DIR}, Execution.Languages.JAVASCRIPT.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-AST.json");
        assertEquals(testFiles.length, outFiles.length);
    }

    @Test
    public void jsCFGJsonTest() {
        String outDir = JS_OUTPUT_DIR + "CFG/";
        String[] args = {"-cfg", "-outdir", outDir, "-format", "json", "-lang","js",JS_SRC_DIR};
        Main.main(args);
        //
        String[] testFiles = FileUtils.listFilesWithSuffix(new String[] {JS_SRC_DIR}, Execution.Languages.JAVASCRIPT.suffix);
        String[] outFiles = FileUtils.listFilesWithSuffix(new String[] {outDir}, "-CFG.json");
        assertEquals(testFiles.length, outFiles.length);
    }

}
