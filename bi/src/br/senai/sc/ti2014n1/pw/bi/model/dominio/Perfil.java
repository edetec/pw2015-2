package br.senai.sc.ti2014n1.pw.bi.model.dominio;

import java.io.Serializable;

public class Perfil implements Serializable{
	private static final long serialVersionUID = -2888395855507609855L;
	private Long id;
	private Integer nivel;
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
