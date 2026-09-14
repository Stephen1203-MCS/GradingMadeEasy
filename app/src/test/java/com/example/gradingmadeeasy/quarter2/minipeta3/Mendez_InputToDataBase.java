package com.example.gradingmadeeasy.quarter2.minipeta3;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
public class Mendez_InputToDataBase {
    private URI Espinosa_register;
    Path RegisterFile = Paths.get(Espinosa_register);


    String Username = Espinosa_register.Username;
    String Password = Espinosa_register.Password;
    String AccountID = Espinosa_register.ID;

    File DataBase = new File("Database");
    if (DataBase.createNewFile()) {

    } else {
        System.out.println("File Already Exists");
    }
}