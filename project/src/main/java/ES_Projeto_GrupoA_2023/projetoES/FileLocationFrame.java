package ES_Projeto_GrupoA_2023.projetoES;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.tools.DocumentationTool.Location;

public class FileLocationFrame extends JFrame {
    private JLabel locationLabel;
    private JTextField locationTextField;
    private JButton browseButton;
    private JButton okButton;

    public FileLocationFrame() {
        super("Selecionar Localização do Arquivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        locationLabel = new JLabel("Localização do ficheiro:");
        add(locationLabel);

        locationTextField = new JTextField(20);
        add(locationTextField);

        browseButton = new JButton("Procurar ficheiro localmente");
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //procura o ficheiro
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(FileLocationFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getPath();
                    locationTextField.setText(path);
                }
            }
        });
        add(browseButton);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String location = locationTextField.getText();
                // faça algo com a localização do arquivo aqui
                JOptionPane.showMessageDialog(FileLocationFrame.this, "Localização do ficheiro selecionada: " + location);
                //Verifica se a String com o caminho do ficheiro inserido terminar em .csv, então converte-o em JSon
                if(location.endsWith(".csv")) {
                    CSVToJSon csv = new CSVToJSon();
                    csv.convertCSVToJSon(location);
                }
                
                dispose();
            }
        });
        add(okButton);

        pack();
        setLocationRelativeTo(null);
    }
}
