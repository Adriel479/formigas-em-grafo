package br.com.formigasemgrafo.core.gerenciadores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.com.formigasemgrafo.core.AgrupamentoSprite;
import br.com.formigasemgrafo.core.Camada;

public class Mapa {

	private static Mapa mapa = new Mapa();
	private Map<String, AgrupamentoSprite> mapas;

	private Mapa() {
		mapas = new HashMap<String, AgrupamentoSprite>();
	}

	public static Mapa getInstancia() {
		return mapa;
	}

	public AgrupamentoSprite getGrupoSprite(String nomeDoAgrupamentoDeSprite) {
		return mapas.get(nomeDoAgrupamentoDeSprite);
	}

	public void carregarAgrupamentoDeSprite(String nome, String caminhoDoAgrupamentoDeSprite,
			String recursosDoAgrupamentoDeSprite) {
		try {
			JsonReader leitor = Json.createReader(new FileInputStream(caminhoDoAgrupamentoDeSprite));
			JsonObject mapa = leitor.readObject();
			JsonArray camadas = mapa.getJsonArray("layers");
			AgrupamentoSprite grupo = new AgrupamentoSprite(camadas.size());
			for (int i = 0; i < camadas.size(); i++) {
				JsonObject jsonCamada = camadas.getJsonObject(i);
				Camada camada = new Camada(jsonCamada.getInt("width"), jsonCamada.getInt("height"));
				camada.setComprimentoSprite(mapa.getInt("tilewidth"));
				camada.setAlturaSprite(mapa.getInt("tileheight"));
				camada.setOpacidade(jsonCamada.getJsonNumber("opacity").numberValue().floatValue());
				camada.setVisivel(jsonCamada.getBoolean("visible"));
				grupo.adicionarCamada(camada);
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public AgrupamentoSprite getAgrupamentoDeSprite(String nome) {
		return mapas.get(nome);
	}
}
