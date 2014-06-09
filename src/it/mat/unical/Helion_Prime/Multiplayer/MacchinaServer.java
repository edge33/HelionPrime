package it.mat.unical.Helion_Prime.Multiplayer;

import java.util.ArrayList;

public class MacchinaServer extends Thread {
	private static int numberPort = 10001;
	private ArrayList<ServerMultiplayer> openMatch;

	public MacchinaServer() {
		openMatch = new ArrayList<ServerMultiplayer>();

	}

	public void startServers() {
		for (int i = 0; i < 10; i++) {
			openMatch.add(new ServerMultiplayer(numberPort, i));
			numberPort++;
			openMatch.get(i).setRandomLevelName();
			System.out.println("Macchina " + i + " opertiva");
			openMatch.get(i).start();
		}
	}

	@Override
	public void run() {
		System.err.println("AGGIORNO");
		while (true) {
			for (int i = 0; i < 10; i++) {

				if (openMatch.get(i).isFinishMultiplayerGame()) {

					int lastPort = openMatch.get(i).getPort();
					int lastId = openMatch.get(i).getId_connection();

					try {
						sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					openMatch.remove(i);

					ServerMultiplayer multiplayer = new ServerMultiplayer(
							lastPort, lastId);
					openMatch.add(multiplayer);
					multiplayer.setRandomLevelName();
					multiplayer.start();
				}
			}

			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
