package softwareeng.project;

// TODO: Delete this Class. All elements must be moved to other classes

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLocationFrame extends JFrame {
    private JLabel locationLabel;
    private JTextField locationTextField;
    private JButton browseButton;
    private JButton okButton;
    private static final Logger LOGGER = Logger.getLogger("FileLocationFrame");

    public FileLocationFrame() {
        super("Schedule PLUS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Error starting program", ex);
        }

        // Set the size of the JFrame
        setSize(500, 150);

        // Set the layout and add components
        setLayout(new GridLayout(2, 1));
        initComponents();
        layoutComponents();
        addListeners();
        setLocationRelativeTo(null);

        // Add a window listener to dispose the JFrame on close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void initComponents() {
        locationLabel = new JLabel("Schedule URL: ", SwingConstants.CENTER);
        locationTextField = new JTextField(20);
        browseButton = new JButton("Search for file locally");
        okButton = new JButton("OK");
        okButton.setBackground(Color.decode("#5cb85c"));
    }

    private void layoutComponents() {
        add(locationLabel);
        add(locationTextField);
        add(browseButton);
        add(okButton);
    }

    private void addListeners() {
        browseButton.addActionListener(e -> browseButtonClicked());
        okButton.addActionListener(e -> okButtonClicked());
    }


    private void browseButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            locationTextField.setText(path);
        }
    }

    private void okButtonClicked() {
        String filelocation = locationTextField.getText();
        switch (getFileExtension(filelocation)) {
            case "csv":
                convertCSVToJson(filelocation);
                break;
            case "json":
                convertJsonToCSV(filelocation);
                break;
            case "webcal":
                convertWebcalToCSV(filelocation);
                break;
            case "https":
                convertHttpsToCSV(filelocation);
                break;
            default:
                if (filelocation.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No URL inserted! Please try again.");
                } else {
                    JOptionPane.showMessageDialog(this, "Unsupported file extension! Please provide a CSV, JSON or Webcal file.");
                }
                break;
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1) {
            return "";
        }
        return filename.substring(extensionIndex + 1);
    }

    private void convertCSVToJson(String filelocation){
        CSVToJson csv = new CSVToJson();
        csv.convertCSVToJson(filelocation);
    }

    private void convertJsonToCSV(String filelocation) {
        try {
            JSonToCSV json = new JSonToCSV(filelocation);
            json.convertFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "There was an error converting the JSON file to CSV: " + e.getMessage());
        }
    }

    private void convertWebcalToCSV(String filelocation) {
        Web web = new Web();
        // Makes it possible to have an .ics file
        String s = filelocation.replace("webcal", "https");
        try {
            URL url = new URL(s);
            web.ReadWeb(url);
            web.URLToCSV(url);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred", e);
        }
    }

    private void convertHttpsToCSV(String filelocation) {
        Web web = new Web();
        try {
            URL url = new URL(filelocation);
            web.ReadWeb(url);
            web.URLToCSV(url);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred", e);
        }
    }


    // Methods for testing

    public JTextField getLocationTextField() {
        return locationTextField;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public void setLocationTextField(String location) {
        locationTextField.setText(location);
    }

    public void clickBrowseButton() {
        browseButton.doClick();
    }
}