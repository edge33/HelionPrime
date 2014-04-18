package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.EnemyEditor.EnemyEditorPanel;
import it.mat.unical.Helion_Prime.LevelEditor.EditorMainPanel;
import it.mat.unical.Helion_Prime.Multiplayer.LevelSwitchPanelMultiplayer;

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
	private ModeSelectPanel modePanel;
	private LoginPanel loginPanel;

	private BufferedImage menuWallpaper;
	private JPanel southPane;
	private JPanel centerPane;
	private JRadioButton musicButton;
	private JButton playButton;
	private JButton editorButton;
	private JButton enemyEditor;
	private JButton escButton;
	private JButton multiplayerButton;
	private Container menuPane;
	private Cursor cursor;

	private Clip clip;
	private Font font;

	private boolean isStoryModeOn = false;

	public MainMenuPanel() {

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

		musicButton.addActionListener(new ActionListener() {

			private boolean musicIsOn = true;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!musicIsOn) {
					// clip.start();
					musicIsOn = true;
				} else {
					// clip.stop();
					musicIsOn = false;
				}
			}
		});

		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modePanel = new ModeSelectPanel();
				MainMenuFrame.getInstance().switchTo(modePanel);

			}
		});

		multiplayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuPanel.this.loginPanel = new LoginPanel();
				MainMenuFrame.getInstance().switchTo(loginPanel);
			}
		});

		editorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuPanel.this.editorMainPanel = new EditorMainPanel(
						MainMenuPanel.this);
				MainMenuFrame.getInstance().switchTo(editorMainPanel);
			}
		});
		enemyEditor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuPanel.this.enemyEditorPanel = new EnemyEditorPanel(font);
				MainMenuFrame.getInstance().switchTo(enemyEditorPanel);
			}

		});

		escButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuFrame.getInstance().dispose();
				System.exit(1);
			}
		});

		//playMusic();
		setLayout(new BorderLayout());
		musicButton.setBounds(10, 1, 150, 150);
		centerPane.add(musicButton);
		southPane.add(playButton);
		southPane.add(multiplayerButton);
		southPane.add(editorButton);
		southPane.add(enemyEditor);
		southPane.add(escButton);
		add(southPane, BorderLayout.SOUTH);
		add(centerPane, BorderLayout.CENTER);

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
		createCustomCursor();
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
			System.out.println("Error with playing sound.");
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

	public void createCustomCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("Resources/Cursor.png");
		Point hotSpot = new Point(0, 0);
		cursor = toolkit.createCustomCursor(image, hotSpot, "Pencil");
		setCursor(cursor);
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
