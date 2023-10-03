package model.entities;

public class Usuario {
	private Long id;
	private String login;
	private String senha;
	private boolean isAdmin;
	
	
	public Usuario() {
	}


	public Usuario(Long id, String login, String senha, boolean isAdmin) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.isAdmin = isAdmin;
	}
	
	public Usuario(String login, String senha) {
	    this.login = login;
	    this.senha = senha;
	    this.isAdmin = false;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
