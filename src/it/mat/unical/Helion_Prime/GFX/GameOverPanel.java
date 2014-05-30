package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.Multiplayer.ServerMultiplayer;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;
import it.mat.unical.Helion_Prime.Online.Server;
import it.mat.unical.Helion_Prime.SavesManager.NewSavegameCommand;
import it.mat.unical.Helion_Prime.SavesManager.OverrideSavegameCommand;
import it.mat.unical.Helion_Prime.ScoreCharts.RemoteDatabaseManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameOverPanel extends JLayeredPane {

	private JPanel previewPaneL;
	private JPanel overlay;

	private JButton backToMenuButton;
	private JButton saveLevel;
	private JButton retryButton;
	private JButton hideButton;

	private JButton newSaveGameButton;
	private JButton overrideSaveButton;
	private JButton submitScore;

	private JTextField userField;
	private JPasswordField passField;

	private JLabel user;
	private JLabel pass;
	private JLabel time;
	private JLabel timeDesc;
	private JLabel bulletTitle;
	private JLabel bulletsGun1Desc;
	private JLabel bulletsGun2Desc;
	private JLabel bulletsGun3Desc;
	private JLabel bulletsGun4Desc;
	private JLabel bulletsGun1;
	private JLabel bulletsGun2;
	private JLabel bulletsGun3;
	private JLabel bulletsGun4;

	private GridBagLayout eastLayout;
	private GridBagConstraints eC;

	private MainMenuFrame mainMenuFrame;
	private BufferedImage gameOverImage;
	private Cursor cursor;
	private UserProfile profile;
	private Server server;
	private ServerMultiplayer serverMultiplayer;
	private File lastlevelPlayed;
	private ClientManager manager;
	private boolean isStoryModeOn;
	protected Client client;

	public GameOverPanel(ClientManager manager, File level) {
		this.manager = manager;
		profile = manager.getUserProfile();
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
		this.hideButton = new JButton("Hide");

		this.overrideSaveButton = new SaveGameInvokerButton(
				"Overwrite Savegame", new OverrideSavegameCommand());
		this.newSaveGameButton = new SaveGameInvokerButton("Save New Game",
				new NewSavegameCommand());
		this.hideButton = new JButton("Hide");

		this.isStoryModeOn = MainMenuFrame.getInstance().getMainMenuPanel()
				.isStoryModeOn();
		if (isStoryModeOn) {
			this.saveLevel = new JButton("Save Level");
		} else {
			this.saveLevel = new JButton("Submit score");
			this.submitScore = new JButton("Confirm Submit");
			this.hideButton = new JButton("Hide");
			this.user = new JLabel("User:");
			this.pass = new JLabel("Password:");
			this.userField = new JTextField(15);
			this.passField = new JPasswordField(15);

			submitScore.setForeground(Color.green);
			submitScore.setBackground(Color.black);
			submitScore.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
					.getFont());
			submitScore.setFont(saveLevel.getFont().deriveFont(16.0f));

			this.userField.setHorizontalAlignment(SwingConstants.CENTER);
			this.passField.setHorizontalAlignment(SwingConstants.CENTER);
		}

		this.time = new JLabel("0");
		this.timeDesc = new JLabel("Tempo:");
		this.bulletTitle = new JLabel("Proiettili Rimanenti:");
		this.bulletsGun1Desc = new JLabel(" Plasma Gun:");
		this.bulletsGun2Desc = new JLabel(" Laser Rifle:");
		this.bulletsGun3Desc = new JLabel(" Shootgun:");
		this.bulletsGun4Desc = new JLabel(" Plasma Cannon:");
		this.bulletsGun1 = new JLabel("-");
		this.bulletsGun2 = new JLabel("0");
		this.bulletsGun3 = new JLabel("0");
		this.bulletsGun4 = new JLabel("0");

		this.createButton();
		this.addListener();

		hideButton.setForeground(Color.green);
		hideButton.setBackground(Color.black);
		hideButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		hideButton.setFont(saveLevel.getFont().deriveFont(16.0f));

		this.add(overlay, BorderLayout.CENTER);
		this.overlay.add(backToMenuButton);
		this.overlay.add(saveLevel);
		this.overlay.add(retryButton);

	}

	public void addListener() {
		this.saveLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createSavePanel();
				backToMenuButton.setEnabled(false);
				saveLevel.setEnabled(false);
				retryButton.setEnabled(false);
			}
		});

		this.hideButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				previewPaneL.setVisible(false);
				backToMenuButton.setEnabled(true);
				saveLevel.setEnabled(true);
				retryButton.setEnabled(true);
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

				if (!manager.isMultiplayerGame()) {
					MainGamePanel mainGamePanel = null;

					try {
						server = new Server(7777);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					server.setLevel(lastlevelPlayed.getName());
					server.start();

					GameManagerImpl.getInstance(0).setServer(server);
					client = new Client("localhost", Client
							.getDefaultNumberPort(), false);
					sendMessage(name);

					if (recieveMessage().equals("ready")) {

						System.out.println("SIAMO READY INIZIA IL GIOCO");
						if (MainMenuFrame.getInstance().getMainMenuPanel()
								.isStoryModeOn())
							mainGamePanel = new MainGamePanel(lastlevelPlayed,
									client, profile);
						else
							mainGamePanel = new MainGamePanel(lastlevelPlayed,
									client);

						System.err.println("HO RICEVUTO IL READY");

					}

					MainMenuFrame.getInstance().switchTo(mainGamePanel);
				} else {

					GameOverPanel.this.manager.sendMessage("retry");

					System.out.println("ATTENDO MESSAGGIO DAL SERVER");

					String responseFromServer = null;

					try {
						responseFromServer = GameOverPanel.this.manager.informations
								.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if ((!responseFromServer.equals("PlayerOneOut"))
							&& (!responseFromServer.equals("PlayerTwoOut"))) {
						System.out.println("MESSAGGIO DAL SERVER ARRIVATO");

						lastlevelPlayed = null;
						lastlevelPlayed = new File("levels/"
								+ responseFromServer + ".txt");

						System.out.println("CLIENTTTTTTTTTT "
								+ responseFromServer);

						MainGamePanel mgGamePanel = new MainGamePanel(
								lastlevelPlayed, GameOverPanel.this.manager
										.getClient());

						MainMenuFrame.getInstance().switchTo(mgGamePanel);

						// } else if (responseFromServer.equals("nOk")) {
						//
						// StageClearPanel.this.mainMenuFrame
						// .switchTo(StageClearPanel.this.mainMenuFrame
						// .getMainMenuPanel());
						//
						// }
					} else {
						JOptionPane.showMessageDialog(GameOverPanel.this,
								responseFromServer);

						GameOverPanel.this.manager.closeConnection();
						MainMenuFrame.getInstance().switchTo(
								MainMenuFrame.getInstance().getMainMenuPanel());
					}
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
		saveLevel.setBackground(Color.black);
		saveLevel.setForeground(Color.green);
		saveLevel.setFont(mainMenuFrame.getMainMenuPanel().getFont());
		saveLevel.setFont(saveLevel.getFont().deriveFont(25.0f));
		saveLevel.setBorderPainted(false);
		saveLevel.setFocusPainted(false);
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
			overrideSaveButton.setForeground(Color.green);
			overrideSaveButton.setBackground(Color.black);
			overrideSaveButton.setFont(MainMenuFrame.getInstance()
					.getMainMenuPanel().getFont());
			overrideSaveButton.setFont(saveLevel.getFont().deriveFont(16.0f));
			newSaveGameButton.setForeground(Color.green);
			newSaveGameButton.setBackground(Color.black);
			newSaveGameButton.setFont(MainMenuFrame.getInstance()
					.getMainMenuPanel().getFont());
			newSaveGameButton.setFont(saveLevel.getFont().deriveFont(16.0f));

		} else {

			submitScore.setForeground(Color.green);
			submitScore.setBackground(Color.black);
			submitScore.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
					.getFont());
			submitScore.setFont(submitScore.getFont().deriveFont(16.0f));

			this.submitScore.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					RemoteDatabaseManager database = RemoteDatabaseManager
							.getInstance();
					String username = GameOverPanel.this.userField.getText();
					String password = String
							.valueOf(GameOverPanel.this.passField.getPassword());

					System.out.println(username + " " + password);

					if (database.doLogin(username, password)) {
						if (database
								.uploadScore(
										username,
										GameOverPanel.this.manager.getMoney(),
										lastlevelPlayed.getName().substring(
												0,
												lastlevelPlayed.getName()
														.length() - 4))) {
							JOptionPane.showMessageDialog(
									MainMenuFrame.getInstance(),
									"Caricamento Effettuato!");
						} else {
							JOptionPane.showMessageDialog(
									MainMenuFrame.getInstance(),
									"Errore Caricamento, controlla la connessione ad internet!");
						}
					} else {
						JOptionPane.showMessageDialog(
								MainMenuFrame.getInstance(),
								"Username o password errata!");
					}

				}
			});

		}

	}

	public void createSavePanel() {

		this.eastLayout = new GridBagLayout();
		this.eC = new GridBagConstraints();
		this.eC.fill = GridBagConstraints.CENTER;
		this.eC.weightx = 1.0;

		this.time.setText(InformationPanel.getTime());

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
		JPanel filler = new JPanel();
		JPanel recap = new JPanel();
		recap.setOpaque(false);
		recap.setLayout(eastLayout);
		filler.setOpaque(false);
		previewPaneL.setLayout(new BorderLayout());
		if (isStoryModeOn) {
			filler.add(overrideSaveButton);
			filler.add(newSaveGameButton);
			filler.add(hideButton);
		} else {
			filler.add(submitScore);
			filler.add(hideButton);
		}
		previewPaneL.setBackground(Color.BLACK);
		previewPaneL.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1,
				true));
		previewPaneL.setBounds(x, y, prevPanelX, prevPanelY);
		previewPaneL.setVisible(true);

		this.eastLayout.setConstraints(timeDesc, eC);
		recap.add(timeDesc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(time, eC);
		recap.add(time);
		eC.gridwidth = 1;

		this.eC.insets = new Insets(40, 0, 0, 0);
		this.eC.gridwidth = 1;

		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletTitle, eC);
		recap.add(bulletTitle);

		this.eC.insets = new Insets(10, 0, 0, 0);
		this.eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun1Desc, eC);
		recap.add(bulletsGun1Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun1, eC);
		recap.add(bulletsGun1);
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun2Desc, eC);
		recap.add(bulletsGun2Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun2, eC);
		recap.add(bulletsGun2);
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun3Desc, eC);
		recap.add(bulletsGun3Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun3, eC);
		recap.add(bulletsGun3);
		eC.gridwidth = 1;

		this.eC.insets = new Insets(10, 0, 0, 0);
		this.eC.gridwidth = 1;
		this.eC.weightx = 1.0;

		this.eastLayout.setConstraints(bulletsGun4Desc, eC);
		recap.add(bulletsGun4Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun4, eC);
		recap.add(bulletsGun4);
		eC.gridwidth = 1;

		this.eC.insets = new Insets(50, 0, 0, 0);
		this.eC.gridwidth = 1;

		if (!isStoryModeOn) {
			this.eastLayout.setConstraints(user, eC);
			recap.add(user);
			this.eC.gridwidth = GridBagConstraints.REMAINDER;
			this.eastLayout.setConstraints(pass, eC);
			recap.add(pass);
			eC.gridwidth = 1;

			this.eastLayout.setConstraints(userField, eC);
			recap.add(userField);
			this.eC.gridwidth = GridBagConstraints.REMAINDER;
			this.eastLayout.setConstraints(passField, eC);
			recap.add(passField);
			eC.gridwidth = 1;
		}

		for (int i = 0; i < recap.getComponentCount(); i++) {
			Component component = recap.getComponent(i);
			if (component instanceof JLabel) {
				component.setForeground(Color.green);
				((JLabel) component).setOpaque(false);
				component.setFont(MainMenuFrame.getInstance()
						.getMainMenuPanel().getFont());
				component.setFont(component.getFont().deriveFont(16.0f));
			}
		}

		previewPaneL.add(recap, BorderLayout.CENTER);
		previewPaneL.add(filler, BorderLayout.SOUTH);
		GameOverPanel.this.add(previewPaneL, BorderLayout.CENTER, new Integer(
				10));
	}

	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(gameOverImage, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}

	private String recieveMessage() {
		try {
			return client.recieveMessage();
		} catch (IOException e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Impossibile contattare il server");

			MainMenuFrame.getInstance().switchTo(
					MainMenuFrame.getInstance().getMainMenuPanel());

			client.closeConnection();
		}
		return null;
	}

	private void sendMessage(String c) {
		try {
			client.sendMessage(c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Impossibile contattare il server");

			MainMenuFrame.getInstance().switchTo(
					MainMenuFrame.getInstance().getMainMenuPanel());

			client.closeConnection();
		}
	}

}
