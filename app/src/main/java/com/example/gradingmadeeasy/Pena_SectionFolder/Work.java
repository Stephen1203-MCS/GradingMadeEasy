package com.example.gradingmadeeasy.Pena_SectionFolder;

import java.util.HashMap;

public class Work {
    private String name;
    private double maxScore;

    // Maps a student from the class list to their score in this work.
    // A student who is missing from the map has no score yet.
    private HashMap<Students, Double> scores; //we love hashmaps

    public Work(String name, double maxScore) {
        this.name = name;
        this.maxScore = maxScore;
        this.scores = new HashMap<>();
    }

    public Work(String name) {
        this(name, 100.0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    // Returns null when the student has no score in this work yet.
    public Double getScore(Students student) {
        return scores.get(student);
    }

    public void setScore(Students student, double score) {
        scores.put(student, score);
    }

    public void clearScore(Students student) {
        scores.remove(student);
    }

    // True if any saved score is higher than the given limit (used when lowering the max score).
    public boolean hasScoreAbove(double limit) {
        for (double score : scores.values()) {
            if (score > limit) {
                return true;
            }
        }
        return false;
    }
}