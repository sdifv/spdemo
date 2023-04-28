package com.yhao.webdemo.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pms_resource")
public class Resource {

    private Integer id;

    private String identify;

    private String uri;

    private String description;

    private Date createTime;

    private Date updateTime;
}
