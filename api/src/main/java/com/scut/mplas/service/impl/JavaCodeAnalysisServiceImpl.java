package com.scut.mplas.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scut.mplas.API;
import com.scut.mplas.Execution;
import com.scut.mplas.service.CodeAnalysisService;
import com.scut.mplas.util.AnalysisArgsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class JavaCodeAnalysisServiceImpl implements CodeAnalysisService {
    @Autowired
    AnalysisArgsBuilder analysisArgsBuilder;
    @Override
    public Object analysis(Execution.Analysis analysis, MultipartFile data) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(data.getInputStream()));
            String line;
            List<String> result = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                JSONObject srcFile = JSONObject.parseObject(line);
                String fileName = srcFile.getString("text");
                String src = srcFile.getString("code");

                InputStream stream = new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8));
                String dataStr = new API(analysisArgsBuilder.
                        setFileName(fileName).
                        setAnalysisOpt(analysis).
                        setInputStream(stream).
                        setLanguageType(Execution.Languages.JAVA).build()).getExec().ExecuteForAPI();
                result.add(dataStr);
            }
            return JSONArray.parse(result.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
