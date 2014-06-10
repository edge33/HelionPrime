package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Online.ClientManager;

public class Repainter extends Thread {

	private GamePane panel;

	public Repainter(GamePane panel) {
		this.panel = panel;
	}

	@Override
	public void run() {

		while (!ClientManager.isFinishGame()) {

			while (GameManagerImpl.isPaused()) {
				//System.out.println("sono in pausa - Repainter");
				GameManagerImpl.waitForCondition();
			}
			panel.repaint();
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("esco dal Repainter");
	}
}
