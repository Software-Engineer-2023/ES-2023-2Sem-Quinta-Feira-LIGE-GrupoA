package softwareeng.project;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
/**
 * Classe responsável por converter um ficheiro csv num ficheiro Json.
 * Para isso, temos 11 variáveis que correspondem a todos os parâmetros do ficheiro csv.
 */
public class CSVToJson {
	private static final Logger LOGGER = Logger.getLogger("CSVToJSON");

	/**
	 * Constructor for CSVToJson class is intentionally left empty.
	 * All necessary setup is done in other methods.
	 */
	public CSVToJson() {
		// intentionally empty
	}

	/**
	 * Este método converte o conteúdo do ficheiro csv em objetos do tipo CSVToJson e guarda a informação numa List.
	 *
	 * @param path representa o caminho do ficheiro.
	 * @return List com o conteúdo do ficheiro.
	 * @throws FileNotFoundException  quando o ficheiro não é encontrado.
	 * @throws CsvValidationException quando o ficheiro não é csv.
	 * @throws IOException            quando o input não está correto.
	 */
	public List<Session> convertCSVToArray(String path){
		List<Session> array = new ArrayList<>();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
				.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
				.build()) {
			reader.skip(2);
			String[] line;
			while ((line = reader.readNext()) != null) {
				int a;
				if (line[10].equals("")) {
					a = 0;
				} else {
					a = Integer.parseInt(line[10]);
				}

				Session c = new Session(line[0], line[1], line[2],
						line[3], Integer.parseInt(line[4]), line[5], line[6],
						line[7], line[8], line[9], a);

				array.add(c);
			}
		} catch (FileNotFoundException e) {
			LOGGER.severe("File not found! Please check if the path is correct.");
		} catch (CsvValidationException | IOException e) {
			LOGGER.severe("Error: issues with CSV validation.");
		}
		return array;
	}

	/**
	 * @param array List que contém o conteúdo de um ficheiro csv.
	 * @throws JsonGenerationException caso a geração de json não corra bem.
	 * @throws JsonMappingException    quando o mapeamento do ficheiro falha.
	 * @throws IOException             quando o path dado não leva a lado nenhum ficheiro.
	 */
	public void convertArrayToJson(List<Session> array) {
		//Cria um mapeamento novo para mapear dados json em java
		ObjectMapper mapa = new ObjectMapper();
		//Seleciona a forma como o ficheiro será escrito tornando-o mais claro para ser lido
		mapa.enable(SerializationFeature.INDENT_OUTPUT);
		//Escreve os dados json num ficheiro
		ObjectWriter writer = mapa.writerWithDefaultPrettyPrinter();

		File file = new File("horario.json");
		try {
			writer.writeValue(file, array);
		} catch (JsonGenerationException e) {
			LOGGER.severe("Erro: problemas na escrita do ficheiro ");
		} catch (JsonMappingException e) {
			LOGGER.severe("Erro: problemas no mapeamento do ficheiro");
		} catch (IOException e) {
			LOGGER.severe("Erro: Não foi possível encontrar o ficheiro ou ficheiro inválido");
		}

		LOGGER.info("Sucesso: ficheiro criado com sucesso");

	}


	/**
	 * Converte um ficheiro CSV para Json através dos métodos desenvolvidos
	 *
	 * @param path o caminho completo do ficheiro CSV a ser convertido
	 * @return true se a conversão for bem sucedida, false caso contrário
	 */
	public boolean convertCSVToJson(String path) {
		List<Session> array = convertCSVToArray(path);
		convertArrayToJson(array);

		return true;
		}


	}
