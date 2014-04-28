package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.LevelEditor.GridPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameOverPanel extends JLayeredPane {


	private JPanel previewPaneL;
	private JPanel overlay;
	private JButton backToMenuButton;
	private JButton saveLevel;
	private JButton retryButton;
	private MainMenuFrame mainMenuFrame;
	private BufferedImage gameOverImage;
	private Cursor cursor;

	public GameOverPanel() {

		try {
			gameOverImage= ImageIO.read(new File("Resources/gameOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setLayout(new BorderLayout());
		this.overlay = new JPanel();
		this.overlay.setOpaque(false);
		this.previewPaneL = null;
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel().getCursor();
		this.setCursor(cursor);
		this.mainMenuFrame = MainMenuFrame.getInstance();
		this.backToMenuButton = new JButton("Back to Menu");
		this.saveLevel = new JButton("Save Level");
		this.retryButton = new JButton("Retry");
		this.createButton();
		this.addListener();

		this.add(overlay,BorderLayout.CENTER);
		this.overlay.add(backToMenuButton);
		this.overlay.add(saveLevel);
		this.overlay.add(retryButton);

	}

	public void addListener()
	{
		this.saveLevel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
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
				previewPaneL.setBackground(Color.BLACK);
				previewPaneL.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1,true));
				previewPaneL.setBounds(x, y, prevPanelX, prevPanelY);
				GameOverPanel.this.add(previewPaneL,BorderLayout.CENTER, new Integer(10));
				previewPaneL.setVisible(true);
				previewPaneL.repaint();
				repaint();

			}
		});
		this.backToMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameOverPanel.this.mainMenuFrame.switchTo(GameOverPanel.this.mainMenuFrame.getMainMenuPanel());
			}
		});

		this.retryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameOverPanel.this.mainMenuFrame.switchTo(GameOverPanel.this.mainMenuFrame.getInstance().getMainMenuPanel().getLevelSwitchPanel());
			}
		});

	}

	public void createButton()
	{
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
	}

	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(gameOverImage, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}
}
