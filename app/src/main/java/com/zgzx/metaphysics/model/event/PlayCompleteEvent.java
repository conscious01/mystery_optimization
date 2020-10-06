package com.zgzx.metaphysics.model.event;

public class PlayCompleteEvent {
    private int pos;
    private int type;

    public PlayCompleteEvent(int pos, int type) {
        this.pos = pos;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
