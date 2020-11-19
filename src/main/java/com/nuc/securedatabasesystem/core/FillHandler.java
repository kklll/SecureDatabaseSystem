package com.nuc.securedatabasesystem.core;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Author DeepBlue
 * @Date 2020/11/18 15:12
 */
@Slf4j
@Component
public class FillHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        String[] getterNames = metaObject.getGetterNames();
        System.out.println(getterNames);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
