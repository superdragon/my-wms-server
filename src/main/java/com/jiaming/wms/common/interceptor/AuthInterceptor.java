package com.jiaming.wms.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.jiaming.wms.common.exception.InvalidTokenException;
import com.jiaming.wms.utils.JwtInfo;
import com.jiaming.wms.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dragon
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、要求发起的请求头部或者表单中必须有token参数
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (StrUtil.isEmpty(token)) {
            log.warn("API {}, 没有获得token", request.getRequestURI());
            throw new InvalidTokenException();
        }
        // 2、验证token的有效性和token是否过期
        JwtInfo jwtInfo = null;
        try {
            jwtInfo = JwtUtil.getSecretInfo(token);
        } catch (Exception e) {
            // ExpiredJwtException token过期异常
            // SignatureException token签名错误异常
            log.warn("API {}, EXP {} token {},", request.getRequestURI(), e.getLocalizedMessage(), token);
            throw new InvalidTokenException();
        }

        return true;
    }

}
