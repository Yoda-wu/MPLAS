/*** In The Name of Allah ***/
package com.scut.mplas.graphs.pdg;

import com.scut.mplas.cpp.CppPDGBuilder;
import com.scut.mplas.java.JavaPDGBuilder;

import java.io.IOException;

/**
 * Program Dependence Graph (PDG) Builder.
 * This class invokes the appropriate builder based on the given language parameter.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class PDGBuilder {

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
