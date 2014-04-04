package it.mat.unical.Helion_Prime.GFX;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StageClearPanel extends JPanel {

	private JButton backToMenuButton;
	private JButton retryButton;
	private MainMenuFrame mainMenuFrame;
	private BufferedImage stageClearImage;
	private Cursor cursor;

	public StageClearPanel() {
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
		this.mainMenuFrame = MainMenuFrame.getInstance();
		this.backToMenuButton = new JButton("Back to Menu");
		this.retryButton = new JButton("Retry");
		createButton();
		this.backToMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StageClearPanel.this.mainMenuFrame
						.switchTo(StageClearPanel.this.mainMenuFrame
								.getMainMenuPanel());
			}
		});

		this.retryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StageClearPanel.this.mainMenuFrame
						.switchTo(MainMenuFrame.getInstance()
								.getMainMenuPanel().getLevelSwitchPanel());
			}
		});

		try {
			stageClearImage = ImageIO
					.read(new File("Resources/stageClear.png")); // sfondo
			// menu
			// iniziale

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.add(backToMenuButton);
		this.add(retryButton);

	}

	public void createButton() {
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

		g.drawImage(stageClearImage, 0, 0, this.getWidth(), this.getHeight(),
				this);
	}
}
