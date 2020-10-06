package com.zgzx.metaphysics.model.params;

public class VerifyParams {

    private String phone;
    private String phone_code;
    private String verify_code;

    public VerifyParams(String phone, String phone_code, String verify_code) {
        this.phone = phone;
        this.phone_code = phone_code;
        this.verify_code = verify_code;
    }

}
