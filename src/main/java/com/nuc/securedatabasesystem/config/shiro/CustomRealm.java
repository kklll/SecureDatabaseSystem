package com.nuc.securedatabasesystem.config.shiro;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nuc.securedatabasesystem.unil.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 17:19
 */
@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.error("进行授权-------------");
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthTokenVo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行认证逻辑");
        String token = (String) authenticationToken.getCredentials();
        //获得token中的用户信息
        DecodedJWT verify = jwtUtils.verify(token);
        log.error(verify.getPayload());
        return new SimpleAuthenticationInfo(token, token, getName());
    }

}
