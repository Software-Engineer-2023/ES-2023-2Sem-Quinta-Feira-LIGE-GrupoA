package softwareeng.project;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the one that executes the main graphical user interface and the runs the app.
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.log(Level.CONFIG, "Program started.");
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
            mainMenu.getOpenSchedulesButton().addActionListener(e -> {});
            mainMenu.getConvertSchedulesButton().addActionListener(e -> {
                    }
            );
            mainMenu.getLoadSchedulesButton().addActionListener(e -> {
                mainMenu.setVisible(false);
                LoadSchedules loadSchedules = new LoadSchedules();
                loadSchedules.setVisible(true);
                loadSchedules.getOkButton().addActionListener(e1 -> {
                    String url = loadSchedules.getUrl();
                    loadSchedules.convertUrl(url);
                });
                loadSchedules.getOkButton1().addActionListener(e2 -> {
                    String url1 = loadSchedules.getUrl();
                    loadSchedules.saveUrl(url1);
                });
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error starting program", e);
        }
    }
}