package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.LoadSchedules;

import static org.junit.jupiter.api.Assertions.*;

class LoadSchedulesTest {
    @Test
    void testConvertUrl() {
        LoadSchedules loadSchedules = new LoadSchedules();
        String url = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=racgo@iscte.pt&password=aYBuMU6KlNFEqSfxBe2Gg8jneIh1CmclMMd7dGpFGqoz8MqfaKeQdhFbhchKJV0o7oN9N6J2lI55s1rSMZsgWnUxwyh5W96GABWAj0bpGi0T5yByZcqgVKlo1rf74oAV";
        assertDoesNotThrow(() -> loadSchedules.convertUrl(url));
    }

    @Test
    void testSaveUrl() {
        LoadSchedules loadSchedules = new LoadSchedules();
        String url = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=racgo@iscte.pt&password=aYBuMU6KlNFEqSfxBe2Gg8jneIh1CmclMMd7dGpFGqoz8MqfaKeQdhFbhchKJV0o7oN9N6J2lI55s1rSMZsgWnUxwyh5W96GABWAj0bpGi0T5yByZcqgVKlo1rf74oAV";
        assertDoesNotThrow(() -> loadSchedules.saveUrl(url));
    }

}