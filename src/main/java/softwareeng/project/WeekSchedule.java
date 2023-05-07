package softwareeng.project;

import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class WeekSchedule extends JFrame {

    private JButton backButton;

    private JButton selectFileButton;

    private Horario h;

    private String filename;

    private static final Logger LOGGER = Logger.getLogger("WeekSchedule");

    private static final String ERROR_MESSAGE = "Error";

    public WeekSchedule(){
        super("Schedule PLUS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Error starting program", ex);
        }

        // Set the size of the JFrame
        setSize(350, 150);

        // Set the layout and add components
        setLayout(new GridLayout(3, 1));
        initComponents();
        layoutComponents();
        addListeners();
        setLocationRelativeTo(null);

    }

    private void initComponents() {
        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int buttonSize = 48;
        backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        selectFileButton = new JButton("Convert your File");
        selectFileButton.setBorderPainted(false);
        selectFileButton.setFocusPainted(false);
        selectFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectFileButton.setIcon(new ImageIcon(new ImageIcon("icons/convertHorario.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));


        JPanel buttonPanel = new JPanel(new GridBagLayout()); // Create panel for button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(backButton, gbc);
        add(buttonPanel);
    }
    private void layoutComponents() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(selectFileButton);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        topPanel.add(Box.createHorizontalGlue());
        add(topPanel);


        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.add(Box.createHorizontalGlue());
        middlePanel.add(backButton);
        add(middlePanel);
    }
    private void addListeners() {
        backButton.addActionListener(e -> backButtonClicked());
        selectFileButton.addActionListener(e -> {
            try {
                selectFileButtonClicked();
            } catch (CsvValidationException | IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Realiza as ações de quando o backButton é clicado
     */
    private void backButtonClicked() {
        if (this.isVisible()) {
            dispose();
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
            }
        }
    private void selectFileButtonClicked() throws CsvValidationException, IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        fileChooser.setFileFilter(csvFilter);

        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON files (*.json)", "json");
        fileChooser.setFileFilter(jsonFilter);


        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".csv") || selectedFile.getName().endsWith(".json")) {
                selectFileButton.setVisible(false);
                filename = selectedFile.getName();
                h = new Horario(filename);
                h.getWeek(selectedFile.getName(), 1);
                //Exibir horário

                int numberWeeks = h.countWeeks(filename);
                JPanel buttonPanel = new JPanel();
                for (int count = 1; count <= numberWeeks; count++) {
                    JButton button = new JButton(Integer.toString(count));
                    buttonPanel.add(button);
                }
                add(buttonPanel, BorderLayout.SOUTH);
                }

            }
        }

}


