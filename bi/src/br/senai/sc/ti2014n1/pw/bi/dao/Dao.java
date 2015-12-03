package br.senai.sc.ti2014n1.pw.bi.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class Dao {

	private Connection conn;

	public Connection getConnection() {
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				
				String servidor = externalContext.getInitParameter("servidor");
				String banco = externalContext.getInitParameter("banco");
				String usuario = externalContext.getInitParameter("usuario");
				String senha = externalContext.getInitParameter("senha");
				
				conn = DriverManager.getConnection(
						"jdbc:mysql://" + servidor +"/" + banco, usuario, senha);
				System.out.println("Conectou");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return conn;
	}
}
