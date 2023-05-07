package softwareeng.project;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Esta classe representa a janela principal da aplicação Schedule PLUS e é responsável por exibir os botões que permitem ao utilizador acessar diferentes funcionalidades do programa, como abrir horários, converter horários, carregar horários e selecionar UCs. Ela também define a aparência dos botões e os ícones que os representam, além de definir as ações executadas quando cada botão é pressionado.
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
        JButton selectUcsButton = new JButton("Selecionar UCs");

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
     * Este método abre a janela de seleção de arquivos de horários quando o botão "Open Schedules" é clicado e também fecha a janela atual do menu principal.
     */
    public void openSchedules() {
        if(this.isVisible()){
            dispose();
           OpenSchedule openSchedule = new OpenSchedule();
            openSchedule.setVisible(true);
        }
    }

    /**
     *Este método abre a janela de conversão de arquivos de horários quando o botão "Convert Schedules" é clicado.
     *Ele também fecha a janela atual do menu principal.
     */
    public void convertSchedules() {
        if(this.isVisible()){
            dispose();
            ConvertSchedules convertSchedules = new ConvertSchedules();
            convertSchedules.setVisible(true);
        }
    }

    /**
     *Este método abre a janela de carregamento de horários quando o botão "Load Schedules" é clicado.
     *Ele também fecha a janela atual do menu principal.
     */
    public void loadSchedules() {
        if(this.isVisible()){
            dispose();
            LoadSchedules loadSchedules = new LoadSchedules();
            loadSchedules.setVisible(true);
        }
    }

    /**
     *Este método abre a janela para seleção de unidades curriculares (UCs) quando o botão "Selecionar UCs" é clicado.
     *Ele também fecha a janela atual do menu principal.
     */
    public void selectUcs() {
        if(this.isVisible()){
            dispose();
            SelectUcs selectUcs = new SelectUcs();
            selectUcs.setVisible(true);
        }
    }

    /**
     *Este método retorna o botão "Open Schedules" criado na classe MainMenu.
     *@return o botão "OpenSchedules"
     */
    public JButton getOpenSchedulesButton() {
        return openSchedulesButton;
    }

    /**
     *Este método retorna o botão que permite converter horários para um formato diferente.
     *@return o botão "ConvertSchedules"
     */
    public JButton getConvertSchedulesButton() {
        return convertSchedulesButton;
    }

    /**
     *Este método retorna o botão "Load Schedules" da classe atual.
     *@return o botão "LoadSchedules" da classe atual
     */
    public JButton getLoadSchedulesButton() {
        return loadSchedulesButton;
    }

}