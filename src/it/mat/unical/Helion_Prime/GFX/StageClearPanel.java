package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.Multiplayer.ServerMultiplayer;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;
import it.mat.unical.Helion_Prime.Online.Server;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StageClearPanel extends JPanel {

	private JButton backToMenuButton;
	private JButton retryButton;
	private JButton nextLevel;
	private MainMenuFrame mainMenuFrame;
	private BufferedImage stageClearImage;
	private Cursor cursor;
	private ClientManager clientManager;
	private UserProfile profile;
	private Server server;
	private ServerMultiplayer serverMultiplayer;
	private File lastLevelPlayed;

	public StageClearPanel(ClientManager clientManager, File level) {
		this.clientManager = clientManager;
		this.profile = clientManager.getUserProfile();
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		this.lastLevelPlayed = level;
		this.mainMenuFrame = MainMenuFrame.getInstance();
		this.backToMenuButton = new JButton("Back to Menu");
		this.retryButton = new JButton("Retry");

		if (MainMenuFrame.getInstance().getMainMenuPanel().isStoryModeOn()) {
			this.nextLevel = new JButton("Next Level");

			this.nextLevel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String choosenLevel = profile.getLevels().get(
							profile.getLastlevelComplete())
							+ ".txt";

					String name = "levels/" + choosenLevel;
					System.out.println("LevelSwitchPanel.LevelSwitchPanel    "
							+ name);
					File level = new File(name);
					System.out
							.println("------------------------------------------------");
					MainGamePanel mainGamePanel = null;

					if (!Server.isServerStarted)
						try {
							server = new Server(7777);
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					else {
						server = null;
						try {
							server = new Server(7777);
						} catch (IOException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}

					}

					server.start();
					GameManagerImpl.getInstance().setServer(server);
					Client client = new Client("localhost", false);
					client.sendMessage(choosenLevel);

					if (client.recieveMessage().equals("ready")) {

						System.out.println("SIAMO READY INIZIA IL GIOCO");
						mainGamePanel = new MainGamePanel(level, client,
								profile);
						MainMenuFrame.getInstance().switchTo(mainGamePanel);
					}

				}
			});

		}

		createButton();

		this.backToMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				StageClearPanel.this.mainMenuFrame
						.switchTo(StageClearPanel.this.mainMenuFrame
								.getMainMenuPanel());

			}
		});

		this.retryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String name = lastLevelPlayed.getName();

				if (!StageClearPanel.this.clientManager.isMultiplayerGame()) {
					MainGamePanel mainGamePanel = null;

					try {
						server = new Server(7777);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					server.start();
					GameManagerImpl.getInstance().setServer(server);
					Client client = new Client("localhost", false);
					client.sendMessage(name);

					if (client.recieveMessage().equals("ready")) {

						System.out.println("SIAMO READY INIZIA IL GIOCO");
						if (MainMenuFrame.getInstance().getMainMenuPanel()
								.isStoryModeOn())
							mainGamePanel = new MainGamePanel(lastLevelPlayed,
									client, profile);
						else
							mainGamePanel = new MainGamePanel(lastLevelPlayed,
									client);

					}
					MainMenuFrame.getInstance().switchTo(mainGamePanel);
				} else {

					StageClearPanel.this.clientManager.sendMessage("retry");

					System.out.println("ATTENDO MESSAGGIO DAL SERVER");

					StageClearPanel.this.clientManager.getClient()
							.recieveMessage();

					System.out.println("MESSAGGIO DAL SERVER ARRIVATO");

					MainGamePanel mgGamePanel = new MainGamePanel(
							lastLevelPlayed, StageClearPanel.this.clientManager
									.getClient());

					MainMenuFrame.getInstance().switchTo(mgGamePanel);

				}

			}

		});

		try {
			stageClearImage = ImageIO
					.read(new File("Resources/stageClear.png")); // sfondo
			// menu
			// iniziale

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.add(backToMenuButton);
		this.add(retryButton);

		if (MainMenuFrame.getInstance().getMainMenuPanel().isStoryModeOn()) {
			this.add(nextLevel);
		}

	}

	public void createButton() {
		retryButton.setBackground(Color.black);
		retryButton.setForeground(Color.green);
		retryButton.setFont(mainMenuFrame.getMainMenuPanel().getFont());
		retryButton.setFont(retryButton.getFont().deriveFont(25.0f));
		retryButton.setBorderPainted(false);
		retryButton.setFocusPainted(false);
		backToMenuButton.setBackground(Color.black);
		backToMenuButton.setForeground(Color.green);
		backToMenuButton.setFont(mainMenuFrame.getMainMenuPanel().getFont());
		backToMenuButton.setFont(retryButton.getFont().deriveFont(25.0f));
		backToMenuButton.setBorderPainted(false);
		backToMenuButton.setFocusPainted(false);

		if (MainMenuFrame.getInstance().getMainMenuPanel().isStoryModeOn()) {
			retryButton.setBackground(Color.black);
			retryButton.setForeground(Color.green);
			retryButton.setFont(mainMenuFrame.getMainMenuPanel().getFont());
			retryButton.setFont(retryButton.getFont().deriveFont(25.0f));
			retryButton.setBorderPainted(false);
			retryButton.setFocusPainted(false);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(stageClearImage, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}
}
