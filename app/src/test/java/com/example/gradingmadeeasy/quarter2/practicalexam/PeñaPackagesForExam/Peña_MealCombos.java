package com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam;

import java.util.Scanner;
import com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam.Peña_MealCombos;
import com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam.Peña_SoloMeals;
public class Peña_MealCombos {

    Peña_SoloMeals SoloMeals = new Peña_SoloMeals();

    public void ChooseMealCombos(Scanner inputScanner, String mealName){
        int currentChoice = inputScanner.nextInt();

        if (currentChoice == 1) { //yes or no
            System.out.println("Upgraded to meal combo");
        } else {
            System.out.println("Ordered " + mealName + " as solo.");
        }
    }
}
