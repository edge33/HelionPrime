package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.GamePad.GamePadController;
import it.mat.unical.Helion_Prime.Logic.FileNotCorrectlyFormattedException;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.Online.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JPanel;

public class MainGamePanel extends JPanel {

	private GamePane levelPanel;
	private InformationPanel informationPanel;
	private TrapPanel trapPanel;
	private JPanel eastPanel;
	private JPanel westPanel;
	private GamePadController gamePadController;
	private Cursor cursor;
	private UserProfile profile;

	public MainGamePanel(File level, Client client)
	{

		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		setLayout(new BorderLayout());
		gamePadController = new GamePadController();
		eastPanel = new JPanel();
		westPanel = new JPanel();
		westPanel.setBackground(Color.black);
		westPanel.setPreferredSize(new Dimension(150, 620));
		eastPanel.setPreferredSize(new Dimension(150, 620));
		eastPanel.setBackground(Color.black);
		eastPanel.setSize(150, 620);
		trapPanel = new TrapPanel();
		informationPanel = new InformationPanel();
		/*
		 * 
		 * implementare rete
		 */

		try {
			levelPanel = new GamePane(level, client, trapPanel,
					informationPanel, gamePadController, null);
		} catch (FileNotCorrectlyFormattedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add(informationPanel, BorderLayout.NORTH);
		add(trapPanel, BorderLayout.SOUTH);
		add(eastPanel, BorderLayout.EAST);
		add(westPanel, BorderLayout.WEST);
		add(levelPanel, BorderLayout.CENTER);
		levelPanel.setVisible(true);
		informationPanel.setVisible(true);
		trapPanel.setVisible(true);
		westPanel.setVisible(true);
		eastPanel.setVisible(true);

	}

	public MainGamePanel(File level, Client client, UserProfile profile) {
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		setLayout(new BorderLayout());
		gamePadController = new GamePadController();
		eastPanel = new JPanel();
		westPanel = new JPanel();
		westPanel.setBackground(Color.black);
		westPanel.setPreferredSize(new Dimension(150, 620));
		eastPanel.setPreferredSize(new Dimension(150, 620));
		eastPanel.setBackground(Color.black);
		eastPanel.setSize(150, 620);
		trapPanel = new TrapPanel();
		informationPanel = new InformationPanel();

		this.profile = profile;
		/*
		 * 
		 * implementare rete
		 */

		try {
			levelPanel = new GamePane(level, client, trapPanel,
					informationPanel, gamePadController, profile);
		} catch (FileNotCorrectlyFormattedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add(informationPanel, BorderLayout.NORTH);
		add(trapPanel, BorderLayout.SOUTH);
		add(eastPanel, BorderLayout.EAST);
		add(westPanel, BorderLayout.WEST);
		add(levelPanel, BorderLayout.CENTER);
		levelPanel.setVisible(true);
		informationPanel.setVisible(true);
		trapPanel.setVisible(true);
		westPanel.setVisible(true);
		eastPanel.setVisible(true);
	}

	public void updatePanel() {

	}

	public GamePane getLevelPanel() {
		return levelPanel;
	}

	@Override
	public void requestFocus() {
		super.requestFocus();
		levelPanel.requestFocus();
	}
}
