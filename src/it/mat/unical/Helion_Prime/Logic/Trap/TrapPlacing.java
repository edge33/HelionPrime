package it.mat.unical.Helion_Prime.Logic.Trap;

import java.awt.Point;
import java.util.concurrent.ConcurrentHashMap;

public interface TrapPlacing {

	public abstract boolean canPlaceTrap(int numberOfTrapInArray,
			int positionXonMap, int positionYonMap);

	public boolean hasPlacedTrap();

	public AbstractTrap getTrapFromArray(int index);

	public ConcurrentHashMap<Point, AbstractTrap> getTrap();
}
