package com.zgzx.metaphysics.model.entity;

public class GanEntity {
    private boolean select;
    private String name;

    public GanEntity(boolean select, String name) {
        this.select = select;
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
