package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.FileDownloader;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de testes unitários da classe FileDownloader
 */
class FileDownloaderTest {

    @Test
    void downloadJsonFile() {

        FileDownloader fd = new FileDownloader();
        fd.downloadJsonFile("https://raw.githubusercontent.com/IronIdkDev/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoA/main/horario.json", "horarioteste.json");
        File file = new File("horarioteste.json");
        assertTrue(file.exists());
        file.delete();


    }
}