package com.example.gradingmadeeasy.quarter2.minipeta3;
import com.example.gradingmadeeasy.quarter2.minipeta3.Peña_MainMenu;

import java.util.Scanner;

public class Malonzo_Settings {
    public void Settings(Scanner inputScanner){
        Peña_MainMenu.PrintlnShortcut("===Light Mode[1] or Dark Mode?[2]===");
        int inputOfUser = inputScanner.nextInt();
        if (inputOfUser == 1){
            Peña_MainMenu.PrintlnShortcut("===Light mode activated===");
        } else {
            Peña_MainMenu.PrintlnShortcut("===Dark mode activated===");
        }
    }
}
