package it.mat.unical.Helion_Prime.Online;

import it.mat.unical.Helion_Prime.GFX.BulletsClient;
import it.mat.unical.Helion_Prime.GFX.GameOverPanel;
import it.mat.unical.Helion_Prime.GFX.GamePane;
import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.GFX.MainMenuPanel;
import it.mat.unical.Helion_Prime.GFX.StageClearPanel;
import it.mat.unical.Helion_Prime.GFX.ThreadPoolBulletClient;
import it.mat.unical.Helion_Prime.Logic.AbstractNativeLite;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.SavesManager.PlayerSaveState;

import java.awt.Point;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

public class ClientManager {
	private Client client;
	private BlockingQueue<String> createBullets;
	private BlockingQueue<String> movementPlayer;
	// private BlockingQueue<String> movementBulletsForThreadPool;
	private BlockingQueue<String> movementWawe;
	private BlockingQueue<String> placementTrap;
	private BlockingQueue<String> outToServer;

	public static boolean isPlayerOne = true;
	protected GamePane gamePane;
	private UserProfile profile;
	private int logicXPlayerOne;
	private int logicYPlayerOne;
	private int movementOffset;
	private int currentGunSelected = 0;
	private int[] numberOfBullet;
	private int money = 0;
	private int life = 0;
	protected boolean gameOver = false;
	protected static boolean finishGame = false;
	private int playerDirection;
	private ThreadPoolBulletClient threadPool;
	public LinkedBlockingQueue<String> informations;
	public boolean isFinishRecieve;
	private static ClientManager instance;
	private static Lock lock;
	private static Condition condition;

