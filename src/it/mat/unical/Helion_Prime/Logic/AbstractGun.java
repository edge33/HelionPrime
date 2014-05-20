package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.Player;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AbstractGun implements RangedWeapon {

	// protected static ConcurrentHashMap<Integer, Bullet> bullets = new
	// ConcurrentHashMap<Integer, Bullet>();
	public static volatile Integer codeBullet = 0;
	protected int damage;
	protected World world;
	private final static Lock lock = new ReentrantLock();

	@Override
	public void hit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer shoot(World world, Player player) {
		return 0;
	}

	public Integer add(Bullet t, int id) {
		codeBullet++;
		GameManagerImpl.getInstance(id).getBullets().put(codeBullet, t);
		return codeBullet;

	}

}
