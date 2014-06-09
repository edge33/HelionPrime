package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.GFX.EnemyMover;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.BountyHunterNative;
import it.mat.unical.Helion_Prime.Logic.Character.Player;
import it.mat.unical.Helion_Prime.Logic.Character.SaboteurNative;
import it.mat.unical.Helion_Prime.Logic.Character.SoldierNative;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractTrap;
import it.mat.unical.Helion_Prime.Logic.Trap.TrapPower;
import it.mat.unical.Helion_Prime.Multiplayer.ServerMultiplayer;
import it.mat.unical.Helion_Prime.Online.Server;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameManagerImpl implements GameManager {

	private WorldImpl world;
	private static Lock lock;
	// MAIDA:aggiungo un campo per determinare lo stato del gioco
	private boolean gameOver;
	private Wave wave;
	private EnemyMover enemyMover;
	private Player playerOne;
	private Player playerTwo;
	private ThreadPoolBullet threadPoolbullets;
	private ConcurrentHashMap<Integer, Bullet> bullets;
	private static Condition condition;
	private static GameManagerImpl instance;
	private int currentLife;
	private ConcurrentHashMap<Point, AbstractTrap> totalPlacedTrap;
	private boolean gameStopped;

	private boolean win;
	private boolean explosion = false;
	private Server server;
	private static boolean pause;
	private boolean isMultiplayerGame;
	private ServerMultiplayer serverMultiplayer;
	private int currentRoomLife;
	private int money;
	private int oldMoney;
	private static GameManagerImpl[] instances = new GameManagerImpl[10];

	public static GameManagerImpl getInstance(int id) {
		if (instances[id] == null) {
			System.out.println("instanzio");
			instances[id] = new GameManagerImpl();
		}
		return instances[id];

	}

	/*
	 * istanzio un mondo, un' ondata di nemici, la assegno al world e prendo il
	 * player dal world.
	 */
	protected GameManagerImpl() {

	}

	public void init(File level, boolean isMultiplayerGame, int id)
			throws FileNotFoundException {

		System.out.println("SONO IN INIIIIIIIIIT");

		try {
			instances[id].bullets = new ConcurrentHashMap<Integer, Bullet>();
			instances[id].isMultiplayerGame = isMultiplayerGame;
			instances[id].world = new WorldImpl(level);
			instances[id].gameOver = false;
			instances[id].win = false;
			instances[id].gameStopped = false;
			instances[id].playerOne = new Player(world.getPlayerSpawner()
					.getX(), world.getPlayerSpawner().getY(), world, id); // ora
																			// il
																			// player
			if (isMultiplayerGame)
				instances[id].totalPlacedTrap = new ConcurrentHashMap<Point, AbstractTrap>();// sara
			// intanziato nel game
			instances[id].currentLife = instances[id].playerOne.getLife();
			instances[id].currentRoomLife = instances[id].world.getRoomLife();
			if (isMultiplayerGame) {
				instances[id].playerTwo = new Player(world.getPlayerSpawner()
						.getX(), world.getPlayerSpawner().getY(), world, id);
			}

			instances[id].money = playerOne.getMoney();
			instances[id].oldMoney = money;
			instances[id].wave = new WaveImpl(this.world, level, true, id);
			instances[id].world.setWave(wave); // manager

			instances[id].threadPoolbullets = new ThreadPoolBullet(id);
			instances[id].pause = false;

			instances[id].threadPoolbullets.start();
			instances[id].enemyMover = new EnemyMover(id);
			instances[id].enemyMover.start();
			lock = new ReentrantLock();
			this.condition = lock.newCondition();
		} catch (FileNotCorrectlyFormattedException e) {
		}

	}

	public ConcurrentHashMap<Integer, Bullet> getBullets() {
		return this.bullets;
	}

	public static boolean isPaused() {
		return pause;
	}

	public void setPause() {
		try {
			lock.lock();
			if (pause) {
				pause = false;
				condition.signalAll();
			} else {
				pause = true;
			}
		} finally {
			lock.unlock();
		}

	}

	// restituisce lo stato del gioco
	@Override
	public boolean gameIsOver() {
		return gameOver;
	}

	// termina il gioco

	@Override
	public void endGame() {
		this.bullets.clear();
		for (Point iterable_element : playerOne.getTrap().keySet()) {

			if (playerOne.getTrap().get(iterable_element) instanceof TrapPower) {
				((TrapPower) playerOne.getTrap().get(iterable_element)).kill();

			}
		}
		if (isMultiplayerGame) {
			for (Point iterable_element : playerTwo.getTrap().keySet()) {

				if (playerTwo.getTrap().get(iterable_element) instanceof TrapPower) {
					((TrapPower) playerTwo.getTrap().get(iterable_element))
							.kill();

				}
			}
			playerTwo.getTrap().clear();
		}

		playerOne.getTrap().clear();
		gameOver = true;

		// if (!this.isMultiplayerGame) {
		// this.server.isGameOver = true;
		//
		// }

	}

	// restituisce l'ondata corrente
	public Wave getWaveImpl() {
		return wave;
	}

	// restituisce il mondo attualmente allocato
	@Override
	public World getWorld() {
		return world;
	}

	// muove il personaggio
	public void movePlayerOne(int direction) {
		playerOne.move(direction);
	}

	public void movePlayerTwo(int direction) {
		playerOne.move(direction);
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setServerMultiplayer(ServerMultiplayer server) {
		this.serverMultiplayer = server;
	}

	public Server getServer() {
		return this.server;

	}

	/*
	 * se, sulla mappa in posizione x,y non è istanziato niente, posso
	 * posizionare la trappola
	 */
	public boolean canPlaceTrap(int positionX, int positionY) {
		if (world.getElementAt(positionX, positionY) == null)
			return true;
		return false;
	}

	/*
	 * se posso posizionare la trappola numberOfTrapInArray, in posizione x,y la
	 * posiziono
	 */
	public boolean placeTrap(int positionX, int positionY,
			int numberOfTrapInArray, Player selectedPlayer) {
		boolean response = false;
		if (selectedPlayer.canPlaceTrap(numberOfTrapInArray, positionX,
				positionY)) {
			response = true;

		} else
			response = false;

		return response;

	}

	/*
	 * aggiorna lo stato del gioco, per ora aggiorna solo la posizione dei
	 * nemici, ed elimina quelli morti.
	 */
	@Override
	public void update() {
		explosion = false;
		int ritorno = 0;
		ConcurrentHashMap<Integer, AbstractNative> natives = wave.getNatives();

		if (currentLife != playerOne.getLife()) {
			server.sendMessage("life " + playerOne.getLife());
			currentLife = playerOne.getLife();
		}
		if (world.getRoomLife() != currentRoomLife) {
			server.sendMessage("lifeRoom " + world.getRoomLife());
			currentRoomLife = world.getRoomLife();

		}

		if (natives.size() > 0) {

			for (AbstractNative currentNative : natives.values()) {

				if (world.getElementAt(currentNative.getX(),
						currentNative.getY()) instanceof AbstractTrap) {

					AbstractTrap currentTrap = (AbstractTrap) world
							.getElementAt(currentNative.getX(),
									currentNative.getY());
					if (currentTrap.getLife() <= 0) {

						Point point = new Point(currentNative.getX(),
								currentNative.getY());

						playerOne
								.getTrap()
								.remove(point,
										world.getWorld()[currentNative.getX()][currentNative
												.getY()]);
						world.getWorld()[currentNative.getX()][currentNative
								.getY()] = null;

						currentNative.setTrapToFind(null);

						server.sendMessage("pr " + currentNative.getX() + "/"
								+ currentNative.getY());

					}

				}

				if (currentNative.getLife() <= 0) {

					if (currentNative instanceof SaboteurNative) {
						incrMoney(350);
					} else if (currentNative instanceof BountyHunterNative) {
						incrMoney(250);
					} else if (currentNative instanceof SoldierNative) {
						incrMoney(100);
					}

					natives.remove(currentNative.getKey());
					AbstractTrap currentTrap = (AbstractTrap) world
							.getElementAt(currentNative.getX(),
									currentNative.getY());
					if (currentTrap != null)
						currentTrap.setCaptured(false);
					this.server.sendMessage("nr " + currentNative.getKey());
				}

			}

			if (!playerOne.isAlive()
					|| ((MaintenanceRoom) world.getRoom()).getLife() <= 0) {
				this.server.sendMessage("over");
				this.endGame();
				this.server.isGameOver = true;
				gameOver = true;
				gameStopped = true;

			}

		} else {
			this.server.sendMessage("clear");
			this.endGame();
			this.server.isStageClear = true;
			win = true;
			gameStopped = true;

		}

		if (this.money != oldMoney) {
			this.server.sendMessage("lMoney " + money);
			oldMoney = money;
		}

	}

	public void updateMultiplayer() {

		explosion = false;
		int ritorno = 0;
		ConcurrentHashMap<Integer, AbstractNative> natives = wave.getNatives();

		if (natives.size() > 0) {

			for (AbstractNative currentNative : natives.values()) {

				if (world.getElementAt(currentNative.getX(),
						currentNative.getY()) instanceof AbstractTrap) {

					AbstractTrap currentTrap = (AbstractTrap) world
							.getElementAt(currentNative.getX(),
									currentNative.getY());

					currentTrap.effect(currentNative);

					if (currentTrap.getLife() <= 0) {

						Point point = new Point(currentNative.getX(),
								currentNative.getY());

						playerOne
								.getTrap()
								.remove(point,
										world.getWorld()[currentNative.getX()][currentNative
												.getY()]);

						playerTwo
								.getTrap()
								.remove(point,
										world.getWorld()[currentNative.getX()][currentNative
												.getY()]);

						totalPlacedTrap
								.remove(point, world.getWorld()[currentNative
										.getX()][currentNative.getY()]);

						world.getWorld()[currentNative.getX()][currentNative
								.getY()] = null;

						currentNative.setTrapToFind(null);

						serverMultiplayer.outBroadcast("pr "
								+ currentNative.getX() + "/"
								+ currentNative.getY());

					}

				}

				if (currentNative.getLife() <= 0) {

					if (currentNative instanceof SaboteurNative) {
						incrMoney(350);
					} else if (currentNative instanceof BountyHunterNative) {
						incrMoney(250);
					} else if (currentNative instanceof SoldierNative) {
						incrMoney(100);
					}

					natives.remove(currentNative.getKey());
					AbstractTrap currentTrap = (AbstractTrap) world
							.getElementAt(currentNative.getX(),
									currentNative.getY());
					if (currentTrap != null)
						currentTrap.setCaptured(false);

					this.serverMultiplayer.outBroadcast("nr "
							+ currentNative.getKey());
				}

			}

			if (!playerTwo.isAlive() || !playerOne.isAlive()
					|| ((MaintenanceRoom) world.getRoom()).getLife() <= 0) {
				this.endGame();
				gameOver = true;
				gameStopped = true;
				System.err.println("GAME OVER");
				this.serverMultiplayer.outBroadcast("over");
			}

		} else {
			this.endGame();
			win = true;
			gameStopped = true;
			this.serverMultiplayer.outBroadcast("clear");
		}

		if (this.money != oldMoney) {
			this.serverMultiplayer.outBroadcast("lMoney " + money);
			oldMoney = money;
		}

	}

	@Override
	public boolean hasWon() {
		return win;
	}

	@Override
	public void stopGame() {
		this.bullets.clear();
		this.playerOne.getTrap().clear();
		if (isMultiplayerGame) {
			this.playerTwo.getTrap().clear();
		}
		gameStopped = true;
	}

	public boolean isGameStopped() {
		return gameStopped;
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

	public boolean isExplosion() {
		return explosion;
	}

	public void setExplosion(boolean explosion) {
		this.explosion = explosion;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public boolean isMultiplayerGame() {
		return this.isMultiplayerGame;
	}

	public ServerMultiplayer getServerMuliplayer() {
		return this.serverMultiplayer;
	}

	public ConcurrentHashMap<Point, AbstractTrap> getTotalPlacedTrap() {
		return totalPlacedTrap;
	}

	public void setTotalPlacedTrap(
			ConcurrentHashMap<Point, AbstractTrap> totalPlacedTrap) {
		this.totalPlacedTrap = totalPlacedTrap;
	}

	public int getMoney() {
		// TODO Auto-generated method stub
		return money;
	}

	public void incrMoney(int a) {
		money += a;

	}

	public void dimMoney(int a) {
		if (money > 0 && money - a >= 0)
			money -= a;
		else
			money = 0;

	}
}
