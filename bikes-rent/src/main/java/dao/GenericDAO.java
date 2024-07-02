package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDAO {
    public GenericDAO() {
        try {

            /* Setup Banco de dados MySQL */
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/bikeRentSystem?serverTimezone=America/New_York";

        // return DriverManager.getConnection(url, "root", "root");
        // configurar usuario e senha conforme o banco

        // String user = "root";
        // String password = "root";
        String user = "chris";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}
