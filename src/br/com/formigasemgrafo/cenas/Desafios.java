package br.com.formigasemgrafo.cenas;

import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.Util;

public class Desafios extends Cena {

	private Sprite fundoDesafios;
	private Sprite botaoAlimentacao0;
	private Sprite botaoAlimentacao1;
	private Sprite botaoProtecao0;
	private Sprite botaoProtecao1;
	private Sprite botaoVoltar0;
	private Sprite botaoVoltar1;

	@Override
	public void carregar() {
		imagem.carregarImagem("fundoDesafios", "/assets/fundoDesafios.png");
		imagem.carregarImagem("botaoAlimentacao0", "/assets/botaoAlimentacao0.png");
		imagem.carregarImagem("botaoAlimentacao1", "/assets/botaoAlimentacao1.png");
		imagem.carregarImagem("botaoProtecao0", "/assets/botaoProtecao0.png");
		imagem.carregarImagem("botaoProtecao1", "/assets/botaoProtecao1.png");
		imagem.carregarImagem("botaoVoltar0", "/assets/botaoVoltar0.png");
		imagem.carregarImagem("botaoVoltar1", "/assets/botaoVoltar1.png");
	}

	@Override
	public void atualizar() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoAlimentacao0)) {
			botaoAlimentacao0.setVisivel(false);
			botaoAlimentacao1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("mapaAlimentacao");
			}
		} else {
			botaoAlimentacao0.setVisivel(true);
			botaoAlimentacao1.setVisivel(false);
		}

		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoProtecao0)) {
			botaoProtecao0.setVisivel(false);
			botaoProtecao1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("mapaProtecao");
			}
		} else {
			botaoProtecao0.setVisivel(true);
			botaoProtecao1.setVisivel(false);
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
	public void criar() {
		fundoDesafios = new Sprite(0, 0, imagem.getImagem("fundoDesafios"));
		botaoAlimentacao0 = new Sprite(125, 397, imagem.getImagem("botaoAlimentacao0"));
		botaoAlimentacao1 = new Sprite(125, 397, imagem.getImagem("botaoAlimentacao1"));
		botaoProtecao0 = new Sprite(424, 397, imagem.getImagem("botaoProtecao0"));
		botaoProtecao1 = new Sprite(424, 397, imagem.getImagem("botaoProtecao1"));
		botaoVoltar0 = new Sprite(277, 470, imagem.getImagem("botaoVoltar0"));
		botaoVoltar1 = new Sprite(277, 470, imagem.getImagem("botaoVoltar1"));

		botaoAlimentacao1.setVisivel(false);
		botaoProtecao1.setVisivel(false);
		botaoVoltar1.setVisivel(false);

		adicionarObjetoRenderizavel(fundoDesafios);
		adicionarObjetoRenderizavel(botaoAlimentacao0);
		adicionarObjetoRenderizavel(botaoAlimentacao1);
		adicionarObjetoRenderizavel(botaoProtecao0);
		adicionarObjetoRenderizavel(botaoProtecao1);
		adicionarObjetoRenderizavel(botaoVoltar0);
		adicionarObjetoRenderizavel(botaoVoltar1);
	}

	@Override
	public void descarregar() {

	}

}
