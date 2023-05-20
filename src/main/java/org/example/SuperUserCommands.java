package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SuperUserCommands extends UserCommands {
    public SuperUserCommands(Scanner sc, SQLMethods sqlMethods) {
        super(sc, sqlMethods);
    }

    @MethodDescriptor(methodDescription = "to add a new book,")
    public void add(){
        String title;
        String author;

        int pages;
        int isAvailable = 1;

        System.out.println("Title: ");
        title = sc.nextLine();
        System.out.println("Author: ");
        author = sc.nextLine();
        System.out.println("Release Date: ");

        try {
            String releaseDate = setDate();
            System.out.println("Number of pages: ");
            pages = sc.nextInt();

            sqlMethods.addBook(title,author,releaseDate, pages, isAvailable);
        }catch (DateTimeParseException e)
        {
            System.out.println(e + "Incorrect date format, use yyyy-MM-dd instead");
        }




    }
    @MethodDescriptor(methodDescription = "to remove book from the library")
    public void remove(){
        System.out.println("Give an ID number of the book you want do permanently delete from the Library database");
        try{
            sqlMethods.removeBook(sc.nextInt());
        }catch (IllegalStateException e ){
            System.out.println(e);
        }
    }

    @MethodDescriptor(methodDescription = "to confirm book return")
    public void returned(){
        System.out.println("Give an ID number of the book which has been returned");
        try{
            sqlMethods.returnBook(sc.nextInt());
        }catch (IllegalStateException e ){
            System.out.println(e);
        }
    }

    @MethodDescriptor(methodDescription = "to update selected records")
    public void update(){
        System.out.println("Give an ID number of the book you want to update");
        int id = sc.nextInt();
        sc.nextLine();
        List<Parameter> parameterList = Arrays.asList(Parameter.values());
        parameterList = parameterList.subList(1, parameterList.size());
        System.out.println("Select one or more of listed down parameters:\n" + parameterList);
        String selected  = sc.nextLine();
        List<String> separatedSelected = Arrays.asList(selected.split(" "));
        for(String element : separatedSelected){
            if(parameterList.toString().contains(element)){
                System.out.println("Give the new value of element " + element + ": ");
                String newValue = "";
                if(element.equalsIgnoreCase("ReleaseDate")){
                    try {
                        newValue = setDate();
                        sqlMethods.updateBook(id, element, newValue);
                    }catch (DateTimeParseException e) {
                        System.out.println(e + ". Incorrect date format, use yyyy-MM-dd instead");
                    }
                }else {
                    newValue = sc.nextLine();
                    sqlMethods.updateBook(id, element, newValue);
                }
            }else System.out.println(element + " element is incorrect, will be ignored");
        }
    }
    @MethodDescriptor(methodDescription = "to list all users")
    public void listUsers(){

    }
    @MethodDescriptor(methodDescription = "to add new library user")
    public void addUser(){

    }
    @MethodDescriptor(methodDescription = "to modify selected user")
    public void modifyUser(){

    }
    @MethodDescriptor(methodDescription = "to delete library user")
    public void deleteUser(){

    }





    private String setDate() throws DateTimeParseException {
        String releaseDate;
        releaseDate = sc.nextLine();
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate l = LocalDate.parse(releaseDate, d);
        return l.toString();
    }





}
