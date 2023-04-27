package ES_Projeto_GrupoA_2023.projetoES;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

import junit.framework.Assert;
import softwareeng.project.CSVToJson;

/**
 * Classe de testes unitários da classe CSVToJson
 */
class CSVToJsonTest {
	@Test
	void convertCSVToArrayListTest() {

	CSVToJson csv = new CSVToJson();
	
	ArrayList<CSVToJson> array = (ArrayList<CSVToJson>) csv.convertCSVToArray("C:\\Teste\\horario_exemplo.csv");
	assertNotNull(array);
	
	assertEquals("ME", array.get(0).getCurso());
	assertEquals("Teoria dos Jogos e dos Contratos", array.get(0).getUc());
	assertEquals("01789TP01", array.get(0).getTurno());
	assertEquals("MEA1", array.get(0).getTurma());
	assertEquals(30, array.get(0).getInscritos());
	assertEquals("Sex", array.get(0).getDiaSemana());
	assertEquals("13:00:00", array.get(0).getHoraInicio());
	assertEquals("14:30:00", array.get(0).getHoraFim());
	assertEquals("02/12/2022", array.get(0).getDataAula());
	assertEquals("AA2.25", array.get(0).getSalaAtribuida());
	assertEquals(34, array.get(0).getLotacao());
	}
	
    @Test
    void iOExceptionTest() {
        try {
            CSVToJson csv = new CSVToJson();
            ArrayList<CSVToJson> array = (ArrayList<CSVToJson>) csv.convertCSVToArray("C:\\Teste\\horario_exemplo.csv");
            throw new IOException("Erro: Não foi possível ler o ficheiro");
        } catch (IOException e) {
            assertEquals("Erro: Não foi possível ler o ficheiro", e.getMessage());
        }
    }
	
    @Test
    void fileNotFoundExceptionTest() {
        try {
            CSVToJson csv = new CSVToJson();
            ArrayList<CSVToJson> array = (ArrayList<CSVToJson>) csv.convertCSVToArray("C:\\Teste\\horarioexemplo.csv");
            throw new FileNotFoundException("Erro:O ficheiro não foi encontrado! Verifique se o path está correto");
        } catch (FileNotFoundException e) {
            assertEquals("Erro:O ficheiro não foi encontrado! Verifique se o path está correto", e.getMessage());
        }
    }
   
    @Test
    void csvValidationExceptionTest() {
        try {
            CSVToJson csv = new CSVToJson();
            ArrayList<CSVToJson> array = (ArrayList<CSVToJson>) csv.convertCSVToArray("C:\\Teste\\horario_exemplo.csv");
            throw new CsvValidationException("Erro: problemas na validação CSV");
        } catch (CsvValidationException e) {
            assertEquals("Erro: problemas na validação CSV", e.getMessage());
        }
    }
    
    @Test
    void convertCSVToJSonTest(){
        ObjectMapper mapper = new ObjectMapper();
        CSVToJson csv = new CSVToJson();
        csv.convertCSVToJson("C:\\Teste\\horario_exemplo.csv");
        
        File arquivo1 = new File("C:\\Users\\Proprietário\\git\\ES-2023-2Sem-Quinta-Feira-LIGE-GrupoA\\horario.json");
        File arquivo2 = new File("C:\\Teste\\horario.json");
        Object obj1;
		try {
			obj1 = mapper.readValue(arquivo1, Object.class);
	        Object obj2 = mapper.readValue(arquivo2, Object.class);
	        Assert.assertEquals(obj1, obj2);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    

}
