package it.mat.unical.Helion_Prime.LevelEditor;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.GFX.MainMenuPanel;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class EditorMainPanel extends JPanel {
	
	private BufferedImage optionWallpaper;
	private OptionsPanel optionsPanel;
	private GridPanel gridPanel;
	private MapElementsPanel mapElementsPanel;
	private EditorOptionsPanel editorOptionsPanel;
	private JSplitPane horizontalEditorPanel;
	private JSplitPane verticalEditorPanel;
	private JSplitPane gridSplitPanel;
	private WavePanel wavePanel;
	private MainMenuPanel mainMenuPanel;
	private Cursor cursor;
	

	public EditorMainPanel(MainMenuPanel mainMenuPanel)
	{
		try { optionWallpaper = ImageIO.read(new File("Resources/optionPanelImage.jpg")); }
		catch (IOException e) {}
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel().getCursor();
		this.setCursor(cursor);
		this.wavePanel = new WavePanel();
		this.mainMenuPanel = mainMenuPanel;
		this.optionsPanel = new OptionsPanel(this,mainMenuPanel);
		
		this.add(optionsPanel);
		
		this.setVisible(true);
		
	}
	
	public void switchToGrid(int rows, int cols) {
		
		
		optionsPanel.setVisible(false);
		remove(optionsPanel);
		
		gridPanel = new GridPanel(rows,cols);
		
		
		setLayout(new BorderLayout());
		
		
				
		verticalEditorPanel = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
		gridSplitPanel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
		gridSplitPanel.setDividerLocation((int)getHeight());
		add( verticalEditorPanel, BorderLayout.CENTER );
		
		horizontalEditorPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT );
				

		mapElementsPanel = new MapElementsPanel(gridPanel.getLevelStruct());
		editorOptionsPanel = new EditorOptionsPanel(gridPanel,mapElementsPanel,mainMenuPanel,wavePanel);
				
		horizontalEditorPanel.setLeftComponent(mapElementsPanel);
		horizontalEditorPanel.setRightComponent(editorOptionsPanel);
				
		verticalEditorPanel.setLeftComponent( horizontalEditorPanel );
		verticalEditorPanel.setRightComponent( gridSplitPanel );
		
		gridSplitPanel.setLeftComponent( gridPanel );
		gridSplitPanel.setRightComponent( wavePanel );	
		setVisible(true);
		gridPanel.setVisible(true);
		mapElementsPanel.setVisible(true);
		editorOptionsPanel.setVisible(true);
		
	}


	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(optionWallpaper, 0, 0, this.getWidth(), this.getHeight(),this);
		
	}

	
}
