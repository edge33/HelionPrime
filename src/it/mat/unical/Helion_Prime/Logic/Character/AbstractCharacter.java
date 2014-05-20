package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.HasScore;
import it.mat.unical.Helion_Prime.Logic.Wall;
import it.mat.unical.Helion_Prime.Logic.World;

public abstract class AbstractCharacter implements Character, HasScore {

	private int x;
	private int y;

	private int graphicX;
	private int graphicY;

	private int direction;
	private int score;
	protected int lifePoints;
	protected World world;

	protected boolean alive;

	public AbstractCharacter(int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.world = world;
		this.score = 0;
		this.lifePoints = 100000;
		this.alive = true;
	}

	public int getDirection() {
		return direction;
	}

	@Override
	public void setX(int positionX) {
		this.x = positionX;
	}

	@Override
	public void setY(int positionY) {
		this.y = positionY;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getLife() {
		return lifePoints;
	}

	@Override
	public void setLife(int lifePoints) {
		if (this.lifePoints > 0) {
			this.lifePoints = lifePoints;
		}
		if (this.lifePoints <= 0) {
			System.out.println("falso filippo morto");
			this.alive = false;
		}
	}

	public boolean isAlive() {
		return this.alive;
	}

	// muove i personaggi, tutti i personaggi chiamano come super questa
	// funzione
	@Override
	public void move(int direction) {

		// TODO: controllare anche i bordi ... forse;
		switch (direction) {
		case UP:
			if (!(world.getElementAt(x - 1, y) instanceof Wall))
				this.x = x - 1;
			this.direction = direction;
			break;
		case DOWN:
			if (!(world.getElementAt(x + 1, y) instanceof Wall))
				this.x = x + 1;
			this.direction = direction;
			break;
		case LEFT:
			if (!(world.getElementAt(x, y - 1) instanceof Wall))
				this.y = y - 1;
			this.direction = direction;
			break;
		case RIGHT:
			if (!(world.getElementAt(x, y + 1) instanceof Wall))
				this.y = y + 1;
			this.direction = direction;
			break;
		default:
			break;
		}

	}

	public World getWorld() {
		return this.world;
	}

	// cordinate da usare nell'interfaccia 2d
	public int getGraphicX() {
		return graphicX;
	}

	// imposta i valori di x nella grafica
	public void setGraphicX(int graphicX) {
		this.graphicX = graphicX;
	}

	// cordinate da usare nell'interfaccia 2d
	public int getGraphicY() {
		return graphicY;
	}

	// imposta i valori di y nella grafica
	public void setGraphicY(int graphicY) {
		this.graphicY = graphicY;
	}

	// cambia la direzione
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
