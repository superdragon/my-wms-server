package com.jiaming.wms.transfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.transfer.bean.entity.TransferList;
import com.jiaming.wms.transfer.bean.vo.AddTransferListVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferListDataVO;
import com.jiaming.wms.transfer.bean.vo.PageTransferListVO;
import com.jiaming.wms.transfer.bean.vo.TransferDetailVO;

/**
 * @author dragon
 */
public interface ITransferListService extends IService<TransferList> {
    void saveTransferList(AddTransferListVO addTransferListVO);

    MyPage<PageTransferListDataVO> pageTransferList(PageTransferListVO listVO);

    void updateStatus(String id, Integer status);

    TransferDetailVO detail(String id);
}
