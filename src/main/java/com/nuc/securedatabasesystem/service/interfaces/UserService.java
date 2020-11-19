package com.nuc.securedatabasesystem.service.interfaces;

import com.nuc.securedatabasesystem.pojo.User;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 16:19
 */
public interface UserService {
    /**
     * 登录接口
     * @param user
     * @return
     */
    User login(User user);
}
