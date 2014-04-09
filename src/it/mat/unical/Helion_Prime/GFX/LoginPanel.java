package it.mat.unical.Helion_Prime.GFX;
import it.mat.unical.Helion_Prime.SavesManager.SaveManager;
import it.mat.unical.Helion_Prime.SavesManager.SaveManagerImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.PasswordView;


public class LoginPanel extends JPanel 
{
	private JPasswordField passField;
	private JTextField userField;

	private JLabel passLabel;
	private JLabel saveLabel;
	private JLabel userLabel;
	private JLabel bulletsDesc;
	private JLabel bulletsNumber;
	private JLabel score;
	private JLabel scoreDescr;
	
	private JComboBox<Timestamp> savedGames;

	private JButton back;
	private JButton create;
	private JButton load;
	private JButton skip; /*Verra eliminato, serve solo per farvi accedere ai menu sottostanti al loginpanel*/

	private JPanel southPanel;
	private JPanel centerPanel;
	private JPanel eastPanel;

	private GridBagLayout layout;
	private GridBagLayout eastLayout;
	private GridBagConstraints c;
	private GridBagConstraints eC;
	
	private BufferedImage levelSwitchWallpaper;
	private Cursor cursor;

	
	public LoginPanel()
	{
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel().getCursor();
		this.setCursor(cursor);
	    try { levelSwitchWallpaper = ImageIO.read(new File("Resources/optionPanelImage.jpg")); }
	    catch (IOException e) {}
		this.setLayout(new BorderLayout());
		this.layout = new GridBagLayout();
		this.c = new GridBagConstraints();
		this.c.fill = GridBagConstraints.BOTH;
		this.c.weightx = 1.0;
		this.eastLayout = new GridBagLayout();
		this.eC = new GridBagConstraints();
		this.eC.fill = GridBagConstraints.BOTH;
		this.eC.weightx = 1.0;

		this.southPanel = new JPanel();
		this.eastPanel = new JPanel();
		this.eastPanel.setLayout(eastLayout);

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(layout);

		this.back = new JButton("Main Menu");
		this.create = new JButton("Create Profile");
		this.load = new JButton("Load Profile");
		this.skip = new JButton("Skip this Shit!");

		this.userLabel = new JLabel("Username:");
		this.passLabel = new JLabel("Password:");
		this.saveLabel = new JLabel("Saved Game:");
		this.scoreDescr = new JLabel("Score");
		this.bulletsDesc = new JLabel("Bullet");
		this.score = new JLabel("0");
		this.bulletsNumber = new JLabel("0");
		
		this.savedGames = new JComboBox();
		
		this.userField = new JTextField(20);
		this.passField = new JPasswordField(20);
		this.userField.setHorizontalAlignment(SwingConstants.CENTER);
		this.passField.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.createButton();
		this.addListener();
		this.fillCenterPanel();

		this.add(centerPanel,BorderLayout.CENTER);
		this.add(southPanel,BorderLayout.SOUTH);
		this.add(eastPanel,BorderLayout.EAST);
		
		this.eastPanel.setBackground(Color.BLACK);
		this.eastPanel.setPreferredSize(new Dimension(200, 700));
		this.southPanel.setBackground(Color.BLACK);
		this.centerPanel.setBackground(Color.BLACK);
		this.centerPanel.setOpaque(false);
		this.eastPanel.setOpaque(false);
	}

	public void createButton()
	{
		userLabel.setForeground(Color.green);
		userLabel.setOpaque(false);
		userLabel.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		userLabel.setFont(userLabel.getFont().deriveFont(25.0f));
		
		passLabel.setForeground(Color.green);
		passLabel.setOpaque(false);
		passLabel.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		passLabel.setFont(userLabel.getFont().deriveFont(25.0f));
		
		saveLabel.setForeground(Color.green);
		saveLabel.setOpaque(false);
		saveLabel.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		saveLabel.setFont(saveLabel.getFont().deriveFont(25.0f));
		
		score.setForeground(Color.green);
		score.setOpaque(false);
		score.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		score.setFont(saveLabel.getFont().deriveFont(15.0f));
		
		scoreDescr.setForeground(Color.green);
		scoreDescr.setOpaque(false);
		scoreDescr.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		scoreDescr.setFont(saveLabel.getFont().deriveFont(25.0f));
		
		bulletsDesc.setForeground(Color.green);
		bulletsDesc.setOpaque(false);
		bulletsDesc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsDesc.setFont(saveLabel.getFont().deriveFont(25.0f));
		
		bulletsNumber.setForeground(Color.green);
		bulletsNumber.setOpaque(false);
		bulletsNumber.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsNumber.setFont(saveLabel.getFont().deriveFont(15.0f));
		
		load.setBackground(Color.black);
		load.setForeground(Color.green);
		load.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		load.setFont(load.getFont().deriveFont(25.0f));
		load.setBorderPainted(false);
		load.setFocusPainted(false);
		load.setBorderPainted(false);

		skip.setBackground(Color.black);
		skip.setForeground(Color.green);
		skip.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		skip.setFont(skip.getFont().deriveFont(25.0f));
		skip.setBorderPainted(false);
		skip.setFocusPainted(false);
		skip.setBorderPainted(false);

		create.setBackground(Color.black);
		create.setForeground(Color.green);
		create.setOpaque(false);
		create.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		create.setFont(create.getFont().deriveFont(25.0f));
		create.setBorderPainted(false);
		create.setFocusPainted(false);
		create.setBorderPainted(false);

		back.setBackground(Color.black);
		back.setForeground(Color.green);
		back.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		back.setFont(back.getFont().deriveFont(25.0f));
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
	}

