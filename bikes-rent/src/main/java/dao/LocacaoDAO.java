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

// ** FUnções com acesso ao banco de LOCACAO
public class LocacaoDAO extends GenericDAO {

    // *** Funções de CREATE ***

    // Cria locação
    public Boolean insert(Locacao locacao) throws SQLException {

        String sql = "INSERT INTO locacao (cpfCliente, cnpjLocadora, dataHorario) VALUES (?, ?, ?)";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            Boolean existe = this.getBydataHorario(locacao.getRegistroAsDateTime(), locacao.getCnpjLocadora()) != null || this.getBydataHorarioCpf(locacao.getRegistroAsDateTime(), locacao.getCpfCliente()) != null;
            if (existe) {
                statement.close();
                con.close();
                return false;
            }

            String stringDiaHora = locacao.getRegistroAsDateTime().toString();
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

    // *** Funções de READ ***

    // Seleciona todas as locações
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

    // Seleciona locação pelo ID
    public Locacao getByID(Long Id) {
        String sql = "SELECT * from locacao where id = ?";
        Locacao locacao = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, Id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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

    // Seleciona locação por Data-Hora e CNPJ
    public Locacao getBydataHorario(LocalDateTime diaHora, String cnpj) {
        String sql = "SELECT * from locacao where dataHorario = ? AND cnpjLocadora = ?";
        Locacao locacao = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            String stringDiaHora = diaHora.toString();
            statement.setString(1, stringDiaHora);
            statement.setString(2, cnpj);
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

    // Seleciona locação por Data-Hora e CPF
    public Locacao getBydataHorarioCpf(LocalDateTime diaHora, String cpf) {
        String sql = "SELECT * from locacao where dataHorario = ? AND cpfCliente = ?";
        Locacao locacao = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            String stringDiaHora = diaHora.toString();
            statement.setString(1, stringDiaHora);
            statement.setString(2, cpf);
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

    // *** Funções de UPDATE ***

    // Atualiza locação
    public void update(Locacao locacao) {
        String sql = "UPDATE locacao SET cpfCliente = ?, cnpjLocadora = ?, dataHorario = ? WHERE id = ?";
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            String dataHora = locacao.getRegistroAsDateTime().toString();
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

    // *** Funções de DELETE ***
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
