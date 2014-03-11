package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.MaintenanceRoom;
import it.mat.unical.Helion_Prime.Logic.StaticObject;
import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractTrap;

public interface NativeAI {

	public int getDirectionFromPlayerAi(AbstractNative currentNative,Player player, World world);
	public int getDirectionFromRoomAi(AbstractNative currentNative,StaticObject room, World world);
	public int getDirectionFromTrapAi(AbstractNative currentNative,AbstractTrap trap, World world);
	public int getAiType(); //ogni nuova ia avrà un identificatore cosi da poter essere identificata dall'interfaccia Abstract Native 
	
}
