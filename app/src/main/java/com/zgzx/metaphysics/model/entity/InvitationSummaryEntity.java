package com.zgzx.metaphysics.model.entity;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description: 我的邀请
 */
public class InvitationSummaryEntity {

    /**
     * inviter_id : 1000184
     * today_count : 4
     * total_count : 4
     * today_top_up : 2000
     * total_top_up : 2000
     */

    private int inviter_id;
    private int today_count;
    private int total_count;
    private float today_top_up;
    private float total_top_up;

    public int getInviter_id() {
        return inviter_id;
    }

    public int getToday_count() {
        return today_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public float getToday_top_up() {
        return today_top_up;
    }

    public float getTotal_top_up() {
        return total_top_up;
    }
}
