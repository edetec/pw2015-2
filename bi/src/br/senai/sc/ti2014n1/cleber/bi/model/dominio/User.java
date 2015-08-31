package br.senai.sc.ti2014n1.cleber.bi.model.dominio;

public class User {
	private long idUser;
	private String titulo;
	private String descricao;

	public User() {
	}

	public User(long idUser, String titulo, String descricao) {
		this.idUser = idUser;
		this.titulo = titulo;
		this.descricao = descricao;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
