package br.senai.sc.ti2014n1.pw.bi.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.senai.sc.ti2014n1.pw.bi.model.UserRn;
import br.senai.sc.ti2014n1.pw.bi.model.dominio.User;

@SessionScoped
@ManagedBean
public class SessaoMB {
	private User usuarioLogado;
	
	private User usuarioForm;
	
	@PostConstruct
	public void iniciar() {
		usuarioForm = new  User();
	}
	
	public User getUsuarioForm() {
		return usuarioForm;
	}

	public void setUsuarioForm(User usuarioForm) {
		this.usuarioForm = usuarioForm;
	}
	
	public String entrar(){
		UserRn rn = new UserRn();
		User usuarioBanco = rn.buscaPorEmail(usuarioForm.getEmail());
		
		if(usuarioBanco != null 
				&& usuarioForm.getEmail().equalsIgnoreCase(usuarioBanco.getEmail()) 
				&& usuarioForm.getSenha().equals(usuarioBanco.getSenha())){
			usuarioLogado = usuarioBanco;
			return "/index";
		}
		return "";
	}
	
	public String sair() {
		usuarioLogado = null;
		return "";
	}

	public Boolean estaLogado(){
		return usuarioLogado != null;
	}

}
