package com.zgzx.metaphysics.model.params;

public class RegisterParams{

    private String verify_code; // 验证码

    private String phone;

    private String login_pass; // 登录密码，明文

    private String invite_code;

    private String phone_code; // 区号，不填默认为国

    public RegisterParams(String verify_code, String phone, String login_pass, String invite_code, String phone_code) {
        this.verify_code = verify_code;
        this.phone = phone;
        this.login_pass = login_pass;
        this.invite_code = invite_code;
        this.phone_code = phone_code;
    }

}
