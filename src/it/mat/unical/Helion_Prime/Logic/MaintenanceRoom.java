package it.mat.unical.Helion_Prime.Logic;

public class MaintenanceRoom implements StaticObject {

	private int x, y;
	private int life;

	public MaintenanceRoom(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.life = life;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	


}


