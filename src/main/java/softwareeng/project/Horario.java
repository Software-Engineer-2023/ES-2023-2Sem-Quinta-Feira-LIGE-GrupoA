package softwareeng.project;

import java.util.*;
import java.util.logging.Logger;

/**
 * Classe que é responsável por criar o horário de um aluno sabendo o seu curso, turma e semana que quer.
 */
public class Horario {

    private String path;
    private static final Logger LOGGER = Logger.getLogger("Horario");

    public Horario(String path) {
        this.path = path;
    }

    /**
     * Devolve o número de semanas presentes num file. Tem como objetivo facilitar a consulta do horário semanal do utilizador
     * @param path caminho para o ficheiro
     * @return número de semanas presentes num file
     */
    public int countWeeks(String path) {

        int count = 1;
        List<Session> list = converFileToArray(path);
        list.sort(Comparator.comparing(Session::getDate));

        Date data = list.get(0).getDate();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int weekSessionDay = calendario.get(Calendar.WEEK_OF_YEAR);
        Iterator<Session> iterator = list.iterator();
        while (iterator.hasNext()) {
            Session aux = iterator.next();
            calendario.setTime(aux.getDate());
            int semanaAux = calendario.get(Calendar.WEEK_OF_YEAR);
            if(semanaAux != weekSessionDay && !aux.getDataAula().equals("") && !aux.getHoraInicio().equals("")) {
                count ++;
                calendario.setTime(aux.getDate());
                weekSessionDay = calendario.get(Calendar.WEEK_OF_YEAR);
            }

        }
        return count;
    }




    /**
     * Método que lê um ficheiro de um horário e, através do número da semana
     * mostra as aulas dessa semana
     * @param path caminho para o ficheiro que irá ser o horário
     * @param semana representa a semana que queremos ver o horário
     */
    public void getWeek(String path, int semana) {
        List<Session> list = converFileToArray(path);
        list.sort(Comparator.comparing(Session::getDate));
        int week = 7 * (semana - 1);
        Date data = list.get(0).getDate();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        calendario.add(Calendar.DAY_OF_MONTH, week);
        int ano = calendario.getTime().getYear()+1900;
        int weekSessionDay = calendario.get(Calendar.WEEK_OF_YEAR);

        List<Session> s = new ArrayList<>();

        Iterator<Session> iterator = list.iterator();
        while (iterator.hasNext()) {
            Session aux = iterator.next();
            calendario.setTime(aux.getDate());
            int semanaAux = calendario.get(Calendar.WEEK_OF_YEAR);
            if(semanaAux == weekSessionDay){
                s.add(aux);
            }else if ( semanaAux > weekSessionDay && (calendario.getTime().getYear() + 1900) == ano){
                break;
            }
        }


        CSVToJson csv = new CSVToJson();
        csv.convertArrayToJson(s, "horarioSemana.json");
    }
    /**
     * Devolve uma lista das Ucs que estão no horário
     * @return ucs que é uma lista com todas as ucs de um horario
     */
    public List<String> getUCsFromHorario() {
        List<String> ucs = new ArrayList<>();
        List<Session> horario = converFileToArray(path);
        horario.sort(Comparator.comparing(Session::getUc));
        Iterator<Session> iterator = horario.iterator();
        while (iterator.hasNext()) {
            Session aux = iterator.next();
            if (!ucs.contains(aux.getUc())) {
                ucs.add(aux.getUc());
            }
        }
        return ucs;
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
     * Recebe uma lista que contém o nome das Ucs que o utilizador escolheu.
     * Assim irá filtrar uma List com todas as aulas presentes no ficheiro.
     * No final irá criar um ficheiro com todas as aulas das Ucs recebidas.
     * @param ucs List de ucs selecionadas pelo utilizador
     */
    public void horarioFile(List<String> ucs){
        List<Session> horario = converFileToArray(path);

        Iterator<Session> iterator = horario.iterator();
        while (iterator.hasNext()) {
            Session aux = iterator.next();
            if (!ucs.contains(aux.getUc())){
                iterator.remove();
            }

        }
        horario.sort(Comparator.comparing(Session::getDate));
        CSVToJson csv = new CSVToJson();
        csv.convertArrayToJson(horario,"horarioPessoal.json");

    }




}