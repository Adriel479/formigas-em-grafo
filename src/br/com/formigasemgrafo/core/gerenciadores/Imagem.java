package br.com.formigasemgrafo.core.gerenciadores;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
			InputStream entrada = getClass().getResourceAsStream(caminho);
			if (entrada == null) {
				throw new RuntimeException(String.format("A imagem %s não foi encontrada.", caminho));
			} else {
				BufferedImage imagem;
				try {
					imagem = ImageIO.read(entrada);
					imagens.put(nome, imagem);
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
