package it.mat.unical.Helion_Prime.Logic;

public class AbstractNativeLite {

	private int x;
	private int y;
	private int key;
	private int typeNative;
	private int currentPosition = 1;
	private int direction;
	private int graphicX;
	private int graphicY;

	public AbstractNativeLite(int x, int y, int typeNative, int key) {

		this.x = x;
		this.y = y;
		this.key = key;
		this.typeNative = typeNative;
		this.direction = -1;

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

}
