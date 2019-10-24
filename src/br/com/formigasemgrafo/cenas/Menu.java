package br.com.formigasemgrafo.cenas;

import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.Util;

public class Menu extends Cena {

	private Sprite fundoMenu;
	private Sprite botaoCredito0;
	private Sprite botaoCredito1;
	private Sprite botaoIniciar0;
	private Sprite botaoIniciar1;

	@Override
	public void onCarregar() {
		imagem.carregarImagem("fundoMenu", "/assets/menu.png");
		imagem.carregarImagem("botaoCredito0", "/assets/botaoCredito0.png");
		imagem.carregarImagem("botaoCredito1", "/assets/botaoCredito1.png");
		imagem.carregarImagem("botaoIniciar0", "/assets/botaoIniciar0.png");
		imagem.carregarImagem("botaoIniciar1", "/assets/botaoIniciar1.png");
		audio.carregarAudio("musicaDeFundo", "/assets/090719bgmidea2.wav");
	}

	@Override
	public void onAtualizar() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoIniciar0)) {
			botaoIniciar0.setVisivel(false);
			botaoIniciar1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("definicaoDeGrafo");
			}
		} else {
			botaoIniciar0.setVisivel(true);
			botaoIniciar1.setVisivel(false);
		}

		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoCredito0)) {
			botaoCredito0.setVisivel(false);
			botaoCredito1.setVisivel(true);
		} else {
			botaoCredito0.setVisivel(true);
			botaoCredito1.setVisivel(false);
		}
	}

	@Override
	public void onCriar() {
		fundoMenu = new Sprite(0, 0, imagem.getImagem("fundoMenu"));
		botaoIniciar0 = new Sprite(273, 437, imagem.getImagem("botaoIniciar0"));
		botaoIniciar1 = new Sprite(273, 437, imagem.getImagem("botaoIniciar1"));
		botaoCredito0 = new Sprite(273, 507, imagem.getImagem("botaoCredito0"));
		botaoCredito1 = new Sprite(273, 507, imagem.getImagem("botaoCredito1"));

		botaoIniciar1.setVisivel(false);
		botaoCredito1.setVisivel(false);
		
		audio.getAudio("musicaDeFundo").loop();
		audio.getAudio("musicaDeFundo").play();

		adicionarObjetoRenderizavel(fundoMenu);
		adicionarObjetoRenderizavel(botaoIniciar0);
		adicionarObjetoRenderizavel(botaoIniciar1);
		adicionarObjetoRenderizavel(botaoCredito0);
		adicionarObjetoRenderizavel(botaoCredito1);
	}

	@Override
	public void onDescarregar() {

	}

}
