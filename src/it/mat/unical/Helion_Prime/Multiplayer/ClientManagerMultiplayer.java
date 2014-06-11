package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.BulletsClient;
import it.mat.unical.Helion_Prime.GFX.GamePane;
import it.mat.unical.Helion_Prime.GFX.SoundTraker;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.util.concurrent.LinkedBlockingQueue;

public class ClientManagerMultiplayer extends ClientManager {

	private int logicXPlayerTwo;
	private int logicYPlayerTwo;
	private int directionPlayerTwo;
	private LinkedBlockingQueue<Integer> movementOffsetPlayer2;
	private static ClientManagerMultiplayer instance;

	private ClientManagerMultiplayer(Client client, GamePane gamePane) {
		super(client, gamePane);
		movementOffsetPlayer2 = new LinkedBlockingQueue<Integer>();
		logicXPlayerTwo = gamePane.getWorld().getPlayerSpawner().getX();
		logicYPlayerTwo = gamePane.getWorld().getPlayerSpawner().getY();
		this.startMovementPlayer();

	}

	private ClientManagerMultiplayer() {
		super();
	}

	public static ClientManagerMultiplayer getInstance() {

		if (instance == null) {
			instance = new ClientManagerMultiplayer();
		}

		return ClientManagerMultiplayer.instance;
	}

	public void createClientManagerMultiplayer(Client client, GamePane gamePane) {

		this.createClientManager(client, gamePane, null);
		movementOffsetPlayer2 = new LinkedBlockingQueue<Integer>();
		logicXPlayerTwo = gamePane.getWorld().getPlayerSpawner().getX();
		logicYPlayerTwo = gamePane.getWorld().getPlayerSpawner().getY();
		this.startMovementPlayer();

	}

	public void moveLogicPlayer(String string) {
		if (string.equals("mUP")) {
			logicXPlayerTwo -= 1;
			try {
				movementOffsetPlayer2.put(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} else if (string.equals("mDOWN")) {
			logicXPlayerTwo += 1;
			try {
				movementOffsetPlayer2.put(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} else if (string.equals("mRIGHT")) {
			logicYPlayerTwo -= 1;
			try {
				movementOffsetPlayer2.put(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} else if (string.equals("mLEFT")) {
			logicYPlayerTwo += 1;
			try {
				movementOffsetPlayer2.put(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
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
		} else if (string.equals("dSTAND")) {
			try {
				movementOffsetPlayer2.put(-1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

	public void startMovementPlayer() {

		new Thread() {
			@Override
			public void run() {
				this.setName("CLIENT_MANAGER Multiplayer- MovementPlayer ");
				while (!finishGame && !gameOver) {
					try {

						String movement = getMovementPlayer().take();
						moveLogicPlayer(movement);
						sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
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
				this.setName("CLIENT MANAGER Multiplayer- movementBullet ");
				while (!finishGame && !gameOver) {
					String movement;
					try {
						movement = getCreateBullets().take();
						String[] movementSplitted = movement.split(" ");

						if (movementSplitted[0].equals("sh")
								&& movementSplitted[2].equals("1")) {
							gamePane.bullets
									.put(Integer.parseInt(movementSplitted[1]),
											new BulletsClient(
													Integer.parseInt(movementSplitted[3]),
													getLogicX(),
													getLogicY(),
													Integer.parseInt(movementSplitted[4])));

							gamePane.getEastPanel().incrBulletState(
									Integer.parseInt(movementSplitted[4]));

							SoundTraker.getInstance().startClip(3);

						} else if (movementSplitted[0].equals("sh")
								&& movementSplitted[2].equals("2")) {
							gamePane.bullets
									.put(Integer.parseInt(movementSplitted[1]),
											new BulletsClient(
													Integer.parseInt(movementSplitted[3]),
													getLogicXPlayerTwo(),
													getLogicYPlayerTwo(),
													Integer.parseInt(movementSplitted[4])));

							gamePane.getEastPanel().incrBulletState(
									Integer.parseInt(movementSplitted[4]));
							SoundTraker.getInstance().startClip(3);
						} else if (movementSplitted[0].equals("srm")) {
							gamePane.bullets
									.put(Integer.parseInt(movementSplitted[1]),

											new BulletsClient(
													Integer.parseInt(movementSplitted[2]),
													Integer.parseInt(movementSplitted[3]),
													Integer.parseInt(movementSplitted[4]),
													5));
							SoundTraker.getInstance().startClip(6);
						}

						else if (movementSplitted[0].equals("sr")) {

							gamePane.bullets.get(
									Integer.parseInt(movementSplitted[1]))
									.setStopBullet();

						}

						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					} catch (NullPointerException e2) {
						continue;
					}

				}
			};
		}.start();

	}

	@Override
	public boolean isMultiplayerGame() {

		return !super.isMultiplayerGame();
	}

	public int getPlayerTwoDirection() {
		return directionPlayerTwo;
	}

	@Override
	public void sendAllFinish() {
		try {
			super.sendAllFinish();
			movementOffsetPlayer2.put(-4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	public void setPlayerTwoDirection(int directionPlayerTwo) {
		this.directionPlayerTwo = directionPlayerTwo;
	}

	public LinkedBlockingQueue<Integer> getMovementOffsetPlayer2() {
		return movementOffsetPlayer2;
	}
}
