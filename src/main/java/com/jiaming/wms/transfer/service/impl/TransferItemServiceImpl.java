package com.jiaming.wms.transfer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.transfer.bean.entity.TransferItem;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemDataVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemVO;
import com.jiaming.wms.transfer.mapper.TransferItemMapper;
import com.jiaming.wms.transfer.service.ITransferItemService;
import org.springframework.stereotype.Service;

/**
 * @author dragon
 */
@Service
public class TransferItemServiceImpl extends ServiceImpl<TransferItemMapper, TransferItem>
        implements ITransferItemService {
    @Override
    public MyPage<PageTransferItemDataVO> myPage(PageTransferItemVO pageTransferItemVO) {
        IPage<PageTransferItemDataVO> page = new Page<>();
        page.setSize(pageTransferItemVO.getPageSize());
        page.setCurrent(pageTransferItemVO.getPageNum());

        this.baseMapper.myPage(page, pageTransferItemVO);

        MyPage<PageTransferItemDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setTotalPage(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }
}
