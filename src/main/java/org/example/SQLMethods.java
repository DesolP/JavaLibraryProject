package org.example;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class SQLMethods {

    private String username = "";
    private String password = "";
    private final String url = "jdbc:mysql://127.0.0.1:3306/librarydb";
    private Connection connection = null;
    private Statement statement = null;

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public void connectSQLDatabase(java.util.Scanner sc) {

        System.out.println("Username:");
        setUsername(sc.nextLine());
        System.out.println("Password:");
        setPassword(sc.nextLine());
        for (int i = 0; i < 15; i++) {
            System.out.println(" ");
        }
        System.out.println("Connecting database...");




        try {
            connection = DriverManager.getConnection(url, "javaUser", "");

            System.out.println("Database connected!");


        } catch (SQLException e) {
            throw new IllegalStateException("Unable to connect to the database. Username or password is incorrect. " +
                    "\n Closing library session... " + e, e);

        }

    }

    public void findBook(Parameter parameter, String searchedStatement) {
        boolean printedColumns = false;
        boolean foundBook = false;
        try {
            String query = "SELECT * FROM books WHERE " + parameter + "='" + searchedStatement + "';";
           // System.out.println("Searched query: \n" + query);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                foundBook = true;
                int id = resultSet.getInt("id");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                Date releaseDate = resultSet.getDate("ReleaseDate");
                int pages = resultSet.getInt("Pages");
                int isAvailable = resultSet.getInt("Available");

                //resultSet.next();
                printResult(id, title, author, releaseDate, pages, isAvailable, printedColumns);
                printedColumns = true;
            }
            if(!foundBook){
                System.out.println("Unable to find book with specified parameter");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public void addBook(String title, String author, String releaseDate, int pages, int isAvailable) {

        String query = "INSERT INTO books (Title,Author,ReleaseDate, Pages, Available)"
                + "VALUES('" + title + "', '" + author + "', '" + releaseDate + "','" + pages + "','" + isAvailable + "');";
       // System.out.println("Query check: " + query);
        try {
            statement = connection.createStatement();
            int i = statement.executeUpdate(query);
            if (i > 0) {
                System.out.println("Record added succesfully");
            } else System.out.println("Record has not been added to the library.");
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println(e);

        }
    }
    public void removeBook(int id) {
        String query = "DELETE FROM librarydb.books WHERE ID = " + id;
        System.out.println("Record will be deleted from the Library.\nDo you want to proceed?\nYES/NO");
        Scanner sc1 = new Scanner(System.in);
        String resp = sc1.nextLine();
        if (resp.equals("YES")) {
            try {
                statement = connection.createStatement();
                int i = statement.executeUpdate(query);
                if (i > 0) {
                    System.out.println("Record successfully deleted");
                } else System.out.println("Record has not been deleted");
            } catch (SQLException e) {
                System.out.println(e);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Record has not been deleted");
        }
    }
    public boolean checkBookAvailability(int id){
        boolean isBookAvailable = false;
        boolean foundBook = false;
        String query = "SELECT * FROM librarydb.books WHERE ID =" + id;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSet copyResultSet = resultSet;

            while (resultSet.next()) {
                foundBook = true;
                int bookID = resultSet.getInt("id");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                Date releaseDate = resultSet.getDate("ReleaseDate");
                int pages = resultSet.getInt("Pages");
                isBookAvailable = copyResultSet.getInt("Available") == 1;
                printResult(bookID, title, author, releaseDate, pages, isBookAvailable ? 1 : 0, false);


            }
            if(!foundBook){
                System.out.println("Unable to find book with specified parameter");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isBookAvailable;
    }
    public void bookTheBook(int id){
        String query = "UPDATE librarydb.books SET Available = 0 WHERE ID= " + id;
        try{
            statement = connection.createStatement();
            int i = statement.executeUpdate(query);
            if(i>0){
                System.out.println("Book successfully booked to user: " + getUsername());
            }else System.out.println("Couldn't book the book");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void returnBook(int id){
        String query = "UPDATE librarydb.books SET Available = 1 WHERE ID= " + id;
        try{
            statement = connection.createStatement();
            int i = statement.executeUpdate(query);
            if(i>0){
                System.out.println("Book successfully returned");
            }else System.out.println("Couldn't return the book");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateBook(int id, String element, String newValue){
        String query = " UPDATE librarydb.books SET " + element + " = '" + newValue + "' WHERE ID= " + id;
        System.out.println(query);
        try{
            statement = connection.createStatement();
            int i = statement.executeUpdate(query);
            if(i>0){
                System.out.println(element + " successfully updated to: " + newValue);
            }else System.out.println("Couldn't update the record");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void printResult(int id, String title, String author, Date releaseDate, int pages, int isAvailable, boolean printedColumns) {
            // print the results
            if (!printedColumns) {
                String columnNames = String.format("%-" + (title.length() + 1) + "s", "Title") + " "
                        + String.format("%-" + (author.length() + 1) + "s", "Author") + " "
                        + String.format("%-" + (releaseDate.toString().length() + 4) + "s", "Release Date") + " "
                        + String.format("%-" + 6 + "s", "Pages") + " "
                        + String.format("%-" + 9 + "s", "Available");
                System.out.println("ID " + columnNames);
            }
            System.out.format("%s, %s, %s, %s,    %s,   %s\n", id, title, author, releaseDate, pages, isAvailable == 1 ? "Yes" : "No");

    }



}
