package br.com.formigasemgrafo.core;

public class Animacao {

	private int x;
	private int y;
	private Integer[] quadros;

	public Animacao(int x, int y, Integer[] quadros) {
		this.setX(x);
		this.setY(y);
		this.setQuadros(quadros);
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

	public Integer[] getQuadros() {
		return quadros;
	}

	public void setQuadros(Integer[] quadros) {
		this.quadros = quadros;
	}

}
