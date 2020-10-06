package com.zgzx.metaphysics.model.entity;

/**
 * created by alexwu
 * on 2020/10/1 12:28
 *
 * @description
 */
public class TestEntity {

    String title;
    long countDowntime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCountDowntime() {
        return countDowntime;
    }

    public void setCountDowntime(long countDowntime) {
        this.countDowntime = countDowntime;
    }

    public TestEntity(String title, long countDowntime) {
        this.title = title;
        this.countDowntime = countDowntime;
    }
}
