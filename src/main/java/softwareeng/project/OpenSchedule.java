package softwareeng.project;

import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class OpenSchedule extends JFrame {

    private JButton selectFileButton;

    private Horario h;

    private String filename;
    private JButton backButton;

    private JPanel panel;

    private static final Logger LOGGER = Logger.getLogger("OpenSchedule");

    private static final String ERROR_MESSAGE = "Error";


    public OpenSchedule() {
        super("Open Schedule");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("icons/semana.png");
        Image scaledImage = icon.getImage().getScaledInstance(2000, 2000, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIconImage(scaledIcon.getImage());

        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
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

    private void initComponents() {
        int buttonSize = 48;
        selectFileButton = new JButton("Open Schedules");
        selectFileButton.setBorderPainted(false);
        selectFileButton.setFocusPainted(false);
        selectFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectFileButton.setIcon(new ImageIcon(new ImageIcon("icons/convertHorario.png")
                .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
        backButton = new JButton();
        backButton.setBorderPainted(false);
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

        selectFileButton.addActionListener(e -> {
            try {
                selectFileButtonClicked();
            } catch (CsvValidationException | IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
        });
        backButton.addActionListener(e -> backToMainMenu());
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

                List<Session> session = h.converFileToArray("horarioSemana.json");

                panel = new JPanel();
                String[] columns = {"Horas", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"};
                String[] rows = {"08:00-09:30", "09:30-11:00", "11:00-12:30", "13:00-14:30", "14:30-16:00", "16:00-17:30", "18:00-19:30", "19:30-21:00", "21:00-22:30"};

                Object[][] data = getTable(rows,columns,session);

                DefaultTableModel tableModel = new DefaultTableModel(data, columns){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //all cells false
                        return false;
                    }
                };
                JTable table = new JTable(tableModel);
                table.setModel(tableModel);
                table.setRowHeight(60);
                table.setRowHeight(3, 60);
                table.getTableHeader().setReorderingAllowed(false);
                tableModel.setDataVector(data, columns);
                table.setDefaultRenderer(Object.class, new CustomTable("//"));
                table.setSelectionBackground(new Color(0, 0, 0, 0));

                JScrollPane sp = new JScrollPane(table);
                add(sp);

                int numberWeeks = h.countWeeks(filename);
                JPanel buttonPanel = new JPanel();
                for (int count = 1; count <= numberWeeks; count++) {
                    JButton button = new JButton(Integer.toString(count));
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String buttonText = button.getText();
                            h.getWeek(filename, Integer.parseInt(buttonText));
                            List<Session> session = h.converFileToArray("horarioSemana.json");
                            if (session.isEmpty()) {
                                JOptionPane.showMessageDialog(buttonPanel, "Não possui aulas marcadas nessa semana");
                            } else {
                                Object dados[][] = getTable(rows,columns,session);
                                tableModel.setDataVector(dados,columns);
                            }
                        }
                    });
                    buttonPanel.add(button);
                }
                add(buttonPanel, BorderLayout.SOUTH);
            }

        }

    }


    private Object[][] getTable(String[] rows, String[] columns, List<Session> session) {
        Object[][] data = new Object[rows.length][columns.length];
        for (int i = 0; i < rows.length; i++) {
            data[i][0] = rows[i];
        }
        int linha = 0;
        int coluna = 0;
        for (Session s : session) {
            String horaI = s.getHoraInicio();
            String dia = s.getDiaSemana();
            switch (horaI) {
                case "08:00:00":
                    linha = 0;
                    break;
                case "09:30:00":
                    linha = 1;
                    break;
                case "11:00:00":
                    linha = 2;
                    break;
                case "13:00:00":
                    linha = 3;
                    break;
                case "14:30:00":
                    linha = 4;
                    break;
                case "16:00:00":
                    linha = 5;
                    break;
                case "18:00:00":
                    linha = 6;
                    break;
                case "19:30:00":
                    linha = 7;
                    break;
                case "21:00:00":
                    linha = 8;
                    break;
            }
            switch (dia) {
                case "Seg":
                    coluna = 1;
                    break;
                case "Ter":
                    coluna = 2;
                    break;
                case "Qua":
                    coluna = 3;
                    break;
                case "Qui":
                    coluna = 4;
                    break;
                case "Sex":
                    coluna = 5;
                    break;
                case "Sáb":
                    coluna = 6;
                    break;
            }

            if (data[linha][coluna] != null) {
                String a = (String) data[linha][coluna];



            } else {
                data[linha][coluna] = s.getUc();
            }


        }

        return data;
    }


    private void backToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }

}