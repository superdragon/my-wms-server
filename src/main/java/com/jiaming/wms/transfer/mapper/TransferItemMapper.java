package com.jiaming.wms.transfer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiaming.wms.transfer.bean.entity.TransferItem;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemDataVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dragon
 */
@Mapper
public interface TransferItemMapper extends BaseMapper<TransferItem> {
    IPage<PageTransferItemDataVO> myPage(IPage<?> page, @Param("filter") PageTransferItemVO pageTransferItemVO);

    List<PageTransferItemDataVO> detail(String id);
}
