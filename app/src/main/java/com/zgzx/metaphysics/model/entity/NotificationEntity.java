package com.zgzx.metaphysics.model.entity;

public class NotificationEntity {

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", orderId=" + orderId +
                ", openType=" + openType +
                ", time=" + time +
                ", external='" + external + '\'' +
                ", pushdata='" + pushdata + '\'' +
                '}';
    }

    public NotificationEntity(int id, String title, String content, long orderId, int openType,
                              long time, String external, String pushdata) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.orderId = orderId;
        this.openType = openType;
        this.time = time;
        this.external = external;
        this.pushdata = pushdata;
    }

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
    private long orderId;
    private int openType;
    private long time;
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

    public long getOrderId() {
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

    public long getTime() {
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
