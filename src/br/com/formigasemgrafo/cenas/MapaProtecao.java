package br.com.formigasemgrafo.cenas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.Util;

public class MapaProtecao extends Cena {

	private Sprite fundoMapaProtecao;
	private Ellipse2D.Float[] fases;
	private boolean[] estadoDasFasesDoDesafioDeProtecao;
	private Sprite botaoVoltar0;
	private Sprite botaoVoltar1;

	@Override
	public void carregar() {
		imagem.carregarImagem("fundoMapaProtecao", "/assets/fasesProtecao.png");
		imagem.carregarImagem("botaoVoltar0", "/assets/botaoVoltar0.png");
		imagem.carregarImagem("botaoVoltar1", "/assets/botaoVoltar1.png");
	}

	@Override
	public void atualizar() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoVoltar0)) {
			botaoVoltar0.setVisivel(false);
			botaoVoltar1.setVisivel(true);
			if (entrada.isClique()) {
				executarCena("desafios");
			}
		} else {
			botaoVoltar0.setVisivel(true);
			botaoVoltar1.setVisivel(false);
		}
	}

	@Override
	public void criar() {
		fundoMapaProtecao = new Sprite(0, 0, imagem.getImagem("fundoMapaProtecao")) {
			/*
			 * Modificações no objeto de renderização devem ser removidas após o uso para
			 * evitar que as modificações se propagem para os outros sprites.
			 * 
			 * ATENÇÃO: Sempre chamar o método da super classe para que a rendização padrão
			 * execute, pois ela é necessária.
			 * 
			 */
			@Override
			public void renderizeme(Graphics2D g) {
				super.renderizeme(g);
				Composite cacheComposite = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
				g.setColor(new Color(231, 70, 40));
				for (int i = 0; i < estadoDasFasesDoDesafioDeProtecao.length; i++) {
					if (!estadoDasFasesDoDesafioDeProtecao[i]) {
						g.fill(fases[i]);
					}
				}
				g.setComposite(cacheComposite);
			}
		};
		adicionarObjetoRenderizavel(fundoMapaProtecao);
		fases = new Ellipse2D.Float[5];
		estadoDasFasesDoDesafioDeProtecao = new boolean[5];

		fases[0] = new Ellipse2D.Float(360, 200, 80, 80);
		fases[1] = new Ellipse2D.Float(580, 200, 80, 80);
		fases[2] = new Ellipse2D.Float(160, 340, 80, 80);
		fases[3] = new Ellipse2D.Float(360, 340, 80, 80);
		fases[4] = new Ellipse2D.Float(580, 340, 80, 80);
		adicionarAtributoCompartilhavel("estadoDasFasesDoDesafioDeProtecao", estadoDasFasesDoDesafioDeProtecao);

		botaoVoltar0 = new Sprite(278, 464, imagem.getImagem("botaoVoltar0"));
		botaoVoltar1 = new Sprite(278, 464, imagem.getImagem("botaoVoltar1"));

		botaoVoltar1.setVisivel(false);

		adicionarObjetoRenderizavel(botaoVoltar0);
		adicionarObjetoRenderizavel(botaoVoltar1);
	}

	@Override
	public void descarregar() {

	}

}
