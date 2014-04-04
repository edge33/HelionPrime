package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MultiplayerPanel extends JPanel {
	private JButton newMultiplayer;
	private JButton joinMultiplayer;
	private Font font;
	private ServerMultiplayer serverMultiplayer;
	private boolean asNewMultiplayer = false;

	public MultiplayerPanel(final Font font) {

		this.setLayout(new FlowLayout());
		newMultiplayer = new JButton("Nuova Partita");
		joinMultiplayer = new JButton("Partecipa");

		this.font = font;
		this.add(newMultiplayer);
		this.add(joinMultiplayer);
		this.setVisible(true);

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
				LevelSwitchPanelMultiplayer levelSwitchPanelMultiplayer = new LevelSwitchPanelMultiplayer(
						font, false);

				MainMenuFrame.getInstance().switchTo(
						levelSwitchPanelMultiplayer);
			}
		});

	}
}
