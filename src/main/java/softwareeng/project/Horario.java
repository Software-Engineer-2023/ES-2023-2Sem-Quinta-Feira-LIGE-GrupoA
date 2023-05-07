package softwareeng.project;

import java.util.*;
import java.util.logging.Logger;

/**
 This is a class responsible for creating a student's timetable based on their course, class and chosen week.
 */
public class Horario {

    private String path;
    private static final Logger LOGGER = Logger.getLogger("Horario");

    public Horario(String path) {
        this.path = path;
    }

    /**
     *
     Returns the number of weeks present in a file. Its purpose is to facilitate the consultation of the user's weekly schedule.
     * @param path file path
     * @return number of weeks present in a file
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
     *Method that reads a schedule file and, using the week number,
     * * displays the classes for that week
     * * @param path file path for the schedule file
     * * @param semana represents the week number we want to see the schedule for
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
     *
     Method that starts by converting a file into a list.
     Then, it takes that list and checks the start dates of classes, and if they are different, it creates a new entry in the map.
     If they are the same, it adds that class to the map.
     In the end, it returns the map with entries that have the associated list with a size() greater than 1, meaning that there is more than one class at the same time.

     @param path the path to a file
     @return a map that contains overlapping classes
     */
    public Map<Date, List<Session>> getOverlappingSessions(String path) {
        Map<Date, List<Session>> map = new HashMap<>();
        List<Session> list = converFileToArray(path);
        for (Session sessao : list) {
            Date data = sessao.getDate();
            if (!map.containsKey(data)) {
                map.put(data, new ArrayList<>());
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


    public Map<Date, List<Session>> getSessionsMap(List<Session> list){

        Map<Date,List<Session>> map = new HashMap<>();
        for (Session sessao : list) {
            Date data = sessao.getDate();
            if (!map.containsKey(data)) {
                map.put(data, new ArrayList<>());
            }
            map.get(data).add(sessao);
        }
        return map;
    }




    /**
     * Returns a list of the Ucs present in the schedule.
     * * @return a list of Ucs present in the schedule.
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
     *
     Converts a file to a List<Session>.
     * @param path the path of the file
     * @return list with all the sessions from the file
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
     Receives a list containing the names of the Ucs that the user has chosen.
     It will filter a List with all the sessions present in the file accordingly.
     Finally, it will create a file with all the sessions of the received Ucs.
     @param ucs List of Ucs selected by the user.
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