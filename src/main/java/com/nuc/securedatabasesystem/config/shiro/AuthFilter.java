package com.nuc.securedatabasesystem.config.shiro;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 19:57
 */

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.nuc.securedatabasesystem.unil.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现自定义的认证拦截器,接收传过来的token,实现前后端分离的权限认证
 */
@Slf4j
@Component
public class AuthFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 在这里拦截所有请求
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("进入拦截器");
        String token = jwtUtils.getRequestToken((HttpServletRequest) request);
        log.info(((HttpServletRequest) request).getRequestURI());
        if ("/api/login".equals(((HttpServletRequest) request).getRequestURI())) {
            return true;
        }
        if (!StringUtils.isBlank(token) && !"null".equals(token)) {
            log.info("token不为空，进行验证");
            try {
                jwtUtils.verify(token);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("认证失败！");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * 请求失败拦截,请求终止，不进行转发直接返回客户端拦截结果
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("进入验证失败的拦截器！");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.sendError(403);
        return false;
    }


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(httpServletRequest, httpServletResponse);
    }
}
