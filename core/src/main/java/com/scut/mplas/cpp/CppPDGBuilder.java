package com.scut.mplas.cpp;

import com.scut.mplas.graphs.pdg.ControlDependenceGraph;
import com.scut.mplas.graphs.pdg.DataDependenceGraph;
import com.scut.mplas.graphs.pdg.ProgramDependeceGraph;

import java.io.File;
import java.io.IOException;

public class CppPDGBuilder {
    /**
     * Builds and returns Program Dependence Graphs (PDG) for each given Java file.
     */
    public static ProgramDependeceGraph[] buildForAll(String[] cppFilePaths) throws IOException {
        File[] cppFiles = new File[cppFilePaths.length];
        for (int i = 0; i < cppFiles.length; ++i)
            cppFiles[i] = new File(cppFilePaths[i]);
        return buildForAll(cppFiles);
    }

    /**
     * Builds and returns Program Dependence Graphs (PDG) for each given Java file.
     */
    public static ProgramDependeceGraph[] buildForAll(File[] cppFiles) throws IOException {

//        ControlDependenceGraph[] ctrlSubgraphs;
//        ctrlSubgraphs = new ControlDependenceGraph[cppFiles.length];
//        for (int i = 0; i < cppFiles.length; ++i)
//            ctrlSubgraphs[i] = CppCDGBuilder.build(cppFiles[i]);
        //
        DataDependenceGraph[] dataSubgraphs;
        dataSubgraphs = CppDDGBuilder.buildForAll(cppFiles);
        //
        // Join the subgraphs into PDGs
        ProgramDependeceGraph[] pdgArray = new ProgramDependeceGraph[cppFiles.length];
        for (int i = 0; i < cppFiles.length; ++i) {
            pdgArray[i] = new ProgramDependeceGraph(cppFiles[i].getName(),
                    null, dataSubgraphs[i]);
        }

        return pdgArray;
    }
}
