package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;

public abstract class AbstractTrap implements Trap {

	protected int typeOfTraps;
	protected int cost;

	private int x, y;

	private int life;

	boolean active;

	public AbstractTrap() {

	}

	public AbstractTrap(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.life = life;
		this.active = true;
	}

	void setActive(boolean b) {
		active = b;
	}

	boolean isActive() {
		return active;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub

	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTypeOfTraps() {
		return typeOfTraps;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	// public int getCost() {
	// return 100;
	// }

	public void setCost(int c) {
		this.cost = c;
	}

	public abstract int getCost();

	public void effect(AbstractNative tmp) {
		// TODO Auto-generated method stub

	}
}
