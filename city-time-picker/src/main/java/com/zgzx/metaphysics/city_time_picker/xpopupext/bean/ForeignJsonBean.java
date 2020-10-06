package com.zgzx.metaphysics.city_time_picker.xpopupext.bean;

import java.util.List;

/**
 * 国外数据
 */
public class ForeignJsonBean {

    /**
     * name : 美国
     * city : ["纽约","洛杉矶"]
     */

    private String name;
    private List<String> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }

}
