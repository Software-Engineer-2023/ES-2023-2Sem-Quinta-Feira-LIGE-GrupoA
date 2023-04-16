package ES_Projeto_GrupoA_2023.projetoES;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CSVToJSonTest {

	@Test
	void test() {

	CSVToJSon csv = new CSVToJSon();
	
	ArrayList<CSVToJSon> array = csv.convertCSVToArray("C:\\Users\\Henrique\\Documents\\Iscte\\ES\\horario_exemplo.csv");
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
	void test2() {
		CSVToJSon csv = new CSVToJSon();
		
		ArrayList<CSVToJSon> array = csv.convertCSVToArray("C:\\Users\\Henrique\\Documents\\Iscte\\ES\\horario_exemplo.csv");
		
		csv.convertArrayToJson(array);
	}


}
