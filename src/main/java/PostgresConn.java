import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class PostgresConn {
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        DriverManager.registerDriver(new org.postgresql.Driver());
        String jdbcURL = "jdbc:postgresql://10.0.6.149:5432/branchx_db?ssl=false";
        conn = DriverManager.getConnection(jdbcURL, "bx_user", "BrEx@23!");
        return conn;
    }
}
