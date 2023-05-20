package org.example;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

@Retention(RetentionPolicy.RUNTIME)
@interface MethodDescriptor{
    String methodDescription() default "Currently there is no description for this function";
}
enum Parameter{
    ID,
    Title,
    Author,
    Available,
    ReleaseDate,
    Pages,
    Cathegory

}

public class UserCommands {
    Scanner sc;
    SQLMethods sqlMethods;
public UserCommands(java.util.Scanner sc, SQLMethods sqlMethods){
    this.sqlMethods = sqlMethods;
    this.sc = sc;
}

@MethodDescriptor(methodDescription = "to exit the library,")
    public void exit(){
        throw new CancellationException();
    }
@MethodDescriptor(methodDescription = "to find the specified book,")
    public void find(){
    System.out.println("Search using: \nID \nTitle \nAuthor \nAvailable \nPages");
while(true) {
    String input = sc.nextLine();
    if (input.equalsIgnoreCase("id")) {
        System.out.println("Give an ID:");
        sqlMethods.findBook(Parameter.ID, sc.nextLine());
        break;
    } else if (input.equalsIgnoreCase("title")) {
        System.out.println("Give the books title:");
        sqlMethods.findBook(Parameter.Title, sc.nextLine());
        break;
    } else if (input.equalsIgnoreCase("author")) {
        System.out.println("Give the book Author:");
        sqlMethods.findBook(Parameter.Author, sc.nextLine());
        break;
    } else if (input.equalsIgnoreCase("available")) {
        System.out.println("Type 1 if you want to check for available books, type 0 to check which are borrowed.");
        sqlMethods.findBook(Parameter.Available, sc.nextLine());
        break;
    } else if (input.equalsIgnoreCase("pages")) {
        System.out.println("Type the number of pages");
        sqlMethods.findBook(Parameter.Pages, sc.nextLine());
        break;
    } else System.out.println("Incorrect statement");
}
}
    @MethodDescriptor(methodDescription = "to book the specified book,")
public void book(){
    //add checking if current user has another book booked already
    System.out.println("Give an ID number of the book you want to book: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(sqlMethods.checkBookAvailability(id)){
        System.out.println("Book is available. Do you really want to book it? \nYES/NO");
        if(sc.nextLine().equalsIgnoreCase("YES")){
            sqlMethods.bookTheBook(id);
        }else System.out.println("Booking operation aborted");
    }else System.out.println("Book unavailable for booking");


}

}
