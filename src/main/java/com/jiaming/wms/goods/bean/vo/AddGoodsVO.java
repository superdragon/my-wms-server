package com.jiaming.wms.goods.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class AddGoodsVO implements Serializable {
    private static final long serialVersionUID = -7435671489498702971L;

    private Long id;

    /**
     * 商品编码
     */
    @NotEmpty(message = "编码不正确")
    private String code;

    /**
     * 品牌ID
     */
    @NotNull(message = "品牌不正确")
    @Range(min = 1, message = "品牌不正确")
    private Long brandId;

    /**
     * 包装ID
     */
    @NotNull(message = "包装不正确")
    @Range(min = 1, message = "包装不正确")
    private Long packId;

    /**
     * 口味ID
     */
    @NotNull(message = "口味不正确")
    @Range(min = 1, message = "口味不正确")
    private Long tasteId;

    /**
     *
     */
    @NotEmpty(message = "名称不正确")
    private String name;

    /**
     * 0:上架,1:下架
     */
    @NotNull(message = "状态不正确")
    @Range(min = 0, max = 1, message = "状态不正确")
    private Integer status;

    /**
     * 销售价格,单位分
     */
    @NotNull(message = "价格不正确")
    @Range(min = 1, message = "价格不正确")
    private Integer price;

    /**
     * 成本价格,单位分
     */
    @NotNull(message = "价格不正确")
    @Range(min = 1, message = "价格不正确")
    private Integer innerPrice;

    @NotNull(message = "商品图片不能为空")
    private List<String> imageUrls;
}
