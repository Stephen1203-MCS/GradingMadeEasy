package com.example.gradingmadeeasy.quarter2.minipeta3;
import java.util.Scanner;



public class Peña_DisplayMenu {
    static Scanner InputScanner = new Scanner(System.in); //Declare scan
    public static void main(String[] args){
        System.out.println("Login or Register: [1 = Login, 2 = Register]"); //Numbers are simply placeholder variables for guis
        int resultOfScanner = InputScanner.nextInt();
        String identificationQuestion = "As a Student or Teacher?: [1 = Teacher, 2 = Student]";

        int teacherOrStudentVariable = 0;

        if (resultOfScanner == 1) { //User Login
            System.out.println(identificationQuestion);
            int teacherOrStudent = InputScanner.nextInt();
            if (teacherOrStudent == 1) {
                teacherOrStudentVariable = 1;
                System.out.println("You are a teacher");
            } else if (teacherOrStudent == 2) {
                teacherOrStudentVariable = 2;
                System.out.println("You are a student");
            } else {
                System.out.println("Invalid Option");
            }
        } else if (resultOfScanner == 2) { //User Registration
            System.out.println(identificationQuestion);
            int teacherOrStudent = InputScanner.nextInt();
            if (teacherOrStudent == 1) {
                teacherOrStudentVariable = 1;
                System.out.println("You are a teacher");
            } else if (teacherOrStudent == 2) {
                teacherOrStudentVariable = 2;
                System.out.println("You are a student");
            } else {
                System.out.println("Invalid Option");
            }
        }

    }
}
