package it.mat.unical.Helion_Prime.Logic;

public interface StaticObject {

	public static final int FLOOR = 0;
	
	public static final int WALL = 1;
	
	public static final int PLAYER_SPAWNER = 2;
	
	public static final int ENEMY_SPAWNER = 3;
	
	public static final int MAINTENANCE_ROOM = 6;
	
	public static final int CORNER = 8;
	
	public static final int CORNER_1 = 9;
	
	public static final int CORNER_2 = 10;
	
	public static final int CORNER_3 = 11;

	public static final int WALL_2 = 12;

	public abstract int getX();

	public abstract int getY();

}
