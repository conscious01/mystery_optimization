package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class TrendEntity {

    private List<XaxisBean> xaxis;
    private List<YaxisBean> yaxis;

    public List<XaxisBean> getXaxis() {
        return xaxis;
    }

    public void setXaxis(List<XaxisBean> xaxis) {
        this.xaxis = xaxis;
    }

    public List<YaxisBean> getYaxis() {
        return yaxis;
    }

    public void setYaxis(List<YaxisBean> yaxis) {
        this.yaxis = yaxis;
    }

    public static class XaxisBean {
        /**
         * key : 1599868800
         * value : 46
         * day_type : -1
         */

        private long key;
        private int value;
        private int day_type;

        public long getKey() {
            return key;
        }

        public void setKey(long key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getDay_type() {
            return day_type;
        }

        public void setDay_type(int day_type) {
            this.day_type = day_type;
        }
    }

    public static class YaxisBean {
        /**
         * title : 加油
         * value : 50
         */

        private String title;
        private int value;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
