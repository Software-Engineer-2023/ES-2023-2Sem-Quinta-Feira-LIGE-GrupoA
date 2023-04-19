package ES_Projeto_GrupoA_2023.projetoES;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class IcsToJsonConverter {
    private final String filename;
    private List<String> lines;

    public IcsToJsonConverter(String filename) {
        this.filename = filename;
        this.lines = new ArrayList<>();
    }

    public void readIcsFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filename));
        String line;
        while ((line = reader.readLine()) != null) {
            this.lines.add(line);
        }
        reader.close();
    }

    public String convertToJson() {
        JSONObject event = null;
        JSONArray events = new JSONArray();
        boolean eventStarted = false;
        for (String line : this.lines) {
            if (!eventStarted) {
                if (line.startsWith("BEGIN:VEVENT")) {
                    event = new JSONObject();
                    eventStarted = true;
                }
            } else {
                if (line.startsWith("END:VEVENT")) {
                    events.put(event);
                    eventStarted = false;
                } else {
                    String[] splitLine = line.split(":");
                    if (splitLine.length == 2) {
                        event.put(splitLine[0], splitLine[1]);
                    }
                }
            }
        }
        return events.toString();
    }
}

