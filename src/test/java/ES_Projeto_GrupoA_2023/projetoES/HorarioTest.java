package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.Aluno;
import softwareeng.project.CSVToJson;
import softwareeng.project.Horario;
import softwareeng.project.Session;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classe de testes unitários da classe HorarioTest
 */
class HorarioTest {

    Horario h = new Horario(new Aluno("Miguel",99860, "ME","MEA1"),"horario.json");
    @Test
    void getHorario(){
        List<Session> list = h.getHorario();
        assertEquals("ME",h.getAluno().getCurso() );
        assertEquals("MEA1", h.getAluno().getTurma());
        assertEquals("Microeconomia (2º Ciclo)", list.get(0).getUc());
    }
    @Test
    void getCrowedSession() {
        List<Session> sessions = h.getCrowedSessions();
        assertEquals("ME", sessions.get(0).getCurso());
        assertEquals(36, sessions.get(0).getInscritos());
        assertEquals(34, sessions.get(0).getLotacao());
    }

    @Test
    void getOverlappingSessions(){
        Map<Date, List<Session>> sessions = h.getOverlappingSessions();
        Calendar calendar = new GregorianCalendar(2022, Calendar.DECEMBER, 2, 13, 0, 0);
        Date d = calendar.getTime();
        assertTrue(sessions.containsKey(d));
    }

    @Test
    void getAluno(){
        assertEquals("Miguel", h.getAluno().getNome());
    }
    @Test
    void setAluno(){
        h.setAluno(new Aluno("Fred",66445,"LIGE", "AE1"));
        assertEquals("Fred", h.getAluno().getNome());
    }

    @Test
    void getWeek(){
        List<Session> list = h.getHorario();
        List<Session> week = h.getWeek(list, 1);
        assertEquals("Crescimento Económico (2º Ciclo)", week.get(0).getUc());

    }

}