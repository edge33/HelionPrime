package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.Player;

public class SimpleGun extends AbstractGun {
	public SimpleGun(World p) {
		this.world = p;
		damage = 50;
	}

	// Maida: metto una distanza ipotetica

	@Override
	public void hit() {

		// TODO Maida: Mi sa che questo e' inutile xD

	}

	@Override
	public Integer shoot(World world, Player player) {

		return super.add(new Bullet((WorldImpl) world, player, damage),
				player.getId());

	}

}
