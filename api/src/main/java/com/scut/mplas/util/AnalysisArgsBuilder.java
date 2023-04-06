package com.scut.mplas.util;

import com.scut.mplas.entity.AnalysisArgs;
import com.scut.mplas.Execution;
import org.springframework.stereotype.Component;

import java.io.InputStream;
@Component
public class AnalysisArgsBuilder {
    private AnalysisArgs args;
    public  AnalysisArgsBuilder (){
        args = new AnalysisArgs();
    }
    public AnalysisArgs build(){
        args.formats = Execution.Formats.JSON;
        return  args;
    }
    public AnalysisArgsBuilder setLanguageType(Execution.Languages languageType){
        args.lang = languageType;
        return this;
    }
    public AnalysisArgsBuilder setAnalysisOpt(Execution.Analysis opt){
        args.opt = opt;
        return this;
    }
    public AnalysisArgsBuilder setFileName(String fileName){
        args.fileName = fileName;
        return this;
    }
    public AnalysisArgsBuilder setInputStream(InputStream stream){
        args.inputStream = stream;
        return this;
    }
}
