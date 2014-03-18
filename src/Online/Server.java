package Online;

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
	private ServerSocket server;
	private Socket client;
	private BufferedReader in;
	private DataOutputStream out;
	private ArrayBlockingQueue<String> movementPlayer;
	private ArrayBlockingQueue<String> placemenTrap;
	private ArrayBlockingQueue<String> messageToClient;

	GameManagerImpl gameManager;
	private Player playerOne;

	public Server(int port) throws IOException {

		this.server = new ServerSocket(port);
		movementPlayer = new ArrayBlockingQueue<String>(20);
		placemenTrap = new ArrayBlockingQueue<String>(20);
		messageToClient = new ArrayBlockingQueue<String>(200);

	}

	@Override
	public void run() {

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

		String level = null;
		startSendMessageToClient();
		level = recieveMessage();
		System.out.println("NOME DEL LIVELLO ARRIVATA INSTANZIO " + level);
		File f = new File("levels/" + level);

		try {

			gameManager.init(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerOne = gameManager.getPlayer();

		playerOne.setGraphicX(playerOne.getX() * TILE_SIZE);
		playerOne.setGraphicY(playerOne.getY() * TILE_SIZE);
		sendMessage("ready");
		System.out.println("Game manager instanziato");

		sendMessage(((Integer) GameManagerImpl.getInstance().getPlayer()
				.getMoney()).toString()); // mando l'intero corrispondente ai
											// money;
		sendMessage(((Integer) GameManagerImpl.getInstance().getPlayer()
				.getLife()).toString()); // mando l'intero corrispondente alla
											// vita;

		this.startMovementPlayer();
		this.startPlacementTrap();

		while (!GameManagerImpl.getInstance().gameIsOver()
				&& !GameManagerImpl.getInstance().isGameStopped()) {

			String message = this.recieveMessage();

			// String response;
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

	private void updateOnline(String message) throws InterruptedException {
		String response = null;

		String[] splitted = message.split(" ");

		if (message.substring(0, 1).equals("m")
				|| message.substring(0, 1).equals("d")) {
			movementPlayer.put(message);

		} else if (message.substring(0, 1).equals("p")) {
			placemenTrap.put(splitted[1]);
		} else if (message.substring(0, 1).equals("s")) {

			sendMessage("sh " + String.valueOf(canShoot()));

		} else if (splitted[0].equals("switchGun")) {
			swintchGunForPlayer(splitted[1]);
		}

	}

	private void swintchGunForPlayer(String string) {
		gameManager.getPlayer().SwitchGun(Integer.parseInt(string));

	}

	private Integer canShoot() {
		return gameManager.getPlayer().shoot();

	}

	private void startSendMessageToClient() {
		new Thread() {
			public void run() {
				while (!GameManagerImpl.getInstance().gameIsOver()
						&& !GameManagerImpl.getInstance().isGameStopped()) {
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
			if (gameManager.placeTrap(realY, realX, trapSelected)) {
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

				while (!GameManagerImpl.getInstance().gameIsOver()
						&& !GameManagerImpl.getInstance().isGameStopped()) {

					try {
						String placement = placemenTrap.take();
						// System.err.println(placement);
						if (canPlaceTrap(placement))
							Server.this.sendMessage("p " + placement + "/"
									+ gameManager.getPlayer().getMoney());
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

				while (!GameManagerImpl.getInstance().gameIsOver()
						&& !GameManagerImpl.getInstance().isGameStopped()) {

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

			gameManager.movePlayer(0);

			playerOne.setDirection(0);

		}

		else if (message.equals("mDOWN")) { // DOWN

			gameManager.movePlayer(1);
			playerOne.setDirection(1);
		}

		else if (message.equals("mRIGHT")) {

			gameManager.movePlayer(2);
			playerOne.setDirection(2);
		}

		else if (message.equals("mLEFT")) {

			gameManager.movePlayer(3);
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

		gameManager.update();

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

}
