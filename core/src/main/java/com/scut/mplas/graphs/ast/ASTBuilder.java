/*** In The Name of Allah ***/
package com.scut.mplas.graphs.ast;

import java.io.IOException;
import java.io.InputStream;

import com.scut.mplas.cpp.CppASTBuilder;
import com.scut.mplas.java.JavaASTBuilder;
import com.scut.mplas.javascript.JavaScriptASTBuilder;
import com.scut.mplas.ruby.RubyASTBuilder;

/**
 * Abstract Syntax Tree (AST) Builder.
 * This class invokes the appropriate builder based on the given language parameter.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class ASTBuilder {
    
	/**
	 * Build and return the CFG of the given source code file with specified language.
	 */
	public static AbstractSyntaxTree build(String lang, String srcFilePath) throws IOException {
		switch (lang) {
			case "Cpp":
				return CppASTBuilder.build(srcFilePath);
			case "JavaScript":
				return JavaScriptASTBuilder.build(srcFilePath);
			//
			case "C":
				return null;
			//
			case "Java":
				return JavaASTBuilder.build(srcFilePath);
			//
			case "Python":
				return  null;
			case "Ruby":
				return RubyASTBuilder.build(srcFilePath);
			//
			default:
				return null;
		}
	}

	/**
	 * Build and return the CFG of the given source code file with specified language.
	 */
	public static AbstractSyntaxTree build(String lang, String fileName, InputStream inputStream) throws IOException {
		switch (lang) {
			case "C++":
				return CppASTBuilder.build(fileName,inputStream);
			//
			case "C":
				return null;
			//
			case "Java":
				return JavaASTBuilder.build(fileName,inputStream );
			//
			case "Python":
				return null;
			case "Ruby":
				return RubyASTBuilder.build(fileName,inputStream);
			//
			default:
				return null;
		}
	}

}
