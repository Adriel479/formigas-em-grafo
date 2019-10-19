package br.com.formigasemgrafo.cenas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.formigasemgrafo.core.Sprite;

public class BarraDeEnergia extends Sprite {

	private int nivelDaBarra;
	private boolean extremo;
	public Color corDaBarra = new Color(128, 190, 31);
	

	public BarraDeEnergia(int x, int y, BufferedImage imagem) {
		super(x, y, imagem);
	}

	@Override
	public void renderizeme(Graphics2D g) {
		ajustarBarraDeEnergia();
		super.renderizeme(g);
	}

	private void ajustarBarraDeEnergia() {
		Graphics2D g = (Graphics2D) imagem.getGraphics();
		g.setColor(new Color(93, 58, 27));
		g.fillRect(0, 0, imagem.getWidth(), imagem.getHeight());
		g.setColor(corDaBarra);
		g.fillRect(0, 0, (int) (imagem.getWidth() * nivelDaBarra / 100.0f), imagem.getHeight());
		g.setColor(Color.RED);
		g.drawRect(0, 0, imagem.getWidth() - 1, imagem.getHeight() - 1);
		g.setColor(Color.GREEN);
		g.dispose();
	}

	public void atualizaNivelDaBarra(int reducao) {
		this.nivelDaBarra -= reducao;
	}
	
	public int getNivelDaBarra() {
		return nivelDaBarra;
	}

	public void setNivelDaBarra(int nivelDaBarra) {
		this.nivelDaBarra = nivelDaBarra;
	}

	public boolean isExtremo() {
		return extremo;
	}

	public void setExtremo(boolean extremo) {
		this.extremo = extremo;
	}

}
