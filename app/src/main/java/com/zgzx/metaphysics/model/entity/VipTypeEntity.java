package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class VipTypeEntity {


        /**
         * id : 1
         * name : 单月会员
         * remark : 有效期内命书免费12次，师傅问答9.5折
         * icon : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
         * data_json : [{"name":"月度尊享","icon":"https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png"},{"name":"12次免费","icon":"https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png"},{"name":"赠送100孔明珠","icon":"https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png"},{"name":"划转孔明珠","icon":"https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png"},{"name":"敬请期待","icon":"https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png"}]
         * price : 999
         * lifebook_free_num : 12
         * questions_discount_rate : 0.95
         * create_time : 0
         * update_time : 0
         */

        private int id;
        private String name;
        private String remark;
        private String icon;
        private float price;
        private int lifebook_free_num;
        private double questions_discount_rate;
        private int create_time;
        private int update_time;
        private  boolean isSelect;
        private List<DataJsonBean> data_json;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public float getPrice() {
            return price;
        }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setPrice(float price) {
            this.price = price;
        }

        public int getLifebook_free_num() {
            return lifebook_free_num;
        }

        public void setLifebook_free_num(int lifebook_free_num) {
            this.lifebook_free_num = lifebook_free_num;
        }

        public double getQuestions_discount_rate() {
            return questions_discount_rate;
        }

        public void setQuestions_discount_rate(double questions_discount_rate) {
            this.questions_discount_rate = questions_discount_rate;
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

        public List<DataJsonBean> getData_json() {
            return data_json;
        }

        public void setData_json(List<DataJsonBean> data_json) {
            this.data_json = data_json;
        }

        public static class DataJsonBean {
            /**
             * name : 月度尊享
             * icon : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
             */

            private String name;
            private String icon;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

}
