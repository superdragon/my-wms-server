package com.jiaming.wms.employee.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dragon
 */
@Data
public class AddEmpVO implements Serializable {
    private static final long serialVersionUID = -2525450542364588363L;

    @NotEmpty(message = "员工姓名不正确")
    private String name;

    @NotEmpty(message = "员工手机号码不正确")
    @Size(min = 11, max = 15, message = "员工手机号码不正确")
    private String phone;

    @NotNull(message = "仓库ID不正确")
    private Long storeId;

    @NotNull(message = "年龄不正确")
    @Range(min = 18, max = 100, message = "年龄不正确")
    private Integer age;

    @NotNull(message = "性别不正确")
    @Range(min = 0, max = 1, message = "性别不正确")
    private Integer gender;

    @NotNull(message = "状态不正确")
    @Range(min = 0, max = 1, message = "状态不正确")
    private Integer status;
}
