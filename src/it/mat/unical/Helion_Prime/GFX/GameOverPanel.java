package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.LevelEditor.GridPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel {

	private JButton backToMenuButton;
	private JButton retryButton;
	private MainMenuFrame mainMenuFrame;
	private BufferedImage gameOverImage;
	
	public GameOverPanel() {
		
		
		this.mainMenuFrame = MainMenuFrame.getInstance();
		this.backToMenuButton = new JButton("Back to Menu");
		this.retryButton = new JButton("Retry");
		createButton();
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
		
		try {
			gameOverImage= ImageIO.read(new File("Resources/gameOver.png")); // sfondo
																			// menu
																			// iniziale

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.add(backToMenuButton);
		this.add(retryButton);
		
		
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
