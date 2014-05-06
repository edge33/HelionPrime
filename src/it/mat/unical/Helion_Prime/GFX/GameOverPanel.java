package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.Multiplayer.ServerMultiplayer;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;
import it.mat.unical.Helion_Prime.Online.Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameOverPanel extends JLayeredPane {

	private JPanel previewPaneL;
	private JPanel overlay;
	private JButton backToMenuButton;
	private JButton saveLevel;
	private JButton retryButton;
	private MainMenuFrame mainMenuFrame;
	private BufferedImage gameOverImage;
	private Cursor cursor;
	private UserProfile profile;
	private Server server;
	private ServerMultiplayer serverMultiplayer;
	private File lastlevelPlayed;

	public GameOverPanel(File level) {

		profile = ClientManager.getInstance().getUserProfile();
		try {
			gameOverImage = ImageIO.read(new File("Resources/gameOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		lastlevelPlayed = level;

		this.setLayout(new BorderLayout());
		this.overlay = new JPanel();
		this.overlay.setOpaque(false);
		this.previewPaneL = null;
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		this.mainMenuFrame = MainMenuFrame.getInstance();
		this.backToMenuButton = new JButton("Back to Menu");
		this.saveLevel = new JButton("Save Level");
		this.retryButton = new JButton("Retry");
		this.createButton();
		this.addListener();

		this.add(overlay, BorderLayout.CENTER);
		this.overlay.add(backToMenuButton);
		this.overlay.add(saveLevel);
		this.overlay.add(retryButton);

	}

	public void addListener() {
		this.saveLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int frameWidth = MainMenuFrame.getInstance().getWidth();
				int frameHeight = MainMenuFrame.getInstance().getHeight();
				int prevPanelX;
				int prevPanelY;
				int x, y;

				if (frameWidth >= 300) {
					prevPanelX = (MainMenuFrame.getInstance().getWidth() - 200);
					x = (frameWidth - prevPanelX) / 2;
				} else {
					prevPanelX = frameHeight;
					x = 0;
				}

				if (frameHeight >= 300) {
					prevPanelY = (MainMenuFrame.getInstance().getHeight() - 200);
					y = (frameHeight - prevPanelY) / 2;
				} else {
					prevPanelY = frameHeight;
					y = 0;
				}
				previewPaneL = new JPanel();
				previewPaneL.setBackground(Color.BLACK);
				previewPaneL.setBorder(BorderFactory.createLineBorder(
						Color.GREEN, 1, true));
				previewPaneL.setBounds(x, y, prevPanelX, prevPanelY);
				GameOverPanel.this.add(previewPaneL, BorderLayout.CENTER,
						new Integer(10));
				previewPaneL.setVisible(true);
				previewPaneL.repaint();
				repaint();

			}
		});
		this.backToMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameOverPanel.this.mainMenuFrame
						.switchTo(GameOverPanel.this.mainMenuFrame
								.getMainMenuPanel());
			}
		});

		this.retryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = lastlevelPlayed.getName();

				if (!ClientManager.getInstance().isMultiplayerGame()) {
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
							mainGamePanel = new MainGamePanel(lastlevelPlayed,
									client, profile);
						else
							mainGamePanel = new MainGamePanel(lastlevelPlayed,
									client);

					}
					try {
						GameManagerImpl.getInstance().init(lastlevelPlayed,
								false);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MainMenuFrame.getInstance().switchTo(mainGamePanel);
				} else {

					ClientManager.getInstance().sendMessage("retry");

					System.out.println("ATTENDO MESSAGGIO DAL SERVER");

					ClientManager.getInstance().getClient().recieveMessage();

					System.out.println("MESSAGGIO DAL SERVER ARRIVATO");

					MainGamePanel mgGamePanel = new MainGamePanel(
							lastlevelPlayed, ClientManager.getInstance()
									.getClient());

					MainMenuFrame.getInstance().switchTo(mgGamePanel);

				}
			}

		});
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
	}

	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(gameOverImage, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}
}
