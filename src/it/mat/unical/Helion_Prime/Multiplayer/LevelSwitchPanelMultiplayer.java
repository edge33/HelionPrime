package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.GameOverPanel;
import it.mat.unical.Helion_Prime.GFX.MainGamePanel;
import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.Online.Client;

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
import javax.swing.JPanel;

public class LevelSwitchPanelMultiplayer extends JPanel {

	private BufferedImage levelSwitchWallpaper;
	private BufferedImage levelPreview;
	private JComboBox<File> comboBox;
	private JButton menuButton;
	private JButton startGameButton;
	private ServerMultiplayer server;
	private Font font;
	private boolean asNewMultiplayer = false;

	private GameOverPanel gameOverPanel;
	private Cursor cursor;

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
		this.comboBox = new JComboBox();

		for (File level : levels) {
			currentLevelName = level.getName();
			currentLevelName = currentLevelName.substring(
					currentLevelName.lastIndexOf(".") + 1,
					currentLevelName.length());
			if (!(currentLevelName.equals("jpg"))) {
				this.comboBox.addItem(level);
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
				;
				levelName = levelName.substring(0, levelName.lastIndexOf("."));
				levelName = levelName.substring(7, levelName.length());
				levelName = levelName + (".jpg");
				createPreview(levelName);
				repaint();
			}

		});

		this.startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Client client = new Client("localhost", true);

				if (LevelSwitchPanelMultiplayer.this.asNewMultiplayer) {
					client.sendMessage("Client 1 connesso (SERVER)");
					ClientManagerMultiplayer.isPlayerOne = true;
					System.out.println(client.recieveMessage());
					File choosenLevel = (File) LevelSwitchPanelMultiplayer.this.comboBox
							.getSelectedItem();
					String levelName = choosenLevel.getName();
					client.sendMessage(levelName);

					System.out.println("ATTENDO GIOCATORE 2");

					System.out.println(client.recieveMessage());

					MainGamePanel mainGamePanel = null;
					mainGamePanel = new MainGamePanel(choosenLevel, client);

					MainMenuFrame.getInstance().switchTo(mainGamePanel);

				} else {
					client.sendMessage("Client 2 connesso");
					String levelName = client.recieveMessage();
					System.out.println("livello scelto dal server" + levelName);

					File choosenLevel = new File("levels/" + levelName);

					MainGamePanel mainGamePanel = null;
					mainGamePanel = new MainGamePanel(choosenLevel, client);

					MainMenuFrame.getInstance().switchTo(mainGamePanel);

				}

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