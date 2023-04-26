package com.yhao.webdemo.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Good {

    private Integer id;

    private double price;

    private Integer uid;

    public Good(double price, int uid) {
        this.price = price;
        this.uid = uid;
    }
}
