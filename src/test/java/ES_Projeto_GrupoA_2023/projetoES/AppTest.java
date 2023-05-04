package ES_Projeto_GrupoA_2023.projetoES;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.lang.reflect.Field;
import javax.swing.JFileChooser;
import org.junit.Test;

import softwareeng.project.MainMenu;


/**
 * Unit test for simple App.
 */
public class AppTest {
   
	@Test
	public void test() {
		 MainMenu frame = new MainMenu();
	        assertTrue(frame.isVisible());
	        


	}
	
	
	
}
