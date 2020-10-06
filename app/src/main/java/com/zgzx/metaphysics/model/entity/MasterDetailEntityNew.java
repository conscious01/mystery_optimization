package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;
import java.util.List;

public class MasterDetailEntityNew implements Serializable {

    /**
     * id : 1
     * master_name : 陈定邦
     * photos : [{"id":1,"master_id":1,"path":"https://mystery-oss.oss-cn-hongkong.aliyuncs
     * .com/default_avatar.png","create_time":0},{"id":2,"master_id":1,
     * "path":"https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png",
     * "create_time":0},{"id":3,"master_id":1,"path":"https://mystery-oss.oss-cn-hongkong
     * .aliyuncs.com/default_avatar.png","create_time":0}]
     * fields : 紫薇斗数,八字,奇门遁甲
     * score : 3
     * intro : 精通五行八卦,上知天文,下知地理
     * answer_num : 100
     * price : 100
     * limit_time_price : 0
     * vip_price : 80
     * vip_discount : 0.8
     * career_years : 1
     */

    private int id;
    private String master_name;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String avatar;
    private String fields;
    private int score;
    private String intro;
    private int answer_num;
    private int price;
    private int limit_time_price;
    private int vip_price;
    private double vip_discount;
    private int career_years;
    private List<PhotosBean> photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaster_name() {
        return master_name;
    }

    public void setMaster_name(String master_name) {
        this.master_name = master_name;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getAnswer_num() {
        return answer_num;
    }

    public void setAnswer_num(int answer_num) {
        this.answer_num = answer_num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLimit_time_price() {
        return limit_time_price;
    }

    public void setLimit_time_price(int limit_time_price) {
        this.limit_time_price = limit_time_price;
    }

    public int getVip_price() {
        return vip_price;
    }

    public void setVip_price(int vip_price) {
        this.vip_price = vip_price;
    }

    public double getVip_discount() {
        return vip_discount;
    }

    public void setVip_discount(double vip_discount) {
        this.vip_discount = vip_discount;
    }

    public int getCareer_years() {
        return career_years;
    }

    public void setCareer_years(int career_years) {
        this.career_years = career_years;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public static class PhotosBean implements Serializable {
        /**
         * id : 1
         * master_id : 1
         * path : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
         * create_time : 0
         */

        private int id;
        private int master_id;
        private String path;
        private int create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMaster_id() {
            return master_id;
        }

        public void setMaster_id(int master_id) {
            this.master_id = master_id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
