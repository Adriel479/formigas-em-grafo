package br.com.formigasemgrafo.core.gerenciadores;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entrada extends KeyAdapter implements MouseMotionListener, MouseListener {

	private final int TECLA_LIBERADA = 0;
	private final int TECLA_PRESSIONADA = 1;

	private Map<Integer, Integer> cacheDeTeclas;
	private List<Integer> teclasPressionadas;
	private List<Integer> teclasLiberadas;

	private int x, y;
	private boolean clique = false;

	private static final Entrada entrada = new Entrada();

	private Entrada() {
		cacheDeTeclas = new HashMap<Integer, Integer>();
		teclasPressionadas = new ArrayList<Integer>();
		teclasLiberadas = new ArrayList<Integer>();
	}

	public static Entrada getInstancia() {
		return entrada;
	}

	@Override
	public void keyPressed(KeyEvent evento) {
		teclasPressionadas.add(evento.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent evento) {
		teclasLiberadas.add(evento.getKeyCode());
	}

	public boolean isTeclaPressionada(int codigoDaTecla) {
		return cacheDeTeclas.containsKey(codigoDaTecla) && cacheDeTeclas.get(codigoDaTecla) == TECLA_PRESSIONADA;
	}

	public boolean isTeclaLiberada(int codigoDaTecla) {
		return cacheDeTeclas.containsKey(codigoDaTecla) && cacheDeTeclas.get(codigoDaTecla) == TECLA_LIBERADA;
	}

	public void atualizaCache() {
		for (int teclasPressionada : teclasLiberadas) {
			cacheDeTeclas.put(teclasPressionada, TECLA_LIBERADA);
		}
		for (int teclaPressionada : teclasPressionadas) {
			cacheDeTeclas.put(teclaPressionada, TECLA_PRESSIONADA);
		}
		teclasPressionadas.clear();
		teclasLiberadas.clear();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isClique() {
		return clique;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.clique = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent evento) {
		teclasPressionadas.add(evento.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent evento) {
		this.x = evento.getX();
		this.y = evento.getY();
	}

}
