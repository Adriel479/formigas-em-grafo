package br.com.formigasemgrafo.core.gerenciadores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Imagem {

	private Map<String, BufferedImage> imagens;
	private static final Imagem imagem = new Imagem();

	private Imagem() {
		imagens = new HashMap<String, BufferedImage>();
	}

	public static Imagem getInstancia() {
		return imagem;
	}

	public void carregarImagem(String nome, String caminho) {
		if (!imagens.containsKey(nome)) {
			URL url = getClass().getResource(caminho);
			if (url == null) {
				throw new RuntimeException(String.format("A imagem %s não foi encontrada.", caminho));
			} else {
				BufferedImage imagem;
				try {
					imagem = ImageIO.read(new File(url.toURI()));
					imagens.put(nome, imagem);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}
	}

	public BufferedImage getImagem(String nome) {
		return imagens.get(nome);
	}

}
