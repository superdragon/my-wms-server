package com.jiaming.wms.account.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dragon
 */
@Data
public class FindAccountDataVO implements Serializable {
    private static final long serialVersionUID = 4991250960938138793L;

    private String loginName;
    private Long id;
    private String nickName;
    private String imageUrl;
    private String phone;
    private Integer gender;
    private Date updateTime;
    private Date createTime;
}
