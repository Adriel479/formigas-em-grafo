package br.com.formigasemgrafo.cenas;

import java.awt.Color;
import java.awt.image.BufferedImage;

import br.com.formigasemgrafo.core.Animacao;
import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.SpriteSheet;
import br.com.formigasemgrafo.core.Util;

public class ExemploBuscaEmProfundidade extends Cena {

	private enum Orientacao {
		CIMA, BAIXO, DIREITA, ESQUERDA
	};

	private Sprite fundo;
	private Sprite botaoVoltar0;
	private Sprite botaoVoltar1;
	private Sprite botaoProximo0;
	private Sprite botaoProximo1;
	private SpriteSheet sprite;
	private long tempoAnt;
	private int ato = 0;
	private BarraDeEnergia barraInicial;
	private BarraDeEnergia barraEsquerda;
	private BarraDeEnergia barraDireita;
	private BarraDeEnergia barraMeio;
	private Orientacao orientacaoDaFormiga;

	@Override
	public void onCarregar() {
		imagem.carregarImagem("fundoExemploBuscaEmProfundidade", "/assets/exemploBuscaEmProfundidade.png");
		imagem.carregarImagem("botaoProximo0", "/assets/botaoProximo0.png");
		imagem.carregarImagem("botaoProximo1", "/assets/botaoProximo1.png");
		imagem.carregarImagem("botaoVoltar0", "/assets/botaoVoltar0.png");
		imagem.carregarImagem("botaoVoltar1", "/assets/botaoVoltar1.png");
		imagem.carregarImagem("jogador", "/assets/spriteSheet.png");
	}

