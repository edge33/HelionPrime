package it.mat.unical.Helion_Prime.Multiplayer;

import java.util.ArrayList;

public class MacchinaServer {
	private static int numberPort = 10001;
	private ArrayList<ServerMultiplayer> openMatch;

	public MacchinaServer() {
		openMatch = new ArrayList<ServerMultiplayer>();
		openMatch.add(new ServerMultiplayer(numberPort, 0));
		numberPort++;
		openMatch.add(new ServerMultiplayer(numberPort, 1));
		numberPort++;
		openMatch.add(new ServerMultiplayer(numberPort, 2));
	}

	public void startServers() {
		for (int i = 0; i < 3; i++) {
			openMatch.get(i).setLevelName("crossroad");

			System.out.println("Macchina " + i + " opertiva");
			openMatch.get(i).start();
		}
	}
}
