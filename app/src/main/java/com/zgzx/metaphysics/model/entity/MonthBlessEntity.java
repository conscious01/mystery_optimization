package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class MonthBlessEntity {

    /**
     * continue_day : 2
     * month_data : [{"day":1,"content":""},{"day":2,"content":""},{"day":3,"content":""},{"day":4,"content":""},{"day":5,"content":""},{"day":6,"content":""},{"day":7,"content":""},{"day":8,"content":"34324324234"},{"day":9,"content":"34324324234"},{"day":10,"content":""},{"day":11,"content":""},{"day":12,"content":""},{"day":13,"content":""},{"day":14,"content":""},{"day":15,"content":""},{"day":16,"content":""},{"day":17,"content":""},{"day":18,"content":""},{"day":19,"content":""},{"day":20,"content":""},{"day":21,"content":""},{"day":22,"content":""},{"day":23,"content":""},{"day":24,"content":""},{"day":25,"content":""},{"day":26,"content":""},{"day":27,"content":""},{"day":28,"content":""},{"day":29,"content":""},{"day":30,"content":""}]
     * today_total : 58699
     * user_total : 3
     */

    private int continue_day;
    private int today_total;
    private int user_total;
    private List<MonthDataBean> month_data;

    public int getContinue_day() {
        return continue_day;
    }

    public void setContinue_day(int continue_day) {
        this.continue_day = continue_day;
    }

    public int getToday_total() {
        return today_total;
    }

    public void setToday_total(int today_total) {
        this.today_total = today_total;
    }

    public int getUser_total() {
        return user_total;
    }

    public void setUser_total(int user_total) {
        this.user_total = user_total;
    }

    public List<MonthDataBean> getMonth_data() {
        return month_data;
    }

    public void setMonth_data(List<MonthDataBean> month_data) {
        this.month_data = month_data;
    }

    public static class MonthDataBean {
        /**
         * day : 1
         * content :
         */

        private int day;
        private String content;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
