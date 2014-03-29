package it.mat.unical.Helion_Prime.EnemyEditor;
import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class NorthPanel extends JPanel
{
	private JComboBox<String> comboBox;
	private JButton previous;
	private JButton next;
	private JLabel mob;
	private FlowLayout layout;
	private Font font;

	public NorthPanel(Font font)
	{
		this.font = font;
		this.setBackground(new Color(0,0,0,64));
		setLayout(layout = new FlowLayout());
		setSize(550, 50);
		comboBox = new JComboBox<String>();
		initComboBox();
		previous = new JButton("<");
		mob = new JLabel("JLabel op");
		next = new JButton(">");
		createButton();
		add(comboBox);
		add(Box.createRigidArea(new Dimension(100,0)));
		add(previous);
		add(mob);
		add(next);

	}

	public void initComboBox()
	{
		File[] levels;

		File path = new File("levels");
		levels = path.listFiles();
		String currentLevelName;
		String levelExtension;
		for (File level : levels) 
		{
			currentLevelName = level.getName();
			levelExtension = currentLevelName.substring(currentLevelName.lastIndexOf(".")+1,currentLevelName.length());
			if(!(levelExtension.equals("jpg")))
			{
				currentLevelName = currentLevelName.substring(0,currentLevelName.indexOf('.'));
				this.comboBox.addItem(currentLevelName);
			}
		}
	}
	
	public void createButton()
	{
		comboBox.setBackground(Color.black);
		comboBox.setForeground(Color.green);
		comboBox.setFont(font);
		comboBox.setFont(comboBox.getFont().deriveFont(20.0f));

		previous.setBackground(Color.black);
		previous.setForeground(Color.green);
		previous.setFont(font);
		previous.setFont(previous.getFont().deriveFont(20.0f));
		previous.setBorderPainted(false);
		previous.setFocusPainted(false);

		next.setBackground(Color.black);
		next.setForeground(Color.green);
		next.setFont(font);
		next.setFont(previous.getFont().deriveFont(20.0f));
		next.setBorderPainted(false);
		next.setFocusPainted(false);
	}
}
