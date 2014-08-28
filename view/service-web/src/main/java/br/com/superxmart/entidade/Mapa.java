package br.com.superxmart.entidade;

import java.util.ArrayList;
import java.util.List;

public class Mapa extends Entidade{
	
	private String nome;
	
	private List<Rota> rotas = new ArrayList<Rota>();

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

	public void addRota(Rota rota) {
		rotas.add(rota);
	}
	
	

}
