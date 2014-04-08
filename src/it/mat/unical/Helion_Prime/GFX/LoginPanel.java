package it.mat.unical.Helion_Prime.GFX;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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
	private JLabel userLabel;

	private JButton back;
	private JButton create;
	private JButton load;
	private JButton skip; /*Verra eliminato, serve solo per farvi accedere ai menu sottostanti al loginpanel*/

	private JPanel southPanel;
	private JPanel centerPanel;

	private GridBagLayout layout;
	private GridBagConstraints c;
	
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

		this.southPanel = new JPanel();

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(layout);

		this.back = new JButton("Main Menu");
		this.create = new JButton("Create Profile");
		this.load = new JButton("Load Profile");
		this.skip = new JButton("Skip this Shit!");

		this.userLabel = new JLabel("Username:");
		this.passLabel = new JLabel("Password:");
		this.userField = new JTextField(20);
		this.passField = new JPasswordField(20);
		this.userField.setHorizontalAlignment(SwingConstants.CENTER);
		this.passField.setHorizontalAlignment(SwingConstants.CENTER);


		this.createButton();
		
		this.southPanel.add(create);
		this.southPanel.add(load);
		this.southPanel.add(back);
		this.southPanel.add(skip);

		this.addListener();
		this.fillCenterPanel();

		this.add(centerPanel,BorderLayout.CENTER);
		this.add(southPanel,BorderLayout.SOUTH);
		
		this.southPanel.setBackground(Color.BLACK);
		this.centerPanel.setBackground(Color.BLACK);
		this.centerPanel.setOpaque(false);
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
	}

	public void fillCenterPanel()
	{
		JLabel decoyLabel = new JLabel();
		JLabel decoyLabel2 = new JLabel();
		JLabel dummyLabel = new JLabel();
		JLabel dummyLabel2 = new JLabel();

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
	}
	
	public void paintComponent(Graphics g)
	{
	     g.drawImage(levelSwitchWallpaper, 0, 0, this.getWidth(), this.getHeight(),this);
	}
}
