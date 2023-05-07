package softwareeng.project;

//TODO: Divide this class into each type of file conversion

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
*Class to read, download, and convert content from web pages.
*/
public class Web {

	private static final Logger LOGGER = Logger.getLogger("Web");

	/**
	*Reads the content of the provided URL page and prints a success message if the reading is successful.
	*/
	public void readWeb(URL url) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			in.close();
			System.out.println("URL lido com sucesso.");
		} catch (MalformedURLException e) {
			System.out.println("URL inválido: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro ao ler URL: " + e.getMessage());
		}
	}

	/**
	 * Method that reads the content from the provided URL, converts it to CSV, filters the desired information, converts it to a JSON object, and saves it to a JSON file.
	 * * @param url URL object containing the address to be read
	 * * @throws IOException if there is any I/O error during the URL reading process
	 *
	 */
	private String urlTolist(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		CSVParser parser = CSVFormat.DEFAULT.parse(in);
		List<CSVRecord> records = parser.getRecords();
		in.close();

		CSVPrinter printer = new CSVPrinter(new FileWriter("outputTemp.csv"), CSVFormat.DEFAULT);
		for (CSVRecord record : records) {
			printer.printRecord(record);
		}
		printer.close();

		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("outputTemp.csv"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("SUMMARY"))
					lines.add(line);
				else if(line.startsWith("\""))
					lines.add(line);
				else if(line.startsWith("LOCATION"))
					lines.add(line);
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Ran into an IOException: ", e);
		}

		File file = new File("outputTemp.csv");

		boolean fileDeleted = file.delete();
		if (fileDeleted) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to delete file");
		}

		StringBuilder stringBuilder = new StringBuilder();
		for(String line : lines	)
			stringBuilder.append(line);
		String s1 = stringBuilder.toString();
		String info = s1.replaceAll( "\"\" ", "");

		stringToJson(info);

		return info;
	}

	
	/**
	*Converts a time formatted string into JSON.
	 * *@param fileContent the String to be converted to JSON.
	*/
	private void stringToJson(String fileContent) {
		String[] subString1 = fileContent.split("SUMMARY:");
		List<Session> array = new ArrayList<>();
		for(String s : subString1){
			if(!(s.startsWith("Exame:") || s.startsWith("Teste:") || s.startsWith("Avaliação Contínua:") || !s.contains("-"))){
				//System.out.println(s);
				try {
					Session session = createSession(s);
					array.add(session);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}
		new CSVToJson().convertArrayToJson(array,"WebCallSchedule.json");
	}

	/**
	*Converts a time-formatted String into a CSV file.
	 *@param fileContent String containing the contents of the time-formatted file to be converted.
	 *@throws RuntimeException if an error occurs during the conversion process.
	*/
	public void stringToCsv(String fileContent){
		System.out.println("entrou aqui");
		stringToJson(fileContent);
		try {
			new JSonToCSV("WebCallSchedule.json").convertFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method that creates a Session object from the input String.
	 * * @param s String containing the class information
	 * * @return Session object created from the input String
	 * * @throws ParseException if there is any error in date/time conversion
	 */
	private Session createSession(String s) throws ParseException {
		String curso = "";
		String uc = s.substring(0, s.indexOf("-"));
		String turno = s.substring(s.indexOf("Turno: ") + 7, s.indexOf("\\n", s.indexOf("Turno: ") + 7));
		String turma = "";
		int inscritos = 0;
		String temp1 = s.substring(s.indexOf("Início: ") + 8, s.indexOf("\\n", s.indexOf("Início: ") + 8));
		String temp2 = s.substring(s.indexOf("Fim: ") + 5, s.indexOf("\\n", s.indexOf("Fim: ") + 5));
		String[] subString2 = temp1.split(" ");
		String[] subString3 = temp2.split(" ");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(subString2[0]);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int  numDiaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		String diaSemana = null;
		switch (numDiaSemana){
			case 2:
				diaSemana = "Seg";
				break;
			case 3:
				diaSemana = "Ter";
				break;
			case 4:
				diaSemana = "Qua";
				break;
			case 5:
				diaSemana = "Qui";
				break;
			case 6:
				diaSemana = "Sex";
				break;
			case 7:
				diaSemana = "Sáb";
				break;
			default:
				break;
		}
		String horaInicio = subString2[1];
		String horaFim = subString3[1];
		int year = date.getYear() + 1900;
		String dataAula =date.getDate() + "/" + date.getMonth() + "/" + year;
		String salaAtribuida = "";
		if(!s.endsWith("LOCATION:")){
			 salaAtribuida =  s.substring(s.indexOf("LOCATION:") + 9, s.indexOf("\\,", s.indexOf("LOCATION:") + 9));
		}
		int lotacao = 0;

		return new Session(curso, uc, turno, turma, inscritos, diaSemana, horaInicio+":00", horaFim+":00", dataAula.replace('-','/'), salaAtribuida, lotacao);
	}

	public void URLToJson(URL url){
		try {
			stringToJson(urlTolist(url));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void URLToCsv(URL url){
		try {
			stringToCsv(urlTolist(url));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	*Downloads the content from a web page from the provided URL and saves it to a local file named "web_content.txt".
	 * *@param url the URL of the web page from which to download the content
	 * *@throws IOException if an I/O error occurs during reading or writing of the web page content
	*/
	public void downloadWebContent(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		BufferedWriter writer = new BufferedWriter(new FileWriter("web_content.txt"));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			writer.write(inputLine);
		}
		in.close();
		writer.close();
	}



}