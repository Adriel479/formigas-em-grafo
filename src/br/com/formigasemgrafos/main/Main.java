package br.com.formigasemgrafos.main;

import java.io.FileNotFoundException;

import br.com.formigasemgrafo.cenas.DefinicaoDeGrafo;
import br.com.formigasemgrafo.cenas.Desafios;
import br.com.formigasemgrafo.cenas.MapaAlimentacao;
import br.com.formigasemgrafo.cenas.MapaProtecao;
import br.com.formigasemgrafo.cenas.Menu;
import br.com.formigasemgrafo.core.Jogo;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Jogo formigasEmGrafo = new Jogo();
		formigasEmGrafo.adicionarCena("menuPrincipal", new Menu());
		formigasEmGrafo.adicionarCena("definicaoDeGrafo", new DefinicaoDeGrafo());
		formigasEmGrafo.adicionarCena("desafios", new Desafios());
		formigasEmGrafo.adicionarCena("mapaAlimentacao", new MapaAlimentacao());
		formigasEmGrafo.adicionarCena("mapaProtecao", new MapaProtecao());
		formigasEmGrafo.setCenaAtual("menuPrincipal");
		formigasEmGrafo.executarJogo();

	}

}
