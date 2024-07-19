package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;
import domain.Locacao;
import utils.DataUtils;

public class ClienteDAO extends GenericDAO {

    // CREATE
    public Boolean insert(Cliente cliente) {

        String sql = "INSERT INTO cliente (email, senha, nome, telefone, sexo, cpf, dataNascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            ;
            Boolean existe = this.getByCpf(cliente.getCpf()) != null || this.get(cliente.getEmail()) != null;
            if (existe) {
                statement.close();
                con.close();
                return false;
            }

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
        return true;
    }

    /********* get LOCACAO by ID *********/
    // obs: O mesmo código já existe em locacaoDAO, poderiamos criar um outro objeto baseado em locacaoDAO
    // para chamar o metodo ja existente em vez de escreve-lo dnv.
    public Locacao getLocacaoByID(Long id) {

        Locacao locacao = null;
        String sql = "SELECT * from locacao WHERE id = ?";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cpfCliente = resultSet.getString("cpfCliente");
                String cnpjLocadora = resultSet.getString("cnpjLocadora");
                String dataHorario = resultSet.getString("dataHorario");

                LocalDateTime dtDiaHora = LocalDateTime.parse(dataHorario);

                locacao = new Locacao(id, cpfCliente, cnpjLocadora, dtDiaHora);
            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locacao;
    }

    /********* get LOCAÇÕES by CPF *********/
    public List<Locacao> getLocacaoByCPF(String cpf) {

        List<Locacao> listaCliente = new ArrayList<>();
        String sql = "SELECT * from locacao WHERE cpfCliente = ?";

        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, cpf);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String cnpjLocadora = resultSet.getString("cnpjLocadora");
                String dataHorario = resultSet.getString("dataHorario");

                LocalDateTime dtDiaHora = LocalDateTime.parse(dataHorario);

                Locacao locacao = new Locacao(id, cpf, cnpjLocadora, dtDiaHora);
                listaCliente.add(locacao);
            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaCliente;
    }

    /********* update LOCAÇÃO by CPF *********/
    public void updateLocacao(Locacao locacao) {
        String sql = "UPDATE locacao SET cpfCliente = ?, cnpjLocadora = ?, dataHorario = ? WHERE id = ?";
        String dtDiaHora = locacao.getRegistroAsDateTime().toString();
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, locacao.getCpfCliente());
            statement.setString(2, locacao.getCnpjLocadora());
            statement.setString(3, dtDiaHora);
            statement.setLong(4, locacao.getId());

            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ ALL
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
                // Formatar a data para String no formato desejado
              
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

    // get por nome
    public Cliente get(String email) {
        String sql = "SELECT * from cliente where email = ?";
        Cliente cliente = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String cpf = resultSet.getString("cpf");
                Date dataNascimento = resultSet.getDate("dataNascimento");
                
                // Formatar a data para String no formato desejado
              /*  SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
                String dataFormatada = formatoDesejado.format(dataNascimento);

                // Converter de volta para Date, se necessário
                Date dataNascimentoFormatada = null;
                try {
                    dataNascimentoFormatada = (Date) formatoDesejado.parse(dataFormatada);
                } catch (ParseException e) {            
                    e.printStackTrace();
                }
                System.out.print(dataNascimentoFormatada);*/
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

    public Cliente getByCpf(String cpf) {
        String sql = "SELECT * from cliente where cpf = ?";
        Cliente cliente = null;
        try {
            Connection con = this.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, cpf);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String email = resultSet.getString("email");
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
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
