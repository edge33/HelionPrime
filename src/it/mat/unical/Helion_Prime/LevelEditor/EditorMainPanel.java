package it.mat.unical.Helion_Prime.LevelEditor;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.GFX.MainMenuPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

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
	private JPanel contenitorPanel;


	public EditorMainPanel(MainMenuPanel mainMenuPanel) {
		try { optionWallpaper = ImageIO.read(new File("Resources/optionPanelImage.jpg")); }
		catch (IOException e) {}
		this.wavePanel = new WavePanel();
		this.mainMenuPanel = mainMenuPanel;
		this.optionsPanel = new OptionsPanel(this,mainMenuPanel);
		this.setCursor(MainMenuFrame.getInstance().getMainMenuPanel().getCursor());
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

		mapElementsPanel = new MapElementsPanel(gridPanel.getLevelStruct(),gridPanel);
		editorOptionsPanel = new EditorOptionsPanel(gridPanel,mapElementsPanel,mainMenuPanel,wavePanel);

		gridPanel.getLevelStruct().addObserver(this.mapElementsPanel);
		
		horizontalEditorPanel.setLeftComponent(mapElementsPanel);
		horizontalEditorPanel.setRightComponent(editorOptionsPanel);

		verticalEditorPanel.setLeftComponent( horizontalEditorPanel );
		verticalEditorPanel.setRightComponent( gridSplitPanel );

		gridSplitPanel.setLeftComponent(wavePanel);
		gridSplitPanel.setRightComponent(gridPanel);	
		gridSplitPanel.setDividerLocation(285);
		setVisible(true);
		gridPanel.setVisible(true);
		mapElementsPanel.setVisible(true);
		editorOptionsPanel.setVisible(true);

		customizeSplitPanel(gridSplitPanel);
		customizeSplitPanel(horizontalEditorPanel);
		customizeSplitPanel(verticalEditorPanel);

	}

	public void customizeSplitPanel(JSplitPane panel)
	{
		panel.setUI(new BasicSplitPaneUI() 
		{
			public BasicSplitPaneDivider createDefaultDivider()
			{
				return new BasicSplitPaneDivider(this)
{
					public void setBorder(Border b)
					{
					}

					@Override
					public void paint(Graphics g)
					{
						g.setColor(Color.GREEN);
						g.fillRect(0, 0, getSize().width, getSize().height);
						g.setColor(Color.BLACK);
						g.fillRect(1, 1, getSize().width-2, getSize().height-2);
						super.paint(g);
					}
				};
			}
		});
		panel.setBorder(null);
	}


	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(optionWallpaper, 0, 0, this.getWidth(), this.getHeight(),this);

	}


}