	public void addListener()
	{
		this.back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MainMenuFrame.getInstance().switchTo(MainMenuFrame.getInstance().getMainMenuPanel());
			}
		});		

		this.skip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ModeSelectPanel m = new ModeSelectPanel();
				MainMenuFrame.getInstance().switchTo(m);

			}
		});
		
		
		this.load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if ( userField.getText() != "" ) {
					
					String username = userField.getText();
					
					ArrayList<Timestamp> profiles = SaveManagerImpl.getInstance().fetchSaves(username);
					
					for (Timestamp timestamp : profiles) {
						savedGames.addItem(timestamp);
					}
					
				}
			}
		});
	}

	public void fillCenterPanel()
	{
		JLabel decoyLabel = new JLabel();
		JLabel decoyLabel2 = new JLabel();
		JLabel dummyLabel = new JLabel();
		JLabel dummyLabel2 = new JLabel();
		JLabel fillerLabel = new JLabel();
		JLabel fillerLabel2 = new JLabel();
		
		this.southPanel.add(create);
		this.southPanel.add(load);
		this.southPanel.add(back);
		this.southPanel.add(skip);
		
		this.eastLayout.setConstraints(bulletsDesc, eC);
		this.eastPanel.add(bulletsDesc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER; 
		this.eastLayout.setConstraints(bulletsNumber, eC);
		this.eastPanel.add(bulletsNumber);
		
		this.eC.insets = new Insets(10,0,0,0); 
		this.eC.gridwidth = 1;
		
		this.eastLayout.setConstraints(scoreDescr, eC);
		this.eastPanel.add(scoreDescr);
		this.eC.gridwidth = GridBagConstraints.REMAINDER; 
		this.eastLayout.setConstraints(score, eC);
		this.eastPanel.add(score);

		this.layout.setConstraints(decoyLabel, c);
		this.centerPanel.add(decoyLabel);
		this.layout.setConstraints(userLabel, c);
		this.centerPanel.add(userLabel);
		this.layout.setConstraints(userField, c);
		this.centerPanel.add(userField);
		this.c.gridwidth = GridBagConstraints.REMAINDER; 
		this.layout.setConstraints(decoyLabel2, c);
		this.centerPanel.add(decoyLabel2);

		this.c.insets = new Insets(10,0,0,0); 
		this.c.gridwidth = 1;

		this.layout.setConstraints(dummyLabel, c);
		this.centerPanel.add(dummyLabel);
		this.layout.setConstraints(passLabel, c);
		this.centerPanel.add(passLabel);
		this.layout.setConstraints(passField, c);
		this.centerPanel.add(passField);
		this.c.gridwidth = GridBagConstraints.REMAINDER; 	
		this.layout.setConstraints(dummyLabel2, c);
		this.centerPanel.add(dummyLabel2);
		
		
		this.c.insets = new Insets(10,0,0,0); 
		this.c.gridwidth = 1;
		
		this.layout.setConstraints(fillerLabel, c);
		this.centerPanel.add(fillerLabel);
		this.layout.setConstraints(saveLabel, c);
		this.centerPanel.add(saveLabel);
		this.layout.setConstraints(savedGames, c);
		this.centerPanel.add(savedGames);
		this.c.gridwidth = GridBagConstraints.REMAINDER; 
		this.layout.setConstraints(fillerLabel2, c);
		this.centerPanel.add(fillerLabel2);
	}
	
	public void paintComponent(Graphics g)
	{
	     g.drawImage(levelSwitchWallpaper, 0, 0, this.getWidth(), this.getHeight(),this);
	}
}
