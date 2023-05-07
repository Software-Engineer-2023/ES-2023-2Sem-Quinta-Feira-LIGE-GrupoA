package softwareeng.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * esta classe é responsavel por converter um ficheiro ics em json
 */
public class IcsToJson {
    private static final Logger LOGGER = Logger.getLogger(IcsToJson.class.getName());
    private final String filePath;

    public IcsToJson(String filePath) {
        this.filePath = filePath;
    }

    /**
     * este método é responsavel por converter um ficheiro ics em json
     * caso esta conversao seja bem sucedida é enviada uma mensagem consoante o que acontecer, dependendo se esse ficheiro ja tiver sido ou nao criado
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */

    public boolean convertFile() throws IOException, ParseException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<JsonObject> events = new ArrayList<>();
            JsonObject event = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("BEGIN:VEVENT")) {
                    event = new JsonObject();
                } else if (line.startsWith("DTSTART")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                    Date start = format.parse(line.substring(line.indexOf(':') + 1));
                    event.addProperty("start", start.getTime());
                } else if (line.startsWith("DTEND")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                    Date end = format.parse(line.substring(line.indexOf(':') + 1));
                    event.addProperty("end", end.getTime());
                } else if (line.startsWith("SUMMARY")) {
                    String summary = line.substring(line.indexOf(':') + 1);
                    event.addProperty("summary", summary);
                } else if (line.startsWith("LOCATION")) {
                    String location = line.substring(line.indexOf(':') + 1);
                    event.addProperty("location", location);
                } else if (line.startsWith("END:VEVENT")) {
                    events.add(event);
                    event = null;
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonArray = gson.toJsonTree(events).getAsJsonArray();

            String jsonFilePath = filePath.replace(".ics", ".json");
            File jsonFile = new File(jsonFilePath);
            if (jsonFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(jsonFile)) {
                    gson.toJson(jsonArray, writer);
                }
                LOGGER.log(Level.INFO, "New file created: {0}", jsonFile.getName());
                return true;
            } else {
                LOGGER.log(Level.INFO, "File already exists.");
                return false;
            }
        } catch (IOException | ParseException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }
}
