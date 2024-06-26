package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Locadora;

public class LocadaraDAO extends GenericDAO {
    // CREATE
    public Boolean insert(Locadora Locadora) {

        String sql = "INSERT INTO locadora (email, senha, nome, cnpj, cidade) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            ;
            Boolean existe = this.getByCnpj(Locadora.getCnpj()) != null || this.get(Locadora.getEmail()) != null;
            if (existe) {
                statement.close();
                con.close();
                return false;
            }
            statement = con.prepareStatement(sql);
            statement.setString(1, Locadora.getEmail());
            statement.setString(2, Locadora.getSenha());
            statement.setString(3, Locadora.getNome());
            statement.setString(4, Locadora.getCnpj());
            statement.setString(5, Locadora.getCidade());

            statement.executeUpdate();

            statement.close();
            con.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // READ ALL
    public List<Locadora> getAll() {

        List<Locadora> listaLocadoras = new ArrayList<>();
        String sql = "SELECT * from locadora";

        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");
                String cnpj = resultSet.getString("cnpj");

                Locadora Locadora = new Locadora(id, senha, nome, cidade, cnpj, email);
                listaLocadoras.add(Locadora);
            }

            // teste de debug
            // listaLocadoras.forEach(Locadora -> {
            // System.out.println(Locadora.getDataNascimento() + " " + Locadora.getNome());
            // });

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLocadoras;
    }

    // READ BY ID
    public Locadora get(Long id) {
        String sql = "SELECT * from locadora where id = ?";
        Locadora Locadora = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");
                String cnpj = resultSet.getString("cnpj");

                Locadora = new Locadora(id, senha, nome, cidade, cnpj, email);
            }

            resultSet.close();
            statement.close();
            con.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Locadora;
    }

    // Get by email
    public Locadora get(String email) {
        String sql = "SELECT * from locadora where email = ?";
        Locadora Locadora = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");
                String cnpj = resultSet.getString("cnpj");

                Locadora = new Locadora(id, senha, nome, cidade, cnpj, email);
            }

            resultSet.close();
            statement.close();
            con.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Locadora;
    }

    // Get by cnpj
    public Locadora getByCnpj(String cnpj) {
        String sql = "SELECT * from locadora where cnpj = ?";
        Locadora Locadora = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, cnpj);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");
                String email = resultSet.getString("email");

                Locadora = new Locadora(id, senha, nome, cidade, email, cnpj);
            }

            resultSet.close();
            statement.close();
            con.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Locadora;
    }

    // UPDATE
    public void update(Locadora locadora) {
        String sql = "UPDATE locadora SET email = ?, senha = ?, nome = ?, cnpj = ?, cidade = ? WHERE id = ?";
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, locadora.getEmail());
            statement.setString(2, locadora.getSenha());
            statement.setString(3, locadora.getNome());
            statement.setString(4, locadora.getCnpj());
            statement.setString(5, locadora.getCidade());
            statement.setLong(6, locadora.getId());

            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    public void delete(Long id) {
        String sql = "DELETE FROM locadora where id = ?";

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
