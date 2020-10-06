package com.zgzx.metaphysics.model.entity;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description:
 */
public class RechargeRecordEntity {


    /**
     * id : 1000209
     * today : 0
     * total : 0
     */

    private int id;
    private float today;
    private float total;
    private float amount;
    private int create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getToday() {
        return today;
    }

    public float getTotal() {
        return total;
    }

    public float getAmount() {
        return amount;
    }

    public int getCreate_time() {
        return create_time;
    }

}
