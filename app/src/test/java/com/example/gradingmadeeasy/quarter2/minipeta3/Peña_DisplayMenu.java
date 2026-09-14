package com.example.gradingmadeeasy.quarter2.minipeta3;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.util.Scanner;
import java.io.File;

public class Peña_DisplayMenu {


    static Boolean Teacher = false;
    static Boolean Login = false;
    static Scanner InputScanner = new Scanner(System.in); //Declare scan
    public static void main(String[] args){
        System.out.println("Do you want to login?: [True = Login or False = Register]");
        Boolean LoginOrNot = InputScanner.nextBoolean();


        if (LoginOrNot) {
            Login = true;
        }

        System.out.println("Are you a Teacher?: [True = Teacher or False = Student]");
        Boolean TeacherOrNot = InputScanner.nextBoolean();

        if (TeacherOrNot) {
            Teacher = true;
        }
    }
}
