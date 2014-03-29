package it.mat.unical.Helion_Prime.EnemyEditor;

import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

public class ScrollLabel extends JLabel
{
	private int type;
	private MouseAdapter mouseAdapter = new DragMouseAdapter();
	
	public ScrollLabel(ImageIcon icon,int type)
	{
		this.type = type;
		super.setIcon(icon);
		this.addMouseListener(mouseAdapter);
		this.setTransferHandler(new TransferHandler("icon"));
	}
	

}
