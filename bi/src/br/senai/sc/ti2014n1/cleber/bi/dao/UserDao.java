package br.senai.sc.ti2014n1.cleber.bi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senai.sc.ti2014n1.cleber.bi.model.dominio.User;

public class UserDao extends Dao{
	
	private Connection connection;
	private final String INSERT = "INSERT INTO user (ideia, descricao) values (?,?)";
	private final String UPDATE = "UPDATE ideia SET titulo = ?,  descricao = ? WHERE iduser = ?";
	private final String DELETE = "DELETE FROM ideia WHERE iduser = ?";
	private final String SELECT = "SELECT * FROM user"; 
	private final String SELECT_ID = "SELECT * FROM ideia WHERE idUser = ?";
	
	public void salvar(User user){
		try {
			PreparedStatement ps = null;
			
			ps = connection.prepareStatement(INSERT);
			ps.setString(1, user.getTitulo());
			ps.setString(2, user.getDescricao());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Erro ao executar o insert: " + e);
			e.printStackTrace();
		}
	}
	
	public void alterar(User user) {
		try {
			PreparedStatement ps = null;
			getConnection();
			
			ps = connection.prepareStatement(UPDATE);
			ps.setString(1, user.getTitulo());
			ps.setString(2, user.getDescricao());
			ps.setLong(3, user.getIdUser());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Erro ao executar o update: " + e);
		} finally {
			getConnection();
		}
		
	}

	public void excluir(User user) {
		try {
			PreparedStatement ps = null;
			getConnection();
			ps = connection.prepareStatement(DELETE);
			ps.setLong(1, user.getIdUser());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro a executar o delete: " + e);
		} finally {
			getConnection();
		}
	}

	public List<User> listarTodos() {
		List<User> users = new ArrayList<User>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			getConnection();
			ps = connection.prepareStatement(SELECT);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setTitulo(rs.getString("titulo"));
				user.setDescricao(rs.getString("descricao"));
				user.setIdUser(rs.getLong("idUser"));
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar o select de user: " + e);
		} finally {
			getConnection();
		}
		return users;
	}

}
