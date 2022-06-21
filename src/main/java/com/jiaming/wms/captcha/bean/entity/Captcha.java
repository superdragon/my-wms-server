package com.jiaming.wms.captcha.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Captcha implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 验证码内容
     */
    private String code;

    /**
     *
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}

