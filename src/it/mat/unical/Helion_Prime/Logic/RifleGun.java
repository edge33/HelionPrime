package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.Player;

public class RifleGun extends AbstractGun implements RangedWeapon {

	public RifleGun(World p) {
		this.damage = 80;
		this.world = p;

	}

	@Override
	public Integer shoot(World world, Player player) {
		return super.add(
				new Bullet((WorldImpl) this.world, player, this.damage),
				player.getId());
	}
}
