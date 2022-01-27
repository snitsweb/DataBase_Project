import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Connection connectToDb(String username, String password) throws Exception{
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/dblab4";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to DB");
            return conn;
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    public static Handler auth() throws Exception {
        Scanner scanner = new Scanner(System.in);

        boolean isAuth = false;
        boolean isWritten = false;
        int choice = 0;
        String username = "";
        String password = "";

        while(!isAuth){
            System.out.println("Please, select your role: \n" +
                    "1. Seller \n2. Administrator \n 3.Director");
            choice = Integer.parseInt(scanner.nextLine());
            if(choice >= 1 && choice <= 3){
                isAuth = true;
                while (!isWritten){
                    System.out.println("Please, enter your username: \n");
                    username = scanner.nextLine();
                    System.out.println("Enter your password: \n");

                    password = scanner.nextLine();
                    if(username.equals("") || password.equals("")){
                        System.out.println("Your username or password isn't correct, please try again.");
                    } else {
                        isWritten = true;
                        switch (choice){
                            case 1:  return new SellerHandler(connectToDb(username, password));
                            case 2:  return new AdminHandler(connectToDb(username, password));
                            case 3:  return new DirectorHandler(connectToDb(username, password));
                        }
                    }
                }
            } else {
                System.out.println("Bad choice, try again.\n");
            }
        }


        return null;
    }


    public static void main(String[] args) throws Exception {

        Handler handler = auth();
        if(handler != null){
            handler.mainMenu();
        }

    }
}
