package com.scut.mplas.controller;

import com.scut.mplas.common.response.BaseResponse;
import com.scut.mplas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    ResponseBuilder responseBuilder;
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    BaseResponse Test(){
        return responseBuilder.getSuccessResponse();
    }
}
