package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class CalendarHourEntity {


    /**
     * hours : 1-3
     * yi : 赴任 出行 修造 動土
     * ji :  安葬 求財 見貴 嫁娶 進人口 移徙
     */

    private String hours;
    private String yi;
    private String ji;
    private String start_hour;
    private String end_hour;
    private String gan_zhi;

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getYi() {
        return yi;
    }

    public void setYi(String yi) {
        this.yi = yi;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }

    public String getGan_zhi() {
        return gan_zhi;
    }

    public void setGan_zhi(String gan_zhi) {
        this.gan_zhi = gan_zhi;
    }
}
