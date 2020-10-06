package com.zgzx.metaphysics.model.entity;

public class PropertyBillEntity {

    /**
     * id : 61
     * uid : 1000025
     * opt : 1
     * type : 6
     * sub_type : 9
     * amount : 99
     * before : 0
     * after : 99
     * money_type : 4
     * status : 1
     * remark : 划转
     * create_time : 1596879312
     * update_time : 0
     */

    private int id;
    private int uid;
    private int opt;
    private int type;
    private int sub_type;
    private float amount;
    private float before;
    private float after;
    private int money_type;
    private int status;
    private String remark;
    private int create_time;
    private int update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSub_type() {
        return sub_type;
    }

    public void setSub_type(int sub_type) {
        this.sub_type = sub_type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getBefore() {
        return before;
    }

    public void setBefore(float before) {
        this.before = before;
    }

    public float getAfter() {
        return after;
    }

    public void setAfter(float after) {
        this.after = after;
    }

    public int getMoney_type() {
        return money_type;
    }

    public void setMoney_type(int money_type) {
        this.money_type = money_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
