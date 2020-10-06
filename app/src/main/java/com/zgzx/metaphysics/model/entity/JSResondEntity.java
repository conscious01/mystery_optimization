package com.zgzx.metaphysics.model.entity;

public class JSResondEntity {
    /**
     * navite_page_name : http://h5.test.kongming365.com/p
     * jump_type : 1
     * link_url : http://h5.test.kongming365.com/pages/divination/divination
     */

    private String navite_page_name;
    private int jump_type;
    private String link_url;

    public String getNavite_page_name() {
        return navite_page_name;
    }

    public void setNavite_page_name(String navite_page_name) {
        this.navite_page_name = navite_page_name;
    }

    public int getJump_type() {
        return jump_type;
    }

    public void setJump_type(int jump_type) {
        this.jump_type = jump_type;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
}
