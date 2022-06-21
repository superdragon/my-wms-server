package com.jiaming.wms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {
    /**
     * token 过期时间，单位秒，默认5分钟
     */
    private static final long EXPIRE_TIME = 3600L;
    /**
     * token 加密私钥，不能泄露
     */
    private static final String SCERET_KEY = "cd38f925c72643e4a1c9138cb9c85dc1";

    // 使用默认的私钥和过期时间创建 token
    public static String createSign(JwtInfo jwtInfo) {
        return createSign(jwtInfo, SCERET_KEY, EXPIRE_TIME);
    }

    // 解析 token 字符串，获取 token 中的信息
    public static JwtInfo getSecretInfo(String token) {
        return getSecretInfo(token, SCERET_KEY);
    }

    // 使用自定义的私钥创建 token
    public static String createSign(JwtInfo jwtInfo, String secretKey) {
        return createSign(jwtInfo, secretKey, EXPIRE_TIME);
    }

    /**
     * 创建签名token
     *
     * @param jwtInfo    账户信息，必须包含账户ID、名称和密码
     * @param expireTime token jwtinfo 过期时间，单位秒
     * @return
     */
    public static String createSign(JwtInfo jwtInfo, String secretKey, long expireTime) {
        // token过期日期
        Date date = new Date(System.currentTimeMillis() + expireTime * 1000);
        Claims claims = Jwts.claims();
        claims.put("accountId", jwtInfo.getId());
        claims.put("loginName", jwtInfo.getLoginName());
        claims.put("userName", jwtInfo.getUserName());
        return Jwts.builder()
                .setClaims(claims) // 设置token中携带的加密信息
                .setExpiration(date) // 设置token的过期时间
                .signWith(SignatureAlgorithm.HS512, secretKey) // 加密签名
                .compact();
    }

    /**
     * 获取加密信息
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static JwtInfo getSecretInfo(String token, String secretKey) {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(body.get("accountId", Long.class));
        jwtInfo.setUserName(body.get("userName", String.class));
        jwtInfo.setLoginName(body.get("loginName", String.class));
        return jwtInfo;

    }

    // 示例
    public static void main(String[] args) {
        // 创建 token 中需要携带的非敏感信息
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(1L);
        jwtInfo.setUserName("admin");
        jwtInfo.setLoginName("admin");
        System.out.println(jwtInfo);

        // 生成 token 字符串
        String token = JwtUtil.createSign(jwtInfo);
        System.out.println(token);

        // 自定义创建
//        JwtUtil.createSign(jwtInfo, "xxxxxx", 3600L);
//
//        // 解密 token 字符串，返回 token 中携带的信息
        JwtInfo secretInfo = getSecretInfo(token);
        System.out.println(secretInfo);

        String token2 = "eyJhbGciOiJIUzUxMiJ9.eyJhY2NvdW50SWQiOjEsImxvZ2luTmFtZSI6ImFkbWluMSIsInVzZXJOYW1lIjoiYWRtaW4xIiwiZXhwIjoxNjQwMDc1MDIwfQ.s9Kgk8ZJb3cioeSx325dXzcOiVI6R0EunCh1oUZyzaG_TXshpVpJ9yMDwnNdEPiYinMnyQeaqtdVeEWw07E-cg";
        getSecretInfo(token2);
    }
}
