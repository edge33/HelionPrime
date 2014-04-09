package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.Timestamp;

public class PlayerState {

	private String username;
	private Timestamp timeStamp;
	
	private int gunBullets1;
	private int gunBullets2;
	private int gunBullets3;
	private int gunBullets4;
	
	private int lastLevelCleared;
	private int score;
	
	
	private PlayerState instance;

	public PlayerState getInstance() {
		if ( instance == null ) 
			instance = new PlayerState();
		return instance;
	}
	
	private PlayerState() {
		
	}
	
	public void init(final String username) {
		this.username = username;
		this.timeStamp = new Timestamp( new java.util.Date().getTime()  );
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	
	public int getGunBullets1() {
		return gunBullets1;
	}

	public void setGunBullets1(int gunBullets1) {
		this.gunBullets1 = gunBullets1;
	}

	public int getGunBullets2() {
		return gunBullets2;
	}

	public void setGunBullets2(int gunBullets2) {
		this.gunBullets2 = gunBullets2;
	}

	public int getGunBullets3() {
		return gunBullets3;
	}

	public void setGunBullets3(int gunBullets3) {
		this.gunBullets3 = gunBullets3;
	}

	public int getGunBullets4() {
		return gunBullets4;
	}

	public void setGunBullets4(int gunBullets4) {
		this.gunBullets4 = gunBullets4;
	}

	public int getLastLevelCleared() {
		return lastLevelCleared;
	}

	public void setLastLevelCleared(int lastLevelCleared) {
		this.lastLevelCleared = lastLevelCleared;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
	
}
