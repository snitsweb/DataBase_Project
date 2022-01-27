import java.sql.Connection;
import java.sql.SQLException;

abstract class Handler {
    Connection conn;

    public Handler(Connection conn){
        this.conn = conn;
    }

    public abstract void mainMenu() throws SQLException;
}
