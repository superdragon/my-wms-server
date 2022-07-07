package com.jiaming.wms.stat.controller;

import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.stat.bean.vo.TodayStatDataVO;
import com.jiaming.wms.stat.service.ITodayStatService;
import com.jiaming.wms.utils.RedisKeyUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dragon
 */
@Api(tags = "今日实时统计API")
@RestController
@RequestMapping("/todayStat")
public class TodayStatController {

    @Autowired
    ITodayStatService todayStatService;

    @GetMapping("/total")
    public ResultVO<TodayStatDataVO> total() {
        TodayStatDataVO todayStatDataVO = new TodayStatDataVO();

        String key = RedisKeyUtil.getTodayTransferStat();
        Long transferTotal = todayStatService.getByRedis(key);

        String inStoreStatkey = RedisKeyUtil.getTodayInStoreStat();
        Long inStoreTotal = todayStatService.getByRedis(inStoreStatkey);

        todayStatDataVO.setTransferTotal(transferTotal);
        todayStatDataVO.setInStoreTotal(inStoreTotal);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, todayStatDataVO);
    }
}
