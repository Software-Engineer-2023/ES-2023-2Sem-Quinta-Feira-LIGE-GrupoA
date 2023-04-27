package softwareeng.project;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Classe que é responsável por criar o horário semanal de um aluno sabendo o seu curso, turma e semana que quer.
 */
public class Horario {

    private String curso;
    private int semana;
    private String turma;
    private static final Logger LOGGER = Logger.getLogger("Horario");


    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Horario(String curso, int semana, String turma){
        this.curso = curso;
        this.semana = semana;
        this.turma = turma;
    }

    /**
     * Método que converte um ficheiro csv ou json numa lista.
     * De seguida filtra as aulas que pertencem a determinado curso, turma e semana.
     * @return list com todas as UCs da semana
     */
    public List<Session> getHorario(String path){
        File file = new File(path);
        List<Session> list = new ArrayList<>();
        if(path.endsWith("csv")){
            CSVToJson csv1 = new CSVToJson();
            list = csv1.convertCSVToArray(path);
            Iterator<Session> iterator= list.iterator();
            while(iterator.hasNext()) {
                Session aux = iterator.next();
                if (!aux.getCurso().contains(curso) || !aux.getTurma().contains(turma)) {
                    iterator.remove();
                }
            }
        }else if(path.endsWith("json")){

            list = JSonToCSV.convertJsonToArray(path);
            Iterator<Session> iterator= list.iterator();
            while(iterator.hasNext()) {
                Session aux = iterator.next();
                if (!aux.getCurso().contains(curso) || !aux.getTurma().contains(turma)) {
                    iterator.remove();
                }
            }

        }else{
            LOGGER.severe("Não foi possível encontrar ficheiro");
        }

        list.sort(Comparator.comparing(Session:: getDate));
        list = getWeek(list);

    return list;
    }

    /**
     * Depois de a list ficar ordenada e, a partir do primeiro dia da list,
     * vamos calcular a x semana para obtermos as aulas dessa semana.
     * @param list que apenas contém todas as aulas de determinada semana
     * @return list com as aulas da semana
     */
    private List<Session> getWeek(List<Session> list){
        int week = 7*(semana-1);
        Date data = list.get(0).getDate();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        calendario.add(Calendar.DAY_OF_MONTH, week);
        Date nova = calendario.getTime(); //Segunda feira dessa semana
        calendario.add(Calendar.DAY_OF_MONTH, 6);
        Date fim = calendario.getTime(); // Segunda feira da próxima semana

        Iterator<Session> iterator= list.iterator();
        while(iterator.hasNext()){
            Session aux = iterator.next();
            if(aux.getDate().compareTo(nova) <= 0  || aux.getDate().compareTo(fim) >0){
                iterator.remove();
            }
        }
        return list;
    }
}
