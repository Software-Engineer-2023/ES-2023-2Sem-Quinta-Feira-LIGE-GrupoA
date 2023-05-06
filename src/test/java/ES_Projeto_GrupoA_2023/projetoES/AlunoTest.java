package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.Aluno;
import softwareeng.project.Session;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlunoTest {

    private Aluno a = new Aluno("Miguel",99860,"LIGE","ET-A9");
    @Test
    void getNome() {
        assertEquals("Miguel", a.getNome());
    }

    @Test
    void setNome() {
        a.setNome("Alexandre");
        assertEquals("Alexandre", a.getNome());
    }

    @Test
    void getNum() {
        assertEquals(99860, a.getNum());
    }

    @Test
    void setNum() {
        a.setNum(36110);
        assertEquals(36110, a.getNum());
    }

    @Test
    void getCurso() {
        assertEquals("LIGE", a.getCurso());
    }

    @Test
    void setCurso() {
        a.setCurso("LIGE-PL");
        assertEquals("LIGE-PL", a.getCurso());
    }


    @Test
    void getTurma() {
        assertEquals("ET-A9", a.getTurma());
    }

    @Test
    void setTurma() {
        a.setTurma("ET-A12");
        assertEquals("ET-A12", a.getTurma());
    }
    @Test
    void converFileToArray(){
        List<Session> s = a.converFileToArray("horario.json");
        assertEquals("ME", s.get(0).getCurso());
        assertEquals("Teoria dos Jogos e dos Contratos",s.get(0).getUc());
        assertEquals("01789TP01", s.get(0).getTurno());
        assertEquals("MEA1", s.get(0).getTurma());
    }


}