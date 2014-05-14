package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.GamePad.GamePadController;
import it.mat.unical.Helion_Prime.Logic.AbstractNativeLite;
import it.mat.unical.Helion_Prime.Logic.Corner;
import it.mat.unical.Helion_Prime.Logic.FileNotCorrectlyFormattedException;
import it.mat.unical.Helion_Prime.Logic.MaintenanceRoom;
import it.mat.unical.Helion_Prime.Logic.NativeSpawner;
import it.mat.unical.Helion_Prime.Logic.PlayerSpawner;
import it.mat.unical.Helion_Prime.Logic.StaticObject;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.Logic.Wall;
import it.mat.unical.Helion_Prime.Logic.Wave;
import it.mat.unical.Helion_Prime.Logic.WaveImpl;
import it.mat.unical.Helion_Prime.Logic.WorldImpl;
import it.mat.unical.Helion_Prime.Multiplayer.ClientManagerMultiplayer;
import it.mat.unical.Helion_Prime.Multiplayer.ThreadPoolMovementPlayerTwo;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.ClientManager;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePane extends JPanel {

	public static final int TILE_SIZE = 50;

	private JLabel controllerInfo;
	private ImageProvider imageProvider;
	private int playerX;
	private int playerY;
	private int playerTwoX;
	private int playerTwoY;
	private int imagePlayer;
	private int imagePlayer2;

	private ThreadPoolMovementPlayerTwo movementPlayerTwo;
	private boolean isConnectedPad = false;
	// public int clientManager.getPlayerDirection();
	private UserProfile profile;

	public ConcurrentHashMap<Integer, BulletsClient> bullets;

	private int cont = 0;
	private int curentLife;
	private int sizeBulletForPrint = 0;
	private int movementOffset = TILE_SIZE / 10;
	private int drawingHorizontalOffset;

	private double scaleFactor;
	private boolean UP = false;
	private boolean DOWN = false;
	private boolean LEFT = false;
	private boolean RIGHT = false;
	private boolean STAND = false;
	private boolean shoot = false;
	private boolean shooter = true;
	private boolean threadAlive = false;
	private boolean gameOver = false;
	private boolean stageClear = false;
	private EnemyMoverGraphic enemyMover;
	private File currentFileLevel;
	private WorldImpl world;
	private Client client;
	private ClientManager clientManager;
	private Wave wave;

	public WestGamePanel westPanel;
	public TrapPanel trapPanel;
	public InformationPanel informationPanel;

	private GamePadController gamePadController;
	public ConcurrentHashMap<Integer, AbstractNativeLite> natives;
	public ConcurrentHashMap<Point, Integer> placedTrap;
	private HashMap<AbstractNativeLite, ArrayList<Integer>> movementGraphicWave;

	private int currentGunSelected = 0;
	private boolean levelClear = false;
	// protected int idButton = 0;
	private static final int DELAY = 40;

	public GamePane(File level, Client client, TrapPanel trapPanel,
			WestGamePanel westPanel, InformationPanel informationPanel,
			GamePadController gamePadController, UserProfile profile)

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
		this.currentFileLevel = level;
		this.setLayout(null);
		this.client = client;
		this.profile = profile;
		this.controllerInfo = new JLabel(new ImageIcon(currentLabelImage));
		this.controllerInfo.setForeground(Color.MAGENTA);
		this.controllerInfo.setVisible(false);
		this.controllerInfo.setFont(controllerInfo.getFont().deriveFont(50.0f));
		this.controllerInfo.setBounds(200, 350, 636, 248);
		this.gamePadController = gamePadController;
		this.westPanel = westPanel;
		this.trapPanel = trapPanel;
		this.informationPanel = informationPanel;
		this.placedTrap = new ConcurrentHashMap<Point, Integer>();
		this.bullets = new ConcurrentHashMap<Integer, BulletsClient>();
		this.movementGraphicWave = new HashMap<AbstractNativeLite, ArrayList<Integer>>();
		// this.threadPoolClient = new ThreadPoolBulletClient(bullets);
		// this.manager.init(level);
		// this.threadPoolClient.start();
		this.world = new WorldImpl(level);

		this.imageProvider = new ImageProvider();

		if (!client.isMultiplayerGame()) {
			this.clientManager = ClientManager.getInstance();
			clientManager.createClientManager(client, this, profile);
		} else {

			this.clientManager = ClientManagerMultiplayer.getInstance();
			((ClientManagerMultiplayer) this.clientManager)
					.createClientManagerMultiplayer(client, this);
			this.movementPlayerTwo = new ThreadPoolMovementPlayerTwo(
					ClientManagerMultiplayer.getInstance()
							.getMovementOffsetPlayer2(), this);
			movementPlayerTwo.start();
			imageProvider.initSecondPlayer();
		}

		clientManager.init();

		this.westPanel.setClientManager(this.clientManager);

		informationPanel.setRoomLife(world.getRoomLife());

		playerX = world.getPlayerSpawner().getX() * TILE_SIZE;
		playerY = world.getPlayerSpawner().getY() * TILE_SIZE;

		playerTwoX = world.getPlayerSpawner().getX() * TILE_SIZE;
		playerTwoY = world.getPlayerSpawner().getY() * TILE_SIZE;

		try {
			this.wave = new WaveImpl(this.world, level, false);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.natives = ((WaveImpl) wave).getNativesLite();

		for (AbstractNativeLite currentNative : natives.values()) {
			currentNative.setX(this.world.getNativeSpawner().getX());
			currentNative.setY(this.world.getNativeSpawner().getY());
			currentNative.setGraphicX(this.world.getNativeSpawner().getX()
					* TILE_SIZE);
			currentNative.setGraphicY(this.world.getNativeSpawner().getY()
					* TILE_SIZE);

			movementGraphicWave.put(currentNative, new ArrayList<Integer>());
		}

		enemyMover = new EnemyMoverGraphic(this);

		enemyMover.start();

		Repainter repainter = new Repainter(this);
		repainter.start();
		this.setFocusable(true);

		this.add(controllerInfo);

		if (/* gamePadController.isPadConnected() == true */isConnectedPad) {
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
					GamePane.this.clientManager
							.pushToQueueForServer("place.trap " + numberOftrap
									+ "/" + realX + "/" + realY);

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
						GamePane.this.clientManager
								.pushToQueueForServer("switchGun 0");
						break;
					case KeyEvent.VK_2:
						GamePane.this.trapPanel.selectUzi();
						GamePane.this.clientManager
								.pushToQueueForServer("switchGun 1");
						currentGunSelected = 1;
						break;
					case KeyEvent.VK_3:
						currentGunSelected = 2;
						GamePane.this.trapPanel.selectShootGun();
						GamePane.this.clientManager
								.pushToQueueForServer("switchGun 2");
						break;
					case KeyEvent.VK_4:
						currentGunSelected = 3;
						GamePane.this.trapPanel.selectHeavy();
						GamePane.this.clientManager
								.pushToQueueForServer("switchGun 3");
						break;
					case KeyEvent.VK_W:
						GamePane.this.UP = false;

						// clientManager.getPlayerDirection() = -1;
						break;
					case KeyEvent.VK_S:
						GamePane.this.DOWN = false;
						// clientManager.getPlayerDirection() = -2;
						break;
					case KeyEvent.VK_A:
						GamePane.this.RIGHT = false;
						// clientManager.getPlayerDirection() = -3;
						break;
					case KeyEvent.VK_D:
						GamePane.this.LEFT = false;
						// clientManager.getPlayerDirection() = -3;
						break;

					case KeyEvent.VK_SPACE:
						shoot = false;
						shooter = true;
						// if (!(manager.getPlayer().getCurrentGunSelected()
						// instanceof UziGun)) {
						// playerOne.shoot();
						// }

						GamePane.this.clientManager.pushToQueueForServer("sh");
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
						if (GamePane.this.clientManager.getPlayerDirection() != 0) {
							GamePane.this.clientManager.setPlayerDirection(0);
							GamePane.this.clientManager
									.pushToQueueForServer("dUP");
						}
						// playerOne.setDirection(AbstractCharacter.UP);

					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_S) {
						GamePane.this.DOWN = true;
						if (GamePane.this.clientManager.getPlayerDirection() != 1) {
							GamePane.this.clientManager.setPlayerDirection(1);
							GamePane.this.clientManager
									.pushToQueueForServer("dDOWN");
						}

						// playerOne.setDirection(AbstractCharacter.DOWN);

					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_A) {
						GamePane.this.RIGHT = true;
						if (GamePane.this.clientManager.getPlayerDirection() != 2) {
							GamePane.this.clientManager.setPlayerDirection(2);
							GamePane.this.clientManager
									.pushToQueueForServer("dRIGHT");
						}

						// playerOne.setDirection(AbstractCharacter.LEFT);

					} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_D) {
						GamePane.this.LEFT = true;
						if (GamePane.this.clientManager.getPlayerDirection() != 3) {
							GamePane.this.clientManager.setPlayerDirection(3);
							GamePane.this.clientManager
									.pushToQueueForServer("dLEFT");
						}

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
				while (!ClientManager.isFinishGame()) {

					if (UP) {
						imagePlayer = 1;

						if (!(world.getElementAt(
								GamePane.this.clientManager.getLogicX() - 1,
								GamePane.this.clientManager.getLogicY()) instanceof Wall)
								|| playerX > GamePane.this.clientManager
										.getLogicX() * TILE_SIZE) {

							playerX -= getMovementOffset();
						}

						if (playerX <= ((GamePane.this.clientManager
								.getLogicX() - 1) * TILE_SIZE) + TILE_SIZE / 3) {
							GamePane.this.clientManager
									.pushToQueueForServer("mUP");

							GamePane.this.clientManager
									.setLogicX(GamePane.this.clientManager
											.getLogicX() - 1);
						}

					}

					else if (DOWN) { // DOWN

						imagePlayer = 2;
						if (!(world.getElementAt(
								GamePane.this.clientManager.getLogicX() + 1,
								GamePane.this.clientManager.getLogicY()) instanceof Wall)
								|| playerX < GamePane.this.clientManager
										.getLogicX() * TILE_SIZE) {

							playerX += getMovementOffset();
						}

						if (playerX > ((GamePane.this.clientManager.getLogicX() + 1) * TILE_SIZE)
								- TILE_SIZE / 2) {

							GamePane.this.clientManager
									.pushToQueueForServer("mDOWN");
							GamePane.this.clientManager
									.setLogicX(GamePane.this.clientManager
											.getLogicX() + 1);
						}
					}

					else if (RIGHT) {

						imagePlayer = 3;
						if (!(world.getElementAt(
								GamePane.this.clientManager.getLogicX(),
								GamePane.this.clientManager.getLogicY() - 1) instanceof Wall)
								|| playerY > GamePane.this.clientManager
										.getLogicY() * TILE_SIZE) {

							playerY -= getMovementOffset();

						}
						if (playerY < ((GamePane.this.clientManager.getLogicY() - 1) * TILE_SIZE)
								+ TILE_SIZE / 2) {

							GamePane.this.clientManager
									.pushToQueueForServer("mRIGHT");
							GamePane.this.clientManager
									.setLogicY(GamePane.this.clientManager
											.getLogicY() - 1);
						}
					}

					else if (LEFT) {

						imagePlayer = 4;
						if (!(world.getElementAt(
								GamePane.this.clientManager.getLogicX(),
								GamePane.this.clientManager.getLogicY() + 1) instanceof Wall)
								|| playerY < GamePane.this.clientManager
										.getLogicY() * TILE_SIZE) {

							playerY += getMovementOffset();

						}
						if (playerY > ((GamePane.this.clientManager.getLogicY() + 1) * TILE_SIZE)
								- TILE_SIZE / 2) {
							GamePane.this.clientManager
									.pushToQueueForServer("mLEFT");
							GamePane.this.clientManager
									.setLogicY(GamePane.this.clientManager
											.getLogicY() + 1);
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
				g.drawImage(imageProvider.getPowerTrap(),
				/* drawingHorizontalOffset */+(point.x * TILE_SIZE), point.y
						* TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				break;
			case 6:
				g.drawImage(imageProvider.getDecoyTrap(),
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
			g.fillOval(bulletClient.getGraphicY() + TILE_SIZE / 2,
					bulletClient.getGraphicX() + TILE_SIZE / 2, 10, 10);

		}
		//

		// // in base alla direzione del player stampa
		// // l'immagine corrispondente
		if (clientManager.isPlayerOne) {
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
		} else {
			switch (imagePlayer2) {
			case 0:
				g.drawImage(imageProvider.getPlayer2Standing(), playerY
				/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
						this);
				break;

			case 1:
				g.drawImage(imageProvider.getPlayer2UpRunning(), playerY
				/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
						this);
				break;
			case 2:
				g.drawImage(imageProvider.getPlayer2DownRunning(), playerY
				/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
						this);
				break;
			case 3:
				g.drawImage(imageProvider.getPlayer2RightRunning(), playerY
				/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
						this);
				break;
			case 4:
				g.drawImage(imageProvider.getPlayer2LeftRunning(), playerY
				/* + drawingHorizontalOffset */, playerX, TILE_SIZE, TILE_SIZE,
						this);
				break;
			default:
				break;
			}

		}
		if (client.isMultiplayerGame()) {

			int tempX = ((ClientManagerMultiplayer) clientManager)
					.getLogicXPlayerTwo();

			int tempY = ((ClientManagerMultiplayer) clientManager)
					.getLogicYPlayerTwo();
			// System.out.println("--------------------------------------------------------Client ;"
			// + clientManager.isPlayerOne);
			if (clientManager.isPlayerOne) {

				switch (imagePlayer2) {
				case 0:
					g.drawImage(imageProvider.getPlayer2Standing(), playerTwoY
					/* + drawingHorizontalOffset */, playerTwoX, TILE_SIZE,
							TILE_SIZE, this);
					break;

				case 1:
					g.drawImage(imageProvider.getPlayer2UpRunning(), playerTwoY
					/* + drawingHorizontalOffset */, playerTwoX, TILE_SIZE,
							TILE_SIZE, this);
					break;
				case 2:
					g.drawImage(imageProvider.getPlayer2DownRunning(),
							playerTwoY
							/* + drawingHorizontalOffset */, playerTwoX,
							TILE_SIZE, TILE_SIZE, this);
					break;
				case 3:
					g.drawImage(imageProvider.getPlayer2RightRunning(),
							playerTwoY
							/* + drawingHorizontalOffset */, playerTwoX,
							TILE_SIZE, TILE_SIZE, this);
					break;
				case 4:
					g.drawImage(imageProvider.getPlayer2LeftRunning(),
							playerTwoY
							/* + drawingHorizontalOffset */, playerTwoX,
							TILE_SIZE, TILE_SIZE, this);
					break;
				default:
					break;

				}
			} else {
				switch (imagePlayer) {
				case 0:
					g.drawImage(imageProvider.getPlayerStanding(), playerTwoY
					/* + drawingHorizontalOffset */, playerTwoX, TILE_SIZE,
							TILE_SIZE, this);
					break;

				case 1:
					g.drawImage(imageProvider.getPlayerUpRunning(), playerTwoY
					/* + drawingHorizontalOffset */, playerTwoX, TILE_SIZE,
							TILE_SIZE, this);
					break;
				case 2:
					g.drawImage(imageProvider.getPlayerDownRunning(),
							playerTwoY
							/* + drawingHorizontalOffset */, playerTwoX,
							TILE_SIZE, TILE_SIZE, this);
					break;
				case 3:
					g.drawImage(imageProvider.getPlayerRightRunning(),
							playerTwoY
							/* + drawingHorizontalOffset */, playerTwoX,
							TILE_SIZE, TILE_SIZE, this);
					break;
				case 4:
					g.drawImage(imageProvider.getPlayerLeftRunning(),
							playerTwoY
							/* + drawingHorizontalOffset */, playerTwoX,
							TILE_SIZE, TILE_SIZE, this);
					break;
				default:
					break;
				}

			}
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
		for (AbstractNativeLite currentNative : natives.values()) {
			
	

			 g.setColor(Color.red);
			 g.fillRect(/* drawingHorizontalOffset */+currentNative.getY()
			 * TILE_SIZE, currentNative.getX() * TILE_SIZE, TILE_SIZE,
			 TILE_SIZE);

			// g.drawImage(imageProvider.getCorrectNative(currentNative),
			// /* drawingHorizontalOffset */+currentNative.getGraphicY() + 10,
			// currentNative.getGraphicX() + 10, TILE_SIZE, TILE_SIZE,
			// this);

			// g.drawImage(imageProvider.getCorrectNative(currentNative),
			// /* drawingHorizontalOffset */+currentNative.getY() * TILE_SIZE,
			// currentNative.getX() * TILE_SIZE, TILE_SIZE, TILE_SIZE,
			// this);

			g.drawImage(imageProvider.getCorrectNative(currentNative),
			/* drawingHorizontalOffset */currentNative.getGraphicY(),
					currentNative.getGraphicX(), TILE_SIZE, TILE_SIZE, this);

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
		// clientManager.getPlayerDirection() = 0;
		// playerOne.setDirection(AbstractCharacter.UP);
		// break;
		// case 7:
		// GamePane.this.DOWN = true;
		// GamePane.this.STAND = false;
		// GamePane.this.UP = false;
		// GamePane.this.LEFT = false;
		// GamePane.this.RIGHT = false;
		// clientManager.getPlayerDirection() = 1;
		// playerOne.setDirection(AbstractCharacter.DOWN);
		// break;
		// case 3:
		// GamePane.this.RIGHT = true;
		// GamePane.this.STAND = false;
		// GamePane.this.UP = false;
		// GamePane.this.DOWN = false;
		// GamePane.this.LEFT = false;
		// clientManager.getPlayerDirection() = 2;
		// playerOne.setDirection(AbstractCharacter.LEFT);
		// break;
		// case 5:
		// GamePane.this.LEFT = true;
		// GamePane.this.STAND = false;
		// GamePane.this.UP = false;
		// GamePane.this.DOWN = false;
		// GamePane.this.RIGHT = false;
		// clientManager.getPlayerDirection() = 3;
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

	private void canMovePlayer() {
		boolean canMove = false;

	}

	public ConcurrentHashMap<Integer, AbstractNativeLite> getNatives() {

		return this.natives;
	}

	public int getMovementOffset() {
		return movementOffset;
	}

	public void setMovementOffset(int movementOffset) {
		this.movementOffset = movementOffset;
	}

	public WorldImpl getWorld() {
		return world;
	}

	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return gameOver;
	}

	public boolean isStageClear() {
		// TODO Auto-generated method stub
		return stageClear;
	}

	public ClientManager getClientManager() {
		return this.clientManager;
	}

	public File getCurrentFileLevel() {
		return this.currentFileLevel;
	}

	public int getPlayerTwoX() {
		return playerTwoX;
	}

	public void setPlayerTwoX(int playerTwoX) {
		this.playerTwoX = playerTwoX;
	}

	public int getPlayerTwoY() {
		return playerTwoY;
	}

	public void setPlayerTwoY(int playerTwoY) {
		this.playerTwoY = playerTwoY;
	}

	public HashMap<AbstractNativeLite, ArrayList<Integer>> getMovementGraphicWave() {
		return movementGraphicWave;
	}

	public void setMovementGraphicWave(
			HashMap<AbstractNativeLite, ArrayList<Integer>> movementGraphicWave) {
		this.movementGraphicWave = movementGraphicWave;
	}
}
