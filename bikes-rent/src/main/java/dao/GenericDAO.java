package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;
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
        Dotenv dotenv = Dotenv.load();

        String dbpass= dotenv.get("DB_PASSWORD");
        String dbUser = dotenv.get("DB_USER");
        System.out.println("dbpass: "+dbpass + " dbUser: "+dbUser);
        // return DriverManager.getConnection(url, "root", "root");
        // configurar usuario e senha conforme o banco

     
        return DriverManager.getConnection(url, dbUser, dbpass);
    }
}
