package com.zgzx.metaphysics.model.params;

public  class RefusedParams {
    private int issue_id;

    public RefusedParams(int issue_id, String reason) {
        this.issue_id = issue_id;
        this.reason = reason;
    }

    private String reason;
}
