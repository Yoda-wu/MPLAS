package com.scut.mplas.service;

import com.scut.mplas.Execution;
import org.springframework.web.multipart.MultipartFile;

public interface CodeAnalysisService {
    public Object analysis(Execution.Analysis analysis, MultipartFile data);
}
