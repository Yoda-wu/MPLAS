/*** In The Name of Allah ***/
package com.scut.mplas.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.scut.mplas.graphs.pdg.ControlDependenceGraph;
import com.scut.mplas.graphs.pdg.DataDependenceGraph;
import com.scut.mplas.graphs.pdg.ProgramDependeceGraph;
import ghaffarian.nanologger.Logger;

/**
 * Program Dependence Graph (PDG) builder for Java programs.
 * A Java parser generated via ANTLRv4 is used for this purpose.
 * This implementation is based on ANTLRv4's Visitor pattern.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class JavaPDGBuilder {
	
	/**
	 * Builds and returns Program Dependence Graphs (PDG) for each given Java file.
	 */
	public static ProgramDependeceGraph[] buildForAll(String[] javaFilePaths) throws IOException {
		File[] javaFiles = new File[javaFilePaths.length];
		for (int i = 0; i < javaFiles.length; ++i)
			javaFiles[i] = new File(javaFilePaths[i]);
		return buildForAll(javaFiles);
	}
	
	/**
	 * Builds and returns Program Dependence Graphs (PDG) for each given Java file.
	 */
	public static ProgramDependeceGraph[] buildForAll(File[] javaFiles) throws IOException {
		
		ControlDependenceGraph[] ctrlSubgraphs;
		ctrlSubgraphs = new ControlDependenceGraph[javaFiles.length];
		for (int i = 0; i < javaFiles.length; ++i)
			ctrlSubgraphs[i] = JavaCDGBuilder.build(javaFiles[i]);
        //
		DataDependenceGraph[] dataSubgraphs;
		dataSubgraphs = JavaDDGBuilder.buildForAll(javaFiles);
        //
		// Join the subgraphs into PDGs
		ProgramDependeceGraph[] pdgArray = new ProgramDependeceGraph[javaFiles.length];
		for (int i = 0; i < javaFiles.length; ++i) {
			pdgArray[i] = new ProgramDependeceGraph(javaFiles[i].getName(), 
					ctrlSubgraphs[i], dataSubgraphs[i]);
		}
		
		return pdgArray;
	}

	public static ProgramDependeceGraph buildForOne(String fileName, InputStream inputStream) throws IOException {
		DataDependenceGraph dataSubgraphs;
		dataSubgraphs=JavaDDGBuilder.buildForOne(fileName,inputStream);
		//
		// Join the subgraphs into PDGs
		ProgramDependeceGraph pdgArray = new ProgramDependeceGraph(fileName, null, dataSubgraphs);
		return pdgArray;
	}
}

