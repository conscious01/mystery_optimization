package com.zgzx.metaphysics.model.entity;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zgzx.metaphysics.ui.fragments.FindFragment;

/**
 * 师傅服务列表
 *
 * @see FindFragment#onRefresh(RefreshLayout)
 */
public class MasterServiceEntity {

    /**
     * id : 1
     * master_name : 王师傅
     * avatar : https://www.showdoc.cc/favicon.ico
     * score : 3
     * intro : 不错
     * answer_num : 10
     * status : 1
     * charge_amount : 66.66
     * symbol : USDT
     */

    private int id;
    private String master_name;
    private String avatar;
    private float score;
    private String intro;
    private int answer_num;
    private int status;
    private double charge_amount;
    private String symbol;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getScore() {
        return score;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCharge_amount() {
        return charge_amount;
    }

    public void setCharge_amount(double charge_amount) {
        this.charge_amount = charge_amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
