package com.jiaming.wms.transfer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.transfer.bean.entity.TransferList;
import com.jiaming.wms.transfer.bean.vo.PageTransferListDataVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dragon
 */
@Mapper
public interface TransferListMapper extends BaseMapper<TransferList> {
    IPage<PageTransferListDataVO> myPage(IPage<?> page, @Param("filter") PageTransferListVO listVO);

    PageTransferListDataVO detail(String id);
}
