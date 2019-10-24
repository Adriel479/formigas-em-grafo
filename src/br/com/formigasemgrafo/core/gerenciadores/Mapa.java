package br.com.formigasemgrafo.core.gerenciadores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.com.formigasemgrafo.core.Camada;
import br.com.formigasemgrafo.core.MapaSprite;

public class Mapa {

	private static Mapa mapa = new Mapa();
	private Map<String, MapaSprite> mapas;

	private Mapa() {
		mapas = new HashMap<String, MapaSprite>();
	}

	public static Mapa getInstancia() {
		return mapa;
	}

	public MapaSprite getMapaDeSprite(String nomeDoAgrupamentoDeSprite) {
		return mapas.get(nomeDoAgrupamentoDeSprite);
	}

	public void carregarMapaDeSprite(String nome, String caminhoDoAgrupamentoDeSprite,
			String recursosDoAgrupamentoDeSprite) {
		if (!mapas.containsKey(nome)) {
			carregarRecursosDoMapa(recursosDoAgrupamentoDeSprite);
			JsonReader leitor = Json.createReader(getClass().getResourceAsStream(caminhoDoAgrupamentoDeSprite));
			JsonObject mapa = leitor.readObject();
			JsonArray camadas = mapa.getJsonArray("layers");
			MapaSprite grupo = new MapaSprite(camadas.size());

			for (int i = 0; i < camadas.size(); i++) {
				JsonObject jsonCamada = camadas.getJsonObject(i);
				Camada camada = new Camada(jsonCamada.getInt("height"), jsonCamada.getInt("width"));
				camada.setComprimentoSprite(mapa.getInt("tilewidth"));
				camada.setAlturaSprite(mapa.getInt("tileheight"));
				camada.setOpacidade(jsonCamada.getJsonNumber("opacity").numberValue().floatValue());
				camada.setVisivel(jsonCamada.getBoolean("visible"));
				JsonArray matrizAssociativaDeImagens = jsonCamada.getJsonArray("data");
				camada.configurarElementosDaCamada(matrizAssociativaDeImagens.toArray());
				grupo.adicionarCamada(camada);
			}
			leitor.close();
			mapas.put(nome, grupo);
		}
	}

	private void carregarRecursosDoMapa(String caminho) {
		try (BufferedReader leitor = new BufferedReader(
				new InputStreamReader((getClass().getResourceAsStream(caminho))))) {
			Pattern ids = Pattern.compile("id=\"\\d+\"");
			Pattern local = Pattern.compile("source=\".+\"");
			while (leitor.ready()) {
				Matcher matcher = ids.matcher(leitor.readLine());
				if (matcher.find()) {
					String stringIntermediaria = matcher.group();
					stringIntermediaria = stringIntermediaria.substring(stringIntermediaria.indexOf("\"") + 1,
							stringIntermediaria.length() - 1);
					Matcher matcherLocal = local.matcher(leitor.readLine());
					if (matcherLocal.find()) {
						String segundaStringIntermediaria = matcherLocal.group();
						segundaStringIntermediaria = segundaStringIntermediaria.substring(
								segundaStringIntermediaria.indexOf("\"") + 3, segundaStringIntermediaria.length() - 1);
						Imagem.getInstancia().carregarImagem("" + (Integer.parseInt(stringIntermediaria) + 1),
								segundaStringIntermediaria);
					} else {
						throw new RuntimeException(
								String.format("O arquivo %s não se encontra no formato esperado!", caminho));
					}
				}
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
