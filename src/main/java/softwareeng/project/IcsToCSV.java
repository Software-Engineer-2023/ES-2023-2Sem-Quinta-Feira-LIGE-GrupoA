package softwareeng.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IcsToCSV {
    private String filePath;

    public IcsToCSV(String filePath) {
        this.filePath = filePath;
    }

    public boolean convertFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             FileWriter writer = new FileWriter(filePath.replace(".ics", ".csv"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("BEGIN:VEVENT")) {
                    writer.write("START,END,SUMMARY,LOCATION\n");
                } else if (line.startsWith("DTSTART")) {
                    String start = line.substring(line.indexOf(':') + 1);
                    String end = reader.readLine().substring(line.indexOf(':') + 1);
                    writer.write(start + "," + end + ",");
                } else if (line.startsWith("SUMMARY")) {
                    String summary = line.substring(line.indexOf(':') + 1);
                    String location = reader.readLine().substring(line.indexOf(':') + 1);
                    writer.write(summary + "," + location + "\n");
                }
            }

            return true;
        }
    }

}
