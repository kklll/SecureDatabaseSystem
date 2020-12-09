package com.nuc.securedatabasesystem.core;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nuc.securedatabasesystem.unil.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @Author DeepBlue
 * @Date 2020/11/20 9:20
 */
@Component
@Slf4j
@Configuration
public class Tool {

    @Value("${mail.password}")
    private String password;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 对象的需要加密的所有字段进行加密
     */
    public void encrypt(Object o) throws IllegalAccessException {
        Field[] secretFiled = getSecretFiled(o);
        for (Field f : secretFiled) {
            encryptField(o, f);
        }
        log.info("加密完成！");
    }

    /**
     * 加密且签名
     */
    public void encryptAndSign(Object o) throws IllegalAccessException {
        encrypt(o);
        sign(o);
    }

    /**
     * @author DeepBlue
     * @date: 2020/11/21 16:23
     * @description: 进行签名，签名算法MD5
     */
    public void sign(Object o) throws IllegalAccessException {
        try {
            //获取类中的sign属性
            Field sign = o.getClass().getDeclaredField("sign");
            //获取类的所有属性加起来的一个字符串
            String content = getContent(o);
            String secContent = content + jwtUtils.getSIGN();
            //这里可以看到我们加密是每个属性的属性值+起来然后最后加一个我们服务器密钥进行MD5摘要运算
            //即 签名内容=MD5(内容+服务器密钥)
            //设置访问权限
            sign.setAccessible(true);
            sign.set(o, SecureUtil.md5(secContent));
            log.info("签名完成！");
        } catch (NoSuchFieldException e) {
            log.error("Sign字段未找到！不能进行签名！");
        }
    }

    /**
     * @author DeepBlue
     * @date: 2020/11/21 16:48
     * @description: 获取类的所有属性的一个字符串
     */
    public String getContent(Object o) throws IllegalAccessException {
        Field[] declaredFields = o.getClass().getDeclaredFields();
        //使用StringBuilder提高运行效率
        StringBuilder sb = new StringBuilder();
        for (Field f : declaredFields) {
            //设置访问权限
            f.setAccessible(true);
            //id值因为要进行回填所以我们不对这个字段进行签名
            if ("id".equals(f.getName().toLowerCase()) || "sign".equals(f.getName().toLowerCase())) {
                continue;
            }
            sb.append(f.get(o));
        }
        return sb.toString();
    }

    public boolean verify(Object o) throws Exception {
        String content = getContent(o);
        String secContent = content + jwtUtils.getSIGN();
        Field sign = o.getClass().getDeclaredField("sign");
        sign.setAccessible(true);
        return SecureUtil.md5(secContent).equals(sign.get(o));
    }


    /**
     * 验证签名且解密
     * 如果被人篡改了那么没有解密的必要了
     * todo 签名验证失败后的动作
     */
    public void verifySignAndDecrypt(Object o) throws Exception {
        if (verify(o)) {
            log.info("签名验证成功！");
            decryptAllField(o);
        } else {

            sendMail(o);
            throw new Exception("签名验证失败");
            //后续操作，比如发送邮件
        }
    }

    public void sendMail(Object o) {
        //新开一个线程，不阻塞主线程，这好吗？这好
        new Thread(() -> {
            log.error("数据篡改警告！");
            log.error("发送邮件！");
            MailAccount account = new MailAccount();
            account.setHost("smtp.163.com");
            account.setPort(465);
            account.setFrom("the__deep@163.com");
            account.setAuth(true);
            account.setSslEnable(true);
            account.setUser("the__deep@163.com");
            account.setPass(password);
            MailUtil.send(account, "1012946585@qq.com", "数据篡改警报!", "数据遭到篡改:" + JSON.toJSONString(o), false);
        }).start();
    }

    /**
     * 获取对象哪些字段需要被加密，即获取@Secret注解修饰的字段
     */
    public Field[] getSecretFiled(Object o) throws IllegalAccessException {
        ArrayList<Field> result = new ArrayList<>();
        //获取要加密的目标的Class类
        Class<?> aClass = o.getClass();
        //如果目标对象被TableName这个注解包裹了的话就说明这个类需要被加密。
        if (aClass.isAnnotationPresent(SecretTable.class)) {
            log.info("{}:是数据类", aClass.getName());
            //获取类的属性
            Field[] fields = aClass.getDeclaredFields();
            for (Field f : fields) {
                //如果这个类其中的属性被Secret注解标注了，应该对此字段进行加密或者解密
                if (f.isAnnotationPresent(Secret.class)) {
                    result.add(f);
                }
            }
        }
        return result.toArray(new Field[0]);
    }

    /**
     * 对属性进行加密
     */
    public void encryptField(Object o, Field field) throws IllegalAccessException {
        //先设置访问权限
        field.setAccessible(true);
        //这里用了JWT认证的密钥进行AES加密，首先获取签名的密钥然后加上字段值，然后进行MD5，散列计算，生成安全的密钥
        String s1 = SecureUtil.md5(jwtUtils.getSIGN() + field.getName());
        System.out.println("密钥:" + s1);
        byte[] key = s1.getBytes();
        //创建AES加密算法对象
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        if (field.get(o) == null) {
            field.set(o, "空");
        }
        String encryptHex = aes.encryptHex(((String) field.get(o)).getBytes());
        field.set(o, encryptHex);
        log.info("加密后的HEX为{}", encryptHex);
    }

    /**
     * 对属性进行解密
     */
    public void decryptField(Object o, Field field) throws IllegalAccessException {
        //创建AES加密算法对象
        //这里用了JWT认证的密钥进行AES加密，首先获取签名的密钥然后加上字段值，然后进行MD5，散列计算，生成安全的密钥
        field.setAccessible(true);
        String s1 = SecureUtil.md5(jwtUtils.getSIGN() + field.getName());
        byte[] key = s1.getBytes();
        System.out.println("密钥:" + s1);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        String decryptStr = aes.decryptStr((String) field.get(o));
        field.set(o, decryptStr);
//        log.info("解密后的内容为{}", decryptStr);
    }

    /**
     * @author DeepBlue
     * @date: 2020/11/21 16:58
     * @description: 解密所有的属性
     */
    public void decryptAllField(Object o) throws IllegalAccessException {
        Field[] secretFiled = getSecretFiled(o);
        for (Field f : secretFiled) {
            decryptField(o, f);
        }
        log.info("解密成功！");
    }
}
