package com.example.gradingmadeeasy.Pena_SectionFolder;

import java.util.ArrayList;

public class Category {
    private String name;
    private double weight;
    private final ArrayList<Work> works = new ArrayList<>();

    public Category(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public ArrayList<Work> getWorks() {
        return works;
    }
}
