package com.jiaming.wms.goods.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class UpdateGoodsVO implements Serializable {
    private static final long serialVersionUID = -7435671489498702971L;

    @NotNull(message = "商品ID不正确")
    private Long id;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 品牌ID
     */
    @Range(min = 1, message = "品牌不正确")
    private Long brandId;

    /**
     * 包装ID
     */
    @Range(min = 1, message = "包装不正确")
    private Long packId;

    /**
     * 口味ID
     */
    @Range(min = 1, message = "口味不正确")
    private Long tasteId;

    /**
     *
     */
    private String name;

    /**
     * 0:上架,1:下架
     */
    @Range(min = 0, max = 1, message = "状态不正确")
    private Integer status;

    /**
     * 销售价格,单位分
     */
    @Range(min = 1, message = "价格不正确")
    private Integer price;

    /**
     * 成本价格,单位分
     */
    @Range(min = 1, message = "价格不正确")
    private Integer innerPrice;

    private List<String> imageUrls;
}
