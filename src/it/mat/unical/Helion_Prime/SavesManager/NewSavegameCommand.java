package it.mat.unical.Helion_Prime.SavesManager;


public class NewSavegameCommand implements SavesCommand {

	@Override
	public boolean execute(PlayerSaveState playerState) {
		return SaveManagerImpl.getInstance().saveNewGame(playerState);
	}

}
