package com.zgzx.metaphysics.model.event;

public class SplashEvent {
   private int type;

    public SplashEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
