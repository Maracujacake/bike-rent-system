package domain;

public class Locadora {

    private Long id;
    private String Senha;
    private String Nome;
    private String Cidade;
    private String cnpj;
    private String email;

    public Locadora(Long id, String senha, String nome, String cidade, String cnpj, String email) {
        this.Senha = senha;
        this.Nome = nome;
        this.Cidade = cidade;
        this.id = id;
        this.cnpj = cnpj;
        this.email = email;
    }

    public Locadora(String senha, String nome, String cidade, String cnpj, String email) {
        this.Senha = senha;
        this.Nome = nome;
        this.Cidade = cidade;
        this.cnpj = cnpj;
        this.email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        this.Senha = senha;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        this.Cidade = cidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
