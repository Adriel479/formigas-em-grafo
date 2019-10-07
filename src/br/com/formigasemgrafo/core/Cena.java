package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import br.com.formigasemgrafo.core.gerenciadores.Audio;
import br.com.formigasemgrafo.core.gerenciadores.Fonte;
import br.com.formigasemgrafo.core.gerenciadores.Imagem;
import br.com.formigasemgrafo.core.gerenciadores.Entrada;

public abstract class Cena {

	protected Entrada teclado;
	protected Imagem imagem;
	protected Audio audio;
	protected Fonte fonte;
	private String proximaCena = null;
	private List<Sprite> sprites;

	public Cena() {
		sprites = new ArrayList<Sprite>();
	}

	public abstract void carregar();

	public abstract void atualizar();
	
	public abstract void criar();

	public void renderizar(Graphics2D g) {
		for (Sprite sprite: sprites) {
			sprite.renderizeme(g);
		}
	}

	public abstract void descarregar();

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
