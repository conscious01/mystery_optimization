package com.zgzx.metaphysics.model.params;

public class FateBookParams {

    private int cate_id; // 分类id
    private int lifebook_id;

    // 删除命书, 命书分类, 命书分类价格
    public FateBookParams(int lifebook_id) {
        this.lifebook_id = lifebook_id;
    }

    // 获取命书详情
    public FateBookParams(int cate_id, int lifebook_id) {
        this.cate_id = cate_id;
        this.lifebook_id = lifebook_id;
    }

}
