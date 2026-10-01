package com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam;

import com.example.gradingmadeeasy.quarter2.practicalexam.PeñaPackagesForExam.Peña_SoloMeals;

import java.util.Scanner;

public class Peña_FastFoodMenu {
    Peña_SoloMeals SoloMeals = new Peña_SoloMeals();
    public void start(Scanner inputScanner){
        boolean isRunning = true;
        System.out.println("||======[Insert Company Name here]'s Menu======||");
        System.out.println("||      [1] - Burger = [Price]                 ||");
        System.out.println("||      [2] - Fries  = [Price]                 ||");
        System.out.println("||      [3] - Exit                             ||");
        System.out.println("||=============================================||");

        while (isRunning){
            int currentChoice = inputScanner.nextInt();
            System.out.println(currentChoice + " <- User Input"); //print out the choice for the user, for debugging purposes

            if (currentChoice == 1) {//order burger
                SoloMeals.ChooseSoloMeals(inputScanner, currentChoice);
            } else if (currentChoice == 2) { //order fries
                SoloMeals.ChooseSoloMeals(inputScanner, currentChoice);
            } else if (currentChoice == 3) { //exit the menu
                System.out.println("||=======================================================||");
                System.out.println("||Thank you for ordering with [Insert Company Name here]!||");
                System.out.println("||=======================================================||");
                isRunning = false;
            } else { //catch the invalid input
                System.out.println("INVALID INPUT, PLEASE TRY AGAIN.");
            }
        }

    }
}
