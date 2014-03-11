package it.mat.unical.Helion_Prime.LevelEditor;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JLabel;

public class EditorImageProvider {			//ogni immagine conterr√† un componente del livello
	private final Image wall;			//conterr‡† il muro orizzontale
	private final Image flippedWall;	//conterr‡† il muro verticale 
	private final Image floor;			//conterr‡† il pavimento
	private final Image room;			//conterr‡† la safeRoom
	private final Image spawn;			//conterr‡† il filippoSpawn
	private final Image enemySpawn;		//conterr‡† lo enemySpawn
	
	private final Image i8;				//conterr‡† il cornerAS {alto che svetta a sinistra}
	private final Image i9;				//conterr‡† il cornerAD {alto che svetta a destra}
	private final Image i10;			//conterr‡† il cornerBD {basso che svetta a destra}
	private final Image i11;			//conterr‡† il cornerBS {basso che svetta a sinistra}
	
	private final Image saboteruPreview;
	private final Image soldierPreview;
	private final Image bountyPreview;
	

	public EditorImageProvider(){
		final Toolkit toolKit = Toolkit.getDefaultToolkit(); 				//istanziazione del Toolkit necessario alle immagini
		
		this.flippedWall = toolKit.getImage("Resources/wallF30x30.jpg");	//search&allocate dell'immagine per il muroVerticale 
		this.wall = toolKit.getImage("Resources/wall30x30.jpg");			//search&allocate dell'immagine per il muroOrizzontale 
		this.room = toolKit.getImage("Resources/room30x30.jpg");			//search&allocate dell'immagine per la safeRoom
		this.floor = toolKit.getImage("Resources/floor30x30.png");			//search&allocate dell'immagine per il pavimento
		this.spawn = toolKit.getImage("Resources/spawn30x30.png");			//search&allocate dell'immagine per il filippoSpawn
		this.enemySpawn = toolKit.getImage("Resources/eSpawn30x30.jpg");	//search&allocate dell'immagine per lo enemySpawn
		this.i8 = toolKit.getImage("Resources/8.jpg");						//search&allocate dell'immagine per il cornerAS
		this.i9 = toolKit.getImage("Resources/9.jpg");						//search&allocate dell'immagine per il cornerAD
		this.i10 = toolKit.getImage("Resources/10.jpg");					//search&allocate dell'immagine per il cornerBD
		this.i11 = toolKit.getImage("Resources/11.jpg");					//search&allocate dell'immagine per il cornerBS
	
		this.soldierPreview = toolKit.getImage("Resources/11.jpg");			//search&allocate dell'immagine per soldier
		this.saboteruPreview = toolKit.getImage("Resources/11.jpg");		//search&allocate dell'immagine per saboteur
		this.bountyPreview = toolKit.getImage("Resources/11.jpg");			//search&allocate dell'immagine per bounty
		
		
		final MediaTracker tracker = new MediaTracker(new JLabel());		//istanziazione del MediaTraker 
		
		tracker.addImage(floor, 0);											//load&identify dell'immagine per il pavimento
		tracker.addImage(wall, 1);											//load&identify dell'immagine per il muroOrizzontale 
		tracker.addImage(spawn,2);											//load&identify dell'immagine per il filippoSpawn
		tracker.addImage(enemySpawn, 3);									//load&identify dell'immagine per lo enemySpawn
		tracker.addImage(room, 6);											//load&identify dell'immagine per la safeRoom
		tracker.addImage(i8,8);												//load&identify dell'immagine per il cornerAS
		tracker.addImage(i9,9);												//load&identify dell'immagine per il cornerAD				
		tracker.addImage(i10,10);											//load&identify dell'immagine per il cornerBD
		tracker.addImage(i11,11);											//load&identify dell'immagine per il cornerBS
		tracker.addImage(flippedWall, 12);									//load&identify dell'immagine per il muroVerticale
		tracker.addImage(soldierPreview, 13);								//load&identify dell'immagine per il soldier
		tracker.addImage(saboteruPreview, 14);								//load&identify dell'immagine per il saboteur
		tracker.addImage(bountyPreview, 15);								//load&identify dell'immagine per il bounty
		try {
			tracker.waitForAll();											//attende il caricamento di tutte le immagini
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}																		//inizio metodi Get() precompilati
	public Image getWall() {												
		return wall;
	}
	
	public Image getFloor() {
		return floor;
	}
	
	public Image getRoom() {
		return room;
	}
	
	public Image getSpawn() {
		return spawn;
	}
	
	public Image getEnemySpawn() {
		return enemySpawn;
	}
	public Image getUpDXcorner() {
		return i8;
	}
	public Image getDownDXcorner() {
		return i9;
	}
	public Image getUpSXcorner() {
		return i10;
	}
	public Image getDownSXcorner() {
		return i11;
	}
	public Image getFlippedWall() {
		return flippedWall;
	}
	public Image getSaboteruPreview() {
		return saboteruPreview;
	}
	public Image getSoldierPreview() {
		return soldierPreview;
	}
	public Image getBountyPreview() {
		return bountyPreview;
	}
}
