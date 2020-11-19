package com.nuc.securedatabasesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nuc.securedatabasesystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @Author DeepBlue
 * @Date 2020/11/18 15:03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select username,password from user where username=#{username} and password=#{password} ")
    User login(User user);
}