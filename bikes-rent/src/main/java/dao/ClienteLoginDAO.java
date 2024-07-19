package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Cliente;

// ** Verificação de Login de CLIENTE
public class ClienteLoginDAO extends GenericDAO{
    

    public Cliente authCliente(String email, String senha) {
        String sql = "SELECT * FROM cliente WHERE email = ? AND senha = ?";
        Cliente cliente = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String cpf = resultSet.getString("cpf");
                Date dataNascimento = resultSet.getDate("dataNascimento");

                cliente = new Cliente(id, email, senha, nome, telefone, sexo, cpf, dataNascimento);
            }

            resultSet.close();
            statement.close();
            con.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }
}
