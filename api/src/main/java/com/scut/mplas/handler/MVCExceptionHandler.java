package com.scut.mplas.handler;

import com.scut.mplas.common.response.BaseResponse;
import com.scut.mplas.exception.BusinessException;
import com.scut.mplas.util.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class MVCExceptionHandler {
    @Autowired
    private ResponseBuilder responseBuilder;
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException businessException){
        log.error(businessException.getMessage());
        return new BaseResponse(businessException.getResponseCode());
    }
    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(Exception exception){
        log.error(exception.getMessage());
        return responseBuilder.getFailResponse();
    }
}
