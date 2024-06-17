package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;

public class ClienteDAO extends GenericDAO {
    public List<Cliente> getAll() {

        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * from cliente";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                Date data = resultSet.getDate("dataNascimento");
                Cliente cliente = new Cliente(" ", " ", nome, " ", " ", cpf, data);
                listaClientes.add(cliente);
            }

            // listaClientes.forEach(cliente -> {
            // System.out.println(cliente.getDataNascimento() + " " + cliente.getNome());
            // });
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaClientes;
    }

    public Cliente get(Long id) {
        Cliente Cliente = null;
        String sql = "SELECT * from cliente where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                Date data = resultSet.getDate("dataNascimento");
                Cliente = new Cliente(" ", " ", nome, " ", " ", cpf, data);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Cliente;
    }
}
