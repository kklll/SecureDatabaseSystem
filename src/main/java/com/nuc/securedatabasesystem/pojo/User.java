package com.nuc.securedatabasesystem.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Author DeepBlue
 * @Date 2020/11/18 15:03
 */
@Data
@TableName(value = "`user`")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username",fill = FieldFill.INSERT_UPDATE)
    private String username;

    @TableField(value = "`password`")
    private String password;
}