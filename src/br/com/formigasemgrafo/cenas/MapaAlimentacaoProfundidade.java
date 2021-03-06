package br.com.formigasemgrafo.cenas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import br.com.formigasemgrafo.core.Cena;
import br.com.formigasemgrafo.core.Sprite;
import br.com.formigasemgrafo.core.Util;

public class MapaAlimentacaoProfundidade extends Cena {

	private Sprite fundoMapaAlimentacao;
	private Ellipse2D.Float[] fases;
	private ArrayList<Boolean> estadoDasFasesDoDesafioDeAlimentacao;
	private Sprite botaoDesafio0;
	private Sprite botaoDesafio1;

	@Override
	public void onCarregar() {
		imagem.carregarImagem("fundoMapaAlimentacaoEmProfundidade", "/assets/fasesAlimentacaoEmProfundidade.png");
		imagem.carregarImagem("botaoDesafio0", "/assets/botaoDesafio0.png");
		imagem.carregarImagem("botaoDesafio1", "/assets/botaoDesafio1.png");
	}

	@Override
	public void onAtualizar() {
		if (Util.mouseEntrouNaAreaDoSpite(entrada.getX(), entrada.getY(), botaoDesafio0)) {
			botaoDesafio0.setVisivel(false);
			botaoDesafio1.setVisivel(true);
			if (entrada.isClique()) {
				audio.getAudio("audioBotao").play();
				executarCena("desafios");
			}
		} else {
			botaoDesafio0.setVisivel(true);
			botaoDesafio1.setVisivel(false);
		}

		for (int i = 0; i < estadoDasFasesDoDesafioDeAlimentacao.size(); i++) {
			if (Util.mouseEntrouNaAreaDaForma(entrada.getX(), entrada.getY(), fases[i]) && entrada.isClique()
					&& estadoDasFasesDoDesafioDeAlimentacao.get(i)) {
				executarCena("fase" + (i + 1) + "BuscaEmProfundidade");
			}
		}

	}

	@Override
	public void onCriar() {
		fundoMapaAlimentacao = new Sprite(0, 0, imagem.getImagem("fundoMapaAlimentacaoEmProfundidade")) {
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
				for (int i = 0; i < estadoDasFasesDoDesafioDeAlimentacao.size(); i++) {
					if (!estadoDasFasesDoDesafioDeAlimentacao.get(i)) {
						g.fill(fases[i]);
					}
				}
				g.setComposite(cacheComposite);
			}
		};
		adicionarObjetoRenderizavel(fundoMapaAlimentacao);
		fases = new Ellipse2D.Float[6];

		if (estadoDasFasesDoDesafioDeAlimentacao == null) {
			estadoDasFasesDoDesafioDeAlimentacao = new ArrayList<Boolean>(6);
			estadoDasFasesDoDesafioDeAlimentacao.add(true);
			for (int i = 1; i < 6; i++)
				estadoDasFasesDoDesafioDeAlimentacao.add(false);
			adicionarAtributoCompartilhavel("estadoDasFasesDoDesafioDeAlimentacaoProfundidade",
					estadoDasFasesDoDesafioDeAlimentacao);
		}

		fases[0] = new Ellipse2D.Float(160, 200, 80, 80);
		fases[1] = new Ellipse2D.Float(360, 200, 80, 80);
		fases[2] = new Ellipse2D.Float(580, 200, 80, 80);
		fases[3] = new Ellipse2D.Float(160, 340, 80, 80);
		fases[4] = new Ellipse2D.Float(360, 340, 80, 80);
		fases[5] = new Ellipse2D.Float(580, 340, 80, 80);

		botaoDesafio0 = new Sprite(278, 464, imagem.getImagem("botaoDesafio0"));
		botaoDesafio1 = new Sprite(278, 464, imagem.getImagem("botaoDesafio1"));

		botaoDesafio1.setVisivel(false);

		adicionarObjetoRenderizavel(botaoDesafio0);
		adicionarObjetoRenderizavel(botaoDesafio1);
	}

	@Override
	public void onDescarregar() {

	}

}
