package br.com.formigasemgrafos.main;

import java.io.FileNotFoundException;

import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase1;
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
		formigasEmGrafo.adicionarCena("fase1BuscaEmProfundidade", new BuscaEmProfundidadeFase1());
		formigasEmGrafo.setCenaAtual("fase1BuscaEmProfundidade");
		formigasEmGrafo.executarJogo();
	}

}
