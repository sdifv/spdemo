package com.yhao.webdemo.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pms_role")
public class Role {

    private Integer id;

    private String name;

    private Integer count;

    private Date createTime;

    private Date updateTime;
}
