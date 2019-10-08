package br.com.formigasemgrafo.core;

public class Util {

	public static boolean mouseEntrouNaAreaDoSpite(int x, int y, Sprite sprite) {
		if (sprite != null) {
			return x >= sprite.getX() && x <= sprite.getX() + sprite.getComprimento() && y >= sprite.getY()
					&& y <= sprite.getY() + sprite.getLargura();
		} else {
			return false;
		}
	}

}
