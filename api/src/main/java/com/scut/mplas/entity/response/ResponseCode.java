package com.scut.mplas.entity.response;

import lombok.Getter;



public enum ResponseCode {

    SUCCESS(200, "success"),
    PARAM_UN_VALID(400_001,"无效参数"),
    FAIL(-1, "fail");
    @Getter private Integer code;
    @Getter private String msg;

    ResponseCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
