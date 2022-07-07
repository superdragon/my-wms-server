package com.jiaming.wms.goodsin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaming.wms.common.entity.MyPage;
import com.jiaming.wms.employee.bean.entity.Employee;
import com.jiaming.wms.employee.service.IEmployeeService;
import com.jiaming.wms.goodsin.bean.entity.InStoreItem;
import com.jiaming.wms.goodsin.bean.entity.InStoreList;
import com.jiaming.wms.goodsin.bean.vo.*;
import com.jiaming.wms.goodsin.mapper.InStoreListMapper;
import com.jiaming.wms.goodsin.service.IInStoreItemService;
import com.jiaming.wms.goodsin.service.IInStoreListService;
import com.jiaming.wms.mq.TopicConstants;
import com.jiaming.wms.stat.service.IStoreGoodsStatService;
import com.jiaming.wms.store.bean.entity.Store;
import com.jiaming.wms.store.service.IStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
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
public class InStoreListServiceImpl extends ServiceImpl<InStoreListMapper, InStoreList> implements IInStoreListService {

    @Autowired
    IInStoreItemService storeItemService;

    @Autowired
    IStoreService storeService;

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IStoreGoodsStatService storeGoodsStatService;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveInStore(AddInStoreListVO listVO) {
        // 保存入库基本信息
        InStoreList inStoreList = new InStoreList();
        // 生成入库单号
        String nowStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        String id = nowStr + listVO.getStoreId() + listVO.getEmployeeId();
        inStoreList.setId(id);
        inStoreList.setEmployeeId(listVO.getEmployeeId());
        inStoreList.setStoreId(listVO.getStoreId());
        this.save(inStoreList);

        // 保存入库商品信息
        List<InStoreItem> items = new ArrayList<>();
        for (AddInStoreItemVO goods : listVO.getGoods()) {
            InStoreItem inStoreItem = new InStoreItem();
            inStoreItem.setListId(id);
            inStoreItem.setGoodsId(goods.getGoodsId());
            inStoreItem.setGoodsNum(goods.getGoodsNum());
            items.add(inStoreItem);

            // 更新入库商品的入库总量
            storeGoodsStatService.increaseInTotal(listVO.getStoreId(), goods.getGoodsId(), goods.getGoodsNum());
        }
        storeItemService.saveBatch(items);

        // 异步发送创建入库订单消息
        rocketMQTemplate.asyncSend(TopicConstants.TODAY_INSTORE_TOTAL, id, new SendCallback() {
            //消息发送成功的回调
            @Override
            public void onSuccess(SendResult sendResult) {
                // 如果有业务是必须在消息投递成功后处理，那么就必须放在这个方法里面写
                log.info("商品入库订单消息发送成功 订单号={} 发送结果={}", id, sendResult);
            }

            //消息发送失败的回调
            @Override
            public void onException(Throwable throwable) {
                // 如果消息发送失败，如何追溯这条失败的消息
                // 1. 使用log记录到文件中。缺点：查询不方便
                // 2. 放到ES，推荐这一种，保证既方便查询又不容易丢失数据
                // 3. 放Redis，保证方便查询，但是不能保证数据不丢失
                log.error("商品入库订单消息发送失败 订单号={} 错误原因={}", id, throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeInStore(String id) {
        // 减少商品入库总量
        // 1. 首先获取当前入库清单的商品明细，以及入库清单基本信息
        InStoreList inStoreList = this.getById(id);
        QueryWrapper<InStoreItem> wrapper = Wrappers.query();
        wrapper.eq("list_id", id);
        List<InStoreItem> inStoreItems = storeItemService.list(wrapper);
        for (InStoreItem inStoreItem : inStoreItems) {
            // 2. 减少对应商品的入库总量
            storeGoodsStatService.reduceInTotal(inStoreList.getStoreId(), inStoreItem.getGoodsId(), inStoreItem.getGoodsNum());
        }
        // 删除入库基本信息
        this.removeById(id);
        // 删除入库商品信息
        // DELETE FROM in_store_item WHERE list_id = xxxx
        storeItemService.remove(wrapper);
    }

    @Override
    public MyPage<PageInStoreListDataVO> pageInStore(PageInStoreListVO listVO) {
        IPage<PageInStoreListDataVO> page = new Page<>();
        page.setSize(listVO.getPageSize());
        page.setCurrent(listVO.getPageNum());

        this.baseMapper.myPage(page, listVO);

        MyPage<PageInStoreListDataVO> result = new MyPage<>();
        result.setTotalNum(page.getTotal());
        result.setTotalPage(page.getPages());
        result.setPageSize(page.getSize());
        result.setPageNum(page.getCurrent());
        result.setItems(page.getRecords());
        return result;
    }

    @Override
    public InfoInStoreListDataVO info(String id) {
        // 组装响应数据
        InfoInStoreListDataVO infoInStoreListDataVO = new InfoInStoreListDataVO();
        // 查询入库基本信息
        InStoreList baseInfo = this.getById(id);
        BeanUtils.copyProperties(baseInfo, infoInStoreListDataVO);

        // 查询仓库基本信息
        Store store = storeService.getById(baseInfo.getStoreId());
        infoInStoreListDataVO.setStore(store);

        // 查询员工基本信息
        Employee employee = employeeService.getById(baseInfo.getEmployeeId());
        infoInStoreListDataVO.setEmployee(employee);

        return infoInStoreListDataVO;
    }
}
