package com.zgzx.metaphysics.model.params;

public class BuyMemberParams {

    private int pay_type;
    private int member_id;

    public BuyMemberParams(int pay_type, int member_id) {
        this.pay_type = pay_type;
        this.member_id = member_id;
    }
}
