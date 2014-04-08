package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MultiplayerPanel extends JPanel {
	private JButton newMultiplayer;
	private JButton joinMultiplayer;
	private JLabel newDescr;
	private JLabel joinDescr;
	private Font font;
	private ServerMultiplayer serverMultiplayer;
	private boolean asNewMultiplayer = false;
	private GridBagLayout layout;
	private GridBagConstraints c;
	private Cursor cursor;
	public MultiplayerPanel(final Font font)
	{

		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel().getCursor();
		this.setCursor(cursor);
		this.layout = new GridBagLayout();
		this.c = new GridBagConstraints();
		this.setLayout(layout);
		this.c.fill = GridBagConstraints.BOTH;
		this.c.weightx = 1.0;

		this.newMultiplayer = new JButton("Nuova Partita");
		this.newDescr = new JLabel("Crea una lobby e attendi un compagno d'arme!");
		this.joinMultiplayer = new JButton("Partecipa");
		this.joinDescr = new JLabel("Partecipa ad una partita già esistente, due sopravvissuti sono meglio di uno!");
		this.newDescr.setHorizontalAlignment(SwingConstants.CENTER);
		this.joinDescr.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		this.font = font;
		this.setVisible(true);
		this.setLayout(layout);
		
		this.createButton();
		this.addListener();
		this.fillPanel();

		this.setBackground(Color.BLACK);

	}
	public void fillPanel()
	{
		this.c.gridwidth = GridBagConstraints.REMAINDER; 
		this.layout.setConstraints(newMultiplayer, c);
		this.add(newMultiplayer);
		this.layout.setConstraints(newDescr, c);
		this.add(newDescr);
		
		c.insets = new Insets(200,0,0,0); 
		c.gridwidth = 1;
		
		this.c.gridwidth = GridBagConstraints.REMAINDER; 
		this.layout.setConstraints(joinMultiplayer, c);
		this.add(joinMultiplayer);
		c.insets = new Insets(0,0,0,0); 
		this.layout.setConstraints(joinDescr, c);
		this.add(joinDescr);
	}

	
	public void createButton()
	{
		newMultiplayer.setBackground(Color.black);
		newMultiplayer.setForeground(Color.green);
		newMultiplayer.setOpaque(false);
		newMultiplayer.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		newMultiplayer.setFont(newMultiplayer.getFont().deriveFont(25.0f));
		newMultiplayer.setBorderPainted(false);
		newMultiplayer.setFocusPainted(false);
		newMultiplayer.setBorderPainted(false);
		
		joinMultiplayer.setBackground(Color.black);
		joinMultiplayer.setForeground(Color.green);
		joinMultiplayer.setOpaque(false);
		joinMultiplayer.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		joinMultiplayer.setFont(joinMultiplayer.getFont().deriveFont(25.0f));
		joinMultiplayer.setBorderPainted(false);
		joinMultiplayer.setFocusPainted(false);
		joinMultiplayer.setBorderPainted(false);
	}
	
	public void addListener()
	{
		newMultiplayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LevelSwitchPanelMultiplayer levelSwitchPanelMultiplayer = new LevelSwitchPanelMultiplayer(
						font, true);
				MultiplayerPanel.this.serverMultiplayer = new ServerMultiplayer(
						7777);
				GameManagerImpl.getInstance().setServerMultiplayer(
						serverMultiplayer);
				MultiplayerPanel.this.serverMultiplayer.start();
				MainMenuFrame.getInstance().switchTo(
						levelSwitchPanelMultiplayer);
			}
		});

		joinMultiplayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WaitLobbyPanel waitPanel= new WaitLobbyPanel();

				MainMenuFrame.getInstance().switchTo(waitPanel);
			}
		});
	}
}
