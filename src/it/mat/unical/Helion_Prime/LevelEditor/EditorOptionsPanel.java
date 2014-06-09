package it.mat.unical.Helion_Prime.LevelEditor;


import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.GFX.MainMenuPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EditorOptionsPanel extends JPanel {


	private MainMenuFrame mainMenuFrame;

	private GridPanel gridPanel;
	private WavePanel wavePanel;
	private JButton saveButton;
	private JButton resetButton;
	private JButton loadButton;
	private JButton showfloorButton;
	private JButton backButton;

	private boolean showFloor;

	private MapElementsPanel mapElementsPanel;

	private MainMenuPanel mainMenuPanel;

	public EditorOptionsPanel(GridPanel gridPanel,MapElementsPanel mapElementsPanel,MainMenuPanel mainMenuPanel, WavePanel wavePanel) {


		this.mainMenuPanel = mainMenuPanel;
		this.mainMenuFrame = MainMenuFrame.getInstance();
		this.wavePanel =wavePanel;
		this.gridPanel = gridPanel;
		this.mapElementsPanel = mapElementsPanel;

		this.setLayout(new FlowLayout());

		this.saveButton = new JButton("Salva");
		this.resetButton = new JButton("Reset");
		this.loadButton = new JButton("Carica");		
		this.showfloorButton = new JButton ("Mostra Pavimento");
		this.backButton = new JButton("Main Menu");

		this.add(saveButton);
		this.add(resetButton);
		this.add(loadButton);
		this.add(showfloorButton);
		this.add(backButton);
		this.addListener();
		this.createButton();
	}
	
	public void createButton()
	{
		backButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		backButton.setFont(backButton.getFont().deriveFont(15.0f));
		backButton.setBorderPainted(false);
		//backButton.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		
		showfloorButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		showfloorButton.setFont(showfloorButton.getFont().deriveFont(15.0f));
		showfloorButton.setBorderPainted(false);
		//showfloorButton.setBorder(BorderFactory.createLineBorder(Color.green, 1));
	
		saveButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		saveButton.setFont(saveButton.getFont().deriveFont(15.0f));
		saveButton.setBorderPainted(false);
		//saveButton.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		
		loadButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		loadButton.setFont(loadButton.getFont().deriveFont(15.0f));
		loadButton.setBorderPainted(false);
		//loadButton.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		
		resetButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		resetButton.setFont(resetButton.getFont().deriveFont(15.0f));
		resetButton.setBorderPainted(false);
		//resetButton.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(MainMenuFrame.getInstance().getMainMenuPanel().createCustomCursor(null));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void showFloor()
	{
		if( showFloor ==false )
		{
			showFloor=true;
			for(int i=0;i<gridPanel.getLevelStruct().getRow();i++)
			{
				for(int j=0;j<gridPanel.getLevelStruct().getColumn();j++)
				{
					if(gridPanel.getLevelStruct().getElementAt(i, j)==0)
					{
						gridPanel.getLevelStruct().setElementAt(i, j, 4);
					}
				}
			}
		}

		gridPanel.repaint();
	}
	public void hideFloor()
	{
		if(showFloor==true)
		{
			showFloor=false;
			for(int i=0;i<gridPanel.getLevelStruct().getRow();i++)
			{
				for(int j=0;j<gridPanel.getLevelStruct().getColumn();j++)
				{
					if(gridPanel.getLevelStruct().getElementAt(i, j)==4)
					{
						gridPanel.getLevelStruct().setElementAt(i, j, 0);
					}
				}
			}
		}		
		gridPanel.repaint();
	}

	public void addListener()
	{

		showfloorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(! EditorOptionsPanel.this.showFloor ) {
					showFloor();
					showfloorButton.setText("Nascondi Pavimento");
					EditorOptionsPanel.this.showFloor = true;
				}
				else {
					hideFloor();
					showfloorButton.setText("Mostra Pavimento");
					EditorOptionsPanel.this.showFloor =false;
				}
			}
		});		

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					UIManager.setLookAndFeel(OptionsPanel.getOriginalLookAndFeel());
					setCursor(MainMenuFrame.getInstance().getMainMenuPanel().createCustomCursor(null));
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				EditorOptionsPanel.this.mainMenuFrame.switchTo(EditorOptionsPanel.this.mainMenuPanel);
			}
		});


		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveLevel();

			}
		});


		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					loadLevel();
				} catch ( Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});


		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditorOptionsPanel.this.gridPanel.getLevelStruct().initMatrix();
				String a = "Editor Resettato!";
				JOptionPane.showMessageDialog(EditorOptionsPanel.this, a);
				EditorOptionsPanel.this.gridPanel.repaint();
				mapElementsPanel.resetButtons();
			}

		});
	}


	public void saveLevel()
	{
		boolean room=false;
		boolean spawn=false;
		boolean enemy=false;
		final JFileChooser fileChooser = new JFileChooser();
		for(int i=0;i<gridPanel.getLevelStruct().getRow();i++)
		{
			for(int j=0;j<gridPanel.getLevelStruct().getColumn();j++)
			{
				switch(gridPanel.getLevelStruct().getElementAt(i, j))
				{
				case 2:
					enemy=true;
					break;
				case 3:
					spawn=true;
					break;
				case 6:
					room=true;
					break;
				}
			}
		}
		if(enemy==true && spawn==true && room==true)
		{
			final int response = fileChooser.showSaveDialog(this);
			if (response == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					final PrintWriter out = new PrintWriter(fileChooser.getSelectedFile());
					out.println("1000");
					out.println(gridPanel.getLevelStruct().getRow() + " " + gridPanel.getLevelStruct().getColumn());
					for (int i = 0; i < gridPanel.getLevelStruct().getRow(); i++)
					{
						for (int j = 0; j < gridPanel.getLevelStruct().getColumn(); j++)
						{
							if(gridPanel.getLevelStruct().getElementAt(i, j)>9)
							{
								out.print(gridPanel.getLevelStruct().getElementAt(i, j));
								out.print(" ");
							}
							else
							{
								if(gridPanel.getLevelStruct().getElementAt(i, j)==4)
									out.print(0);
								else
									out.print(gridPanel.getLevelStruct().getElementAt(i, j));
								out.print("  ");
							}
						}
						out.println();
					}
					out.close();
					String path = fileChooser.getCurrentDirectory().toString(); 
					String name = fileChooser.getSelectedFile().getName();
					name = name.substring(0, name.lastIndexOf("."));
					name = takeSnapShot(gridPanel, path, name);
					saveWave(name);
					JOptionPane.showMessageDialog(null,"Livello Salvato Correttamente!");
				}
				catch (final IOException e1)
				{
					JOptionPane.showMessageDialog(null, "Impossibile Salvare Livello. Contattare Ricca");
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Mancano alcuni componenti essenziali, aggiungili o contatta Ricca");
		}
	}


	public String takeSnapShot(GridPanel panel, String path, String name)
	{
		showFloor();
		String absolutePath = path;
		String localName = name;
		BufferedImage bufImage = new BufferedImage(gridPanel.getCols()*50, panel.getHeight(),BufferedImage.TYPE_INT_RGB);  
		panel.paint(bufImage.createGraphics());
		absolutePath.replace('\\', '/');
		String pathfinale = new String (absolutePath + "/" + name + ".jpg");
		try{   
			ImageIO.write(bufImage, "jpg", new File(pathfinale));  
		}catch(Exception ex){  
		}  
		return name;
	}  

	public void saveWave(String name)
	{
		try {
			final PrintWriter out = new PrintWriter(new File("waves/" + name + ".txt"));
			out.println("0 " + wavePanel.getSoldierNumber());
			out.println("1 " + wavePanel.getBountyNumber());
			out.print("2 " + wavePanel.getSaboteruNumber());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadLevel() throws Exception {

		File loadedFile;
		String path;
		final JFileChooser fileChooser = new JFileChooser();
		int returnTipe=fileChooser.showOpenDialog(this);

		if(returnTipe==0)
		{
			path=fileChooser.getSelectedFile().getAbsolutePath();

			loadedFile = new File(path);

			gridPanel.loadStructFromFile(loadedFile);

			mapElementsPanel.setLevelStruct(gridPanel.getLevelStruct());

			gridPanel.repaint();
		}
	}

}


