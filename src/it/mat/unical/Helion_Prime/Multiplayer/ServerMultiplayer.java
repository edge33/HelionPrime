package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.UziGun;
import it.mat.unical.Helion_Prime.Logic.Character.Player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMultiplayer extends Thread {

	private ServerSocket serverMultiplayer;
	private Socket client;
	private BufferedReader in;
	private DataOutputStream out;
	private BufferedReader inTwo;
	private DataOutputStream outTwo;
	private int connectedClient;
	private String levelName;
	private Player playerOne;
	private Player playertwo;
	private CyclicBarrier barrier;
	private File level;
	private boolean wantRetryPlayerOne;
	private boolean wantRetryPlayerTwo;
	private BlockingQueue<String> fromPlayerOne;
	private BlockingQueue<String> fromPlayerTwo;
	private BlockingQueue<String> broadcastMessage;
	private BlockingQueue<String> forPlayerOne;
	private BlockingQueue<String> forPlayerTwo;
	private GameManagerImpl gameManager;
	private LinkedBlockingQueue<String> placementTrap;
	protected boolean isStageClear;
	private int id_connection = 0;
	private boolean isFinishMultiplayerGame;

	public ServerMultiplayer(int port, int id) {
		try {
			this.id_connection = id;
			serverMultiplayer = new ServerSocket(port);
			System.out.println("port server " + port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isFinishMultiplayerGame = false;
		this.connectedClient = 0;
		wantRetryPlayerOne = false;
		barrier = new CyclicBarrier(2);
		wantRetryPlayerTwo = false;
		fromPlayerOne = new LinkedBlockingQueue<String>();
		fromPlayerTwo = new LinkedBlockingQueue<String>();
		placementTrap = new LinkedBlockingQueue<String>();
		broadcastMessage = new LinkedBlockingQueue<String>();
		forPlayerOne = new LinkedBlockingQueue<String>();
		forPlayerTwo = new LinkedBlockingQueue<String>();
		levelName = null;

	}

	@Override
	public void run() {

		while (connectedClient < 2) {
			try {
				System.out.println("starto il server sono su accept");
				System.out.println("default level " + levelName);
				client = serverMultiplayer.accept();
				System.out.println("sono su dopo accept");
				if (connectedClient < 1) {
					connectedClient++;
					// System.out.println("CREO IL CLIENT 1");
					in = new BufferedReader(new InputStreamReader(
							client.getInputStream()));
					out = new DataOutputStream(client.getOutputStream());

					System.out.println(in.readLine());
					out.writeBytes("Welcome player1" + "\n");
					if (levelName == null)
						levelName = in.readLine();
					else
						out.writeBytes(levelName + "\n");

					System.out.println("livello scelto dal player 1(SERVER) "
							+ levelName);

				} else {

					inTwo = new BufferedReader(new InputStreamReader(
							client.getInputStream()));
					outTwo = new DataOutputStream(client.getOutputStream());

					System.out.println(inTwo.readLine());
					outTwo.writeBytes("Welcome player1" + "\n");
					outTwo.writeBytes(levelName + "\n");
					connectedClient++;

					out.writeBytes("ok" + "\n");
					outTwo.writeBytes("ok" + "\n");

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		initServerMultiplayer();
	}

	public void initServerMultiplayer() {
		// sendToClientOne("GIOCATORE 2 ARRIVATO");

		level = new File("levels/" + levelName + ".txt");

		gameManager = GameManagerImpl.getInstance(this.id_connection);
		gameManager.setServerMultiplayer(this);
		try {
			gameManager.init(level, true, this.id_connection);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		playerOne = GameManagerImpl.getInstance(id_connection).getPlayerOne();
		playertwo = GameManagerImpl.getInstance(id_connection).getPlayerTwo();

		sendBroadcast(((Integer) GameManagerImpl.getInstance(id_connection)
				.getPlayerOne().getMoney()).toString()); // mando l'intero
															// corrispondente ai
															// money;
		sendBroadcast(((Integer) GameManagerImpl.getInstance(id_connection)
				.getPlayerOne().getLife()).toString()); // mando l'intero
														// corrispondente alla
														// vita;

		wantRetryPlayerOne = false;
		wantRetryPlayerTwo = false;

		this.startInFromPlayerOne();
		this.startInFromPlayerTwo();
		this.startPlacementTrap();
		this.startUpdateFromPlayerOne();
		this.startUpdateFromPlayerTwo();
		this.startSendMessageBroadcast();
		this.startSendMessagePlayerOne();
		this.startSendMessagePlayerTwo();
		this.startUpdater();
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	private void startUpdater() {
		new Thread() {

			public void run() {

				this.setName("SERVER MULTYPLAYER - UPDATER ");
				while (!GameManagerImpl.getInstance(id_connection)
						.isGameStopped()
						&& !GameManagerImpl.getInstance(id_connection)
								.gameIsOver()) {
					gameManager.updateMultiplayer();

					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			};
		}.start();

	}

	private void startSendMessagePlayerOne() {
		new Thread() {
			public void run() {

				this.setName("SERVER MULTYPLAYER - SEND_MESSAGE:P1");
				while (true) {

					try {
						String messageForPlayerOne = forPlayerOne.take();

						sendToClientOne(messageForPlayerOne);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}.start();

	}

	private void startSendMessagePlayerTwo() {
		new Thread() {

			public void run() {
				this.setName("SERVER MULTYPLAYER - SEND_MESSAGE:P2");
				while (true) {

					try {
						String messageForPlayerOne = forPlayerTwo.take();
						sendToClientTwo(messageForPlayerOne);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}.start();

	}

	public void outToClientOne(String string) {
		try {
			forPlayerOne.put(string);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void outToClientTwo(String messageFromPlayerTwo) {
		try {
			forPlayerTwo.put(messageFromPlayerTwo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startSendMessageBroadcast() {
		new Thread() {
			public void run() {
				this.setName("SERVER MULTYPLAYER - SEND_MESSAGE: BR");
				while (true) {

					try {
						String messageBroadcast = broadcastMessage.take();
						sendBroadcast(messageBroadcast);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}.start();
	}

	private void startInFromPlayerOne() {
		new Thread() {
			public void run() {
				this.setName("SERVER_MULTIPLAYER - MEX FROM PLAYER ONE");
				while (!isFinishMultiplayerGame) {

					try {
						String css = inFromClientOne();
						if (css != null)
							fromPlayerOne.put(css);
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};

		}.start();

	}

	private void startInFromPlayerTwo() {
		new Thread() {
			public void run() {
				this.setName("SERVER_MULTIPLAYER - MEX FROM PLAYER TWO");
				while (!isFinishMultiplayerGame) {

					try {
						String css = inFromClientTwo();
						if (css != null)
							fromPlayerTwo.put(css);
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};

		}.start();

	}

	public void startUpdateFromPlayerOne() {
		new Thread() {

			public void run() {

				this.setName("SERVER_MULTIPLAYER - UPDATER PLAYER 1 ");

				while (!isFinishMultiplayerGame) {

					String messageFromPlayerOne;

					try {
						messageFromPlayerOne = fromPlayerOne.take();
						System.out.println(messageFromPlayerOne
								+ " From Player One");
						if (messageFromPlayerOne.substring(0, 1).equals("m")
								|| messageFromPlayerOne.substring(0, 1).equals(
										"d")) {
							canMovePlayer(messageFromPlayerOne,
									gameManager.getPlayerOne());
							outToClientTwo(messageFromPlayerOne);

						} else if (messageFromPlayerOne.substring(0, 1).equals(
								"p")) {

							String[] splitted = messageFromPlayerOne.split(" ");

							placementTrap.put(splitted[1] + "/2");

						} else if (messageFromPlayerOne.substring(0, 2).equals(
								"sh")) {
							if (playerOne.getCurrentGunSelected() instanceof UziGun) {
								playerOne.shootForUziGun(1);
							} else {
								int key = canShoot(gameManager.getPlayerOne());
								outToClientOne("sh " + String.valueOf(key)
										+ " 1 " + playerOne.getDirection());
								outToClientTwo("sh " + String.valueOf(key)
										+ " 2 " + playerOne.getDirection());
							}

						} else if (messageFromPlayerOne.substring(0, 2).equals(
								"sw")) {

							playerOne.SwitchGun(Integer
									.parseInt(messageFromPlayerOne.substring(
											messageFromPlayerOne.length() - 1,
											messageFromPlayerOne.length())));

						} else if (messageFromPlayerOne.equals("retry")
								|| messageFromPlayerOne.equals("notRetry")) {

							if (messageFromPlayerOne.equals("retry"))
								wantRetryPlayerOne = true;

							try {
								barrier.await();
							} catch (BrokenBarrierException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if (wantRetryPlayerOne && wantRetryPlayerTwo) {
								sendBroadcast("Ok");
								sendBroadcast(((Integer) GameManagerImpl
										.getInstance(id_connection)
										.getPlayerOne().getMoney()).toString()); // mando
																					// l'intero
																					// corrispondente
																					// ai
																					// money;
								sendBroadcast(((Integer) GameManagerImpl
										.getInstance(id_connection)
										.getPlayerOne().getLife()).toString()); // mando
																				// l'intero
																				// corrispondente
																				// alla
																				// vita;

								try {
									gameManager
											.init(level, true, id_connection);
									playerOne = GameManagerImpl.getInstance(
											id_connection).getPlayerOne();
									playertwo = GameManagerImpl.getInstance(
											id_connection).getPlayerTwo();
									gameManager
											.setServerMultiplayer(ServerMultiplayer.this);
									ServerMultiplayer.this.startUpdater();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								wantRetryPlayerOne = false;
								wantRetryPlayerTwo = false;

							} else {
								if (!wantRetryPlayerOne) {
									outToClientTwo("PlayerOneOut");
								} else
									outToClientOne("PlayerTwoOut");

								closeConnecionPlayerTwo();
								closeConnectionPlayerOne();
								isFinishMultiplayerGame = true;
								ServerMultiplayer.this.serverMultiplayer
										.close();

							}

						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}.start();
	}

	public void startUpdateFromPlayerTwo() {
		new Thread() {

			public void run() {

				this.setName("SERVER_MULTIPLAYER - UPDATER PLAYER 2 ");

				while (!isFinishMultiplayerGame) {

					try {
						String messageFromPlayerTwo = fromPlayerTwo.take();
						System.out.println(messageFromPlayerTwo
								+ " From Player two");
						if (messageFromPlayerTwo.substring(0, 1).equals("m")
								|| messageFromPlayerTwo.substring(0, 1).equals(
										"d")) {
							canMovePlayer(messageFromPlayerTwo,
									gameManager.getPlayerTwo());
							outToClientOne(messageFromPlayerTwo);

						} else if (messageFromPlayerTwo.substring(0, 1).equals(
								"p")) {

							String[] splitted = messageFromPlayerTwo.split(" ");

							placementTrap.put(splitted[1] + "/2");

						} else if (messageFromPlayerTwo.substring(0, 2).equals(
								"sh")) {
							if (playertwo.getCurrentGunSelected() instanceof UziGun) {
								playertwo.shootForUziGun(2);
							} else {
								int key = canShoot(gameManager.getPlayerOne());
								outToClientOne("sh " + String.valueOf(key)
										+ " 2 " + playerOne.getDirection());
								outToClientTwo("sh " + String.valueOf(key)
										+ " 1 " + playerOne.getDirection());
							}

						} else if (messageFromPlayerTwo.substring(0, 2).equals(
								"sw")) {

							playertwo.SwitchGun(Integer
									.parseInt(messageFromPlayerTwo.substring(
											messageFromPlayerTwo.length() - 1,
											messageFromPlayerTwo.length())));

						} else if (messageFromPlayerTwo.equals("retry")) {

							wantRetryPlayerTwo = true;

							try {
								barrier.await();
							} catch (BrokenBarrierException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (messageFromPlayerTwo.equals("notRetry")) {
							wantRetryPlayerTwo = false;
							try {
								barrier.await();
							} catch (BrokenBarrierException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							closeConnecionPlayerTwo();
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}.start();
	}

	private Integer canShoot(Player player) {
		return player.shoot();

	}

	public void startPlacementTrap() {
		new Thread() {

			public void run() {
				this.setName("SERVER_MULTIPLAYER - PLACEMENT_TRAP");
				while (!GameManagerImpl.getInstance(id_connection).gameIsOver()
						&& !GameManagerImpl.getInstance(id_connection)
								.isGameStopped()) {

					try {
						String placement = placementTrap.take();
						// System.err.println(placement);

						if (placement.substring(placement.length() - 1,
								placement.length()).equals("1")
								&& canPlaceTrap(placement,
										gameManager.getPlayerOne())) {
							placement = placement.substring(0,
									placement.length() - 2);
							ServerMultiplayer.this.outBroadcast(("p "
									+ placement + "/" + gameManager
									.getPlayerOne().getMoney()));

						} else if (placement.substring(placement.length() - 1,
								placement.length()).equals("2")
								&& canPlaceTrap(placement,
										gameManager.getPlayerTwo())) {
							placement = placement.substring(0,
									placement.length() - 2);

							ServerMultiplayer.this.outBroadcast(("p "
									+ placement + "/" + gameManager
									.getPlayerTwo().getMoney()));

						}

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

	private boolean canPlaceTrap(String string, Player player) {

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
			if (gameManager.placeTrap(realY, realX, trapSelected, player)) {
				response = true;
			} else
				response = false;
		} else
			response = false;

		return response;

	}

	public void closeConnection() {
		try {
			in.close();
			out.close();
			inTwo.close();
			outTwo.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// private void updateOnline(String message) throws InterruptedException {
	// String response = null;
	//
	// String[] splitted = message.split(" ");
	//
	//
	// } else if (message.substring(0, 1).equals("s")) {
	//
	// sendMessage("sh " + String.valueOf(canShoot()));
	//
	// } else if (splitted[0].equals("switchGun")) {
	// swintchGunForPlayer(splitted[1]);
	// }
	//
	// }

	private void canMovePlayer(String message, Player player) {

		if (message.equals("mUP")) { // UP
			player.move(0);
			// gameManager.movePlayerOne(0);
			player.setDirection(0);
			// playerOne.setDirection(0);

		}

		else if (message.equals("mDOWN")) { // DOWN
			player.move(1);
			player.setDirection(1);
			// gameManager.movePlayerOne(1);
			// playerOne.setDirection(1);
		}

		else if (message.equals("mRIGHT")) {
			player.move(2);
			player.setDirection(2);
			// gameManager.movePlayerOne(2);
			// playerOne.setDirection(2);
		}

		else if (message.equals("mLEFT")) {
			player.move(3);
			player.setDirection(3);
			// gameManager.movePlayerOne(3);
			// playerOne.setDirection(3);
		}

		else if (message.equals("dUP")) { // per settare le direzione del
											// giocatore
											// playerOne.setDirection(0);
			player.setDirection(0);
		} else if (message.equals("dDOWN")) {
			// playerOne.setDirection(1);
			player.setDirection(1);
		} else if (message.equals("dRIGHT")) {
			// playerOne.setDirection(2);
			player.setDirection(2);
		} else if (message.equals("dLEFT")) {
			// playerOne.setDirection(3);
			player.setDirection(3);
		}

		// gameManager.updateMultiplayer();

	}

	public String inFromClientOne() {
		try {
			return in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "finish";
		}

	}

	public String inFromClientTwo() {
		try {
			return inTwo.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "finish";
		}

	}

	public synchronized void sendToClientOne(String sentence) {
		try {
			out.writeBytes(sentence + "\n");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void sendToClientTwo(String sentence) {
		try {
			outTwo.writeBytes(sentence + "\n");
			outTwo.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendBroadcast(String messageBroadcast) {
		sendToClientOne(messageBroadcast);
		sendToClientTwo(messageBroadcast);
	}

	public void outBroadcast(String sentence) {
		try {
			broadcastMessage.put(sentence);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeConnectionPlayerOne() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void closeConnecionPlayerTwo() {
		try {
			inTwo.close();
			outTwo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
