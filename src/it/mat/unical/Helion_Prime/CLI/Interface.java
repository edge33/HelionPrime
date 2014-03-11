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

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Interface extends JFrame {

	private GameManagerImpl manager;
	private World world;
	private Drawer drawer;

	public static void main(String[] args) {

		Interface iface = new Interface();

	}

	public Interface() {
		super("Helion Prime");
		this.setLocation(1022, 350);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		manager = manager;
		world = manager.getWorld();

		this.drawer = new Drawer(world, manager);

		JPanel panel = new Panel(manager, this);

		this.getContentPane().add(panel);
		this.pack();

		drawer.draw();
	}

	void draw() {

		int playerX = manager.getPlayer().getX();
		int playerY = manager.getPlayer().getY();
		boolean MiServePerUscireDalFor = false;
		ConcurrentHashMap<Integer, AbstractNative> natives = manager
				.getWaveImpl().getNatives();

		for (int i = 0; i < world.getHeight(); i++) {
			for (int j = 0; j < world.getLenght(); j++) {

				for (int j2 = 0; j2 < natives.size(); j2++) { // for che serve
																// ad aggiornare
																// le posizioni
																// dei nemici
					if (natives.get(j2).getX() == i
							&& natives.get(j2).getY() == j) {
						System.out.print("[N]");
						MiServePerUscireDalFor = true;
					}
				}

				if (playerX == i && playerY == j) {
					System.out.print("[F]");
				} else if (!MiServePerUscireDalFor) {
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

			System.out.println();
			MiServePerUscireDalFor = false;

		}
		System.out.println("Player : " + manager.getPlayer().getLife());
	}

}
