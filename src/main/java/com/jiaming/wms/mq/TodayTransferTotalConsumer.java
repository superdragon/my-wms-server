package com.jiaming.wms.mq;

import cn.hutool.core.util.StrUtil;
import com.jiaming.wms.stat.service.ITodayStatService;
import com.jiaming.wms.transfer.bean.vo.TransferDetailVO;
import com.jiaming.wms.transfer.service.ITransferListService;
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
        consumerGroup = "todayTransferTotalGroup",
        topic = "todayTransferTotal",
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class TodayTransferTotalConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    ITodayStatService todayStatService;

    @Autowired
    ITransferListService transferListService;

    @Override
    public void onMessage(MessageExt msgExt) {
        log.info("接收的调货订单消息 {}", msgExt);
        // 获取消息的内容,调拨订单ID
        String msg = new String(msgExt.getBody(), StandardCharsets.UTF_8);
        if (StrUtil.isNotEmpty(msg)) {
            // 获取调拨清单详细信息，用于做其他业务
            TransferDetailVO transferDetailVO = transferListService.detail(msg);
            log.info("接收的调货订单详细信息 {}", transferDetailVO);
            // 统计
            todayStatService.plusTodayTransferTotal();
        } else {
            log.error("接收的调货订单消息内容为空 msgid={}", msgExt.getMsgId());
        }
    }
}
