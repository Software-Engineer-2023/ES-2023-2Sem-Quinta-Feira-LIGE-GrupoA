package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import softwareeng.project.Web;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Classe de testes unitários da classe Web
 */
class WebTest {

    @Test
    void testReadWebWithValidUrl() {
        Web web = new Web();
        URL url;
        try {
            url = new URL("https://www.example.com");
            Assertions.assertDoesNotThrow(() -> web.ReadWeb(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadWebWithInvalidUrl() {
        Web web = new Web();
        URL url;
        try {
            url = new URL("invalidurl");
            Exception exception = Assertions.assertThrows(Exception.class, () -> web.ReadWeb(url));
            Assertions.assertEquals("URL inválido: no protocol: invalidurl", exception.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    
    @Test
    void testURLToCSVWithInvalidUrl() {
        Web web = new Web();
        URL url;
        try {
            url = new URL("invalidurl");
            Exception exception = Assertions.assertThrows(Exception.class, () -> web.URLToCSV(url));
            Assertions.assertEquals("unknown protocol: invalidurl", exception.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    @Test
    void testURLToJsonWithInvalidUrl() {
        Web web = new Web();
        URL url;
        try {
            url = new URL("invalidurl");
            Exception exception = Assertions.assertThrows(Exception.class, () -> web.URLToJson(url));
            Assertions.assertEquals("unknown protocol: invalidurl", exception.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDownloadWebContentWithValidUrlContainingHTML() {
        Web web = new Web();
        URL url;
        try {
            url = new URL("https://example.com");
            Assertions.assertDoesNotThrow(() -> web.downloadWebContent(url));
            // TODO: Add assertions to verify if HTML content was correctly written to file
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDownloadWebContentWithInvalidUrl() {
        Web web = new Web();
        URL url;
        try {
            url = new URL("invalidurl");
            Exception exception = Assertions.assertThrows(Exception.class, () -> web.downloadWebContent(url));
            Assertions.assertEquals("unknown protocol: invalidurl", exception.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
