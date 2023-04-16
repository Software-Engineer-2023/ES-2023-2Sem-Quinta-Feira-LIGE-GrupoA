package ES_Projeto_GrupoA_2023.projetoES;

import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOGGER.info("This is an information message");

        // cria a instância da janela de seleção de arquivo
        FileLocationFrame fileLocationFrame = new FileLocationFrame();
        // torna a janela visível para o usuário
        fileLocationFrame.setVisible(true);
    }
}

class FileLocationFrame extends JFrame {
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
                dispose();
            }
        });
        add(okButton);

        pack();
        setLocationRelativeTo(null);
    }
}
