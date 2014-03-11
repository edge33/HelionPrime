package it.mat.unical.Helion_Prime.Logic;

public class UziGun extends AbstractGun implements RangedWeapon {

	private boolean shooted = false;

	public UziGun(World p) {
		this.damage = 15;
		this.world = p;

	}

	@Override
	public Integer shoot(World world) {

		return UziGun.add(new Bullet((WorldImpl) UziGun.this.world, damage));

	}

}
