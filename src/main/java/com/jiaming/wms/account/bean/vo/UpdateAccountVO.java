package com.jiaming.wms.account.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class UpdateAccountVO implements Serializable {
    private static final long serialVersionUID = 8179058864500752623L;
    private String loginName;
    @NotNull(message = "账户ID不正确")
    private Long id;
    private String userName;
    private String mobile;
    private String avatarUrl;
}
