package softwareeng.project;

import java.io.*;
import java.text.ParseException;
import java.util.Iterator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.CSVWriter;

import java.util.List;
import java.util.logging.Logger;

/**
 * esta classe é responsavel por converter um ficheiro Json em Csv
 */
public class JSonToCSV{

    private final Iterator<JsonNode> elements;
    private final CSVWriter writer;

    private static final  String CSVFILENAMETEMP = "data_temp.csv";
    private static final  String CSVFILENAME = "data.csv";
    private static final Logger LOGGER = Logger.getLogger("JSONTOCSV");

    /**
     * Construtor da classe JSonToCSV
     * @param fileName nome do ficheiro
     * @throws IOException
     */
    public JSonToCSV(String fileName) throws IOException {
        JsonNode rootNode = new ObjectMapper().readTree(new File(fileName));
        elements =  rootNode.elements();
        writer = new CSVWriter(new FileWriter(CSVFILENAMETEMP));
        createSchema();
    }

    /**
    *Converte o conteúdo de um arquivo JSON em um arquivo CSV, escrevendo as informações nas colunas apropriadas.
    *@return true se a conversão for bem-sucedida, false caso contrário
    *@throws IOException se ocorrer um erro ao ler ou escrever os arquivos
    */
    public boolean convertFile() throws IOException {
        while (elements.hasNext()) {
            ObjectNode object = (ObjectNode) elements.next();
            String[] line = new String[11];
            line[0] = object.get("Curso").asText();
            line[1] = object.get("Unidade Curricular").asText();
            line[2] = object.get("Turno").asText();
            line[3] = object.get("Turma").asText();
            line[4] = object.get("Inscritos no turno").asText();
            line[5] = object.get("Dia da semana").asText();
            line[6] = object.get("Hora início da aula").asText();
            line[7] = object.get("Hora fim da aula").asText();
            line[8] = object.get("Data da aula").asText();
            line[9] = object.get("Sala atribuída à aula").asText();
            line[10] = object.get("Lotação da sala").asText();
            writer.writeNext(line);
        }
        writer.close();
        changeCommas();
        return true;
    }

    /**
    *Este método cria a linha de cabeçalho para o arquivo CSV que será gerado,definindo os campos que serão presentes na tabela.
    */
    private void createSchema(){
        String[] line = new String[11];
        line[0] = "Curso";
        line[1] = "Unidade Curricular";
        line[2] = "Turno";
        line[3] = "Turma";
        line[4] = "Inscritos no turno";
        line[5] = "Dia da semana";
        line[6] = "Hora início da aula";
        line[7] = "Hora fim da aula";
        line[8] = "Data da aula";
        line[9] = "Sala atribuída à aula";
        line[10] = "Lotação da sala";
        writer.writeNext(line);
    }

    /**
    *Este método realiza a substituição de todas as vírgulas por ponto e vírgula em um arquivo CSV temporário,em seguida, cria um novo arquivo CSV sem as vírgulas e exclui o arquivo temporário.
    */
    private void changeCommas() {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(CSVFILENAMETEMP));
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(CSVFILENAME))
        ) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace(",", ";");
                writer2.write(line);
                writer2.newLine();
            }
            reader.close();
            if (new File(CSVFILENAMETEMP).delete())
                LOGGER.info("O arquivo temporário foi excluído com sucesso!");
            else
                LOGGER.info("Não foi possível excluir o arquivo temporário.");
        }  catch (IOException e) {
            LOGGER.info("Erro na leitura ou na escrita do novo ficheiro CSV");
        }
    }

    
    /**
     * Converte um arquivo JSON em uma lista de objetos Session.
     * @param path o caminho para o arquivo JSON a ser convertido.
     * @return uma lista de objetos Session.
     * @throws RuntimeException se ocorrer um erro durante a leitura do arquivo JSON.
     */
    public static List<Session> convertJsonToArray(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<Session> sessions = null;
        try {
            sessions = mapper.readValue(new File(path), new TypeReference<List<Session>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sessions;
    }


}