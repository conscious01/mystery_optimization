package com.zgzx.metaphysics.model.params;

import java.util.List;

public class BuyBookParams {

    /**
     * pay_type : 1
     * lifebook_id : 1
     * cate_ids : [31]
     */

    private int pay_type;
    private String order_no;
    private int discount_id;

    public BuyBookParams(int pay_type, String order_no, int discount_id) {
        this.pay_type = pay_type;
        this.order_no = order_no;
        this.discount_id = discount_id;
    }
}
