package com.zgzx.metaphysics.model.params;

public class OrderParams {
    private int page;
    private int page_size;

    public OrderParams(int page, int page_size, int status) {
        this.page = page;
        this.page_size = page_size;
        this.status = status;
    }

    private int status;
}
