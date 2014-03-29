package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.FileNotCorrectlyFormattedException;
import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Online.Client;
import Online.Server;

public class ContenitorPanel extends JPanel
{
	private static StoryPanel centerPanel;
	private UpperPanel upperPanel;
	private JPanel lowerPanel; 
	private JButton back;
	private JPanel fillerPanelW;
	private JPanel fillerPanelE;
	private JButton startGameButton;
	private Server server;
	private Cursor cursor; 

	public ContenitorPanel()
	{
		this.back = new JButton("Main Menu");
		this.startGameButton = new JButton("Start Level");

		this.lowerPanel = new JPanel();
		this.lowerPanel.setSize(400, 50);
		this.lowerPanel.add(startGameButton);
		this.lowerPanel.add(Box.createRigidArea(new Dimension(40,40)));
		this.lowerPanel.add(back);
		this.lowerPanel.setBackground(Color.BLACK);
		createButton();
		addListener();

		this.upperPanel = new UpperPanel();
		this.upperPanel.setSize(400, 50);


		this.centerPanel = new StoryPanel(this);
		
		this.fillerPanelW = new JPanel();
		this.fillerPanelW.setBackground(Color.BLACK);
		
		this.fillerPanelE = new JPanel();
		this.fillerPanelE.setBackground(Color.BLACK);
		
		this.setSize(400,400);
		this.setLayout(new BorderLayout());

		this.add(centerPanel,BorderLayout.CENTER);
		this.add(upperPanel,BorderLayout.NORTH);
		this.add(lowerPanel,BorderLayout.SOUTH);
		this.add(fillerPanelW, BorderLayout.WEST);
		this.add(fillerPanelE, BorderLayout.EAST);
		
		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel().getCursor();
		this.setCursor(cursor);
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

		this.startGameButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				String choosenLevel = centerPanel.getLevelSelected();
				String name = "levels/"+choosenLevel;
				System.out
				.println("LevelSwitchPanel.LevelSwitchPanel    " + name);
				File level = new File(name);
				System.out.println("------------------------------------------------");
				MainGamePanel mainGamePanel = null;

				try {
					server = new Server(7777);
				} catch (IOException e2) 
				{
					e2.printStackTrace();
				}
				server.start();
				GameManagerImpl.getInstance().setServer(server);
				Client client = new Client();
				client.sendMessage(choosenLevel);

				if (client.recieveMessage().equals("ready")) {

					System.out.println("SIAMO READY INIZIA IL GIOCO");
					try {
						mainGamePanel = new MainGamePanel(level, client);

					} catch (FileNotCorrectlyFormattedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MainMenuFrame.getInstance().switchTo(mainGamePanel);
				}
				// try {
				// // mainGamePanel = new MainGamePanel(choosenLevel);
				// MainMenuFrame.getInstance().switchTo(mainGamePanel);
				// } catch (FileNotFoundException e1) {
				// JOptionPane.showMessageDialog(null,
				// "Wave per il livello selezionato, non presente!");
				// MainMenuFrame.getInstance().switchTo(LevelSwitchPanel.this);
				// }
			}
		});
	}



	public void createButton()
	{
		startGameButton.setBackground(Color.black);
		startGameButton.setForeground(Color.green);
		startGameButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		startGameButton.setFont(startGameButton.getFont().deriveFont(25.0f));
		startGameButton.setBorderPainted(false);
		startGameButton.setFocusPainted(false);
		startGameButton.setBorderPainted(false);
		
		back.setBackground(Color.black);
		back.setForeground(Color.green);
		back.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		back.setFont(back.getFont().deriveFont(25.0f));
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
	}

	public void setLevelName(String name)
	{
		upperPanel.setLevelName(name);
		upperPanel.repaint();
		System.out.println(name);
	}
}