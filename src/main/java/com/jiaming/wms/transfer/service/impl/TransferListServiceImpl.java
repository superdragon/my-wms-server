package com.jiaming.wms.transfer.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.goodsin.bean.vo.AddInStoreItemVO;
import com.jiaming.wms.mq.TopicConstants;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import com.jiaming.wms.transfer.bean.entity.TransferItem;
import com.jiaming.wms.transfer.bean.entity.TransferList;
import com.jiaming.wms.transfer.bean.vo.*;
import com.jiaming.wms.transfer.mapper.TransferListMapper;
import com.jiaming.wms.transfer.service.ITransferItemService;
import com.jiaming.wms.transfer.service.ITransferListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dragon
 */
@Slf4j
@Service
public class TransferListServiceImpl extends ServiceImpl<TransferListMapper, TransferList>
        implements ITransferListService {

    @Autowired
    ITransferItemService transferItemService;

    @Autowired
    IStoreGoodsStatService storeGoodsStatService;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    // 保存调拨清单及明细，同时锁定调拨商品数量
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTransferList(AddTransferListVO addTransferListVO) {
        // 保存调拨清单基本信息
        TransferList transferList = new TransferList();
        // 生成调拨单号
        String nowStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // YYYYMMDDHHMMSSSSS+发货仓库ID+发货员工ID+收货仓库ID
        String id = nowStr + addTransferListVO.getShipStoreId()
                + addTransferListVO.getShipEmpId()
                + addTransferListVO.getReceiveStoreId();
        transferList.setId(id);
        transferList.setShipStoreId(addTransferListVO.getShipStoreId());
        transferList.setShipEmpId(addTransferListVO.getShipEmpId());
        transferList.setReceiveStoreId(addTransferListVO.getReceiveStoreId());
        this.save(transferList);

        // 保存调拨清单商品明细，以及锁定调拨商品数量
        List<TransferItem> items = new ArrayList<>();
        for (AddInStoreItemVO goods : addTransferListVO.getGoods()) {
            // 创建保存商品明细的实体类
            TransferItem item = new TransferItem();
            item.setListId(id);
            item.setGoodsId(goods.getGoodsId());
            item.setGoodsNum(goods.getGoodsNum());
            // 存放到集合中，后续一次性的把所有清单商品批量添加
            items.add(item);

            // 锁定发货仓库调拨的商品数量
            storeGoodsStatService.lockGoodsNum(addTransferListVO.getShipStoreId(),
                    goods.getGoodsId(), goods.getGoodsNum());
        }

        // 批量保存调拨清单中的所有商品信息
        transferItemService.saveBatch(items);

        // 异步发送创建调拨订单消息
        rocketMQTemplate.asyncSend(TopicConstants.TODAY_TRANSFER_TOTAL, id, new SendCallback() {
            //消息发送成功的回调
            @Override
            public void onSuccess(SendResult sendResult) {
                // 如果有业务是必须在消息投递成功后处理，那么就必须放在这个方法里面写
                log.info("库存调拨订单消息发送成功 订单号={} 发送结果={}", id, sendResult);
            }

            //消息发送失败的回调
            @Override
            public void onException(Throwable throwable) {
                // 如果消息发送失败，如何追溯这条失败的消息
                // 1. 使用log记录到文件中。缺点：查询不方便
                // 2. 放到ES，推荐这一种，保证既方便查询又不容易丢失数据
                // 3. 放Redis，保证方便查询，但是不能保证数据不丢失
                log.error("库存调拨订单消息发送失败 订单号={} 错误原因={}", id, throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public MyPage<PageTransferListDataVO> pageTransferList(PageTransferListVO listVO) {
        IPage<PageTransferListDataVO> page = new Page<>();
        page.setSize(listVO.getPageSize());
        page.setCurrent(listVO.getPageNum());

        this.baseMapper.myPage(page, listVO);

        MyPage<PageTransferListDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setPageNum(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }

    /**
     * 更新调拨订单的状态
     * @param id 调拨订单ID
     * @param status 订单状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String id, Integer status) {
        // 根据订单号获取调拨清单商品明细
        QueryWrapper<TransferItem> queryWrapper = Wrappers.query();
        queryWrapper.eq("list_id", id);
        List<TransferItem> transferItems = transferItemService.list(queryWrapper);

        // 根据订单号获取清单信息
        TransferList transferList = this.getById(id);

        // 更新成已发货状态
        if (status == 1) {
            for (TransferItem transferItem : transferItems) {
                // 减去当前调拨商品的待出库数量，已出库数量增加对等数量
                storeGoodsStatService.updateOutTotal(transferList.getShipStoreId(),
                        transferItem.getGoodsId(), transferItem.getGoodsNum());
            }
        }
        // 更新成已收货状态
        if (status == 2) {
            for (TransferItem transferItem : transferItems) {
                // 增加当前调拨商品的入库数量
                storeGoodsStatService.increaseInTotal(transferList.getReceiveStoreId(),
                        transferItem.getGoodsId(), transferItem.getGoodsNum());
            }
        }
        // 更新成取消状态
        if (status == 3) {
            // 如果当前状态是配货中，那么取消调拨时需要减去调拨商品的待出库数量
            if (transferList.getStatus() == 0) {
                for (TransferItem transferItem : transferItems) {
                    storeGoodsStatService.reduceOuttingTotal(transferList.getShipStoreId(),
                            transferItem.getGoodsId(), transferItem.getGoodsNum());
                }
            }
            // 如果当前状态是已发货，那么取消调拨时需要减去调拨商品的已出库数量
            if (transferList.getStatus() == 1) {
                for (TransferItem transferItem : transferItems) {
                    storeGoodsStatService.reduceOutTotal(transferList.getShipStoreId(),
                            transferItem.getGoodsId(), transferItem.getGoodsNum());
                }
            }
        }

        // 更新状态
        UpdateWrapper<TransferList> updateWrapper = Wrappers.update();
        updateWrapper.set("status", status);
        if (status == 1) {
            updateWrapper.set("ship_time", new Date());
        }
        if (status == 2) {
            updateWrapper.set("receive_time", new Date());
        }
        updateWrapper.eq("id", id);
        this.update(updateWrapper);
    }

    @Override
    public TransferDetailVO detail(String id) {
        // 获取调拨订单基本信息
        PageTransferListDataVO info = this.baseMapper.detail(id);
        // 获取调拨订单明细
        List<PageTransferItemDataVO> items = transferItemService.detail(id);
        TransferDetailVO transferDetailVO = new TransferDetailVO();
        transferDetailVO.setInfo(info);
        transferDetailVO.setItems(items);
        return transferDetailVO;
    }
}
