package it.mat.unical.Helion_Prime.LevelEditor;

import it.mat.unical.Helion_Prime.Logic.StaticObject;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MapElementsPanel extends JPanel {

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
			}

		});
		cornerButton = new JButton("Corner");
		cornerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.CORNER);

			}

		});
		wallButton = new JButton("Wall");
		wallButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.WALL);

			}

		});
		floorButton = new JButton("Floor");
		floorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.FLOOR);

			}

		});
		enemySpawnerButton = new JButton("Enemy Spawn");
		enemySpawnerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			MapElementsPanel.this.levelStruct.setObjectID(StaticObject.ENEMY_SPAWNER);
			}

		});
		roomEntranceButton = new JButton("Maintenance Room");
		roomEntranceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapElementsPanel.this.levelStruct.setObjectID(StaticObject.MAINTENANCE_ROOM);
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
	
}
