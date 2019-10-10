package br.com.formigasemgrafo.core;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.formigasemgrafo.core.gerenciadores.Imagem;

public class Camada implements Renderizavel {

	private Sprite[][] elementosDaCamada;
	private int comprimentoSprite;
	private int alturaSprite;
	private float opacidade;
	private boolean visivel;

	public Camada(int numeroDeLinhas, int numeroDeColunas) {
		this(numeroDeLinhas, numeroDeColunas, 0, 0, 0, true);
	}

	public Camada(int numeroDeLinhas, int numeroDeColunas, int comprimento, int altura, float opcacidade,
			boolean visivel) {
		elementosDaCamada = new Sprite[numeroDeLinhas][numeroDeColunas];
		this.setComprimentoSprite(comprimento);
		this.setAlturaSprite(altura);
		setOpacidade(opcacidade);
		setVisivel(visivel);
	}

	public void configurarElementosDaCamada(Object[] nomeDosElementos) {
		int k = 0;
		int x = 0;
		int y = 0;
		Imagem imagem = Imagem.getInstancia();
		BufferedImage img = null;
		for (int i = 0; i < elementosDaCamada.length; i++) {
			for (int j = 0; j < elementosDaCamada[i].length; j++) {
				if ((img = imagem.getImagem(nomeDosElementos[k++].toString())) != null)
					elementosDaCamada[i][j] = new Sprite(x, y, img);
				x += this.getComprimentoSprite();
			}
			x = 0;
			y += this.getAlturaSprite();
		}
	}

	@Override
	public void renderizeme(Graphics2D g) {
		if (isVisivel()) {
			Composite cacheComposite = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getOpacidade()));
			for (int i = 0; i < elementosDaCamada.length; i++) {
				for (int j = 0; j < elementosDaCamada[i].length; j++) {
					if (elementosDaCamada[i][j] != null)
						elementosDaCamada[i][j].renderizeme(g);
				}
			}
			g.setComposite(cacheComposite);
		}
	}

	public float getOpacidade() {
		return opacidade;
	}

	public void setOpacidade(float opacidade) {
		this.opacidade = opacidade;
	}

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public int getComprimentoSprite() {
		return comprimentoSprite;
	}

	public void setComprimentoSprite(int comprimentoSprite) {
		this.comprimentoSprite = comprimentoSprite;
	}

	public int getAlturaSprite() {
		return alturaSprite;
	}

	public void setAlturaSprite(int alturaSprite) {
		this.alturaSprite = alturaSprite;
	}

}
