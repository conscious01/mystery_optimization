package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;

public  class UserAssetEntity implements Serializable {

    /**
     * coin_available : 0
     * coin_freeze : 0
     * coin_status : 1
     * coupon_available : 0
     * coupon_freeze : 0
     * coupon_status : 1
     */

    private float coin_available;
    private float coin_freeze;
    private int coin_status;
    private float coupon_available;

    public float getCoin_available() {
        return coin_available;
    }

    public void setCoin_available(float coin_available) {
        this.coin_available = coin_available;
    }

    public float getCoin_freeze() {
        return coin_freeze;
    }

    public void setCoin_freeze(float coin_freeze) {
        this.coin_freeze = coin_freeze;
    }

    public float getCoupon_available() {
        return coupon_available;
    }

    public void setCoupon_available(float coupon_available) {
        this.coupon_available = coupon_available;
    }

    public float getCoupon_freeze() {
        return coupon_freeze;
    }

    public void setCoupon_freeze(float coupon_freeze) {
        this.coupon_freeze = coupon_freeze;
    }

    private float coupon_freeze;
    private int coupon_status;



    public void setCoin_freeze(int coin_freeze) {
        this.coin_freeze = coin_freeze;
    }

    public int getCoin_status() {
        return coin_status;
    }

    public void setCoin_status(int coin_status) {
        this.coin_status = coin_status;
    }



    public void setCoupon_available(int coupon_available) {
        this.coupon_available = coupon_available;
    }


    public void setCoupon_freeze(int coupon_freeze) {
        this.coupon_freeze = coupon_freeze;
    }

    public int getCoupon_status() {
        return coupon_status;
    }

    public void setCoupon_status(int coupon_status) {
        this.coupon_status = coupon_status;
    }
}
