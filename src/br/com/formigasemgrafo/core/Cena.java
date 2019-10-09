package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.formigasemgrafo.core.gerenciadores.Audio;
import br.com.formigasemgrafo.core.gerenciadores.Entrada;
import br.com.formigasemgrafo.core.gerenciadores.Fonte;
import br.com.formigasemgrafo.core.gerenciadores.Imagem;

public abstract class Cena {

	protected Entrada entrada;
	protected Imagem imagem;
	protected Audio audio;
	protected Fonte fonte;
	private String proximaCena = null;
	private List<Sprite> sprites;
	private Jogo jogo;
	private Map<String, Object> atributosCompatilhados;

	public Cena() {
		sprites = new ArrayList<Sprite>();
		atributosCompatilhados = new HashMap<String, Object>();
	}

	public abstract void carregar();

	public abstract void atualizar();

	public abstract void criar();

	public void renderizar(Graphics2D g) {
		for (Sprite sprite : sprites) {
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

	public void adicionarAtributoCompartilhavel(String nomeDoAtributo, Object atributo) {
		atributosCompatilhados.put(nomeDoAtributo, atributo);
	}

	public Object getAtributoCompartilhavel(String nomeDoAtributo) {
		return atributosCompatilhados.get(nomeDoAtributo);
	}

	public void removeAtributoCompartilhavel(String nomeDoAtributo) {
		atributosCompatilhados.remove(nomeDoAtributo);
	}

}
