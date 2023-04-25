package softwareeng.project;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @author Proprietário
 */
public class CSVToJson {

	@JsonProperty("Curso")
	private String curso;

	@JsonProperty("Unidade Curricular")
	private String uc;

	@JsonProperty("Turno")
	private String turno;

	@JsonProperty("Turma")
	private String turma;

	@JsonProperty("Inscritos no turno")
	private int inscritos;

	@JsonProperty("Dia da semana")
	private String diaSemana;

	@JsonProperty("Hora início da aula")
	private String horaInicio;

	@JsonProperty("Hora fim da aula")
	private String horaFim;

	@JsonProperty("Data da aula")
	private String dataAula;

	@JsonProperty("Sala atribuída à aula")
	private String salaAtribuida;

	@JsonProperty("Lotação da sala")
	private int lotacao;

	private static final Logger LOGGER = Logger.getLogger("CSVToJSON");
	/**
	 * Construtor da classe CSVToJson vazio.
	 */
	public CSVToJson() {
	}

	/**
	 * Este método converte o conteúdo do ficheiro csv em objetos do tipo CSVToJson e guarda a informação numa List.
	 * @param path representa o caminho do ficheiro.
	 * @return List com o conteúdo do ficheiro.
	 * @exception FileNotFoundException quando o ficheiro não é encontrado.
	 * @exception CsvValidationException quando o ficheiro não é csv.
	 * @exception IOException quando o input não está correto.
	 */
	public List<CSVToJson> convertCSVToArray(String path) {
		List<CSVToJson> array = new ArrayList<>();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
				.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
				.build()) {
			reader.skip(2);
			String[] line;
			while ((line = reader.readNext()) != null) {
				CSVToJson csv = new CSVToJson();
				csv.setCurso(line[0]);
				csv.setUc(line[1]);
				csv.setTurno(line[2]);
				csv.setTurma(line[3]);
				csv.setInscritos(Integer.parseInt(line[4]));
				csv.setDiaSemana(line[5]);
				csv.setHoraInicio(line[6]);
				csv.setHoraFim(line[7]);
				csv.setDataAula(line[8]);
				csv.setSalaAtribuida(line[9]);
				if (line[10].equals("")) {
					line[10] = "0";
				} else {
					csv.setLotacao(Integer.parseInt(line[10]));
				}
				array.add(csv);
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
	 * @exception JsonGenerationException caso a geração de json não corra bem.
	 * @exception JsonMappingException quando o mapeamento do ficheiro falha.
	 * @exception IOException quando o path dado não leva a lado nenhum ficheiro.
	 */
	public void convertArrayToJson(List<CSVToJson> array) {
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
	 * @param path
	 * @return true
	 */
	public boolean convertCSVToJson(String path) {
		ArrayList<CSVToJson> array = (ArrayList<CSVToJson>) convertCSVToArray(path);
		convertArrayToJson(array);

		return true;
	}
	/**
	 * Retorna o curso do objeto
	 * @return uma string que indica o curso
	 */
	public String getCurso() {
		return curso;
	}
	/**
	 * Altera o valor de curso
	 * @param curso o curso a que a aula pertence
	 */
	public void setCurso(String curso) {
		this.curso = curso;
	}
	/**
	 * Retorna a unidade curricular do objeto
	 * @return uma string que representa a unidade curricular
	 */
	public String getUc() {
		return uc;
	}
	/**
	 * Altera o valor de uc
	 * @param uc a unidade curricular
	 */
	public void setUc(String uc) {
		this.uc = uc;
	}
	/**
	 * Retorna o turno do objeto
	 * @return uma string que representa o turno
	 */
	public String getTurno() {
		return turno;
	}
	/**
	 * Altera o valor de turno
	 * @param turno o turno da aula
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}
	/**
	 * Retorna a turma do objeto
	 * @return uma string que representa a turma
	 */
	public String getTurma() {
		return turma;
	}
	/**
	 * Altera o valor de turma
	 * @param turma a turma
	 */
	public void setTurma(String turma) {
		this.turma = turma;
	}
	/**
	 * Retorna o valor de inscritos
	 * @return um inteiro que representa os inscritos
	 */
	public int getInscritos() {
		return inscritos;
	}
	/**
	 * Altera o valor de inscritos
	 * @param inscritos que são os inscritos
	 */
	public void setInscritos(int inscritos) {
		this.inscritos = inscritos;
	}
	/**
	 * Retorna o dia da semana
	 * @return uma string que é o dia da semana
	 */
	public String getDiaSemana() {
		return diaSemana;
	}
	/**
	 * Altera o valor do dia da semana
	 * @param diaSemana que é o dia da semana
	 */
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	/**
	 * Retorna a hora de início da aula
	 * @return uma string que é a hora de início
	 */
	public String getHoraInicio() {
		return horaInicio;
	}
	/**
	 * Altera o valor da hora de início
	 * @param horaInicio que é uma hora
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	/**
	 * Retorna a hora de fim da aula
	 * @return uma string que é a hora do fim da aula
	 */
	public String getHoraFim() {
		return horaFim;
	}
	/**
	 * Altera o valor da hora de fim da aula
	 * @param horaFim que é uma hora
	 */
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	/**
	 * Retorna a data da aula
	 * @return uma string que é a data da aula
	 */
	public String getDataAula() {
		return dataAula;
	}
	/**
	 * Altera o valor da data da aula
	 * @param dataAula que é uma data de uma aula
	 */
	public void setDataAula(String dataAula) {
		this.dataAula = dataAula;
	}
	/**
	 * Retorna a sala atribuída à aula
	 * @return uma string que é a sala atribuida a uma aula
	 */
	public String getSalaAtribuida() {
		return salaAtribuida;
	}
	/**
	 * Altera o valor da sala atribuída
	 * @param salaAtribuida que é uma sala atribuída a uma aula
	 */
	public void setSalaAtribuida(String salaAtribuida) {
		this.salaAtribuida = salaAtribuida;
	}
	/**
	 * Retorna a lotacao da aula
	 * @return um integer que representa o número de pessoas inscritas na aula
	 */
	public int getLotacao() {
		return lotacao;
	}
	/**
	 * Altera o valor de lotação da aula
	 * @param lotacao que é o número de pessoas na aula
	 */
	public void setLotacao(int lotacao) {
		this.lotacao = lotacao;
	}
}