package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe de testes unitários da classe Session
 */
class SessionTest {

    private Session s = new Session("LIGE", "ES", "B2", "C5",
            22, "Qui", "13:00:00","14:30:00" ,"27/04/2023",
            "D1.C3", 45);

    @Test
    void getCurso() {
    assertEquals("LIGE", s.getCurso());
    }

    @Test
    void setCurso() {
        s.setCurso("LIGE-PL");
        assertEquals("LIGE-PL", s.getCurso());
    }

    @Test
    void getUc() {
        assertEquals("ES", s.getUc());
    }

    @Test
    void setUc() {
        s.setUc("DIAM");
        assertEquals("DIAM", s.getUc());
    }

    @Test
    void getTurno() {
        assertEquals("B2", s.getTurno());
    }

    @Test
    void setTurno() {
        s.setTurno("B8");
        assertEquals("B8", s.getTurno());
    }

    @Test
    void getTurma() {
        assertEquals("C5", s.getTurma());
    }

    @Test
    void setTurma() {
        s.setTurma("C1-PL");
        assertEquals("C1-PL", s.getTurma());
    }

    @Test
    void getInscritos() {
        assertEquals(22, s.getInscritos());
    }

    @Test
    void setInscritos() {
        s.setInscritos(36);
        assertEquals(36, s.getInscritos());
    }

    @Test
    void getDiaSemana() {
        assertEquals("Qui", s.getDiaSemana());
    }

    @Test
    void setDiaSemana() {
        s.setDiaSemana("Sex");
        assertEquals("Sex", s.getDiaSemana());
    }

    @Test
    void getHoraInicio() {
        assertEquals("13:00:00", s.getHoraInicio());
    }

    @Test
    void setHoraInicio() {
        s.setHoraInicio("14:30:30");
        assertEquals("14:30:30", s.getHoraInicio());
    }

    @Test
    void getHoraFim() {
    assertEquals("14:30:00", s.getHoraFim());
    }

    @Test
    void setHoraFim() {
        s.setHoraFim("15:00:00");
        assertEquals("15:00:00", s.getHoraFim());
    }

    @Test
    void getDataAula() {
        assertEquals("27/04/2023", s.getDataAula());
    }

    @Test
    void setDataAula() {
        s.setDataAula("1/1/2001");
        assertEquals("1/1/2001", s.getDataAula());
    }

    @Test
    void getSalaAtribuida() {
        assertEquals("D1.C3", s.getSalaAtribuida());
    }

    @Test
    void setSalaAtribuida() {
        s.setSalaAtribuida("D2.C2");
        assertEquals("D2.C2", s.getSalaAtribuida());
    }

    @Test
    void getLotacao() {
        assertEquals(45, s.getLotacao());
    }

    @Test
    void setLotacao() {
        s.setLotacao(36);
        assertEquals(36,s.getLotacao());
    }

    @Test
    void getDate() {
        Date a = s.getDate();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String data = s.getDataAula() + " " + s.getHoraInicio();
        Date date = null;
        try {
            date = formato.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        assertEquals(date,a);
    }
}