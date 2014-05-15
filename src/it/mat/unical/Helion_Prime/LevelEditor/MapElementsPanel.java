package it.mat.unical.Helion_Prime.LevelEditor;

import it.mat.unical.Helion_Prime.Logic.StaticObject;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MapElementsPanel extends JPanel implements SimpleObserver {

	private LevelStruct levelStruct;
	private JButton roomEntranceButton;
	private JButton filippoSpawnButton;
	private JButton cornerButton;
	private JButton wallButton;
	private JButton floorButton;
	private JButton enemySpawnerButton;
	
	
	
	public MapElementsPanel(LevelStruct levelStruct) {
		
		
		this.levelStruct = levelStruct;
		GridLayout layout = new GridLayout(0,2);
		this.setLayout(layout);
		
		
		filippoSpawnButton = new JButton("Filippo Spawn");
		filippoSpawnButton.addActionListener(new ActionListener() {
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.PLAYER_SPAWNER);
				enableButtons();
			}

			});
		cornerButton = new JButton("Corner");
		cornerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.CORNER);
				enableButtons();				
				MapElementsPanel.this.cornerButton.setEnabled(false);
			}

		});
		wallButton = new JButton("Wall");
		wallButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.WALL);
				enableButtons();
				MapElementsPanel.this.wallButton.setEnabled(false);
			}

		});
		floorButton = new JButton("Floor");
		floorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.FLOOR);
				enableButtons();
				MapElementsPanel.this.floorButton.setEnabled(false);
			}

		});
		enemySpawnerButton = new JButton("Enemy Spawn");
		enemySpawnerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			MapElementsPanel.this.levelStruct.setObjectID(StaticObject.ENEMY_SPAWNER);
				enableButtons();
			}

		});
		roomEntranceButton = new JButton("Maintenance Room");
		roomEntranceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.MAINTENANCE_ROOM);
				enableButtons();
			}

		});
		
		add(filippoSpawnButton);
		add(enemySpawnerButton);
		add(wallButton);
		add(floorButton);
		add(roomEntranceButton);
		add(cornerButton);
		
	}


	public void setLevelStruct(LevelStruct levelStruct) {
		this.levelStruct = levelStruct;
	}


	@Override
	public void update(int mapElement) {
		if ( mapElement == StaticObject.ENEMY_SPAWNER ) {
			if ( this.enemySpawnerButton.isEnabled() ) 
				this.enemySpawnerButton.setEnabled(false);
			else 
				this.enemySpawnerButton.setEnabled(true);
		} else if ( mapElement == StaticObject.PLAYER_SPAWNER ) {
			if ( this.filippoSpawnButton.isEnabled() ) 
				this.filippoSpawnButton.setEnabled(false);
			else 
				this.filippoSpawnButton.setEnabled(true);
		} else if ( mapElement == StaticObject.MAINTENANCE_ROOM ) {
			if ( this.roomEntranceButton.isEnabled() ) 
				this.roomEntranceButton.setEnabled(false);
			else 
				this.roomEntranceButton.setEnabled(true);
		}
		
		this.levelStruct.setObjectID(StaticObject.FLOOR);;
			
	}
	
	private void enableButtons() {
		this.cornerButton.setEnabled(true);
		this.floorButton.setEnabled(true);
		this.wallButton.setEnabled(true);
	}
	
}
