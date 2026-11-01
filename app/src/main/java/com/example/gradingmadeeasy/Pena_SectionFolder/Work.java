package com.example.gradingmadeeasy.Pena_SectionFolder;

public class Work {
    private String name;
    private double maxScore;

    public Work(String name, double maxScore) {
        this.name = name;
        this.maxScore = maxScore;
    }

    public String getName() {
        return name;
    }

    public double getMaxScore() {
        return maxScore;
    }
}
