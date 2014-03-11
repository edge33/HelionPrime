package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.EnemyEditor.EnemyEditorPanel;
import it.mat.unical.Helion_Prime.LevelEditor.EditorMainPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
	private EditorMainPanel editorMainPanel;
	private EnemyEditorPanel enemyEditorPanel;

	private BufferedImage menuWallpaper;
	private JPanel southPane;
	private JRadioButton musicButton;
	private JButton playButton;
	private JButton editorButton;
	private JButton enemyEditor;
	private JButton escButton;
	private Container menuPane;

	private Clip clip;
	private Font font;

	public MainMenuPanel() {

		this.setLayout(new BorderLayout());

		southPane = new JPanel();
		southPane.setLayout(new FlowLayout());
		southPane.setBackground(Color.BLACK);

		musicButton = new JRadioButton("No Music");
		playButton = new JButton("Play");
		editorButton = new JButton("Editor Mode");
		enemyEditor = new JButton("Create Mode");
		escButton = new JButton("Esci");

		musicButton.addActionListener(new ActionListener() {

			private boolean musicIsOn = true;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!musicIsOn) {
					clip.start();
					musicIsOn = true;
				} else {
					clip.stop();
					musicIsOn = false;
				}
			}
		});

		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuPanel.this.levelSwitchPanel = new LevelSwitchPanel(font);
				MainMenuFrame.getInstance().switchTo(levelSwitchPanel);
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
				MainMenuPanel.this.enemyEditorPanel = new EnemyEditorPanel();
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

		playMusic();
		setLayout(new BorderLayout());

		southPane.add(musicButton);
		southPane.add(playButton);
		southPane.add(editorButton);
		southPane.add(enemyEditor);
		southPane.add(escButton);
		add(southPane, BorderLayout.SOUTH);

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
		musicButton.setBackground(Color.black);
		musicButton.setForeground(Color.green);
		musicButton.setFont(font);
		musicButton.setFont(musicButton.getFont().deriveFont(20.0f));
		musicButton.setBorderPainted(false);
		musicButton.setFocusPainted(false);
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

	public LevelSwitchPanel getLevelSwitchPanel() {
		return levelSwitchPanel;
	}

	public Font getFont() {
		return font;
	}

	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(menuWallpaper, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}

}
