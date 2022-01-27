import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SellerHandler extends Handler {

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return formatter.format(date);
    }

    public SellerHandler(Connection conn) {
        super(conn);
    }

    public void mainMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String item_code = "";
        int count = 0;
        final String cytate = "\"";
        boolean isCode = false;
        boolean isCount = false;

        while (!isCode) {
            System.out.println("Please, write code of product : \n");
            item_code = scanner.nextLine();
            if (item_code.equals("")) {
                System.out.println("Code can't be empty! Try again");
            } else {
                isCode = true;

                while (!isCount) {
                    System.out.println("Please, write count of product : \n");
                    count = Integer.parseInt(scanner.nextLine());
                    if (count == 0) {
                        System.out.println("Count can't be 0! Try again");
                    } else {
                        isCount = true;
                        PreparedStatement statement = conn.prepareStatement("SELECT getProductIdByCode(" + item_code + ");");
                        ResultSet result = statement.executeQuery();
                        if(result.next()){
                            System.out.println(getDate());
                            System.out.println(conn);
                            PreparedStatement statement_2 = conn.prepareStatement(
                                    "CALL pushsale(?, ?, ?)"
                                    );
                            statement_2.setString(1, getDate());
                            statement_2.setInt(2, Integer.parseInt(result.getString(1)));
                            statement_2.setInt(3, count);

                            ResultSet result_2 = statement_2.executeQuery();
                        } else {
                            System.out.println("Err");
                        }
                    }
                }

            }
        }

        return;
    }

}