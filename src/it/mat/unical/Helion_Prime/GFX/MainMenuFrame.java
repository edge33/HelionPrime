package it.mat.unical.Helion_Prime.GFX;

import java.awt.BorderLayout;
import java.sql.Timestamp;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	}

	public MainMenuPanel getMainMenuPanel() {
		return mainMenuPanel;
	}

}
