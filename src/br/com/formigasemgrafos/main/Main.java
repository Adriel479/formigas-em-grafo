package br.com.formigasemgrafos.main;

import br.com.formigasemgrafo.cenas.DefinicaoDeGrafo;
import br.com.formigasemgrafo.cenas.Menu;
import br.com.formigasemgrafo.core.Jogo;

public class Main {

	public static void main(String[] args) {
		Jogo formigasEmGrafo = new Jogo();
		formigasEmGrafo.adicionarCena("menuPrincipal", new Menu());
		formigasEmGrafo.adicionarCena("definicaoDeGrafo", new DefinicaoDeGrafo());
		formigasEmGrafo.setCenaAtual("menuPrincipal");
		formigasEmGrafo.executarJogo();
	}

}
