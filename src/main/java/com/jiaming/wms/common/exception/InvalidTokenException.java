package com.jiaming.wms.common.exception;


import com.jiaming.wms.common.response.ResultCodeEnum;

public class InvalidTokenException extends ApiException {
    public InvalidTokenException() {
        super(ResultCodeEnum.INVALID_TOKEN, "token 无效");
    }
}
