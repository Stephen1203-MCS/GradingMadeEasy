package com.example.gradingmadeeasy.SectionFile;

import java.util.ArrayList;

public class Category {
    private String name;
    private double Weight;
    private ArrayList<Work> Works;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
