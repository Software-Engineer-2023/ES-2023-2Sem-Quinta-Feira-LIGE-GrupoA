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
*Classe para ler, baixar e converter conteúdo de páginas web.
*/
public class Web {

	private static final Logger LOGGER = Logger.getLogger("Web");

	/**
	*Lê o conteúdo da página da URL fornecida e imprime uma mensagem de sucesso se a leitura for bem sucedida.
	*@param url a URL da página a ser lida
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
	 * Método que lê o conteúdo da URL passada, converte para CSV, filtra as informações desejadas,converte para um objeto JSON e salva em um arquivo JSON.
	 * @param url objeto URL contendo o endereço a ser lido
	 * @throws IOException se houver algum erro de I/O durante a leitura da URL
	 */
	public String urlTolist(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		//para analisar o conteúdo CSV e criar uma lista de objetos
		CSVParser parser = CSVFormat.DEFAULT.parse(in);
		List<CSVRecord> records = parser.getRecords();
		in.close();

		//para gravar cada registro em um arquivo CSV
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
	*Converte uma String no formato de horário em JSON.
	*@param fileContent a String a ser convertida em JSON.
	*/
	public void stringToJson(String fileContent) {
		System.out.println("teste1");

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
		System.out.println("Tamanho do session " + array.size());
		System.out.println("teste2");
		new CSVToJson().convertArrayToJson(array,"horarioWebcall.json");
	}

	/**
	*Converte uma String em formato de arquivo Horario para um arquivo CSV.
	*@param fileContent String contendo o conteúdo do arquivo Horario a ser convertido.
	*@throws RuntimeException caso ocorra um erro durante a conversão.
	*/
	public void stringToCsv(String fileContent){
		stringToJson(fileContent);
		try {
			new JSonToCSV("horario.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Método que cria um objeto Session a partir da String de entrada.
	 * @param s String contendo as informações da aula
	 * @return objeto Session criado a partir da String de entrada
	 * @throws ParseException se houver algum erro na conversão de data/hora
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

	/**
	*Faz o download do conteúdo de uma página web a partir da URL fornecida e salva-o em um arquivo local com o nome "web_content.txt".
	*@param url a URL da página web de onde baixar o conteúdo
	*@throws IOException se ocorrer um erro de I/O durante a leitura ou gravação do conteúdo da página web
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