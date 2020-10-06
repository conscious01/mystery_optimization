package com.zgzx.metaphysics.model.params;

/**
 * 1-注册 2-忘记登录密码 3-修改登录密码 4-修改支付密码
 */
public class PhoneParams {

    private String phone; // 手机号

    private String phone_code; // 区号

    private int scene; // 场景

    public PhoneParams(String phone, String code, int scene) {
        this.phone = phone;
        this.phone_code = code;
        this.scene = scene;
    }

}
