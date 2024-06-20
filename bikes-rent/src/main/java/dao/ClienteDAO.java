package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;

public class ClienteDAO extends GenericDAO {

	// CREATE
    public void insert(Cliente cliente) {

        String sql = "INSERT INTO cliente (email, senha, nome, telefone, sexo, cpf, dataNascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);;

            statement = con.prepareStatement(sql);
            statement.setString(1, cliente.getEmail());
            statement.setString(2, cliente.getSenha());
            statement.setString(3, cliente.getNome());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getSexo());
            statement.setString(6, cliente.getCpf());
            statement.setDate(7, cliente.getDataNascimento());
            statement.executeUpdate();

            statement.close();
            con.close();
        } 
        
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	
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


	// READ BY ID
    public Cliente get(Long id) {
        String sql = "SELECT * from cliente where id = ?";
        Cliente cliente = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, id);
            

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
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
    
    
    // UPDATE
    public void update(Cliente cliente) {
        String sql = "UPDATE cliente SET email = ?, senha = ?, nome = ?, telefone = ?, sexo = ?, cpf = ?, dataNascimento = ? WHERE id = ?";
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, cliente.getEmail());
            statement.setString(2, cliente.getSenha());
            statement.setString(3, cliente.getNome());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getSexo());
            statement.setString(6, cliente.getCpf());
            statement.setDate(7, new java.sql.Date(cliente.getDataNascimento().getTime()));
            statement.setLong(8, cliente.getId());
    
            statement.executeUpdate();
            statement.close();
            con.close();
        } 
        catch (SQLException e){
            throw new RuntimeException(e);            
        }
    }
    
    //DELETE
    public void delete(Long id) {
        String sql = "DELETE FROM cliente where id = ?";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setLong(1, id);
            statement.executeUpdate();

            statement.close();
            con.close();
        } 
        
        catch (SQLException e) {
        	throw new RuntimeException(e);
        }
    }
    
}
