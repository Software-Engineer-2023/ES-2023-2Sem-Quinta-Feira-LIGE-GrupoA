package ES_Projeto_GrupoA_2023.projetoES;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.IOException;


public class FileLocationFrame extends JFrame {
    private JLabel locationLabel;
    private JTextField locationTextField;
    private JButton browseButton;
    private JButton okButton;

    public FileLocationFrame() {
        super("Selecionar localização do ficheiro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));
        initComponents();
        layoutComponents();
        addListeners();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        locationLabel = new JLabel("Localização do ficheiro:");
        locationTextField = new JTextField(20);
        browseButton = new JButton("Procurar ficheiro localmente");
        okButton = new JButton("OK");
    }

    private void layoutComponents() {
        add(locationLabel);
        add(locationTextField);
        add(browseButton);
        add(okButton);
    }

    private void addListeners() {
        browseButton.addActionListener(e -> browseButtonClicked());
        okButton.addActionListener(e -> okButtonClicked());
    }


    private void browseButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            locationTextField.setText(path);
        }
    }

    private void okButtonClicked() {
        String location = locationTextField.getText();
        // faça algo com a localização do arquivo aqui
        JOptionPane.showMessageDialog(this, "Localização do ficheiro selecionada: " + location);
        //Verifica se a String com o caminho do ficheiro inserido terminar em .csv, então converte-o em JSon
        if(location.endsWith(".csv")) {
            CSVToJSon csv = new CSVToJSon();
            csv.convertCSVToJSon(location);
        }
        if(location.endsWith(".json")) {
            try {
                JSonToCSV json = new JSonToCSV(location);
                json.convertFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao converter o arquivo JSON para CSV: " + e.getMessage());
            }
        }
        dispose();
    }

    // Métodos para testar

    public JTextField getLocationTextField() {
        return locationTextField;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public void setLocationTextField(String location) {
        locationTextField.setText(location);
    }

    public void clickBrowseButton() {
        browseButton.doClick();
    }


}