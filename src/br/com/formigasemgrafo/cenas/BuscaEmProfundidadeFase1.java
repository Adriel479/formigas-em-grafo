package br.com.formigasemgrafo.cenas;

import br.com.formigasemgrafo.core.Cena;

public class BuscaEmProfundidadeFase1 extends Cena {

	@Override
	public void carregar() {
		mapa.carregarMapaDeSprite("mapaBPFase1", "/mapas/buscaEmProfundidadeFase1.json", "/mapas/recursos.tsx");
	}

	@Override
	public void atualizar() {

	}

	@Override
	public void criar() {
		adicionarObjetoRenderizavel(mapa.getMapaDeSprite("mapaBPFase1"));
	}

	@Override
	public void descarregar() {

	}

}
