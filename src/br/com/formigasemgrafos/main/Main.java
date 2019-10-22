package br.com.formigasemgrafos.main;

import java.io.FileNotFoundException;

import br.com.formigasemgrafo.cenas.BuscaEmLarguraFase1;
import br.com.formigasemgrafo.cenas.BuscaEmLarguraFase2;
import br.com.formigasemgrafo.cenas.BuscaEmLarguraFase3;
import br.com.formigasemgrafo.cenas.BuscaEmLarguraFase4;
import br.com.formigasemgrafo.cenas.BuscaEmLarguraFase5;
import br.com.formigasemgrafo.cenas.BuscaEmLarguraFase6;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase1;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase2;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase3;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase4;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase5;
import br.com.formigasemgrafo.cenas.BuscaEmProfundidadeFase6;
import br.com.formigasemgrafo.cenas.DefinicaoDeGrafo;
import br.com.formigasemgrafo.cenas.Desafios;
import br.com.formigasemgrafo.cenas.MapaAlimentacaoLargura;
import br.com.formigasemgrafo.cenas.MapaAlimentacaoProfundidade;
import br.com.formigasemgrafo.cenas.Menu;
import br.com.formigasemgrafo.core.Jogo;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Jogo formigasEmGrafo = new Jogo();
		formigasEmGrafo.adicionarCena("menuPrincipal", new Menu());
		formigasEmGrafo.adicionarCena("definicaoDeGrafo", new DefinicaoDeGrafo());
		formigasEmGrafo.adicionarCena("desafios", new Desafios());
		formigasEmGrafo.adicionarCena("mapaAlimentacaoProfundidade", new MapaAlimentacaoProfundidade());
		formigasEmGrafo.adicionarCena("mapaAlimentacaoLargura", new MapaAlimentacaoLargura());
		formigasEmGrafo.adicionarCena("fase1BuscaEmProfundidade", new BuscaEmProfundidadeFase1());
		formigasEmGrafo.adicionarCena("fase2BuscaEmProfundidade", new BuscaEmProfundidadeFase2());
		formigasEmGrafo.adicionarCena("fase3BuscaEmProfundidade", new BuscaEmProfundidadeFase3());
		formigasEmGrafo.adicionarCena("fase4BuscaEmProfundidade", new BuscaEmProfundidadeFase4());
		formigasEmGrafo.adicionarCena("fase5BuscaEmProfundidade", new BuscaEmProfundidadeFase5());
		formigasEmGrafo.adicionarCena("fase6BuscaEmProfundidade", new BuscaEmProfundidadeFase6());
		formigasEmGrafo.adicionarCena("fase1BuscaEmLargura", new BuscaEmLarguraFase1());
		formigasEmGrafo.adicionarCena("fase2BuscaEmLargura", new BuscaEmLarguraFase2());
		formigasEmGrafo.adicionarCena("fase3BuscaEmLargura", new BuscaEmLarguraFase3());
		formigasEmGrafo.adicionarCena("fase4BuscaEmLargura", new BuscaEmLarguraFase4());
		formigasEmGrafo.adicionarCena("fase5BuscaEmLargura", new BuscaEmLarguraFase5());
		formigasEmGrafo.adicionarCena("fase6BuscaEmLargura", new BuscaEmLarguraFase6());
		formigasEmGrafo.setCenaAtual("menuPrincipal");
		formigasEmGrafo.executarJogo();
	}

}
