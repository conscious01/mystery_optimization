package com.zgzx.metaphysics.model.params;

import java.util.List;

public class OrderLifeBookParams {
    private int[] cate_ids;
    private int lifebook_id;

    public OrderLifeBookParams(int[] cate_id, int lifebook_id) {
        this.cate_ids = cate_id;
        this.lifebook_id = lifebook_id;
    }
}
