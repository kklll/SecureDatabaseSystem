package com.nuc.securedatabasesystem.core;

import com.nuc.securedatabasesystem.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Author DeepBlue
 * @Date 2020/11/18 11:20
 */
@Configuration
@Aspect
@Slf4j
public class Aop {
    @Pointcut("execution(* com.nuc.securedatabasesystem.mapper.*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void doSomeThing(JoinPoint joinPoint) {
        System.out.println("******拦截前的逻辑******");
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));

        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) + "个参数为:" + args[i]);
            if (args[i].getClass().isAnnotationPresent(MyAnnotation.class)) {
                System.out.println("使用了 MyAnnotation.class 注释");
                MyAnnotation annotation = args[i].getClass().getAnnotation(MyAnnotation.class);
                String[] fields = annotation.fields();
                for (String s : fields) {
                    System.out.println("注释中的内容: " + s);
                }
                String[] filedName = getFiledName(args[i]);
                for (String s : filedName) {
                    System.out.println("属性名称：" + s);
                    System.out.println(getFieldValueByFieldName(s, args[i]));
                    if ("password".equals(s)) {
//                        setFieldValueByFieldName(s,args[i],"年轻人你不讲武德");
                        System.out.println(args[i]);
                    }
                }
            }
            System.out.println("第" + (i + 1) + "个参数全类名为:" + args[i].getClass().getName());
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }

    private String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    private void setFieldValueByFieldName(String fieldName, Object object, String value) {
        try {
            // 获取obj类的字节文件对象
            Class c = object.getClass();
            // 获取该类的成员变量
            Field f = c.getDeclaredField(fieldName);
            // 取消语言访问检查
            f.setAccessible(true);
            // 给变量赋值
            f.set(object, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

