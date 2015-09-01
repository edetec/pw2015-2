package br.senai.sc.ti2014n1.cleber.bi;

import br.senai.sc.ti2014n1.cleber.bi.dao.Dao;

public class Main {

	public static void main(String[] args) {
		conectar();

	}

	private static void conectar() {
		Dao dao = new Dao() {
		};
		dao.getConnection();

	}
}
