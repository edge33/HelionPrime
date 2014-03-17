package it.mat.unical.Helion_Prime.SavesManager;

public interface SaveManager {

	public boolean saveGame();
	
	public boolean loadGame();
	
	public boolean deleteSavedGame();
	
	//dovrebbe selezionare e restituire tutti i salvataggi di un player dal db, poi la implemento
	public void fetchSaves();
}
