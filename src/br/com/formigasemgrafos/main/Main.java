package br.com.formigasemgrafos.main;

import java.io.FileNotFoundException;

import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase1;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase2;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase3;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase4;
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
		formigasEmGrafo.adicionarCena("fase2BuscaEmProfundidade", new BuscaEmProfundidadeFase2());
		formigasEmGrafo.adicionarCena("fase3BuscaEmProfundidade", new BuscaEmProfundidadeFase3());
		formigasEmGrafo.adicionarCena("fase4BuscaEmProfundidade", new BuscaEmProfundidadeFase4());
//		formigasEmGrafo.adicionarCena("fase5BuscaEmProfundidade", new BuscaEmProfundidadeFase5());
//		formigasEmGrafo.adicionarCena("fase6BuscaEmProfundidade", new BuscaEmProfundidadeFase6());
		formigasEmGrafo.setCenaAtual("fase4BuscaEmProfundidade");
		formigasEmGrafo.executarJogo();
	}

}
