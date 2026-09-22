package com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam;

import java.util.Scanner;
public class Peña_MealCombos {
    public void ChooseMealCombos(Scanner inputScanner, String mealName){
        int currentChoice = inputScanner.nextInt();

        if (currentChoice == 1) { //question for comboMeals
            System.out.println("Upgraded to meal combo!"); //yes
        } else {
            System.out.println("Ordered " + mealName + " as solo."); //no
        }
    }
}
