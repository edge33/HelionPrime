package it.mat.unical.Helion_Prime.GFX;
import it.mat.unical.Helion_Prime.Multiplayer.MultiplayerPanel;
import it.mat.unical.Helion_Prime.SavesManager.PlayerState;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private JLabel bulletsGun1Desc;
	private JLabel bulletsGun2Desc;
	private JLabel bulletsGun3Desc;
	private JLabel bulletsGun4Desc;
	private JLabel bulletsGun1;
	private JLabel bulletsGun2;
	private JLabel bulletsGun3;
	private JLabel bulletsGun4;
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

	private boolean isCreateClicked = false;
	private boolean isLoadClicked = false;

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
		this.bulletsGun1Desc = new JLabel("Bullet Gun 1");
		this.bulletsGun2Desc = new JLabel("Bullet Gun 2");
		this.bulletsGun3Desc = new JLabel("Bullet Gun 3");
		this.bulletsGun4Desc = new JLabel("Bullet Gun 4");
		this.score = new JLabel("0");
		this.bulletsGun1 = new JLabel("0");
		this.bulletsGun2 = new JLabel("0");
		this.bulletsGun3 = new JLabel("0");
		this.bulletsGun4 = new JLabel("0");

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

		bulletsGun1Desc.setForeground(Color.green);
		bulletsGun1Desc.setOpaque(false);
		bulletsGun1Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun1Desc.setFont(saveLabel.getFont().deriveFont(25.0f));
		
		bulletsGun2Desc.setForeground(Color.green);
		bulletsGun2Desc.setOpaque(false);
		bulletsGun2Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun2Desc.setFont(saveLabel.getFont().deriveFont(25.0f));
		
		bulletsGun3Desc.setForeground(Color.green);
		bulletsGun3Desc.setOpaque(false);
		bulletsGun3Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun3Desc.setFont(saveLabel.getFont().deriveFont(25.0f));

		bulletsGun4Desc.setForeground(Color.green);
		bulletsGun4Desc.setOpaque(false);
		bulletsGun4Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun4Desc.setFont(saveLabel.getFont().deriveFont(25.0f));
		
		bulletsGun1.setForeground(Color.green);
		bulletsGun1.setOpaque(false);
		bulletsGun1.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun1.setFont(saveLabel.getFont().deriveFont(15.0f));
		
		bulletsGun2.setForeground(Color.green);
		bulletsGun2.setOpaque(false);
		bulletsGun2.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun2.setFont(saveLabel.getFont().deriveFont(15.0f));

		bulletsGun3.setForeground(Color.green);
		bulletsGun3.setOpaque(false);
		bulletsGun3.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun3.setFont(saveLabel.getFont().deriveFont(15.0f));
		
		bulletsGun4.setForeground(Color.green);
		bulletsGun4.setOpaque(false);
		bulletsGun4.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun4.setFont(saveLabel.getFont().deriveFont(15.0f));
		
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
		this.create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(!isCreateClicked)
				{
					userLabel.setText("New Username:");
					passLabel.setText("New Password:");
					bulletsGun1Desc.setVisible(false);
					bulletsGun1.setVisible(false);
					score.setVisible(false);
					scoreDescr.setVisible(false);
					savedGames.setVisible(false);
					saveLabel.setVisible(false);
					create.setText("Done!");
					load.setEnabled(false);
					isCreateClicked = true;
				}
				else
				{
					userLabel.setText("Username:");
					passLabel.setText("Password:");
					bulletsGun1Desc.setVisible(true);
					bulletsGun1.setVisible(true);
					score.setVisible(true);
					scoreDescr.setVisible(true);
					savedGames.setVisible(true);
					saveLabel.setVisible(true);
					create.setText("Create Profile");
					load.setEnabled(true);
					isCreateClicked = false;
				}


			}
		});
		
		this.load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{
//				if(!isLoadClicked)
//				{
//					userLabel.setText("Your Username:");
//					passLabel.setText("Your Password:");
//					bulletsDesc.setVisible(false);
//					bulletsNumber.setVisible(false);
//					score.setVisible(false);
//					scoreDescr.setVisible(false);
//					savedGames.setVisible(false);
//					saveLabel.setVisible(false);
//					load.setText("Load Me!");
//					create.setEnabled(false);
//					isLoadClicked = true;
//				}
//				else
//				{
//					userLabel.setText("Username:");
//					passLabel.setText("Password:");
//					bulletsDesc.setVisible(true);
//					bulletsNumber.setVisible(true);
//					score.setVisible(true);
//					scoreDescr.setVisible(true);
//					savedGames.setVisible(true);
//					saveLabel.setVisible(true);
//					load.setText("Load Profile");
//					create.setEnabled(true);
//					isLoadClicked = false;
//				}


			}
		});
		
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
				MultiplayerPanel m = new MultiplayerPanel(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
				MainMenuFrame.getInstance().switchTo(m);

			}
		});


		this.load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if ( userField.getText().length() > 0 ) {
					
					savedGames.removeAllItems();
					
					String username = userField.getText();

					ArrayList<Timestamp> profiles = SaveManagerImpl.getInstance().fetchSaves(username);

					for (Timestamp timestamp : profiles) {
						savedGames.addItem(timestamp);
					}

				}
			}
		});
		

		this.savedGames.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				PlayerState playerstate =  PlayerState.getInstance().loadProfile(userField.getText(), (Timestamp) savedGames.getSelectedItem());
				score.setText(String.valueOf( playerstate.getScore() ));
				bulletsGun1.setText(String.valueOf( playerstate.getGunBullets1() ));
				bulletsGun2.setText(String.valueOf( playerstate.getGunBullets2() ));
				bulletsGun3.setText(String.valueOf( playerstate.getGunBullets3() ));
				bulletsGun4.setText(String.valueOf( playerstate.getGunBullets4() ));
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

		this.eastLayout.setConstraints(bulletsGun1Desc, eC);
		this.eastPanel.add(bulletsGun1Desc);
		this.eastLayout.setConstraints(bulletsGun2Desc, eC);
		this.eastPanel.add(bulletsGun2Desc);
		this.eastLayout.setConstraints(bulletsGun3Desc, eC);
		this.eastPanel.add(bulletsGun3Desc);
		this.eastLayout.setConstraints(bulletsGun4Desc, eC);
		this.eastPanel.add(bulletsGun4Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER; 
		this.eastLayout.setConstraints(bulletsGun1, eC);
		this.eastPanel.add(bulletsGun1);
		this.eastLayout.setConstraints(bulletsGun2, eC);
		this.eastPanel.add(bulletsGun2);
		this.eastLayout.setConstraints(bulletsGun3, eC);
		this.eastPanel.add(bulletsGun3);
		this.eastLayout.setConstraints(bulletsGun4, eC);
		this.eastPanel.add(bulletsGun4);

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
