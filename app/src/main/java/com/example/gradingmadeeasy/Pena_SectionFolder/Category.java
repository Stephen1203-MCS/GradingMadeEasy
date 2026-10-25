package com.example.gradingmadeeasy.Pena_SectionFolder;

import java.util.ArrayList;

public class Category {
    private String name;
    private double weight;
    private ArrayList<Work> works;

    public Category(String name, double weight) {
        this.name = name;
        this.weight = weight;
        this.works = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ArrayList<Work> getWorks() {
        return works;
    }
}
