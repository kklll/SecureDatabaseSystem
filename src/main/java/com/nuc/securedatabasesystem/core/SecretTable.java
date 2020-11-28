package com.nuc.securedatabasesystem.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName SecretTable
 * @Deacription TODO
 * @Author DeepBlue
 * @Date 2020/11/24 11:31
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SecretTable {
}

