package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.BountyHunterNative;
import it.mat.unical.Helion_Prime.Logic.Character.SaboteurNative;
import it.mat.unical.Helion_Prime.Logic.Character.SoldierNative;

public class SpikeTrap extends AbstractTrap {

	public SpikeTrap() {
		super();
		typeOfTraps = 0;
		this.setCost(200);

	}

	public SpikeTrap(int positionXonMap, int positionYonMap, int life) {

		super(positionXonMap, positionYonMap, life);

	}

	@Override
	public void effect(AbstractNative currentNative) {
		if (currentNative instanceof SoldierNative) {
			this.setLife(0);
			currentNative.setLife(0);
		}

		if (currentNative instanceof BountyHunterNative) {
			currentNative.setLife(currentNative.getLife() - 30);
			this.setLife(0);
		}

		if (currentNative instanceof SaboteurNative) {
			currentNative.setLife(currentNative.getLife() - 30);
			this.setLife(0);
		}

	}

	@Override
	public boolean equals(Object obj) {
		return this.typeOfTraps == ((AbstractTrap) obj).typeOfTraps;

	}

	public int getCost() {
		return 100;
	}
}
