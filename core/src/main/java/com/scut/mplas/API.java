package com.scut.mplas;

import com.scut.mplas.entity.AnalysisArgs;

/**
 * 提供给api层的执行入口
 */
public class API {
    private final Execution exec;

    public API() {
        exec = new Execution();
    }
    public API(AnalysisArgs args) {
        exec = new Execution();
        exec.addAnalysisOption(args.opt);
        exec.setLanguage(args.lang);
        exec.setOutputFormat(args.formats);
        exec.setFileName(args.fileName);
        exec.setInputStream(args.inputStream);
    }

    public Execution getExec(){
        return exec;
    }
}
