package softwareeng.project;

import com.opencsv.exceptions.CsvValidationException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;

/**
 * Classe SelectUcs que vai permitir criar o horário do utilizador através da escolha das Ucs que seleciona
 */
public class SelectUcs extends JFrame{
	
	private JButton backButton;
	private JButton finishButton;

	private JButton selectFileButton;

	private JButton showScheduleButton;

	private JPanel checkBoxPanel;

	private JScrollPane scrollPane;
	private Horario h;

	private List<String> selectedUCs;


    private static final Logger LOGGER = Logger.getLogger("SelectUcs");

	private static final String ERROR_MESSAGE = "Error";

	/**
	 * Construtor da classe SelectUcs
	 * Define o tamanho da JFrame e inicializa os outros componentes
	 */
	public SelectUcs() {
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
          setSize(350, 150);

          // Set the layout and add components
          setLayout(new GridLayout(3, 1));
          initComponents();
          layoutComponents();
          addListeners();
          setLocationRelativeTo(null);
          
        }

	
	/**
	 * Método responsável por inicializar os botões e posicioná-los
	 */
	private void initComponents() {
		int buttonSize = 48;
		selectFileButton = new JButton("Convert your File");
		selectFileButton.setBorderPainted(false);
		selectFileButton.setFocusPainted(false);
		selectFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		selectFileButton.setIcon(new ImageIcon(new ImageIcon("icons/convertHorario.png")
				.getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

		showScheduleButton= new JButton("Show your Schedule");
		showScheduleButton.setBorderPainted(false);
		showScheduleButton.setFocusPainted(false);
		showScheduleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showScheduleButton.setIcon(new ImageIcon(new ImageIcon("icons/schedule.png")
				.getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

		showScheduleButton.setVisible(false);
		showScheduleButton.addActionListener(e -> showScheduleButton());

		 backButton = new JButton();
		    backButton.setBorderPainted(false);
		    backButton.setFocusPainted(false);
		    backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
		    backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    buttonSize = 48;
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



	}

	/**
	 * Adiciona os botoes anteriormente criados e tratados a JPanels e, por fim, adiciona-os à JFrame
	 */
	private void layoutComponents() {
	    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	    JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        //topPanel.add(checkBox);
		topPanel.add(selectFileButton);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		topPanel.add(showScheduleButton);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        
        topPanel.add(Box.createHorizontalGlue());
        add(topPanel);

	    // Add the button panel to the second panel
	    JPanel middlePanel = new JPanel();
	    middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
	    middlePanel.add(Box.createHorizontalGlue());
	    middlePanel.add(backButton);
	    //middlePanel.add(Box.createHorizontalGlue());
	    add(middlePanel);
		
		
	}

	/**
	 * Adiciona Listeners aos botões backButton e selectFileButton
	 */
	private void addListeners() {
		backButton.addActionListener(e -> backButtonClicked());
		selectFileButton.addActionListener(e -> {
			try {
				selectFileButtonClicked();
			} catch (CsvValidationException | IOException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
			}
		});
	}
//TODO terminar isto
	/**
	 * Este método permite selecionar um ficheiro do nosso computador e, de seguida, lê o seu conteúdo e mostra todas as
	 * ucs desse mesmo ficheiro com uma checkbox cada.
	 * Nesta fase, o JPanel checkBoxPanel tem um Listener associado a si para que detete sempre que uma checkbox é selecionada.
	 * Quando uma checkbox é selecionada, o botão finixhButton é mostrado na tela e assim já é possível terminar as escolhas.
	 * @throws CsvValidationException quando o ficheiro csv é inválido
	 * @throws IOException quando nao é possível ler o ficheiro selecionado
	 */
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
				h = new Horario(selectedFile.getName());
				List<String> ucs = h.getUCsFromHorario();
				int cols = 1;
				checkBoxPanel = new JPanel(new GridLayout(0, cols));
				for (String uc : ucs) {
					JCheckBox checkBox = new JCheckBox(uc);
					checkBox.addItemListener(e -> {
						boolean atLeastOneSelected = false;
						selectedUCs = new ArrayList<>();
						for (Component comp : checkBoxPanel.getComponents()) {
							if (comp instanceof JCheckBox) {
								JCheckBox cb = (JCheckBox) comp;
								if (cb.isSelected()) {
									atLeastOneSelected = true;
									selectedUCs.add(cb.getText());
								}
							}
						}
						finishButton.setVisible(atLeastOneSelected);
					});
					checkBoxPanel.add(checkBox);
				}
				scrollPane = new JScrollPane(checkBoxPanel);
				scrollPane.setViewportView(checkBoxPanel);
				add(scrollPane, BorderLayout.CENTER);
				pack();
				createFinishButton();
				add(finishButton, BorderLayout.NORTH);
				pack();
			} else {
				JOptionPane.showMessageDialog(this, "The selected file is not a CSV file or Json File.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
				selectFileButtonClicked();
			}
		}
	}

	/**
	 * Método responsável por fazer acoes quando o botão finishButton é carregado
	 */
	private void finishButtonClicked(){
		h.horarioFile(selectedUCs);
		for (Component comp : checkBoxPanel.getComponents()) {
			if (comp instanceof JCheckBox) {
				JCheckBox cb = (JCheckBox) comp;
				cb.setVisible(false);
			}
		}
		finishButton.setVisible(false);
		scrollPane.setVisible(false);
		showScheduleButton.setVisible(true);
		setSize(350, 150);
	}


	private void showScheduleButton(){

	}

	/**
	 * Cria o botão finishButton
	 */
	private void createFinishButton() {
		int buttonSize = 48;
		finishButton = new JButton("Confirm Choices");
		finishButton.setBorderPainted(false);
		finishButton.setFocusPainted(false);
		finishButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		finishButton.setIcon(new ImageIcon(new ImageIcon("icons/confirm.png")
				.getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

		finishButton.setVisible(false);
		finishButton.addActionListener(e -> finishButtonClicked());
	}


	/**
	 * Realiza as ações de quando o backButton é clicado
	 */
	private void backButtonClicked() {
        if (this.isVisible()) {
            dispose();
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
			File file = new File("horarioPessoal.json");
			if(file.exists() && !showScheduleButton.isVisible()){
				file.delete();
			}
        }
    }
}

