package domain;


public class Admin {
	private Long Id;
	private String Email;
	private String Senha;

	public Admin(Long id, String email, String senha) {
		this.Id = id;
		this.Email = email;
		this.Senha = senha;
	}

    public Admin(String email, String senha) {
        this.Email = email;
        this.Senha = senha;
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
}
