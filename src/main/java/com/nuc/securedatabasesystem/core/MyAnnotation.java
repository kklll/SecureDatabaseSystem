package com.nuc.securedatabasesystem.core;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * @Author DeepBlue
 * @Date 2020/11/18 16:18
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String[] fields() default "unknown";
}

