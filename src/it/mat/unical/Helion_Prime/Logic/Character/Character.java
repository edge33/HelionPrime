package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.DynamicObject;

public interface Character extends DynamicObject {

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int RIGHT = 3;
	public static final int LEFT = 2;

	public abstract int getLife();

	public abstract void setLife(int lifePoints);

	public abstract void move(int direction);

}
