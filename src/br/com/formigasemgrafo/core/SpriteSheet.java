package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheet extends Sprite {

	private int comprimentoElemento;
	private int larguraElemento;
	private Map<String, Animacao> animacoes;
	private Animacao animacao;
	private int proximoQuadro;

	/* O comprimento e a largura se referem as dimensões de um único quadro */

	public SpriteSheet(int x, int y, BufferedImage imagem, int comprimento, int largura) {
		super(x, y, imagem);
		comprimentoElemento = comprimento;
		larguraElemento = largura;
		this.setLargura(largura);
		this.animacoes = new HashMap<String, Animacao>();
	}

	/*
	 * As animações do sprite podem ser organizadas na imagem em linhas e colunas. A
	 * animação deve sempre está em apenas uma única linha. (Várias linhas, várias
	 * animações). Para avançar um quadro é necessário:
	 * 
	 * 
	 * imagem = xSpriteSheet + comprimeintoDoSprite * numeroDoQuadroQueEuQueroVê
	 * 
	 */

	@Override
	public void renderizeme(Graphics2D g) {
		if (isVisivel()) {
			BufferedImage img = imagem.getSubimage(
					animacao.getX() + comprimentoElemento * animacao.getQuadros()[proximoQuadro], animacao.getY(),
					comprimentoElemento, larguraElemento);
			g.drawImage(img, x, y, getComprimento(), getLargura(), null);
		}
		proximoQuadro++;
		if (proximoQuadro == animacao.getQuadros().length)
			proximoQuadro = 0;
	}

	@Override
	public int getComprimento() {
		return comprimento;
	}

	public void setComprimento(int comprimento) {
		this.comprimento = comprimento;
	}

	@Override
	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public void adicionarAnimacao(String nome, Animacao animacao) {
		this.animacoes.put(nome, animacao);
	}

	public void executarAnimacao(String nome) {
		Animacao animacaoAux = animacoes.get(nome);
		if (animacao != animacaoAux) {
			animacao = animacaoAux;
			proximoQuadro = 0;
		}
	}

}
