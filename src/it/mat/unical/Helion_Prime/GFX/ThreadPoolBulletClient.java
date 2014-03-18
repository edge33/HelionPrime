package it.mat.unical.Helion_Prime.GFX;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadPoolBulletClient extends Thread {

	private ConcurrentHashMap<Integer, BulletsClient> bullets;

	private GamePane gamePane;

	public ThreadPoolBulletClient(GamePane gamePane) {
		this.gamePane = gamePane;
		this.bullets = gamePane.bullets;

	}

	@Override
	public void run() {
		while (!gamePane.isGameOver() && !gamePane.isStageClear()) {
			if (gamePane.bullets.size() > 0)
				for (Integer key : bullets.keySet()) {

					if (bullets.get(key).stopBullet()) {

						bullets.remove(key);
					} else {
						bullets.get(key).shooting();

						// GameManagerImpl.getInstance().getServer()
						// .sendMessage(String.valueOf(key));

					}
				}

			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
