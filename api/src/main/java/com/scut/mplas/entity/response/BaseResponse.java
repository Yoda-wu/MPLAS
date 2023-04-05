package com.scut.mplas.entity.response;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {
    @JSONField(name = "status_code")
    private Integer code;
    @JSONField(name = "status_msg")
    private String msg;
    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    private Object data;

    public BaseResponse(ResponseCode code){
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public BaseResponse(ResponseCode code, Object data){
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }
}
