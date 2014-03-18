package it.mat.unical.Helion_Prime.Logic;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadPoolBullet extends Thread {

	private ConcurrentHashMap<Integer, Bullet> bullets;

	private GameManagerImpl gameManager;

	private String message;

	public ThreadPoolBullet() {
		this.gameManager = GameManagerImpl.getInstance();
		this.bullets = this.gameManager.getPlayer().getCurrentGunSelected()
				.getBullets();

	}

	@Override
	public void run() {
		while (!gameManager.gameIsOver() && !gameManager.isGameStopped()) {
			while (GameManagerImpl.isPaused()) {
				System.out.println("Sono in pausa -  ThreadPoolBullets");
				GameManagerImpl.waitForCondition();
			}

			if (bullets.size() > 0) {
				for (Integer key : bullets.keySet()) {

					if (bullets.get(key).ControllStopBullet()
							|| bullets.get(key).getStopBullet()) {

						gameManager.getServer().sendMessage("sr " + key);

						bullets.remove(key);
					} else {
						bullets.get(key).shooting();

						// GameManagerImpl.getInstance().getServer()
						// .sendMessage(String.valueOf(key));

					}
				}
			}
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("esco dal threadPoolBullet");
	}
}
