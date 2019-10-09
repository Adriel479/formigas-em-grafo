package br.com.formigasemgrafo.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class AgrupamentoSprite implements Renderizavel{

	private List<Camada> camadas;
	
	public AgrupamentoSprite(int numeroDeCamadas) {
		camadas = new ArrayList<Camada>(numeroDeCamadas);
	}
	
	public void adicionarCamada(Camada camada) {
		camadas.add(camada);
	}
	
	public void removerCamada(Camada camada) {
		camadas.remove(camada);
	}
	
	@Override
	public void renderizeme(Graphics2D g) {
		for (Camada camada : camadas) 
			camada.renderizeme(g);
	}

}
