package com.example.gradingmadeeasy.quarter2.minipeta3;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;
import java.util.Scanner;

import com.example.gradingmadeeasy.quarter2.minipeta3.Mendez_CheckCurrentSections;
import com.example.gradingmadeeasy.quarter2.minipeta3.Malonzo_Settings;
import com.example.gradingmadeeasy.quarter2.minipeta3.Espinosa_SectionCreation;

public class Peña_MainMenu {
    public static void PrintlnShortcut(Object Text) {
        System.out.println(Text);
    }
    public void start(Scanner inputScanner){
        boolean currentlyRunning = true;
        //initialize all important files
        Mendez_CheckCurrentSections checkCurrentSections = new Mendez_CheckCurrentSections();
        Malonzo_Settings checkSettings = new Malonzo_Settings();
        Espinosa_SectionCreation sectionCreation = new Espinosa_SectionCreation();

        PrintlnShortcut("==========================");
        PrintlnShortcut("Welcome to the Main Menu");
        PrintlnShortcut("1 = Settings");
        PrintlnShortcut("2 = To create a section");
        PrintlnShortcut("3 = Check all current sections");
        PrintlnShortcut("4 = Main Menu");
        PrintlnShortcut("5 = Exit");
        PrintlnShortcut("==========================");
        while (currentlyRunning == true){

            int choice = inputScanner.nextInt();
            PrintlnShortcut(choice);

            if (choice == 1){
                checkSettings.Settings(inputScanner);
            } else if (choice == 2){
                inputScanner.nextLine();
                sectionCreation.CreateSection(inputScanner);
            } else if (choice == 3){
                checkCurrentSections.CheckSections();
            } else if (choice == 4){
                PrintlnShortcut("==========================");
                PrintlnShortcut("Welcome to the Main Menu");

                PrintlnShortcut("1 = Main Menu");
                PrintlnShortcut("2 = To create a section");
                PrintlnShortcut("3 = Check all current sections");
                PrintlnShortcut("4 = Settings");
                PrintlnShortcut("==========================");
            } else if (choice == 5) {
                PrintlnShortcut("EXITED");
                break;
            }
        }
    }

}
