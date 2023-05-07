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
 This class is responsible for converting an ICS file to JSON.
 */
public class IcsToJson {
    private static final Logger LOGGER = Logger.getLogger(IcsToJson.class.getName());
    private final String filePath;

    public IcsToJson(String filePath) {
        this.filePath = filePath;
    }

    /**
     This method is responsible for converting an ics file to json. If the conversion is successful, a message will be sent depending on whether the file has already been created or not.

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
                    Date start = parseDate(line);
                    if (event != null) {
                        event.addProperty("start", start.getTime());
                    }
                } else if (line.startsWith("DTEND")) {
                    Date end = parseDate(line);
                    if (event != null) {
                        event.addProperty("end", end.getTime());
                    }
                } else if (line.startsWith("SUMMARY")) {
                    String summary = parseString(line);
                    if (event != null) {
                        event.addProperty("summary", summary);
                    }
                } else if (line.startsWith("LOCATION")) {
                    String location = parseString(line);
                    if (event != null) {
                        event.addProperty("location", location);
                    }
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
            String message = "Error while converting file: " + e.getMessage();
            LOGGER.log(Level.SEVERE, message, e);
        }
        return false;
    }

    private Date parseDate(String line) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        return format.parse(line.substring(line.indexOf(':') + 1));
    }

    private String parseString(String line) {
        return line.substring(line.indexOf(':') + 1);
    }

}
