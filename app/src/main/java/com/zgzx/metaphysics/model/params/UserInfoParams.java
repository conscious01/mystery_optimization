package com.zgzx.metaphysics.model.params;

/**
 * 完善用户信息
 */
public class UserInfoParams {
    private String birth_time; // 头像

    private String avatar; // 头像

    private String nickname; // 昵称

    private String realname; // 真实姓名

    private int sex; // 性别，1-男，2-女

    private int birth_day; // 出生年月日，格式：1595398597

    private int birth_hour; // 出生时辰，[-1]-未知，[1-24]-其他

    private int calendar_type; // 日历类型，1-农历，2-阳历

    private String birth_area; // 出生地区

    private String live_area; // 居住地


    // 更改用户头像
    public UserInfoParams(String avatar) {
        this.avatar = avatar;
    }

    // 更新用户信息
    public UserInfoParams(String birth_time,String nickname, int birth_day, int birth_hour, int calendar_type, String birth_area, String live_area) {
       this.birth_time=birth_time;
        this.nickname = nickname;
        this.birth_day = birth_day;
        this.birth_hour = birth_hour;
        this.calendar_type = calendar_type;
        this.birth_area = birth_area;
        this.live_area = live_area;
    }

    // 完善用户信息
    public UserInfoParams(String birth_time, String avatar, String nickname, int sex, int birth_day, int birth_hour, int calendar_type, String birth_area) {
        this.birth_time = birth_time;
        this.avatar = avatar;
        this.nickname = nickname;
        this.sex = sex;
        this.birth_day = birth_day;
        this.birth_hour = birth_hour;
        this.calendar_type = calendar_type;
        this.birth_area = birth_area;
    }

}
