package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.Player;

public class HeavyWeapon extends AbstractGun implements RangedWeapon {

	public HeavyWeapon(World p) {
		this.damage = 150;
		this.world = p;

	}

	@Override
	public Integer shoot(World world, Player player) {
		GameManagerImpl.getInstance(player.getId()).dimMoney(50);
		return super.add(
				new Bullet((WorldImpl) this.world, player, this.damage),
				player.getId());
	}
}
