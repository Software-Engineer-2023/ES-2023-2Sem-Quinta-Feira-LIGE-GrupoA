package softwareeng.project;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Classe que é responsável por criar o horário de um aluno sabendo o seu curso, turma e semana que quer.
 */
public class Horario {

    private Aluno a;
    private String path;
    private static final Logger LOGGER = Logger.getLogger("Horario");

    public Horario(Aluno a, String path) {
        this.a = a;
        this.path = path;
    }

    /**
     * Devolve o Aluno do horário
     * @return aluno
     */
    public Aluno getAluno() {
        return a;
    }

    /**
     * Altera o valor do Aluno do horário
     * @param a
     */
    public void setAluno(Aluno a){
        this.a = a;
    }
    /**
     * Método que converte um ficheiro csv ou json numa lista.
     * De seguida filtra as aulas que pertencem a determinado curso, turma e semana.
     *
     * @return list com todas as UCs da semana
     */
    public List<Session> getHorario() {
        List<Session> list = a.converFileToArray(path);
        Iterator<Session> iterator = list.iterator();
            while (iterator.hasNext()) {
                Session aux = iterator.next();
                if (!aux.getCurso().contains(a.getCurso()) || !aux.getTurma().contains(a.getTurma())) {
                    iterator.remove();
                }
            }
        list.sort(Comparator.comparing(Session::getDate));
        //list = getWeek(list);
        return list;
    }

    /**
     * Depois de a list ficar ordenada e, a partir do primeiro dia da list,
     * vamos calcular a x semana para obtermos as aulas dessa semana.
     *
     * @param list que apenas contém todas as aulas de determinada semana
     * @return list com as aulas da semana
     */
    public List<Session> getWeek(List<Session> list, int semana) {
        int week = 7 * (semana - 1);
        Date data = list.get(0).getDate();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        calendario.add(Calendar.DAY_OF_MONTH, week);
        Date nova = calendario.getTime(); //Segunda feira dessa semana
        calendario.add(Calendar.DAY_OF_MONTH, 6);
        Date fim = calendario.getTime(); // Segunda feira da próxima semana
        Iterator<Session> iterator = list.iterator();
        while (iterator.hasNext()) {
            Session aux = iterator.next();
            if (aux.getDate().compareTo(nova) <= 0 || aux.getDate().compareTo(fim) > 0) {
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * Método que verifica se as aulas presentes no horário de um aluno estão sobrelotadas.
     * Aquelas que não estiverem sobrelotadas são eliminadas da List
     * @return List com aulas sobrelotadas
     */
    public List<Session> getCrowedSessions() {
        List<Session> sessions = getHorario();
        Iterator<Session> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            Session aux = iterator.next();
            if (aux.getInscritos() <= aux.getLotacao()) {
                iterator.remove();
            }
        }
        for(Session s: sessions){
            System.out.println(s.toString());
        }
            return sessions;
    }

    /**
     * Método que começa por passar um ficheiro para list
     * De seguida, pega nessa list e vai analisar as datas de início de aulas, caso sejam diferentes
     * cria uma nova entry no map, caso sejam iguais adiciona ao map essa mesma aula.
     * No final devolve o map com as entrys que possuem a List associada com size() superior a 1, ou seja,
     * há mais de uma aula com o mesmo horário
     * @return map que contém as aulas sobrepostas
     */
    public Map<Date, List<Session>> getOverlappingSessions() {
        Map<Date, List<Session>> map = new HashMap<>();
        List<Session> list = getHorario();
        for (Session sessao : list) {
            Date data = sessao.getDate();
            if (!map.containsKey(data)) {
                map.put(data, new ArrayList<Session>());
            }
            map.get(data).add(sessao);
        }
        Iterator<Map.Entry<Date, List<Session>>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Date, List<Session>> entry = iter.next();
            if (entry.getValue().size() == 1) {
                iter.remove();
            }
        }
        return map;
    }
    /**
     * Método constroi um horário apenas com as aulas de uma determinada Unidade Curricular
     * @param uc representa uma unidade curricular
     * @return List com todas as aulas de uma determinada unidade curricular
     */
    public List<Session> getHorarioPorUnidadeCurricular(String uc) {
        List<Session> session = getHorario();
        Iterator<Session> iterator= session.iterator();
        while(iterator.hasNext()) {
            Session aux = iterator.next();
            if (!aux.getCurso().contains(a.getCurso()) || !aux.getTurma().contains(a.getTurma()) || !aux.getUc().contains(uc)) {
                iterator.remove();
            }
        }
        session.sort(Comparator.comparing(Session:: getDate));
        return session;
    }
}