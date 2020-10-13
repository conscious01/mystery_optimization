package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;
import java.util.List;

public class OrderLifeBookEntity implements Serializable {

    /**
     * order_no : 20092210555354942604
     * subject : Android-孔明在线-命书-xxx
     * origin_amount : 999
     * discount_rate : 0.8
     * coin_available : 0
     * discount_list : [{"id":1,"discount":70},{"id":2,"discount":60},{"id":3,"discount":50},{"id
     * ":4,"discount":40},{"id":5,"discount":30},{"id":6,"discount":20},{"id":7,"discount":10}]
     */
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String order_no;
    private String subject;
    private float origin_amount;
    private float discount_rate;
    private float coin_available;
    private String pay_tips;
    private List<DiscountListBean> discount_list;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public float getOrigin_amount() {
        return origin_amount;
    }

    public void setOrigin_amount(float origin_amount) {
        this.origin_amount = origin_amount;
    }

    public String getPay_tips() {
        return pay_tips;
    }

    public void setPay_tips(String pay_tips) {
        this.pay_tips = pay_tips;
    }

    public float getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(float discount_rate) {
        this.discount_rate = discount_rate;
    }

    public float getCoin_available() {
        return coin_available;
    }

    public void setCoin_available(float coin_available) {
        this.coin_available = coin_available;
    }

    public List<DiscountListBean> getDiscount_list() {
        return discount_list;
    }

    public void setDiscount_list(List<DiscountListBean> discount_list) {
        this.discount_list = discount_list;
    }

    public static class DiscountListBean implements Serializable {
        /**
         * id : 1
         * discount : 70
         */

        private int id;
        private int discount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }
    }
}
