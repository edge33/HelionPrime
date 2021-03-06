package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.Wall;
import it.mat.unical.Helion_Prime.Logic.WorldImpl;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractCharacter;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.Character;

import java.util.concurrent.ConcurrentHashMap;

public class EnemyMover extends Thread {

	private int offset;
	private GameManagerImpl manager;
	private int TileSize;
	private int id;

	public EnemyMover(int id) {
		this.id = id;
		TileSize = GamePane.TILE_SIZE;
		this.manager = GameManagerImpl.getInstance(id);
		this.offset = GamePane.TILE_SIZE / 5;

		// hash map concorrente essendo che
		// piu thread hanno accesso alla
		// map dei nemici

		ConcurrentHashMap<Integer, AbstractCharacter> natives = manager
				.getWaveImpl().getNatives();

		for (AbstractCharacter curNative : natives.values()) {
			curNative.setGraphicX(curNative.getX() * this.TileSize);
			curNative.setGraphicY(curNative.getY() * this.TileSize);
		}

	}

	public void MoveNative(AbstractNative currentNative) {
		WorldImpl world = (WorldImpl) manager.getWorld();

		int direction = currentNative.getMove();

		switch (direction) {

		case Character.UP:
			if (!(world.getElementAt(currentNative.getX() - 1,
					currentNative.getY()) instanceof Wall)
					|| currentNative.getGraphicX() > currentNative.getX()
							* this.TileSize) {

				// setta i parametri della posizione
				// del nemico nella grafica

				currentNative.setGraphicX(currentNative.getGraphicX() - offset);

			}

			// setta i parametri della
			// posizione del nemico nella
			// logica
			if (currentNative.getGraphicX() <= ((currentNative.getX() - 1) * this.TileSize)
					+ this.TileSize / 2) {

				if (!GameManagerImpl.getInstance(id).isMultiplayerGame())
					GameManagerImpl.getInstance(id).getServer()
							.sendMessage("nU " + currentNative.getKey());
				else {
					GameManagerImpl.getInstance(id).getServerMuliplayer()
							.outBroadcast("nU " + currentNative.getKey());

				}

				currentNative.setDirection(Character.UP);
				currentNative.move(Character.UP);

			}
			currentNative.attack(currentNative.getAttackPower());
			break;
		case Character.DOWN:

			if (!(world.getElementAt(currentNative.getX() + 1,
					currentNative.getY()) instanceof Wall)
					|| currentNative.getGraphicX() < currentNative.getX()
							* this.TileSize) {
				currentNative.setGraphicX(currentNative.getGraphicX() + offset);

			}

			// qui ci metto diviso due per avere più precisione nei
			// movimenti verso l'alto

			if (currentNative.getGraphicX() > ((currentNative.getX() + 1) * this.TileSize)
					- this.TileSize / 2) {

				if (!GameManagerImpl.getInstance(id).isMultiplayerGame())
					GameManagerImpl.getInstance(id).getServer()
							.sendMessage("nD " + currentNative.getKey());
				else
					GameManagerImpl.getInstance(id).getServerMuliplayer()
							.outBroadcast("nD " + currentNative.getKey());

				currentNative.move(Character.DOWN);
				currentNative.setDirection(Character.DOWN);

			}
			currentNative.attack(currentNative.getAttackPower());
			break;

		case Character.LEFT:

			if (!(world.getElementAt(currentNative.getX(),
					currentNative.getY() - 1) instanceof Wall)
					|| currentNative.getGraphicY() > currentNative.getY()
							* this.TileSize) {
				currentNative.setGraphicY(currentNative.getGraphicY() - offset);

			}
			if (currentNative.getGraphicY() < ((currentNative.getY() - 1) * this.TileSize)
					+ this.TileSize / 2) {

				if (!GameManagerImpl.getInstance(id).isMultiplayerGame())
					GameManagerImpl.getInstance(id).getServer()
							.sendMessage("nL " + currentNative.getKey());
				else
					GameManagerImpl.getInstance(id).getServerMuliplayer()
							.outBroadcast("nL " + currentNative.getKey());

				currentNative.move(Character.LEFT);
				currentNative.setDirection(Character.LEFT);

			}
			currentNative.attack(currentNative.getAttackPower());
			break;
		case Character.RIGHT:

			if (!(world.getElementAt(currentNative.getX(),
					currentNative.getY() + 1) instanceof Wall)
					|| currentNative.getGraphicY() < currentNative.getY()
							* this.TileSize) {
				currentNative.setGraphicY(currentNative.getGraphicY() + offset);

			}
			if (currentNative.getGraphicY() > ((currentNative.getY() + 1) * this.TileSize)
					- this.TileSize / 2) {

				if (!GameManagerImpl.getInstance(id).isMultiplayerGame())
					GameManagerImpl.getInstance(id).getServer()
							.sendMessage("nR " + currentNative.getKey());
				else
					GameManagerImpl.getInstance(id).getServerMuliplayer()
							.outBroadcast("nR " + currentNative.getKey());

				currentNative.move(Character.RIGHT);
				currentNative.setDirection(Character.RIGHT);

			}
			currentNative.attack(currentNative.getAttackPower());
			break;
		default:
			currentNative.attack(currentNative.getAttackPower());
			break;

		}
	}

	void firstMove() {

		new Thread() {
			ConcurrentHashMap<Integer, AbstractNative> nativesList = manager
					.getWaveImpl().getNatives();

			@Override
			public void run() {

				for (AbstractNative currentNative : nativesList.values()) {
					if (!currentNative.getFisrtMove())
						for (int i = 0; i < 5; i++) {

							while (GameManagerImpl.isPaused()) {
								System.out
										.println("Sono in pausa -  ThreadPoolBullets");
								GameManagerImpl.waitForCondition();
							}

							MoveNative(currentNative);

							try {
								sleep(150);
							} catch (InterruptedException e) {

								e.printStackTrace();
							}

						}
					currentNative.setFisrtMove(true);
					try {
						sleep(120);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			};
		}.start();

	}

	@Override
	public void run() {

		ConcurrentHashMap<Integer, AbstractNative> nativesList = manager
				.getWaveImpl().getNatives();

		try {
			sleep(1000);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}

//		System.err.println("MUOVO I NEMICI");
		firstMove();
		while (!manager.gameIsOver() && !manager.isGameStopped()) {

			while (GameManagerImpl.isPaused()) {
				//System.out.println("Sono in pausa - EnemyMover");
				GameManagerImpl.waitForCondition();
			}

			for (AbstractNative currentNative : nativesList.values()) {
				if (currentNative.getFisrtMove())
					MoveNative(currentNative);

			}

			try {
				sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		//System.out.println("esco dall' enemyMover");

	}
}