	@Override
	public void onAtualizar() {
		animarFormiga();
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoProximo0)) {
			botaoProximo0.setVisivel(false);
			botaoProximo1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("controles");
			}
		} else {
			botaoProximo0.setVisivel(true);
			botaoProximo1.setVisivel(false);
		}

		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoVoltar0)) {
			botaoVoltar0.setVisivel(false);
			botaoVoltar1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("ajudaBuscaEmProfundidade");
			}
		} else {
			botaoVoltar0.setVisivel(true);
			botaoVoltar1.setVisivel(false);
		}
	}

	@Override
	public void onCriar() {
		fundo = new Sprite(0, 0, imagem.getImagem("fundoExemploBuscaEmProfundidade"));
		botaoVoltar0 = new Sprite(99, 491, imagem.getImagem("botaoVoltar0"));
		botaoVoltar1 = new Sprite(99, 491, imagem.getImagem("botaoVoltar1"));
		botaoProximo0 = new Sprite(450, 491, imagem.getImagem("botaoProximo0"));
		botaoProximo1 = new Sprite(450, 491, imagem.getImagem("botaoProximo1"));

		botaoVoltar1.setVisivel(false);
		botaoProximo1.setVisivel(false);

		adicionarObjetoRenderizavel(fundo);
		adicionarObjetoRenderizavel(botaoVoltar0);
		adicionarObjetoRenderizavel(botaoVoltar1);
		adicionarObjetoRenderizavel(botaoProximo0);
		adicionarObjetoRenderizavel(botaoProximo1);

		barraInicial = new BarraDeEnergia(387, 458, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barraInicial.setNivelDaBarra(100);
		barraInicial.corDaBarra = Color.yellow;

		barraEsquerda = new BarraDeEnergia(112, 318, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barraEsquerda.setNivelDaBarra(100);

		barraDireita = new BarraDeEnergia(662, 318, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barraDireita.setNivelDaBarra(100);

		barraMeio = new BarraDeEnergia(387, 318, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barraMeio.setNivelDaBarra(100);

		adicionarObjetoRenderizavel(barraInicial);
		adicionarObjetoRenderizavel(barraEsquerda);
		adicionarObjetoRenderizavel(barraDireita);
		adicionarObjetoRenderizavel(barraMeio);

		sprite = new SpriteSheet(380, 445, imagem.getImagem("jogador"), 50, 50);
		sprite.adicionarAnimacao("animacaoCimaNormal", new Animacao(0, 0, new Integer[] { 0 }));
		sprite.adicionarAnimacao("animacaoCimaAtaque", new Animacao(0, 0, new Integer[] { 1 }));
		sprite.adicionarAnimacao("animacaoCimaMovimento", new Animacao(0, 0, new Integer[] { 2, 3 }));
		sprite.adicionarAnimacao("animacaoBaixoNormal", new Animacao(0, 50, new Integer[] { 0 }));
		sprite.adicionarAnimacao("animacaoBaixoAtaque", new Animacao(0, 50, new Integer[] { 1 }));
		sprite.adicionarAnimacao("animacaoBaixoMovimento", new Animacao(0, 50, new Integer[] { 2, 3 }));
		sprite.adicionarAnimacao("animacaoDireitaNormal", new Animacao(0, 100, new Integer[] { 0 }));
		sprite.adicionarAnimacao("animacaoDireitaAtaque", new Animacao(0, 100, new Integer[] { 1 }));
		sprite.adicionarAnimacao("animacaoDireitaMovimento", new Animacao(0, 100, new Integer[] { 2, 3 }));
		sprite.adicionarAnimacao("animacaoEsquerdaNormal", new Animacao(0, 150, new Integer[] { 0 }));
		sprite.adicionarAnimacao("animacaoEsquerdaAtaque", new Animacao(0, 150, new Integer[] { 1 }));
		sprite.adicionarAnimacao("animacaoEsquerdaMovimento", new Animacao(0, 150, new Integer[] { 2, 3 }));
		sprite.executarAnimacao("animacaoCimaNormal");
		orientacaoDaFormiga = Orientacao.CIMA;
		sprite.redimensionar(40, 40);
		sprite.criarAreaRetangular("jogador", 14, 5, 12, 34);
		adicionarObjetoRenderizavel(sprite);
		
		ato = 0;
		tempoAnt = 0;
		barraInicial.setVisivel(true);
		barraEsquerda.setVisivel(true);
		barraDireita.setVisivel(true);
		barraMeio.setVisivel(true);
		

	}

	private void animarFormiga() {

		long agora = System.currentTimeMillis();
		if (agora - tempoAnt > 100) {
			barraInicial.atualizaNivelDaBarra(1);
			barraEsquerda.atualizaNivelDaBarra(1);
			barraDireita.atualizaNivelDaBarra(1);
			barraMeio.atualizaNivelDaBarra(1);
			switch (orientacaoDaFormiga) {
			case CIMA:
				sprite.deslocarXY(0, -30);
				sprite.executarAnimacao("animacaoCimaMovimento");
				break;
			case BAIXO:
				sprite.deslocarXY(0, 30);
				sprite.executarAnimacao("animacaoBaixoMovimento");
				break;
			case DIREITA:
				sprite.deslocarXY(30, 0);
				sprite.executarAnimacao("animacaoDireitaMovimento");
				break;
			case ESQUERDA:
				sprite.deslocarXY(-30, 0);
				sprite.executarAnimacao("animacaoEsquerdaMovimento");
				break;
			}
			tempoAnt = agora;
			ato++;
		} else {
			switch (orientacaoDaFormiga) {
			case CIMA:
				sprite.executarAnimacao("animacaoCimaNormal");
				break;
			case BAIXO:
				sprite.executarAnimacao("animacaoBaixoNormal");
				break;
			case DIREITA:
				sprite.executarAnimacao("animacaoDireitaNormal");
				break;
			case ESQUERDA:
				sprite.executarAnimacao("animacaoEsquerdaNormal");
				break;
			}
		}

		switch (ato) {
		case 5:
			orientacaoDaFormiga = Orientacao.ESQUERDA;
			break;
		case 14:
			barraEsquerda.setVisivel(false);
			orientacaoDaFormiga = Orientacao.DIREITA;
			break;
		case 32:
			barraDireita.setVisivel(false);
			orientacaoDaFormiga = Orientacao.ESQUERDA;
			break;

		case 41:
			barraMeio.setVisivel(false);
			orientacaoDaFormiga = Orientacao.BAIXO;
			break;

		case 47:
			barraInicial.setNivelDaBarra(100);
			barraEsquerda.setNivelDaBarra(100);
			barraDireita.setNivelDaBarra(100);
			barraMeio.setNivelDaBarra(100);
			barraInicial.setVisivel(true);
			barraEsquerda.setVisivel(true);
			barraDireita.setVisivel(true);
			barraMeio.setVisivel(true);
			sprite.executarAnimacao("animacaoCimaNormal");
			orientacaoDaFormiga = Orientacao.CIMA;
			sprite.x = 380;
			sprite.y = 445;
			ato = 0;
			break;
		}

	}

	@Override
	public void onDescarregar() {

	}

}
