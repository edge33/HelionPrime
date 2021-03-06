package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Online.ClientManager;

public class AbstractNativeLite {

	private int x;
	private int y;
	private int key;
	private int typeNative;
	private int currentPosition = 2;
	private boolean isDeath;
	private int direction;
	private int graphicX;
	private int graphicY;
	private int enemyResistance;

	private int numberOfMovement = 10;
	private boolean isVeryDeath;

	public AbstractNativeLite(int x, int y, int typeNative, int key) {
		isDeath = false;
		isVeryDeath = false;
		this.x = x;
		this.y = y;
		this.key = key;
		this.typeNative = typeNative;
		this.direction = 1;
		new Thread() {

			public void run() {
				this.setName("ABSTRACT NATIVE LIGHT"
						+ AbstractNativeLite.this.key);
				while (!ClientManager.isFinishGame() && !isDeath) {

					if (currentPosition == 1)
						currentPosition = 2;
					else if (currentPosition == 2)
						currentPosition = 3;
					else if (currentPosition == 3)
						currentPosition = 4;
					else if (currentPosition == 4)
						currentPosition = 1;

					try {
						sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				try {
					sleep(350);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				isVeryDeath = true;

			}
		}.start();

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getTypeNative() {
		return typeNative;
	}

	public void setTypeNative(int typeNative) {
		this.typeNative = typeNative;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setGraphicX(int i) {
		this.graphicX = i;

	}

	public void setGraphicY(int i) {
		this.graphicY = i;

	}

	public int getResistance() {
		return enemyResistance;
	}

	public void setResistance(int r) {
		enemyResistance = r;
	}

	public int getGraphicX() {
		return graphicX;
	}

	public int getGraphicY() {
		return graphicY;
	}

	public void decrMovement() {
		numberOfMovement--;

	}

	public int getNumberOfMovement() {
		return numberOfMovement;
	}

	public void setNumberOfMovement(int numberOfMovement) {
		this.numberOfMovement = numberOfMovement;
	}

	public boolean isDeath() {
		return isDeath;
	}

	public void setDeath(boolean isDeath) {
		this.isDeath = isDeath;
	}

	public boolean isVeryDeath() {
		return isVeryDeath;
	}

	public void setVeryDeath(boolean isVeryDeath) {
		this.isVeryDeath = isVeryDeath;
	}

}
