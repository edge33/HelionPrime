package it.mat.unical.Helion_Prime.EnemyEditor;
import java.awt.Color;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.border.Border;


public class CenterPanel extends JPanel
{
	private JLabel uppDX;
	private JLabel uppSX;
	private JLabel lowDX;
	private JLabel lowSX;
	private Border border;
	private MouseAdapter mouseAdapter = new DragMouseAdapter();
	
	public CenterPanel()
	{
		this.setSize(500, 500);
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.border = BorderFactory.createLineBorder(Color.GREEN, 1);
		uppDX = new JLabel();
		uppSX = new JLabel();
		lowDX = new JLabel();
		lowSX = new JLabel();
		uppDX.setBorder(border);
		uppSX.setBorder(border);
		lowDX.setBorder(border);
		lowSX.setBorder(border);
		this.addDragListener();
		this.fillPanel();
	}
	
	public void fillPanel()
	{
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		this.uppSX.setBounds((panelWidth/2)-40, (panelHeight/2), 40, 40);
		this.uppDX.setBounds((panelWidth/2), (panelHeight/2), 40, 40);
		this.lowSX.setBounds((panelWidth/2)-40, (panelHeight/2)+40, 40, 40);
		this.lowDX.setBounds((panelWidth/2), (panelHeight/2)+40, 40, 40);

		this.add(uppSX);
		this.add(uppDX);
		this.add(lowSX);
		this.add(lowDX);
	}
	
	public void addDragListener()
	{
		uppSX.addMouseListener(mouseAdapter);
		uppSX.setTransferHandler(new TransferHandler("icon"));
		
		lowDX.addMouseListener(mouseAdapter);
		lowDX.setTransferHandler(new TransferHandler("icon"));
		
		lowSX.addMouseListener(mouseAdapter);
		lowSX.setTransferHandler(new TransferHandler("icon"));
		
		uppDX.addMouseListener(mouseAdapter);
		uppDX.setTransferHandler(new TransferHandler("icon"));
	}
	
}
