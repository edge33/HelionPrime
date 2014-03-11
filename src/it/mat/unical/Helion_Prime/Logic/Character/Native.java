package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Ability.AbilityInterface;
import it.mat.unical.Helion_Prime.Logic.Ability.Resistance;

public class Native extends AbstractCharacter implements Resistance {

	public Native(int x, int y, World world) {
		super(x, y, world);
	}

	public void Attack() {
		// TODO: atacking method working on RN - Maida
		Player player = GameManagerImpl.getInstance().getPlayer();

		if (getX() == player.getX() && getY() == player.getY()) {
			System.out.println("te compaaa");
		}

		// TODO: cambiare questa munnizza!!!!!! - Maida
		// player.setLife(10);
	}

	private AbilityInterface ability = null;

	public AbilityInterface getAbility() {
		return ability;
	}

	@Override
	public int getResistance() {
		// TODO Auto-generated method stub
		return 0;
	}

}
