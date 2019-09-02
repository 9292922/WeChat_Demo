package com.example.wechat_demo;

import java.io.Serializable;

/*
 * 实现功能:
 * 是每个item的数据对象，实现getter，setter方法
 * */
public class Messages implements Serializable {//定义一个message类来存储朋友圈数据
    String name;
    String context;
    String time;
    String place;
    int type;
    Boolean isAgree;
    String typeText;
    int icon;

    public void setIsAgree(Boolean b) {
        this.isAgree = b;
    }

    public Boolean getIsAgree() {
        return this.isAgree;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setAgree(Boolean agree) {
        isAgree = agree;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getContext() {
        return context;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public Boolean getAgree() {
        return isAgree;
    }

    public String getTypeText() {
        return typeText;
    }

    public int getIcon() {
        return icon;
    }
}
