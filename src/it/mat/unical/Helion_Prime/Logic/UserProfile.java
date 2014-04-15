package it.mat.unical.Helion_Prime.Logic;

import java.util.HashMap;

public class UserProfile {

	private String name;
	private int gun1, gun2, gun3;
	private HashMap<Integer, String> levels;
	private static int lastlevelComplete;

	public UserProfile(String name) {
		this.name = name;
		lastlevelComplete = 1;
		levels = new HashMap<Integer, String>();
		loadDefaultLevels();
	}

	private void loadDefaultLevels() {
		levels.put(1, "bastion");
		levels.put(2, "crossroad");
		levels.put(3, "labyrinth");
		levels.put(4, "spirals");
		levels.put(5, "twistedlane");
		levels.put(6, "vasteland");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGun1() {
		return gun1;
	}

	public void setGun1(int gun1) {
		this.gun1 = gun1;
	}

	public int getGun2() {
		return gun2;
	}

	public void setGun2(int gun2) {
		this.gun2 = gun2;
	}

	public int getGun3() {
		return gun3;
	}

	public void setGun3(int gun3) {
		this.gun3 = gun3;
	}

	public HashMap<Integer, String> getLevels() {
		return levels;
	}

	public int getLastlevelComplete() {
		return lastlevelComplete;
	}

	public void setLastlevelComplete(int lastlevelComplete) {
		UserProfile.lastlevelComplete = lastlevelComplete;
	}

	public static void incrLevel() {
		lastlevelComplete++;
	}

}
