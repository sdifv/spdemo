package com.yhao.webdemo.dao.dynamic;

public enum DataSourceEnum {

    MASTER("master"),
    SLAVE("slave");

    private String name;

    DataSourceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
