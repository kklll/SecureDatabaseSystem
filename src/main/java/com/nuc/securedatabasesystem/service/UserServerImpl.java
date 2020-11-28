package com.nuc.securedatabasesystem.service;

import com.nuc.securedatabasesystem.mapper.UserMapper;
import com.nuc.securedatabasesystem.pojo.User;
import com.nuc.securedatabasesystem.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 16:18
 */
@Service
public class UserServerImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * Login 方法的实现，返回username=传进来的name and password=传进来的password的人，
     * 如果找到，证明有这个用户，否则没这个用户
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public User login(User user) {
        User login = userMapper.login(user);
        if (login != null) {
            return login;
        }
        throw new RuntimeException("登陆失败");
    }
}
