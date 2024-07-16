package domain;

import java.sql.Date;

import utils.DataUtils;

public class Cliente {
	private Long Id;
	private String Email;
	private String Senha;
	private String Nome;
	private String Telefone;
	private String Sexo;
	private Date DataNascimento;
	private String dataFormatada;
	private String cpf;

	public Cliente(Long id, String email, String senha, String nome, String telefone, String sexo,
			String cpf, Date dataNascimento) {
		this.Id = id;
		this.Email = email;
		this.Senha = senha;
		this.Nome = nome;
		this.Telefone = telefone;
		this.Sexo = sexo;
		this.DataNascimento = dataNascimento;
		this.cpf = cpf;
		setDataFormatada();
	}

	public Cliente(String email, String senha, String nome, String telefone, String sexo,
			String cpf, Date dataNascimento) {
		this.Email = email;
		this.Senha = senha;
		this.Nome = nome;
		this.Telefone = telefone;
		this.Sexo = sexo;
		this.DataNascimento = dataNascimento;
		this.cpf = cpf;
		setDataFormatada();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		this.Email = email;
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

	public String getTelefone() {
		return Telefone;
	}

	public void setTelefone(String telefone) {
		this.Telefone = telefone;
	}

	public String getSexo() {
		return Sexo;
	}

	public void setSexo(String sexo) {
		this.Sexo = sexo;
	}

	public Date getDataNascimento() {
		return DataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.DataNascimento = dataNascimento;
		setDataFormatada();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataFormatada() {
		return DataUtils.getformatDate(DataNascimento);
	}

	public void setDataFormatada() {
		this.dataFormatada = DataUtils.getformatDate(DataNascimento);;
	}
}
