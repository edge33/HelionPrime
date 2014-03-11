package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.World;

public class AbstractFactoryTrap {

	public AbstractFactoryTrap() {
	}

	public AbstractTrap returnTrapForType(int x, int y, int type) {
		if (type == 0) {
			return new SpikeTrap(x, y, 100);
		}
			
		if (type == 1) {
			return new FireTrap(x, y, 200);
		}
		if (type == 3) {
			return new AcidTrap(x, y, 300);
		}
		if (type == 4) {
			return new ElectricTrap(x, y, 300);
		}
		if (type == 5) {
			return new TrapPower(x, y, 300);
		}

		
		return null;
	}
	
	public AbstractTrap returnTrapForTypeWorld(int x, int y, World world) {
			return new DecoyTrap(x, y, 300, world);
	}

}
