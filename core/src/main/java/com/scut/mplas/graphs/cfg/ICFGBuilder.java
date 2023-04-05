/*** In The Name of Allah ***/
package com.scut.mplas.graphs.cfg;

import java.io.IOException;
import com.scut.mplas.java.JavaICFGBuilder;

/**
 * Interprocedural Control Flow Graph (ICFG) Builder.
 * This class invokes the appropriate builder based on the given language parameter.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class ICFGBuilder {
	
	/**
	 * Build and return ICFG of given source code files with specified language.
	 */
	public static ControlFlowGraph buildForAll(String lang, String[] javaFilePaths) throws IOException {
		switch (lang) {
			case "C":
				return null;
			//
			case "Java":
				return JavaICFGBuilder.buildForAll(javaFilePaths);
			//
			case "Python":
				return null;
			//
			default:
				return null;
		}
	}
}
