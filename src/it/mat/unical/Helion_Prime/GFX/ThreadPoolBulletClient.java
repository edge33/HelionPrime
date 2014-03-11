package it.mat.unical.Helion_Prime.GFX;

import java.util.ArrayList;

public class ThreadPoolBulletClient extends Thread {

	ArrayList<BulletsClient> bullets;

	public ThreadPoolBulletClient(ArrayList<BulletsClient> bullets) {
		this.bullets = bullets;
	}

	@Override
	public void run() {
		while (true) {
			for (int i = 0; i < bullets.size(); i++)
				bullets.get(i).shooting();

			try {
				sleep(21);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
