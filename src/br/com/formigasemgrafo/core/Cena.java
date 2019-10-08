package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import br.com.formigasemgrafo.core.gerenciadores.Audio;
import br.com.formigasemgrafo.core.gerenciadores.Fonte;
import br.com.formigasemgrafo.core.gerenciadores.Imagem;
import br.com.formigasemgrafo.core.gerenciadores.Entrada;

public abstract class Cena {

	protected Entrada entrada;
	protected Imagem imagem;
	protected Audio audio;
	protected Fonte fonte;
	private String proximaCena = null;
	private List<Sprite> sprites;
	private Jogo jogo;

	public Cena() {
		sprites = new ArrayList<Sprite>();
	}

	public abstract void carregar();

	public abstract void atualizar();
	
	public abstract void criar();

	public void renderizar(Graphics2D g) {
		for (Sprite sprite: sprites) {
			if (sprite.isVisivel())
				sprite.renderizeme(g);
		}
	}

	public abstract void descarregar();

	void configurarGerentes(Jogo jogo) {
		entrada = jogo.entrada;
		imagem = jogo.imagem;
		audio = jogo.audio;
		fonte = jogo.fonte;
		this.jogo = jogo;
	}

	public String getProximaCena() {
		return proximaCena;
	}

	public void executarCena(String proximaCena) {
		jogo.setNovaCena(true);
		jogo.setCenaAtual(proximaCena);
	}
	
	public void adicionarSprite(Sprite sprite) {
		sprites.add(sprite);
	}

}
