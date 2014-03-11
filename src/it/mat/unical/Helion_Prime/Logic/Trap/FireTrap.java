package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.BountyHunterNative;
import it.mat.unical.Helion_Prime.Logic.Character.SaboteurNative;
import it.mat.unical.Helion_Prime.Logic.Character.SoldierNative;

public class FireTrap extends AbstractTrap {

	public FireTrap(int positionXonMap, int positionYonMap, int life) {

		super(positionXonMap, positionYonMap, life);
	}

	@Override
	public void effect(AbstractNative currentNative) {
		if (currentNative instanceof SoldierNative) {
			if(currentNative.getResistance()==2)
			{
				currentNative.setLife(50);
				System.out.println("danno ridotto!");
			}
			else
			currentNative.setLife(0);
			this.setLife(this.getLife() - 100);
		}

		if (currentNative instanceof BountyHunterNative) {
			currentNative.setLife(currentNative.getLife() - 50);
			this.setLife(0);
		}
		if (currentNative instanceof SaboteurNative) {
			currentNative.setLife(currentNative.getLife() - 30);
			this.setLife(this.getLife() - 100);
			this.setActive(false);

		}

	}

	@Override
	public int getCost() {
		return 200;
	}
}
