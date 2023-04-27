package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.CSVToJson;
import softwareeng.project.Horario;
import softwareeng.project.Session;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classe de testes unitários da classe HorarioTest
 */
class HorarioTest {

    private Horario h = new Horario("LIGE", 1, "C5");


    @Test
    void getCurso() {
        assertEquals("LIGE", h.getCurso());
    }

    @Test
    void setCurso() {
        h.setCurso("LIGE-PL");
        assertEquals("LIGE-PL", h.getCurso());
    }

    @Test
    void getSemanaCSV() {
        Horario horario = new Horario("LP", 1, "PA3");
        List<Session> list = horario.getHorario("data.csv");
        assertEquals("LP",list.get(0).getCurso());
        assertEquals("Psicologia Social",list.get(0).getUc());
        assertEquals("PA4; PA3; PA2; PA1",list.get(0).getTurma());

    }
    @Test
    void getSemanaJson() {
        Horario horario = new Horario("LP", 1, "PA3");
        List<Session> list = horario.getHorario("horario.json");
        assertEquals("LP",list.get(0).getCurso());
        assertEquals("Psicologia Social",list.get(0).getUc());
        assertEquals("PA4, PA3, PA2, PA1",list.get(0).getTurma());

    }


}