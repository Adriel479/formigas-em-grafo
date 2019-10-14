package br.com.formigasemgrafo.cenas;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
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
import br.com.formigasemgrafo.core.Sprite;
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
	private Camada camadaDeGrama;
	private long tempoAnterior;
	private Map<Point, List<Point>> mapaDeAdjacencia;
	private Map<Point, BarraDeEnergia> mapaDeBarras;
	private int nivelDaBarra = 3;
	private Sprite estrela;
	private Integer score = 0;

	@Override
	public void carregar() {
		mapa.carregarMapaDeSprite("mapaBPFase1", "/mapas/buscaEmProfundidadeFase1.json", "/mapas/recursos.tsx");
		imagem.carregarImagem("jogador", "/assets/spriteSheet.png");
		imagem.carregarImagem("estrela", "/assets/estrela.png");
		fonte.carregarFonte("fonte", "/assets/kenvector_future_thin.ttf", 20, Font.BOLD);
	}

	@Override
	public void atualizar() {

		long tempoAtual = System.currentTimeMillis();
		if (tempoAtual - tempoAnterior > 500) {
			for (BarraDeEnergia barraDeEnergia : mapaDeBarras.values()) {
				if (barraDeEnergia.isExtremo())
					barraDeEnergia.atualizaNivelDaBarra(nivelDaBarra);
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
				if (adjacentes.size() == 1) {
					mapaDeAdjacencia.get(adjacentes.get(0)).remove(ponto);
					if (mapaDeAdjacencia.get(adjacentes.get(0)).size() == 1) {
						mapaDeBarras.get(adjacentes.get(0)).setExtremo(true);
						mapaDeBarras.get(adjacentes.get(0)).atualizaNivelDaBarra(5);
					} else if (mapaDeAdjacencia.get(adjacentes.get(0)).size() == 0) {
						mapaDeBarras.get(adjacentes.get(0)).setExtremo(true);
						mapaDeBarras.get(adjacentes.get(0)).atualizaNivelDaBarra(5);
					}
					adjacentes.remove(0);
				}
				barra.setVisivel(false);
				score += 100;
				break;
			} else if (Util.houveInterseccao("jogador", sprite, "padrao", barra) && barra.isVisivel()
					&& !barra.isExtremo() && entrada.isTeclaPressionada(KeyEvent.VK_A)) {
				nivelDaBarra += 5;
				if (score - 100 >= 0)
					score -= 100;
				barra.setVisivel(false);
			}
		}
		
		if (Util.houveInterseccao(sprite, "grama", camadaDeGrama, "2")) {
			deslocamento = 3;
		} else {
			deslocamento = 10;
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

		estrela = new Sprite(0, 0, imagem.getImagem("estrela"));
		estrela.redimensionar(30, 30);
		estrela.setVisivel(false);

		meuMapa = mapa.getMapaDeSprite("mapaBPFase1");
		camadaDeArvores = meuMapa.getCamada(2);
		camadaDeGrama = meuMapa.getCamada(0);
		camadaDeArvores.criarAreaRetangular("arvores", 0, 0, 50, 50);
		camadaDeGrama.criarAreaRetangular("grama", 0, 0, 50, 50);

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
		adicionarObjetoRenderizavel(estrela);

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
		Composite composite = g.getComposite();
		g.setColor(Color.white);
		g.setFont(fonte.getFonte("fonte"));
		g.drawString("Score: " + score, 30, 30);
		g.setComposite(composite);
	}

	@Override
	public void descarregar() {

	}

}
