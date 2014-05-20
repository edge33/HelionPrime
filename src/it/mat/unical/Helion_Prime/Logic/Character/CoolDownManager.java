package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

public class CoolDownManager extends Thread {

	private AbstractNative theNative;
	private int timeToSleep;
	private GameManagerImpl gameManager;
	private int id;

	CoolDownManager(AbstractNative theNative, int timeToSleep, int id) {
		this.id = id;
		this.theNative = theNative;
		this.timeToSleep = timeToSleep;
		this.gameManager = GameManagerImpl.getInstance(id);
	}

	public void run() {
		System.out.println("COOLDOWN");
		this.setName("COOLDOWN MANAGER");

		while (theNative.isAlive()
				&& !GameManagerImpl.getInstance(id).isGameStopped()
				&& !GameManagerImpl.getInstance(id).gameIsOver()) {

			while (GameManagerImpl.isPaused()) {
				System.out.println("Sono in pausa  - CoolDownManager");
				GameManagerImpl.waitForCondition();
			}

			if (!theNative.isCanAttack()) {
				theNative.setAttack(true);

			}

			try {
				// System.out.println("dormo " + this.getId());
				sleep(timeToSleep);
				// System.out.println("esco" + this.getId() );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// System.out.println("muoio " + this.getId());
	};

}
