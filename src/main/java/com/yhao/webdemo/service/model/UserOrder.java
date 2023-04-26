package com.yhao.webdemo.service.model;

import com.yhao.webdemo.dao.model.Good;
import com.yhao.webdemo.dao.model.User;
import lombok.Data;

@Data
public class UserOrder {
    private User user;

    private Good order;

    public UserOrder(User user, Good order) {
        this.user = user;
        this.order = order;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "user=" + user +
                ", order=" + order +
                '}';
    }
}
