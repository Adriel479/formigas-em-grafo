package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;

public abstract class Cena {

	protected Teclado teclado;
	protected Imagem imagem;
	protected Audio audio;
	protected Fonte fonte;
	private String proximaCena = null;
	
	public abstract void carregarDados();

	public abstract void atualizarJogo();

	public abstract void renderizarJogo(Graphics2D g);

	public abstract void descarregarDados();

	void configurarGerentes(Jogo jogo) {
		teclado = jogo.teclado;
		imagem = jogo.imagem;
		audio = jogo.audio;
		fonte = jogo.fonte;
	}

	public String getProximaCena() {
		return proximaCena;
	}

	public void executarProximaCena(String proximaCena) {
		this.proximaCena = proximaCena;
	}

}
