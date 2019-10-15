package br.com.formigasemgrafo.core;

import java.awt.Shape;

import br.com.formigasemgrafo.core.gerenciadores.Imagem;

public class Util {

	public static boolean mouseEntrouNaAreaDoSpite(int x, int y, Sprite sprite) {
		if (sprite != null) {
			return x >= sprite.getX() && x <= sprite.getX() + sprite.getComprimento() && y >= sprite.getY()
					&& y <= sprite.getY() + sprite.getLargura();
		} else {
			return false;
		}
	}
	
	public static boolean mouseEntrouNaAreaDaForma(int x, int y, Shape forma) {
		if (forma != null) {
			return forma.contains(x, y);
		} else {
			return false;
		}
	}

	public static boolean houveInterseccao(String nomeAreaSprite1, Sprite sprite1, String nomeAreaSprite2,
			Sprite sprite2) {
		return sprite1.getArea(nomeAreaSprite1).intersects(sprite2.getArea(nomeAreaSprite2));
	}

	public static boolean houveInterseccao(Sprite sprite, String nomeDaArea, Camada camada) {
		for (int i = 0; i < camada.elementosDaCamada.length; i++) {
			for (int j = 0; j < camada.elementosDaCamada[0].length; j++) {
				if (camada.elementosDaCamada[i][j] != null) {
					if (camada.elementosDaCamada[i][j].getArea(nomeDaArea).intersects(sprite.getX(), sprite.getY(),
							sprite.getComprimento(), sprite.getLargura()))
						return true;
				}
			}
		}
		return false;
	}

	public static boolean houveInterseccao(Sprite sprite, String nomeDaArea, Camada camada, String nomeImagem) {
		for (int i = 0; i < camada.elementosDaCamada.length; i++) {
			for (int j = 0; j < camada.elementosDaCamada[0].length; j++) {
				if (camada.elementosDaCamada[i][j] != null && camada.elementosDaCamada[i][j].imagem == Imagem.getInstancia().getImagem(nomeImagem)) {
					if (camada.elementosDaCamada[i][j].getArea(nomeDaArea).intersects(sprite.getX(), sprite.getY(),
							sprite.getComprimento(), sprite.getLargura()))
						return true;
				}
			}
		}
		return false;
	}
	
	public static boolean houveInterseccao(String nomeDaArea0, Sprite sprite, String nomeDaArea1, Camada camada) {
		for (int i = 0; i < camada.elementosDaCamada.length; i++) {
			for (int j = 0; j < camada.elementosDaCamada[0].length; j++) {
				if (camada.elementosDaCamada[i][j] != null) {
					if (camada.elementosDaCamada[i][j].getArea(nomeDaArea1).intersects(sprite.getArea(nomeDaArea0)))
						return true;
				}
			}
		}
		return false;
	}

	public static boolean foraDaCena(Sprite sprite, Cena cena) {
		return sprite.getX() < 0 || sprite.getX() + sprite.getComprimento() >= cena.getComprimento()
				|| sprite.getY() < 0 || sprite.getY() + sprite.getLargura() >= cena.getLargura();
	}

}
