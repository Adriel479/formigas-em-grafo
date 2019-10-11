package br.com.formigasemgrafo.cenas;

import java.awt.event.KeyEvent;

import br.com.formigasemgrafo.core.Animacao;
import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.SpriteSheet;

public class BuscaEmProfundidadeFase1 extends Cena {

	private SpriteSheet sprite;
	private int velocidade = 10;

	@Override
	public void carregar() {
		mapa.carregarMapaDeSprite("mapaBPFase1", "/mapas/buscaEmProfundidadeFase1.json", "/mapas/recursos.tsx");
		imagem.carregarImagem("jogador", "/assets/spriteSheet.png");
	}

	@Override
	public void atualizar() {
		if (entrada.isTeclaPressionada(KeyEvent.VK_UP)) {
			sprite.setY(sprite.getY() - velocidade);
			sprite.executarAnimacao("movimento");
		} else if (entrada.isTeclaPressionada(KeyEvent.VK_DOWN)) {
			sprite.setY(sprite.getY() + velocidade);
			sprite.executarAnimacao("movimento");
		} else  if (entrada.isTeclaPressionada(KeyEvent.VK_SPACE)) {
			sprite.executarAnimacao("ataque");
		}else {
			sprite.executarAnimacao("normal");
		}
		
	
	}

	@Override
	public void criar() {
		sprite = new SpriteSheet(350, 500, imagem.getImagem("jogador"), 50, 50);
		sprite.adicionarAnimacao("movimento", new Animacao(0, 0, new Integer[] { 2, 3 }));
		sprite.adicionarAnimacao("ataque", new Animacao(0, 0, new Integer[] { 1 }));
		sprite.adicionarAnimacao("normal", new Animacao(0, 0, new Integer[] { 0 }));
		sprite.executarAnimacao("normal");
		adicionarObjetoRenderizavel(mapa.getMapaDeSprite("mapaBPFase1"));
		adicionarObjetoRenderizavel(sprite);
	}

	@Override
	public void descarregar() {

	}

}
