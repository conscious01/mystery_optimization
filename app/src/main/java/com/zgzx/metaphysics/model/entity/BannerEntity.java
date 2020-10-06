package com.zgzx.metaphysics.model.entity;

public class BannerEntity {


    /**
     * id : 1
     * title : banner1
     * image : http://mystery-oss.oss-cn-hongkong.aliyuncs.com/admin/image/20200924/67c5c5fa235d859465aae58db2fc3ee7d11ca9bb.jpg
     * link_url : https://www.baidu.com/
     * bg_color : 0xFFFFFF
     * bg_image : https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/index/banner_main.png
     * jump_type : 2
     * navite_page_name : mingshu
     */

    private int id;
    private String title;
    private String image;
    private String link_url;
    private String bg_color;
    private String bg_image;
    private int jump_type;
    private String navite_page_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getBg_image() {
        return bg_image;
    }

    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
    }

    public int getJump_type() {
        return jump_type;
    }

    public void setJump_type(int jump_type) {
        this.jump_type = jump_type;
    }

    public String getNavite_page_name() {
        return navite_page_name;
    }

    public void setNavite_page_name(String navite_page_name) {
        this.navite_page_name = navite_page_name;
    }
}
