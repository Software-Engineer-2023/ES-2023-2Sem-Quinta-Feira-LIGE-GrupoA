package softwareeng.project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe Session que representa uma aula.
 */
public class Session {

    @JsonProperty("Curso")
    private String curso;

    @JsonProperty("Unidade Curricular")
    private String uc;

    @JsonProperty("Turno")
    private String turno;

    @JsonProperty("Turma")
    private String turma;

    @JsonProperty("Inscritos no turno")
    private int inscritos;

    @JsonProperty("Dia da semana")
    private String diaSemana;

    @JsonProperty("Hora início da aula")
    private String horaInicio;

    @JsonProperty("Hora fim da aula")
    private String horaFim;

    @JsonProperty("Data da aula")
    private String dataAula;

    @JsonProperty("Sala atribuída à aula")
    private String salaAtribuida;

    @JsonProperty("Lotação da sala")
    private int lotacao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date date;

    public Session(){
        curso = null;
        uc = null;
        turno = null;
        turma = null;
        inscritos =0;
        diaSemana = null;
        horaInicio = null;
        horaFim = null;
        dataAula = null;
        salaAtribuida = null;
        lotacao = 0;
    }

    /**
     * Construtor da classe Session
     * @param curso que é o curso
     * @param uc é a unidade curricula
     * @param turno é o turno da aula
     * @param turma é o conjunto de turmas que estão nessa aula
     * @param inscritos é o número de inscritos na aula
     * @param diaSemana é o dia da semana da aula
     * @param horaInicio é a hora de início da aula
     * @param horaFim é a hora em que a aula termina
     * @param dataAula é o dia em que a aula decorrerá
     * @param salaAtribuida é a sala em que a aula irá acontecer
     * @param lotacao é o número máximo de alunos que podem estar nessa sala para assistir a essa aula
     */

    public Session(String curso, String uc, String turno, String turma, int inscritos, String diaSemana, String horaInicio, String horaFim, String dataAula, String salaAtribuida, int lotacao) {
        this.curso = curso;
        this.uc = uc;
        this.turno = turno;
        this.turma = turma;
        this.inscritos = inscritos;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dataAula = dataAula;
        this.salaAtribuida = salaAtribuida;
        this.lotacao = lotacao;
    }

    public void resetStatus(){
        curso = null;
        uc = null;
        turno = null;
        turma = null;
        inscritos =0;
        diaSemana = null;
        horaInicio = null;
        horaFim = null;
        dataAula = null;
        salaAtribuida = null;
        lotacao = 0;
    }


    /**
     * Retorna o curso do objeto
     * @return uma string que indica o curso
     */
    public String getCurso() {
        return curso;
    }
    /**
     * Altera o valor de curso
     * @param curso o curso a que a aula pertence
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }
    /**
     * Retorna a unidade curricular do objeto
     * @return uma string que representa a unidade curricular
     */
    public String getUc() {
        return uc;
    }
    /**
     * Altera o valor de uc
     * @param uc a unidade curricular
     */
    public void setUc(String uc) {
        this.uc = uc;
    }
    /**
     * Retorna o turno do objeto
     * @return uma string que representa o turno
     */
    public String getTurno() {
        return turno;
    }
    /**
     * Altera o valor de turno
     * @param turno o turno da aula
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }
    /**
     * Retorna a turma do objeto
     * @return uma string que representa a turma
     */
    public String getTurma() {
        return turma;
    }
    /**
     * Altera o valor de turma
     * @param turma a turma
     */
    public void setTurma(String turma) {
        this.turma = turma;
    }
    /**
     * Retorna o valor de inscritos
     * @return um inteiro que representa os inscritos
     */
    public int getInscritos() {
        return inscritos;
    }
    /**
     * Altera o valor de inscritos
     * @param inscritos que são os inscritos
     */
    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }
    /**
     * Retorna o dia da semana
     * @return uma string que é o dia da semana
     */
    public String getDiaSemana() {
        return diaSemana;
    }
    /**
     * Altera o valor do dia da semana
     * @param diaSemana que é o dia da semana
     */
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    /**
     * Retorna a hora de início da aula
     * @return uma string que é a hora de início
     */
    public String getHoraInicio() {
        return horaInicio;
    }
    /**
     * Altera o valor da hora de início
     * @param horaInicio que é uma hora
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    /**
     * Retorna a hora de fim da aula
     * @return uma string que é a hora do fim da aula
     */
    public String getHoraFim() {
        return horaFim;
    }
    /**
     * Altera o valor da hora de fim da aula
     * @param horaFim que é uma hora
     */
    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }
    /**
     * Retorna a data da aula
     * @return uma string que é a data da aula
     */
    public String getDataAula() {
        return dataAula;
    }
    /**
     * Altera o valor da data da aula
     * @param dataAula que é uma data de uma aula
     */
    public void setDataAula(String dataAula) {
        this.dataAula = dataAula;
    }
    /**
     * Retorna a sala atribuída à aula
     * @return uma string que é a sala atribuida a uma aula
     */
    public String getSalaAtribuida() {
        return salaAtribuida;
    }
    /**
     * Altera o valor da sala atribuída
     * @param salaAtribuida que é uma sala atribuída a uma aula
     */
    public void setSalaAtribuida(String salaAtribuida) {
        this.salaAtribuida = salaAtribuida;
    }
    /**
     * Retorna a lotacao da aula
     * @return um integer que representa o número de pessoas inscritas na aula
     */
    public int getLotacao() {
        return lotacao;
    }
    /**
     * Altera o valor de lotação da aula
     * @param lotacao que é o número de pessoas na aula
     */
    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }

    /**
     * Transforma a string dataAula num objeto do tipo date
     * @return uma Date
     * @exception ParseException Quando não é possível converter a string
     */
    public Date getDate(){
        if(dataAula.equals(""))return new Date(2023,4,27);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = null;
        String aux = dataAula+" " + horaInicio;
        try {
            data = formato.parse(aux);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    public String toString() {
        return "Class{" +
                "curso='" + curso + '\'' +
                ", uc='" + uc + '\'' +
                ", turno='" + turno + '\'' +
                ", turma='" + turma + '\'' +
                ", inscritos=" + inscritos +
                ", diaSemana='" + diaSemana + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFim='" + horaFim + '\'' +
                ", dataAula='" + dataAula + '\'' +
                ", salaAtribuida='" + salaAtribuida + '\'' +
                ", lotacao=" + lotacao +
                '}';
    }
}
