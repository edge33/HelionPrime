package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface SaveManager {

	public boolean saveNewGame(PlayerState playerState);
	
	public boolean overrideSave(PlayerState playerState);
	
	public void loadGame(String username,Timestamp timestamp,PlayerState playerState);
	
	public boolean deleteSavedGame();
	
	public ArrayList<Timestamp> fetchSaves(String username);
}
