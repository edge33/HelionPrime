package it.mat.unical.Helion_Prime.Logic;

public interface GameManager {

	public abstract boolean gameIsOver();

	public abstract void endGame();

	public abstract World getWorld();

	public abstract void update();
	
	public abstract boolean hasWon();
	
	public abstract	void stopGame();
	
}
