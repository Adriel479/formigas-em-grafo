package br.com.formigasemgrafo.cenas;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private long tempoAnterior;
	private Map<Point, List<Point>> mapaDeAdjacencia;
	private Map<Point, BarraDeEnergia> mapaDeBarras;
	private Point pontoAuxiliar;

	@Override
	public void carregar() {
		mapa.carregarMapaDeSprite("mapaBPFase1", "/mapas/buscaEmProfundidadeFase1.json", "/mapas/recursos.tsx");
		imagem.carregarImagem("jogador", "/assets/spriteSheet.png");
	}

	@Override
	public void atualizar() {

		// Controla a barra dos formigueiros
		long tempoAtual = System.currentTimeMillis();
		if (tempoAtual - tempoAnterior > 500) {
			for (BarraDeEnergia barraDeEnergia : mapaDeBarras.values()) {
				if (barraDeEnergia.getNivelDaBarra() <= 0) {
					barraDeEnergia.setVisivel(false);
				}
				if (barraDeEnergia.isExtremo())
					barraDeEnergia.atualizaNivelDaBarra(3);
				else
					barraDeEnergia.atualizaNivelDaBarra(1);
			}
			tempoAnterior = tempoAtual;
		}

		for (Point ponto : mapaDeBarras.keySet()) {
			BarraDeEnergia barra = mapaDeBarras.get(ponto);
			if (Util.houveInterseccao("jogador", sprite, "padrao", barra) && barra.isVisivel() && barra.isExtremo()
					&& entrada.isTeclaPressionada(KeyEvent.VK_A)) {
				List<Point> adjacentes = mapaDeAdjacencia.get(ponto);

				// Dado o (u, v) remove a adjacencia (v, u), pois u será removido do grafo
				if (adjacentes.size() > 0)
					mapaDeAdjacencia.get(adjacentes.get(0)).remove(ponto);

				// Quando um nó 'u' só possui um ou nenhum adjacente, ele é um extremo
				if (mapaDeAdjacencia.get(adjacentes.get(0)).size() <= 1) {
					mapaDeBarras.get(adjacentes.get(0)).setExtremo(true);
					mapaDeBarras.get(adjacentes.get(0)).atualizaNivelDaBarra(10);
				}

				barra.setVisivel(false);
				break;
			}
		}

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

		sprite.redimensionar(40, 40);
		sprite.criarAreaRetangular("jogador", 0, 0, 40, 40);

		mapaDeBarras = new HashMap<Point, BarraDeEnergia>();

		BarraDeEnergia barra = new BarraDeEnergia(360, 570, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(7, 11), barra);

		barra = new BarraDeEnergia(360, 270, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(7, 5), barra);

		barra = new BarraDeEnergia(60, 270, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(true);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(1, 5), barra);

		barra = new BarraDeEnergia(760, 270, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(true);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(15, 5), barra);

		barra = new BarraDeEnergia(360, 120, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(7, 2), barra);

		barra = new BarraDeEnergia(610, 120, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(true);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(12, 2), barra);

		adicionarObjetoRenderizavel(meuMapa);
		for (BarraDeEnergia barraE : mapaDeBarras.values())
			adicionarObjetoRenderizavel(barraE);
		adicionarObjetoRenderizavel(sprite);

		mapaDeAdjacencia = new HashMap<Point, List<Point>>();
		mapaDeAdjacencia.put(new Point(7, 11), new ArrayList<Point>(Arrays.asList(new Point(7, 5))));
		mapaDeAdjacencia.put(new Point(7, 5), new ArrayList<Point>(
				Arrays.asList(new Point(7, 11), new Point(1, 5), new Point(15, 5), new Point(7, 2))));
		mapaDeAdjacencia.put(new Point(1, 5), new ArrayList<Point>(Arrays.asList(new Point(7, 5))));
		mapaDeAdjacencia.put(new Point(15, 5), new ArrayList<Point>(Arrays.asList(new Point(7, 5))));
		mapaDeAdjacencia.put(new Point(7, 2), new ArrayList<Point>(Arrays.asList(new Point(12, 2), new Point(7, 5))));
		mapaDeAdjacencia.put(new Point(12, 2), new ArrayList<Point>(Arrays.asList(new Point(7, 2))));
	}

	@Override
	public void renderizar(Graphics2D g) {
		super.renderizar(g);
	}

	@Override
	public void descarregar() {

	}

}
