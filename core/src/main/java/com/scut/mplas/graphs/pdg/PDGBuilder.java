/*** In The Name of Allah ***/
package com.scut.mplas.graphs.pdg;

import java.io.IOException;
import java.io.InputStream;

import com.scut.mplas.cpp.CppPDGBuilder;
import com.scut.mplas.java.JavaPDGBuilder;

/**
 * Program Dependence Graph (PDG) Builder.
 * This class invokes the appropriate builder based on the given language parameter.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class PDGBuilder {
	public static ProgramDependeceGraph buildForOne(String lang, String fileName, InputStream inputStream) throws IOException {
		switch (lang) {
			case "Cpp":
				return CppPDGBuilder.buildForOne(fileName,inputStream);
			//
			case "C":
				return null;
			//
			case "Java":
				return null;
			//
			case "Python":
				return null;
			//
			default:
				return null;
		}
	}
	
	public static ProgramDependeceGraph[] buildForAll(String lang, String[] srcFilePaths) throws IOException {
		switch (lang) {
			case "Cpp":
				return CppPDGBuilder.buildForAll(srcFilePaths);
			//
			case "C":
				return null;
			//
			case "Java":
				return JavaPDGBuilder.buildForAll(srcFilePaths);
			//
			case "Python":
				return null;
			//
			default:
				return null;
		}
	}
	
}
