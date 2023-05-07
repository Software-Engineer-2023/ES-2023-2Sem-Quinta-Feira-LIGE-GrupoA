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
 This class is responsible for converting a Json file to Csv.
 */
public class JSonToCSV{

    private final Iterator<JsonNode> elements;
    private final CSVWriter writer;

    private static final  String CSVFILENAMETEMP = "data_temp.csv";
    private static final  String CSVFILENAME = "data.csv";
    private static final Logger LOGGER = Logger.getLogger("JSONTOCSV");

    /**
     Constructor of the JSonToCSV class
     @param fileName name of the file
     @throws IOException
     */
    public JSonToCSV(String fileName) throws IOException {
        JsonNode rootNode = new ObjectMapper().readTree(new File(fileName));
        elements =  rootNode.elements();
        writer = new CSVWriter(new FileWriter(CSVFILENAMETEMP));
        createSchema();
    }

    /**
     *Converts the content of a JSON file to a CSV file, writing the information into the appropriate columns.
     *@return true if the conversion is successful, false otherwise
     *@throws IOException if an error occurs while reading or writing the files.
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
     This method creates the header row for the CSV file that will be generated, defining the fields that will be present in the table.    */
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
     This method replaces all commas with semicolons in a temporary CSV file,
     then creates a new CSV file without commas and deletes the temporary file.
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
     *
     Converts a JSON file to a list of Session objects.
     * @param path the path to the JSON file to be converted.
     * @return a list of Session objects.
     * @throws RuntimeException if an error occurs during the JSON file reading.
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