package ES_Projeto_GrupoA_2023.projetoES;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import softwareeng.project.FileLocationFrame;
/**
 * Classe de testes unitários da classe FileLocationFrame
 */
public class FileLocationFrameTest {

    private FileLocationFrame fileLocationFrame;

    @Before
    public void setUp() throws Exception {
        fileLocationFrame = new FileLocationFrame();
    }

    @Test
    public void testComponents() {
        assertNotNull(fileLocationFrame.getLocationTextField());
        assertNotNull(fileLocationFrame.getBrowseButton());
        assertNotNull(fileLocationFrame.getOkButton());
    }

    @Test
    public void testBrowseButtonClicked() {
        fileLocationFrame.clickBrowseButton();
        assertEquals("C:\\Teste\\horario_exemplo.csv", fileLocationFrame.getLocationTextField().getText());
    }




    @Test
    public void testLocationTextFieldIsEditable() {
        assertTrue(fileLocationFrame.getLocationTextField().isEditable());
    }

    @Test
    public void testBrowseButton() {
        fileLocationFrame.clickBrowseButton();
        assertNotNull(fileLocationFrame.getLocationTextField().getText());
    }
    
    @Test
    public void testOkButtonEnabled() {
        fileLocationFrame.setLocationTextField("C:\\Teste\\horario-exemplo.csv");
        assertTrue(fileLocationFrame.getOkButton().isEnabled());
    }
}

