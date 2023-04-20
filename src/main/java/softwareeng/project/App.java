package softwareeng.project;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.log(Level.CONFIG, "Program started.");
            // Create the instance of the file selection window.
            FileLocationFrame fileLocationFrame = new FileLocationFrame();
            // Make the window visible to the user
            fileLocationFrame.setVisible(true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error starting program", e);
        }
    }
}