package it.mat.unical.Helion_Prime.Logic;

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
	public Integer shoot(World world) {

		return SimpleGun.add(new Bullet((WorldImpl) world, damage));

	}

}
