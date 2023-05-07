package softwareeng.project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 The LoadSchedules class is a subclass of JFrame that represents the window responsible for loading schedules.
 */
public class LoadSchedules extends JFrame {

    private static final String EXCEPTION = "Exception occurred";

    private JButton backButton;
    private static final String WEB_CAL = "webcal";
    private JButton insertSaveButton;
    private JButton insertConvertButton;
    private String url;
    JButton okButton = new JButton("OK");
    JButton okButton1 = new JButton("OK");
    private static final Logger LOGGER = Logger.getLogger("LoadSchedules");

    /**
     Constructor of the LoadSchedules screen.
     */
    public LoadSchedules() {
        super("Load Schedules");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("icons/week.png");
        Image scaledImage = icon.getImage().getScaledInstance(2000, 2000, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIconImage(scaledIcon.getImage());

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Error starting program", ex);
        }

        // Set the size of the JFrame
        setSize(500, 150);

        // Set the layout and add components
        setLayout(new GridLayout(3, 1));
        initComponents();
        layoutComponents();
        addListeners();
        setLocationRelativeTo(null);
    }

    /**
     Converts a URL to CSV and JSON, if possible.
     @param url the URL that will be converted.
     */
    public void convertUrl(String url) {
        Web web = new Web();
        if (url.startsWith(WEB_CAL)) {
            String s = url.replace(WEB_CAL, "https");
            try {
                URL u = new URL(s);
                web.readWeb(u);
                //ALTEREI AQUI
                web.URLToCsv(u);
            } catch (MalformedURLException ex) {
                LOGGER.log(Level.SEVERE, "MaformedURLException occurred", ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, EXCEPTION, ex);
            }
        } else {
            try {
                URL u = new URL(url);
                web.URLToJson(u);
                web.URLToCsv(u);
            } catch (MalformedURLException ex) {
                LOGGER.log(Level.SEVERE, EXCEPTION, ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, EXCEPTION);
            }
        }
    }


    /**
     *Saves the content of the URL to a local file.
     *@param url The URL to download and save.
     */
    public void saveUrl(String url) {
        Web web = new Web();
        if (url.startsWith(WEB_CAL)) {
            String s = url.replace(WEB_CAL, "https");
            try {
                URL u = new URL(s);
                web.readWeb(u);
                web.downloadWebContent(u);
            } catch (Exception ex) { // Combinação de catch para MalformedURLException e IOException
                LOGGER.log(Level.SEVERE, EXCEPTION, ex);
            }
        } else {
            try {
                URL u = new URL(url);
                web.readWeb(u);
                web.downloadWebContent(u);
            } catch (Exception ex) { // Combinação de catch para MalformedURLException e IOException
                LOGGER.log(Level.SEVERE, EXCEPTION, ex);
            }
        }
    }





    /**
     *Initializes and configures the graphical user interface components.
     */
    private void initComponents() {
        int buttonSize = 48;
        backButton = new JButton();
        insertSaveButton = new JButton("Insert & Save");
        insertConvertButton = new JButton("Insert & Convert");

        insertSaveButton.setBorderPainted(false);
        insertSaveButton.setFocusPainted(false);
        insertSaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insertSaveButton.setIcon(new ImageIcon(new ImageIcon("icons/save.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

        insertConvertButton.setBorderPainted(false);
        insertConvertButton.setFocusPainted(false);
        insertConvertButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insertConvertButton.setIcon(new ImageIcon(new ImageIcon("icons/convert.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
    }

    private void layoutComponents() {
        // Add the first row of buttons
        JPanel rowOnePanel = new JPanel(new GridLayout(1, 2));
        rowOnePanel.add(insertSaveButton);
        rowOnePanel.add(insertConvertButton);
        add(rowOnePanel);

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


    /**
     Adds listeners to the buttons in the graphical user interface.
     */
    private void addListeners() {
        backButton.addActionListener(e -> backButtonClicked());
        insertSaveButton.addActionListener(e -> {
            // Cria a nova janela
            JFrame saveFrame = new JFrame("Save");
            saveFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            saveFrame.setLayout(new BorderLayout());


            // Adiciona o campo de texto para o URL na nova janela
            JPanel urlPanel1 = new JPanel();
            JLabel urlLabel1 = new JLabel("URL:");
            JTextField urlTextField1 = new JTextField(20);
            urlPanel1.add(urlLabel1);
            urlPanel1.add(urlTextField1);
            saveFrame.add(urlPanel1, BorderLayout.CENTER);

            // Adiciona os botões "OK" e "Cancel" à nova janela
            JPanel buttonPanel1 = new JPanel();

            JButton cancelButton1 = new JButton("Cancel");
            buttonPanel1.add(okButton1);
            buttonPanel1.add(cancelButton1);
            saveFrame.add(buttonPanel1, BorderLayout.SOUTH);

            // Define o tamanho e a posição da nova janela
            saveFrame.setSize(300, 200);
            saveFrame.setLocationRelativeTo(null);
            saveFrame.setVisible(true);

            // Adiciona a ação dos botões "OK" e "Cancel"
            okButton1.addActionListener(e1 -> {
                // Lógica ao clicar no botão "OK"
                url = urlTextField1.getText();
                saveFrame.dispose(); // Fecha a janela de conversão
            });
        });


        insertConvertButton.addActionListener(e -> {
            // Cria a nova janela
            JFrame convertFrame = new JFrame("Convert");
            convertFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            convertFrame.setLayout(new BorderLayout());

            // Adiciona o campo de texto para o URL na nova janela
            JPanel urlPanel = new JPanel();
            JLabel urlLabel = new JLabel("URL:");
            JTextField urlTextField = new JTextField(20);
            urlPanel.add(urlLabel);
            urlPanel.add(urlTextField);
            convertFrame.add(urlPanel, BorderLayout.CENTER);

            // Adiciona os botões "OK" e "Cancel" à nova janela
            JPanel buttonPanel = new JPanel();

            JButton cancelButton = new JButton("Cancel");
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            convertFrame.add(buttonPanel, BorderLayout.SOUTH);

            // Define o tamanho e a posição da nova janela
            convertFrame.setSize(300, 200);
            convertFrame.setLocationRelativeTo(null);
            convertFrame.setVisible(true);

            // Adiciona a ação dos botões "OK" e "Cancel"
            okButton.addActionListener(e1 -> {
                url = urlTextField.getText();
                convertFrame.dispose();
            });

            cancelButton.addActionListener(e1 -> convertFrame.dispose());
        });
    }

    /**
     Method responsible for closing the current window and displaying the Main Menu window when the "backButton" is clicked.
     */
    private void backButtonClicked() {
        if (this.isVisible()) {
            dispose();
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        }
    }

    /**
     *Returns the "OK" button.
     *@return the "OK" button.
     */
    public JButton getOkButton() {
        return okButton;
    }

    /**
     Returns the "OK1" button.
     @return the "OK1" button.
    */
    public JButton getOkButton1() {
        return okButton1;
    }
    /**
     Returns the URL currently stored in the object.
     @return the stored URL.
     */
    public String getUrl() {
        return url;
    }



}