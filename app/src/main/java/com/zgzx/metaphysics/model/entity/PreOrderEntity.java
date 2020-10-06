package com.zgzx.metaphysics.model.entity;

import com.google.gson.annotations.SerializedName;

public class PreOrderEntity {

    /**
     * alipay :
     * wxpay : {"partnerId":"1602864844","prepayId":"wx22105916071349ad9dc76d08da29cd0000","sign":"4E989EEE8ABDA1961385762A94BA0E242D98FD4582E97C4D036E8FBFB2A2A1AF","package":"Sign=WXPay","noncestr":"sHEhrsFPJXFFr9IR","timestamp":"1600743556"}
     */

    private String alipay;
    private WxpayBean wxpay;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public WxpayBean getWxpay() {
        return wxpay;
    }

    public void setWxpay(WxpayBean wxpay) {
        this.wxpay = wxpay;
    }

    public static class WxpayBean {
        /**
         * partnerId : 1602864844
         * prepayId : wx22105916071349ad9dc76d08da29cd0000
         * sign : 4E989EEE8ABDA1961385762A94BA0E242D98FD4582E97C4D036E8FBFB2A2A1AF
         * package : Sign=WXPay
         * noncestr : sHEhrsFPJXFFr9IR
         * timestamp : 1600743556
         */

        private String partnerId;
        private String prepayId;
        private String sign;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
