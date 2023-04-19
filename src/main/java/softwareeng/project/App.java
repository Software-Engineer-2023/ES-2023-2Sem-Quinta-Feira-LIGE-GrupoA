package softwareeng.project;

import java.util.logging.Logger;


public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Program started with no issues.");

        // cria a instância da janela de seleção de arquivo
        FileLocationFrame fileLocationFrame = new FileLocationFrame();
        // torna a janela visível para o usuário
        fileLocationFrame.setVisible(true);
    }
}
