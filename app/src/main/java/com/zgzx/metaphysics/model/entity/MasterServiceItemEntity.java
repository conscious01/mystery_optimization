package com.zgzx.metaphysics.model.entity;

import com.zgzx.metaphysics.ui.activities.MasterHomepageActivity;

import java.util.List;

/**
 * 师傅服务项目
 *
 * @see MasterHomepageActivity#renderServices(List)
 */
public class MasterServiceItemEntity {

    private String name;

    private double price;

    private String icon;

    private boolean selected;

    public MasterServiceItemEntity() {
    }

    public MasterServiceItemEntity(String name, double price, String icon) {
        this.name = name;
        this.price = price;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
