package com.zgzx.metaphysics.model.params;

public class CreateFateBookParams {

    /**
     * nickname : ssas
     * birth_day : 1595398597
     * birth_hour : -1
     * sex : 2
     */

    private String realname;
    private int birth_day;
    private int birth_hour;
    private int sex;

    public CreateFateBookParams(String realname, int birth_day, int birth_hour, int sex) {
        this.realname = realname;
        this.birth_day = birth_day;
        this.birth_hour = birth_hour;
        this.sex = sex;
    }

}
