package com.yhao.webdemo.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pms_role_resource")
public class RoleResource {
    private Integer id;

    private Integer roleId;

    private Integer sourceId;

    private Date createTime;

    private Date updateTime;
}
