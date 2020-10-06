package com.zgzx.metaphysics.model.entity;

public class SystemMessageEntity {

    /**
     * id : 1
     * title : 系统消息-余额不足
     * content : test
     * create_time : 1594974316
     * update_time : 1594974316
     */

    private int id;
    private String title;
    private String content;
    private String url;
    private int create_time;
    private int update_time;

    public String getUrl() {
        return url;
    }

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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }
}
