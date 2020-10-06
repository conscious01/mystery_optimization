package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class MessageEntity {


    /**
     * page : 1
     * page_size : 10
     * total : 1
     * items : [{"id":1,"title":"系统消息1","url":"http://localhost:8080/message?id=1&&type=2"}]
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
         * id : 1
         * title : 系统消息1
         * url : http://localhost:8080/message?id=1&&type=2
         */
        private long create_time;

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        private int id;

        public int getIssue_id() {
            return issue_id;
        }

        public void setIssue_id(int issue_id) {
            this.issue_id = issue_id;
        }

        private int issue_id;

        public int getJump_type() {
            return jump_type;
        }

        public void setJump_type(int jump_type) {
            this.jump_type = jump_type;
        }

        private int jump_type;
        private String title;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
