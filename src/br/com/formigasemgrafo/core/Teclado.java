package br.com.formigasemgrafo.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teclado extends KeyAdapter {

	private final int TECLA_LIBERADA = 0;
	private final int TECLA_PRESSIONADA = 1;

	private Map<Integer, Integer> cacheDeTeclas;
	private List<Integer> teclasPressionadas;
	private List<Integer> teclasLiberadas;

	private static final Teclado teclado = new Teclado();

	private Teclado() {
		cacheDeTeclas = new HashMap<Integer, Integer>();
		teclasPressionadas = new ArrayList<Integer>();
		teclasLiberadas = new ArrayList<Integer>();
	}

	public static Teclado getInstancia() {
		return teclado;
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

}
