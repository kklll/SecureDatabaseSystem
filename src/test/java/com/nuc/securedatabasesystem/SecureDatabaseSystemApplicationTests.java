package com.nuc.securedatabasesystem;

import cn.hutool.crypto.SecureUtil;
import com.nuc.securedatabasesystem.core.MyAnnotation;
import com.nuc.securedatabasesystem.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@SpringBootTest
class SecureDatabaseSystemApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void encryptPassword() {
        String password = "123123";
        System.out.println(password);
        for (int i = 0; i < 10; i++) {
            password = SecureUtil.md5(password);
        }
        System.out.println("十次MD5散列后:\n" + password);
    }

    @Test
    void testSql() {
        try {
            com.nuc.securedatabasesystem.core.Test test = new com.nuc.securedatabasesystem.core.Test();
            test.setAddress("山西省临汾市");
            com.nuc.securedatabasesystem.core.Test tt = new com.nuc.securedatabasesystem.core.Test();
            Class<? extends com.nuc.securedatabasesystem.core.Test> aClass = tt.getClass();
            boolean annotations = aClass.isAnnotationPresent(MyAnnotation.class);
            MyAnnotation annotation = aClass.getAnnotation(MyAnnotation.class);
            String[] fields = annotation.fields();

            for (String s : fields) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
