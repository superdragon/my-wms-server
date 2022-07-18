package com.jiaming.wms.mq;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jiaming.wms.stat.service.ITodayStatService;
import com.jiaming.wms.trade.bean.entity.Trade;
import com.jiaming.wms.trade.bean.vo.TradeDetailDataVO;
import com.jiaming.wms.trade.service.ITradeService;
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
        consumerGroup = "todayTradeTotalGroup",
        topic = "paySuccess",
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class TodayTradeTotalConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    ITodayStatService todayStatService;

    @Autowired
    ITradeService tradeService;

    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("接收采购订单支付成功消息 {}", messageExt);

        String msg = new String(messageExt.getBody(), StandardCharsets.UTF_8);

        if (StrUtil.isNotEmpty(msg)) {
            Trade trade = JSONUtil.toBean(msg, Trade.class);
            TradeDetailDataVO detail = tradeService.detail(trade.getId());
            log.info("接收的采购订单详细信息 {}", detail);
            // 统计
            todayStatService.plusTodayTradeTotal(detail);
        } else {
            log.error("接收采购订单支付成功消息内容为空 msgid={}", messageExt.getMsgId());
        }
    }
}
