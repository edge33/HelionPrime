package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.AbstractNativeLite;
import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EnemyMoverGraphic extends Thread {

	private GamePane gamePane;
	private HashMap<AbstractNativeLite, ArrayList<Integer>> movementWave;

	public EnemyMoverGraphic(GamePane gamePane) {
		this.gamePane = gamePane;
		this.movementWave = gamePane.getMovementGraphicWave();
	}

	@Override
	public void run() {
		while (!ClientManager.isFinishGame()) {
			for (Iterator<AbstractNativeLite> iterator = movementWave.keySet()
					.iterator(); iterator.hasNext();) {
				AbstractNativeLite curNative = iterator.next();

				if (!movementWave.get(curNative).isEmpty()) {
					int movement = movementWave.get(curNative).get(0);

					moveNativeGraphic(curNative, movement);
				}
			}

			try {
				sleep(50);
			} catch (InterruptedException e) {

				e.printStackTrace();
				continue;
			}
		}
	}

	private void moveNativeGraphic(AbstractNativeLite curNative, int movement) {
		if (movement == 0) {
			curNative.setGraphicX(curNative.getGraphicX()
					- gamePane.getMovementOffset());
		} else if (movement == 1) {
			curNative.setGraphicX(curNative.getGraphicX()
					+ gamePane.getMovementOffset());
		} else if (movement == 2) {
			curNative.setGraphicY(curNative.getGraphicY()
					- gamePane.getMovementOffset());
		} else if (movement == 3) {
			curNative.setGraphicY(curNative.getGraphicY()
					+ gamePane.getMovementOffset());
		}

		curNative.decrMovement();

		if (curNative.getNumberOfMovement() <= 0) {
			curNative.setNumberOfMovement(10);
			movementWave.get(curNative).remove(0);
		}

	}
}
