package ES_Projeto_GrupoA_2023.projetoES;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

class CSVToJSonTest {

	@Test
	void testConvertCSVToArrayList() {

	CSVToJSon csv = new CSVToJSon();
	
	ArrayList<CSVToJSon> array = csv.convertCSVToArray("C:\\Teste\\horario_exemplo.csv");
	assertNotNull(array);
	
	assertEquals("ME", array.get(0).getCurso());
	assertEquals("Teoria dos Jogos e dos Contratos", array.get(0).getUc());
	assertEquals("01789TP01", array.get(0).getTurno());
	assertEquals("MEA1", array.get(0).getTurma());
	assertEquals(30, array.get(0).getIncritos());
	assertEquals("Sex", array.get(0).getDiaSemana());
	assertEquals("13:00:00", array.get(0).getHoraInicio());
	assertEquals("14:30:00", array.get(0).getHoraFim());
	assertEquals("02/12/2022", array.get(0).getDataAula());
	assertEquals("AA2.25", array.get(0).getSalaAtribuida());
	assertEquals(34, array.get(0).getLotacao());
	}
	
    @Test
    public void testarIOException() {
        try {
            CSVToJSon csv = new CSVToJSon();
            ArrayList<CSVToJSon> array = csv.convertCSVToArray("C:\\Teste\\horario_exemplo.csv");
            throw new IOException("Erro: Não foi possível ler o ficheiro");
        } catch (IOException e) {
            assertEquals("Erro: Não foi possível ler o ficheiro", e.getMessage());
        }
    }
	
    @Test
    public void testarFileNotFoundException() {
        try {
            CSVToJSon csv = new CSVToJSon();
            ArrayList<CSVToJSon> array = csv.convertCSVToArray("C:\\Teste\\horario_exemplo.csv");
            throw new FileNotFoundException("Erro:O ficheiro não foi encontrado! Verifique se o path está correto");
        } catch (FileNotFoundException e) {
            assertEquals("Erro:O ficheiro não foi encontrado! Verifique se o path está correto", e.getMessage());
        }
    }
   
    @Test
    public void testarCsvValidationException() {
        try {
            CSVToJSon csv = new CSVToJSon();
            ArrayList<CSVToJSon> array = csv.convertCSVToArray("C:\\Teste\\horario_exemplo.csv");
            throw new CsvValidationException("Erro: problemas na validação CSV");
        } catch (CsvValidationException e) {
            assertEquals("Erro: problemas na validação CSV", e.getMessage());
        }
    }
    
    @Test
	void convertArrayListToJSonTest() {
		CSVToJSon csv = new CSVToJSon();
		
		ArrayList<CSVToJSon> array = csv.convertCSVToArray("C:\\Users\\Henrique\\Documents\\Iscte\\ES\\horario_exemplo.csv");
		
		csv.convertArrayToJson(array);
	}


}
