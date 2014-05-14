package it.mat.unical.Helion_Prime.Online;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.Character.Player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Server extends Thread {
	private static final int TILE_SIZE = 50;
	private int movementOffset = TILE_SIZE / 10;
	protected ServerSocket server;
	private Socket client;
	private BufferedReader in;
	private DataOutputStream out;
	private ArrayBlockingQueue<String> movementPlayer;
	private ArrayBlockingQueue<String> placemenTrap;
	private ArrayBlockingQueue<String> messageToClient;
	private File f;
	public static boolean isServerStarted = false;
	public static boolean isGameOver = false;
	public static boolean isStageClear = false;
	GameManagerImpl gameManager;
	private Player playerOne;
	private boolean finishGame = false;
	private String level;
	private boolean isRetry = false;

	public Server(int port) throws IOException {

		this.server = new ServerSocket(port);

		this.setName("SERVER");
		movementPlayer = new ArrayBlockingQueue<String>(20);
		placemenTrap = new ArrayBlockingQueue<String>(20);
		messageToClient = new ArrayBlockingQueue<String>(200);
		this.isServerStarted = true;
		setLevel(null);

	}

	@Override
	public void run() {

		this.isGameOver = false;
		this.isStageClear = false;

		try {
			this.client = this.server.accept();
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gameManager = GameManagerImpl.getInstance();
		System.out.println("ATTENDO NOME LIVELLO");

		// setLevel(null);
		startSendMessageToClient();
		if (getLevel() == null)
			setLevel(recieveMessage());
		System.out.println("NOME DEL LIVELLO ARRIVATA INSTANZIO " + getLevel());
		f = new File("levels/" + getLevel());

		initServer(f);

		this.startMovementPlayer();
		this.startPlacementTrap();
		this.startUpdater();

		while (!isGameOver && !isStageClear) {

			String message = this.recieveMessage();

			if (message.equals("finish")) {
				try {
					this.isGameOver = true;
					this.isStageClear = true;
					this.finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			try {
				updateOnline(message);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void finish() throws InterruptedException {
		messageToClient.put("finish");
		placemenTrap.put("finish");
		movementPlayer.put("finish");
		try {
			this.server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initServer(File f) {

		try {

			gameManager.init(f, false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerOne = gameManager.getPlayerOne();

		playerOne.setGraphicX(playerOne.getX() * TILE_SIZE);
		playerOne.setGraphicY(playerOne.getY() * TILE_SIZE);
		sendMessage("ready");
		System.out.println("Game manager instanziato");

		sendMessage(((Integer) GameManagerImpl.getInstance().getPlayerOne()
				.getMoney()).toString()); // mando l'intero corrispondente ai
											// money;
		sendMessage(((Integer) GameManagerImpl.getInstance().getPlayerOne()
				.getLife()).toString()); // mando l'intero corrispondente alla
											// vita;
	}

	private void startUpdater() {

		new Thread() {

			public void run() {
				this.setName("UPDATER");
				while (!isGameOver && !isStageClear) {

					GameManagerImpl.getInstance().update();

					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private void updateOnline(String message) throws InterruptedException {
		String response = null;

		String[] splitted = message.split(" ");

		if (message.substring(0, 1).equals("m")
				|| message.substring(0, 1).equals("d")) {
			movementPlayer.put(message);

		} else if (message.substring(0, 1).equals("p")) {
			placemenTrap.put(splitted[1]);
		} else if (message.equals("sh")) {
			if (canShoot())
				sendMessage("sh " + String.valueOf(playerOne.shoot() + " ")
						+ playerOne.getDirection());

		} else if (splitted[0].equals("switchGun")) {
			swintchGunForPlayer(splitted[1]);
		} else if (splitted[0].equals("close")) {
			closeConnection();
		} else if (splitted[0].equals("retry")) {
			initServer(f);
		}

	}

	private void closeConnection() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void swintchGunForPlayer(String string) {
		gameManager.getPlayerOne().SwitchGun(Integer.parseInt(string));

	}

	private boolean canShoot() {
		int bulletsCurrent = playerOne.getBulletsArmy().get(
				playerOne.getCurrentGunSelected());

		System.out.println("ARMY" + playerOne.getCurrentGunSelected() + " "
				+ bulletsCurrent);

		if (bulletsCurrent != 0) {
			bulletsCurrent--;
			playerOne.getBulletsArmy().put(playerOne.getCurrentGunSelected(),
					bulletsCurrent);
			return true;
		} else
			return false;

	}

	protected void startSendMessageToClient() {
		new Thread() {
			public void run() {
				this.setName("Message_to_Client");
				while (!isGameOver && !isStageClear) {
					try {
						String message = messageToClient.take();
						Server.this.sendMessageOnline(message);
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				System.out.println("sendClear");

			}

		}.start();
	}

	private boolean canPlaceTrap(String string) {

		boolean response = false;
		int realX = 0, realY = 0, trapSelected = 0;
		System.out.println("String : " + string);
		String[] splitted = string.split("/");
		System.out.println("size of splitted" + splitted.length);

		trapSelected = Integer.parseInt(splitted[0]);
		realX = Integer.parseInt(splitted[1]);
		realY = Integer.parseInt(splitted[2]);

		if (realX < gameManager.getWorld().getLenght()
				&& realY < gameManager.getWorld().getHeight()) {
			if (gameManager.placeTrap(realY, realX, trapSelected,
					gameManager.getPlayerOne())) {
				response = true;
			} else
				response = false;
		} else
			response = false;

		return response;

	}

	private void startPlacementTrap() {
		new Thread() {

			public void run() {
				this.setName("Placement_trap");
				while (!isGameOver && !isStageClear) {

					try {
						String placement = placemenTrap.take();

						if (!placement.equals("finish"))
							if (canPlaceTrap(placement))
								Server.this
										.sendMessage("p "
												+ placement
												+ "/"
												+ gameManager.getPlayerOne()
														.getMoney());
							else
								// Server.this.sendMessage("notPlaceTrap");

								sleep(100);
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
				this.setName("Movement_Player");
				while (!isGameOver && !isStageClear) {

					try {

						String movement = movementPlayer.take();

						canMovePlayer(movement);
						sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}.start();
	}

	private void canMovePlayer(String message) {

		if (message.equals("mUP")) { // UP

			gameManager.movePlayerOne(0);

			playerOne.setDirection(0);

		}

		else if (message.equals("mDOWN")) { // DOWN

			gameManager.movePlayerOne(1);
			playerOne.setDirection(1);
		}

		else if (message.equals("mRIGHT")) {

			gameManager.movePlayerOne(2);
			playerOne.setDirection(2);
		}

		else if (message.equals("mLEFT")) {

			gameManager.movePlayerOne(3);
			playerOne.setDirection(3);
		}

		else if (message.equals("dUP")) { // per settare le direzione del
											// giocatore
			playerOne.setDirection(0);
		} else if (message.equals("dDOWN")) {
			playerOne.setDirection(1);
		} else if (message.equals("dRIGHT")) {
			playerOne.setDirection(2);
		} else if (message.equals("dLEFT")) {
			playerOne.setDirection(3);
		}

	}

	public void sendMessage(String message) {
		try {
			this.messageToClient.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessageOnline(String message) {
		try {
			out.writeBytes(message + '\n');
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String recieveMessage() {
		String message = null;
		try {
			message = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	public void fulsh() {
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
