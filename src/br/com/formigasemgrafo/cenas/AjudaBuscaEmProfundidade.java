package br.com.formigasemgrafo.cenas;

import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.Util;

public class AjudaBuscaEmProfundidade extends Cena {

	private Sprite fundo;
	private Sprite botaoVoltar0;
	private Sprite botaoVoltar1;
	private Sprite botaoProximo0;
	private Sprite botaoProximo1;

	@Override
	public void onCarregar() {
		imagem.carregarImagem("fundoAjudaBuscaEmProfundidade", "/assets/ajudaBuscaEmProfundidade.png");
		imagem.carregarImagem("botaoProximo0", "/assets/botaoProximo0.png");
		imagem.carregarImagem("botaoProximo1", "/assets/botaoProximo1.png");
		imagem.carregarImagem("botaoVoltar0", "/assets/botaoVoltar0.png");
		imagem.carregarImagem("botaoVoltar1", "/assets/botaoVoltar1.png");
	}

	@Override
	public void onAtualizar() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoProximo0)) {
			botaoProximo0.setVisivel(false);
			botaoProximo1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("exemploBuscaEmProfundidade");
			}
		} else {
			botaoProximo0.setVisivel(true);
			botaoProximo1.setVisivel(false);
		}

		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoVoltar0)) {
			botaoVoltar0.setVisivel(false);
			botaoVoltar1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("desafios");
			}
		} else {
			botaoVoltar0.setVisivel(true);
			botaoVoltar1.setVisivel(false);
		}
	}

	@Override
	public void onCriar() {
		fundo = new Sprite(0, 0, imagem.getImagem("fundoAjudaBuscaEmProfundidade"));
		botaoVoltar0 = new Sprite(99, 481, imagem.getImagem("botaoVoltar0"));
		botaoVoltar1 = new Sprite(99, 481, imagem.getImagem("botaoVoltar1"));
		botaoProximo0 = new Sprite(450, 481, imagem.getImagem("botaoProximo0"));
		botaoProximo1 = new Sprite(450, 481, imagem.getImagem("botaoProximo1"));

		botaoVoltar1.setVisivel(false);
		botaoProximo1.setVisivel(false);

		adicionarObjetoRenderizavel(fundo);
		adicionarObjetoRenderizavel(botaoVoltar0);
		adicionarObjetoRenderizavel(botaoVoltar1);
		adicionarObjetoRenderizavel(botaoProximo0);
		adicionarObjetoRenderizavel(botaoProximo1);
	}

	@Override
	public void onDescarregar() {

	}

}
