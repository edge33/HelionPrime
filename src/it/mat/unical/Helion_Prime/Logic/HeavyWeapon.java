package it.mat.unical.Helion_Prime.Logic;

public class HeavyWeapon extends AbstractGun implements RangedWeapon {

	public HeavyWeapon(World p) {
		this.damage = 150;
		this.world = p;

	}

	@Override
	public Integer shoot(World world) {
		return HeavyWeapon.add(new Bullet((WorldImpl) this.world, this.damage));
	}
}
