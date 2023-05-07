package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.Test;
import softwareeng.project.IcsToCSV;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertTrue;

public class IcsToCSVTest {
    private final String TEST_FILE_PATH = "C:\\Users\\carol\\OneDrive\\Documentos\\GitHub\\ES-2023-2Sem-Quinta-Feira-LIGE-GrupoAasd\\rafetelvino@gmail.com.ics";

    @Test
    public void testConvertFile() throws IOException {
        // Test if conversion is successful
        IcsToCSV converter = new IcsToCSV(TEST_FILE_PATH);
        System.out.println("teste bem sucedido");
        assertTrue(converter.convertFile());

        // Test if output file was created
        File outputFile = new File(TEST_FILE_PATH.replace(".ics", ".csv"));
        System.out.println("ficheiro criado");
        assertTrue(outputFile.exists());

        // Test if output file is not empty
        assertTrue(outputFile.length() > 0);

        // Delete output file after test
        outputFile.delete();
    }
}
