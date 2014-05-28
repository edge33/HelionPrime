package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface SaveManager {

	public boolean saveNewGame(PlayerSaveState playerState);
	
	public boolean overrideSave(PlayerSaveState playerState);
	
	public void loadGame(String username,Timestamp timestamp,PlayerSaveState playerState);
	
	public boolean deleteSavedGame();
	
	public ArrayList<Timestamp> fetchSaves(String username);
}
