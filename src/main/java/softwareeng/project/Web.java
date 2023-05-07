package softwareeng.project;

//TODO: Divide this class into each type of file conversion

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Web {

	//usamos a classe URL para criar uma conexão com a página web e a classe BufferedReader para ler o conteúdo da página linha por linha
	public void ReadWeb(URL url) {
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

	public String URLToList(URL url) throws IOException {

		URLConnection connection = url.openConnection();
		//lê cada linha
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
			e.printStackTrace();
		}

		if (new File("outputTemp.csv").delete()) {
		} else {

		}

		StringBuilder stringBuilder = new StringBuilder();
		for(String line : lines	)
			stringBuilder.append(line);
		String s1 = stringBuilder.toString();
		String info = s1.replaceAll( "\"\" ", "");

		StringTOJson(info);


		return info;
	}


	public void StringTOJson(String fileContent) {
		System.out.println("teste1");

		String[] subString1 = fileContent.split("SUMMARY:");
		List<Session> array = new ArrayList<>();
		for(String s : subString1){
			if(!(s.startsWith("Exame:") || s.startsWith("Teste:") || s.startsWith("Avaliação Contínua:") || s.indexOf("-") == -1)){
				//System.out.println(s);
				try {
					Session session = createSession(s);
					array.add(session);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}
		System.out.println("teste2");
		new CSVToJson().convertArrayToJson(array,fileContent);
	}

	public void StringToCsv(String fileContent){
		StringTOJson(fileContent);
		try {
			new JSonToCSV("horario.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


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

		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		Date date = sdf.parse(subString2[0]);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int  numDiaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		String diaSemana = null;
		switch (numDiaSemana){
			case 2:
				diaSemana = "Seg";
			case 3:
				diaSemana = "Ter";
			case 4:
				diaSemana = "Qua";
			case 5:
				diaSemana = "Qui";
			case 6:
				diaSemana = "Sex";
			case 7:
				diaSemana = "Sab";
		}
		String horaInicio = subString2[1];
		String horaFim = subString3[1];
		String dataAula =subString2[0];
		String salaAtribuida = "";
		if(!s.endsWith("LOCATION:")){
			 salaAtribuida =  s.substring(s.indexOf("LOCATION:") + 9, s.indexOf("\\,", s.indexOf("LOCATION:") + 9));
		}
		int lotacao = 0;

		return new Session(curso, uc, turno, turma, inscritos, diaSemana, horaInicio, horaFim, dataAula, salaAtribuida, lotacao);
	}



	//class URLToCSV recebe um URL e transforma em CSV
	public void URLToCSV(URL url) throws IOException{

	}

	public void URLToJson(URL url) throws IOException {

	}

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