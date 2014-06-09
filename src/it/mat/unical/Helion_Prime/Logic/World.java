package it.mat.unical.Helion_Prime.Logic;


public interface World {

	public abstract int getHeight();

	public abstract int getLenght();

	public abstract void update();

	public Wave getWaveImpl();

	public StaticObject[][] getWorld();

	public StaticObject getElementAt(int i, int j);

	public StaticObject getNativeSpawner();

	public StaticObject getPlayerSpawner();

	public StaticObject getRoom();

	public int getRoomLife();

	public void setRoomLife(int i); // temporaneo sereve solo per simulare la
									// Room

	public abstract void fakeOn();

	public abstract void fakeOff();

	public abstract boolean getFake();

	public abstract int getFakeX();

	public abstract int getFakeY();

	public abstract void setFakeX(int fakeX);

	public abstract void setFakeY(int fakeY);


}
