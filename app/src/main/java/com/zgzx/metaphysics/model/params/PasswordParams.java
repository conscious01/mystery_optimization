package com.zgzx.metaphysics.model.params;

/**
 * 忘记登录密码，修改登录密码，修改资金密码
 */
public class PasswordParams {

    private String new_pass; // 登录密码，明文，格式：8-20位数字+字母/符号组合
    private String phone_code; // 区号，不填默认为国内
    private String phone; // 手机号
    private String verify_code;  // 手机验证码，短信服务关闭时默认”12345”

    public PasswordParams(String new_pass, String phone_code, String phone, String verify_code) {
        this.new_pass = new_pass;
        this.phone_code = phone_code;
        this.phone = phone;
        this.verify_code = verify_code;
    }

}
