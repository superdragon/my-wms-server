package com.jiaming.wms.common.response;

import lombok.Getter;

/**
 * @author dragon
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "SUCCESS"),
    FAIL(500, "未知错误"),
    BIZ_FAIL(501, "业务错误"),
    INVALID_TOKEN(401, "无效token"),
    INVALID_PERMISSIONS(402, "无效权限"),

    FAIL_PARAM_MISSING(510, "缺少必填参数"),
    FAIL_PARAM_TYPE(511, "参数类型不匹配"),
    FAIL_REQUEST_METHOD(512, "请求方法错误"),
    FAIL_REQUEST_TYPE(513, "请求内容类型错误"),
    FAIL_REQUEST_CONTENT(514, "无法读取请求内容"),
    FAIL_PARAM_VALIDATE(515, "参数值不合法"),
    FAIL_DUPLICATE_KEY(516, "记录已存在"),
    ;

    private Integer code;
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
