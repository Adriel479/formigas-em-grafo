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

public class BuscaEmProfundidadeFase3 extends Cena {

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
	private int nivelDaBarra;
	private int nivelDaBarraInterna;
	private Integer score;
	private SpriteSheet aranhas[];
	private Point[] pontos;
	private Orientacao[] orientacaoAranhas;
	private int[] deslocamentoAranha;
	private Sprite fimDeJogoAranha;
	private Sprite fimDeJogoFormigueiro;
	private Sprite fimDeJogoVitoria;
	private Sprite botaoVoltar0;
	private Sprite botaoVoltar1;
	private Sprite botaoProximo0;
	private Sprite botaoProximo1;
	private boolean pausa;
	private boolean vitoria;

	@Override
	public void onCarregar() {
		mapa.carregarMapaDeSprite("mapaBPFase3", "/mapas/buscaEmProfundidadeFase3.json", "/mapas/recursos.tsx");
		imagem.carregarImagem("jogador", "/assets/spriteSheet.png");
		imagem.carregarImagem("aranhas", "/assets/spriteSheetCompletoAranha.png");
		fonte.carregarFonte("fonte", "/assets/kenvector_future_thin.ttf", 20, Font.BOLD);
		imagem.carregarImagem("botaoVoltar0", "/assets/botaoVoltar0.png");
		imagem.carregarImagem("botaoVoltar1", "/assets/botaoVoltar1.png");
		imagem.carregarImagem("fimDeJogoAranha", "/assets/fimDeJogo0.png");
		imagem.carregarImagem("fimDeJogoFormigueiro", "/assets/fimDeJogo1.png");
		imagem.carregarImagem("fimDeJogoVitoria", "/assets/fimDeJogoVitoria.png");
		imagem.carregarImagem("botaoProximo0", "/assets/botaoProximo0.png");
		imagem.carregarImagem("botaoProximo1", "/assets/botaoProximo1.png");
	}

	@Override
	public void onAtualizar() {
		if (!pausa) {
			logicaParaDeixarJogadorLento();
			logicaParaAtualizacaoDoTempoDeVidaDosFormigueiros();
			if (!pausa) {
				logicaParaAlimentacaoDosFormigueiros();
				logicaControleDoJogador();
				logicaControleDasAranhas();
				logicaParaMatarFormiga();
			}
		} else if (pausa && vitoria) {
			logicaBotaoDeProximo();
		} else if (pausa) {
			logicaBotaoDeVoltar();
		}
	}

	@Override
	public void onCriar() {
		pausa = false;
		vitoria = false;
		score = 0;
		nivelDaBarra = 2;
		nivelDaBarraInterna = 1;
		deslocamentoAranha = new int[] { 6, 6 };
		orientacaoAranhas = new Orientacao[] { Orientacao.BAIXO, Orientacao.BAIXO };
		pontos = new Point[] { new Point(200, 150), new Point(550, 200) };
		criarMapa();
		criarBarrasDeVida();
		criarAranhas();
		criarJogador();
		criarFimDeJogo();
	}

	@Override
	public void renderizar(Graphics2D g) {
		super.renderizar(g);
		Composite composite = g.getComposite();
		g.setColor(Color.white);
		g.setFont(fonte.getFonte("fonte"));
		g.drawString("Pontuação: " + score, 30, 30);
		g.setComposite(composite);
	}

	@Override
	public void onDescarregar() {

	}

