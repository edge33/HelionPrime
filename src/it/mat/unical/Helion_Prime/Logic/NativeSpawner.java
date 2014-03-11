package it.mat.unical.Helion_Prime.Logic;

public class NativeSpawner implements StaticObject {

	private int x, y;

	public NativeSpawner(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

}
