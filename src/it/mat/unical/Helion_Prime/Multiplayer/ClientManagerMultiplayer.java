package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.BulletsClient;
import it.mat.unical.Helion_Prime.GFX.GamePane;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;

public class ClientManagerMultiplayer extends ClientManager {

	private int logicXPlayerTwo;
	private int logicYPlayerTwo;
	private int directionPlayerTwo;

	public ClientManagerMultiplayer(Client client, GamePane gamePane) {
		super(client, gamePane);
		logicXPlayerTwo = gamePane.getWorld().getPlayerSpawner().getX();
		logicYPlayerTwo = gamePane.getWorld().getPlayerSpawner().getY();
		this.startMovementPlayer();

	}

	public void moveLogicPlayer(String string) {
		if (string.equals("mUP")) {
			logicXPlayerTwo -= 1;
		} else if (string.equals("mDOWN")) {
			logicXPlayerTwo += 1;
		} else if (string.equals("mRIGHT")) {
			logicYPlayerTwo -= 1;
		} else if (string.equals("mLEFT")) {
			logicYPlayerTwo += 1;
		} else if (string.equals("dUP")) { // per settare le direzione del

			setPlayerTwoDirection(0);
		} else if (string.equals("dDOWN")) {
			// playerOne.setDirection(1);
			setPlayerTwoDirection(1);
		} else if (string.equals("dRIGHT")) {
			// playerOne.setDirection(2);
			setPlayerTwoDirection(2);
		} else if (string.equals("dLEFT")) {
			// playerOne.setDirection(3);
			setPlayerTwoDirection(3);
		}
	}

	public void startMovementPlayer() {

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {

						String movement = getMovementPlayer().take();
						moveLogicPlayer(movement);
						sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}.start();
	}

	public int getLogicXPlayerTwo() {
		return logicXPlayerTwo;
	}

	public void setLogicXPlayerTwo(int logicXPlayerTwo) {
		this.logicXPlayerTwo = logicXPlayerTwo;
	}

	public int getLogicYPlayerTwo() {
		return logicYPlayerTwo;
	}

	public void setLogicYPlayerTwo(int logicYPlayerTwo) {
		this.logicYPlayerTwo = logicYPlayerTwo;
	}

	@Override
	public void startMovementOfBullets() {

		new Thread() {

			public void run() {
				while (true) {
					String movement;
					try {
						movement = getCreateBullets().take();
						String[] movementSplitted = movement.split(" ");

						if (movementSplitted[0].equals("sh")
								&& movementSplitted[2].equals("1")) {
							gamePane.bullets.put(Integer
									.parseInt(movementSplitted[1]),
									new BulletsClient(getPlayerDirection(),
											getLogicX(), getLogicY()));

						} else if (movementSplitted[0].equals("sh")
								&& movementSplitted[2].equals("2")) {
							gamePane.bullets.put(Integer
									.parseInt(movementSplitted[1]),
									new BulletsClient(getPlayerTwoDirection(),
											getLogicXPlayerTwo(),
											getLogicYPlayerTwo()));

						} else if (movementSplitted[0].equals("srm")) {
							gamePane.bullets
									.put(Integer.parseInt(movementSplitted[1]),

											new BulletsClient(
													Integer.parseInt(movementSplitted[2]),
													Integer.parseInt(movementSplitted[3]),
													Integer.parseInt(movementSplitted[4])));
						}

						else if (movementSplitted[0].equals("sr")) {

							gamePane.bullets.get(
									Integer.parseInt(movementSplitted[1]))
									.setStopBullet();

						}

						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NullPointerException e2) {
						continue;
					}

				}
			};
		}.start();

	}

	public int getPlayerTwoDirection() {
		return directionPlayerTwo;
	}

	public void setPlayerTwoDirection(int directionPlayerTwo) {
		this.directionPlayerTwo = directionPlayerTwo;
	}
}
