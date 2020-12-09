package com.nuc.securedatabasesystem;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nuc.securedatabasesystem.core.ClassScanUtil;
import com.nuc.securedatabasesystem.core.Tool;
import com.nuc.securedatabasesystem.mapper.HospitalRecordsMapper;
import com.nuc.securedatabasesystem.mapper.UserMapper;
import com.nuc.securedatabasesystem.pojo.HospitalRecords;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


@Slf4j
@SpringBootTest(properties = "application-dev.properties")
class SecureDatabaseSystemApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassScanUtil classScanUtil;

    @Autowired
    Tool tool;

    @Autowired
    HospitalRecordsMapper hospitalRecordsMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void encryptPassword() {
        String password = "password";
        System.out.println(password);
        for (int i = 0; i < 10; i++) {
            password = SecureUtil.md5(password);
        }
        System.out.println("十次MD5散列后:\n" + password);
    }

    @Test
    void testSql() throws IllegalAccessException {
        HospitalRecords hospitalRecords = new HospitalRecords();
        hospitalRecords.setAddress("山西省xxx");

        Field[] secretFiled = new Field[0];
        try {
            secretFiled = tool.getSecretFiled(hospitalRecords);
            log.info(hospitalRecords.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Field f : secretFiled) {
            f.setAccessible(true);
            System.out.println("name:" + f.getName() + " 值" + f.get(hospitalRecords));
            tool.encryptField(hospitalRecords, f);
        }
        for (Field f : secretFiled) {
            f.setAccessible(true);
            System.out.println("name:" + f.getName() + " 值" + f.get(hospitalRecords));
            tool.decryptField(hospitalRecords, f);
        }
        log.info(hospitalRecords.toString());
    }

    @Test
    public void encryptAndSignTest() throws IllegalAccessException, NoSuchFieldException {
        HospitalRecords hospitalRecords = new HospitalRecords(1,
                "张三丰", "男", "山西省太原市中北大学",
                "141033199901240098", "2001-1-1",
                "18834160999", "张医生",
                "上吐下泻，疑似新冠病毒", "内科",
                "高血压，高血糖", "建议喝点药",
                "2020-11-21", "2020-11-21", "");
        System.out.println(hospitalRecords);
        tool.encryptAndSign(hospitalRecords);
//        hospitalRecordsMapper.insert(hospitalRecords);
        System.out.println(hospitalRecords);
        try {
            tool.verifySignAndDecrypt(hospitalRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(hospitalRecords);
    }

    /**
     * 扫描com.demo.web路径下所有包含Api注解的类 * @param args
     */
    @Test
    public void main() {
        String packageName = "com.nuc.securedatabasesystem.pojo";
        Set<Class> set = classScanUtil.getClass4Annotation(packageName, TableName.class);
        System.out.println(JSON.toJSONString(set));
    }
}
