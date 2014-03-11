package it.mat.unical.Helion_Prime.Logic;

import java.util.concurrent.ConcurrentHashMap;

public interface RangedWeapon extends Weapon {

	public Integer shoot(World world);

	public ConcurrentHashMap<Integer, Bullet> getBullets();
}
