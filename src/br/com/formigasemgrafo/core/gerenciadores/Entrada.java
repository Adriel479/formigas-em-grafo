package br.com.formigasemgrafo.core.gerenciadores;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

public class Entrada extends KeyAdapter implements MouseMotionListener, MouseListener {

	private final int LIBERADA = 0;
	private final int PRESSIONADA = 1;

	private Map<Integer, Integer> mapaDeTeclas;
	private Map<Integer, Integer> cacheMapaDeTeclas;

	private int x, y;

	private static final Entrada entrada = new Entrada();

	private Entrada() {
		mapaDeTeclas = new HashMap<Integer, Integer>();
		cacheMapaDeTeclas = new HashMap<Integer, Integer>();

	}

	public static Entrada getInstancia() {
		return entrada;
	}
	
	public void limpar() {
		cacheMapaDeTeclas.clear();
	}

	@Override
	public void keyPressed(KeyEvent evento) {
		mapaDeTeclas.put(evento.getKeyCode(), PRESSIONADA);
	}

	@Override
	public void keyReleased(KeyEvent evento) {
		mapaDeTeclas.put(evento.getKeyCode(), LIBERADA);
	}

	public boolean isTeclaPressionada(int codigoDaTecla) {
		return cacheMapaDeTeclas.containsKey(codigoDaTecla) && cacheMapaDeTeclas.get(codigoDaTecla) == PRESSIONADA;
	}

	public boolean isTeclaLiberada(int codigoDaTecla) {
		return cacheMapaDeTeclas.containsKey(codigoDaTecla) && cacheMapaDeTeclas.get(codigoDaTecla) == LIBERADA;
	}

	public void atualizaCache() {
		cacheMapaDeTeclas.clear();
		for (int chave : mapaDeTeclas.keySet()) {
			cacheMapaDeTeclas.put(chave, mapaDeTeclas.get(chave));
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isClique() {
		return cacheMapaDeTeclas.containsKey(MouseEvent.BUTTON1) && cacheMapaDeTeclas.get(MouseEvent.BUTTON1) == PRESSIONADA;
	}

	@Override
	public void mouseClicked(MouseEvent evento) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent evento) {
		mapaDeTeclas.put(evento.getButton(), PRESSIONADA);

	}

	@Override
	public void mouseReleased(MouseEvent evento) {
		mapaDeTeclas.put(evento.getButton(), LIBERADA);
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
