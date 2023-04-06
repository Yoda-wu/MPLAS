package com.scut.mplas.controller;


import com.alibaba.fastjson.JSON;
import com.scut.mplas.entity.response.BaseResponse;
import com.scut.mplas.API;
import com.scut.mplas.Execution;
import com.scut.mplas.entity.response.ResponseCode;
import com.scut.mplas.service.CodeAnalysisServiceFactory;
import com.scut.mplas.util.AnalysisArgsBuilder;
import com.scut.mplas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/")
public class ApplicationController {
    @Autowired
    ResponseBuilder responseBuilder;
    @RequestMapping(value = "/ast", method = RequestMethod.POST)
    public BaseResponse GenerateAST(@RequestParam String lang,  @RequestBody MultipartFile data){
        // Your Code here
        BaseResponse successResponse =responseBuilder.getSuccessResponse();

        //防止空指针异常
        if (CodeAnalysisServiceFactory.getService(lang)==null){
            return responseBuilder.getFailResponse(ResponseCode.PARAM_UN_VALID);
        }
        Object res = CodeAnalysisServiceFactory.getService(lang).analysis(Execution.Analysis.AST, data);
        successResponse.setData(res);
        return successResponse;
    }
    @RequestMapping(value = "/cfg", method = RequestMethod.POST)
    public BaseResponse GenerateCFG(){
        // Your Code here
        BaseResponse successResponse =responseBuilder.getSuccessResponse();
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
