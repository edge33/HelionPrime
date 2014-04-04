package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.Player;

import java.util.concurrent.ConcurrentHashMap;

public interface RangedWeapon extends Weapon {

	public Integer shoot(World world, Player player);

	public ConcurrentHashMap<Integer, Bullet> getBullets();
}
