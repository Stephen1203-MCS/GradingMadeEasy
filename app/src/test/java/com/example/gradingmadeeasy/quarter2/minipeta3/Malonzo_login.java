package com.example.gradingmadeeasy.quarter2.minipeta3;
import org.junit.Test;

import java.util.Objects;
import java.util.Scanner;

public class Malonzo_login {
@Test
    Boolean TeacherValue = false;
    static Boolean Login = true;

    static String PlaceHolderName =  Username123;
    static String PlaceHolderPassword = Password123;
    String Username = null;
    String Password = null;

    static Scanner InputScanner = new Scanner(System.in); //Declare scan
    public static void main(String[] args){
        if (Login) {
            System.out.println("What is your Username?");
            String Username = InputScanner.nextLine();

            System.out.println("What is your Password");
            String Password = InputScanner.nextLine();

            if (Objects.equals(Username, PlaceHolderName)); {
                System.out.println("Username Exists");
                if (Objects.equals(Password, PlaceHolderPassword)); {
                    System.out.println("Logged in");
                }
            }
        }
    }
}
