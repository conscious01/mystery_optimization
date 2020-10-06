package com.zgzx.metaphysics.model.event;

public class OnFragmentChanged {
    private int position;
    private boolean ifCanPlay;

    public OnFragmentChanged(boolean ifCanPlay) {
        this.ifCanPlay = ifCanPlay;
    }

    public boolean isIfCanPlay() {
        return ifCanPlay;
    }

    public void setIfCanPlay(boolean ifCanPlay) {
        this.ifCanPlay = ifCanPlay;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public OnFragmentChanged(int position) {
        this.position = position;
    }
}
