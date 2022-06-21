package com.jiaming.wms.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 */
@Data
public class MyPage<T> implements Serializable {
    private static final long serialVersionUID = 1479181009245862194L;
    // 当前页码
    private Long pageNum;
    // 每页条数
    private Long pageSize;
    // 总记录数
    private Long totalNum;
    // 总页数
    private Long totalPage;
    // 记录数
    private List<T> items;
}
