package com.example.gradingmadeeasy.Pena_SectionFolder;

import java.util.ArrayList;
import java.util.HashMap;

public class Quarter {
    private String name;
    private ArrayList<Category> categories;

    // Saved quarter grades (in percent). They are only filled in when "Update Grades" is pressed
    // A student who is missing from the map has no grade yet
    private HashMap<Students, Double> grades;

    public Quarter(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    // Returns null when no grade has been calculated for this student yet
    public Double getGrade(Students student) {
        return grades.get(student);
    }

    public void clearGrade(Students student) {
        grades.remove(student);
    }

    // Adds up the weights of every category in this quarter
    public double getTotalWeight() {
        double total = 0;
        for (Category category : categories) {
            total += category.getWeight();
        }
        return total;
    }

    // TOTAL SCORE system (ignores weights):
    // all of the student's scores added up / all max scores added up. A missing score counts as 0
    public double calculateTotalScoreGrade(Students student) {
        double earned = 0;
        double possible = 0;
        for (Category category : categories) {
            for (Work work : category.getWorks()) {
                Double score = work.getScore(student);
                if (score != null) {
                    earned += score;
                }
                possible += work.getMaxScore();
            }
        }
        if (possible <= 0) {
            return 0;
        }
        return earned / possible * 100;
    }

    // WEIGHTED CATEGORY
    // each category gets a percent (scores / max scores, missing = 0), then the percents are
    // combined using the category weights. Returns null when the weights add up to 0
    public Double calculateWeightedGrade(Students student) {
        double totalWeight = getTotalWeight();
        if (totalWeight <= 0) {
            return null;
        }

        double weightedSum = 0;
        for (Category category : categories) {
            double earned = 0;
            double possible = 0;
            for (Work work : category.getWorks()) {
                Double score = work.getScore(student);
                if (score != null) {
                    earned += score;
                }
                possible += work.getMaxScore();
            }

            double percent = (possible <= 0) ? 0 : earned / possible * 100;
            weightedSum += percent * category.getWeight();
        }

        // Divided by the weights actually entered, so the result always stays between 0 and 100.
        return weightedSum / totalWeight;
    }

    // Recalculates and saves the grade of every student in the class list.
    public void recalculateGrades(ArrayList<Students> students, boolean weighted) {
        grades.clear();
        for (Students student : students) {
            if (weighted) {
                Double grade = calculateWeightedGrade(student);
                if (grade != null) {
                    grades.put(student, grade);
                }
            } else {
                grades.put(student, calculateTotalScoreGrade(student));
            }
        }
    }
}