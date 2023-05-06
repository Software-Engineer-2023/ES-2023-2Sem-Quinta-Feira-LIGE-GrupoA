package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.MainMenu;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de testes unitários da classe MainMenu
 */
class MainMenuTest {

    @Test
    void OpenSchedulesTest() {
        MainMenu menu = new MainMenu();
        JButton openButton = menu.getOpenSchedulesButton();
        openButton.doClick();
        assertFalse(menu.isVisible());
    }

    @Test
    void ConvertSchedulesTest() {
        MainMenu menu = new MainMenu();
        JButton convertButton = menu.getConvertSchedulesButton();
        convertButton.doClick();
        assertFalse(menu.isVisible());
    }

    @Test
    void LoadSchedulesTest() {
        MainMenu menu = new MainMenu();
        JButton loadButton = menu.getLoadSchedulesButton();
        loadButton.doClick();
        assertFalse(menu.isVisible());
    }

}