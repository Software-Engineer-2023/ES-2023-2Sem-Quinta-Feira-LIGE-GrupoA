package softwareeng.project;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class SelectUcs extends JFrame{
	
	private JButton backButton;
	private JCheckBox checkBox;

    private static final Logger LOGGER = Logger.getLogger("LoadSchedules");
    
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

	private void initComponents() {
		
		 backButton = new JButton();
		    backButton.setBorderPainted(false);
		    backButton.setFocusPainted(false);
		    backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
		    backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    int buttonSize = 48;
		    backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
		            .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
		    checkBox = new JCheckBox("UC1");
		    JPanel buttonPanel = new JPanel(new GridBagLayout()); // Create panel for button
		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.gridx = 0;
		    gbc.gridy = 0;
		    gbc.weightx = 1.0;
		    gbc.fill = GridBagConstraints.HORIZONTAL;
		    buttonPanel.add(backButton, gbc);
		    add(buttonPanel);
		
	}
	
	

	private void layoutComponents() {
		// Use a Box layout instead of a GridLayout
	    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

	    // Add the location label and text field to the first panel
	    JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(checkBox);
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
	
	private void addListeners() {
		backButton.addActionListener(e -> backButtonClicked());	
		
	}
	
	private void backButtonClicked() {
        if (this.isVisible()) {
            dispose();
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        }
    }
}

