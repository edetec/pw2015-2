package br.senai.sc.ti2014n1.cleber.bi;

import br.senai.sc.ti2014n1.cleber.bi.dao.Dao;
import br.senai.sc.ti2014n1.cleber.bi.model.UserRn;
import br.senai.sc.ti2014n1.cleber.bi.model.dominio.User;

public class Main {

	private static UserRn userRn;

	public static void main(String[] args) {
		userRn = new UserRn();
//		conectar();
//		inserirUm();
//		listarTodos();
//		buscarPorId();
//		editar();
//		excluir();

	}

	private static void excluir() {
		try {
			userRn.excluir(3L);
			System.out.println("Excluido");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void editar() {
		User user = userRn.buscarPorId(1L);
		user.setNome(user.getNome().concat(" A."));
		try {
			userRn.salvar(user);
			System.out.println("Salvo");
		} catch (Exception e) {
			System.out.println("Erro ao salvar: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void buscarPorId() {
		mostrar(userRn.buscarPorId(2L));
	}

	private static void listarTodos() {
		for(User user : userRn.listar()){
			mostrar(user);
		}
	}

	private static void mostrar(User user) {
		System.out.println("Nome: " + user.getNome());
		System.out.println("E-mail: " + user.getEmail());
		System.out.println("------------------------------");
	}

	private static void inserirUm() {
		User user = new User();
		user.setNome("Maria");
		user.setEmail("maria@email.com");
		
		try {
			userRn.salvar(user);
			System.out.println("Salvo");
		} catch (Exception e) {
			System.out.println("Erro ao salvar: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void conectar() {
		Dao dao = new Dao() {
		};
		dao.getConnection();
	}

}
