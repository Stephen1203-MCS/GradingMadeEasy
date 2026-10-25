package com.example.gradingmadeeasy.Pena_SectionFolder;

// One student in the class list. Scores are no longer stored here, each Work keeps its own scores.
public class Students {
    private String name;

    public Students(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}