package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Locadora;

// ** Verificação de Login de LOCADORA
public class LocadoraLoginDAO extends GenericDAO{
    
    public Locadora authLocadora(String email, String senha) {
        String sql = "SELECT * FROM locadora WHERE email = ? AND senha = ?";
        Locadora locadora = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                String cnpj = resultSet.getString("cnpj");
                String cidade = resultSet.getString("cidade");

                locadora = new Locadora(id, senha, nome, cidade, cnpj, email);
            }

            resultSet.close();
            statement.close();
            con.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return locadora;
    }
}
