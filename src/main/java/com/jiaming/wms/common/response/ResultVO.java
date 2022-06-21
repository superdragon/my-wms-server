package com.jiaming.wms.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class ResultVO<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public ResultVO() {
    }

    public ResultVO(ResultCodeEnum resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public ResultVO(ResultCodeEnum resultCode, T data) {
        this(resultCode);
        this.data = data;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
