package com.kuilei.zhuyi.bean;

import java.io.Serializable;

/**
 * Created by lenovog on 2016/6/28.
 */
public class ChannelItem implements Serializable {

    public Integer id;

    public  String name;

    public Integer orderId;

    public Integer selected;

    public ChannelItem() {
    }

    public ChannelItem(Integer id, String name, Integer orderId, Integer selected) {
        this.id = id;
        this.name = name;
        this.orderId = orderId;
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "ChannelItem [id=" + this.id + ", name=" + this.name
                + ", selected=" + this.selected + "]";
    }
}
