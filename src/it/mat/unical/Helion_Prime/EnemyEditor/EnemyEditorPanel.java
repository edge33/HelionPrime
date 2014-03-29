package it.mat.unical.Helion_Prime.EnemyEditor;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class EnemyEditorPanel extends JPanel
{
	private BufferedImage levelSwitchWallpaper;
	private CenterPanel centerPanel;
	private ScrollPanel panel;
	private JScrollPane scrollPane;
	private NorthPanel northPanel;
	private SouthPanel southPanel;
	private JPanel contenitorPanel;
	private Font font;
	
	public EnemyEditorPanel(Font font)
	{
	    try { levelSwitchWallpaper = ImageIO.read(new File("Resources/optionPanelImage.jpg")); }
	    catch (IOException e) {}
	    this.setCursor(MainMenuFrame.getInstance().getMainMenuPanel().getCursor());
		this.font = font;
		this.setLayout(new BorderLayout());
		
		centerPanel = new CenterPanel();
		northPanel = new NorthPanel(font);
		southPanel = new SouthPanel(font);
		panel = new ScrollPanel();
		
		contenitorPanel = new JPanel();
		contenitorPanel.setLayout(new BorderLayout());
		contenitorPanel.add(BorderLayout.CENTER,centerPanel);
		contenitorPanel.add(BorderLayout.NORTH,northPanel);
		contenitorPanel.add(BorderLayout.SOUTH,southPanel);
		contenitorPanel.setBackground(new Color(0,0,0,64));
		
		scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBackground(new Color(0,0,0,64));
		
		this.add(BorderLayout.CENTER,contenitorPanel);
		this.add(BorderLayout.WEST,scrollPane);
		this.setVisible(true);
		repaint();
	}
	
	public Font getFont()
	{
		return font;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
	     g.drawImage(levelSwitchWallpaper, 0, 0, this.getWidth(), this.getHeight(),this);
	}
}
