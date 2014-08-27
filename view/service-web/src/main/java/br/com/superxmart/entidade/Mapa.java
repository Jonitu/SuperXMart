package br.com.superxmart.entidade;

import java.util.List;

public class Mapa extends Entidade{
	
	private String nome;
	
	private List<Rota> rotas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}
	
	

}
