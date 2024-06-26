package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.Locacao;

public class LocacaoDAO extends GenericDAO {

    // CREATE
    public Boolean insert(Locacao locacao) throws SQLException {

        String sql = "INSERT INTO locacao (cpfCliente, cnpjLocadora, dataHorario) VALUES (?, ?, ?)";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            Boolean existe = this.getBydataHorario(locacao.getRegistro()) != null;
            if (existe) {
                statement.close();
                con.close();
                return false;
            }

            String stringDiaHora = locacao.getRegistro().toString();
            statement = con.prepareStatement(sql);
            statement.setString(1, locacao.getCpfCliente());
            statement.setString(2, locacao.getCnpjLocadora());
            statement.setString(3, stringDiaHora);

            statement.executeUpdate();

            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // READ ALL
    public List<Locacao> getAll() {

        List<Locacao> listaLocacoes = new ArrayList<>();
        String sql = "SELECT * from locacao";

        try {
            Connection con = this.getConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                Long id = resultSet.getLong("id");
                String cpfCliente = resultSet.getString("cpfCliente");
                String cnpjLocadora = resultSet.getString("cnpjLocadora");
                String diaHora = resultSet.getString("dataHorario");

                LocalDateTime dtDiaHora = LocalDateTime.parse(diaHora);

                Locacao locacao = new Locacao(id, cpfCliente, cnpjLocadora, dtDiaHora);
                listaLocacoes.add(locacao);
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaLocacoes;
    }

    // READ BY ID
    public Locacao getByID(Long Id) {
        String sql = "SELECT * from locacao where id = ?";
        Locacao locacao = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, Id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cpfCliente = resultSet.getString("cpfCliente");
                String cnpjLocadora = resultSet.getString("cnpjLocadora");
                String diaHora = resultSet.getString("dataHorario");

                LocalDateTime dtDiaHora = LocalDateTime.parse(diaHora);

                locacao = new Locacao(Id, cpfCliente, cnpjLocadora, dtDiaHora);
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locacao;
    }

    // READ BY DATAHORARIO
    public Locacao getBydataHorario(LocalDateTime diaHora) {
        String sql = "SELECT * from locacao where dataHorario = ?";
        Locacao locacao = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            String stringDiaHora = diaHora.toString();
            statement.setString(1, stringDiaHora);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String cpfCliente = resultSet.getString("cpfCliente");
                String cnpjLocadora = resultSet.getString("cnpjLocadora");

                locacao = new Locacao(id, cpfCliente, cnpjLocadora, diaHora);
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locacao;
    }

    // UPDATE
    public void update(Locacao locacao) {
        String sql = "UPDATE locacao SET cpfCliente = ?, cnpjLocadora = ?, dataHorario = ? WHERE id = ?";
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            String dataHora = locacao.getRegistro().toString();
            statement.setString(1, locacao.getCpfCliente());
            statement.setString(2, locacao.getCnpjLocadora());
            statement.setString(3, dataHora);
            statement.setLong(4, locacao.getId());

            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    public void delete(Long id) {
        String sql = "DELETE FROM locacao where id = ?";

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
