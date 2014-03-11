package it.mat.unical.Helion_Prime.Logic;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AbstractGun implements RangedWeapon {

	protected static ConcurrentHashMap<Integer, Bullet> bullets = new ConcurrentHashMap<Integer, Bullet>();
	private static Integer codeBullet = 0;
	protected int damage;
	protected World world;
	private final static Lock lock = new ReentrantLock();

	@Override
	public void hit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer shoot(World world) {
		return 0;
	}

	public static Integer add(Bullet t) {
		codeBullet++;
		bullets.put(codeBullet, t);
		return codeBullet;

	}

	@Override
	public ConcurrentHashMap<Integer, Bullet> getBullets() {

		return this.bullets;
	}

}
