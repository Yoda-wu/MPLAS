package com.scut.mplas.service.impl;

import com.alibaba.fastjson.JSON;
import com.scut.mplas.API;
import com.scut.mplas.Execution;
import com.scut.mplas.service.CodeAnalysisService;
import com.scut.mplas.util.AnalysisArgsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class CppCodeAnalysisServiceImpl implements CodeAnalysisService {
    @Autowired
    AnalysisArgsBuilder analysisArgsBuilder;
    @Override
    public Object analysis(Execution.Analysis analysis, MultipartFile data) {
        try {
            InputStream stream = data.getInputStream();
            String fileName = data.getName();
            String dataStr = new API(analysisArgsBuilder.
                    setFileName(fileName).
                    setAnalysisOpt(analysis).
                    setInputStream(stream).
                    setLanguageType(Execution.Languages.CPP).build()).getExec().ExecuteForAPI();
            Object res = JSON.parse(dataStr);
            return  res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
