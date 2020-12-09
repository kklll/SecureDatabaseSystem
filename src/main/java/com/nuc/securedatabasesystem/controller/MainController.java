package com.nuc.securedatabasesystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nuc.securedatabasesystem.config.ResultBody;
import com.nuc.securedatabasesystem.core.Tool;
import com.nuc.securedatabasesystem.mapper.HospitalRecordsMapper;
import com.nuc.securedatabasesystem.pojo.HospitalRecords;
import com.nuc.securedatabasesystem.pojo.User;
import com.nuc.securedatabasesystem.service.interfaces.UserService;
import com.nuc.securedatabasesystem.unil.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private HospitalRecordsMapper hospitalRecordsMapper;

    @Autowired
    private Tool tool;

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
            log.warn("登陆失败，校验密码错误");
            return ResultBody.error(e.getMessage());
        }
    }


    /**
     * @author DeepBlue
     * @date: 2020/11/21 20:07
     * @description:
     */
    @PostMapping("add")
    public ResultBody add(@RequestBody HospitalRecords record) {
        try {
            LocalDate now = LocalDate.now();
            String format = now.format(DateTimeFormatter.ISO_DATE);
            record.setClinicDate(format);
            tool.encryptAndSign(record);
            hospitalRecordsMapper.insert(record);
            return ResultBody.success();
        } catch (IllegalAccessException e) {
            return ResultBody.error("添加错误，请重试！");
        }
    }

    /**
     * @author DeepBlue
     * @date: 2020/11/22 16:47
     * @description: 选择
     */
    @GetMapping("select")
    public ResultBody select() {
        List<HospitalRecords> hospitalRecords = hospitalRecordsMapper.selectList(new QueryWrapper<>());
        for (HospitalRecords hospitalRecord : hospitalRecords) {
            try {
                tool.verifySignAndDecrypt(hospitalRecord);
            } catch (Exception e) {
                return ResultBody.error("数据遭到篡改，请联系管理员");
            }
        }
        return ResultBody.success(hospitalRecords);
    }

    @PostMapping("change")
    public ResultBody update(@RequestBody HospitalRecords records) throws IllegalAccessException {
        UpdateWrapper<HospitalRecords> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", records.getId());
        tool.encryptAndSign(records);
        hospitalRecordsMapper.update(records, wrapper);
        return ResultBody.success();
    }


}
