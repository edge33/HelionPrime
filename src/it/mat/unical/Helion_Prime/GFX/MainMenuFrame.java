package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.CommonProperties;
import it.mat.unical.Helion_Prime.SavesManager.H2DbManager;

import java.awt.BorderLayout;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

//frame principale contiene Il pannello di scelta di inizio gioco O editor

public class MainMenuFrame extends JFrame {

	private static MainMenuFrame instance;

	private JPanel contentPanel;

	private MainMenuPanel mainMenuPanel;

	public static MainMenuFrame getInstance() {

		if (instance == null) {
			instance = new MainMenuFrame();
		}
		return instance;
	}

	private MainMenuFrame() {

	}

	public void start() {
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		contentPanel = new JPanel(new BorderLayout());
		add(contentPanel);

		contentPanel.setVisible(true);

		mainMenuPanel = new MainMenuPanel();
		switchTo(mainMenuPanel);

	}

	public void switchTo(final JPanel panel) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				contentPanel.removeAll();
				contentPanel.add(panel, BorderLayout.CENTER);
				contentPanel.updateUI();
				panel.requestFocus();
			}
		});
	}

	public void switchTo(final JLayeredPane panel) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				contentPanel.removeAll();
				contentPanel.add(panel, BorderLayout.CENTER);
				contentPanel.updateUI();
				panel.requestFocus();
			}
		});
	}

	public static void main(String[] args) {

		String property = "swing.aatext";
		System.setProperty(property, "true");
		MainMenuFrame frame = MainMenuFrame.getInstance();
		frame.start();
		
		try {
            // Set System L&F
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		
		
		try {
			CommonProperties.getInstance().loadProperties(new File("database.properties"));
		} catch (FileNotFoundException e) {
			int choice = JOptionPane.showConfirmDialog(instance,"Nessun file di configurazione trovato, vuoi cercarne uno?","Errore",JOptionPane.YES_NO_OPTION);
			
			if ( choice == JOptionPane.YES_OPTION ) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("properties files","properties");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(instance);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						CommonProperties.getInstance().loadProperties(chooser.getSelectedFile());
					} catch (FileNotFoundException e1) {
					}
				}
			} else {
				System.exit(0);
			}
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				H2DbManager db = H2DbManager.getInstance();
					db.H2disengange();
			}
		});
		
	}

	public MainMenuPanel getMainMenuPanel() {
		return mainMenuPanel;
	}

}
