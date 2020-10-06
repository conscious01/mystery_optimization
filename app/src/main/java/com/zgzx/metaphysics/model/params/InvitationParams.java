package com.zgzx.metaphysics.model.params;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description: 充值记录
 */
public class InvitationParams {

    private int today_sort; // 今日排序 1-倒序 2-正序，两种排序只能同时选一种
    private int total_sort; // 累计排序 1-倒序 2-正序

    private int page;
    private int page_size = 10;

    public InvitationParams(int today_sort, int total_sort, int page) {
        this.page = page;
        this.today_sort = today_sort;
        this.total_sort = total_sort;
    }

}