	private void criarFimDeJogo() {
		botaoVoltar0 = new Sprite(270, 360, imagem.getImagem("botaoVoltar0"));
		botaoVoltar0.setVisivel(false);
		botaoVoltar1 = new Sprite(270, 360, imagem.getImagem("botaoVoltar1"));
		botaoVoltar1.setVisivel(false);
		fimDeJogoAranha = new Sprite(170, 200, imagem.getImagem("fimDeJogoAranha"));
		fimDeJogoAranha.setVisivel(false);
		fimDeJogoFormigueiro = new Sprite(170, 200, imagem.getImagem("fimDeJogoFormigueiro"));
		fimDeJogoFormigueiro.setVisivel(false);
		fimDeJogoVitoria = new Sprite(170, 200, imagem.getImagem("fimDeJogoVitoria"));
		fimDeJogoVitoria.setVisivel(false);
		botaoProximo0 = new Sprite(270, 360, imagem.getImagem("botaoProximo0"));
		botaoProximo0.setVisivel(false);
		botaoProximo1 = new Sprite(270, 360, imagem.getImagem("botaoProximo1"));
		botaoProximo1.setVisivel(false);
		adicionarObjetoRenderizavel(fimDeJogoAranha);
		adicionarObjetoRenderizavel(fimDeJogoFormigueiro);
		adicionarObjetoRenderizavel(fimDeJogoVitoria);
		adicionarObjetoRenderizavel(botaoVoltar0);
		adicionarObjetoRenderizavel(botaoVoltar1);
		adicionarObjetoRenderizavel(botaoProximo0);
		adicionarObjetoRenderizavel(botaoProximo1);
	}

	private void criarMapa() {
		meuMapa = mapa.getMapaDeSprite("mapaBPFase3");
		camadaDeArvores = meuMapa.getCamada(2);
		camadaDeGrama = meuMapa.getCamada(0);
		camadaDeArvores.criarAreaRetangular("arvores", 5, 5, 30, 30);
		camadaDeGrama.criarAreaRetangular("grama", 5, 5, 30, 30);
		adicionarObjetoRenderizavel(meuMapa);
	}

	private void criarJogador() {
		sprite = new SpriteSheet(10, 255, imagem.getImagem("jogador"), 50, 50);
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
		sprite.executarAnimacao("animacaoDireitaNormal");
		orientacaoDaFormiga = Orientacao.DIREITA;
		sprite.redimensionar(40, 40);
		sprite.criarAreaRetangular("jogador", 14, 5, 12, 34);
		adicionarObjetoRenderizavel(sprite);
	}

	private void criarAranhas() {
		aranhas = new SpriteSheet[2];
		for (int i = 0; i < aranhas.length; i++) {
			aranhas[i] = new SpriteSheet((int) pontos[i].getX(), (int) pontos[i].getY(), imagem.getImagem("aranhas"),
					50, 50);
			aranhas[i].adicionarAnimacao("animacaoCimaNormal", new Animacao(0, 0, new Integer[] { 0 }));
			aranhas[i].adicionarAnimacao("animacaoCimaMovimento", new Animacao(0, 0, new Integer[] { 1, 2 }));
			aranhas[i].adicionarAnimacao("animacaoBaixoNormal", new Animacao(0, 50, new Integer[] { 0 }));
			aranhas[i].adicionarAnimacao("animacaoBaixoMovimento", new Animacao(0, 50, new Integer[] { 1, 2 }));
			aranhas[i].executarAnimacao("animacaoBaixoMovimento");
			aranhas[i].criarAreaRetangular("aranha", 15, 9, 20, 32);
		}
		for (SpriteSheet aranha : aranhas)
			adicionarObjetoRenderizavel(aranha);
	}

