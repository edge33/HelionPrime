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
	private EastGamePanel eastPanel;
	private WestGamePanel westPanel;
	private GamePadController gamePadController;
	private Cursor cursor;
	private UserProfile profile;

	public MainGamePanel(File level, Client client) {
		int outerPanelWith = MainMenuFrame.getInstance().getWidth() / 4;
		int innerPanelWith = MainMenuFrame.getInstance().getWidth() / 2;
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		setLayout(new BorderLayout());
		// gamePadController = new GamePadController();
		eastPanel = new EastGamePanel();
		westPanel = new WestGamePanel();
		westPanel.setBackground(Color.black);

		trapPanel = new TrapPanel(client.isMultiplayerGame());
		informationPanel = new InformationPanel();
		/*
		 * 
		 * implementare rete
		 */

		try {
			levelPanel = new GamePane(level, client, trapPanel, westPanel,
					informationPanel, gamePadController, null, eastPanel);
		} catch (FileNotCorrectlyFormattedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eastPanel.setPreferredSize(new Dimension(150, 620));
		westPanel.setPreferredSize(new Dimension(150, 620));
		levelPanel.setPreferredSize(new Dimension(innerPanelWith, 620));
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
		// gamePadController = new GamePadController();
		eastPanel = new EastGamePanel();
		westPanel = new WestGamePanel();
		westPanel.setBackground(Color.black);
		westPanel.setPreferredSize(new Dimension(150, 620));
		eastPanel.setPreferredSize(new Dimension(150, 620));
		eastPanel.setBackground(Color.black);
		eastPanel.setSize(150, 620);
		trapPanel = new TrapPanel(client.isMultiplayerGame());
		informationPanel = new InformationPanel();

		this.profile = profile;
		/*
		 * 
		 * implementare rete
		 */

		try {
			levelPanel = new GamePane(level, client, trapPanel, westPanel,
					informationPanel, gamePadController, profile, eastPanel);
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
