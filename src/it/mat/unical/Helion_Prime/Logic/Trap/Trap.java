package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.StaticObject;

public interface Trap extends StaticObject {

	@Override
	public int getX();

	@Override
	public int getY();

	public abstract void effect();

}
