package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface SaveManager {

	public boolean saveGame();
	
	public boolean loadGame();
	
	public boolean deleteSavedGame();
	
	//dovrebbe selezionare e restituire tutti i salvataggi di un player dal db, poi la implemento
	public ArrayList<Timestamp> fetchSaves(String username);
}
