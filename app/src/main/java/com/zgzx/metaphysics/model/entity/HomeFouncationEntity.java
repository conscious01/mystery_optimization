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
         * id : 1
         * name : 运势
         * icon : https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/index/entry_fortune.png
         * link_url : https://www.baidu.com/
         */

        private int id;
        private String name;
        private String icon;
        private String link_url;

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
    }

    public static class Icon2Bean {
        /**
         * id : 3
         * name : 师傅
         * icon : https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/index/entry_master.png
         * link_url : https://www.baidu.com/
         */

        private int id;
        private String name;
        private String icon;
        private String link_url;

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
    }

    public static class BannerBean {
        /**
         * id : 8
         * name : 咨询
         * icon : https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/index/entry_consult.png
         * link_url : https://www.baidu.com/
         */

        private int id;
        private String name;
        private String icon;
        private String link_url;

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
    }
}
