package br.com.formigasemgrafo.cenas;

import java.awt.event.KeyEvent;

import br.com.formigasemgrafo.core.Animacao;
import br.com.formigasemgrafo.core.Camada;
import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.MapaSprite;
import br.com.formigasemgrafo.core.SpriteSheet;
import br.com.formigasemgrafo.core.Util;

public class BuscaEmProfundidadeFase1 extends Cena {

	private enum Orientacao {
		CIMA, BAIXO, DIREITA, ESQUERDA
	};

	private SpriteSheet sprite;
	private int deslocamento = 10;
	private Orientacao orientacaoDaFormiga;
	private MapaSprite meuMapa;
	private Camada camadaDeArvores;

	@Override
	public void carregar() {
		mapa.carregarMapaDeSprite("mapaBPFase1", "/mapas/buscaEmProfundidadeFase1.json", "/mapas/recursos.tsx");
		imagem.carregarImagem("jogador", "/assets/spriteSheet.png");
	}

	@Override
	public void atualizar() {
		if (entrada.isTeclaPressionada(KeyEvent.VK_UP)) {
			sprite.deslocarXY(0, -deslocamento);
			if (Util.houveInterseccao(sprite, "arvores", camadaDeArvores) || Util.foraDaCena(sprite, this)) {
				sprite.deslocarXY(0, deslocamento);
			} else {
				sprite.executarAnimacao("animacaoCimaMovimento");
				orientacaoDaFormiga = Orientacao.CIMA;
			}
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_DOWN)) {
			sprite.deslocarXY(0, deslocamento);
			if (Util.houveInterseccao(sprite, "arvores", camadaDeArvores) || Util.foraDaCena(sprite, this)) {
				sprite.deslocarXY(0, -deslocamento);
			} else {
				sprite.executarAnimacao("animacaoBaixoMovimento");
				orientacaoDaFormiga = Orientacao.BAIXO;
			}
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_LEFT)) {
			sprite.deslocarXY(-deslocamento, 0);
			if (Util.houveInterseccao(sprite, "arvores", camadaDeArvores) || Util.foraDaCena(sprite, this)) {
				sprite.deslocarXY(deslocamento, 0);
			} else {
				sprite.executarAnimacao("animacaoEsquerdaMovimento");
				orientacaoDaFormiga = Orientacao.ESQUERDA;
			}
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_RIGHT)) {
			sprite.deslocarXY(deslocamento, 0);
			if (Util.houveInterseccao(sprite, "arvores", camadaDeArvores) || Util.foraDaCena(sprite, this)) {
				sprite.deslocarXY(-deslocamento, 0);
			} else {
				sprite.executarAnimacao("animacaoDireitaMovimento");
				orientacaoDaFormiga = Orientacao.DIREITA;
			}
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_SPACE) && orientacaoDaFormiga == Orientacao.CIMA) {
			sprite.executarAnimacao("animacaoCimaAtaque");
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_SPACE) && orientacaoDaFormiga == Orientacao.BAIXO) {
			sprite.executarAnimacao("animacaoBaixoAtaque");
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_SPACE) && orientacaoDaFormiga == Orientacao.ESQUERDA) {
			sprite.executarAnimacao("animacaoEsquerdaAtaque");
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_SPACE) && orientacaoDaFormiga == Orientacao.DIREITA) {
			sprite.executarAnimacao("animacaoDireitaAtaque");
		} else if (orientacaoDaFormiga == Orientacao.CIMA) {
			sprite.executarAnimacao("animacaoCimaNormal");
		} else if (orientacaoDaFormiga == Orientacao.BAIXO) {
			sprite.executarAnimacao("animacaoBaixoNormal");
		} else if (orientacaoDaFormiga == Orientacao.ESQUERDA) {
			sprite.executarAnimacao("animacaoEsquerdaNormal");
		} else if (orientacaoDaFormiga == Orientacao.DIREITA) {
			sprite.executarAnimacao("animacaoDireitaNormal");
		}

	}

	@Override
	public void criar() {
		sprite = new SpriteSheet(350, 500, imagem.getImagem("jogador"), 50, 50);

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

		meuMapa = mapa.getMapaDeSprite("mapaBPFase1");
		camadaDeArvores = meuMapa.getCamada(2);
		camadaDeArvores.criarAreaRetangular("arvores", 0, 0, 50, 50);
		adicionarObjetoRenderizavel(meuMapa);
		adicionarObjetoRenderizavel(sprite);

		sprite.redimensionar(40, 40);
	}

	@Override
	public void descarregar() {

	}

}
