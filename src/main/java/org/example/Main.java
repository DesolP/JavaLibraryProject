package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

import static javafx.application.Platform.exit;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //String url = "jdbc:mysql://127.0.0.1:3306/librarydb";

        SQLMethods sqlMethods = new SQLMethods();
        UserCommands userCommands = new UserCommands(sc, sqlMethods);
        SuperUserCommands superUserCommands = new SuperUserCommands(sc, sqlMethods);
        UserProperties userProperties = new UserProperties();


        //Connect to the database
        try {
            sqlMethods.connectSQLDatabase(sc);
        }catch(IllegalStateException e){
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Type \"help\" to see all possible operations with description");
        try {
            do {
                String inputText = sc.nextLine();
                if (inputText.equals("help")) {
                    System.out.println("All methods you can do as " + sqlMethods.getUsername() + ": ");
                    userProperties.help(userCommands);
                    userProperties.help(superUserCommands);

                }
                if (inputText.equalsIgnoreCase("exit")) {
                    userCommands.exit();
                }
                if(inputText.equalsIgnoreCase("find")){
                    userCommands.find();
                }
                if(inputText.equalsIgnoreCase("add")){
                    superUserCommands.add();
                }
                if (inputText.equalsIgnoreCase("remove")) {
                    superUserCommands.remove();
                }
                if(inputText.equalsIgnoreCase("book")){
                    userCommands.book();
                }
                if(inputText.equalsIgnoreCase("returned")){
                    superUserCommands.returned();
                }
                if(inputText.equalsIgnoreCase("update")){
                    superUserCommands.update();
                }

            } while (true);
        }catch (CancellationException e){
        }finally {

            System.out.println("Library closed");
        }


        }
}