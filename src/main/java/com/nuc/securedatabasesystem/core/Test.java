package com.nuc.securedatabasesystem.core;

import lombok.Data;

/**
 * @Author DeepBlue
 * @Date 2020/11/18 20:30
 */
@MyAnnotation(fields = {"我的", "你的"})
@Data
public class Test {
    private String name;
    private String sex;
    private String address;
}
