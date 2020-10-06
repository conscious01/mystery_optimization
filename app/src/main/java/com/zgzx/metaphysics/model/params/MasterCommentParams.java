package com.zgzx.metaphysics.model.params;

public class MasterCommentParams {

    private int  master_id,uid;
    private int page;
    private int page_size;

    public MasterCommentParams(int master_id, int uid,int page, int page_size) {
        this.master_id = master_id;
        this.uid = uid;

        this.page = page;
        this.page_size = page_size;
    }


}
