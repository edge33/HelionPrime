package it.mat.unical.Helion_Prime.Online;

import it.mat.unical.Helion_Prime.GFX.BulletsClient;
import it.mat.unical.Helion_Prime.GFX.GameOverPanel;
import it.mat.unical.Helion_Prime.GFX.GamePane;
import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.GFX.StageClearPanel;
import it.mat.unical.Helion_Prime.GFX.ThreadPoolBulletClient;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;

import java.awt.Point;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientManager {
	private Client client;
	private BlockingQueue<String> createBullets;
	private BlockingQueue<String> movementPlayer;
	// private BlockingQueue<String> movementBulletsForThreadPool;
	private BlockingQueue<String> movementWawe;
	private BlockingQueue<String> placementTrap;
	private BlockingQueue<String> outToServer;

	private GamePane gamePane;

	private int logicX;
	private int logicY;
	private int movementOffset;
	private int money = 0;
	private int life = 0;
	private boolean gameOver = false;
	protected int playerDirection;
	private ThreadPoolBulletClient threadPool;

	public ClientManager(Client client, GamePane gamePane) {

		this.client = client;
		// this.placedTrap = new ConcurrentHashMap<Point, Integer>();
		// this.bullets = new ConcurrentHashMap<Integer, BulletsClient>();
		// this.movementBulletsForThreadPool = new
		// LinkedBlockingQueue<String>();
		this.movementPlayer = new LinkedBlockingQueue<String>();
		this.placementTrap = new LinkedBlockingQueue<String>();
		this.createBullets = new LinkedBlockingQueue<String>();
		this.movementWawe = new LinkedBlockingQueue<String>();
		this.outToServer = new LinkedBlockingQueue<String>();
		this.gamePane = gamePane;
		this.movementOffset = gamePane.getMovementOffset();
		this.threadPool = new ThreadPoolBulletClient(gamePane);
		threadPool.start();
	}

	public void pushToQueueForServer(String sentence) {
		try {
			outToServer.put(sentence);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startMessageToServer() {
		new Thread() {

			public void run() {
				while (!gameOver) {
					String sentence = null;
					try {
						sentence = outToServer.take();

						client.sendMessage(sentence);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
		}.start();

	}

	public void init() {
		this.money = Integer.parseInt(client.recieveMessage()); // ricevo i
		// l'intero
		// corrrispondente
		// ai soldi
		this.life = Integer.parseInt(client.recieveMessage());
		gamePane.informationPanel.setMoney(money);
		gamePane.informationPanel.setLife(life);
		logicX = gamePane.getWorld().getPlayerSpawner().getX();
		logicY = gamePane.getWorld().getPlayerSpawner().getY();

		this.startClient();
		this.startRecieveMessageFromServer();
		this.startMovementOfBullets();
		this.startMovementWave();
		this.startPlacementTrap();
		this.startMessageToServer();

	}

	public int getMoney() {
		return this.money;
	}

	public int getLife() {
		return this.life;
	}

	public void startClient() {

		new Thread() {
			String responseFromServer;

			@Override
			public void run() {

				while (true) {
					responseFromServer = client.recieveMessage();

					if (responseFromServer != null)
						try {
							executeServerResponse(responseFromServer);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					try {
						sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();

	}

	private void executeServerResponse(String responseFromServer)
			throws InterruptedException {

		if (responseFromServer.substring(0, 1).equals("m")) {
			movementPlayer.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("p")) {
			placementTrap.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("s")) {
			createBullets.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("n")) {
			movementWawe.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("c")) {
			StageClearPanel clearPanel = new StageClearPanel();
			MainMenuFrame.getInstance().switchTo(clearPanel);
		} else if (responseFromServer.substring(0, 1).equals("o")) {
			GameOverPanel gameOverPanel = new GameOverPanel();
			MainMenuFrame.getInstance().switchTo(gameOverPanel);
		}

		else {
			createBullets.put(responseFromServer);
		}
	}

	private void placeTrapOnGraphicMap(String message) {
		String[] splittedMessage = message.split(" ");
		String[] splitted = splittedMessage[1].split("/");
		if (splittedMessage[0].equals("p")) {

			gamePane.placedTrap.put(new Point(Integer.parseInt(splitted[1]),
					Integer.parseInt(splitted[2])), Integer
					.parseInt(splitted[0]));

			gamePane.informationPanel.setMoney(Integer.parseInt(splitted[3]));
		} else if (splittedMessage[0].equals("pr")) {

			Point point = new Point(Integer.parseInt(splitted[1]),
					Integer.parseInt(splitted[0]));
			gamePane.placedTrap.remove(point);
		}

	}

	private void moveLogicPlayer(String string) {
		if (string.equals("mUP")) {
			logicX -= 1;
		} else if (string.equals("mDOWN")) {
			logicX += 1;
		} else if (string.equals("mLEFT")) {
			logicY -= 1;
		} else if (string.equals("mRIGHT")) {
			logicY += 1;
		}

	}

	private void startMovementWave() {
		new Thread() {
			public void run() {

				while (true) {
					try {
						String movement = movementWawe.take();
						String[] splitted = movement.split(" ");
						AbstractNative abstractNative = gamePane.natives
								.get(Integer.parseInt(splitted[1]));

						if (movement.substring(1, 2).equals("U")) {

							abstractNative.setX(abstractNative.getX() - 1);
						} else if (movement.substring(1, 2).equals("D")) {
							abstractNative.setX(abstractNative.getX() + 1);
						} else if (movement.substring(1, 2).equals("L")) {
							abstractNative.setY(abstractNative.getY() - 1);
						} else if (movement.substring(1, 2).equals("R")) {
							abstractNative.setY(abstractNative.getY() + 1);
						} else if (splitted[0].equals("nr")) {
							gamePane.natives.remove(Integer
									.parseInt(splitted[1]));
						}
						sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();

	}

	private void startMovementPlayer() {

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {

						String movement = movementPlayer.take();
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

	private void startPlacementTrap() {
		new Thread() {
			public void run() {
				while (true) {
					String placement;
					try {
						placement = placementTrap.take();
						placeTrapOnGraphicMap(placement);
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
		}.start();
	}

	private void startMovementOfBullets() {

		new Thread() {

			public void run() {
				while (true) {
					String movement;
					try {
						movement = createBullets.take();
						String[] movementSplitted = movement.split(" ");

						if (movementSplitted[0].equals("sh")) {
							gamePane.bullets.put(Integer
									.parseInt(movementSplitted[1]),
									new BulletsClient(gamePane.playerDirection,
											logicX, logicY));

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
							;

						} // else if (movementSplitted[0].equals("sUP")) {
							// gamePane.bullets
							// .get(Integer.parseInt(movementSplitted[1]))
							// .setX(gamePane.bullets
							// .get(Integer
							// .parseInt(movementSplitted[1]))
							// .getX() - 1);
							// } else if (movementSplitted[0].equals("sDOWN")) {
							// gamePane.bullets
							// .get(Integer.parseInt(movementSplitted[1]))
							// .setX(gamePane.bullets
							// .get(Integer
							// .parseInt(movementSplitted[1]))
							// .getX() + 1);
							// } else if (movementSplitted[0].equals("sRIGHT"))
							// {
							// gamePane.bullets
							// .get(Integer.parseInt(movementSplitted[1]))
							// .setY(gamePane.bullets
							// .get(Integer
							// .parseInt(movementSplitted[1]))
							// .getY() - 1);
							// } else if (movementSplitted[0].equals("sLEFT")) {
							// gamePane.bullets
							// .get(Integer.parseInt(movementSplitted[1]))
							// .setY(gamePane.bullets
							// .get(Integer
							// .parseInt(movementSplitted[1]))
							// .getY() + 1);

						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
		}.start();

	}

	public void startRecieveMessageFromServer() {

		new Thread() {
			String responseFromServer;

			@Override
			public void run() {

				while (true) {
					responseFromServer = client.recieveMessage();

					if (responseFromServer != null)
						try {
							executeServerResponse(responseFromServer);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					try {
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();

	}

	public int getLogicX() {
		return logicX;
	}

	public void setLogicX(int logicX) {
		this.logicX = logicX;
	}

	public int getLogicY() {
		return logicY;
	}

	public void setLogicY(int logicY) {
		this.logicY = logicY;
	}

	// public ConcurrentHashMap<Point, Integer> getPlacedTrap() {
	// return placedTrap;
	// }
	//
	// public ConcurrentHashMap<Integer, BulletsClient> getBullets() {
	// // TODO Auto-generated method stub
	// return this.bullets;
	// }

}
