/*** In The Name of Allah ***/
package com.scut.mplas;

import com.scut.mplas.graphs.ast.ASTBuilder;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
import com.scut.mplas.graphs.cfg.CFGBuilder;
import com.scut.mplas.graphs.cfg.ControlFlowGraph;
import com.scut.mplas.graphs.cfg.ICFGBuilder;
import com.scut.mplas.graphs.pdg.PDGBuilder;
import com.scut.mplas.graphs.pdg.ProgramDependeceGraph;
import com.scut.mplas.java.JavaClass;
import com.scut.mplas.java.JavaClassExtractor;
import com.scut.mplas.utils.FileUtils;
import com.scut.mplas.utils.SystemUtils;
import ghaffarian.nanologger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class which holds program execution options.
 * These options determine the execution behavior of the program.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Execution {
	
	private final ArrayList<Analysis> analysisTypes;
	private final ArrayList<String> inputPaths;
    private boolean debugMode;
	private String outputDir;
	private Languages lang;
	private Formats format;

	private InputStream inputStream;
	private String fileName;

	public Execution() {
        debugMode = false;
		analysisTypes = new ArrayList<>();
		inputPaths = new ArrayList<>();
		lang = Languages.JAVA;
		format = Formats.DOT;
		outputDir = System.getProperty("user.dir");
		if (!outputDir.endsWith(File.separator))
			outputDir += File.separator;
		inputStream = null;
		fileName = "";
	}
	
	/**
	 * Enumeration of different execution options.
	 */
	public enum Analysis {
		// analysis types
		CFG("CFG"),
		PDG("PDG"),
		AST("AST"),
		DDG("DDG"),
		ICFG("ICFG"),
		SRC_INFO("INFO");

		private Analysis(String str) {
			type = str;
		}

		@Override
		public String toString() {
			return type;
		}
		public final String type;
	}

	/**
	 * Enumeration of different supported languages.
	 */
	public enum Languages {
		CPP("Cpp", ".cpp"),
		JAVASCRIPT("JavaScript", ".js"),
		C("C", ".c"),
		JAVA("Java", ".java"),
		RUBY("Ruby", ".rb"),
		PYTHON("Python", ".py");

		private Languages(String str, String suffix) {
			name = str;
			this.suffix = suffix;
		}

		@Override
		public String toString() {
			return name;
		}

		public final String name;
		public final String suffix;
	}
	
	/**
	 * Enumeration of different supported output formats.
	 */
	public enum Formats {
		DOT, GML, JSON
	}
	
	
	/*=======================================================*/
	public void setInputStream(InputStream inputStream){
		this.inputStream = inputStream;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public void addAnalysisOption(Analysis opt) {
		analysisTypes.add(opt);
	}
	
	public void addInputPath(String path) {
		inputPaths.add(path);
	}
	
	public void setLanguage(Languages lang) {
		this.lang = lang;
	}
    
    public void setDebugMode(boolean isDebug) {
        debugMode = isDebug;
    }
	
	public void setOutputFormat(Formats fmt) {
		format = fmt;
	}
	
	public boolean setOutputDirectory(String outPath) {
        if (!outPath.endsWith(File.separator))
            outPath += File.separator;
		File outDir = new File(outPath);
        outDir.mkdirs();
		if (outDir.exists()) {
			if (outDir.canWrite() && outDir.isDirectory()) {
				outputDir = outPath;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("PROGEX execution config:");
		str.append("\n  Language = ").append(lang);
		str.append("\n  Output format = ").append(format);
		str.append("\n  Output directory = ").append(outputDir);
		str.append("\n  Analysis types = ").append(Arrays.toString(analysisTypes.toArray()));
		str.append("\n  File name = ").append(fileName);
		str.append("\n  Input paths = \n");
		for (String path: inputPaths)
			str.append("        ").append(path).append('\n');
		return str.toString();
	}
	/**
	 * Execute the PROGEX program with the given options and return a JSON string
	 */
	public String ExecuteForAPI(){
		Logger.info(toString());
		for (Analysis analysis: analysisTypes) {

			Logger.debug("\nMemory Status");
			Logger.debug("=============");
			Logger.debug(SystemUtils.getMemoryStats());

			switch (analysis.type) {
				//
				case "AST":
					Logger.info("\nAbstract Syntax Analysis");
					Logger.info("========================");
					Logger.debug("START: " + Logger.time() + '\n');

					try {
						AbstractSyntaxTree ast = ASTBuilder.build(lang.name, fileName,inputStream);
						return  ast.exportJson();
					} catch (IOException ex) {
						Logger.error(ex);
					}
					break;
				//
				case "CFG":
					Logger.info("\nControl-Flow Analysis");
					Logger.info("=====================");
					Logger.debug("START: " + Logger.time() + '\n');
					try {
						ControlFlowGraph cfg = CFGBuilder.build(lang.name, fileName,inputStream);
						return cfg.exportJson();
					} catch (IOException ex) {
						Logger.error(ex);
					}
					break;
				//
				case "ICFG":
//					Logger.info("\nInterprocedural Control-Flow Analysis");
//					Logger.info("=====================================");
//					Logger.debug("START: " + Logger.time() + '\n');
//					try {
//						ControlFlowGraph icfg = ICFGBuilder.buildForAll(lang.name, filePaths);
//						icfg.export(format.toString(), outputDir);
//					} catch (IOException ex) {
//						Logger.error(ex);
//					}
//					break;
				//
				case "PDG":
					Logger.info("\nProgram-Dependence Analysis");
					Logger.info("===========================");
					Logger.debug("START: " + Logger.time() + '\n');
//					try {
//						for (ProgramDependeceGraph pdg: PDGBuilder.buildForAll(lang.name, filePaths)) {
//							pdg.CDS.export(format.toString(), outputDir);
//							pdg.DDS.export(format.toString(), outputDir);
//							if (debugMode) {
//								pdg.DDS.getCFG().export(format.toString(), outputDir);
//								pdg.DDS.printAllNodesUseDefs(Logger.Level.DEBUG);
//							}
//						}
//					} catch (IOException ex) {
//						Logger.error(ex);
//					}
					break;
				//
				case "INFO":
					Logger.info("\nCode Information Analysis");
					Logger.info("=========================");
					Logger.debug("START: " + Logger.time() + '\n');
//					for (String srcFile : filePaths)
//						analyzeInfo(lang.name, srcFile);
					break;
				//
				default:
					Logger.info("\n\'" + analysis.type + "\' analysis is not supported!\n");
			}
			Logger.debug("\nFINISH: " + Logger.time());
		}
		//
		Logger.debug("\nMemory Status");
		Logger.debug("=============");
		Logger.debug(SystemUtils.getMemoryStats());
		return "{}";
	}

	/**
	 * Execute the PROGEX program with the given options.
	 */
	public void execute() {
		if (inputPaths.isEmpty() && inputStream == null ) {
			Logger.info("No input  provided!\nAbort.");
			System.exit(0);
		}
		if (analysisTypes.isEmpty()) {
			Logger.info("No analysis type provided!\nAbort.");
			System.exit(0);
		}

		Logger.info(toString());
		
		// 1. Extract source files from input-paths, based on selected language
		String[] paths = inputPaths.toArray(new String[inputPaths.size()]);
		String[] filePaths = new String[0];
		if (paths.length > 0)
			filePaths = FileUtils.listFilesWithSuffix(paths, lang.suffix);
		Logger.info("\n# " + lang.name + " source files = " + filePaths.length + "\n");
		
		// Check language
		if (!(lang.equals(Languages.JAVA) || lang.equals(Languages.CPP) || lang.equals(Languages.JAVASCRIPT) || lang.equals(Languages.RUBY))) {
			Logger.info("Analysis of " + lang.name + " programs is not yet supported!");
			Logger.info("Abort.");
			System.exit(0);
		}

		// 2. For each analysis type, do the analysis and output results
		for (Analysis analysis: analysisTypes) {
			
			Logger.debug("\nMemory Status");
			Logger.debug("=============");
			Logger.debug(SystemUtils.getMemoryStats());

			switch (analysis.type) {
				//
				case "AST":
					Logger.info("\nAbstract Syntax Analysis");
					Logger.info("========================");
					Logger.debug("START: " + Logger.time() + '\n');

						for (String srcFile : filePaths) {
							try {
								AbstractSyntaxTree ast = ASTBuilder.build(lang.name, srcFile);
								ast.export(format.toString(), outputDir);
							} catch (IOException ex) {
								Logger.error(ex);
							}
						}
					break;
				//
				case "CFG":
					Logger.info("\nControl-Flow Analysis");
					Logger.info("=====================");
					Logger.debug("START: " + Logger.time() + '\n');
					for (String srcFile : filePaths) {
						try {
							ControlFlowGraph cfg = CFGBuilder.build(lang.name, srcFile);
							cfg.export(format.toString(), outputDir);
						} catch (IOException ex) {
							Logger.error(ex);
						}
					}
					break;
				//
				case "DDG":
					Logger.info("\nData-Dependence Analysis");
					Logger.info("=====================");
					Logger.debug("START: " + Logger.time() + '\n');
					try {
						for (ProgramDependeceGraph pdg : PDGBuilder.buildForAll(lang.name, filePaths)) {
							pdg.DDS.export(format.toString(), outputDir);
						}
					} catch (IOException ex) {
						Logger.error(ex);
					}
					break;
				//
				case "ICFG":
					Logger.info("\nInterprocedural Control-Flow Analysis");
					Logger.info("=====================================");
					Logger.debug("START: " + Logger.time() + '\n');
					try {
						ControlFlowGraph icfg = ICFGBuilder.buildForAll(lang.name, filePaths);
						icfg.export(format.toString(), outputDir);
					} catch (IOException ex) {
						Logger.error(ex);
					}
					break;
				//
				case "PDG":
					Logger.info("\nProgram-Dependence Analysis");
					Logger.info("===========================");
					Logger.debug("START: " + Logger.time() + '\n');
					try {
						for (ProgramDependeceGraph pdg: PDGBuilder.buildForAll(lang.name, filePaths)) {
							pdg.CDS.export(format.toString(), outputDir);
							pdg.DDS.export(format.toString(), outputDir);
                            if (debugMode) {
                                pdg.DDS.getCFG().export(format.toString(), outputDir);
                                pdg.DDS.printAllNodesUseDefs(Logger.Level.DEBUG);
                            }
						}
					} catch (IOException ex) {
						Logger.error(ex);
					}
					break;
				//
				case "INFO":
					Logger.info("\nCode Information Analysis");
					Logger.info("=========================");
					Logger.debug("START: " + Logger.time() + '\n');
					for (String srcFile : filePaths)
						analyzeInfo(lang.name, srcFile);
					break;
				//
				default:
					Logger.info("\n\'" + analysis.type + "\' analysis is not supported!\n");
			}
			Logger.debug("\nFINISH: " + Logger.time());
		}
		//
		Logger.debug("\nMemory Status");
		Logger.debug("=============");
		Logger.debug(SystemUtils.getMemoryStats());
	}
    
	private void analyzeInfo(String lang, String srcFilePath) {
		switch (lang.toLowerCase()) {
			case "c":
				return;
			//
			case "java":
				try {
					Logger.info("\n========================================\n");
					Logger.info("FILE: " + srcFilePath);
					// first extract class info
					List<JavaClass> classInfoList = JavaClassExtractor.extractInfo(srcFilePath);
					for (JavaClass classInfo : classInfoList)
						Logger.info("\n" + classInfo);
					// then extract imports info
					if (classInfoList.size() > 0) {
						Logger.info("\n- - - - - - - - - - - - - - - - - - - - -");
						String[] imports = classInfoList.get(0).IMPORTS;
						for (JavaClass importInfo : JavaClassExtractor.extractImportsInfo(imports)) 
							Logger.info("\n" + importInfo);
					}
				} catch (IOException ex) {
					Logger.error(ex);
				}
				return;
			//
			case "python":
				return;
		}
	}
}
