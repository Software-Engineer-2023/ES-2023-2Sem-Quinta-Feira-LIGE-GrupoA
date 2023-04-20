package softwareeng.project;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadSchedules extends JFrame {
    private JLabel locationLabel;
    private JTextField locationTextField;
    private JButton backButton;
    private static final Logger LOGGER = Logger.getLogger("LoadSchedules");

    public LoadSchedules() {
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
    }

    private void initComponents() {
        locationLabel = new JLabel("Schedule URL: ", SwingConstants.LEFT);
        locationLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add padding
        locationTextField = new JTextField(20);
        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int buttonSize = 48;
        backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        JPanel buttonPanel = new JPanel(new GridBagLayout()); // Create panel for button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(backButton, gbc);
        add(buttonPanel);
        add(locationLabel);
        add(locationTextField);
    }

    private void layoutComponents() {
        // Use a Box layout instead of a GridLayout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add the location label and text field to the first panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(locationLabel);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(locationTextField);
        topPanel.add(Box.createHorizontalGlue());
        add(topPanel);

        // Add the back button to the second panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalGlue());
        add(bottomPanel);
    }

    private void addListeners() {
        backButton.addActionListener(e -> backButtonClicked());
    }

    private void backButtonClicked() {
        if(this.isVisible()) {
            dispose();
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        }
    }

    // Methods for testing

}