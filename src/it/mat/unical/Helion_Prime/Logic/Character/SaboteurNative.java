package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.World;

public class SaboteurNative extends AbstractNative {

	private int direction;
	private int currentPosition = 1;
	private final int type = 2;
	private final int cooldownTime = 3000;
	private int id;

	public SaboteurNative(int x, int y, World world, int nativeIndex, int id) {
		super(x, y, world, nativeIndex, id);
		this.id = id;
		super.setLife(100);
		super.attackPower = 10;
		this.direction = 0;
		super.nativeAi = FindTrapAI.getInstance();

		coolDownManager = new CoolDownManager(this, cooldownTime, id);
		coolDownManager.start();

	}

	@Override
	public void move(int direction) {
		super.move(direction);
	}

	@Override
	public int getCooldownTime() {
		return cooldownTime;
	}

	public int getDirection() {
		return direction;
	}

	public int getType() {
		return type;
	}

}
