package com.jiaming.wms.employee.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class UpdateEmpVO implements Serializable {
    private static final long serialVersionUID = -2525450542364588363L;

    @NotNull(message = "员工ID不正确")
    private Long id;

    private String name;

    @Size(min = 11, max = 15, message = "员工手机号码不正确")
    private String phone;

    private Long storeId;

    @Range(min = 18, max = 100, message = "年龄不正确")
    private Integer age;

    @Range(min = 0, max = 1, message = "性别不正确")
    private Integer gender;

    @Range(min = 0, max = 1, message = "状态不正确")
    private Integer status;
}
