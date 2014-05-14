package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.Timestamp;

public class PlayerState {

	private String username;
	private Timestamp timestamp;

	private int gunBullets1;
	private int gunBullets2;
	private int gunBullets3;
	private int gunBullets4;

	private int lastLevelCleared;
	private int score;

	private static PlayerState instance;

	public static PlayerState getInstance() {
		if (instance == null)
			instance = new PlayerState();
		return instance;
	}

	private PlayerState() {

	}

	public void init(final String username) {
		reset();
		this.username = username;
		this.timestamp = new Timestamp(new java.util.Date().getTime());
	}

	private void reset() {
		this.gunBullets1 = 0;
		this.gunBullets2 = 0;
		this.gunBullets3 = 0;
		this.gunBullets4 = 0;
		this.lastLevelCleared = 0;
		this.score = 0;
	}

	public PlayerState loadProfile(String username, Timestamp timestamp) {
		this.username = username;
		this.timestamp = timestamp;

		SaveManagerImpl.getInstance().loadGame(username, timestamp, this);

		return this;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getTimeStamp() {
		return timestamp;
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

	public void incrBulletState(int currentGun) {
		switch (currentGun) {
		case 0:
			gunBullets1++;
			break;
		case 1:
			gunBullets2++;
			break;
		case 2:
			gunBullets3++;
			break;
		case 3:
			gunBullets4++;
			break;
		default:
			break;
		}

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return new String(username + " " + timestamp.toString() + " " + gunBullets1 + " " + " " + gunBullets2 + " " + gunBullets3 + " " + gunBullets4 + " " + lastLevelCleared + " " + score);
	}

	public void setTimestamp(Timestamp newTimeStamp) {
		this.timestamp = newTimeStamp;
	}

}
