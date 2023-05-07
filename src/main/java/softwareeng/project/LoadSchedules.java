package softwareeng.project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*A classe LoadSchedules é uma subclasse de JFrame que representa a janela responsável por carregar horários.
*/
public class LoadSchedules extends JFrame {

    private JButton backButton;
    private static final String WEB_CAL = "webcal";
    private JButton insertSaveButton;
    private JButton insertConvertButton;
    private String url;
    JButton okButton = new JButton("OK");
    JButton okButton1 = new JButton("OK");
    private static final Logger LOGGER = Logger.getLogger("LoadSchedules");

    public LoadSchedules() {
        super("Schedule PLUS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
     * Converte uma URL para CSV e JSON, se possível.
     * @param url a URL que será convertida
     * @throws MalformedURLException se a URL fornecida estiver malformada
     * @throws IOException se ocorrer um erro ao ler ou gravar dados da URL
     */
    public void convertUrl(String url) {
        if (url.startsWith(WEB_CAL)) {
            Web web = new Web();
            String s = url.replace(WEB_CAL, "https");
            try {
                URL u = new URL(s);
                web.ReadWeb(u);
                //ALTEREI AQUI
                web.URLToList(u);
                web.URLToList(u);
            } catch (MalformedURLException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            }
        } else {
            Web web = new Web();
            try {
                URL u = new URL(url);
                web.ReadWeb(u);
                //ALTEREI AQUI
                web.URLToList(u);
            } catch (MalformedURLException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Exception occurred");
            }
        }
    }


    /**
     *Salva o conteudo do URL para um ficheiro local.
     *@param url O URL para fazer o download e salvar.
     *@throws MalformedURLException if the URL is malformed
     *@throws IOException if an I/O error occurs while reading or writing to the file
     */
    public void saveUrl(String url) {
        if (url.startsWith(WEB_CAL)) {
            Web web = new Web();
            String s = url.replace(WEB_CAL, "https");
            try {
                URL u = new URL(s);
                web.ReadWeb(u);
                web.downloadWebContent(u);
            } catch (Exception ex) { // Combinação de catch para MalformedURLException e IOException
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            }
        } else {
            Web web = new Web();
            try {
                URL u = new URL(url);
                web.ReadWeb(u);
                web.downloadWebContent(u);
            } catch (Exception ex) { // Combinação de catch para MalformedURLException e IOException
                LOGGER.log(Level.SEVERE, "Exception occurred", ex);
            }
        }
    }





    /**
     *Inicializa e configura os componentes da interface gráfica.
     */
    private void initComponents() {

        backButton = new JButton();
        insertSaveButton = new JButton("Insert & Save");
        insertConvertButton = new JButton("Insert & Convert");
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
        buttonPanel.add(insertSaveButton, gbc);
        buttonPanel.add(insertConvertButton, gbc);
        add(buttonPanel);

    }

    /**
     *Define e organiza o layout dos componentes na interface gráfica do usuário.
     */
    private void layoutComponents() {
        // Use a Box layout instead of a GridLayout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add the location label and text field to the first panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());

        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        topPanel.add(Box.createHorizontalGlue());
        add(topPanel);

        // Add the button panel to the second panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.add(Box.createHorizontalGlue());
        middlePanel.add(backButton);
        middlePanel.add(Box.createHorizontalGlue());
        middlePanel.add(insertSaveButton);
        middlePanel.add(Box.createHorizontalGlue());
        middlePanel.add(insertConvertButton);
        middlePanel.add(Box.createHorizontalGlue());
        add(middlePanel);
    }


    /**
     *Adiciona os listeners aos botões da interface gráfica.
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
                // Lógica ao clicar no botão "OK"
                url = urlTextField.getText();
                convertFrame.dispose(); // Fecha a janela de conversão
            });

            cancelButton.addActionListener(e1 -> {

                convertFrame.dispose(); // Fecha a janela de conversão
            });
        });
    }

    /**
     *Método responsável por fechar a janela atual e exibir a janela do Menu Principal quando o botão "backButton" é clicado.
     */
    private void backButtonClicked() {
        if (this.isVisible()) {
            dispose();
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        }
    }

    /**
     *Retorna o botão "OK".
     *@return o botão "OK".
     */
    public JButton getOkButton() {
        return okButton;
    }

    /**
    Retorna o botão "OK1".
    @return o botão "OK1".
    */
    public JButton getOkButton1() {
        return okButton1;
    }
    /**
     *Retorna a URL atualmente armazenada no objeto.
     *@return a URL armazenada.
     */
    public String getUrl() {
        return url;
    }



}