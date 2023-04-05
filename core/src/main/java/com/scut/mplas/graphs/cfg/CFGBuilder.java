/*** In The Name of Allah ***/
package com.scut.mplas.graphs.cfg;

import java.io.IOException;
import com.scut.mplas.java.JavaCFGBuilder;

/**
 * Control Flow Graph (CFG) Builder.
 * This class invokes the appropriate builder based on the given language parameter.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class CFGBuilder {
	
	/**
	 * Build and return the CFG of the given source code file with specified language.
	 */
	public static ControlFlowGraph build(String lang, String srcFilePath) throws IOException {
		switch (lang) {
			case "C":
				return null;
			//
			case "Java":
				return JavaCFGBuilder.build(srcFilePath);
			//
			case "Python":
				return null;
			//
			default:
				return null;
		}
	}
	
}
