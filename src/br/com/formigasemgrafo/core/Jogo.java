package br.com.formigasemgrafo.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public abstract class Jogo {

	private JFrame janela;
	private BufferStrategy estrategiaDeRenderizacao;
	private boolean estadoDoJogo;
	protected Teclado teclado;
	protected Imagem imagem;
	protected Audio audio;

	public Jogo() {
		janela = new JFrame();
		janela.setSize(800, 600);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setIgnoreRepaint(true);
		janela.createBufferStrategy(2);
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
		estrategiaDeRenderizacao = janela.getBufferStrategy();
		estadoDoJogo = false;
		teclado = Teclado.getInstancia();
		imagem = Imagem.getInstancia();
		audio = Audio.getInstancia();
		janela.addKeyListener(teclado);
		janela.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finalizarJogo();
			}
		});
	}

	private void carregaDados() {
		onCarregarDados();
	}

	public void executarJogo() {
		carregaDados();
		estadoDoJogo = true;
		while (estadoDoJogo) {
			atualizarJogo();
			renderizarJogo();
		}
		descarregarDados();
	}

	public void finalizarJogo() {
		estadoDoJogo = false;
	}

	private void atualizarJogo() {
		onAtualizarJogo();
		Thread.yield();
	}

	private void renderizarJogo() {
		Graphics2D g = (Graphics2D) estrategiaDeRenderizacao.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, janela.getWidth(), janela.getHeight());
		onRenderizarJogo(g);
		g.dispose();
		estrategiaDeRenderizacao.show();
	}

	private void descarregarDados() {
		onDescarregarDados();
		estrategiaDeRenderizacao.dispose();
		janela.dispose();
	}

	public abstract void onCarregarDados();

	public abstract void onAtualizarJogo();

	public abstract void onRenderizarJogo(Graphics2D g);

	public abstract void onDescarregarDados();
}
