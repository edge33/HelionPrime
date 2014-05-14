package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.World;

public class BountyHunterNative extends AbstractNative {

	private int direction;
	
	private int currentPosition = 1;
	private final int type = 1;

	private int coolDownTime = 1500;

	public BountyHunterNative(int x, int y, World world, int nativeIndex) {
		super(x, y, world, nativeIndex);
		super.setLife(100);
		super.attackPower = 10;
		this.direction = 0;
		super.nativeAi = BasicAI.getInstance();
		super.coolDownManager = new CoolDownManager(this, coolDownTime);
		coolDownManager.start();

	}

	@Override
	public void move(int direction) {
		super.move(direction);
	}

	public int getType() {
		return type;
	}
}
