package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.MainMenu;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    @Test
    void openSchedulesTest() {
        MainMenu mm= new MainMenu();
        mm.openSchedules();
        assertEquals("This feature is under development! Please be patient.", JOptionPane.getRootFrame().getMostRecentFocusOwner().toString());

    }
    @Test
    void convertSchedulesTest() {
        MainMenu mm = new MainMenu();
        mm.getConvertSchedulesButton().doClick();
        assertTrue(mm.isVisible());
        assertFalse(mm.getOpenSchedulesButton().isVisible());
        assertFalse(mm.getConvertSchedulesButton().isVisible());
        assertFalse(mm.getLoadSchedulesButton().isVisible());

    }

    @Test
    void loadSchedules() {
        MainMenu mm = new MainMenu();
        mm.getLoadSchedulesButton().doClick();
        assertTrue(mm.isVisible());
        assertFalse(mm.getOpenSchedulesButton().isVisible());
        assertFalse(mm.getConvertSchedulesButton().isVisible());
        assertFalse(mm.getLoadSchedulesButton().isVisible());
    }

}