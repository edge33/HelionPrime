package it.mat.unical.Helion_Prime.GFX;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.mat.unical.Helion_Prime.SavesManager.PlayerState;
import it.mat.unical.Helion_Prime.SavesManager.SavesCommand;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class SaveGameInvokerButton extends JButton {

	private SavesCommand command;
	
	
	public SaveGameInvokerButton(String text) {
		super(text);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayerState playerState = PlayerState.getInstance();
				if ( command.execute(playerState) ) {
					JOptionPane.showMessageDialog(
							MainMenuFrame.getInstance(),
							"Salvataggio effettuato, slot: "
									+ playerState.getUsername() + " "
									+ playerState.getTimeStamp());
				} else {
					JOptionPane.showMessageDialog(MainMenuFrame.getInstance(),
							"Errore Salvataggio!");
				}
			}
			
		});
	}
	
	public void setCommand(SavesCommand command) {
		this.command = command;
	}
	
	public SavesCommand getCommand() {
		return command;
	}
	
	
	
	
}
