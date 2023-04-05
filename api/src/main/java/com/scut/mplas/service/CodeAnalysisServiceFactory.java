package com.scut.mplas.service;

import com.scut.mplas.service.CodeAnalysisService;
import com.scut.mplas.service.impl.JavaCodeAnalysisServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CodeAnalysisServiceFactory {
    private static final Map<String, CodeAnalysisService> map = new HashMap<>();
    static {
        map.put("java", new JavaCodeAnalysisServiceImpl());
    }
    public static CodeAnalysisService getService(String medalType) {
        return map.get(medalType);
    }
}
