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
import br.com.formigasemgrafo.core.gerenciadores.Mapa;

public abstract class Cena {

	protected Entrada entrada;
	protected Imagem imagem;
	protected Audio audio;
	protected Fonte fonte;
	protected Mapa mapa;
	private String proximaCena = null;
	private List<Renderizavel> objetosRenderizaveis;
	private Jogo jogo;
	private Map<String, Object> atributosCompatilhados;

	public Cena() {
		objetosRenderizaveis = new ArrayList<Renderizavel>();
		atributosCompatilhados = new HashMap<String, Object>();
		imagem = Imagem.getInstancia();
		audio = Audio.getInstancia();
		fonte = Fonte.getInstancia();
		mapa = Mapa.getInstancia();
	}

	public abstract void carregar();

	public abstract void atualizar();

	public abstract void criar();

	public void renderizar(Graphics2D g) {
		for (Renderizavel objetoRenderizavel : objetosRenderizaveis) {
			objetoRenderizavel.renderizeme(g);
		}
	}

	public abstract void descarregar();

	void configurarGerentes(Jogo jogo) {
		entrada = jogo.entrada;
		this.jogo = jogo;
	}

	public String getProximaCena() {
		return proximaCena;
	}

	public void executarCena(String proximaCena) {
		jogo.setNovaCena(true);
		jogo.setCenaAtual(proximaCena);
	}

	public void adicionarObjetoRenderizavel(Renderizavel objetoRenderizavel) {
		objetosRenderizaveis.add(objetoRenderizavel);
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

	public int getComprimento() {
		return jogo.getComprimento();
	}

	public int getLargura() {
		return jogo.getLargura();
	}

}
