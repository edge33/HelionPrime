package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.GamePad.GamePadController;
import it.mat.unical.Helion_Prime.Logic.Corner;
import it.mat.unical.Helion_Prime.Logic.FileNotCorrectlyFormattedException;
import it.mat.unical.Helion_Prime.Logic.MaintenanceRoom;
import it.mat.unical.Helion_Prime.Logic.NativeSpawner;
import it.mat.unical.Helion_Prime.Logic.PlayerSpawner;
import it.mat.unical.Helion_Prime.Logic.StaticObject;
import it.mat.unical.Helion_Prime.Logic.Wall;
import it.mat.unical.Helion_Prime.Logic.Wave;
import it.mat.unical.Helion_Prime.Logic.WaveImpl;
import it.mat.unical.Helion_Prime.Logic.WorldImpl;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePane extends JPanel {
	/*
	 * 
	 * incviare al managerOnline il livello scelto
	 */

	public static final int TILE_SIZE = 50;
	private JLabel controllerInfo;
	private ImageProvider imageProvider;
	private int playerX, playerY, logicX, logicY;
	private int imagePlayer;
	private int money;
	private int playerDirection;
	private double scaleFactor;
	private int currentRoomLife;
	int cont = 0;
	private HashMap<Point, Integer> placedTrap;
	private ConcurrentHashMap<Integer, BulletsClient> bullets;
	// private static Integer codeBullet = 0;
	private int curentLife;
	private int sizeBulletForPrint = 0; // quando avremo le immaggini per i
	// proiettili quasta variabile non ci
	// servira piu
	private int movementOffset = TILE_SIZE / 10;
	private int drawingHorizontalOffset;

	private boolean UP = false;
	private boolean DOWN = false;
	private boolean LEFT = false;
	private boolean RIGHT = false;
	private boolean STAND = false;
	private boolean shoot = false;
	private boolean shooter = true;
	private boolean threadAlive = false;
	private WorldImpl world;
	private Client client;
	private Wave wave;

	private ArrayBlockingQueue<String> movementBullets;
	private ArrayBlockingQueue<String> movementPlayer;
	private ArrayBlockingQueue<String> movementWawe;
	private ArrayBlockingQueue<String> placementTrap;
	public TrapPanel trapPanel;
	private InformationPanel informationPanel;
	private ThreadPoolBulletClient threadPoolClient;
	private GamePadController gamePadController;
	private ConcurrentHashMap<Integer, AbstractNative> natives;
	private int currentGunSelected = 0;
	private boolean levelClear = false;
	// protected int idButton = 0;
	private static final int DELAY = 40;

	public GamePane(File level, Client client, TrapPanel trapPanel,
			InformationPanel informationPanel,
			GamePadController gamePadController)
			throws FileNotCorrectlyFormattedException {
		super();
		Image currentLabelImage = null;
		try {
			currentLabelImage = ImageIO.read(new File("Resources/MiniPad.png"));
		} catch (IOException e) {
			System.out.println("SpikeTrapIcon Mancante");
		}
		try {
			currentLabelImage = ImageIO.read(new File("Resources/MiniPad.png"));
		} catch (IOException e) {
			System.out.println("PadPrewiew Mancante");
		}
		this.setLayout(null);
		this.client = client;
		this.controllerInfo = new JLabel(new ImageIcon(currentLabelImage));
		this.controllerInfo.setForeground(Color.MAGENTA);
		this.controllerInfo.setVisible(false);
		this.controllerInfo.setFont(controllerInfo.getFont().deriveFont(50.0f));
		this.controllerInfo.setBounds(200, 350, 636, 248);
		this.gamePadController = gamePadController;
		this.trapPanel = trapPanel;
		this.informationPanel = informationPanel;
		this.placedTrap = new HashMap<Point, Integer>();
		this.bullets = new ConcurrentHashMap<Integer, BulletsClient>();
		this.movementPlayer = new ArrayBlockingQueue<String>(20);
		this.placementTrap = new ArrayBlockingQueue<String>(20);
		this.movementBullets = new ArrayBlockingQueue<String>(20);
		this.movementWawe = new ArrayBlockingQueue<String>(20);
		// this.threadPoolClient = new ThreadPoolBulletClient(bullets);
		// this.manager.init(level);
		// this.threadPoolClient.start();
		this.world = new WorldImpl(level);

		this.money = Integer.parseInt(client.recieveMessage()); // ricevo i
																// l'intero
																// corrrispondente
																// hai soldi
		int life = Integer.parseInt(client.recieveMessage());
		this.imageProvider = new ImageProvider();
		informationPanel.setMoney(money);
		informationPanel.setLife(life);
		informationPanel.setRoomLife(world.getRoomLife());

		logicX = world.getPlayerSpawner().getX();
		logicY = world.getPlayerSpawner().getY();

		playerX = world.getPlayerSpawner().getX() * TILE_SIZE;
		playerY = world.getPlayerSpawner().getY() * TILE_SIZE;

		this.playerDirection = 2;

		try {
			this.wave = new WaveImpl(this.world, level);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.natives = wave.getNatives();

		for (AbstractNative currentNative : natives.values()) {
			currentNative.setX(this.world.getNativeSpawner().getX());
			currentNative.setY(this.world.getNativeSpawner().getY());
			currentNative.setGraphicX(this.world.getNativeSpawner().getX()
					* TILE_SIZE);
			currentNative.setGraphicY(this.world.getNativeSpawner().getY()
					* TILE_SIZE);
		}

		Repainter repainter = new Repainter(this);
		repainter.start();
		this.setFocusable(true);

		this.add(controllerInfo);

		if (gamePadController.isPadConnected() == true) {
			// startPolling();
		} else {
			this.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent arg0) {

					double x, y;
					x = arg0.getX();
					y = arg0.getY();
					System.out.println("x:" + x);
					System.out.println("y:" + y);
					int realX = (int) (x / (TILE_SIZE * scaleFactor));
					int realY = (int) (y / (TILE_SIZE * scaleFactor));

					System.out.println(realX + " " + realY);
					Integer numberOftrap = GamePane.this.trapPanel
							.getCurrentTrapSelected();
					GamePane.this.client.sendMessage("place.trap "
							+ numberOftrap + "/" + realX + "/" + realY);

				}
			});
			this.addKeyListener(new KeyAdapter() { // semplice key listener che
				// cattura l'evento e muove il
				// nostro player

				@Override
				public void keyReleased(KeyEvent arg0) {
					imagePlayer = 0; // per il momento la posizione di default
					// quando viene rilasciato il tasto è quella
					// con il player rivolto verso
					// l'alto ossia posizione 2

					switch (arg0.getKeyCode()) {
					case KeyEvent.VK_1:
						// GamePane.this.playerOne.SwitchGun(0);
						GamePane.this.trapPanel.selectGun();
						GamePane.this.client.sendMessage("switchGun 0");
						break;
					case KeyEvent.VK_2:
						GamePane.this.trapPanel.selectUzi();
						GamePane.this.client.sendMessage("switchGun 1");
						currentGunSelected = 1;
						break;
					case KeyEvent.VK_3:
						currentGunSelected = 2;
						GamePane.this.trapPanel.selectShootGun();
						GamePane.this.client.sendMessage("switchGun 2");
						break;
					case KeyEvent.VK_4:
						currentGunSelected = 3;
						GamePane.this.trapPanel.selectHeavy();
						GamePane.this.client.sendMessage("switchGun 3");
						break;
					case KeyEvent.VK_W:
						GamePane.this.UP = false;

						// playerDirection = -1;
						break;
					case KeyEvent.VK_S:
						GamePane.this.DOWN = false;
						// playerDirection = -2;
						break;
					case KeyEvent.VK_A:
						GamePane.this.RIGHT = false;
						// playerDirection = -3;
						break;
					case KeyEvent.VK_D:
						GamePane.this.LEFT = false;
						// playerDirection = -3;
						break;

					case KeyEvent.VK_SPACE:
						shoot = false;
						shooter = true;
						// if (!(manager.getPlayer().getCurrentGunSelected()
						// instanceof UziGun)) {
						// playerOne.shoot();
						// }

						GamePane.this.client.sendMessage("s "
								+ currentGunSelected);
						break;
					default:
						break;
					}

				}

				// MAIDA: muovono il personaggio, ad ogni pressione di tasto gli
				// cambio la direzione

				@Override
				public void keyPressed(KeyEvent paramKeyEvent) {

					if (paramKeyEvent.getKeyCode() == KeyEvent.VK_W) {

						GamePane.this.UP = true;
						playerDirection = 0;

						// playerOne.setDirection(AbstractCharacter.UP);

					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_S) {
						GamePane.this.DOWN = true;
						playerDirection = 1;

						// playerOne.setDirection(AbstractCharacter.DOWN);

					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_A) {
						GamePane.this.RIGHT = true;
						playerDirection = 2;

						// playerOne.setDirection(AbstractCharacter.LEFT);

					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_D) {
						GamePane.this.LEFT = true;
						playerDirection = 3;

						// playerOne.setDirection(AbstractCharacter.RIGHT);
					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_P) {

						// GameManagerImpl.getInstance().setPause();

					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
						// shootForUziGun();
					}

				}

			});
		}

		new Thread() {

			public void run() {

				// while (!manager.gameIsOver() && !manager.isGameStopped()) {
				//
				// GameManagerImpl.getInstance();
				// while (GameManagerImpl.isPaused()) {
				// GameManagerImpl.waitForCondition();
				// }
				while (true) {

					if (UP) {
						imagePlayer = 1;
						GamePane.this.client.sendMessage("dUP");
						if (!(world.getElementAt(logicX - 1, logicY) instanceof Wall)
								|| playerX > logicX * TILE_SIZE) {

							playerX -= movementOffset;
						}

						if (playerX <= ((logicX - 1) * TILE_SIZE) + TILE_SIZE
								/ 3) {
							GamePane.this.client.sendMessage("mUP");

							logicX -= 1;
						}
						// gameManager.movePlayer(0);

					}

					else if (DOWN) { // DOWN
						GamePane.this.client.sendMessage("dDOWN");
						imagePlayer = 2;
						if (!(world.getElementAt(logicX + 1, logicY) instanceof Wall)
								|| playerX < logicX * TILE_SIZE) {

							playerX += movementOffset;
						}

						if (playerX > ((logicX + 1) * TILE_SIZE) - TILE_SIZE
								/ 2) {
							// gameManager.movePlayer(1);
							GamePane.this.client.sendMessage("mDOWN");
							logicX += 1;
						}
					}

					else if (RIGHT) {
						GamePane.this.client.sendMessage("dRIGHT");
						imagePlayer = 3;
						if (!(world.getElementAt(logicX, logicY - 1) instanceof Wall)
								|| playerY > logicY * TILE_SIZE) {

							playerY -= movementOffset;

						}
						if (playerY < ((logicY - 1) * TILE_SIZE) + TILE_SIZE
								/ 2) {
							// gameManager.movePlayer(2);

							GamePane.this.client.sendMessage("mRIGHT");
							logicY -= 1;
						}
					}

					else if (LEFT) {
						GamePane.this.client.sendMessage("dLEFT");
						imagePlayer = 4;
						if (!(world.getElementAt(logicX, logicY + 1) instanceof Wall)
								|| playerY < logicY * TILE_SIZE) {

							playerY += movementOffset;

						}
						if (playerY > ((logicY + 1) * TILE_SIZE) - TILE_SIZE
								/ 2) {
							// gameManager.movePlayer(3);
							GamePane.this.client.sendMessage("mLEFT");
							logicY += 1;
						}
					}
					try {
						sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			// if (manager.gameIsOver() && manager.isGameStopped()) {
			// if (manager.hasWon()) {
			// StageClearPanel stageClearPanel = new StageClearPanel();
			// System.out.println("Game Pane - ho vinto");
			// MainMenuFrame.getInstance().switchTo(stageClearPanel);
			// } else {
			// GameOverPanel gameOverPanel = new GameOverPanel();
			// MainMenuFrame.getInstance().switchTo(gameOverPanel);
			// }
			// } else
			// MainMenuFrame.getInstance().switchTo(
			// MainMenuFrame.getInstance().getMainMenuPanel());
			// System.out.println("esco dal trhead update");
			// manager.update();

		}.start();
		this.startClient();
		this.startMovementPlayer();
		this.startPlacementTrap();
		this.startMovementOfBullets();
		this.startMovementWave();
		this.setVisible(true);
	}

	// protected void shootForUziGun() {
	// if (playerOne.getCurrentGunSelected() instanceof UziGun && shooter
	// && !threadAlive) {
	// shooter = false;
	// shoot = true;
	// threadAlive = true;
	// new Thread() {
	// public void run() {
	//
	// while (shoot && !GameManagerImpl.getInstance().gameIsOver()
	// && !GameManagerImpl.getInstance().isGameStopped()) {
	// GamePane.this.playerOne.shoot();
	//
	// try {
	// sleep(100);
	// } catch (InterruptedException e) {
	//
	// e.printStackTrace();
	// }
	//
	// }
	// threadAlive = false;
	// System.out.println("esco da thread shoot");
	// }
	// }.start();
	//
	// }
	// }

	@Override
	public void paintComponent(Graphics g) {
		boolean firstTimeRepaint = false;
		// informationPanel.setLife(playerOne.getLife());
		// currentRoomLife = ((MaintenanceRoom) GameManagerImpl.getInstance()
		// .getWorld().getRoom()).getLife();
		// informationPanel.setRoomLife(currentRoomLife);
		informationPanel.repaint();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight()); // colorazione di
		// sfondo nero

		double hScale = getWidth() / (double) (world.getLenght() * TILE_SIZE);
		double vScale = getHeight() / (double) (world.getHeight() * TILE_SIZE);
		// setSize((int) hScale, (int) vScale);
		double min = Math.min(hScale, vScale);
		this.scaleFactor = min;

		// Maida: ancora non va bene devo trovare il modo di risolvere
		// this./*drawingHorizontalOffset*/ = (int)
		// (world.getLenght()/2*TILE_SIZE*scaleFactor );
		((Graphics2D) g).scale(min, min);

		if (firstTimeRepaint == false) {
			drawFloor(g);
			firstTimeRepaint = true;
		}

		for (int i = 0; i < world.getHeight(); i++) { // questi for vanno a
			// disegnare tutti gli
			// elementi contenuti
			// nella "Matrice"
			// logica di gioco
			for (int j = 0; j < world.getLenght(); j++) {
				StaticObject element = world.getElementAt(i, j);

				if (element instanceof Corner) {
					int type = ((Corner) element).getType();
					switch (type) {
					case 8:
						g.drawImage(imageProvider.getI8(),
						/* drawingHorizontalOffset */+(j * TILE_SIZE), i
								* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						break;
					case 9:
						g.drawImage(imageProvider.getI9(),
						/* drawingHorizontalOffset */+(j * TILE_SIZE), i
								* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						break;
					case 10:
						g.drawImage(imageProvider.getI10(),
						/* drawingHorizontalOffset */+(j * TILE_SIZE), i
								* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						break;
					case 11:
						g.drawImage(imageProvider.getI11(),
						/* drawingHorizontalOffset */+(j * TILE_SIZE), i
								* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						break;
					}
				}

				else if (element instanceof Wall) {
					if (((Wall) element).getType() == 0)
						g.drawImage(imageProvider.getWall(),
						/* drawingHorizontalOffset */+(j * TILE_SIZE), i
								* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					else
						g.drawImage(imageProvider.getFlippedWall(),
						/* drawingHorizontalOffset */+(j * TILE_SIZE), i
								* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				} else if (element instanceof NativeSpawner) {
					g.drawImage(imageProvider.getEnemyS(),
					/* drawingHorizontalOffset */+(j * TILE_SIZE), i
							* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

				} else if (element instanceof MaintenanceRoom) {
					g.drawImage(imageProvider.getRoom(),
					/* drawingHorizontalOffset */+(j * TILE_SIZE), i
							* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				} else if (element instanceof PlayerSpawner) {
					g.drawImage(imageProvider.getSpawn(),
					/* drawingHorizontalOffset */+(j * TILE_SIZE), i
							* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
		}

		for (Point point : placedTrap.keySet()) {

			switch (placedTrap.get(point)) {
			case 0:
				g.drawImage(imageProvider.getSpikeTrap(),
						+(point.x * TILE_SIZE), point.y * TILE_SIZE, TILE_SIZE,
						TILE_SIZE, this);
				break;
			case 1:
				g.drawImage(imageProvider.getFireTrap(),
						+(point.x * TILE_SIZE), point.y * TILE_SIZE, TILE_SIZE,
						TILE_SIZE, this);
				break;
			case 3:
				g.drawImage(imageProvider.getAcidTrap(),
				/* drawingHorizontalOffset */+(point.x * TILE_SIZE), point.y
						* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				break;
			case 4:
				g.drawImage(imageProvider.getElectricTrap(),
				/* drawingHorizontalOffset */+(point.x * TILE_SIZE), point.y
						* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				break;
			case 5:
				g.drawImage(imageProvider.getAcidTrap(),
				/* drawingHorizontalOffset */+(point.x * TILE_SIZE), point.y
						* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				break;
			case 6:
				g.drawImage(imageProvider.getAcidTrap(),
				/* drawingHorizontalOffset */+(point.x * TILE_SIZE), point.y
						* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				break;
			case 7:
				g.drawImage(imageProvider.getDecoyTrap(),
				/* drawingHorizontalOffset */+(point.x * TILE_SIZE), point.y
						* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				break;
			default:
				break;
			}

		}

		/*
		 * g.setColor(Color.YELLOW); // posizione nella logica !!! //
		 * g.fillRect(/* drawingHorizontalOffset +playerOne.getY()
		 */
		// * TILE_SIZE, playerOne.getX() * TILE_SIZE, TILE_SIZE,
		// TILE_SIZE);

		// ArrayList<Bullet> bullets = playerOne.getCurrentGunSelected()
		// .getBullets();
		//
		// // questo for rimuove i proiettili "morti" dall
		// // array dei proiettili sparati NB: SUCCESSIVAMENTE VA
		// // SPOSTATO NELL UPDATE DEL GAMEMANAGER PER ALLEGGERIRE IL PAINT
		// // COMPONENT
		//

		// ConcurrentHashMap<Integer, Bullet> bullets = GameManagerImpl
		// .getInstance().getPlayer().getCurrentGunSelected().getBullets();
		for (BulletsClient bulletClient : bullets.values()) {
			//
			// if (this.playerOne.getCurrentGunSelected() instanceof RifleGun)
			// sizeBulletForPrint = 15;
			//
			// if (this.playerOne.getCurrentGunSelected() instanceof UziGun)
			// sizeBulletForPrint = 7;
			//
			// if (this.playerOne.getCurrentGunSelected() instanceof SimpleGun)
			// sizeBulletForPrint = 10;
			//
			// if (this.playerOne.getCurrentGunSelected() instanceof
			// HeavyWeapon)
			// sizeBulletForPrint = 25;
			//
			g.setColor(Color.BLACK);
			// g.fillOval(bullets.get(i).getGraphicY(), bullets.get(i)
			// .getGraphicX(), 10, 10);
			g.fillOval(bulletClient.getY() * TILE_SIZE + TILE_SIZE / 2,
					bulletClient.getX() * TILE_SIZE + TILE_SIZE / 2, 10, 10);

		}
		//
		// // in base alla direzione del player stampa
		// // l'immagine corrispondente

		switch (imagePlayer) {
		case 0:
			g.drawImage(imageProvider.getPlayerStanding(), playerY
			/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
					this);
			break;

		case 1:
			g.drawImage(imageProvider.getPlayerUpRunning(), playerY
			/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
					this);
			break;
		case 2:
			g.drawImage(imageProvider.getPlayerDownRunning(), playerY
			/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
					this);
			break;
		case 3:
			g.drawImage(imageProvider.getPlayerRightRunning(), playerY
			/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
					this);
			break;
		case 4:
			g.drawImage(imageProvider.getPlayerLeftRunning(), playerY
			/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
					this);
			break;
		default:
			break;
		}

		informationPanel.repaint();
		//
		// ConcurrentHashMap<Integer, AbstractNative> natives = manager
		// .getWaveImpl().getNatives();
		//
		// // Disegna semplicemente i vari nemici
		// // contenuti nell array per il momento come
		// // semplice cerchi poi saranno sostituite con le immagini
		//
		for (AbstractNative currentNative : natives.values()) {

			// g.setColor(Color.red);
			// g.fillRect(/* drawingHorizontalOffset */+currentNative.getY()
			// * TILE_SIZE, currentNative.getX() * TILE_SIZE, TILE_SIZE,
			// TILE_SIZE);

			// g.drawImage(imageProvider.getCorrectNative(currentNative),
			// /* drawingHorizontalOffset */+currentNative.getGraphicY() + 10,
			// currentNative.getGraphicX() + 10, TILE_SIZE, TILE_SIZE,
			// this);

			g.drawImage(imageProvider.getCorrectNative(currentNative),
			/* drawingHorizontalOffset */+currentNative.getY() * TILE_SIZE,
					currentNative.getX() * TILE_SIZE, TILE_SIZE, TILE_SIZE,
					this);

			// if (currentNative instanceof SoldierNative) {
			// g.setColor(Color.BLACK);
			// g.fillOval(/* drawingHorizontalOffset */+currentNative
			// .getGraphicY(), currentNative.getGraphicX() + 10, 25,
			// 25);
			// } else if (currentNative instanceof BountyHunterNative) {
			// g.setColor(Color.GREEN);
			// g.fillRect(/* drawingHorizontalOffset */+currentNative
			// .getGraphicY(), currentNative.getGraphicX() + 10, 25,
			// 25);
			// } else if (currentNative instanceof SaboteurNative) {
			// g.setColor(Color.MAGENTA);
			// g.fillOval(/* drawingHorizontalOffset */+currentNative
			// .getGraphicY(), currentNative.getGraphicX() + 10, 25,
			// 25);
			// }

			// if (GameManagerImpl.isPaused()) {
			// g.setColor(new Color(0, 0, 0, 34));
			//
			// g.fillRect(0, 0, this.getWidth() * TILE_SIZE, this.getHeight()
			// * TILE_SIZE);
			// }
		}

	}

	public int getBuilderHeight() {
		return world.getHeight() * TILE_SIZE;
	}

	public int getBuilderWidth() {
		return (world.getLenght() - 1) * TILE_SIZE;
	}

	public ImageProvider getImageProvider() {
		return imageProvider;
	}

	public void drawFloor(Graphics g) {
		boolean grayFloor = false;
		if (world.getLenght() % 2 == 0) {
			for (int i = 0; i < world.getHeight(); i++) {
				for (int j = 0; j < world.getLenght() - 1; j++) {
					if (grayFloor == true) {
						g.drawImage(imageProvider.getFloor(),/* drawingHorizontalOffset */
						+(j * TILE_SIZE), i * TILE_SIZE, this);
						grayFloor = false;
					} else {
						g.drawImage(imageProvider.getFloor2(),/* drawingHorizontalOffset */
						+(j * TILE_SIZE), i * TILE_SIZE, this);
						grayFloor = true;
					}
				}
			}
		} else {
			for (int i = 0; i < world.getHeight(); i++) {
				for (int j = 1; j < world.getLenght() - 1; j++) {
					if (grayFloor == true) {
						g.drawImage(imageProvider.getFloor(),/* drawingHorizontalOffset */
						+(j * TILE_SIZE), i * TILE_SIZE, this);
						grayFloor = false;
					} else {
						g.drawImage(imageProvider.getFloor2(),/* drawingHorizontalOffset */
						+(j * TILE_SIZE), i * TILE_SIZE, this);
						grayFloor = true;
					}
				}
			}
		}
	}

	public void showControllerInfo() {

	}

	/*
	 * Set up a timer which is activated every DELAY ms and polls the game pad
	 * and updates the GUI. Safe since the action handler is executed in the
	 * event-dispatching thread.
	 */
	public void startPolling() {

		// new Thread() {
		//
		// public void run() {
		// while (!manager.isGameStopped() && !manager.gameIsOver()) {
		// gamePadController.poll();
		// int povDirection = gamePadController.getHatDir();
		// int x = 0, y = 0;
		//
		// switch (povDirection) {
		// case 1:
		// x = (playerOne.getX()) - 1;
		// System.out.println(x);
		// y = (playerOne.getY());
		// System.out.println(y);
		// manager.placeTrap(x, y, GamePane.this.trapPanel
		// .getCurrentTrapSelected());
		// GamePane.this.informationPanel.setMoney(playerOne
		// .getMoney());
		// break;
		// case 3:
		// x = (playerOne.getX());
		// System.out.println(x);
		// y = (playerOne.getY()) - 1;
		// System.out.println(y);
		// manager.placeTrap(x, y, GamePane.this.trapPanel
		// .getCurrentTrapSelected());
		// GamePane.this.informationPanel.setMoney(playerOne
		// .getMoney());
		// break;
		// case 5:
		// x = (playerOne.getX());
		// System.out.println(x);
		// y = (playerOne.getY()) + 1;
		// System.out.println(y);
		// manager.placeTrap(x, y, GamePane.this.trapPanel
		// .getCurrentTrapSelected());
		// GamePane.this.informationPanel.setMoney(playerOne
		// .getMoney());
		// break;
		// case 7:
		// x = (playerOne.getX()) + 1;
		// System.out.println(x);
		// y = (playerOne.getY());
		// System.out.println(y);
		// manager.placeTrap(x, y, GamePane.this.trapPanel
		// .getCurrentTrapSelected());
		// GamePane.this.informationPanel.setMoney(playerOne
		// .getMoney());
		// break;
		// default:
		// break;
		// }
		//
		// // get compass direction for the two analog sticks
		// int XYDirection = gamePadController.getXYStickDir();
		// switch (XYDirection) {
		// case 1:
		// GamePane.this.UP = true;
		// GamePane.this.STAND = false;
		// GamePane.this.DOWN = false;
		// GamePane.this.LEFT = false;
		// GamePane.this.RIGHT = false;
		// playerDirection = 0;
		// playerOne.setDirection(AbstractCharacter.UP);
		// break;
		// case 7:
		// GamePane.this.DOWN = true;
		// GamePane.this.STAND = false;
		// GamePane.this.UP = false;
		// GamePane.this.LEFT = false;
		// GamePane.this.RIGHT = false;
		// playerDirection = 1;
		// playerOne.setDirection(AbstractCharacter.DOWN);
		// break;
		// case 3:
		// GamePane.this.RIGHT = true;
		// GamePane.this.STAND = false;
		// GamePane.this.UP = false;
		// GamePane.this.DOWN = false;
		// GamePane.this.LEFT = false;
		// playerDirection = 2;
		// playerOne.setDirection(AbstractCharacter.LEFT);
		// break;
		// case 5:
		// GamePane.this.LEFT = true;
		// GamePane.this.STAND = false;
		// GamePane.this.UP = false;
		// GamePane.this.DOWN = false;
		// GamePane.this.RIGHT = false;
		// playerDirection = 3;
		// playerOne.setDirection(AbstractCharacter.RIGHT);
		// break;
		// case 4:
		// GamePane.this.STAND = true;
		// GamePane.this.UP = false;
		// GamePane.this.DOWN = false;
		// GamePane.this.LEFT = false;
		// GamePane.this.RIGHT = false;
		// break;
		// default:
		// break;
		// }
		//
		// int ZRZDirection = gamePadController.getZRZStickDir();
		// // System.out.println(ZRZDirection);
		// switch (ZRZDirection) {
		// case 1:
		// case 2:
		// GamePane.this.playerOne.SwitchGun(0);
		// GamePane.this.trapPanel.selectGun();
		// break;
		// case 0:
		// case 3:
		// GamePane.this.playerOne.SwitchGun(1);
		// GamePane.this.trapPanel.selectUzi();
		// break;
		// case 6:
		// case 7:
		// GamePane.this.playerOne.SwitchGun(2);
		// GamePane.this.trapPanel.selectShootGun();
		// break;
		// case 8:
		// case 5:
		// GamePane.this.playerOne.SwitchGun(3);
		// GamePane.this.trapPanel.selectHeavy();
		// break;
		// default:
		// break;
		// }
		//
		// int idButton = gamePadController.getButtonPressed();
		//
		// if (cont > 0 && idButton != 5) {
		// if (!(playerOne.getCurrentGunSelected() instanceof UziGun))
		// playerOne.shoot();
		// cont = 0;
		// shoot = false;
		// shooter = true;
		// }
		//
		// switch (idButton) {
		// /* Triangolo */
		// case 0:
		// GamePane.this.trapPanel.selectSpikeTrap();
		// GamePane.this.trapPanel.repaint();
		// break;
		// /* Cerchio */
		// case 1:
		// GamePane.this.trapPanel.selectFireTrap();
		// GamePane.this.trapPanel.repaint();
		// break;
		// /* Croce */
		// case 2:
		// GamePane.this.trapPanel.selectAcidTrap();
		// GamePane.this.trapPanel.repaint();
		// break;
		// /* Quadrato */
		// case 3:
		// GamePane.this.trapPanel.selectElectricTrap();
		// GamePane.this.trapPanel.repaint();
		// break;
		// /* L2 */
		// case 4:
		// System.out.println("4");
		// break;
		// /* R2 */
		// case 5:
		// if (cont == 0)
		// cont++;
		// if (playerOne.getCurrentGunSelected() instanceof UziGun) {
		// shootForUziGun();
		// }
		//
		// break;
		// /* L1 */
		// case 6:
		// System.out.println("6");
		// break;
		// /* R1 */
		// case 7:
		// GamePane.this.trapPanel.selectPowerTrap();
		// GamePane.this.trapPanel.repaint();
		// break;
		// /* Select */
		// case 8:
		// controllerInfo.setVisible(true);
		// break;
		// /* Start */
		// case 9:
		// GameManagerImpl.getInstance().setPause();
		// showControllerInfo();
		// break;
		// /* L3 */
		// case 10:
		// System.out.println("10");
		// break;
		// /* R3 */
		// case 11:
		// System.out.println("11");
		// break;
		// default:
		// controllerInfo.setVisible(false);
		// break;
		// }
		//
		// try {
		// sleep(20);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// System.out.println("esco dall poll gamepad");
		//
		// };
		// }.start();
	} // end of startPolling()

	public void startClient() {

		new Thread() {
			String responseFromServer;

			@Override
			public void run() {

				while (true) {
					responseFromServer = GamePane.this.client.recieveMessage();

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
			movementBullets.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("n")) {
			movementWawe.put(responseFromServer);
		} else if (responseFromServer.substring(0, 1).equals("c")) {
			this.levelClear = true;
			StageClearPanel clearPanel = new StageClearPanel();
			MainMenuFrame.getInstance().switchTo(clearPanel);
		}
	}

	private void placeTrapOnGraphicMap(String message) {
		String[] splittedMessage = message.split(" ");
		String[] splitted = splittedMessage[1].split("/");
		if (splittedMessage[0].equals("p")) {

			placedTrap.put(
					new Point(Integer.parseInt(splitted[1]), Integer
							.parseInt(splitted[2])), Integer
							.parseInt(splitted[0]));

			this.money = Integer.parseInt(splitted[3]);

			informationPanel.setMoney(money);
		} else if (splittedMessage[0].equals("pr")) {

			Point point = new Point(Integer.parseInt(splitted[1]),
					Integer.parseInt(splitted[0]));
			placedTrap.remove(point);
		}

	}

	private void moveGraphicPlayer(String string) {
		if (string.equals("mUP")) {
			playerX -= movementOffset;
		} else if (string.equals("mDOWN")) {
			playerX += movementOffset;
		} else if (string.equals("mRIGHT")) {
			playerY -= movementOffset;
		} else if (string.equals("mLEFT")) {
			playerY += movementOffset;
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
						AbstractNative abstractNative = natives.get(Integer
								.parseInt(splitted[1]));

						if (movement.substring(1, 2).equals("U")) {

							abstractNative.setX(abstractNative.getX() - 1);
						} else if (movement.substring(1, 2).equals("D")) {
							abstractNative.setX(abstractNative.getX() + 1);
						} else if (movement.substring(1, 2).equals("L")) {
							abstractNative.setY(abstractNative.getY() - 1);
						} else if (movement.substring(1, 2).equals("R")) {
							abstractNative.setY(abstractNative.getY() + 1);
						} else if (splitted[0].equals("nr")) {
							natives.remove(Integer.parseInt(splitted[1]));
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
						moveGraphicPlayer(movement);
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
						movement = movementBullets.take();
						String[] movementSplitted = movement.split(" ");
						if (movementSplitted[0].equals("shoot")) {
							bullets.put(Integer.parseInt(movementSplitted[1]),
									new BulletsClient(
											GamePane.this.playerDirection,
											GamePane.this.logicX,
											GamePane.this.logicY));

						} else if (movementSplitted[0].equals("sr")) {
							System.out.println(movement);

							bullets.remove(Integer
									.parseInt(movementSplitted[1]));
							System.err.println("GamePane - "
									+ movement.substring(2, 3));

						} else if (movementSplitted[0].equals("sUP")) {
							bullets.get(Integer.parseInt(movementSplitted[1]))
									.setX(bullets
											.get(Integer
													.parseInt(movementSplitted[1]))
											.getX() - 1);
						} else if (movementSplitted[0].equals("sDOWN")) {
							bullets.get(Integer.parseInt(movementSplitted[1]))
									.setX(bullets
											.get(Integer
													.parseInt(movementSplitted[1]))
											.getX() + 1);
						} else if (movementSplitted[0].equals("sRIGHT")) {
							bullets.get(Integer.parseInt(movementSplitted[1]))
									.setY(bullets
											.get(Integer
													.parseInt(movementSplitted[1]))
											.getY() - 1);
						} else if (movementSplitted[0].equals("sLEFT")) {
							bullets.get(Integer.parseInt(movementSplitted[1]))
									.setY(bullets
											.get(Integer
													.parseInt(movementSplitted[1]))
											.getY() + 1);
						} else if (movementSplitted[0].equals("srm")) {
							bullets.put(
									Integer.parseInt(movementSplitted[1]),

									new BulletsClient(
											Integer.parseInt(movementSplitted[2]),
											Integer.parseInt(movementSplitted[3]),
											Integer.parseInt(movementSplitted[4])));

						}
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
		}.start();

	}

	private void canMovePlayer() {
		boolean canMove = false;

	}
}
