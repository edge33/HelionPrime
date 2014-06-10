package it.mat.unical.Helion_Prime.GFX;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class UpperPanel extends JPanel 
{
	private JLabel levelName;
	
	public UpperPanel() 
	{
		this.levelName = new JLabel("-");
		this.levelName.setForeground(Color.GREEN);
		this.levelName.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		this.levelName.setFont(levelName.getFont().deriveFont(25.0f));
		this.add(levelName);
	}
	
	public void setLevelName(String name)
	{
		this.levelName.setText(name);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
