package com.jiaming.wms.common.exception;

import com.jiaming.wms.common.response.ResultCodeEnum;
import lombok.Data;

/**
 * @author dragon
 */
@Data
public class ApiException extends RuntimeException  {
    /**
     * 错误码
     */
    private int code;
    /**
     * 引起异常的简要信息
     */
    private String msg;

    /**
     * 引起异常的详细信息
     */
    private String detail;

    public ApiException(ResultCodeEnum resultCodeEnum, String detail) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.detail = detail;
    }

    public ApiException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.detail = resultCodeEnum.getMsg();
    }

    public ApiException(int code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public ApiException(String detail) {
        this.detail = detail;
    }
}
