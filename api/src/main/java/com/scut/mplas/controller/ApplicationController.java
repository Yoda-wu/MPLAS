package com.scut.mplas.controller;


import com.scut.mplas.Execution;
import com.scut.mplas.entity.response.BaseResponse;
import com.scut.mplas.service.CodeAnalysisServiceFactory;
import com.scut.mplas.util.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
@Slf4j
public class ApplicationController {
    @Autowired
    ResponseBuilder responseBuilder;
    @Autowired
    CodeAnalysisServiceFactory codeAnalysisServiceFactory;

    @RequestMapping(value = "/ast", method = RequestMethod.POST)
    public BaseResponse GenerateAST(@RequestParam String lang, @RequestBody MultipartFile data) {
        // Your Code here
        log.info(lang);
        BaseResponse successResponse = responseBuilder.getSuccessResponse();
        Object res = codeAnalysisServiceFactory.getService(lang).analysis(Execution.Analysis.AST, data);
        successResponse.setData(res);
        return successResponse;
    }

    @RequestMapping(value = "/cfg", method = RequestMethod.POST)
    public BaseResponse GenerateCFG(@RequestParam String lang, @RequestBody MultipartFile data) {
        // Your Code here
        log.info(lang);
        BaseResponse successResponse = responseBuilder.getSuccessResponse();
        Object res = codeAnalysisServiceFactory.getService(lang).analysis(Execution.Analysis.CFG, data);
        successResponse.setData(res);
        return successResponse;
    }

    @RequestMapping(value = "/pdg", method = RequestMethod.POST)
    public BaseResponse GeneratePDG(){
        // Your Code here
        BaseResponse successResponse =responseBuilder.getSuccessResponse();
        return successResponse;
    }

    @RequestMapping(value = "/ddg", method = RequestMethod.POST)
    public BaseResponse GenerateDDG(){
        // Your Code here
        BaseResponse successResponse =responseBuilder.getSuccessResponse();
        return successResponse;
    }
}
