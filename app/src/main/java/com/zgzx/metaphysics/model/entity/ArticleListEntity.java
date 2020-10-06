package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class ArticleListEntity {


        /**
         * page : 1
         * page_size : 10
         * total : 4
         * items : [{"id":5,"title":"33333","link_url":"https://www.baidu.com","cate_id":2,"look_num":33333,"real_look_num":2147483647,"create_time":1599551428},{"id":1,"title":"2021年运势解读","link_url":"https://www.baidu.com","cate_id":1,"look_num":100,"real_look_num":1,"create_time":1599466544},{"id":2,"title":"2021年黄道吉日","link_url":"https://www.baidu.com","cate_id":2,"look_num":199,"real_look_num":1,"create_time":1599466544},{"id":3,"title":"2021年生命灵数","link_url":"https://www.baidu.com","cate_id":3,"look_num":299,"real_look_num":0,"create_time":1599466544}]
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
             * title : 33333
             * link_url : https://www.baidu.com
             * cate_id : 2
             * look_num : 33333
             * real_look_num : 2147483647
             * create_time : 1599551428
             */

            private int id;
            private String title;
            private String content;
            private String desc;
            private String link_url;

            private int cate_id;
            private int look_num;
            private int real_look_num;
            private int create_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public int getCate_id() {
                return cate_id;
            }

            public void setCate_id(int cate_id) {
                this.cate_id = cate_id;
            }

            public int getLook_num() {
                return look_num;
            }

            public void setLook_num(int look_num) {
                this.look_num = look_num;
            }

            public int getReal_look_num() {
                return real_look_num;
            }

            public void setReal_look_num(int real_look_num) {
                this.real_look_num = real_look_num;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }
        }

}
