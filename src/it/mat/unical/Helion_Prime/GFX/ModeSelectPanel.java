package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.CommonProperties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ModeSelectPanel extends JPanel {
	private JPanel centerPanel;
	private JPanel southPanel;
	private JButton storyMode;
	private JButton freeMode;
	private JButton back;
	private JLabel storyDescr;
	private JLabel freeDescr;
	private Border storyBorder;
	private Border freeBorder;
	private LoginPanel contenitorPanel;
	private LevelSwitchPanel levelSwitchPanel;
	private Cursor cursor;
	private GridBagLayout layout;
	private GridBagConstraints c;

	public ModeSelectPanel() {
		this.centerPanel = new JPanel();
		this.southPanel = new JPanel();
		this.setLayout(new BorderLayout());

		this.layout = new GridBagLayout();
		this.c = new GridBagConstraints();
		this.centerPanel.setLayout(layout);
		this.c.fill = GridBagConstraints.BOTH;
		this.c.weightx = 1.0;

		this.storyMode = new JButton("Story Mode");
		this.freeMode = new JButton("Free Mode");
		this.back = new JButton("Main Menu");

		if (!CommonProperties.getInstance().isPropertiesLoaded()) {
			this.storyMode.setEnabled(false);
		}

		this.storyDescr = new JLabel(
				"Gioca tutti i livelli della campagna single player. Riuscirai a sopravvivere all'orda?");
		this.freeDescr = new JLabel(
				"Gioca il livello che pi� ti piace. Scala la classifica e centra il punteggio migliore!");
		this.storyDescr.setHorizontalAlignment(SwingConstants.CENTER);
		this.freeDescr.setHorizontalAlignment(SwingConstants.CENTER);

		this.addListener();
		this.fillPanel();
		this.createButton();

		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);

		this.centerPanel.setBackground(Color.BLACK);
		this.southPanel.setBackground(Color.BLACK);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	public void createButton() {
		storyMode.setBackground(Color.black);
		storyMode.setForeground(Color.green);
		storyMode.setOpaque(false);
		storyMode.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		storyMode.setFont(storyMode.getFont().deriveFont(25.0f));
		storyMode.setBorderPainted(false);
		storyMode.setFocusPainted(false);

		freeMode.setBackground(Color.black);
		freeMode.setForeground(Color.green);
		freeMode.setOpaque(false);
		freeMode.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		freeMode.setFont(freeMode.getFont().deriveFont(25.0f));
		freeMode.setBorderPainted(false);
		freeMode.setFocusPainted(false);

		back.setBackground(Color.black);
		back.setForeground(Color.green);
		back.setOpaque(false);
		back.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		back.setFont(back.getFont().deriveFont(25.0f));
		back.setBorderPainted(false);
		back.setFocusPainted(false);

	}

	public void fillPanel() {
		this.c.gridwidth = GridBagConstraints.REMAINDER;
		this.layout.setConstraints(storyMode, c);
		this.centerPanel.add(storyMode);
		this.layout.setConstraints(storyDescr, c);
		this.centerPanel.add(storyDescr);

		c.insets = new Insets(200, 0, 0, 0);
		c.gridwidth = 1;

		this.c.gridwidth = GridBagConstraints.REMAINDER;
		this.layout.setConstraints(freeMode, c);
		this.centerPanel.add(freeMode);
		c.insets = new Insets(0, 0, 0, 0);
		this.layout.setConstraints(freeDescr, c);
		this.centerPanel.add(freeDescr);

		c.insets = new Insets(200, 0, 0, 0);
		c.gridwidth = 1;

		this.southPanel.add(back);
	}

	public void addListener() {

		storyMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				contenitorPanel = new LoginPanel();

				MainMenuFrame.getInstance().getMainMenuPanel()
						.setStoryModeOn(true);
				MainMenuFrame.getInstance().switchTo(contenitorPanel);
			}
		});

		freeMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				levelSwitchPanel = new LevelSwitchPanel();
				MainMenuFrame.getInstance().getMainMenuPanel()
						.setStoryModeOn(false);
				MainMenuFrame.getInstance().switchTo(levelSwitchPanel);
			}
		});

		this.back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SoundTraker.getInstance().startClip(0);
				MainMenuFrame.getInstance().switchTo(
						MainMenuFrame.getInstance().getMainMenuPanel());

			}
		});
	}
}
