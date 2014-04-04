package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.Player;

import java.util.concurrent.ConcurrentHashMap;

//COMPARI MIEI CCA SENTIMU I RISI CCU SU BALLET

public class Bullet/* extends Thread */{

	private static final int TILE_SIZE = 50;
	private int x = 0;
	private int y = 0;
	private int direction; // intero che stabilisce la direzione del proiettile
							// (dovrebbe esserre uguale alla player direction)
	private World world;
	private boolean stopBullet; // quanto la variabile stopBullet sarà true il
								// proiettile sara cancellato
	private int graphicX;
	private int graphicY;
	private int offset = 15;
	private int damage;
	static int num = 0;

	public Bullet(WorldImpl pl, Player player, int dam) {
		world = pl;

		stopBullet = false;

		this.x = player.getX();
		this.y = player.getY();

		if (player.getDirection() == 3 || player.getDirection() == 2) {
			// this.graphicX = player.getGraphicX() + TILE_SIZE / 2;
			// this.graphicY = player.getGraphicY();

			this.graphicX = x * TILE_SIZE;
			this.graphicY = y * TILE_SIZE;
		}

		else {
			// this.graphicX = player.getGraphicX();
			// this.graphicY = player.getGraphicY() + TILE_SIZE / 2;
			this.graphicX = x * TILE_SIZE;
			this.graphicY = y * TILE_SIZE;
		}

		this.direction = player.getDirection();

		this.damage = dam;
		// this.start();

	}

	public Bullet(int positionX, int positionY, int direction, int dam,
			WorldImpl w) {
		this.world = w;
		this.x = positionX;
		this.y = positionY;
		this.graphicX = positionX * TILE_SIZE + (TILE_SIZE / 2);
		this.graphicY = positionY * TILE_SIZE + (TILE_SIZE / 2);
		this.direction = direction;
		this.damage = dam;
	}

	public boolean ControllStopBullet() { // si effettua un controllo sul
											// proiettile per vedere se si trova
											// sulla posizione di un nemico
		ConcurrentHashMap<Integer, AbstractNative> tmp = this.world
				.getWaveImpl().getNatives();

		for (AbstractNative currentNative : tmp.values()) {

			if (this.x == currentNative.getX() // se il proiettile colpirà un
												// nemico causerà danno con la
												// funzione setLife(...) per il
												// momento reca danno pari a 50
												// lifePoint , questo 50 sara
												// sostituito da un avariabile
												// che varierà a sendo del tipo
												// di proiettile che andremo a
												// sparare
					&& this.y == currentNative.getY()) {
				currentNative.setLife(currentNative.getLife() - damage);
				return true;
			}

		}
		return false;
	}

	public String shooting() {

		String string = null;

		switch (this.direction) {
		case 0:
			if (!(this.world.getElementAt(x - 1, y) instanceof Wall)) { // aggiorna
																		// la
																		// posizione
																		// proiettile
																		// per
																		// quanto
																		// riguarda
																		// la
																		// logica
																		// e la
																		// grafica

				graphicX -= offset;
			} else {
				stopBullet = true; // se trova un muro o arriva alla fine del
									// nostro mondo il proiettile sara
									// cancellato in modo tale da non sprecare
									// piu risorse essendo che sarà gestito da
									// un thread
			}
			if (graphicX <= ((this.x - 1) * TILE_SIZE) + TILE_SIZE) {
				this.x = x - 1;
				string = "sUP";
			}
			break;
		case 1:

			if (!(this.world.getElementAt(this.getX() + 1, this.y) instanceof Wall)) { // effettua
																						// lo
																						// stesso
																						// controllo
																						// spiegato
																						// prima
																						// ,
																						// e
																						// lo
																						// effettua
																						// per
																						// tutti
																						// i
																						// tipi
																						// di
																						// direzione

				graphicX += offset;

			} else
				stopBullet = true;

			if (this.graphicX >= ((this.x + 1) * TILE_SIZE) - TILE_SIZE / 3) {

				this.x += 1;
				string = "sDOWN";
			}
			break;
		case 2:

			if (!(this.world.getElementAt(x, y - 1) instanceof Wall))
				graphicY -= offset;

			else
				stopBullet = true;

			if (this.graphicY < ((this.y - 1) * TILE_SIZE) + TILE_SIZE) {
				this.y = y - 1;
				string = "sRIGHT";
			}
			break;
		case 3:
			if (!(this.world.getElementAt(x, y + 1) instanceof Wall))
				graphicY += offset;
			else
				stopBullet = true;

			if (graphicY > ((this.y + 1) * TILE_SIZE) - TILE_SIZE / 3) {
				this.y = y + 1;
				string = "sLEFT";
			}
			break;

		default:
			break;

		}
		return string;
	}

	// @Override
	// public void run() { // thread che gestisce il movimento del proiettile,
	// il
	// // thread rimarrà in vita fino a quando stopBullet non
	// // sarà TRUE
	//
	// while (!stopBullet) {
	//
	// this.shooting();
	// try {
	// sleep(20);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }

	public int getGraphicX() {
		return graphicX;
	}

	public int getGraphicY() {
		return graphicY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getStopBullet() {
		return this.stopBullet;
	}

}