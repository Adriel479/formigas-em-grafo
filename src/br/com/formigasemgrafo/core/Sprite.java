package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {

	private int x;
	private int y;
	private BufferedImage imagem;

	public Sprite(int x, int y, BufferedImage imagem) {
		this.x = x;
		this.y = y;
		this.setImagem(imagem);
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
		g.drawImage(imagem, x, y, null);
	}

	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}

}
