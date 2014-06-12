package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.Server;

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

public class LevelSwitchPanel extends JPanel {

	private BufferedImage levelSwitchWallpaper;
	private BufferedImage levelPreview;
	private JComboBox<String> comboBox;
	private JButton menuButton;
	private JButton startGameButton;
	private Font font;
	private Cursor cursor;
	private Server server;

	private GameOverPanel gameOverPanel;
	protected Client client;

	public LevelSwitchPanel() {
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		try {
			levelSwitchWallpaper = ImageIO.read(new File(
					"Resources/optionPanelImage.jpg"));
		} catch (IOException e) {
		}
		this.font = MainMenuFrame.getInstance().getMainMenuPanel().getFont();
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
		this.menuButton = new JButton("Main Menu");
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

				SoundTraker.getInstance().stopClip(1);

				String choosenLevel = (String) LevelSwitchPanel.this.comboBox
						.getSelectedItem();
				String name = "levels/" + choosenLevel + ".txt";
				// //System.out.println("LevelSwitchPanel.LevelSwitchPanel    "
				// + name);
				File level = new File(name);
				// System.out
				// .println("------------------------------------------------");
				MainGamePanel mainGamePanel = null;

				try {
					server = new Server(10001);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
//					e2.printStackTrace();
				}
				server.start();
				GameManagerImpl.getInstance(0).setServer(server);
				client = new Client("localhost", Client.getDefaultNumberPort(),
						false);
				sendMessage(choosenLevel + ".txt");

				if (recieveMessage().equals("ready")) {

					// System.out.println("SIAMO READY INIZIA IL GIOCO");
					mainGamePanel = new MainGamePanel(level, client);

				}
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
				// System.out.println("manca l'universo; smetti di giocare");
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
