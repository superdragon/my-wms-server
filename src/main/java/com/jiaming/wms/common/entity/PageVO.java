package com.jiaming.wms.common.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class PageVO implements Serializable {

    private static final long serialVersionUID = -1305651411515635179L;
    @NotNull(message = "当前页码不正确")
    @Range(min = 1, message = "当前页码不正确")
    private Integer pageNum;
    @NotNull(message = "每页最多显示多少条记录不正确")
    @Range(min = 5, message = "每页显记录最少5条")
    private Integer pageSize;
}
