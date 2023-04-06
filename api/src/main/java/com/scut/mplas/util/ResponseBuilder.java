package com.scut.mplas.util;

import com.scut.mplas.entity.response.BaseResponse;
import com.scut.mplas.entity.response.ResponseCode;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {

    /**
     * @return  generate a success response without data
     */
    public BaseResponse getSuccessResponse(){
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    /**
     * @param data data include:ast, cfg, pdg, ddg etc
     * @return generate a success response with data
     */
    public BaseResponse getSuccessResponse(Object data){
        return new BaseResponse(ResponseCode.SUCCESS, data);
    }

    /**
     * @return generate a fail response with data
     */
    public BaseResponse getFailResponse(){
        return new BaseResponse(ResponseCode.FAIL);
    }

    /**
     * @return a fail response with responseCode
     */
    public BaseResponse getFailResponse(ResponseCode responseCode){
        return new BaseResponse(responseCode);
    }
}
