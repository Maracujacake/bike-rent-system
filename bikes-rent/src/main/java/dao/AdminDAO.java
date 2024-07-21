package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Admin;

// ** Funções com acesso ao banco de ADMIN

public class AdminDAO extends GenericDAO {
     public Admin authAdmin(String email, String senha) {
        String sql = "SELECT * FROM admin WHERE email = ? AND senha = ?";
        Admin admin = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);


            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");

                admin = new Admin(id, email, senha);
            }

            resultSet.close();
            statement.close();
            con.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }
}
