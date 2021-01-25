package core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Product;

/**
 * Classe client que representa a conexão a um data lake. No caso deste projeto, le o json mock.
 *
 */
public class DataLakeClient {

	private static final String MOCK_PATH = System.getProperty("user.dir") + File.separator + "mocks" + File.separator + "data_lake_mock.json";
	
	public static Map<String, Product> productMap;
	
	/**
	 * Preenche a estrutura de produtos com as informacoes obtidas atraves do arquivo mock.
	 */
	public static void getData() {
		try {
			String mockJson = FileUtils.readFileToString(new File(MOCK_PATH), StandardCharsets.UTF_8);
			Type listType = new TypeToken<Map<String, Product>>() {}.getType();
			productMap = new Gson().fromJson(mockJson, listType);
		} catch (IOException e) {
			System.err.println("Houve um erro ao ler o arqvuivo: " + MOCK_PATH);
			e.printStackTrace();
		}
	}
	

}
