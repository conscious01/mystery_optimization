package com.zgzx.metaphysics.model.params;

import java.util.List;

/**
 * 购买命书
 */
public class BuyFateBookParams {

    private int lifebook_id;
    private String pay_pass;
    private int is_all; //否是购买全部 1-否 2-是
    private List<Integer> cate_id;
    private int pay_type = 1; // 购买类型 1-孔明珠 2-单个命书购买 3-整本命书购买

    public BuyFateBookParams(int lifebook_id, String pay_pass, int is_all, int pay_type, List<Integer> cate_id) {
        this.lifebook_id = lifebook_id;
        this.pay_pass = pay_pass;
        this.is_all = is_all;
        this.cate_id = cate_id;
        this.pay_type = pay_type;
    }

}
