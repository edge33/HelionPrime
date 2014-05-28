package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.Player;

public class UziGun extends AbstractGun implements RangedWeapon {

	private boolean shooted = false;

	public UziGun(World p) {
		this.damage = 25;
		this.world = p;

	}

	@Override
	public Integer shoot(World world, Player player) {
		// player.setScore(newScore);
		GameManagerImpl.getInstance(player.getId()).dimMoney(20);
		return super.add(new Bullet((WorldImpl) UziGun.this.world, player,
				damage), player.getId());

	}

}
