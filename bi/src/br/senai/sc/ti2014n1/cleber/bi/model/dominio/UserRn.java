package br.senai.sc.ti2014n1.cleber.bi.model.dominio;

public class UserRn {
	
	public void salvar(User user) throws Exception {
		if (user.getTitulo().trim().isEmpty()) {
			throw new Exception("O titulo � obrigat�rio!");
		}

		if (user.getDescricao().trim().isEmpty()) {
			throw new Exception("A Descricao � obrigat�rio!");
		}
	}

}
