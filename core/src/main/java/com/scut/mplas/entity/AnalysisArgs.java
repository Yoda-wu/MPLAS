package com.scut.mplas.entity;

import com.scut.mplas.Execution;

import java.io.InputStream;

public class AnalysisArgs {
    public Execution.Languages lang;
    public  Execution.Analysis opt;
    public Execution.Formats formats;

    public InputStream inputStream;

    public String fileName;

}
