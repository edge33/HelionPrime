package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.GFX.EnemyMover;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.Player;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractTrap;
import it.mat.unical.Helion_Prime.Logic.Trap.DecoyTrap;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Online.Server;

public class GameManagerImpl implements GameManager {

	private WorldImpl world;
	private static Lock lock;
	// MAIDA:aggiungo un campo per determinare lo stato del gioco
	private boolean gameOver;
	private Wave wave;
	private EnemyMover enemyMover;
	private Player player;
	private ThreadPoolBullet threadPoolbullets;
	private static Condition condition;
	private static GameManagerImpl instance;

	private boolean gameStopped;

	private boolean win;
	private boolean explosion = false;
	private Server server;
	private static boolean pause;

	public static GameManagerImpl getInstance() {
		if (instance == null) {
			System.out.println("instanzio");
			instance = new GameManagerImpl();
		}
		return instance;

	}

	/*
	 * istanzio un mondo, un' ondata di nemici, la assegno al world e prendo il
	 * player dal world.
	 */
	private GameManagerImpl() {

	}

	public void init(File level) throws FileNotFoundException {

		try {

			world = new WorldImpl(level);
			gameOver = false;
			win = false;
			gameStopped = false;
			player = new Player(world.getPlayerSpawner().getX(), world
					.getPlayerSpawner().getY(), world); // ora il player sara
			// intanziato nel game

			wave = new WaveImpl(this.world, level);
			world.setWave(wave); // manager

			threadPoolbullets = new ThreadPoolBullet();
			pause = false;

			threadPoolbullets.start();
			enemyMover = new EnemyMover();
			enemyMover.start();
			lock = new ReentrantLock();
			this.condition = lock.newCondition();
		} catch (FileNotCorrectlyFormattedException e) {
		}

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
		AbstractGun.bullets.clear();
		gameOver = true;
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
	public void movePlayer(int direction) {
		player.move(direction);
	}

	public void setServer(Server server) {
		this.server = server;
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
			int numberOfTrapInArray) {
		boolean response = false;
		if (player.canPlaceTrap(numberOfTrapInArray, positionX, positionY)) {
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

		if (natives.size() > 0) {

			for (AbstractNative currentNative : natives.values()) {

				if (world.getElementAt(currentNative.getX(),
						currentNative.getY()) instanceof AbstractTrap) {

					AbstractTrap currentTrap = (AbstractTrap) world
							.getElementAt(currentNative.getX(),
									currentNative.getY());
					if (currentTrap instanceof DecoyTrap) {
						currentTrap.effect(currentNative);
						if (((DecoyTrap) currentTrap).getDecoyLife() == 1) {
							explosion = true;
							System.out.println("GMI - explosion= true");
						}
					} else {
						currentTrap.effect(currentNative);
					}
					if (currentTrap.getLife() <= 0) {

						Point point = new Point(currentNative.getX(),
								currentNative.getY());

						player.getTrap()
								.remove(point,
										world.getWorld()[currentNative.getX()][currentNative
												.getY()]);
						world.getWorld()[currentNative.getX()][currentNative
								.getY()] = null;

						server.sendMessage("pr " + currentNative.getX() + "/"
								+ currentNative.getY());

					}

				}

				if (currentNative.getLife() <= 0) {
					natives.remove(currentNative.getKey());
					this.server.sendMessage("nr " + currentNative.getKey());
				}

			}

			if (!player.isAlive()
					|| ((MaintenanceRoom) world.getRoom()).getLife() <= 0) {
				this.endGame();
				gameOver = true;
				gameStopped = true;
				this.server.sendMessage("over");
			}

		} else {
			win = true;
			this.endGame();
			gameStopped = true;
			this.server.sendMessage("clear");
		}

	}

	@Override
	public boolean hasWon() {
		return win;
	}

	@Override
	public void stopGame() {
		AbstractGun.bullets.clear();
		this.player.getTrap().clear();

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

	public Player getPlayer() {
		return player;
	}
}
