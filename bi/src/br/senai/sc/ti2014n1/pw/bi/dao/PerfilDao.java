package br.senai.sc.ti2014n1.pw.bi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.senai.sc.ti2014n1.pw.bi.model.dominio.Perfil;

public class PerfilDao extends Dao {

	private static final String INSERT = "INSERT INTO perfil (nivel, nome) values(?, ?)";
	private static final String UPDATE = "UPDATE perfil SET nivel = ?, nome = ? WHERE id = ?";
	private static final String SELECT_ID = "SELECT * FROM perfil WHERE id = ?";
	private static final String EXCLUIR = "DELETE FROM perfil WHERE id = ?";

	public Perfil salvar(Perfil perfil) throws Exception {

		try {
			if (perfil.getId() != null && perfil.getId() > 0) {
				atualizar(perfil);
			} else {
				inserir(perfil);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar o perfil");
		}
		return perfil;
	}

	private void atualizar(Perfil perfil) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement(UPDATE);
		ps.setInt(1, perfil.getNivel());
		ps.setString(2, perfil.getNome());
		ps.setLong(3, perfil.getId());
		ps.executeUpdate();
	}

	private void inserir(Perfil perfil) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, perfil.getNivel());
		ps.setString(2, perfil.getNome());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()){
			Long novoId = rs.getLong(1);
			perfil.setId(novoId);
		}
	}

	public Perfil buscarPorId(Long idPerfil) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement(SELECT_ID);
		ps.setLong(1, idPerfil);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return parsePerfil(rs);
		}
		return null;
	}

	private Perfil parsePerfil(ResultSet rs) throws SQLException {
		Perfil perfil = new Perfil();
		perfil.setId(rs.getLong("id"));
		perfil.setNivel(rs.getInt("nivel"));
		perfil.setNome(rs.getString("nome"));
		return perfil;
	}

	public void excluir(Long id) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement(EXCLUIR);
		ps.setLong(1, id);
		ps.executeUpdate();
	}
}























