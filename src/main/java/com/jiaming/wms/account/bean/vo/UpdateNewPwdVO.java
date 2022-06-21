package com.jiaming.wms.account.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class UpdateNewPwdVO implements Serializable {
    private static final long serialVersionUID = -2249389216421647190L;
    @NotNull(message = "账户ID不正确")
    private Long id;
    @NotEmpty(message = "账户新密码不能为空")
    private String newLoginPwd;
    @NotEmpty(message = "账户确认新密码不能为空")
    private String checkNewLoginPwd;
}
