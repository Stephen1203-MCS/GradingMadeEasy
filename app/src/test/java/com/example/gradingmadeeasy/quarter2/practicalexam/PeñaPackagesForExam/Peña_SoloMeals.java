package com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam;

import java.util.Scanner;
import com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam.Peña_MealCombos;

public class Peña_SoloMeals {
    Peña_MealCombos ComboMeals = new Peña_MealCombos();
    public void ChooseSoloMeals(Scanner inputScanner) {
        //Burger[1], //[2]Chicken, //[3]Tenders

        int currentChoice = inputScanner.nextInt();
        if (currentChoice == 1) {
            System.out.println("You have ordered a Burger! Would you like to upgrade to a combo meal?");
            ComboMeals.ChooseMealCombos(inputScanner, "Burger");
        } else if (currentChoice == 2) {
            System.out.println("You have ordered Chicken! Would you like to upgrade to a combo meal?");
            ComboMeals.ChooseMealCombos(inputScanner, "Chicken");
        } else if (currentChoice == 3) {
            System.out.println("You have ordered Tenders! Would you like to upgrade to a combo meal?");
            ComboMeals.ChooseMealCombos(inputScanner, "Tenders");
        } else { //we assume that the user choice is 3, and use the else statement to make it as a default option
           System.out.println("Invalid input, please try again.");
        }
    }
}
