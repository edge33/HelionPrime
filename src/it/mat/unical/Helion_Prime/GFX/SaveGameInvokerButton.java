package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.SavesManager.PlayerSaveState;
import it.mat.unical.Helion_Prime.SavesManager.SavesCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class SaveGameInvokerButton extends JButton {

	private SavesCommand command;
	
	
	public SaveGameInvokerButton(String text,SavesCommand command) {
		super(text);
		this.command = command;
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayerSaveState playerState = PlayerSaveState.getInstance();
				if ( SaveGameInvokerButton.this.command.execute(playerState) ) {
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