	protected ClientManager(Client client, GamePane gamePane) {

		this.client = client;
		// this.placedTrap = new ConcurrentHashMap<Point, Integer>();
		// this.bullets = new ConcurrentHashMap<Integer, BulletsClient>();
		// this.movementBulletsForThreadPool = new
		// LinkedBlockingQueue<String>();
		finishGame = false;
		gameOver = false;
		lock = new ReentrantLock();
		this.condition = lock.newCondition();

		informations = new LinkedBlockingQueue<String>();
		//
		this.placementTrap = new LinkedBlockingQueue<String>();
		this.movementPlayer = new LinkedBlockingQueue<String>();
		this.createBullets = new LinkedBlockingQueue<String>();
		this.movementWawe = new LinkedBlockingQueue<String>();
		this.outToServer = new LinkedBlockingQueue<String>();
		this.gamePane = gamePane;
		this.isFinishRecieve = false;
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
		lock = new ReentrantLock();
		this.condition = lock.newCondition();
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
		lock = new ReentrantLock();
		this.condition = lock.newCondition();
		finishGame = false;
		informations = new LinkedBlockingQueue<String>();
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

						if (!sentence.equals("finish"))
							sendMessage(sentence);
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
		this.money = Integer.parseInt(recieveMessage()); // ricevo i
		
		
		if ( PlayerSaveState.getInstance().isSet() ) {
			this.money = PlayerSaveState.getInstance().getScore();
		}
		
		// l'intero
		// corrrispondente
		// ai soldi
		this.life = Integer.parseInt(recieveMessage());
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

		this.money = Integer.parseInt(recieveMessage()); // ricevo i
		// l'intero
		// corrrispondente
		// ai soldi
		this.life = Integer.parseInt(recieveMessage());

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

	public String recieveMessage() {

		try {
			return client.recieveMessage();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(gamePane,
					"Impossibile Contattare Il server");

			sendAllFinish();

			finishGame = true;

			client.closeConnection();
			isFinishRecieve = true;
			MainMenuFrame.getInstance().switchTo(
					MainMenuFrame.getInstance().getMainMenuPanel());

		}
		return null;
	}

	public void startClient() {

		new Thread() {
			String responseFromServer;

			@Override
			public void run() {
				this.setName("RECIEVE MESSAGE_CLIENT");
				isFinishRecieve = false;
				while (!isFinishRecieve) {
					responseFromServer = recieveMessage();
					System.err.println(responseFromServer);
					if (responseFromServer != null)
						try {
							if (responseFromServer.equals("clear")) {
								finishGame = true;

							}
							if (!isLevelGame(responseFromServer)) {

								if (responseFromServer
										.equals("il giocatore uno ha lasciato la partita")
										|| responseFromServer
												.equals("il giocatore due ha lasciato la partita")) {
									isFinishRecieve = true;
								}
								executeServerResponse(responseFromServer);
							} else {
								informations.put(responseFromServer);
								isFinishRecieve = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

				}

			}
		}.start();

	}

	private boolean isLevelGame(String fromServerSentence) {
		if (fromServerSentence.equals("crossroad")
				|| fromServerSentence.equals("labyrinth")
				|| fromServerSentence.equals("spirals")
				|| fromServerSentence.equals("bastion")
				|| fromServerSentence.equals("twistedlane")
				|| fromServerSentence.equals("wasteland")
				|| fromServerSentence.equals("PlayerOneOut")
				|| fromServerSentence.equals("PlayerTwoOut")) {
			return true;
		}
		return false;
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
			if (!client.isMultiplayerGame) {
				isFinishRecieve = true;
				try {
					client.sendMessage("finish");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			UserProfile.incrLevel();
			StageClearPanel clearPanel = new StageClearPanel(this,
					gamePane.getCurrentFileLevel());
			
			if ( MainMenuFrame.getInstance().getMainMenuPanel().isStoryModeOn() ) {
				PlayerSaveState.getInstance().setScore(money);
				PlayerSaveState.getInstance().setLastLevelCleared(profile.getLastlevelComplete());
			}
			
			MainMenuFrame.getInstance().switchTo(clearPanel);
		} else if (responseFromServer.substring(0, 1).equals("o")) {
			finishGame = true;
			sendAllFinish();
			if (!client.isMultiplayerGame) {
				isFinishRecieve = true;
				try {
					client.sendMessage("finish");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			GameOverPanel gameOverPanel = new GameOverPanel(this,
					gamePane.getCurrentFileLevel());
			
			if ( MainMenuFrame.getInstance().getMainMenuPanel().isStoryModeOn() ) {
				PlayerSaveState.getInstance().setScore(money);
				PlayerSaveState.getInstance().setLastLevelCleared(profile.getLastlevelComplete());
			}
			
			

			MainMenuFrame.getInstance().switchTo(gameOverPanel);
		} else if (responseFromServer.substring(0, 1).equals("l")) {
			String[] splitted = responseFromServer.split(" ");
			if (splitted[0].equals("life")) {
				gamePane.informationPanel
						.setLife(Integer.parseInt(splitted[1]));
			} else if (splitted[0].equals("lMoney")) {
				gamePane.informationPanel.setMoney(Integer
						.parseInt(splitted[1]));

				this.money = Integer.parseInt(splitted[1]);
				gamePane.informationPanel.setMoney(Integer
						.parseInt(splitted[1]));

			} else if (splitted[0].equals("lifeRoom")) {
				gamePane.informationPanel.setRoomLife(Integer
						.parseInt(splitted[1]));

			}
		} else if (responseFromServer
				.equals("il giocatore uno ha lasciato la partita")
				|| responseFromServer
						.equals("il giocatore due ha lasciato la partita")) {

			JOptionPane.showMessageDialog(null, responseFromServer);
			finishGame = true;
			sendAllFinish();
			closeConnection();
			MainMenuFrame.getInstance().switchTo(
					MainMenuFrame.getInstance().getMainMenuPanel());

		}

		else {
			createBullets.put(responseFromServer);
		}
	}

	public void sendAllFinish() {
		try {
			outToServer.put("finish");
			movementWawe.put("finish");
			createBullets.put("finish");
			placementTrap.put("finish");
			movementWawe.put("finish");
			movementPlayer.put("finish");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

				// gamePane.informationPanel.setMoney(Integer
				// .parseInt(splitted[3]));
				//
				// this.setMoney(Integer.parseInt(splitted[3]));

			} else if (splittedMessage[0].equals("pr")) {

				Point point = new Point(Integer.parseInt(splitted[1]),
						Integer.parseInt(splitted[0]));
				gamePane.placedTrap.remove(point);
			}
		}

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
							AbstractNativeLite abstractNative = gamePane.natives
									.get(Integer.parseInt(splitted[1]));

							if (movement.substring(1, 2).equals("U")) {

								abstractNative.setX(abstractNative.getX() - 1);
								abstractNative.setDirection(0);
								gamePane.getMovementGraphicWave()
										.get(abstractNative).add(0);

							} else if (movement.substring(1, 2).equals("D")) {
								abstractNative.setX(abstractNative.getX() + 1);
								abstractNative.setDirection(1);
								gamePane.getMovementGraphicWave()
										.get(abstractNative).add(1);

							} else if (movement.substring(1, 2).equals("L")) {
								abstractNative.setY(abstractNative.getY() - 1);
								abstractNative.setDirection(2);
								gamePane.getMovementGraphicWave()
										.get(abstractNative).add(2);

							} else if (movement.substring(1, 2).equals("R")) {
								abstractNative.setY(abstractNative.getY() + 1);
								abstractNative.setDirection(3);
								gamePane.getMovementGraphicWave()
										.get(abstractNative).add(3);

							} else if (splitted[0].equals("nr")) {

								gamePane.getMovementGraphicWave().remove(
										abstractNative);
								//
								// gamePane.natives.remove(Integer
								// .parseInt(splitted[1]));

								gamePane.natives.get(
										Integer.parseInt(splitted[1]))
										.setDeath(true);
							}
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
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

							PlayerSaveState.getInstance().incrBulletState(
									Integer.parseInt(movementSplitted[3]));

							gamePane.getEastPanel().incrBulletState(
									Integer.parseInt(movementSplitted[3]));

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

		try {
			client.sendMessage(c);
		} catch (IOException e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(gamePane,
					"Impossibile Contattare Il server");

			sendAllFinish();
			finishGame = true;
			isFinishRecieve = true;
			client.closeConnection();

			MainMenuFrame.getInstance().switchTo(
					MainMenuFrame.getInstance().getMainMenuPanel());
		}

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

	public void reset() {
		finishGame = false;
		gameOver = false;
		closeConnection();

	}

	public int getCurrentGunSelected() {
		return currentGunSelected;
	}

	public void setCurrentGunSelected(int currentGunSelected) {
		this.currentGunSelected = currentGunSelected;
		pushToQueueForServer("switchGun " + currentGunSelected);
	}

	public static void waitForCondition() {
		try {
			lock.lock();
			condition.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public static void signallAll() {
		try {
			lock.lock();
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
