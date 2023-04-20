package softwareeng.project;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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


	//class URLToCSV recebe um URL e transforma em CSV
	public void URLToCSV(URL url) throws IOException{

		 //usa uma conexão URLConnection para baixar o conteúdo da página
		 URLConnection connection = url.openConnection();
		 //lê cada linha
		 BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		 //para analisar o conteúdo CSV e criar uma lista de objetos
		 CSVParser parser = CSVFormat.DEFAULT.parse(in);
		 List<CSVRecord> records = parser.getRecords();
		 in.close();

		 //para gravar cada registro em um arquivo CSV
		 CSVPrinter printer = new CSVPrinter(new FileWriter("output.csv"), CSVFormat.DEFAULT);
		 for (CSVRecord record : records) {
			 printer.printRecord(record);
		 }
		 printer.close();

		 //o arquivo é lido e o seu conteúdo é exibido usando um objeto
		 BufferedReader reader = new BufferedReader(new FileReader("output.csv"));
		 String line;
		 while ((line = reader.readLine()) != null) {
			 System.out.println(line);
		 }
		 reader.close();
	 }

	public void URLToJson(URL url) {

	}
}
