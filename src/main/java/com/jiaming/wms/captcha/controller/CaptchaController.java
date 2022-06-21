package com.jiaming.wms.captcha.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import com.jiaming.wms.captcha.bean.entity.Captcha;
import com.jiaming.wms.captcha.service.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dragon
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    ICaptchaService captchaService;

    // 获取图片验证码，并把图片验证码的内容保存到DB中
    @GetMapping("/render")
    public void render(HttpServletResponse response) {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 20);
        // 获取验证码图片对应的内容
        String captchaCode = lineCaptcha.getCode();
        // 保存验证码信息
        Captcha captcha = new Captcha();
        // 给当前验证码提供一个唯一ID
        captcha.setId(IdUtil.fastSimpleUUID());
        captcha.setCode(captchaCode);
        captchaService.saveForCache(captcha);
        OutputStream out = null;
        try {
            // 把 key 放到响应头中，
            // 注意：header中的自定义属性不能使用驼峰式名，因为跨域时会自动转换成全小写
            response.setHeader("captcha_key", captcha.getId());
            out = response.getOutputStream();
            //图形验证码写出到响应流中
            lineCaptcha.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    // 关闭响应流
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
