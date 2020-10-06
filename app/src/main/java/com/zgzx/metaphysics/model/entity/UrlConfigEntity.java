package com.zgzx.metaphysics.model.entity;

public class UrlConfigEntity {

    /**
     * timestamp : 1601276818
     * domain : http://h5.test.kongming365.com
     * sign_type : MD5
     * key : 1234567890
     * ak : kongmingzaixian
     */

    private long timestamp;
    private String domain;
    private String sign_type;
    private String key;
    private String ak;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }
}
