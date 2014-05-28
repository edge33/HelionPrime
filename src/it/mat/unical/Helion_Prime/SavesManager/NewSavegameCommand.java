package it.mat.unical.Helion_Prime.SavesManager;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import javax.swing.JOptionPane;

public class NewSavegameCommand implements SavesCommand {

	@Override
	public boolean execute(PlayerSaveState playerState) {
		return SaveManagerImpl.getInstance().saveNewGame(playerState);
	}

}
