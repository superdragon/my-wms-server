package com.jiaming.wms.mq;

import cn.hutool.core.util.StrUtil;
import com.jiaming.wms.goodsin.bean.vo.InfoInStoreListDataVO;
import com.jiaming.wms.goodsin.service.IInStoreListService;
import com.jiaming.wms.stat.service.ITodayStatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author dragon
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "todayInStoreTotalGroup",
        topic = "todayInStoreTotal",
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class TodayInStoreTotalConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    ITodayStatService todayStatService;

    @Autowired
    IInStoreListService inStoreListService;

    @Override
    public void onMessage(MessageExt msgExt) {
        log.info("接收的入库订单消息 {}", msgExt);
        // 获取消息的内容,入库订单ID
        String msg = new String(msgExt.getBody(), StandardCharsets.UTF_8);
        if (StrUtil.isNotEmpty(msg)) {
            // 获取入库清单详细信息，用于做其他业务
            InfoInStoreListDataVO info = inStoreListService.info(msg);
            log.info("接收的入库订单详细信息 {}", info);
            // 统计
            todayStatService.plusTodayInStoreTotal();
        } else {
            log.error("接收的入库订单消息内容为空 msgid={}", msgExt.getMsgId());
        }
    }
}
