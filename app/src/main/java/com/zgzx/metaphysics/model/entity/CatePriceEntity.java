package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class CatePriceEntity {

    /**
     * member_free_num : 8
     * onlyone_free_num : 1
     * pay_data : [{"id":31,"name":"爱情","price":30},{"id":33,"name":"资产及财运","price":30},{"id":34,"name":"家庭及人际","price":30}]
     */

    private int member_free_num;
    private int onlyone_free_num;
    private List<PayDataBean> pay_data;

    public CatePriceEntity() {
    }

    public CatePriceEntity(int member_free_num, int onlyone_free_num, List<PayDataBean> pay_data) {
        this.member_free_num = member_free_num;
        this.onlyone_free_num = onlyone_free_num;
        this.pay_data = pay_data;
    }

    public int getMember_free_num() {
        return member_free_num;
    }

    public void setMember_free_num(int member_free_num) {
        this.member_free_num = member_free_num;
    }

    public int getOnlyone_free_num() {
        return onlyone_free_num;
    }

    public void setOnlyone_free_num(int onlyone_free_num) {
        this.onlyone_free_num = onlyone_free_num;
    }

    public List<PayDataBean> getPay_data() {
        return pay_data;
    }

    public void setPay_data(List<PayDataBean> pay_data) {
        this.pay_data = pay_data;
    }

    public static class PayDataBean {
        /**
         * id : 31
         * name : 爱情
         * price : 30
         */

        private int id;
        private String name;
        private int price;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
