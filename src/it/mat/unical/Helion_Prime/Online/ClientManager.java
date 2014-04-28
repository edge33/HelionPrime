package it.mat.unical.Helion_Prime.Online;

import it.mat.unical.Helion_Prime.GFX.BulletsClient;
import it.mat.unical.Helion_Prime.GFX.GameOverPanel;
import it.mat.unical.Helion_Prime.GFX.GamePane;
import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.GFX.StageClearPanel;
import it.mat.unical.Helion_Prime.GFX.ThreadPoolBulletClient;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
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
	public static boolean isPlayerOne;
	protected GamePane gamePane;
	private UserProfile profile;
	private int logicXPlayerOne;
	private int logicYPlayerOne;
	private int movementOffset;
	private int money = 0;
	private int life = 0;
	protected boolean gameOver = false;
	protected static boolean finishGame = false;
	private int playerDirection;
	private ThreadPoolBulletClient threadPool;
	private static ClientManager instance;

	protected ClientManager(Client client, GamePane gamePane) {

		this.client = client;
		// this.placedTrap = new ConcurrentHashMap<Point, Integer>();
		// this.bullets = new ConcurrentHashMap<Integer, BulletsClient>();
		// this.movementBulletsForThreadPool = new
		// LinkedBlockingQueue<String>();
		finishGame = false;
		gameOver = false;
		this.placementTrap = new LinkedBlockingQueue<String>();
		this.movementPlayer = new LinkedBlockingQueue<String>();
		this.createBullets = new LinkedBlockingQueue<String>();
		this.movementWawe = new LinkedBlockingQueue<String>();
		this.outToServer = new LinkedBlockingQueue<String>();
		this.gamePane = gamePane;
		// this.movementOffset = gamePane.getMovementOffset();
		this.threadPool = new ThreadPoolBulletClient(gamePane);

	}

	protected ClientManager(Client client, GamePane gamePane,
			UserProfile profile) {

		this.client = client;
		// this.placedTrap = new ConcurrentHashMap<Point, Integer>();
		// this.bullets = new ConcurrentHashMap<Integer, BulletsClient>();
		// this.movementBulletsForThreadPool = new
		// LinkedBlockingQueue<String>();
		finishGame = false;
		this.placementTrap = new LinkedBlockingQueue<String>();
		this.movementPlayer = new LinkedBlockingQueue<String>();
		this.createBullets = new LinkedBlockingQueue<String>();
		this.movementWawe = new LinkedBlockingQueue<String>();
		this.outToServer = new LinkedBlockingQueue<String>();
		this.gamePane = gamePane;
		this.movementOffset = gamePane.getMovementOffset();
		this.threadPool = new ThreadPoolBulletClient(gamePane);
		this.profile = profile;

	}

	protected ClientManager() {
		// TODO Auto-generated constructor stub
	}

	public static ClientManager getInstance() {
		if (instance == null)
			instance = new ClientManager();

		return instance;
	}

	public void createClientManager(Client client, GamePane gamePane,
			UserProfile profile) {

		this.client = client;
		// this.placedTrap = new ConcurrentHashMap<Point, Integer>();
		// this.bullets = new ConcurrentHashMap<Integer, BulletsClient>();
		// this.movementBulletsForThreadPool = new
		// LinkedBlockingQueue<String>();
		finishGame = false;
		this.placementTrap = new LinkedBlockingQueue<String>();
		this.movementPlayer = new LinkedBlockingQueue<String>();
		this.createBullets = new LinkedBlockingQueue<String>();
		this.movementWawe = new LinkedBlockingQueue<String>();
		this.outToServer = new LinkedBlockingQueue<String>();
		this.gamePane = gamePane;
		this.movementOffset = gamePane.getMovementOffset();
		this.threadPool = new ThreadPoolBulletClient(gamePane);
		this.profile = profile;

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
				while (!gameOver && !finishGame) {
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

		System.err.println("ENTRO IN INIT");
		finishGame = false;
		gameOver = false;
		this.money = Integer.parseInt(client.recieveMessage()); // ricevo i
		// l'intero
		// corrrispondente
		// ai soldi
		this.life = Integer.parseInt(client.recieveMessage());
		gamePane.informationPanel.setMoney(money);
		gamePane.informationPanel.setLife(life);
		logicXPlayerOne = gamePane.getWorld().getPlayerSpawner().getX();
		logicYPlayerOne = gamePane.getWorld().getPlayerSpawner().getY();
		this.playerDirection = 2;

		System.err.println("Arrivate " + money + " " + life);
		this.threadPool.start();
		this.startClient();

		this.startMovementOfBullets();
		this.startMovementWave();
		this.startPlacementTrap();
		this.startMessageToServer();

		System.err.println("ESCO DA INIT");
	}

	public void retrySelection() {

		this.money = Integer.parseInt(client.recieveMessage()); // ricevo i
		// l'intero
		// corrrispondente
		// ai soldi
		this.life = Integer.parseInt(client.recieveMessage());

		gamePane.informationPanel.setMoney(money);
		gamePane.informationPanel.setLife(life);
	}

	public int getMoney() {
		return this.money;
	}

	public int getLife() {
		return this.life;
	}

	public void closeConnection() {
		client.closeConnection();
	}

	public void startClient() {

		new Thread() {
			String responseFromServer;

			@Override
			public void run() {
				this.setName("RECIEVE MESSAGE_CLIENT");
				while (!gameOver && !finishGame) {
					responseFromServer = client.recieveMessage();

					if (responseFromServer != null)
						try {
							if (responseFromServer.equals("clear")) {
								finishGame = true;

							}
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

		if (responseFromServer.substring(0, 1).equals("m")
				|| responseFromServer.substring(0, 1).equals("d")) {
			movementPlayer.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("p")) {
			placementTrap.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("s")) {
			createBullets.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("n")) {
			movementWawe.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("c")) {
			this.finishGame = true;
			sendAllFinish();
			UserProfile.incrLevel();
			StageClearPanel clearPanel = new StageClearPanel(this,
					gamePane.getCurrentFileLevel());
			this.finishGame = true;
			MainMenuFrame.getInstance().switchTo(clearPanel);
		} else if (responseFromServer.substring(0, 1).equals("o")) {
			GameOverPanel gameOverPanel = new GameOverPanel();
			MainMenuFrame.getInstance().switchTo(gameOverPanel);
		} else if (responseFromServer.substring(0, 1).equals("l")) {
			String[] splitted = responseFromServer.split(" ");
			if (splitted[0].equals("life"))
				gamePane.informationPanel
						.setLife(Integer.parseInt(splitted[1]));
			else
				gamePane.informationPanel.setRoomLife(Integer
						.parseInt(splitted[1]));
		}

		else {
			createBullets.put(responseFromServer);
		}
	}

	public void sendAllFinish() throws InterruptedException {
		outToServer.put("finish");
		movementWawe.put("finish");
		createBullets.put("finish");
		placementTrap.put("finish");
		movementWawe.put("finish");
		movementPlayer.put("finish");
	}

	private void placeTrapOnGraphicMap(String message) {

		if (!message.equals("finish")) {
			String[] splittedMessage = message.split(" ");
			String[] splitted = splittedMessage[1].split("/");
			if (splittedMessage[0].equals("p")) {

				gamePane.placedTrap.put(
						new Point(Integer.parseInt(splitted[1]), Integer
								.parseInt(splitted[2])), Integer
								.parseInt(splitted[0]));

				gamePane.informationPanel.setMoney(Integer
						.parseInt(splitted[3]));

			} else if (splittedMessage[0].equals("pr")) {

				Point point = new Point(Integer.parseInt(splitted[1]),
						Integer.parseInt(splitted[0]));
				gamePane.placedTrap.remove(point);
			}
		}

	}

	public void sendCloseMessage() {
		client.sendMessage("close");
	}

	public Client getClient() {
		return this.client;
	}

	public GamePane getGamePane() {
		return this.gamePane;
	}

	private void startMovementWave() {
		new Thread() {
			public void run() {
				this.setName("MOVEMENT_WAWE_CLIENT");
				while (!gameOver && !finishGame) {
					try {
						String movement = movementWawe.take();
						String[] splitted = movement.split(" ");

						if (!movement.equals("finish")) {
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
						}
						sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NullPointerException e2) {
						e2.printStackTrace();
						continue;
					}
				}
			};
		}.start();

	}

	private void startPlacementTrap() {
		new Thread() {
			public void run() {
				this.setName("PLACEMENT_CLIENT");
				while (!gameOver && !finishGame) {
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

	public void startMovementOfBullets() {

		new Thread() {

			public void run() {

				this.setName("BULLETS_CLIENT");
				while (!gameOver && !finishGame) {
					String movement;
					try {
						movement = createBullets.take();
						String[] movementSplitted = movement.split(" ");

						if (movementSplitted[0].equals("sh")) {
							gamePane.bullets.put(
									Integer.parseInt(movementSplitted[1]),
									new BulletsClient(Integer
											.parseInt(movementSplitted[2]),
											logicXPlayerOne, logicYPlayerOne));

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
					} catch (NumberFormatException e2) {
						continue;
					}

				}
			};
		}.start();

	}

	public int getLogicX() {
		return logicXPlayerOne;
	}

	public void setLogicX(int logicX) {
		this.logicXPlayerOne = logicX;
	}

	public int getLogicY() {
		return logicYPlayerOne;
	}

	public void setLogicY(int logicY) {
		this.logicYPlayerOne = logicY;
	}

	public BlockingQueue<String> getMovementPlayer() {
		return movementPlayer;
	}

	public BlockingQueue<String> getCreateBullets() {
		return createBullets;
	}

	public int getPlayerDirection() {
		return playerDirection;
	}

	public void setPlayerDirection(int playerDirection) {
		this.playerDirection = playerDirection;
	}

	public void sendMessage(String c) {
		client.sendMessage(c);

	}

	public static boolean isFinishGame() {
		return finishGame;
	}

	public static void setFinishGame(boolean b) {
		finishGame = true;
	}

	public UserProfile getUserProfile() {

		return this.profile;
	}

	public boolean isMultiplayerGame() {
		return false;
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
