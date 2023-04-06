package com.scut.mplas.exception;

import com.scut.mplas.entity.response.ResponseCode;

public class BusinessException extends RuntimeException{
    private final ResponseCode responseCode;
    public ResponseCode getResponseCode(){
        return responseCode;
    }
    public BusinessException(ResponseCode responseCode){
        super(responseCode.getMsg());
        this.responseCode=responseCode;
    }
}
