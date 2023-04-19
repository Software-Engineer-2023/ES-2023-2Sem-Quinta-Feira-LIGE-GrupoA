package softwareeng.project;

import java.util.logging.Logger;


public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Program started with no issues.");

        // Create the instance of the file selection window.
        FileLocationFrame fileLocationFrame = new FileLocationFrame();
        // Make the window visible to the user
        fileLocationFrame.setVisible(true);
    }
}
