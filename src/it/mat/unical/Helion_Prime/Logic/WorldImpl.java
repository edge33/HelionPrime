package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Trap.DecoyTrap;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WorldImpl implements World {

	private int height;
	private int lenght;
	private int roomLife;
	private StaticObject[][] matrix;

	private StaticObject nativeSpawner;
	private StaticObject playerSpawner;
	private StaticObject room;
	private Wave wave;
	private boolean fake = false;
	private int fakeX;
	private int fakeY;
	public DecoyTrap decoy;

	public WorldImpl(File level) throws FileNotCorrectlyFormattedException {

		try {

			Scanner reader = new Scanner(level);
			Scanner firstLineScanner = null;
			Scanner secondLineScanner = null;
			String firstLine = reader.nextLine();
			firstLineScanner = new Scanner(firstLine);
			roomLife = firstLineScanner.nextInt();
			System.out.println("sono World impl che stampa: la room ha "
					+ roomLife + " punti vita");
			firstLineScanner.close();
			String secondLine = reader.nextLine();
			secondLineScanner = new Scanner(secondLine);
			if (secondLineScanner.hasNextInt()) {
				this.height = secondLineScanner.nextInt();
			} else
				throw new FileNotCorrectlyFormattedException();
			if (secondLineScanner.hasNextInt()) {
				this.lenght = secondLineScanner.nextInt();
			} else
				throw new FileNotCorrectlyFormattedException();
			secondLineScanner.close();

			this.matrix = new StaticObject[height][lenght];

			int number = 0;

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < lenght; j++) {
					if (reader.hasNextInt()) {
						number = reader.nextInt();
						if (number == 2) {
							matrix[i][j] = new PlayerSpawner(i, j);
							this.playerSpawner = matrix[i][j];
						} else {
							switch (number) {

							case 1:
								matrix[i][j] = new Wall(i, j, 0);
								break;
							case 3:
								matrix[i][j] = new NativeSpawner(i, j);
								this.nativeSpawner = matrix[i][j];
								break;
							case 6:
								matrix[i][j] = new MaintenanceRoom(i, j,
										roomLife);
								this.room = matrix[i][j];
								break;
							case 8:
								matrix[i][j] = new Corner(i, j, 8);
								break;
							case 9:
								matrix[i][j] = new Corner(i, j, 9);
								break;
							case 10:
								matrix[i][j] = new Corner(i, j, 10);
								break;
							case 11:
								matrix[i][j] = new Corner(i, j, 11);
								break;
							case 12:
								matrix[i][j] = new Wall(i, j, 1);
								break;

							}
						}
					}

				}
			}
			reader.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update() {

		// TODO Controllare cosa deve fare update del world
		// matrix[thePlayer.getOldX()][thePlayer.getOldY()] = null;
		// matrix[thePlayer.getX()][thePlayer.getY()] = 5;
		/*
		 * if (thePlayer instanceof Player) { Player p = (Player) thePlayer; if
		 * (((Player) thePlayer).hasPlacedTrap()) matrix[p.getTrapFromArray(0,
		 * p.getTrap()).getX()][p .getTrapFromArray(0, p.getTrap()).getY()] =
		 * new AbstractTrap(); }
		 */
	}

	@Override
	public WaveImpl getWaveImpl() {
		return (WaveImpl) this.wave;
	}

	@Override
	public StaticObject[][] getWorld() {
		return matrix;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int getLenght() {

		return this.lenght;
	}

	@Override
	public int getRoomLife() {

		return this.roomLife;
	}

	@Override
	public void setRoomLife(int i) {

		roomLife = i;
	}

	@Override
	public StaticObject getElementAt(int i, int j) {
		return matrix[i][j];
	}

	@Override
	public StaticObject getNativeSpawner() {
		return nativeSpawner;
	}

	@Override
	public StaticObject getPlayerSpawner() {
		return playerSpawner;
	}

	@Override
	public StaticObject getRoom() {
		return room;
	}

	public void setWave(Wave v) {
		this.wave = v;
	}

	@Override
	public void fakeOn() {
		fake = true;
	}

	@Override
	public void fakeOff() {
		fake = false;
	}

	@Override
	public boolean getFake() {
		return fake;
	}

	public int getFakeX() {
		return fakeX;
	}

	public int getFakeY() {
		return fakeY;
	}

	public void setFakeX(int fakeX) {
		this.fakeX = fakeX;
	}

	public void setFakeY(int fakeY) {
		this.fakeY = fakeY;
	}

	@Override
	public void setDecoy(DecoyTrap decoyTrap) {
		decoy = decoyTrap;
	}

	public void setDecoyLife() {
		decoy.setLife(1);
	}

	@Override
	public DecoyTrap getDecoy() {
		return decoy;
	}

}
