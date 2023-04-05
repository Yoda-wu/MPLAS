package com.scut.mplas.controller;

import com.scut.mplas.entity.response.BaseResponse;
import com.scut.mplas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {

    @Autowired
    ResponseBuilder responseBuilder;
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    BaseResponse Test(){
        return responseBuilder.getSuccessResponse();
    }
    BaseResponse Test(MultipartFile file){
//        InputStream fileInputStream = file.getInputStream();


        return responseBuilder.getSuccessResponse();
    }
}
