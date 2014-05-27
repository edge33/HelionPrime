package it.mat.unical.Helion_Prime.SavesManager;


public class OverrideSavegameCommand implements SavesCommand {

	@Override
	public boolean execute(PlayerSaveState playerState) {
		return SaveManagerImpl.getInstance().overrideSave(playerState);
	}

}
