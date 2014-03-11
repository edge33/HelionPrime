package it.mat.unical.Helion_Prime.CLI;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.MaintenanceRoom;
import it.mat.unical.Helion_Prime.Logic.NativeSpawner;
import it.mat.unical.Helion_Prime.Logic.PlayerSpawner;
import it.mat.unical.Helion_Prime.Logic.Wall;
import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractTrap;

import java.util.concurrent.ConcurrentHashMap;

public class Drawer {

	private World world;
	private GameManagerImpl managerDrawer;

	public Drawer(World world, GameManagerImpl managerDrawer) {

		this.world = world;
		this.managerDrawer = managerDrawer;
	}

	public void draw() {

		int playerX = managerDrawer.getPlayer().getX();
		int playerY = managerDrawer.getPlayer().getY();

		ConcurrentHashMap<Integer, AbstractNative> natives = managerDrawer
				.getWaveImpl().getNatives();

		boolean enemyHere = false;

		for (int i = 0; i < world.getHeight(); i++) {
			for (int j = 0; j < world.getLenght(); j++) {

				for (AbstractNative currentNative : natives.values()) {
					if (currentNative.getX() == i && currentNative.getY() == j
							&& enemyHere == false)
						enemyHere = true;
				}

				if (enemyHere) {

					enemyHere = false;

					System.out.print("[N]");

					// for (int j2 = 0; j2 < natives.size(); j2++) { //for che
					// serve ad aggiornare le posizioni dei nemici
					// if(natives.get(j2).getX()==i&&natives.get(j2).getY()==j){
					// System.out.print("[N]");
					// break;
					// }
					// }

				} else {

					if (playerX == i && playerY == j) {
						System.out.print("[F]");
					} else {
						if (world.getElementAt(i, j) instanceof Wall) {
							System.out.print("[#]");
						}
						if (world.getElementAt(i, j) == null) {
							System.out.print("[ ]");
						}
						if (world.getElementAt(i, j) instanceof NativeSpawner) {
							System.out.print("[S]");
						}
						if (world.getElementAt(i, j) instanceof PlayerSpawner) {
							System.out.print("[P]");
						}
						if (world.getElementAt(i, j) instanceof MaintenanceRoom) {
							System.out.print("[M]");
						}

						if (world.getElementAt(i, j) instanceof AbstractTrap) {
							System.out.print("[T]");

						}

					}
				}

			}

			System.out.println();
		}
		System.out.println("Player : " + managerDrawer.getPlayer().getLife());
	}
}
