package it.mat.unical.Helion_Prime.EnemyEditor;


import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class ScrollPanel extends JPanel
{
	private JPanel panel;
	private int[] positionsArray = new int [5];
	
	private int x = 10;
	private int y = 10;
	
	private Image Usx;
	private Image Dsx;
	private Image Udx;
	private Image Ddx;
	
	private ScrollLabel UsxL;
	private ScrollLabel DsxL;
	private ScrollLabel UdxL;
	private ScrollLabel DdxL;

	


	public ScrollPanel()
	{
		try{
			Usx = ImageIO.read(new File("Resources/Pieces/USX.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Dsx= ImageIO.read(new File("Resources/Pieces/DSX.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Udx = ImageIO.read(new File("Resources/Pieces/UDX.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Ddx = ImageIO.read(new File("Resources/Pieces/DDX.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		}
		catch(Exception e)
		{
			//System.out.println("manca qualche immagine");
		}
		UsxL = new ScrollLabel(new ImageIcon(Usx),1);
		DsxL = new ScrollLabel(new ImageIcon(Dsx),2);
		UdxL = new ScrollLabel(new ImageIcon(Udx),3);
		DdxL = new ScrollLabel(new ImageIcon(Ddx),4);
		
		this.add(UdxL);
		this.add(Box.createRigidArea(new Dimension(40,40)));
		this.add(DdxL);
		this.add(Box.createRigidArea(new Dimension(40,40)));
		this.add(UsxL);
		this.add(Box.createRigidArea(new Dimension(40,40)));
		this.add(DsxL);
		this.add(Box.createRigidArea(new Dimension(40,40)));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(50, 600);
		this.setVisible(true);
		this.setBackground(new Color(0,0,0,64));
		this.setCursor(MainMenuFrame.getInstance().getMainMenuPanel().getCursor());
	}

}
