package it.mat.unical.Helion_Prime.GFX;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
public class StoryPanel extends JPanel 
{
	private JLabel level;
	private ImageIcon levelPreview;
	private File[] levels;
	private File path;
	private int levelNumber;
	private MouseListener listener;
	private ContenitorPanel contenitor;
	private BufferedImage levelSwitchWallpaper;
	private String levelSelected;
	private GridBagLayout layout;
	private GridBagConstraints c;
	private boolean panelListenerOn;

	public StoryPanel(ContenitorPanel contenitor)
	{
		try { levelSwitchWallpaper = ImageIO.read(new File("Resources/optionPanelImage.jpg")); }
		catch (IOException e) {}
		this.contenitor = contenitor;
		this.levelSelected = "none";
		this.panelListenerOn = false;
		setSize(300,300);
		path = new File("levels");
		levels = path.listFiles();
		levelNumber = levels.length;
		System.out.println(levelNumber);
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		setLayout(layout);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		setBackground(Color.BLACK);
		initListener();
		fillPanel();
	}



	public void initListener()
	{
		listener = new MouseListener() 
		{

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO AVVIA LIVELLO

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO NULLA

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO SETTA NULL ALLO STRINGPANE	
				if(!panelListenerOn)
					contenitor.setLevelName(" ");

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO SETTA IL NOME ALLO STRINGPANE	
				if(!panelListenerOn)
					contenitor.setLevelName(((JLabel) arg0.getSource()).getText());
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO SE DESTRO MOSTRA PREVIEW
				if(!panelListenerOn)
				{
					if(SwingUtilities.isLeftMouseButton(arg0)==true)
					{
						levelSelected = ((JLabel) arg0.getSource()).getText();
						System.out.println(levelSelected);
					}
					else if(SwingUtilities.isRightMouseButton(arg0)==true)
					{
						contenitor.showPanel(((JLabel)arg0.getSource()).getText());

					}
				}
			}
		};
	}

	public String getLevelSelected()
	{
		return levelSelected;
	}

	public void fillPanel()
	{
		int k=1;
		for(int i=0; i<levelNumber; i++)
		{
			if(levels[i].getName().contains(".txt"))
			{
				level = new JLabel(levels[i].getName());
				level.setBorder(BorderFactory.createLineBorder(Color.GREEN,1));
				level.addMouseListener(listener);
				level.setHorizontalAlignment(SwingConstants.CENTER);
				level.setPreferredSize(new Dimension(50, 50));
				if(k!=5)
				{
					layout.setConstraints(level, c);
					this.add(level);
					this.add(Box.createRigidArea(new Dimension(40, 40)));
					k++;
				}
				else
				{
					c.gridwidth = GridBagConstraints.REMAINDER; 
					layout.setConstraints(level, c);
					this.add(level);
					k=1;
					c.insets = new Insets(10,0,0,0); 
					c.gridwidth = 1;
				}
			}
		}
	}



	@Override
	protected void paintComponent(Graphics g) 
	{
		g.drawImage(levelSwitchWallpaper, 0, 0, this.getWidth(), this.getHeight(),this);
	}



	public void disableListener(boolean b) 
	{
		panelListenerOn = b;

	}



}
