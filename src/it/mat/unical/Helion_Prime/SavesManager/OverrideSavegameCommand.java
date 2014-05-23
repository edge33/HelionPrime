package it.mat.unical.Helion_Prime.SavesManager;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import javax.swing.JOptionPane;

public class OverrideSavegameCommand implements SavesCommand {

	@Override
	public void execute() {
		PlayerState playerState = PlayerState.getInstance();
		if (SaveManagerImpl.getInstance().overrideSave(playerState)) {
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

}
