package br.senai.sc.ti2014n1.pw.bi.mb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.servlet.http.Part;

import br.senai.sc.ti2014n1.pw.bi.model.UserRn;
import br.senai.sc.ti2014n1.pw.bi.model.dominio.Perfil;
import br.senai.sc.ti2014n1.pw.bi.model.dominio.User;
import br.senai.sc.ti2014n1.pw.bi.util.UploadImagemUtil;

@ManagedBean
public class UserMB {
	private List<User> usuarios;
	private User user;
	private Part foto;
	private UserRn rn;
	
	@ManagedProperty(value = "#{sessaoMB}")
	private SessaoMB sessaoMB;

	@PostConstruct
	public void init() {
		rn = new UserRn();
		user = new User();
		user.setPerfil(new Perfil());
	}

	public List<User> getUsuarios() {
		if (usuarios == null) {
			usuarios = rn.listar();
		}
		return usuarios;
	}

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

	public User getUser() {
		return user;
	}

	public Part getFoto() {
		return foto;
	}

	public void setFoto(Part foto) {
		this.foto = foto;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SessaoMB getSessaoMB() {
		return sessaoMB;
	}

	public void setSessaoMB(SessaoMB sessaoMB) {
		this.sessaoMB = sessaoMB;
	}

	public String salvar() {
		try {
			System.out.println("Usuário Logado: " + sessaoMB.getNomeUsuarioLogado());
			String nomeFoto = UploadImagemUtil.copiar(foto, user.getFoto());
			user.setFoto(nomeFoto);
			rn.salvar(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "userlist";
	}

	public String excluir(String idParam) {
		Long id = Long.parseLong(idParam);
		
		User userAntigo = rn.buscarPorId(id);
		
		try {
			rn.excluir(id);
			UploadImagemUtil.remover(userAntigo.getFoto());
			usuarios = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String editar(String idParam) {
		Long id = Long.parseLong(idParam);
		user = rn.buscarPorId(id);
		return "userform";
	}

}
