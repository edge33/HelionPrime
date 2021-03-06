package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.AbstractGun;
import it.mat.unical.Helion_Prime.Logic.Bullet;
import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.WorldImpl;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.BountyHunterNative;
import it.mat.unical.Helion_Prime.Logic.Character.SaboteurNative;
import it.mat.unical.Helion_Prime.Logic.Character.SoldierNative;

public class TrapPower extends AbstractTrap {

	public WorldImpl world;
	private int id;
	private boolean isRunning;

	public TrapPower(int positionXonMap, int positionYonMap, int life, int id) {

		super(positionXonMap, positionYonMap, life);
		this.id = id;
		world = (WorldImpl) GameManagerImpl.getInstance(id).getWorld();

		isRunning = true;
	}

	@Override
	public void effect(AbstractNative currentNative) {
		if (currentNative instanceof SoldierNative) {
			currentNative.setLife(0);
			this.setLife(this.getLife() - 100);
		}

		if (currentNative instanceof BountyHunterNative) {
			currentNative.setLife(currentNative.getLife() - 50);
			this.setLife(0);
		}
		if (currentNative instanceof SaboteurNative) {
			currentNative.setLife(0);
			this.setLife(this.getLife() - 100);
			this.setActive(false);

		}

	}

	@Override
	public int getCost() {
		return 1000;
	}

	public void start() {
		new Thread() {

			public void run() {
				this.setName("TRAP_POWER");
				while (TrapPower.this.getLife() > 0 && isRunning) {
					while (GameManagerImpl.isPaused()) {
						//System.out.println("Sono in Pausa - TrapPower");
						GameManagerImpl.waitForCondition();
					}

					try {
						sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					for (int i = 0; i < 4 && isRunning; i++) {

						AbstractGun.codeBullet++;

						GameManagerImpl
								.getInstance(id)
								.getBullets()
								.put(AbstractGun.codeBullet,
										new Bullet(TrapPower.this.getX(),
												TrapPower.this.getY(), i, 15,
												TrapPower.this.world));

						String messageForBullet = "srm " +

						String.valueOf(AbstractGun.codeBullet) + " " + i + " "
								+ String.valueOf(TrapPower.this.getX()) + " "
								+ String.valueOf(TrapPower.this.getY());

						//System.out.println(messageForBullet);

						if (!GameManagerImpl.getInstance(id)
								.isMultiplayerGame())
							GameManagerImpl.getInstance(id).getServer()
									.sendMessage(messageForBullet);
						else
							GameManagerImpl.getInstance(id)
									.getServerMuliplayer()
									.outBroadcast(messageForBullet);

					}
				}
			}
		}.start();
	}

	public void kill() {
		isRunning = false;

	}
}
