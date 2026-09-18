package com.example.gradingmadeeasy.quarter2.minipeta3;

public class Mendez_CheckCurrentSections {
    String[] Sections = {};

    public void CheckSections() {
        if (Sections.length == 0) {
            System.out.println("null");
        } else {
            for (String section : Sections) {
                System.out.println(section);
            }
        }
    }
}

