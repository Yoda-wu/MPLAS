package com.scut.mplas.controller;


import com.scut.mplas.common.response.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApplicationController {

    @RequestMapping(value = "/ast", method = RequestMethod.POST)
    public BaseResponse GenerateAST(){
        // Your Code here
        return null;
    }
    @RequestMapping(value = "/cfg", method = RequestMethod.POST)
    public BaseResponse GenerateCFG(){
        // Your Code here
        return null;
    }

    @RequestMapping(value = "/pdg", method = RequestMethod.POST)
    public BaseResponse GeneratePDG(){
        // Your Code here
        return null;
    }

    @RequestMapping(value = "/ddg", method = RequestMethod.POST)
    public BaseResponse GenerateDDG(){
        // Your Code here
        return null;
    }
}
