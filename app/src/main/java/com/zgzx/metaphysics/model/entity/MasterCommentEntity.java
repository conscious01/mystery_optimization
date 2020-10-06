package com.zgzx.metaphysics.model.entity;

import java.util.List;

public  class MasterCommentEntity {
    /**
     * page : 3
     * page_size : 2
     * total : 5
     * items : [{"id":5,"avatar":"https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar
     * .png","realname":"","score":2,"create_time":1599209849},{"id":8,"avatar":"https://mystery
     * -oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png","realname":"","score":5,
     * "create_time":1599555652}]
     */

    private int page;
    private int page_size;
    private int total;
    private List<ItemsBean> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 5
         * avatar : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
         * realname :
         * score : 2
         * create_time : 1599209849
         */

        private int id;
        private String avatar;
        private String realname;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        private String nickname;
        private int score;
        private int create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
