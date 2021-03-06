package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.EnemyEditor.EnemyEditorPanel;
import it.mat.unical.Helion_Prime.LevelEditor.EditorMainPanel;
import it.mat.unical.Helion_Prime.Multiplayer.LevelSwitchPanelMultiplayer;
import it.mat.unical.Helion_Prime.Multiplayer.MacchinaServer;
import it.mat.unical.Helion_Prime.Multiplayer.MultiplayerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

public class MainMenuPanel extends JPanel {

	private LevelSwitchPanel levelSwitchPanel;
	private LevelSwitchPanelMultiplayer levelSwitchPanelMultiplayer;
	private EditorMainPanel editorMainPanel;
	private EnemyEditorPanel enemyEditorPanel;
	private ModeSelectPanel modeSelect;
	private MultiplayerPanel multiplayerPanel;

	private BufferedImage menuWallpaper;
	private JPanel southPane;
	private JPanel centerPane;
	private JRadioButton musicButton;
	private JButton playButton;
	private JButton editorButton;
	private JButton enemyEditor;
	private JButton escButton;
	private JButton multiplayerButton;
	private JButton serverMachine;
	private Container menuPane;
	private Cursor cursor;
	public static boolean musicIsOn = true;
	private Clip clip;
	private Font font;

	private boolean isStoryModeOn = false;

	public MainMenuPanel() {

		SoundTraker.getInstance();

		SoundTraker.getInstance().startClip(1);

		this.setLayout(new BorderLayout());
		centerPane = new JPanel();
		centerPane.setLayout(null);
		centerPane.setOpaque(false);
		southPane = new JPanel();
		southPane.setBackground(Color.BLACK);

		musicButton = new JRadioButton("No Music");
		multiplayerButton = new JButton("MultiPlayer");
		playButton = new JButton("Play");
		editorButton = new JButton("Editor Mode");
		enemyEditor = new JButton("Create Mode");
		escButton = new JButton("Esci");
		serverMachine = new JButton("Server Machine");

		musicButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!musicIsOn) {

					musicIsOn = true;
					SoundTraker.getInstance().startClip(1);

				} else {

					musicIsOn = false;
					SoundTraker.getInstance().stopAll();
				}
			}
		});

		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modeSelect = new ModeSelectPanel();
				MainMenuFrame.getInstance().switchTo(modeSelect);

				SoundTraker.getInstance().startClip(0);

			}
		});

		multiplayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				MainMenuPanel.this.multiplayerPanel = new MultiplayerPanel(font);
				MainMenuFrame.getInstance().switchTo(multiplayerPanel);
			}
		});

		serverMachine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				MacchinaServer macchinaServer = new MacchinaServer();
				macchinaServer.startServers();
				macchinaServer.start();

			}
		});

		editorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				MainMenuPanel.this.editorMainPanel = new EditorMainPanel(
						MainMenuPanel.this);
				MainMenuFrame.getInstance().switchTo(editorMainPanel);
			}
		});
		enemyEditor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				MainMenuPanel.this.enemyEditorPanel = new EnemyEditorPanel(font);
				MainMenuFrame.getInstance().switchTo(enemyEditorPanel);
			}

		});

		escButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				MainMenuFrame.getInstance().dispose();
				System.exit(1);
			}
		});

		// playMusic();
		setLayout(new BorderLayout());
		musicButton.setBounds(10, 1, 150, 150);
		centerPane.add(musicButton);
		southPane.add(playButton);
		southPane.add(multiplayerButton);
		southPane.add(editorButton);
		// southPane.add(enemyEditor);
		southPane.add(escButton);
		add(southPane, BorderLayout.SOUTH);
		add(centerPane, BorderLayout.CENTER);

		southPane.add(serverMachine);

		setVisible(true);

		this.menuWallpaper = null;

		try {
			menuWallpaper = ImageIO.read(new File("Resources/Alien.jpg")); // sfondo
			// menu
			// iniziale
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createButton();
		createCustomCursor(null);
		this.setCursor(cursor);

	}

	public void createButton() {
		UIManager.put("Button.select", Color.black);
		// Il Try-Catch rende indipendente l'utilizzare il font Halo3 nel
		// programma dall'installarlo sulla macchina
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(
					"Resources/Halo3.ttf"));
		} catch (Exception ex) {
			ex.printStackTrace();
			font = new Font("serif", Font.PLAIN, 24);
		}
		musicButton.setOpaque(false);
		musicButton.setForeground(Color.green);
		musicButton.setFont(font);
		musicButton.setFont(musicButton.getFont().deriveFont(15.0f));
		musicButton.setBorderPainted(false);
		musicButton.setFocusPainted(false);

		multiplayerButton.setBackground(Color.black);
		multiplayerButton.setForeground(Color.green);
		multiplayerButton.setFont(font);
		multiplayerButton
				.setFont(multiplayerButton.getFont().deriveFont(25.0f));
		multiplayerButton.setBorderPainted(false);
		multiplayerButton.setFocusPainted(false);

		playButton.setBackground(Color.black);
		playButton.setForeground(Color.green);
		playButton.setFont(font);
		playButton.setFont(playButton.getFont().deriveFont(25.0f));
		playButton.setBorderPainted(false);
		playButton.setFocusPainted(false);

		editorButton.setBackground(Color.black);
		editorButton.setForeground(Color.green);
		editorButton.setFont(font);
		editorButton.setFont(editorButton.getFont().deriveFont(25.0f));
		editorButton.setBorderPainted(false);
		editorButton.setFocusPainted(false);
		editorButton.setBorderPainted(false);

		enemyEditor.setBackground(Color.black);
		enemyEditor.setForeground(Color.green);
		enemyEditor.setFont(font);
		enemyEditor.setFont(musicButton.getFont().deriveFont(25.0f));
		enemyEditor.setBorderPainted(false);
		enemyEditor.setFocusPainted(false);

		escButton.setBackground(Color.black);
		escButton.setForeground(Color.green);
		escButton.setFont(font);
		escButton.setFont(escButton.getFont().deriveFont(25.0f));
		escButton.setBorderPainted(false);
		escButton.setFocusPainted(false);
		escButton.setBorderPainted(false);

		serverMachine.setBackground(Color.black);
		serverMachine.setForeground(Color.green);
		serverMachine.setFont(font);
		serverMachine.setFont(escButton.getFont().deriveFont(25.0f));
		serverMachine.setBorderPainted(false);
		serverMachine.setFocusPainted(false);
		serverMachine.setBorderPainted(false);
	}

	public void playMusic() {

		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("Ost/Lucian.wav")
							.getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			// System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}

	}

	public void setStoryModeOn(boolean value) {
		isStoryModeOn = value;
	}

	public boolean isStoryModeOn() {

		return isStoryModeOn;
	}

	public LevelSwitchPanel getLevelSwitchPanel() {
		return levelSwitchPanel;
	}

	public Font getFont() {
		return font;
	}

	public Cursor createCustomCursor(Image img) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		if (img == null) {
			Image image = toolkit.getImage("Resources/Cursor.png");
			Point hotSpot = new Point(0, 0);
			cursor = toolkit.createCustomCursor(image, hotSpot, "Pencil");
		} else {
			Point hotSpot = new Point(0, 0);
			// System.out.println("sono nell'else");
			cursor = toolkit.createCustomCursor(img, hotSpot, "Pencil");
		}

		return cursor;
	}

	public Cursor getCursor() {
		return cursor;
	}

	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(menuWallpaper, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}

}
