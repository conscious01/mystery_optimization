package com.zgzx.metaphysics.model.entity;

import java.util.List;

public  class APreviewEntity {
    /**
     * code : 0
     * msg : 成功
     * data : {"id":413,"nickname":"测试安卓图片显示","sex":1,"status":2,"birth_day":1398877023,
     * "birth_hour":23,"calendar_type":2,"birth_area":"中国 辽宁省 沉阳市 和平区","issue_content":"测试一下",
     * "photos":["https://mystery-oss.oss-cn-hongkong.aliyuncs
     * .com/uploads/2020/10/06/1601971042119756690.jpg","https://mystery-oss.oss-cn-hongkong
     * .aliyuncs.com/uploads/2020/10/06/1601971042120063070.jpg"],"master_name":"陈定邦",
     * "reason":"","answer_content":"","ba_zi":{"year":"甲午","month":"戊辰","day":"癸酉","hour":"壬子"}}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 413
         * nickname : 测试安卓图片显示
         * sex : 1
         * status : 2
         * birth_day : 1398877023
         * birth_hour : 23
         * calendar_type : 2
         * birth_area : 中国 辽宁省 沉阳市 和平区
         * issue_content : 测试一下
         * photos : ["https://mystery-oss.oss-cn-hongkong.aliyuncs
         * .com/uploads/2020/10/06/1601971042119756690.jpg","https://mystery-oss.oss-cn-hongkong
         * .aliyuncs.com/uploads/2020/10/06/1601971042120063070.jpg"]
         * master_name : 陈定邦
         * reason :
         * answer_content :
         * ba_zi : {"year":"甲午","month":"戊辰","day":"癸酉","hour":"壬子"}
         */

        private int id;
        private String nickname;
        private int sex;
        private int status;
        private int birth_day;
        private int birth_hour;
        private int calendar_type;
        private String birth_area;
        private String issue_content;
        private String master_name;
        private String reason;
        private String answer_content;
        private BaZiBean ba_zi;
        private List<String> photos;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getBirth_day() {
            return birth_day;
        }

        public void setBirth_day(int birth_day) {
            this.birth_day = birth_day;
        }

        public int getBirth_hour() {
            return birth_hour;
        }

        public void setBirth_hour(int birth_hour) {
            this.birth_hour = birth_hour;
        }

        public int getCalendar_type() {
            return calendar_type;
        }

        public void setCalendar_type(int calendar_type) {
            this.calendar_type = calendar_type;
        }

        public String getBirth_area() {
            return birth_area;
        }

        public void setBirth_area(String birth_area) {
            this.birth_area = birth_area;
        }

        public String getIssue_content() {
            return issue_content;
        }

        public void setIssue_content(String issue_content) {
            this.issue_content = issue_content;
        }

        public String getMaster_name() {
            return master_name;
        }

        public void setMaster_name(String master_name) {
            this.master_name = master_name;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getAnswer_content() {
            return answer_content;
        }

        public void setAnswer_content(String answer_content) {
            this.answer_content = answer_content;
        }

        public BaZiBean getBa_zi() {
            return ba_zi;
        }

        public void setBa_zi(BaZiBean ba_zi) {
            this.ba_zi = ba_zi;
        }

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }

        public static class BaZiBean {
            /**
             * year : 甲午
             * month : 戊辰
             * day : 癸酉
             * hour : 壬子
             */

            private String year;
            private String month;
            private String day;
            private String hour;

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHour() {
                return hour;
            }

            public void setHour(String hour) {
                this.hour = hour;
            }
        }
    }
}