	private void criarBarrasDeVida() {
		mapaDeBarras = new HashMap<Point, BarraDeEnergia>();
		BarraDeEnergia barra = new BarraDeEnergia(10, 270, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.corDaBarra = Color.yellow;
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(0, 5), barra);

		barra = new BarraDeEnergia(210, 270, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(4, 5), barra);

		barra = new BarraDeEnergia(210, 170, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(4, 3), barra);

		barra = new BarraDeEnergia(210, 370, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(4, 7), barra);

		barra = new BarraDeEnergia(210, 520, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(4, 10), barra);

		barra = new BarraDeEnergia(410, 520, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(8, 10), barra);

		barra = new BarraDeEnergia(410, 420, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(8, 8), barra);

		barra = new BarraDeEnergia(610, 420, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(12, 8), barra);

		barra = new BarraDeEnergia(760, 170, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(true);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(15, 3), barra);

		barra = new BarraDeEnergia(760, 570, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(true);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(15, 11), barra);

		barra = new BarraDeEnergia(460, 170, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(9, 3), barra);

		barra = new BarraDeEnergia(760, 420, new BufferedImage(30, 10, BufferedImage.TYPE_3BYTE_BGR));
		barra.setNivelDaBarra(100);
		barra.setExtremo(false);
		barra.criarAreaRetangular("padrao", 0, 0, 30, 10);
		mapaDeBarras.put(new Point(15, 8), barra);

		for (BarraDeEnergia barraE : mapaDeBarras.values())
			adicionarObjetoRenderizavel(barraE);
		mapaDeAdjacencia = new HashMap<Point, List<Point>>();
		mapaDeAdjacencia.put(new Point(0, 5), new ArrayList<Point>(Arrays.asList(new Point(4, 5))));
		mapaDeAdjacencia.put(new Point(15, 3), new ArrayList<Point>(Arrays.asList(new Point(9, 3))));
		mapaDeAdjacencia.put(new Point(15, 11), new ArrayList<Point>(Arrays.asList(new Point(15, 8))));
		mapaDeAdjacencia.put(new Point(9, 3), new ArrayList<Point>(Arrays.asList(new Point(15, 3), new Point(4, 3))));
		mapaDeAdjacencia.put(new Point(15, 8),
				new ArrayList<Point>(Arrays.asList(new Point(12, 8), new Point(15, 11))));
		mapaDeAdjacencia.put(new Point(4, 3), new ArrayList<Point>(Arrays.asList(new Point(9, 3), new Point(4, 5))));
		mapaDeAdjacencia.put(new Point(12, 8), new ArrayList<Point>(Arrays.asList(new Point(15, 8), new Point(8, 8))));
		mapaDeAdjacencia.put(new Point(4, 5),
				new ArrayList<Point>(Arrays.asList(new Point(0, 5), new Point(4, 3), new Point(4, 7))));
		mapaDeAdjacencia.put(new Point(8, 8), new ArrayList<Point>(Arrays.asList(new Point(12, 8), new Point(8, 10))));
		mapaDeAdjacencia.put(new Point(4, 7), new ArrayList<Point>(Arrays.asList(new Point(4, 5), new Point(4, 10))));
		mapaDeAdjacencia.put(new Point(4, 10), new ArrayList<Point>(Arrays.asList(new Point(4, 7), new Point(8, 10))));
		mapaDeAdjacencia.put(new Point(8, 10), new ArrayList<Point>(Arrays.asList(new Point(8, 8), new Point(4, 10))));

	}

	private void logicaControleDasAranhas() {
		for (int i = 0; i < aranhas.length; i++) {
			aranhas[i].deslocarXY(0, deslocamentoAranha[i]);
			if (Util.houveInterseccao(aranhas[i], "arvores", camadaDeArvores) || Util.foraDaCena(aranhas[i], this)) {
				aranhas[i].deslocarXY(0, -deslocamentoAranha[i]);
				deslocamentoAranha[i] *= -1;
				if (orientacaoAranhas[i] == Orientacao.BAIXO) {
					aranhas[i].executarAnimacao("animacaoCimaMovimento");
					orientacaoAranhas[i] = Orientacao.CIMA;
					aranhas[i].deslocarXY(0, deslocamentoAranha[i]);
				} else {
					aranhas[i].executarAnimacao("animacaoBaixoMovimento");
					orientacaoAranhas[i] = Orientacao.BAIXO;
					aranhas[i].deslocarXY(0, deslocamentoAranha[i]);
				}
			}
		}
	}

