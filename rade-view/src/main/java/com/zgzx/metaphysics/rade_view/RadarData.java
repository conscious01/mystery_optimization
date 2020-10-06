package com.zgzx.metaphysics.rade_view;

public class RadarData {

    private String title;
    private double percentage;
    private String number;
    public RadarData(String title,String number, double percentage) {
        this.number=number;
        this.title = title;
        this.percentage = percentage;
    }

    public String getTitle() {
        return title;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getNumber() {
        return number;
    }
}
