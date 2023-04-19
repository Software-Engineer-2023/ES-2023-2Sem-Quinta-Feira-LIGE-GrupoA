package ES_Projeto_GrupoA_2023.projetoES;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
public class CSVToJSon {
	//Tag @JsonProperty é usada para quando um ficheiro csv for convertido em json, os campos tenham o mesmo nome que no ficheiro csv
	
	
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
	 * Método vazio que apenas permite criar um objeto CSVToJSon e acessar aos métodos desta classe
	 */
	public CSVToJSon() {
	}

	/* Método que converte um ficheiro CSV para um ArrayList */
	public ArrayList<CSVToJSon> convertCSVToArray(String path) {
		ArrayList<CSVToJSon> array = new ArrayList<>();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
		        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
		        .build()) {
			//Passa duas linhas à frente pois a primeira é não possui texto e a segunda possui as colunas da tabela
			reader.skip(2); 
			String[] linha;
			try {
				while((linha = reader.readNext()) != null) {
					CSVToJSon csv = new CSVToJSon();
					csv.setCurso(linha[0]);
					csv.setUc(linha[1]);
					csv.setTurno(linha[2]);
					csv.setTurma(linha[3]);
					csv.setInscritos(Integer.parseInt(linha[4]));
					csv.setDiaSemana(linha[5]);
					csv.setHoraInicio(linha[6]);
					csv.setHoraFim(linha[7]);
					csv.setDataAula(linha[8]);
					csv.setSalaAtribuida(linha[9]);
					
					//Existem valores inteiros que estão vazios no ficheiro csv. Para evitar possíveis erros, colocamos esses valores a 0.
					
					if(linha[10].equals("") ) {
						linha[10] = "0";
					}else {
						csv.setLotacao(Integer.parseInt(linha[10]));
					}
					array.add(csv);
				}
			} catch (CsvValidationException e) {
				LOGGER.severe("Erro: problemas na validação CSV");
				
			}
			
		} catch (FileNotFoundException e) {
			LOGGER.severe("Erro:O ficheiro não foi encontrado! Verifique se o path está correto");
		} catch (IOException e) {
			LOGGER.severe("Erro: Não foi possível ler o ficheiro");
		}
		return array;
	}	
	
	public void convertArrayToJson(ArrayList<CSVToJSon> array) {
		//Cria um mapeamento novo para mapear dados json em java
		ObjectMapper mapa = new ObjectMapper();
		//Seleciona a forma como o ficheiro será escrito tornando-o mais claro para ser lido
        mapa.enable(SerializationFeature.INDENT_OUTPUT);
        //Escreve os dados json num ficheiro
        ObjectWriter writer = mapa.writerWithDefaultPrettyPrinter();

        File file = new File("horario.json");
        try {
			writer.writeValue(file, array);
		} catch (StreamWriteException e) {
			LOGGER.severe("Erro: problemas na escrita do ficheiro ");
		} catch (DatabindException e) {
			LOGGER.severe("Erro: problemas no mapeamento do ficheiro");
		} catch (IOException e) {
			LOGGER.severe("Erro: Mão foi possível encontrar o ficheiro ou ficheiro inválido");
		}
        
        LOGGER.info("Sucesso: ficheiro criado com sucesso");
        
	}
	public void convertCSVToJSon(String path) {
		
		ArrayList<CSVToJSon> array = convertCSVToArray(path);
		convertArrayToJson(array);
		
	}
	
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getUc() {
		return uc;
	}
	public void setUc(String uc) {
		this.uc = uc;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	public int getInscritos() {
		return inscritos;
	}
	public void setInscritos(int inscritos) {
		this.inscritos = inscritos;
	}
	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	public String getDataAula() {
		return dataAula;
	}
	public void setDataAula(String dataAula) {
		this.dataAula = dataAula;
	}
	public String getSalaAtribuida() {
		return salaAtribuida;
	}
	public void setSalaAtribuida(String salaAtribuida) {
		this.salaAtribuida = salaAtribuida;
	}
	public int getLotacao() {
		return lotacao;
	}
	public void setLotacao(int lotacao) {
		this.lotacao = lotacao;
	}

	
	
	
	

}

