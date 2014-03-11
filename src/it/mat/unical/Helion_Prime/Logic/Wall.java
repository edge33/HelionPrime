package it.mat.unical.Helion_Prime.Logic;

public class Wall implements StaticObject {

	private int x, y;
	private int type;


	public Wall(int x, int y,int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}
}
