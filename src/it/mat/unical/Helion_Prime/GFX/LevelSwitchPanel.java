package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.FileNotCorrectlyFormattedException;
import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

import java.awt.Color;
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

import Online.Server;

public class LevelSwitchPanel extends JPanel {

	private BufferedImage levelSwitchWallpaper;
	private BufferedImage levelPreview;
	private JComboBox<File> comboBox;
	private JButton menuButton;
	private JButton startGameButton;
	private Server server;
	private Font font;

	private GameOverPanel gameOverPanel;

	public LevelSwitchPanel(Font font) {
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

				File choosenLevel = (File) LevelSwitchPanel.this.comboBox
						.getSelectedItem();
				MainGamePanel mainGamePanel = null;

				try {
					server = new Server(7777);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				server.start();
				GameManagerImpl.getInstance().setServer(server);
				Client client = new Client();
				String message = choosenLevel.getName();
				client.sendMessage(message);

				if (client.recieveMessage().equals("ready")) {

					System.out.println("SIAMO READY INIZIA IL GIOCO");
					try {
						mainGamePanel = new MainGamePanel(choosenLevel, client);

					} catch (FileNotCorrectlyFormattedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MainMenuFrame.getInstance().switchTo(mainGamePanel);
				}
				// try {
				// // mainGamePanel = new MainGamePanel(choosenLevel);
				// MainMenuFrame.getInstance().switchTo(mainGamePanel);
				// } catch (FileNotFoundException e1) {
				// JOptionPane.showMessageDialog(null,
				// "Wave per il livello selezionato, non presente!");
				// MainMenuFrame.getInstance().switchTo(LevelSwitchPanel.this);
				// }
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
