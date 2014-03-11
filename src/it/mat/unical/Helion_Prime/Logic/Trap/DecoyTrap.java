package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;

public class DecoyTrap extends AbstractTrap { 
	
	private World innerWorld;
	private int decoyLife;
	
	public DecoyTrap(int positionXonMap, int positionYonMap, int life,World world)
	{
		super(positionXonMap, positionYonMap, life);
		decoyLife=5;
		innerWorld = world;
		innerWorld.setDecoy(this);
		innerWorld.fakeOn();
		innerWorld.setFakeX(positionXonMap);
		innerWorld.setFakeY(positionYonMap);
	}

	@Override
	public void effect(AbstractNative currentNative) 
	{
		
		if(decoyLife==0)
		{
			currentNative.setLife(0);
			innerWorld.setFakeX(0);
			innerWorld.setFakeY(0);
			super.setLife(0);
			innerWorld.fakeOff();
		}
	}

	@Override
	public int getCost()
	{
		return 1000;
	}

	public void fakeOff()
	{
		innerWorld.fakeOff();
	}
	
	public int getDecoyLife()
	{
		return decoyLife;
	}
	
	public void setLife(int i)
	{
		decoyLife--;
	}
	
}
