package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.StaticObject;
import it.mat.unical.Helion_Prime.Logic.Wall;
import it.mat.unical.Helion_Prime.Logic.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindRoomAI implements NativeAI {

	private static FindRoomAI instance;

	private FindRoomAI() {

	}
	
	
	public static FindRoomAI getInstance() {
		if ( instance == null ) 
			instance = new FindRoomAI();
		return instance;
	}

private static class Cell {
		
		 int x;
		
		int y;
		
		public Cell(final int x, final int y) {
			this.x = x;
			this.y = y;
		}
		
		
		@Override
		public boolean equals(Object cell) {
			
			if ( this == cell ) {
				return true;
			}
			if ( cell == null  ) {
				return false;
			}
			final Cell other = (Cell) cell;
			if ( this.x != other.x ) {
				return false;
			}
			if ( this.y != other.y ) {
				return false;
			}
			
			return true;
		}
		
	}
	
	@Override
	public int getDirection(AbstractNative currentNative,Object target, World world) {
		
		StaticObject room = (StaticObject) target;
		
		final List<Cell> queue = new ArrayList<Cell>();
		
		Cell cell = new Cell ( currentNative.getX(),currentNative.getY() );
		
		
		queue.add(cell);
		
		boolean found = false;
		
		int last = 0;
		
		final Map<Cell,Cell> tree = new HashMap<Cell,Cell>();
		
		while ( !found && last < queue.size() )  {
			
			Cell currentCell = queue.get(last++); 
			                                      
			if ( ( room.getX() == currentCell.x - 1 && room.getY() == currentCell.y ) || 
					 ( room.getX() == currentCell.x + 1 && room.getY() == currentCell.y ) ||
					 ( room.getX() == currentCell.x && room.getY() == currentCell.y - 1 ) ||
					 ( room.getX() == currentCell.x && room.getY() == currentCell.y + 1 )
			) {
				
				
	
				found = true;
				return findDirectionFromCell(tree,currentCell,currentNative);
				
			}
			else {
				addChildren(queue,tree,currentCell,world);
			}
		}
		
		return -1;
	}
	
private void addChildren(List<Cell> queue, Map<Cell, Cell> tree, Cell currentCell,World world) {
		
		
		if ( currentCell.x - 1 >= 0 && !(world.getElementAt(currentCell.x-1,currentCell.y) instanceof Wall )) {
			final Cell newCell = new Cell(currentCell.x-1, currentCell.y);
			
			if ( !queue.contains(newCell) ) {
				queue.add(newCell);
				tree.put(newCell,currentCell);
			}
		}
		if (currentCell.x + 1 < world.getHeight() && !(world.getElementAt(currentCell.x+1, currentCell.y) instanceof Wall))
        {
            final Cell newCell = new Cell(currentCell.x + 1, currentCell.y);
            if (!queue.contains(newCell))
            {
                queue.add(newCell);
                tree.put(newCell, currentCell);
            }
        }
        if (currentCell.y - 1 >= 0 && !(world.getElementAt(currentCell.x, currentCell.y-1) instanceof Wall))
        {
            final Cell newCell = new Cell(currentCell.x, currentCell.y - 1);
            if (!queue.contains(newCell))
            {
                queue.add(newCell);
                tree.put(newCell, currentCell);
            }
        }
        if (currentCell.y + 1 < world.getLenght() && !(world.getElementAt(currentCell.x, currentCell.y+1) instanceof Wall))
        {
            final Cell newCell = new Cell(currentCell.x, currentCell.y + 1);
            if (!queue.contains(newCell))
            {
                queue.add(newCell);
                tree.put(newCell, currentCell);
            }
        }
		
	}



	private int findDirectionFromCell(Map<Cell, Cell> tree, Cell currentCell,AbstractNative currentNative) {
		
		Cell parent = tree.get(currentCell);
		Cell directionCell = currentCell;
		
		while ( tree.get(parent) != null ) {
			directionCell = parent;
			parent = tree.get(parent);
		}
		
		if ( directionCell == null ) {
			return -1;
		}
		else if (directionCell.x < currentNative.getX()) {
	            return Character.UP;
	    }
	    else if (directionCell.x > currentNative.getX()) {
	            return Character.DOWN;
	    }
	    else if (directionCell.y < currentNative.getY()) {
	            return Character.LEFT;
	    }
	    else if (directionCell.y >  currentNative.getY()) {
	            return Character.RIGHT;
	    }
	    else {
	    	return -1;
	    }
		
		
	}
	@Override
	public int getAiType()
	{
	  return NativeAI.ROOM_FINDER;
	}

}
