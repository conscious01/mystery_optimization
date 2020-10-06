package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;

public class PayResultEntity implements Serializable {
    private boolean payResultBoolean;
    private String payResultString;
    private Class<?> cls;

    public PayResultEntity(boolean payResultBoolean, String payResultString,
                           String paidMoenyString, Class<?> cls) {
        this.payResultBoolean = payResultBoolean;
        this.payResultString = payResultString;
        this.paidMoenyString = paidMoenyString;
        this.cls = cls;

    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public boolean isPayResultBoolean() {
        return payResultBoolean;
    }

    public void setPayResultBoolean(boolean payResultBoolean) {
        this.payResultBoolean = payResultBoolean;
    }

    public String getPayResultString() {
        return payResultString;
    }

    public void setPayResultString(String payResultString) {
        this.payResultString = payResultString;
    }

    public String getPaidMoenyString() {
        return paidMoenyString;
    }

    public void setPaidMoenyString(String paidMoenyString) {
        this.paidMoenyString = paidMoenyString;
    }

    public PayResultEntity(boolean payResultBoolean, String payResultString,
                           String paidMoenyString) {
        this.payResultBoolean = payResultBoolean;
        this.payResultString = payResultString;
        this.paidMoenyString = paidMoenyString;
    }

    private String paidMoenyString;

}
