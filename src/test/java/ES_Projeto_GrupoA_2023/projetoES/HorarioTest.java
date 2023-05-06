package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.Horario;
import softwareeng.project.Session;

import java.io.File;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static softwareeng.project.JSonToCSV.convertJsonToArray;

class HorarioTest {

    private final Horario horario = new Horario("horario.json");



    @Test
    void getWeek() {
        horario.getWeek("data.csv", 2);
        File file = new File("horarioSemana.json");
        assertTrue(file.exists());
        List<Session> session = convertJsonToArray(file.getName());
        assertEquals("MM", session.get(0).getCurso());

    }

    @Test
    void getOverlappingSessions() {
        Map<Date, List<Session>> map = horario.getOverlappingSessions("horarioSemana.json");
        String dateString = "Fri Sep 09 17:30:00 WEST 2022";
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        assertTrue(map.containsKey(date));




    }

    @Test
    void getUCsFromHorario() {

        List<String> ucs = horario.getUCsFromHorario();
        assertTrue(ucs.contains("Análise de Dados em Ciências Sociais - Descritiva"));

    }

    @Test
    void convertFileToArray() {
        List<Session> s = horario.converFileToArray("horario.json");
        assertEquals("ME", s.get(0).getCurso());
        assertEquals("Teoria dos Jogos e dos Contratos",s.get(0).getUc());
        assertEquals("01789TP01", s.get(0).getTurno());
        assertEquals("MEA1", s.get(0).getTurma());


    }

    @Test
    void horarioFile() {
        List<String> ucs = new ArrayList<>();
        ucs.add("Métodos Numéricos");
        ucs.add("Investimentos Financeiros (Mf)");
        ucs.add("Econometria dos Mercados Financeiros");

        horario.horarioFile(ucs);
        File file = new File("horarioPessoal.json");
        assertTrue(file.exists());

        List<Session> sessions = horario.converFileToArray(file.getName());
        for(Session aux : sessions){
            assertTrue(ucs.contains(aux.getUc()));
        }

    }
}