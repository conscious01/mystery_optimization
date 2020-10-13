package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class HomeFouncationEntity {


    private List<Icon1Bean> icon1;
    private List<Icon2Bean> icon2;
    private List<BannerBean> banner;

    public List<Icon1Bean> getIcon1() {
        return icon1;
    }

    public void setIcon1(List<Icon1Bean> icon1) {
        this.icon1 = icon1;
    }

    public List<Icon2Bean> getIcon2() {
        return icon2;
    }

    public void setIcon2(List<Icon2Bean> icon2) {
        this.icon2 = icon2;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public static class Icon1Bean {
        /**
         * id : 3
         * name : 每日祈福
         * icon : https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/v1_0_0/home_cp_mrqf.png
         * link_url :
         * jump_type : 2
         * navite_page_name : meiriqifu
         */

        private int id;
        private String name;
        private String icon;
        private String link_url;
        private int jump_type;
        private String navite_page_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
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

    public static class Icon2Bean {
        /**
         * id : 8
         * name : 小六壬
         * icon : http://mystery-oss.oss-cn-hongkong.aliyuncs.com/admin/image/20200924/8e86ca9fc9a02dd779adc68f33cddaf74c07dc6c.png
         * link_url : https://www.baidu.com/
         * jump_type : 1
         * navite_page_name :
         */

        private int id;
        private String name;
        private String icon;
        private String link_url;
        private int jump_type;
        private String navite_page_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
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

    public static class BannerBean {
        /**
         * id : 1
         * name : 真命之书
         * icon : https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/v1_0_0/home_cp_zmzs.png
         * link_url :
         * jump_type : 2
         * navite_page_name : mingshu
         */

        private int id;
        private String name;
        private String icon;
        private String link_url;
        private int jump_type;
        private String navite_page_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
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
}
