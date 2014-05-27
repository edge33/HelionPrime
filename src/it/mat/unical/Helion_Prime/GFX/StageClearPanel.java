package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.Multiplayer.ServerMultiplayer;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;
import it.mat.unical.Helion_Prime.Online.Server;
import it.mat.unical.Helion_Prime.SavesManager.OverrideSavegameCommand;
import it.mat.unical.Helion_Prime.SavesManager.PlayerState;

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
import java.util.concurrent.LinkedBlockingQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StageClearPanel extends JLayeredPane {

	private JButton backToMenuButton;
	private JButton saveLevel;
	private JButton retryButton;
	private JButton nextLevel;

	private JPanel previewPaneL;
	private JPanel overlay;
	private SaveGameInvokerButton confirmButton;
	private JButton hideButton;

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

	private MainMenuFrame mainMenuFrame;
	private BufferedImage stageClearImage;
	private Cursor cursor;
	private ClientManager clientManager;
	private UserProfile profile;
	private Server server;
	private ServerMultiplayer serverMultiplayer;
	private File lastLevelPlayed;

	private GridBagLayout eastLayout;
	private GridBagConstraints eC;
	protected boolean isBufferEmpty = false;
	protected LinkedBlockingQueue<String> fromServer;

	public StageClearPanel(ClientManager clientManager, File level) {

		this.confirmButton = new SaveGameInvokerButton("Save");
		this.hideButton = new JButton("Hide");
		fromServer = new LinkedBlockingQueue<String>();
		this.overlay = new JPanel();
		this.overlay.setOpaque(false);
		this.setLayout(new BorderLayout());

		this.time = new JLabel("0");
		this.timeDesc = new JLabel("Tempo:");
		this.bulletTitle = new JLabel("Proiettili Sparati:");
		this.bulletsGun1Desc = new JLabel(" Plasma Gun:");
		this.bulletsGun2Desc = new JLabel(" Laser Rifle:");
		this.bulletsGun3Desc = new JLabel(" Shootgun:");
		this.bulletsGun4Desc = new JLabel(" Plasma Cannon:");
		this.bulletsGun1 = new JLabel("0");
		this.bulletsGun2 = new JLabel("0");
		this.bulletsGun3 = new JLabel("0");
		this.bulletsGun4 = new JLabel("0");

		this.clientManager = clientManager;

		this.profile = clientManager.getUserProfile();
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		this.lastLevelPlayed = level;
		this.mainMenuFrame = MainMenuFrame.getInstance();
		this.backToMenuButton = new JButton("Back to Menu");
		this.saveLevel = new JButton("Save Level");
		this.retryButton = new JButton("Retry");

		if (!PlayerState.getInstance().isSet()) {
			this.saveLevel.setEnabled(false);
		}

		if (MainMenuFrame.getInstance().getMainMenuPanel().isStoryModeOn()) {

			this.nextLevel = new JButton("Next Level");
			nextLevel.setBackground(Color.black);
			nextLevel.setForeground(Color.green);
			nextLevel.setFont(mainMenuFrame.getMainMenuPanel().getFont());
			nextLevel.setFont(nextLevel.getFont().deriveFont(25.0f));
			nextLevel.setBorderPainted(false);
			nextLevel.setFocusPainted(false);
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
					server.setLevel(choosenLevel);
					server.start();
					GameManagerImpl.getInstance(0).setServer(server);
					Client client = new Client("localhost", Client
							.getDefaultNumberPort(), false);
					client.sendMessage(choosenLevel);

					if (client.recieveMessage().equals("ready")) {

						System.out.println("SIAMO READY INIZIA IL GIOCO");
						mainGamePanel = new MainGamePanel(level, client,
								profile);

						MainMenuFrame.getInstance().switchTo(mainGamePanel);
					}

				}
			});

			this.confirmButton.setCommand(new OverrideSavegameCommand());

		} else {
			this.confirmButton.setText("Upload Score");
		}

		createButton();

		this.backToMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!StageClearPanel.this.clientManager.isMultiplayerGame()) {

					StageClearPanel.this.mainMenuFrame
							.switchTo(StageClearPanel.this.mainMenuFrame
									.getMainMenuPanel());

				} else {
					// StageClearPanel.this.clientManager.sendMessage("finish");

					StageClearPanel.this.clientManager.sendMessage("notRetry");
					StageClearPanel.this.clientManager.closeConnection();
					StageClearPanel.this.mainMenuFrame
							.switchTo(StageClearPanel.this.mainMenuFrame
									.getMainMenuPanel());

				}
			}
		});

		this.saveLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				PlayerState playerState = PlayerState.getInstance();

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

		this.retryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String name = lastLevelPlayed.getName();

				if (!StageClearPanel.this.clientManager.isMultiplayerGame()) {
					MainGamePanel mainGamePanel = null;

					StageClearPanel.this.clientManager.reset();
					try {
						server = new Server(7777);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					server.setLevel(lastLevelPlayed.getName());
					server.start();
					GameManagerImpl.getInstance(0).setServer(server);
					Client client = new Client("localhost", Client
							.getDefaultNumberPort(), false);
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

						// try {
						// GameManagerImpl.getInstance().init(lastLevelPlayed,
						// false);
						// } catch (FileNotFoundException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
					}
					MainMenuFrame.getInstance().switchTo(mainGamePanel);
				} else {

					StageClearPanel.this.clientManager.sendMessage("retry");

					System.out.println("ATTENDO MESSAGGIO DAL SERVER");

					String responseFromServer = null;
					try {
						responseFromServer = StageClearPanel.this.clientManager.informations
								.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("RESPONSE FROM SERVER "
							+ responseFromServer);

					if ((!responseFromServer.equals("PlayerOneOut"))
							&& (!responseFromServer.equals("PlayerTwoOut"))) {
						System.out.println("MESSAGGIO DAL SERVER ARRIVATO");

						lastLevelPlayed = null;
						lastLevelPlayed = new File("levels/"
								+ responseFromServer + ".txt");

						System.out.println("CLIENTTTTTTTTTT "
								+ responseFromServer);

						MainGamePanel mgGamePanel = new MainGamePanel(
								lastLevelPlayed,
								StageClearPanel.this.clientManager.getClient());

						MainMenuFrame.getInstance().switchTo(mgGamePanel);

						// } else if (responseFromServer.equals("nOk")) {
						//
						// StageClearPanel.this.mainMenuFrame
						// .switchTo(StageClearPanel.this.mainMenuFrame
						// .getMainMenuPanel());
						//
						// }
					} else {
						JOptionPane.showMessageDialog(StageClearPanel.this,
								responseFromServer);

						StageClearPanel.this.clientManager.closeConnection();
						MainMenuFrame.getInstance().switchTo(
								MainMenuFrame.getInstance().getMainMenuPanel());
					}
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

		this.overlay.add(backToMenuButton);
		this.overlay.add(saveLevel);
		this.overlay.add(retryButton);

		if (MainMenuFrame.getInstance().getMainMenuPanel().isStoryModeOn()) {
			this.overlay.add(nextLevel);
		}
		this.add(overlay, BorderLayout.NORTH);
	}

	public void createButton() {

		saveLevel.setBackground(Color.black);
		saveLevel.setForeground(Color.green);
		saveLevel.setFont(mainMenuFrame.getMainMenuPanel().getFont());
		saveLevel.setFont(saveLevel.getFont().deriveFont(25.0f));
		saveLevel.setBorderPainted(false);
		saveLevel.setFocusPainted(false);
		confirmButton.setForeground(Color.green);
		confirmButton.setBackground(Color.black);
		confirmButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		confirmButton.setFont(saveLevel.getFont().deriveFont(16.0f));
		hideButton.setForeground(Color.green);
		hideButton.setBackground(Color.black);
		hideButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		hideButton.setFont(saveLevel.getFont().deriveFont(16.0f));
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

	public void svuotaBuffer() {
		new Thread() {

			@Override
			public void run() {
				while (!isBufferEmpty) {
					try {
						fromServer.put(clientManager.getClient()
								.recieveMessage());

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}.start();

	}

	public void createSavePanel() {

		this.eastLayout = new GridBagLayout();
		this.eC = new GridBagConstraints();
		this.eC.fill = GridBagConstraints.CENTER;
		this.eC.weightx = 1.0;

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
		filler.add(confirmButton);
		filler.add(hideButton);
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

		PlayerState playerState = PlayerState.getInstance();

		this.eastLayout.setConstraints(bulletsGun1Desc, eC);
		recap.add(bulletsGun1Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun1, eC);
		recap.add(bulletsGun1);
		this.bulletsGun1.setText(String.valueOf(playerState.getGunBullets1()));
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun2Desc, eC);
		recap.add(bulletsGun2Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun2, eC);
		recap.add(bulletsGun2);
		this.bulletsGun2.setText(String.valueOf(playerState.getGunBullets2()));
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun3Desc, eC);
		recap.add(bulletsGun3Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun3, eC);
		recap.add(bulletsGun3);
		this.bulletsGun3.setText(String.valueOf(playerState.getGunBullets3()));
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun4Desc, eC);
		recap.add(bulletsGun4Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun4, eC);
		this.bulletsGun4.setText(String.valueOf(playerState.getGunBullets4()));
		recap.add(bulletsGun4);
		eC.gridwidth = 1;

		this.eC.insets = new Insets(10, 0, 0, 0);
		this.eC.gridwidth = 1;
		this.eC.weightx = 1.0;

		for (int i = 0; i < recap.getComponentCount(); i++) {
			Component component = recap.getComponent(i);
			component.setForeground(Color.green);
			((JLabel) component).setOpaque(false);
			component.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
					.getFont());
			component.setFont(component.getFont().deriveFont(16.0f));
		}

		previewPaneL.add(recap, BorderLayout.CENTER);
		previewPaneL.add(filler, BorderLayout.SOUTH);
		StageClearPanel.this.add(previewPaneL, BorderLayout.CENTER,
				new Integer(10));
	}

	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(stageClearImage, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}
}
