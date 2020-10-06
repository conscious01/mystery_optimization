package com.zgzx.metaphysics.model.params;

import java.util.List;

public class ApplyMasterParams {

    private String phone;
    private String intro;
    private List<String> photos;

    public ApplyMasterParams(String phone, String intro, List<String> photos) {
        this.phone = phone;
        this.intro = intro;
        this.photos = photos;
    }
}
