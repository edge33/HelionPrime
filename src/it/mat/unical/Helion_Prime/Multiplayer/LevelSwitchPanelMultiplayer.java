package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.GameOverPanel;
import it.mat.unical.Helion_Prime.GFX.MainGamePanel;
import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LevelSwitchPanelMultiplayer extends JPanel {

	private BufferedImage levelSwitchWallpaper;
	private BufferedImage levelPreview;
	private JComboBox<String> comboBox;
	private JButton menuButton;
	private JButton startGameButton;
	private ServerMultiplayer server;
	private Font font;
	private boolean asNewMultiplayer = false;

	private GameOverPanel gameOverPanel;
	private Cursor cursor;
	protected Client client;

	public LevelSwitchPanelMultiplayer(Font font, boolean asNewMultuplayer) {

		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		this.asNewMultiplayer = asNewMultuplayer;

		try {
			levelSwitchWallpaper = ImageIO.read(new File(
					"Resources/optionPanelImage.jpg"));
		} catch (IOException e) {
		}
		this.font = font;
		setVisible(true);

		File[] levels;

		File path = new File("levels");
		levels = path.listFiles();
		String currentLevelName;
		String levelExtension;
		this.comboBox = new JComboBox();
		for (File level : levels) {
			currentLevelName = level.getName();
			levelExtension = currentLevelName.substring(
					currentLevelName.lastIndexOf(".") + 1,
					currentLevelName.length());
			if (!(levelExtension.equals("jpg"))) {
				currentLevelName = currentLevelName.substring(0,
						currentLevelName.indexOf('.'));
				this.comboBox.addItem(currentLevelName);
			}
		}
		this.add(comboBox);
		this.menuButton = new JButton("Back To Menu");
		this.startGameButton = new JButton("Start Game");
		createButton();

		this.comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String levelName = String.valueOf(comboBox.getSelectedItem());
				levelName = levelName + (".jpg");
				createPreview(levelName);
				repaint();
			}

		});

		this.startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ServerMultiplayer multiplayer = new ServerMultiplayer(7777, 0);

				multiplayer
						.setLevelName((String) LevelSwitchPanelMultiplayer.this.comboBox
								.getSelectedItem());

				multiplayer.start();

				client = new Client("localhost", Client.getDefaultNumberPort(),
						true);

				sendMessage("Client 2 connesso");

				System.out.println("client" + recieveMessage());
				String numberPlayer = recieveMessage();
				if (numberPlayer.substring(0, 1).equals("1")) {
					ClientManager.isPlayerOne = true;

				} else {
					ClientManager.isPlayerOne = false;

				}
				String levelName = recieveMessage();
				System.out.println("livello scelto dal server" + levelName);

				File choosenLevel = new File("levels/" + levelName + ".txt");

				recieveMessage();

				MainGamePanel mainGamePanel = null;
				mainGamePanel = new MainGamePanel(choosenLevel, client);

				MainMenuFrame.getInstance().switchTo(mainGamePanel);

			}
		});
		this.menuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				MainMenuFrame.getInstance().switchTo(
						MainMenuFrame.getInstance().getMainMenuPanel());

			}
		});

		this.add(startGameButton);
		this.add(menuButton);

	}

	public void createButton() {
		comboBox.setBackground(Color.black);
		comboBox.setForeground(Color.green);
		comboBox.setFont(font);
		comboBox.setFont(comboBox.getFont().deriveFont(20.0f));

		menuButton.setBackground(Color.black);
		menuButton.setForeground(Color.green);
		menuButton.setFont(font);
		menuButton.setFont(menuButton.getFont().deriveFont(25.0f));
		menuButton.setBorderPainted(false);
		menuButton.setFocusPainted(false);

		startGameButton.setBackground(Color.black);
		startGameButton.setForeground(Color.green);
		startGameButton.setFont(font);
		startGameButton.setFont(startGameButton.getFont().deriveFont(25.0f));
		startGameButton.setBorderPainted(false);
		startGameButton.setFocusPainted(false);
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

	public void createPreview(String previewName) {
		previewName = ("levels/" + previewName);
		BufferedImage IOlevelPreview = null;
		try {
			IOlevelPreview = ImageIO.read(new File(previewName));
		} catch (IOException e) {
			try {
				IOlevelPreview = ImageIO
						.read(new File("Resources/missing.jpg"));
			} catch (IOException e1) {
				System.out.println("manca l'universo; smetti di giocare");
			}
		}
		levelPreview = IOlevelPreview;
		Graphics2D g = levelPreview.createGraphics();
	}

	@Override
	public void paintComponent(Graphics g) {
		boolean prima = false;
		if (prima == false) {
			g.drawImage(levelSwitchWallpaper, 0, 0, this.getWidth(),
					this.getHeight(), this);
			prima = true;
		}
		g.drawImage(levelPreview, 30, 50, 500, 500, this);
	}

}