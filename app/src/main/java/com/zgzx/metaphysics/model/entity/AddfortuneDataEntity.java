package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;

public class AddfortuneDataEntity implements Serializable {

    public AddfortuneDataEntity(int add_fortune_score, int add_fortune_times, int average_score,
                                int max_add_fortune_times) {
        this.add_fortune_score = add_fortune_score;
        this.add_fortune_times = add_fortune_times;
        this.average_score = average_score;
        this.max_add_fortune_times = max_add_fortune_times;
    }

    /**
     * add_fortune_score : 0
     * add_fortune_times : 0
     * average_score : 35
     * max_add_fortune_times : 3
     */

    private int add_fortune_score;
    private int add_fortune_times;
    private int average_score;
    private int max_add_fortune_times;

    public int getAdd_fortune_score() {
        return add_fortune_score;
    }

    public void setAdd_fortune_score(int add_fortune_score) {
        this.add_fortune_score = add_fortune_score;
    }

    public int getAdd_fortune_times() {
        return add_fortune_times;
    }

    public void setAdd_fortune_times(int add_fortune_times) {
        this.add_fortune_times = add_fortune_times;
    }

    public int getAverage_score() {
        return average_score;
    }

    public void setAverage_score(int average_score) {
        this.average_score = average_score;
    }

    public int getMax_add_fortune_times() {
        return max_add_fortune_times;
    }

    public void setMax_add_fortune_times(int max_add_fortune_times) {
        this.max_add_fortune_times = max_add_fortune_times;
    }
}
