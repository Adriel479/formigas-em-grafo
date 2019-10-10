package br.com.formigasemgrafo.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import br.com.formigasemgrafo.core.gerenciadores.Entrada;

public class Jogo {

	private JFrame janela;
	private BufferStrategy estrategiaDeRenderizacao;
	private boolean estadoDoJogo;
	Entrada entrada;
	private Map<String, Cena> cenas;
	private Cena cenaAtual;
	private boolean novaCena;

	public Jogo() {
		janela = new JFrame();
		janela.setSize(800, 600);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		estadoDoJogo = false;
		entrada = Entrada.getInstancia();

		janela.addKeyListener(entrada);
		janela.addMouseListener(entrada);
		janela.addMouseMotionListener(entrada);
		janela.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finalizarJogo();
			}
		});
		cenas = new HashMap<String, Cena>();
		janela.setLocationRelativeTo(null);
	}

	public void adicionarCena(String nome, Cena cena) {
		if (!cenas.containsKey(nome)) {
			cena.configurarGerentes(this);
			cenas.put(nome, cena);
		}
	}

	private void carregaDados() {
		janela.setIgnoreRepaint(true);
		janela.setUndecorated(true);
		janela.setVisible(true);
		janela.createBufferStrategy(2);
		estrategiaDeRenderizacao = janela.getBufferStrategy();
		for (String chaves : cenas.keySet()) {
			cenas.get(chaves).carregar();
		}
	}

	public void executarJogo() {
		carregaDados();
		cenaAtual.criar();
		estadoDoJogo = true;
		while (estadoDoJogo) {
			if (isNovaCena()) {
				cenaAtual.descarregar();
				cenaAtual.carregar();
				cenaAtual.criar();
			}
			entrada.atualizaCache();
			atualizarJogo();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			renderizarJogo();

		}
		descarregarDados();
	}

	public void finalizarJogo() {
		estadoDoJogo = false;
	}

	private void atualizarJogo() {
		cenaAtual.atualizar();
		Thread.yield();
	}

	private void renderizarJogo() {
		Graphics2D g = (Graphics2D) estrategiaDeRenderizacao.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, janela.getWidth(), janela.getHeight());
		cenaAtual.renderizar(g);
		g.dispose();
		estrategiaDeRenderizacao.show();
	}

	private void descarregarDados() {
		cenaAtual.descarregar();
		estrategiaDeRenderizacao.dispose();
		janela.dispose();
	}

	public Cena getCenaAtual() {
		return cenaAtual;
	}

	public void setCenaAtual(String nome) {
		if (cenas.containsKey(nome)) {
			cenaAtual = cenas.get(nome);
		} else {
			throw new RuntimeException(String.format("A cena \"%s\" não existe.", nome));
		}
	}

	public boolean isNovaCena() {
		return novaCena;
	}

	public void setNovaCena(boolean novaCena) {
		this.novaCena = novaCena;
	}

}
