package com.zgzx.metaphysics.model.entity;

public class FortuneGradeEntity {
    private int score;
    private String name;

    public FortuneGradeEntity(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
