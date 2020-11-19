package com.nuc.securedatabasesystem.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.nuc.securedatabasesystem.config.ResultBody;
import com.nuc.securedatabasesystem.pojo.User;
import com.nuc.securedatabasesystem.service.interfaces.UserService;
import com.nuc.securedatabasesystem.unil.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author DeepBlue
 * @Date 2020/11/11 17:58
 */
@RestController
@Validated
@Slf4j
@RequestMapping("api")
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登陆方法，传一个用户名称和密码，然后进行验证。
     */
    @PostMapping("login")
    public ResultBody login(@RequestBody User user) {
        try {
            userService.login(user);
            log.info("用户名{},密码{} is login", user.getUsername(), user.getPassword());
            Map<String, String> map = new HashMap<>();
            map.put("username", user.getUsername());
            String token = jwtUtils.getToken(map);
            return ResultBody.success(token);
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.warn("登陆失败，校验密码错误");
            return ResultBody.error(e.getMessage());
        }
    }


    @GetMapping("login/cap")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        //定义图形验证码的长和宽
        ShearCaptcha lineCaptcha = CaptchaUtil.createShearCaptcha(200, 100);
        request.setAttribute("cap", lineCaptcha.getCode());
        try {
            lineCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
