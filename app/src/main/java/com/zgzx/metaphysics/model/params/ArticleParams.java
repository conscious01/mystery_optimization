package com.zgzx.metaphysics.model.params;

public class ArticleParams {
    private int cate_id;
    private int page;
    private int page_size;

    public ArticleParams(int cate_id, int page, int page_size) {
        this.cate_id = cate_id;
        this.page = page;
        this.page_size = page_size;
    }
}
