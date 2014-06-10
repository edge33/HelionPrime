package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.World;

public interface NativeAI {

	public int getDirection(AbstractNative currentNative,Object targetObject, World world);
	public int getAiType(); //ogni nuova ia avrà un identificatore cosi da poter essere identificata dall'interfaccia Abstract Native 
	
	public static final int PLAYER_FINDER = 0;
	public static final int ROOM_FINDER = 1;
	public static final int TRAP_FINDER = 2;
}
