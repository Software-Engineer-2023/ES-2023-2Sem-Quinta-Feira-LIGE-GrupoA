package softwareeng.project;

import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConvertSchedules extends JFrame {
    private JButton csvToJsonButton;
    private JButton jsonToCsvButton;
    private JButton icsToJsonButton;
    private JButton icsToCsvButton;
    private JButton backButton;
    private static final Logger LOGGER = Logger.getLogger("FileLocationFrame");
    private static final String ERROR_MESSAGE = "Error";

    public ConvertSchedules() {
        super("Convert Schedules");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.WARNING, "Failed to set look and feel", ex);
        }

        // Set the size of the JFrame
        setSize(500, 200);

        // Set the layout and add components
        setLayout(new GridLayout(3, 1));
        initComponents();
        layoutComponents();
        addListeners();
        setLocationRelativeTo(null);

        // Add a window listener to dispose the JFrame on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void initComponents() {
        csvToJsonButton = new JButton("CSV to Json");
        jsonToCsvButton = new JButton("Json to CSV");
        icsToJsonButton = new JButton("ics to Json");
        icsToCsvButton = new JButton("ics to CSV");
        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int buttonSize = 48;
        backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
    }

    private void layoutComponents() {
        // Add the first row of buttons
        JPanel rowOnePanel = new JPanel(new GridLayout(1, 2));
        rowOnePanel.add(csvToJsonButton);
        rowOnePanel.add(jsonToCsvButton);
        add(rowOnePanel);

        // Add the second row of buttons
        JPanel rowTwoPanel = new JPanel(new GridLayout(1, 2));
        rowTwoPanel.add(icsToJsonButton);
        rowTwoPanel.add(icsToCsvButton);
        add(rowTwoPanel);

        // Add the third row with the back button
        JPanel rowThreePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rowThreePanel.add(backButton, gbc);
        add(rowThreePanel);
    }

    private void addListeners() {
        csvToJsonButton.addActionListener(e -> {
            try {
                csvToJsonButtonClicked();
            } catch (CsvValidationException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        jsonToCsvButton.addActionListener(e -> jsonToCsvButtonClicked());
        icsToJsonButton.addActionListener(e -> icsToJsonButtonClicked());
        icsToCsvButton.addActionListener(e -> icsToCsvButtonClicked());
        backButton.addActionListener(e -> backToMainMenu());
    }


    private void csvToJsonButtonClicked() throws CsvValidationException, IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".csv")) {
                convertCSVToJson(path);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not a CSV file.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
                csvToJsonButtonClicked(); // restart the file chooser
            }
        }
    }

    private void jsonToCsvButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".json")) {
                convertJsonToCSV(path);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not a JSON file.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
                jsonToCsvButtonClicked(); // restart the file chooser
            }
        }
    }

    private void icsToCsvButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("ICS files", "ics"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".ics")) {
                convertICSToCSV(path);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not an ICS file.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
                icsToCsvButtonClicked(); // restart the file chooser
            }
        }
    }

    private void icsToJsonButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("ICS files", "ics"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".ics")) {
                convertICSToJson(path);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not an ICS file.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
                icsToJsonButtonClicked(); // restart the file chooser
            }
        }
    }


    private void convertCSVToJson(String filelocation) throws CsvValidationException, IOException {
        CSVToJson csv = new CSVToJson();
        boolean success = csv.convertCSVToJson(filelocation);
        if(success){
            JOptionPane.showMessageDialog(this, "CSV file converted to JSON successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "There was an error converting the CSV file to JSON.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void convertJsonToCSV(String filelocation) {
        try {
            JSonToCSV json = new JSonToCSV(filelocation);
            boolean success = json.convertFile();
            if(success){
                JOptionPane.showMessageDialog(this, "JSON file converted to CSV successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "There was an error converting the JSON file to CSV.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "There was an error converting the JSON file to CSV: " + e.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void convertICSToCSV(String fileLocation) {
        String location = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rageo@iscte.pt&password=zLgFKoKyGjZf1Ago80qtjmy8f0eS5uDCJQZSq2MNDGbZlTcMLw7pXDjThCYU52bDlIZsBYjNgXsIGLGUYPs8HHDfk9YnHQIZtkZXHgyBlk1nvaoTbqw4S2BG4V70CcTl";

        Web web = new Web();
        if(location.startsWith("webcal")) {
            String s = location.replace("webcal", "https");

            try {
                URL url = new URL(s);
                web.ReadWeb(url);
                web.URLToCSV(url);
            } catch (MalformedURLException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            }
        } else {

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

    private void convertICSToJson(String fileLocation) {
        String location = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rageo@iscte.pt&password=zLgFKoKyGjZf1Ago80qtjmy8f0eS5uDCJQZSq2MNDGbZlTcMLw7pXDjThCYU52bDlIZsBYjNgXsIGLGUYPs8HHDfk9YnHQIZtkZXHgyBlk1nvaoTbqw4S2BG4V70CcTl";

        if(location.startsWith("webcal")) {
            Web web = new Web();
            String s = location.replace("webcal", "https");

            try {
                URL url = new URL(s);
                web.ReadWeb(url);
                web.URLToJson(url);
            } catch (MalformedURLException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            }
        }
    }



    private void backToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }
}