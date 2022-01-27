import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DirectorHandler extends Handler {

    public void changeWorkersStatus(int index, Scanner scanner) throws SQLException {
        if (index == 1) {
            String name = "";
            String surname = "";
            int day = 0;
            int month = 0;
            int year = 0;
            int pesel = 0;
            String position = "";

            System.out.println("Name: ");
            name = scanner.nextLine();

            System.out.println("Surname: ");
            surname = scanner.nextLine();

            System.out.println("Day of birthday: ");
            day = Integer.parseInt(scanner.nextLine());

            System.out.println("month of birthday: ");
            month = Integer.parseInt(scanner.nextLine());

            System.out.println("year of birthday: ");
            year = Integer.parseInt(scanner.nextLine());

            System.out.println("Pesel: ");
            pesel = Integer.parseInt(scanner.nextLine());


            System.out.println("Position: ");
            position = scanner.nextLine();

            PreparedStatement statement = conn.prepareStatement("SELECT getPositionByName(" + position + ");");
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int id = Integer.parseInt(result.getString(1));
                PreparedStatement statement2 = conn.prepareStatement(
                        "CALL pushworker(?, ?, ?, ? ,?)"
                );
                statement2.setString(1, name);
                statement2.setString(2, surname);
                statement2.setString(3, year + "-" + month + "-" + day);
                statement2.setInt(4, pesel);
                statement2.setInt(5, id);

                boolean result2 = statement2.execute();

            } else {
                System.out.println("Wrong position");
            }
        } else if (index == 2) {
            int pesel = 0;
            System.out.println("Pesel: ");
            pesel = Integer.parseInt(scanner.nextLine());
            PreparedStatement statement = conn.prepareStatement("CALL deleteWorkerByPesel(" + pesel + ");");
            ResultSet result = statement.executeQuery();
        } else if (index == 3) {
            System.out.println("Select position: ");
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM positions");

            ResultSet result = statement.executeQuery();

            ArrayList<String> array = new ArrayList<>();
            int i = 1;
            while (result.next()) {
                System.out.println(i + "." + result.getString("pos_name"));
                array.add(result.getString("id"));
                i++;
            }

            int answer = Integer.parseInt(scanner.nextLine());

            int pesel = 0;
            System.out.println("Pesel: ");
            pesel = Integer.parseInt(scanner.nextLine());

            PreparedStatement statement2 = conn.prepareStatement("CALL deleteWorkerByPesel(?, ?);");
            statement2.setInt(1, pesel);
            statement2.setInt(2, answer);
            ResultSet result2 = statement2.executeQuery();

        } else if (index == 4) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM positions");

            ResultSet result = statement.executeQuery();

            ArrayList<String> array = new ArrayList<>();
            int i = 1;
            while (result.next()) {
                System.out.println(i + "." + result.getString("pos_name"));
                array.add(result.getString("id"));
                i++;
            }

            statement = conn.prepareStatement("SELECT * FROM workers");
            result = statement.executeQuery();

            while (result.next()){
                System.out.println(
                        result.getString("worker_name") + "\t"
                                + result.getString("worker_surname") + "\t"
                                + result.getString("worker_bth") + "\t"
                                + array.get(result.getInt("position") - 1)
                );
            }
        }
    }

    public DirectorHandler(Connection conn) {
        super(conn);
    }

    @Override
    public void mainMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int answer = 0;
        boolean isAnswer = false;

        System.out.println("What do you want to do? \n" +
                "1.Add new worker \n" +
                "2.Remove worker \n" +
                "3.Change worker position \n" +
                "4.See actual workers data");
        answer = Integer.parseInt(scanner.nextLine());

        changeWorkersStatus(answer, scanner);


    }
}
