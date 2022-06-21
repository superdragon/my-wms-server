package com.jiaming.wms.captcha.service.impl;

import com.jiaming.wms.captcha.bean.entity.Captcha;
import com.jiaming.wms.captcha.service.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author dragon
 */
@Service
public class CaptchaServiceImpl implements ICaptchaService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void saveForCache(Captcha captcha) {
        // 保存验证码
        redisTemplate.opsForValue().set(captcha.getId(), captcha.getCode());
        // 设置验证码的过期时间
        redisTemplate.expire(captcha.getId(), 5, TimeUnit.MINUTES);
    }

    @Override
    public Captcha getForCacheById(String captchaKey) {
        String code = redisTemplate.opsForValue().get(captchaKey);
        Captcha captcha = new Captcha();
        captcha.setId(captchaKey);
        captcha.setCode(code);
        return captcha;
    }
}
