package it.mat.unical.Helion_Prime.Logic;

public class RifleGun extends AbstractGun implements RangedWeapon {

	public RifleGun(World p) {
		this.damage = 80;
		this.world = p;

	}

	@Override
	public Integer shoot(World world) {
		return RifleGun.add(new Bullet((WorldImpl) this.world, this.damage));
	}
}
