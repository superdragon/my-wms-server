package com.jiaming.wms.utils;

import lombok.Data;

/**
 * token中保存的信息
 */
@Data
public class JwtInfo {
    // 账户 ID
    private Long id;
    // 账号信息
    private String loginName;
    // 用户名称
    private String userName;
}
