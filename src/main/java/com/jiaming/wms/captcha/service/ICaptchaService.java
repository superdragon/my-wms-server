package com.jiaming.wms.captcha.service;


import com.jiaming.wms.captcha.bean.entity.Captcha;

/**
 * @author dragon
 */
public interface ICaptchaService {
    void saveForCache(Captcha captcha);

    Captcha getForCacheById(String captchaKey);
}
