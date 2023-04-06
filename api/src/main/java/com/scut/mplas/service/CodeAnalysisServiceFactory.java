package com.scut.mplas.service;

import com.scut.mplas.service.CodeAnalysisService;
import com.scut.mplas.service.impl.JavaCodeAnalysisServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class CodeAnalysisServiceFactory {

    CodeAnalysisService javaCodeAnalysisService;
    private  final Map<String, CodeAnalysisService> map = new HashMap<>();
    public CodeAnalysisServiceFactory( @Qualifier(value = "javaCodeAnalysisServiceImpl")CodeAnalysisService javaCodeAnalysisService) {
        this.javaCodeAnalysisService = javaCodeAnalysisService;
        map.put("java", this.javaCodeAnalysisService);
    }
    public CodeAnalysisService getService(String medalType) {
        return map.get(medalType);
    }
}
