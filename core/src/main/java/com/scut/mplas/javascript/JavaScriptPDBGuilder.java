package com.scut.mplas.javascript;

import com.scut.mplas.graphs.pdg.DataDependenceGraph;
import com.scut.mplas.graphs.pdg.ProgramDependeceGraph;

import java.io.File;
import java.io.IOException;

public class JavaScriptPDBGuilder {
    /**
     * Builds and returns Program Dependence Graphs (PDG) for each given JavaScript file.
     */
    public static ProgramDependeceGraph[] buildForAll(String[] jsFilePaths) throws IOException {
        File[] jsFiles = new File[jsFilePaths.length];
        for (int i = 0; i < jsFiles.length; ++i)
            jsFiles[i] = new File(jsFilePaths[i]);
        return buildForAll(jsFiles);
    }

    /**
     * Builds and returns Program Dependence Graphs (PDG) for each given JavaScript file.
     */
    public static ProgramDependeceGraph[] buildForAll(File[] jsFiles) throws IOException {

//        ControlDependenceGraph[] ctrlSubgraphs;
//        ctrlSubgraphs = new ControlDependenceGraph[cppFiles.length];
//        for (int i = 0; i < cppFiles.length; ++i)
//            ctrlSubgraphs[i] = CppCDGBuilder.build(cppFiles[i]);
        //
        DataDependenceGraph[] dataSubgraphs;
        dataSubgraphs = JavaScriptDDGBuilder.buildForAll(jsFiles);
        //
        // Join the subgraphs into PDGs
        ProgramDependeceGraph[] pdgArray = new ProgramDependeceGraph[jsFiles.length];
        for (int i = 0; i < jsFiles.length; ++i) {
            pdgArray[i] = new ProgramDependeceGraph(jsFiles[i].getName(),
                    null, dataSubgraphs[i]);
        }

        return pdgArray;
    }
}
