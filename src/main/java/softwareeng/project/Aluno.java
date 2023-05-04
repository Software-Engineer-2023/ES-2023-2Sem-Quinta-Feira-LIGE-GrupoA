package softwareeng.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


/**
 * Classe que representa um Aluno
 * O Aluno é identificado pelo seu nome, número, curso a que pertence e aulas que frequenta
 */

public class Aluno {

    private String nome;
    private int num;
    private String curso;

    private String turma;
    private List<String> aulas;

    private final Logger LOGGER = Logger.getLogger("Aluno");
    /**
     * Construtor de Aluno
     * @param nome
     * @param num
     * @param curso
     * @param turma
     */
    public Aluno(String nome, int num, String curso, String turma) {
        this.nome = nome;
        this.num = num;
        this.curso = curso;
        this.turma = turma;
    }

    /**
     * Retorna o nome do Aluno
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Muda o nome do Aluno
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o número do Aluno
     * @return num
     */
    public int getNum() {
        return num;
    }

    /**
     * Muda o número do aluno
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Retorna o curso do Aluno
     * @return curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Muda o curso do Aluno
     * @param curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }


    /**
     * Converte um ficheiro para List<Session>
     * @param path caminho do ficheiro
     * @return list com todas as aulas do ficheiro
     */
    public List<Session> converFileToArray(String path){
        List<Session> list = new ArrayList<>();
        if(path.endsWith("csv")){
            CSVToJson csv1 = new CSVToJson();
            list = csv1.convertCSVToArray(path);

        }else if(path.endsWith("json")){
            list = JSonToCSV.convertJsonToArray(path);
        }else {
            LOGGER.severe("Não foi possível encontrar ficheiro");
        }
        return list;
    }

    /**
     * Retorna a turma do aluno
     * @return turma
     */
    public String getTurma() {
        return turma;
    }

    /**
     * Muda a turma do aluno
     * @param turma
     */
    public void setTurma(String turma) {
        this.turma = turma;
    }
}