package com.zgzx.metaphysics.model.entity;

public class NotificationEntity {

    /**
     * id : 0
     * title : 11111
     * content : 222222
     * orderId : 55555
     * openType : 1
     * time : 56456546
     * external :
     * pushdata :
     */

    private int id;
    private String title;
    private String content;
    private int orderId;
    private int openType;
    private int time;
    private String external;
    private String pushdata;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }

    public String getPushdata() {
        return pushdata;
    }

    public void setPushdata(String pushdata) {
        this.pushdata = pushdata;
    }
}
