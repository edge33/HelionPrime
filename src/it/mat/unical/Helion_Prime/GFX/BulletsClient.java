package it.mat.unical.Helion_Prime.GFX;

public class BulletsClient {

	private int direction;
	private int graphicX;
	private int graphicY;
	private int x;
	private int y;

	public BulletsClient(int direction, int logicX, int logicY) {
		this.direction = direction;

		this.x = logicX;
		this.y = logicY;
		this.graphicX = x * 50;
		this.graphicY = y * 50;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getGraphicX() {
		return graphicX;
	}

	public void setGraphicX(int graphicX) {
		this.graphicX = graphicX;
	}

	public int getGraphicY() {
		return graphicY;
	}

	public void setGraphicY(int graphicY) {
		this.graphicY = graphicY;
	}

	public void shooting() {
		switch (this.direction) {
		case 0:
			this.setGraphicX(this.getGraphicX() - 15);

			break;
		case 1:

			this.setGraphicX(this.getGraphicX() + 15);
			break;

		case 2:
			this.setGraphicY(this.getGraphicY() - 15);

			break;

		case 3:
			this.setGraphicY(this.getGraphicY() + 15);

			break;

		default:
			break;
		}
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
}
