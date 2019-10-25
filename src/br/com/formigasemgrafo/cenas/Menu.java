package br.com.formigasemgrafo.cenas;

import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.Util;

public class Menu extends Cena {

	private Sprite fundoMenu;
	private Sprite botaoSair0;
	private Sprite botaoSair1;
	private Sprite botaoIniciar0;
	private Sprite botaoIniciar1;
	private boolean iniciou = true;

	@Override
	public void onCarregar() {
		imagem.carregarImagem("fundoMenu", "/assets/menu.png");
		imagem.carregarImagem("botaoSair0", "/assets/botaoSair0.png");
		imagem.carregarImagem("botaoSair1", "/assets/botaoSair1.png");
		imagem.carregarImagem("botaoIniciar0", "/assets/botaoIniciar0.png");
		imagem.carregarImagem("botaoIniciar1", "/assets/botaoIniciar1.png");
		audio.carregarAudio("musicaDeFundo", "assets/bgmidea2.wav");
		audio.carregarAudio("audioBotao", "assets/audioBotao.wav");
	}

	@Override
	public void onAtualizar() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoIniciar0)) {
			botaoIniciar0.setVisivel(false);
			botaoIniciar1.setVisivel(true);
			if (entrada.isClique()) {
				audio.getAudio("audioBotao").play();
				executarCena("definicaoDeGrafo");
			}
		} else {
			botaoIniciar0.setVisivel(true);
			botaoIniciar1.setVisivel(false);
		}

		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoSair0)) {
			botaoSair0.setVisivel(false);
			botaoSair1.setVisivel(true);
			if (entrada.isClique()) {
				audio.getAudio("audioBotao").play();
				System.exit(0);
			}
		} else {
			botaoSair0.setVisivel(true);
			botaoSair1.setVisivel(false);
		}
	}

	@Override
	public void onCriar() {
		fundoMenu = new Sprite(0, 0, imagem.getImagem("fundoMenu"));
		botaoIniciar0 = new Sprite(273, 437, imagem.getImagem("botaoIniciar0"));
		botaoIniciar1 = new Sprite(273, 437, imagem.getImagem("botaoIniciar1"));
		botaoSair0 = new Sprite(273, 507, imagem.getImagem("botaoSair0"));
		botaoSair1 = new Sprite(273, 507, imagem.getImagem("botaoSair1"));

		botaoIniciar1.setVisivel(false);
		botaoSair1.setVisivel(false);

		if (iniciou) {
			audio.getAudio("musicaDeFundo").loop();
			iniciou = false;
		}

		adicionarObjetoRenderizavel(fundoMenu);
		adicionarObjetoRenderizavel(botaoIniciar0);
		adicionarObjetoRenderizavel(botaoIniciar1);
		adicionarObjetoRenderizavel(botaoSair0);
		adicionarObjetoRenderizavel(botaoSair1);
	}

	@Override
	public void onDescarregar() {

	}

}
