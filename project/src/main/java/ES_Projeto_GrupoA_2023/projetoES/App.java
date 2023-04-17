package ES_Projeto_GrupoA_2023.projetoES;

/**
 * Hello world!
 *
 */

import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOGGER.info("This is an information message");

        // cria a instância da janela de seleção de arquivo
        FileLocationFrame fileLocationFrame = new FileLocationFrame();
        // torna a janela visível para o usuário
        fileLocationFrame.setVisible(true);
    }
}

