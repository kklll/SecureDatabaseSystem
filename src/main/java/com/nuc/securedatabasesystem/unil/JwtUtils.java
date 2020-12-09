package com.nuc.securedatabasesystem.unil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 15:20
 * jwt的一些工具类方法
 */
@Slf4j
@Component
public class JwtUtils {
    @Value("${jwt.secretKey}")
    private String SIGN;

    public String getToken(Map<String, String> map) {
        //获取时间
        Calendar calendar = Calendar.getInstance();
        //设置Token60分钟过期
        calendar.add(Calendar.MINUTE, 60);
        //创建一个Token
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        builder.withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(SIGN));
    }


    /**
     * 如果抛出异常那么说明验签失败
     *
     * @param token
     */
    public DecodedJWT verify(String token) {
        //sha 256
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    public String getRequestToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("authorization");
        log.error("获取到jwtToken=={}", token);
        return token;
    }

    public String getSIGN() {
        return SIGN;
    }
}
