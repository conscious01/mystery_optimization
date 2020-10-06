package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;

public class OrderResultEntity implements Serializable {
    /**
     * id : 7
     * orderno : 202009091599646512192275
     * trade_name : Android-孔明在线-普通提问-陈定邦大师
     * price : 100
     * create_time : 1599646512
     * pay_time : 0
     * trans_time : 0
     */

    public int id;
    public String orderno;
    public String trade_name;
    public int price;
    public int create_time;
    public int pay_time;
    public int trans_time;
    public int  status , end_time;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }
//         * end_time : 1719699

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getPay_time() {
        return pay_time;
    }

    public void setPay_time(int pay_time) {
        this.pay_time = pay_time;
    }

    public int getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(int trans_time) {
        this.trans_time = trans_time;
    }
}
