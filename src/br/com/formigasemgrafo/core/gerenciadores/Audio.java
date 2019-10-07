package br.com.formigasemgrafo.core.gerenciadores;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

@SuppressWarnings("all")
public class Audio {

	private static final Audio audio = new Audio();
	private Map<String, AudioClip> audios;

	private Audio() {
		audios = new HashMap<String, AudioClip>();
	}

	public static Audio getInstancia() {
		return audio;
	}

	public void carregarAudio(String nome, String caminho) {
		if (!audios.containsKey(nome)) {
			URL url = getClass().getResource(caminho);
			if (url == null) {
				throw new RuntimeException(String.format("O audio %s não foi encontradao.", caminho));
			} else {
				audios.put(nome, Applet.newAudioClip(url));
			}
		}
	}

	public AudioClip getFonte(String nome) {
		if (audios.containsKey(nome)) {
			return audios.get(nome);
		} else {
			throw new RuntimeException(String.format("O audio \"%s\" não se encontra no cache de audios do sistema. "
					+ "Tente carrega-ló antes de acessa-ló. ", nome));
		}
	}

}
