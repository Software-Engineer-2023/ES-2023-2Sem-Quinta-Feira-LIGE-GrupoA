package softwareeng.project;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;


public class OpenSchedule extends JFrame {
    private JButton csvButton;
    private JButton jsonButton;
    private JButton backButton;

    private static final Logger LOGGER = Logger.getLogger("OpenSchedule");


    public OpenSchedule(){
        super("Open Schedule");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
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
            @Override
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
        backButton = new JButton();        backButton.setBorderPainted(false);
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
            File selectedFile = fileChooser.getSelectedFile();

            if (selectedFile.getName().endsWith(".csv")) {
                openCSV(selectedFile);
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
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".json")) {
                openJson(selectedFile);
            } else {
                JOptionPane.showMessageDialog(this, "The selected file is not a json file.", "Error", JOptionPane.ERROR_MESSAGE);
                openJSonSchedule(); // restart the file chooser
            }
        }
    }

    private void openCSV(File csvFile){
        String csvData = null;
        JFrame csvFrame = new JFrame("content of " + csvFile);
        csvFrame.setSize(700,300);
        csvFrame.setLocationRelativeTo(null);

        try {
            csvData = new String(Files.readAllBytes(csvFile.toPath()));
        } catch (IOException e) {
            LOGGER.info("Não é possivel ler o arquivo .csv selecionado!");
        }

        JPanel panel = new JPanel(new GridLayout(1, 1));
        JTextArea  jsonText = new JTextArea(csvData);
        panel.add(jsonText);

        JScrollPane scrollPane = new JScrollPane(panel);
        csvFrame.add(scrollPane);
        csvFrame.add(panel);

        csvFrame.setVisible(true);

    }


    private void openJson(File jsonFile)  {
        String jsonData = null;
        JFrame jsonInfo = new JFrame("Content of " + jsonFile.getName());
        jsonInfo.setSize(500,500);
        jsonInfo.setLocationRelativeTo(null);

        try {
            jsonData = new String(Files.readAllBytes(jsonFile.toPath()));
        } catch (IOException e) {
            LOGGER.info("Não é possivel ler o arquivo .json selecionado!");
        }

        JPanel panel = new JPanel(new GridLayout(1, 1));
        JTextArea  jsonText = new JTextArea(jsonData);
        panel.add(jsonText);

        JScrollPane scrollPane = new JScrollPane(panel);
        jsonInfo.add(scrollPane);
        jsonInfo.add(panel);

        jsonInfo.setVisible(true);

    }

    private void backToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }

}