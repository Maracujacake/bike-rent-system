package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;

public class ClienteDAO extends GenericDAO {


    //READ ALL
    public List<Cliente> getAll() {

        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * from cliente";

        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String cpf = resultSet.getString("cpf");
                Date dataNascimento = resultSet.getDate("dataNascimento");
    
                Cliente cliente = new Cliente(id, email, senha, nome, telefone, sexo, cpf, dataNascimento);
                listaClientes.add(cliente);
            }
            
            // teste de debug
            // listaClientes.forEach(cliente -> {
            // System.out.println(cliente.getDataNascimento() + " " + cliente.getNome());
            // });
            
            
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaClientes;
    }

    /* 

	// READ BY ID
    public Cliente get(Long id) {
        Cliente Cliente = null;
        String sql = "SELECT * from Cliente where id = ?";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

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
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Cliente;
    }
    */
}
