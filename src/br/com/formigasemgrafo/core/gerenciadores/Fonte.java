package br.com.formigasemgrafo.core.gerenciadores;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Fonte {

	private static final Fonte fonte = new Fonte();
	private Map<String, Font> fontes;

	private Fonte() {
		fontes = new HashMap<String, Font>();
	}

	public static Fonte getInstancia() {
		return fonte;
	}

	public void carregarFonte(String nome, String caminho, int tamanho, int estilo) {
		if (!fontes.containsKey(nome)) {
			URL url = getClass().getResource(caminho);
			if (url == null) {
				throw new RuntimeException(String.format("A fonte %s não foi encontrada.", caminho));
			} else {
				try {
					Font fonte = Font.createFont(Font.TRUETYPE_FONT, new File(url.toURI()));
					fonte = fonte.deriveFont((float) tamanho);
					switch (estilo) {
					case Font.BOLD:
						fonte = fonte.deriveFont(Font.BOLD);
						break;
					case Font.ITALIC:
						fonte = fonte.deriveFont(Font.ITALIC);
					default:
						fonte = fonte.deriveFont(Font.PLAIN);
					}
					fontes.put(nome, fonte);
				} catch (FontFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Font getFonte(String nome) {
		return fontes.get(nome);
	}

}
