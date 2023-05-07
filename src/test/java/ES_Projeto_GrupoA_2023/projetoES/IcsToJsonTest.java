package ES_Projeto_GrupoA_2023.projetoES;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import softwareeng.project.IcsToJson;

/**
 * Classe de testes unitarios para a class IcsToJson
 */


public class IcsToJsonTest {
    private IcsToJson converter;

    @Before
    public void setUp() throws Exception {
        String filePath = "rafetelvino@gmail.com.ics";
        converter = new IcsToJson(filePath);
    }

    @Test
    public void testConvertFile() throws IOException, ParseException {
        boolean success = converter.convertFile();

        assertTrue(success);

        String jsonFilePath = "C:\\Users\\Proprietário\\Documents\\GitHub\\ES-2023-2Sem-Quinta-Feira-LIGE-GrupoA\\rafetelvino@gmail.com.json";
        File jsonFile = new File(jsonFilePath);

        assertTrue(jsonFile.exists());
        assertTrue(jsonFile.isFile());
        assertTrue(jsonFile.length() > 0);

        jsonFile.delete();
    }
}