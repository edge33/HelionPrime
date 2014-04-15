package it.mat.unical.Helion_Prime.GFX;

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
	private JButton storyMode;
	private JButton freeMode;
	private JLabel storyDescr;
	private JLabel freeDescr;
	private Border storyBorder;
	private Border freeBorder;
	private ContenitorPanel contenitorPanel;
	private LevelSwitchPanel levelSwitchPanel;
	private Cursor cursor;
	private GridBagLayout layout;
	private GridBagConstraints c;

	public ModeSelectPanel() {
		this.layout = new GridBagLayout();
		this.c = new GridBagConstraints();
		this.setLayout(layout);
		this.c.fill = GridBagConstraints.BOTH;
		this.c.weightx = 1.0;

		this.storyMode = new JButton("Story Mode");
		this.freeMode = new JButton("Free Mode");

		this.storyDescr = new JLabel(
				"Gioca tutti i livelli della campagna single player. Riuscirai a sopravvivere all'orda?");
		this.freeDescr = new JLabel(
				"Gioca il livello che più ti piace. Scala la classifica e centra il punteggio migliore!");
		this.storyDescr.setHorizontalAlignment(SwingConstants.CENTER);
		this.freeDescr.setHorizontalAlignment(SwingConstants.CENTER);

		this.addListener();
		this.fillPanel();
		this.createButton();

		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);

		this.setBackground(Color.BLACK);
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
		storyMode.setBorderPainted(false);

		freeMode.setBackground(Color.black);
		freeMode.setForeground(Color.green);
		freeMode.setOpaque(false);
		freeMode.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		freeMode.setFont(freeMode.getFont().deriveFont(25.0f));
		freeMode.setBorderPainted(false);
		freeMode.setFocusPainted(false);
		freeMode.setBorderPainted(false);
	}

	public void fillPanel() {
		this.c.gridwidth = GridBagConstraints.REMAINDER;
		this.layout.setConstraints(storyMode, c);
		this.add(storyMode);
		this.layout.setConstraints(storyDescr, c);
		this.add(storyDescr);

		c.insets = new Insets(200, 0, 0, 0);
		c.gridwidth = 1;

		this.c.gridwidth = GridBagConstraints.REMAINDER;
		this.layout.setConstraints(freeMode, c);
		this.add(freeMode);
		c.insets = new Insets(0, 0, 0, 0);
		this.layout.setConstraints(freeDescr, c);
		this.add(freeDescr);
	}

	public void addListener() {

		storyMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contenitorPanel = new ContenitorPanel();

				MainMenuFrame.getInstance().getMainMenuPanel()
						.setStoryModeOn(true);
				MainMenuFrame.getInstance().switchTo(contenitorPanel);
			}
		});

		freeMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				levelSwitchPanel = new LevelSwitchPanel();
				MainMenuFrame.getInstance().getMainMenuPanel()
						.setStoryModeOn(false);
				MainMenuFrame.getInstance().switchTo(levelSwitchPanel);
			}
		});
	}
}
