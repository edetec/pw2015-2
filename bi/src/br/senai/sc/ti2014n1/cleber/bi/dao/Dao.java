package br.senai.sc.ti2014n1.cleber.bi.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class Dao {

	private Connection conn;

	public Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(
						"jbdc:mysql://localhost/bi_bd", "root", "root");
				System.out.println("Conectou");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return conn;
	}
}
