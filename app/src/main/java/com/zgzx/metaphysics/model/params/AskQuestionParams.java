package com.zgzx.metaphysics.model.params;

import java.util.List;

public class AskQuestionParams {

    private String nickname;
    private int sex;
    private int master_id;
    private long birth_day;
    private int birth_hour;
    private int calendar_type;
    private String birth_area;
    private String content;
    private List<String> paths;

    public AskQuestionParams(String nickname, int gender, int master_id, long birth_day,
                             int birth_hour, int calendar_type, String birth_area, String content
            , List<String> paths) {
        this.nickname = nickname;
        this.sex = gender;
        this.master_id = master_id;
        this.birth_day = birth_day;
        this.birth_hour = birth_hour;
        this.calendar_type = calendar_type;
        this.birth_area = birth_area;
        this.content = content;
        this.paths = paths;
    }
}
