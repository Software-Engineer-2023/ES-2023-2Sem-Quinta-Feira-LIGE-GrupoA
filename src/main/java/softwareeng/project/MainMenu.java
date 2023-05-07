package softwareeng.project;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 This class represents the main menu of our app.
 */

public class MainMenu extends JFrame {
    private final JButton openSchedulesButton;
    private final JButton convertSchedulesButton;
    private final JButton loadSchedulesButton;

    private static final Logger LOGGER = Logger.getLogger("FileLocationFrame");

    public MainMenu() {
        super("Schedule PLUS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("icons/week.png");
        Image scaledImage = icon.getImage().getScaledInstance(2000, 2000, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIconImage(scaledIcon.getImage());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Error starting program", ex);
        }
        openSchedulesButton = new JButton("Open Schedules");
        convertSchedulesButton = new JButton("Convert Schedules");
        loadSchedulesButton = new JButton("Load Schedules");
        JButton selectUcsButton = new JButton("Select UCs");
        openSchedulesButton.setBorderPainted(false);
        convertSchedulesButton.setBorderPainted(false);
        loadSchedulesButton.setBorderPainted(false);
        selectUcsButton.setBorderPainted(false);

        openSchedulesButton.setFocusPainted(false);
        convertSchedulesButton.setFocusPainted(false);
        loadSchedulesButton.setFocusPainted(false);
        selectUcsButton.setFocusPainted(false);

        openSchedulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        convertSchedulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loadSchedulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectUcsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set icons for buttons
        int buttonSize = 48;
        openSchedulesButton.setIcon(new ImageIcon(new ImageIcon("icons/open.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        convertSchedulesButton.setIcon(new ImageIcon(new ImageIcon("icons/convert.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        loadSchedulesButton.setIcon(new ImageIcon(new ImageIcon("icons/load.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        selectUcsButton.setIcon(new ImageIcon(new ImageIcon("icons/horario.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

        // Add action listeners using lambda expressions
        openSchedulesButton.addActionListener(e -> openSchedules());
        convertSchedulesButton.addActionListener(e -> convertSchedules());
        loadSchedulesButton.addActionListener(e -> loadSchedules());
        selectUcsButton.addActionListener(e -> selectUcs());

        JPanel panel = new JPanel();
        panel.add(openSchedulesButton);
        panel.add(convertSchedulesButton);
        panel.add(loadSchedulesButton);
        panel.add(selectUcsButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // Methods for button functionality
    /**
     This method opens the schedule file selection window when the "Open Schedules"
     button is clicked and also closes the current main menu window.     */
    public void openSchedules() {
        if(this.isVisible()){
            dispose();
           OpenSchedule openSchedule = new OpenSchedule();
            openSchedule.setVisible(true);
        }
    }

    /**
     This method opens the schedule file conversion window when the "Convert Schedules" button is clicked.
     It also closes the current main menu window.
     */
    public void convertSchedules() {
        if(this.isVisible()){
            dispose();
            ConvertSchedules convertSchedules = new ConvertSchedules();
            convertSchedules.setVisible(true);
        }
    }

    /**
     This method opens the schedule loading window when the "Load Schedules" button is clicked.
     *It also closes the current main menu window.
     */
    public void loadSchedules() {
        if(this.isVisible()){
            dispose();
            LoadSchedules loadSchedules = new LoadSchedules();
            loadSchedules.setVisible(true);
        }
    }

    /**
     This method opens the window for selecting academic courses (UCs) when the "Select UCs" button is clicked.
     It also closes the current main menu window.
     */
    public void selectUcs() {
        if(this.isVisible()){
            dispose();
            SelectUcs selectUcs = new SelectUcs();
            selectUcs.setVisible(true);
        }
    }

    /**
     *This method returns the "Open Schedules" button created in the MainMenu class.
     *@return the "Open Schedules" button.
     */
    public JButton getOpenSchedulesButton() {
        return openSchedulesButton;
    }

    /**
     *This method returns the button that allows converting schedules to a different format.
     *@return the "ConvertSchedules" button.
     */
    public JButton getConvertSchedulesButton() {
        return convertSchedulesButton;
    }

    /**
     *This method returns the "Load Schedules" button of the current class.
     *@return the "LoadSchedules" button of the current class.
     */
    public JButton getLoadSchedulesButton() {
        return loadSchedulesButton;
    }

}