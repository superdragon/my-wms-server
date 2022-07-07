package com.jiaming.wms.transfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.transfer.bean.entity.TransferItem;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemDataVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferItemVO;

import java.util.List;

/**
 * @author dragon
 */
public interface ITransferItemService extends IService<TransferItem> {
    MyPage<PageTransferItemDataVO> myPage(PageTransferItemVO pageTransferItemVO);

    List<PageTransferItemDataVO> detail(String id);
}
