package it.mat.unical.Helion_Prime.Logic;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadPoolBullet extends Thread {

	private ConcurrentHashMap<Integer, Bullet> bullets;

	private GameManagerImpl gameManager;

	private String message;

	public ThreadPoolBullet(int id) {
		this.gameManager = GameManagerImpl.getInstance(id);
		this.bullets = gameManager.getBullets();

	}

	@Override
	public void run() {
		while (!gameManager.gameIsOver() && !gameManager.isGameStopped()) {
			while (GameManagerImpl.isPaused()) {
				//System.out.println("Sono in pausa -  ThreadPoolBullets");
				GameManagerImpl.waitForCondition();
			}

			if (bullets.size() > 0) {
				for (Integer key : bullets.keySet()) {

					if ( bullets != null && (bullets.get(key).ControllStopBullet() || bullets.get(key).getStopBullet() ) ) {
						if (!gameManager.isMultiplayerGame()) {
							gameManager.getServer().sendMessage("sr " + key);
							// //System.out.println("dsdd");
						} else
							gameManager.getServerMuliplayer().outBroadcast(
									"sr " + key);

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
				continue;
			}

		}
		//System.out.println("esco dal threadPoolBullet");
	}
}
