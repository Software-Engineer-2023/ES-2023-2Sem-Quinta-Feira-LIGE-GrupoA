package softwareeng.project;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.log(Level.CONFIG, "Program started.");
            // Create the instance of the file selection window.
            //FileLocationFrame fileLocationFrame = new FileLocationFrame();
            MainMenu mainMenu = new MainMenu();
            // Make the wi  ndow visible to the user
            mainMenu.setVisible(true);
            mainMenu.getOpenSchedulesButton().addActionListener(e -> {});
            mainMenu.getConvertSchedulesButton().addActionListener(e -> {
                String location = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rageo@iscte.pt&password=zLgFKoKyGjZf1Ago80qtjmy8f0eS5uDCJQZSq2MNDGbZlTcMLw7pXDjThCYU52bDlIZsBYjNgXsIGLGUYPs8HHDfk9YnHQIZtkZXHgyBlk1nvaoTbqw4S2BG4V70CcTl";

                if(location.startsWith("webcal")) {
                    Web web = new Web();
                    String s = location.replace("webcal", "https");

                    try {
                        URL url = new URL(s);
                        web.ReadWeb(url);
                        web.URLToCSV(url);
                        web.URLToJson(url);
                    } catch (MalformedURLException ex) {
                        LOGGER.log(Level.SEVERE, "Exception occurred", ex);
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, "Exception occurred", ex);
                    }
                } else {
                    Web web = new Web();

                    try {
                        URL url = new URL(location);
                        web.ReadWeb(url);
                        web.URLToCSV(url);
                    } catch (MalformedURLException ex) {
                        LOGGER.log(Level.SEVERE, "Exception occurred", ex);
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, "Exception occurred", ex);
                    }
                }
            }
            );


            mainMenu.getLoadSchedulesButton().addActionListener(e -> {
                mainMenu.setVisible(false);
                LoadSchedules loadSchedules = new LoadSchedules();
                loadSchedules.setVisible(true);
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error starting program", e);
        }
    }
}