	private void logicaControleDoJogador() {
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

	private void logicaParaAlimentacaoDosFormigueiros() {
		for (Point ponto : mapaDeBarras.keySet()) {
			BarraDeEnergia barra = mapaDeBarras.get(ponto);
			if (Util.houveInterseccao("jogador", sprite, "padrao", barra) && barra.isVisivel() && barra.isExtremo()
					&& entrada.isTeclaPressionada(KeyEvent.VK_A)) {
				if (barra.corDaBarra != Color.yellow) {
					List<Point> adjacentes = mapaDeAdjacencia.get(ponto);
					if (adjacentes.size() == 1) {
						mapaDeAdjacencia.get(adjacentes.get(0)).remove(ponto);
						if (mapaDeAdjacencia.get(adjacentes.get(0)).size() == 1) {
							mapaDeBarras.get(adjacentes.get(0)).setExtremo(true);
							mapaDeBarras.get(adjacentes.get(0)).atualizaNivelDaBarra(3);
						} else if (mapaDeAdjacencia.get(adjacentes.get(0)).size() == 0) {
							mapaDeBarras.get(adjacentes.get(0)).setExtremo(true);
							mapaDeBarras.get(adjacentes.get(0)).atualizaNivelDaBarra(3);
						}
						adjacentes.remove(0);
					}
					barra.setVisivel(false);
					score += 100;
					break;
				}
			} else if (Util.houveInterseccao("jogador", sprite, "padrao", barra) && barra.isVisivel()
					&& !barra.isExtremo() && entrada.isTeclaPressionada(KeyEvent.VK_A)

					|| (Util.houveInterseccao("jogador", sprite, "padrao", barra) && barra.isVisivel()
							&& entrada.isTeclaPressionada(KeyEvent.VK_A) && barra.corDaBarra == Color.yellow
							&& score < 1100)) {
				nivelDaBarra += 10;
				if (score - 100 >= 0)
					score -= 100;
				barra.setVisivel(false);
			}
			if (Util.houveInterseccao("jogador", sprite, "padrao", barra) && entrada.isTeclaPressionada(KeyEvent.VK_A)
					&& barra.isVisivel() && barra.corDaBarra == Color.yellow && score == 1100) {
				executarVitoria();
			}
		}
	}

	private void logicaParaAtualizacaoDoTempoDeVidaDosFormigueiros() {
		long tempoAtual = System.currentTimeMillis();
		if (tempoAtual - tempoAnterior > 500) {
			for (BarraDeEnergia barraDeEnergia : mapaDeBarras.values()) {
				if (barraDeEnergia.isExtremo())
					barraDeEnergia.atualizaNivelDaBarra(nivelDaBarra);
				else
					barraDeEnergia.atualizaNivelDaBarra(nivelDaBarraInterna);
			}
			tempoAnterior = tempoAtual;
		}
		for (BarraDeEnergia barraDeEnergia : mapaDeBarras.values()) {
			if (barraDeEnergia.getNivelDaBarra() <= 0 && barraDeEnergia.isVisivel()) {
				pausa = true;
				fimDeJogoFormigueiro.setVisivel(true);
				botaoVoltar0.setVisivel(true);
			}
		}
	}

	private void logicaParaDeixarJogadorLento() {
		if (Util.houveInterseccao(sprite, "grama", camadaDeGrama, "2")) {
			deslocamento = 3;
		} else {
			deslocamento = 10;
		}
	}

	private void logicaParaMatarFormiga() {
		for (SpriteSheet aranha : aranhas) {
			if (Util.houveInterseccao("jogador", sprite, "aranha", aranha)) {
				fimDeJogoAranha.setVisivel(true);
				botaoVoltar0.setVisivel(true);
				pausa = true;
			}
		}
	}

	private void logicaBotaoDeVoltar() {
		if (pausa) {
			if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoVoltar0)) {
				botaoVoltar0.setVisivel(false);
				botaoVoltar1.setVisivel(true);
				if (entrada.isClique()) {
					executarCena("mapaAlimentacaoProfundidade");
				}
			} else {
				botaoVoltar0.setVisivel(true);
				botaoVoltar1.setVisivel(false);
			}
		}
	}

	private void logicaBotaoDeProximo() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoProximo0)) {
			botaoProximo0.setVisivel(false);
			botaoProximo1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("mapaAlimentacaoProfundidade");
			}
		} else {
			botaoProximo0.setVisivel(true);
			botaoProximo1.setVisivel(false);
		}
	}

	private void executarVitoria() {
		fimDeJogoVitoria.setVisivel(true);
		botaoProximo0.setVisivel(true);
		pausa = true;
		vitoria = true;
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> estado = (ArrayList<Boolean>) getAtributoCompartilhavel(
				"estadoDasFasesDoDesafioDeAlimentacao");
		estado.set(3, true);
	}

}