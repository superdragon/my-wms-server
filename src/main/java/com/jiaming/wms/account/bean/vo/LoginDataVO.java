package com.jiaming.wms.account.bean.vo;

import com.jiaming.wms.permission.bean.vo.PermissionDataVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class LoginDataVO implements Serializable {
    private static final long serialVersionUID = 5124822980503543659L;
    private String loginName;
    private Long id;
    private String userName;
    private String avatarUrl;
    private String mobile;
    private String token;
    private PermissionDataVO permission;
}
