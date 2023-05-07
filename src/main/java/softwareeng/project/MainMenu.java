package softwareeng.project;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenu extends JFrame {
    private final JButton openSchedulesButton;
    private final JButton convertSchedulesButton;
    private final JButton loadSchedulesButton;
    private final JButton SelectUcsButton;
    private static final Logger LOGGER = Logger.getLogger("FileLocationFrame");

    public MainMenu() {
        super("Schedule PLUS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Error starting program", ex);
        }
        openSchedulesButton = new JButton("Open Schedules");
        convertSchedulesButton = new JButton("Convert Schedules");
        loadSchedulesButton = new JButton("Load Schedules");
        SelectUcsButton = new JButton ("Selecionar UCs");
        
        openSchedulesButton.setBorderPainted(false);
        convertSchedulesButton.setBorderPainted(false);
        loadSchedulesButton.setBorderPainted(false);
        SelectUcsButton.setBorderPainted(false);

        openSchedulesButton.setFocusPainted(false);
        convertSchedulesButton.setFocusPainted(false);
        loadSchedulesButton.setFocusPainted(false);
        SelectUcsButton.setFocusPainted(false);

        openSchedulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        convertSchedulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loadSchedulesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        SelectUcsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set icons for buttons
        int buttonSize = 48;
        openSchedulesButton.setIcon(new ImageIcon(new ImageIcon("icons/open.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        convertSchedulesButton.setIcon(new ImageIcon(new ImageIcon("icons/convert.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        loadSchedulesButton.setIcon(new ImageIcon(new ImageIcon("icons/load.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        SelectUcsButton.setIcon(new ImageIcon(new ImageIcon("icons/horario.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

        // Add action listeners using lambda expressions
        openSchedulesButton.addActionListener(e -> openSchedules());
        convertSchedulesButton.addActionListener(e -> convertSchedules());
        loadSchedulesButton.addActionListener(e -> loadSchedules());
        SelectUcsButton.addActionListener(e -> SelectUcs());

        JPanel panel = new JPanel();
        panel.add(openSchedulesButton);
        panel.add(convertSchedulesButton);
        panel.add(loadSchedulesButton);
        panel.add(SelectUcsButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Methods for button functionality
    public void openSchedules() {
        if(this.isVisible()){
            dispose();
           OpenSchedule openSchedule = new OpenSchedule();
            openSchedule.setVisible(true);
        }
    }

    public void convertSchedules() {
        if(this.isVisible()){
            dispose();
            ConvertSchedules convertSchedules = new ConvertSchedules();
            convertSchedules.setVisible(true);
        }
    }

    public void loadSchedules() {
        if(this.isVisible()){
            dispose();
            LoadSchedules loadSchedules = new LoadSchedules();
            loadSchedules.setVisible(true);
        }
    }
    
    public void SelectUcs() {
        if(this.isVisible()){
            dispose();
            SelectUcs selectUcs = new SelectUcs();
            selectUcs.setVisible(true);
        }
    }
    
    public JButton getOpenSchedulesButton() {
        return openSchedulesButton;
    }

    public JButton getConvertSchedulesButton() {
        return convertSchedulesButton;
    }

    public JButton getLoadSchedulesButton() {
        return loadSchedulesButton;
    }
    
    public JButton getSelectUcsButton() {
        return SelectUcsButton;
    }
}



