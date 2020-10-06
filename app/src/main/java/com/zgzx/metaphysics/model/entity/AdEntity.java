package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class AdEntity {

    /**
     * app_id : 1000
     * ad_ids : [945513392,945489461]
     */

    private int app_id;
    private List<Integer> ad_ids;

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public List<Integer> getAd_ids() {
        return ad_ids;
    }

    public void setAd_ids(List<Integer> ad_ids) {
        this.ad_ids = ad_ids;
    }
}
