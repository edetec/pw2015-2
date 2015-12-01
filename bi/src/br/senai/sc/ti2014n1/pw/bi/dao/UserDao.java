package br.senai.sc.ti2014n1.pw.bi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sun.misc.Perf.GetPerfAction;
import br.senai.sc.ti2014n1.pw.bi.model.dominio.Perfil;
import br.senai.sc.ti2014n1.pw.bi.model.dominio.User;

public class UserDao extends Dao {

	private static final String SELECT_EMAIL = "SELECT * FROM user WHERE email = ?";
	private final String INSERT = "INSERT INTO user (nome, email, senha, foto, perfil_id) values (?,?,?,?,?)";
	private final String UPDATE = "UPDATE user SET nome = ?,  email = ?, senha = ?, foto = ?, perfil_id = ? WHERE id = ?";
	private final String DELETE = "DELETE FROM user WHERE id = ?";
	private final String SELECT = "SELECT * FROM user";
	private final String SELECT_ID = "SELECT * FROM user WHERE id = ?";

	private PerfilDao perfilDao;

	public UserDao() {
		perfilDao = new PerfilDao();
	}

	public void salvar(User user) throws Exception {
		try {
			getConnection().setAutoCommit(false);
			Perfil perfil = perfilDao.salvar(user.getPerfil());
			user.setPerfil(perfil);

			if (user.getId() == 0) {
				inserir(user);
			} else {
				alterar(user);
			}

			getConnection().commit();
		} catch (Exception e) {
			getConnection().rollback();
			throw e;
		} finally {
			getConnection().setAutoCommit(true);
		}
	}

	private void inserir(User user) throws Exception {
		try {

			PreparedStatement ps = getConnection().prepareStatement(INSERT);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getSenha());
			ps.setString(4, user.getFoto());
			ps.setLong(5, user.getPerfil().getId());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao tentar salvar o usuário");
		}
	}

	public void alterar(User user) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(UPDATE);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getSenha());
			ps.setString(4, user.getFoto());
			ps.setLong(5, user.getPerfil().getId());
			ps.setLong(6, user.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao executar o update: " + e);
		}

	}

	public void excluir(Long id) throws Exception {
		User user = buscarPorId(id);
		if (user == null) {
			return;
		}

		try {
			getConnection().setAutoCommit(false);

			PreparedStatement ps = getConnection().prepareStatement(DELETE);
			ps.setLong(1, id);
			ps.executeUpdate();

			if (user.getPerfil() != null) {
				perfilDao.excluir(user.getPerfil().getId());
			}

			getConnection().commit();
		} catch (SQLException e) {
			getConnection().rollback();
			e.printStackTrace();
			System.out.println("Erro a executar o delete: " + e);
			throw new Exception("Erro ao tentar excluir");
		} finally {
			getConnection().setAutoCommit(true);
		}
	}

	public List<User> listarTodos() {
		List<User> users = new ArrayList<User>();
		try {
			PreparedStatement ps = getConnection().prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = parseUser(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao executar o select de user: " + e);
		}
		return users;
	}

	private User parseUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setNome(rs.getString("nome"));
		user.setEmail(rs.getString("email"));
		user.setId(rs.getLong("id"));
		user.setSenha(rs.getString("senha"));
		user.setFoto(rs.getString("foto"));
		user.setPerfil(getPerfil(rs));
		return user;
	}

	private Perfil getPerfil(ResultSet rs) throws SQLException {
		Long idPerfil = rs.getLong("perfil_id");
		return perfilDao.buscarPorId(idPerfil);
	}

	public User buscarPorId(Long id) {
		User user = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(SELECT_ID);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = parseUser(rs);
			}
			
			ps.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao executar o select de user: " + e);
		}
		return null;
	}

	public User buscaPorEmail(String email) {
		try {
			PreparedStatement preparedStatement = getConnection()
					.prepareStatement(SELECT_EMAIL);
			preparedStatement.setString(1, email);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return parseUser(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
