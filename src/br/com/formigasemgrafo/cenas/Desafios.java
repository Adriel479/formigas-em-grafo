package br.com.formigasemgrafo.cenas;

import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.Util;

public class Desafios extends Cena {

	private Sprite fundoDesafios;
	private Sprite botaoProfundidade0;
	private Sprite botaoProfundidade1;
	private Sprite botaoLargura0;
	private Sprite botaoLargura1;
	private Sprite botaoVoltar0;
	private Sprite botaoVoltar1;

	@Override
	public void onCarregar() {
		imagem.carregarImagem("fundoDesafios", "/assets/fundoDesafios.png");
		imagem.carregarImagem("botaoProfundidade0", "/assets/botaoProfundidade0.png");
		imagem.carregarImagem("botaoProfundidade1", "/assets/botaoProfundidade1.png");
		imagem.carregarImagem("botaoLargura0", "/assets/botaoLargura0.png");
		imagem.carregarImagem("botaoLargura1", "/assets/botaoLargura1.png");
		imagem.carregarImagem("botaoVoltar0", "/assets/botaoVoltar0.png");
		imagem.carregarImagem("botaoVoltar1", "/assets/botaoVoltar1.png");
	}

	@Override
	public void onAtualizar() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoProfundidade0)) {
			botaoProfundidade0.setVisivel(false);
			botaoProfundidade1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("ajudaBuscaEmProfundidade");
			}
		} else {
			botaoProfundidade0.setVisivel(true);
			botaoProfundidade1.setVisivel(false);
		}

		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoLargura0)) {
			botaoLargura0.setVisivel(false);
			botaoLargura1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("mapaAlimentacaoLargura");
			}
		} else {
			botaoLargura0.setVisivel(true);
			botaoLargura1.setVisivel(false);
		}

		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoVoltar0)) {
			botaoVoltar0.setVisivel(false);
			botaoVoltar1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("definicaoDeGrafo");
			}
		} else {
			botaoVoltar0.setVisivel(true);
			botaoVoltar1.setVisivel(false);
		}
	}

	@Override
	public void onCriar() {
		fundoDesafios = new Sprite(0, 0, imagem.getImagem("fundoDesafios"));
		botaoProfundidade0 = new Sprite(125, 397, imagem.getImagem("botaoProfundidade0"));
		botaoProfundidade1 = new Sprite(125, 397, imagem.getImagem("botaoProfundidade1"));
		botaoLargura0 = new Sprite(424, 397, imagem.getImagem("botaoLargura0"));
		botaoLargura1 = new Sprite(424, 397, imagem.getImagem("botaoLargura1"));
		botaoVoltar0 = new Sprite(277, 470, imagem.getImagem("botaoVoltar0"));
		botaoVoltar1 = new Sprite(277, 470, imagem.getImagem("botaoVoltar1"));

		botaoProfundidade1.setVisivel(false);
		botaoLargura1.setVisivel(false);
		botaoVoltar1.setVisivel(false);

		adicionarObjetoRenderizavel(fundoDesafios);
		adicionarObjetoRenderizavel(botaoProfundidade0);
		adicionarObjetoRenderizavel(botaoProfundidade1);
		adicionarObjetoRenderizavel(botaoLargura0);
		adicionarObjetoRenderizavel(botaoLargura1);
		adicionarObjetoRenderizavel(botaoVoltar0);
		adicionarObjetoRenderizavel(botaoVoltar1);
	}

	@Override
	public void onDescarregar() {

	}

}
