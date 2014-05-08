package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Online.ClientManager;

public class CoolDownManager extends Thread {

	private AbstractNative theNative;
	private int timeToSleep;
	private GameManagerImpl gameManager;

	CoolDownManager(AbstractNative theNative, int timeToSleep) {
		this.theNative = theNative;
		this.timeToSleep = timeToSleep;
		this.gameManager = GameManagerImpl.getInstance();
	}

	public void run() {

		this.setName("COOLDOWN MANAGER");

		while (theNative.isAlive() && !ClientManager.isFinishGame()) {

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
