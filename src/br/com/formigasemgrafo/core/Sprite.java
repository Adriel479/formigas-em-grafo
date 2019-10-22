package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Sprite implements Renderizavel {

	public int x;
	public int y;
	protected int comprimento;
	protected int largura;
	protected BufferedImage imagem;
	protected boolean visivel;
	Map<String, Rectangle2D.Float> areasDeColisao;

	public Sprite(int x, int y, BufferedImage imagem) {
		this.x = x;
		this.y = y;
		this.setImagem(imagem);
		this.visivel = true;
		areasDeColisao = new HashMap<String, Rectangle2D.Float>();

	}

	public void redimensionar(int comprimento, int largura) {
		this.comprimento = comprimento;
		this.largura = largura;
	}

	public void criarAreaRetangular(String nome, int x, int y, int comprimento, int largura) {
		areasDeColisao.put(nome, new Rectangle2D.Float(this.x + x, this.y + y, comprimento, largura));
	}

	public Rectangle2D.Float getArea(String nome) {
		return areasDeColisao.get(nome);
	}

	public void deslocarXY(int deslocamentoX, int deslocamentoY) {
		this.x += deslocamentoX;
		this.y += deslocamentoY;
		for (Rectangle2D.Float areaDeColisao : areasDeColisao.values()) {
			areaDeColisao.x += deslocamentoX;
			areaDeColisao.y += deslocamentoY;
		}
	}

	public void renderizeme(Graphics2D g) {
		if (isVisivel())
			g.drawImage(imagem, x, y, null);
	}

	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
		comprimento = imagem.getWidth();
		largura = imagem.getHeight();
	}

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public int getComprimento() {
		return this.comprimento;
	}

	public int getLargura() {
		return this.largura;
	}

}
