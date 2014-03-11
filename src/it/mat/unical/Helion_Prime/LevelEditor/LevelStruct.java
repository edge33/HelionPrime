package it.mat.unical.Helion_Prime.LevelEditor;

import it.mat.unical.Helion_Prime.Logic.StaticObject;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class LevelStruct {


	
	private int row;				//numero di righe che avrà la struttura
	private int column;			//numero di colonne che avrà la struttura
	
	private int objectID = 0;
	private int[][] matrix;			//la matrice d'interi che conterrà il livello

	private boolean playerSpawnerAlreadyPlaced;
	private boolean enemySpawnerAlreadyPlaced;
	private boolean maintenanceRoomAlreadyPlaced;
	
	private Point playerSpawnerPosition;
	private Point enemySpawnerPosition;
	private Point maintenanceRoomPosition;
	private int roomLife;

	public LevelStruct(int row, int column) { //Costruttore della Struttura
		this.row = row;
		this.column = column;
		initMatrix();
	}
	
	public void initMatrix() {
		
		this.enemySpawnerAlreadyPlaced = false;
		this.playerSpawnerAlreadyPlaced = false;
		this.maintenanceRoomAlreadyPlaced = false;
		
		this.enemySpawnerPosition = null;
		this.playerSpawnerPosition = null;
		this.maintenanceRoomPosition = null;
		
		matrix = new int[row][column];
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if ( i == 0 || i == row - 1 ) {
					matrix[i][j] = 1;
				} else if ( j == 0 || j == column - 1 ) {
					matrix[i][j] = 1;
				} else {
					matrix[i][j] = 0;
				}
				
			}
		}
	}
	


	
	
	public LevelStruct(File file) throws Exception {
		
		
		Scanner reader = new Scanner(file);
		Scanner firstLineScanner = null;
		String firstLine = reader.nextLine();
		firstLineScanner = new Scanner(firstLine);
		if (firstLineScanner.hasNextInt())
		{
			this.roomLife = firstLineScanner.nextInt();
			System.out.println(roomLife);
		}
		else {
			reader.close();
			firstLineScanner.close();
			throw new Exception("File Not Correctly Formatted");
		}
		firstLineScanner.close();
		Scanner secondLineScanner = null;
		String secondLine = reader.nextLine();
		secondLineScanner = new Scanner(secondLine);
		if (secondLineScanner.hasNextInt())
		{
			this.row = secondLineScanner.nextInt();
		}
		else {
			reader.close();
			secondLineScanner.close();
			throw new Exception("File Not Correctly Formatted");
		}
			if (secondLineScanner.hasNextInt())
		{
			this.column = secondLineScanner.nextInt();
		}
		else {
			reader.close();
			secondLineScanner.close();
			throw new Exception("File Not Correctly Formatted");
		}
		secondLineScanner.close();
		matrix = new int[row][column];
		int number=0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (reader.hasNextInt()) 
				{
					number=reader.nextInt();
					matrix[i][j] =  number;
				}
			}
		}
		reader.close();
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int height) {
		this.row = height;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int lenght) {
		this.column = lenght;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public int getElementAt(int i, int j) {
		return matrix[i][j];
	}

	public boolean setElementAt(int i, int j, int element ) {
		
		int elementToBeOverwritten = this.matrix[i][j];
		
		switch (elementToBeOverwritten) {
		
		case StaticObject.ENEMY_SPAWNER:
			
			matrix[i][j] = StaticObject.FLOOR;
			this.enemySpawnerAlreadyPlaced = false;
			
			break;

		case StaticObject.PLAYER_SPAWNER:
			
			matrix[i][j] = StaticObject.FLOOR;
			this.playerSpawnerAlreadyPlaced = false;
			
			break;
			
		case StaticObject.MAINTENANCE_ROOM :
			
			matrix[i][j] = StaticObject.FLOOR;
			this.maintenanceRoomAlreadyPlaced = false;
			
			break;
		}
		
		
		if ( element == StaticObject.ENEMY_SPAWNER || element == StaticObject.PLAYER_SPAWNER || element == StaticObject.MAINTENANCE_ROOM )
			return setCriticalElement(i, j, element);
		
		matrix[i][j] = element;
		
		return true;
	}
	
	private boolean setCriticalElement(int i,int j,int element) {
		
		if ( element == StaticObject.ENEMY_SPAWNER && !enemySpawnerAlreadyPlaced ) {
			this.matrix[i][j] = element;
			this.enemySpawnerAlreadyPlaced = true;
			this.enemySpawnerPosition = new Point(i, j);
			return true;
		} else if ( element == StaticObject.PLAYER_SPAWNER && !playerSpawnerAlreadyPlaced ) {
			this.matrix[i][j] = element;
			this.playerSpawnerAlreadyPlaced = true;
			this.enemySpawnerPosition = new Point(i, j);
			return true;
		} else if ( element == StaticObject.MAINTENANCE_ROOM && !maintenanceRoomAlreadyPlaced ) {
			this.matrix[i][j] = element;
			this.maintenanceRoomAlreadyPlaced = true;
			this.maintenanceRoomPosition = new Point(i,j);
			return true;
		}
		
		return false;
	}

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}
}
