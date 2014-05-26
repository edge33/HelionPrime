package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.GamePane;
import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolMovementPlayerTwo extends Thread {

	private LinkedBlockingQueue<Integer> movement;
	private GamePane gPane;

	public ThreadPoolMovementPlayerTwo(LinkedBlockingQueue<Integer> movement,
			GamePane gamePane) {
		this.movement = movement;
		this.gPane = gamePane;
	}

	@Override
	public void run() {
		while (!ClientManager.isFinishGame()) {

			try {
				Integer move = movement.take();
				gPane.imagePlayer2 = move + 1;

				if (move != -1)
					for (int i = 0; i < 10; i++) {
						movePlayerTwo(move);

						sleep(40);
					}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void movePlayerTwo(Integer move) {
		int movementOffset = gPane.getMovementOffset();
		int playerTwoX = gPane.getPlayerTwoX();
		int playerTwoY = gPane.getPlayerTwoY();

		if (move == 0) {
			gPane.setPlayerTwoX(playerTwoX - movementOffset);

		} else if (move == 1) {

			gPane.setPlayerTwoX(playerTwoX + movementOffset);
		}

		else if (move == 2) {

			gPane.setPlayerTwoY(playerTwoY - movementOffset);
		} else if (move == 3) {
			gPane.setPlayerTwoY(playerTwoY + movementOffset);
		}

	}

}
