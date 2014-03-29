package it.mat.unical.Helion_Prime.GFX;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MultiplayerPanel extends JPanel
{
	private JButton singlePlayer;
	private JButton multiPlayer;
	private JButton back;

	public MultiplayerPanel()
	{
		this.singlePlayer = new JButton("SinglePlayer");
		this.multiPlayer = new JButton("MultiPlayer");
		this.back = new JButton("Main Menu");

		this.back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MainMenuFrame.getInstance().switchTo(MainMenuFrame.getInstance().getMainMenuPanel());
			}
		});	

		this.add(singlePlayer);
		this.add(multiPlayer);
		this.add(back);
	}

}
