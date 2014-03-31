package it.mat.unical.Helion_Prime.GFX;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PreviewPanel extends JPanel
{
	private JButton button;
	private JPanel southPanel;
	private ContenitorPanel panel;

	private BufferedImage levelPreview;

	public PreviewPanel(final ContenitorPanel panel, String name) {
		this.panel = panel;
		this.southPanel = new JPanel();
		this.southPanel.setBackground(Color.BLACK);
		this.southPanel.setOpaque(false);
		this.button = new JButton("Hide Preview");
		this.setLayout(new BorderLayout());
		this.button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				panel.enableListener();
				setVisible(false);

			}
		});
		this.createPreview(name);
		this.createButton();
		this.southPanel.add(button);
		this.add(southPanel, BorderLayout.SOUTH);

	}

	public void createPreview(String name)
	{
		name = name.replace(".txt", ".jpg");
		name = ("levels/" + name);
		BufferedImage IOlevelPreview = null;
		try {IOlevelPreview = ImageIO.read(new File(name));}
		catch (IOException e)
		{
			try {IOlevelPreview = ImageIO.read(new File("Resources/missing.jpg"));}
			catch (IOException e1) {System.out.println("manca l'universo; smetti di giocare");}
		}
		levelPreview = IOlevelPreview;
		Graphics2D g = levelPreview.createGraphics();
	}
	
	public void createButton()
	{
	    button.setBackground(Color.black);
	    button.setForeground(Color.green);
	    button.setOpaque(false);
	    button.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
	    button.setFont(button.getFont().deriveFont(20.0f));
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		g.setColor(Color.BLACK);
		g.fillRect( 0, 0, this.getWidth(), this.getHeight());
		g.drawImage(levelPreview, 10, 10, this.getWidth()-20, this.getHeight()-20,this);
	}

}
