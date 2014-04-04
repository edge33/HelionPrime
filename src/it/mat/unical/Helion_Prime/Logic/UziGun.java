package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.Player;

public class UziGun extends AbstractGun implements RangedWeapon {

	private boolean shooted = false;

	public UziGun(World p) {
		this.damage = 15;
		this.world = p;

	}

	@Override
	public Integer shoot(World world, Player player) {

		return UziGun.add(new Bullet((WorldImpl) UziGun.this.world, player,
				damage));

	}

}
