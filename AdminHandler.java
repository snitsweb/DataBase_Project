import com.mysql.cj.xdevapi.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminHandler extends Handler {
    Connection conn;

    public AdminHandler(Connection conn) {
        super(conn);
    }

    public void changeProductsStatus(int index, Scanner scanner) throws SQLException {
        if(index == 1){
            //add
            String name = "";
            String category = "";
            int code = 0;

            System.out.println("Product name: ");
            name = scanner.nextLine();

            System.out.println("Product category: ");
            category = scanner.nextLine();

            System.out.println("Product code: ");
            code = Integer.parseInt(scanner.nextLine());

            PreparedStatement statement = conn.prepareStatement("SELECT getCategoryIdByName(" + category + ");");
            ResultSet result = statement.executeQuery();
            if(result.next()){
                int cat_id = result.getInt(1);
                statement = conn.prepareStatement("CALL addNewProduct(?, ?, ?);");
                statement.setString(1, name);
                statement.setInt(2, cat_id);
                statement.setInt(3, code);
                result = statement.executeQuery();
            } else {
                System.out.println("Something went wrong");
            }

        } else if(index == 2) {
            int code = 0;
            System.out.println("Product code: ");
            code = Integer.parseInt(scanner.nextLine());

            PreparedStatement statement = conn.prepareStatement("CALL deleteProductByCode(?);");
            statement.setInt(1, code);
            ResultSet result = statement.executeQuery();
        } else {
            System.out.println("Something went wrong.");
        }
    }

    public void changeActualProductsStatus(Scanner scanner) throws SQLException {
        int code = 0;
        int count = 0;
        int id = 0;

        System.out.println("Product code: ");
        code = Integer.parseInt(scanner.nextLine());

        System.out.println("Product count: ");
        count = Integer.parseInt(scanner.nextLine());

        PreparedStatement statement = conn.prepareStatement("SELECT getProductIdByCode(?);");
        statement.setInt(1, code);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            id = result.getInt("0");
            statement = conn.prepareStatement("CALL updateProductCount(?, ?);");
            statement.setInt(1, id);
            statement.setInt(2, count);
            result = statement.executeQuery();
        } else {
            System.out.println("Something went wrong.");
        }

    }

    public void mainMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int answer = 0;
        int count = 0;
        boolean isAnswer = false;

        while (!isAnswer) {
            System.out.println("Please, select what you want to do : \n" +
                    "1. Change products database \n" +
                    "2. Change actual products count \n");
            answer = Integer.parseInt(scanner.nextLine());
             if (answer == 1) {
                isAnswer = true;
                System.out.println("What do you want to do? \n" +
                        "1.Add new product \n" +
                        "2.Remove product \n"
                );
                answer = Integer.parseInt(scanner.nextLine());
                changeProductsStatus(answer, scanner);
            } else if (answer == 2) {
                isAnswer = true;
                changeActualProductsStatus(scanner);
            } else {
                System.out.println("Something went wrong, please try again. \n");
            }
        }
    }
}

