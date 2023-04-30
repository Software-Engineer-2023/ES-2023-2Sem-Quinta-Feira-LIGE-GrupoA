package softwareeng.project;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.file.Files;
import java.util.logging.Logger;



public class OpenSchedule extends JFrame {

    private final File htmlFile = new File("pageJson.html");
    private JButton csvButton;
    private JButton jsonButton;
    private JButton backButton;
    private static final Logger LOGGER = Logger.getLogger(OpenSchedule.class.getName());


    public OpenSchedule(){
        super("Open Schedule");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.severe("Error setting the look and feel: " + ex.getMessage());
        }

        setSize(350, 150);

        // Set the layout and add components
        setLayout(new GridLayout(2, 1));
        setLocationRelativeTo(null);
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

    private void  initComponents(){
        csvButton = new JButton("Open CSV File");
        jsonButton = new JButton("Open Json File");
        csvButton.setBorderPainted(false);
        jsonButton.setBorderPainted(false);
        csvButton.setFocusPainted(false);
        jsonButton.setFocusPainted(false);
        csvButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jsonButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        int buttonSize = 48;
        csvButton.setIcon(new ImageIcon(new ImageIcon("icons/CSV.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        jsonButton.setIcon(new ImageIcon(new ImageIcon("icons/Json.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
    }

    private void layoutComponents() {
        JPanel rowOnePanel = new JPanel(new GridLayout(1, 2));
        rowOnePanel.add(csvButton);
        rowOnePanel.add(csvButton);
        add(rowOnePanel);

        JPanel rowTwoPanel = new JPanel(new GridLayout(1, 2));
        rowTwoPanel.add(jsonButton);
        rowTwoPanel.add(jsonButton);
        add(rowTwoPanel);

        JPanel rowThreePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rowThreePanel.add(backButton, gbc);
        add(rowThreePanel);
    }

    private void addListeners() {
        csvButton.addActionListener(e -> openCSVSchedule());
        jsonButton.addActionListener(e -> openJSonSchedule());
        backButton.addActionListener(e -> backToMainMenu());
    }

    private void openCSVSchedule(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();

            if (selectedFile.getName().endsWith(".csv")) {
                    openCSVOnHtml(selectedFile);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not a CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
                openCSVSchedule(); // restart the file chooser
            }
        }
    }

    private void openJSonSchedule(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Json files", "json"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".json")) {
                    openJsonOnHtml(selectedFile);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not a json file.", "Error", JOptionPane.ERROR_MESSAGE);
                openJSonSchedule(); // restart the file chooser
            }
        }
    }

    private void openCSVOnHtml(File csvFile){
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createConection(){

        try {
            ServerSocket serverSocket = new ServerSocket(63342);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void openJsonOnHtml(File jsonFile)  {

        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(jsonFile.toPath()));
            String htmlString = new String(Files.readAllBytes(htmlFile.toPath()));
            htmlString = htmlString.replace("$jsonData", jsonString);
            File tempHtmlFile = File.createTempFile("schedule", ".html", new File(System.getProperty("user.home")));
            Files.write(tempHtmlFile.toPath(), htmlString.getBytes());
            Desktop.getDesktop().browse(URI.create("localhost:63342/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoA/pageJson.html"));
            System.out.println("teste123");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void backToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }

}
