package com.zgzx.metaphysics.model.params;

public class TransferParams {

    private long to_uid;
    private float num;
    private String pay_pass;

    public TransferParams(long to_uid, float num, String pay_pass) {
        this.to_uid = to_uid;
        this.num = num;
        this.pay_pass = pay_pass;
    }

}
