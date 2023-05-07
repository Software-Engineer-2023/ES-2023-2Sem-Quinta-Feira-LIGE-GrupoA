package softwareeng.project;

import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for converting ICS to JSON, ICS to CSV, JSON to CSV, and CSV to JSON
 */
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

        ImageIcon icon = new ImageIcon("icons/week.png");
        Image scaledImage = icon.getImage().getScaledInstance(2000, 2000, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIconImage(scaledIcon.getImage());

        // This piece of code is used to set the UI's look and feel to the systems default
        // making it look more modern and updated.
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Failed to set the look and feel", ex);
        }


        setSize(500, 200);
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

    /**
     * Method responsible for initializing the UI components
     */
    private void initComponents() {
        csvToJsonButton = new JButton("CSV to JSON");
        jsonToCsvButton = new JButton("JSON to CSV");
        icsToJsonButton = new JButton("ICS to Json");
        icsToCsvButton = new JButton("ICS to CSV");
        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int buttonSize = 48;
        backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
    }

    /**
     * Method responsible for locating the JFrame components
     */
    private void layoutComponents() {
        // Adds the first row of buttons
        JPanel rowOnePanel = new JPanel(new GridLayout(1, 2));
        rowOnePanel.add(csvToJsonButton);
        rowOnePanel.add(jsonToCsvButton);
        add(rowOnePanel);

        // Adds the second row of buttons
        JPanel rowTwoPanel = new JPanel(new GridLayout(1, 2));
        rowTwoPanel.add(icsToJsonButton);
        rowTwoPanel.add(icsToCsvButton);
        add(rowTwoPanel);

        // Adds the third row with the back button
        JPanel rowThreePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rowThreePanel.add(backButton, gbc);
        add(rowThreePanel);
    }

    /**
     * Method responsible for adding each action to its button
     */
    private void addListeners() {
        csvToJsonButton.addActionListener(e -> {
            try {
                csvToJsonButtonClicked();
            } catch (CsvValidationException | IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
        });
        jsonToCsvButton.addActionListener(e -> jsonToCsvButtonClicked());
        icsToJsonButton.addActionListener(e -> icsToJsonButtonClicked());
        icsToCsvButton.addActionListener(e -> {
            try {
                icsToCsvButtonClicked();
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "There was a problem when clicking the conversion button: ", ex);
            }
        });
        backButton.addActionListener(e -> backToMainMenu());
    }

    /**
     * Method responsible for adding the action to the conversion of csv to json
     */
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

    /**
     * Method responsible for adding the action to the conversion of json to csv
     */

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

    /**
<<<<<<< Updated upstream
     * metodo responsavel por adicionar a ação de converter um ficheiro ics em csv
     * @throws IOException
=======
     *  Method responsible for adding the action to the conversion of ics to csv
>>>>>>> Stashed changes
     */

    private void icsToCsvButtonClicked() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("ICS files", "ics"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".ics")) {
                convertIcsToCSV(path);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not an ICS file.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
                icsToCsvButtonClicked(); // restart the file chooser
            }
        }
    }

    /**
     *  Method responsible for adding the action to the conversion of ics to json
     */
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

    /**
     *  Action to be performed when button "CSV to JSON" clicked
     */
    private void convertCSVToJson(String filelocation) {
        CSVToJson csv = new CSVToJson();
        boolean success = csv.convertCSVToJson(filelocation, "schedule.json");
        if(success){
            JOptionPane.showMessageDialog(this, "CSV file converted to JSON successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "There was an error converting the CSV file to JSON.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
<<<<<<< Updated upstream
     * metodo responsavel por adicionar a ação de converter um ficheiro ics em csv
     * @param filelocation
=======
     *  Action to be performed when button "ics to CSV" clicked
>>>>>>> Stashed changes
     */
    private void convertIcsToCSV(String filelocation) {
        try {
            IcsToCSV ics = new IcsToCSV(filelocation);
            boolean success = ics.convertFile();
            if(success){
                JOptionPane.showMessageDialog(this, "ICS file converted to CSV successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "There was an error converting the ICS file to CSV.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "There was an error converting the ICS file to CSV: " + e.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
<<<<<<< Updated upstream
     * metodo responsavel por adicionar a ação de converter um ficheiro json em csv
     * @param filelocation
=======
     *   Action to be performed when button "JSON to CSV" clicked
>>>>>>> Stashed changes
     */
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


    /**
     * Action to be performed when button "ics to JSON" clicked
     */
    private void convertICSToJson(String fileLocation) {
        try {
            IcsToJson ics = new IcsToJson(fileLocation);

            boolean success = ics.convertFile();
            if(success){
                JOptionPane.showMessageDialog(this, "ICS file converted to JSON successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "There was an error converting the ICS file to JSON.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "There was an error converting the ICS file to json: " + e.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            LOGGER.log(Level.WARNING, "There was a problem when clicking the conversion button: ", e);
        }

    }


    /**
     * Method responsible for allowing the user to go back to main menu
     */
    private void backToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }
}