package com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam;

import com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam.Peña_MealCombos;
import com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam.Peña_SoloMeals;

import java.util.Scanner;

public class Peña_FastFoodMenu {
    Peña_SoloMeals SoloMeals = new Peña_SoloMeals();
    Peña_MealCombos ComboMeals = new Peña_MealCombos();
    public void start(Scanner inputScanner){
        boolean isRunning = true;

        System.out.println("Menu");

        int currentChoice = inputScanner.nextInt();
        System.out.println(currentChoice); //print out the choice for the user, for debugging purposes

        while (isRunning){
            if (currentChoice == 1) {//order burger

            } else if (currentChoice == 2) { //order fries

            } else if (currentChoice == 3) { //exit the menu

            } else { //catch the invalid input

            }

        }

    }
}
