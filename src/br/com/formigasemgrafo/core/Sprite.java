package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite implements Renderizavel{

	protected int x;
	protected int y;
	protected BufferedImage imagem;
	protected boolean visivel;

	public Sprite(int x, int y, BufferedImage imagem) {
		this.x = x;
		this.y = y;
		this.setImagem(imagem);
		this.visivel = true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void renderizeme(Graphics2D g) {
		if (isVisivel())
			g.drawImage(imagem, x, y, null);
	}

	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public int getComprimento() {
		return this.imagem.getWidth();
	}

	public int getLargura() {
		return this.imagem.getHeight();
	}

}
