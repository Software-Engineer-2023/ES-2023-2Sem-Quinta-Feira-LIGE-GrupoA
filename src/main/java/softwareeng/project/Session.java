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


    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getDataAula() {
        return dataAula;
    }

    public void setDataAula(String dataAula) {
        this.dataAula = dataAula;
    }

    public String getSalaAtribuida() {
        return salaAtribuida;
    }

    public void setSalaAtribuida(String salaAtribuida) {
        this.salaAtribuida = salaAtribuida;
    }

    public int getLotacao() {
        return lotacao;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }


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
