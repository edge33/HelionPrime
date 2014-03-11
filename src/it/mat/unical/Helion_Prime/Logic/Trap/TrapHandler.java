package it.mat.unical.Helion_Prime.Logic.Trap;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

import java.util.Collection;
import java.util.Iterator;

public class TrapHandler extends Thread {
	
	private Collection<AbstractTrap> traps; 
	private GameManagerImpl manager;

	public TrapHandler(Collection<AbstractTrap> traps,GameManagerImpl manager) {
		this.traps = traps;
		this.manager = manager;
	}
	
	@Override
	public void run() {
	
		while ( !manager.gameIsOver() ) {
			
			System.out.println("dimension " + traps.size());
			
			for ( Iterator<AbstractTrap> it = traps.iterator() ; it.hasNext() && traps.size() > 0 ; ) {
				
				AbstractTrap currentTrap = it.next();
				
				if ( !currentTrap.isActive() ) {
					currentTrap.setActive(true);
				}
				
				
				if ( currentTrap.getLife() == 0 ) {
					traps.remove(currentTrap);
				}
				
			} 
		
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} 
		
	
	}
	
}
