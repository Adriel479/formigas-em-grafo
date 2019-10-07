package br.com.formigasemgrafo.core.gerenciadores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

	public void carregarImagem(String nome, String caminho) throws IOException {
		if (!imagens.containsKey(nome)) {
			URL url = getClass().getResource(caminho);
			if (url == null) {
				throw new RuntimeException(String.format("A imagem %s não foi encontrada.", caminho));
			} else {
				BufferedImage imagem = ImageIO.read(new File(caminho));
				imagens.put(nome, imagem);
			}
		}
	}

	public BufferedImage getImagem(String nome) {
		if (imagens.containsKey(nome)) {
			return imagens.get(nome);
		} else {
			throw new RuntimeException(String.format("A imagem \"%s\" não se encontra no cache de imagens do sistema. "
					+ "Tente carrega-lá antes de acessa-lá. ", nome));
		}
	}

}
