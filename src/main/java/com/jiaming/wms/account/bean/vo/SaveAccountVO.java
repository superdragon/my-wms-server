package com.jiaming.wms.account.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class SaveAccountVO implements Serializable {
    private static final long serialVersionUID = -6173351940617016110L;

    @NotEmpty(message = "登录账号不正确")
    private String loginName;
    @NotEmpty(message = "登录密码不正确")
    @Size(min = 6, max = 10, message = "密码长度必须在6~10个字符")
    private String loginPwd;
    @NotEmpty(message = "确认密码不正确")
    private String checkLoginPwd;
    @NotEmpty(message = "账户名称不正确")
    private String userName;
    @NotEmpty(message = "账户头像不正确")
    private String avatarUrl;
    @NotEmpty(message = "账户手机号不正确")
    private String mobile;
}
