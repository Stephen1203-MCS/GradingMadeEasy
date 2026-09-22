package com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam;

import java.util.Scanner;
import com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam.Peña_MealCombos;

public class Peña_SoloMeals {
    Peña_MealCombos ComboMeals = new Peña_MealCombos();
    public void ChooseSoloMeals(Scanner inputScanner, int currentChoice) {
        if (currentChoice == 1) {
            System.out.println("You have ordered a Burger! Would you like to upgrade to a combo meal?");
            ComboMeals.ChooseMealCombos(inputScanner, "Burger");
        } else if(currentChoice == 2) {
            System.out.println("You have ordered a Fries!"); //removed ComboMeals as Fries is not a valid item for ComboMeal
        } else { //catch if the user has inputs an invalid value
            System.out.println("Invalid input, please try again.");
        }
    }
}
