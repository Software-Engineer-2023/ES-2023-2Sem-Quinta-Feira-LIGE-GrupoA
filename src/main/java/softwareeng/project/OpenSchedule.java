package softwareeng.project;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenSchedule extends JFrame {

    private JButton selectFileButton;

    private JButton backButton;

    private static final Logger LOGGER = Logger.getLogger("OpenSchedule");

    private static final String ERROR_MESSAGE = "Error";


    public OpenSchedule(){
        super("Open Schedule");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("icons/week.png");
        Image scaledImage = icon.getImage().getScaledInstance(2000, 2000, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIconImage(scaledIcon.getImage());

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Failed to set the look and feel", ex);
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
        int buttonSize = 48;
        selectFileButton = new JButton("Convert your File");
        selectFileButton.setBorderPainted(false);
        selectFileButton.setFocusPainted(false);
        selectFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectFileButton.setIcon(new ImageIcon(new ImageIcon("icons/convertHorario.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        backButton = new JButton();        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
    }

    private void layoutComponents() {
        JPanel rowOnePanel = new JPanel(new GridLayout(1, 2));
        rowOnePanel.add(selectFileButton);
        add(rowOnePanel);


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
        selectFileButton.addActionListener(e -> selectFileButtonClicked());
        backButton.addActionListener(e -> backToMainMenu());
    }


    private void selectFileButtonClicked() {
        String filename;
        Horario h;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        fileChooser.setFileFilter(csvFilter);

        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON files (*.json)", "json");
        fileChooser.setFileFilter(jsonFilter);


        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".csv") || selectedFile.getName().endsWith(".json")) {
                selectFileButton.setVisible(false);
                filename = selectedFile.getName();
                h = new Horario(filename);
                h.getWeek(selectedFile.getName(), 1);

                List<Session> session = h.converFileToArray("horarioSemana.json");

                JPanel panel = new JPanel(); // declare panel as a local variable
                JTextField textField = new JTextField(session.get(0).toString()); // Temporário
                panel.add(textField);
                add(panel);

                int numberWeeks = h.countWeeks(filename);
                JPanel buttonPanel = new JPanel();
                for (int count = 1; count <= numberWeeks; count++) {
                    JButton button = new JButton(Integer.toString(count));
                    button.addActionListener(e -> {
                        String buttonText = button.getText();
                        h.getWeek(filename, Integer.parseInt(buttonText));
                        List<Session> session1 = h.converFileToArray("horarioSemana.json");
                        if (session1.isEmpty()) {
                            JOptionPane.showMessageDialog(buttonPanel, "Não possui aulas marcadas nessa semana");
                        } else {
                            textField.setText(session1.get(0).toString());
                        }
                    });
                    buttonPanel.add(button);
                }
                add(buttonPanel);
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid file type selected", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void backToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }

}