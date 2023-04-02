package com.scut.mplas.common.response;

import lombok.Data;
import lombok.Getter;



public enum ResponseCode {

    SUCCESS(200, "success"),
    FAIL(-1, "fail");
    @Getter private Integer code;
    @Getter private String msg;

    ResponseCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
