package com.example.gradingmadeeasy.quarter2.minipeta3;


import android.text.BoringLayout;

import org.junit.Test;

public class Peña_Formulas {
    static class Category {
        String name;
        double weight;
        public Category(String name, double weight) {
            this.name = name;
            this.weight = weight;
        }
    }

    static class ScoreSummary {
        int points;
        public ScoreSummary(int points) {
            this.points = points;
        }
    }


    Boolean GPA = false;
    Boolean WeightedCategory = false;
    Boolean TotalSum = true;

    static double WeightedCategoryGradeCalculator(Category[] categories, ScoreSummary[] scores) {
        double FinalGrade = 0;
        for (int i = 0; i < categories.length; i++) {
            Category category = categories[i];
            ScoreSummary score = scores[i];
            FinalGrade += score.points * category.weight;
        }
       return FinalGrade;
    }
    static double GPACalculator(double TotalQualityPoints, double TotalCreditHours) {
        double GPA = 0;
        GPA = TotalQualityPoints / TotalCreditHours;
        return GPA;
    }

    static double GradingSystemTotalPointsCalculator(int TotalPossiblePoints, int TotalEarnedPoints) {

        double Divided = (double) TotalPossiblePoints / TotalEarnedPoints;

        double Final = (TotalEarnedPoints / (double) TotalPossiblePoints) * 100;
        return Final;
    }


    @Test
    public void Test(){
        if (GPA == true){
            System.out.println(GPACalculator(12, 5));
        } else if (WeightedCategory == true){
            Category[] categories = new Category[3];
            categories[0] = new Category("Written Works", 0.2);
            categories[1] = new Category("Performance Tasks", 0.6);
            categories[2] = new Category("Quarterly Assessment", 0.2);

            ScoreSummary[] scores = new ScoreSummary[3];
            scores[0] = new ScoreSummary(85);
            scores[1] = new ScoreSummary(90);
            scores[2] = new ScoreSummary(75);

            System.out.println(WeightedCategoryGradeCalculator(categories, scores));
        } else {
            System.out.println(GradingSystemTotalPointsCalculator(100, 98));
        }
    }
}
