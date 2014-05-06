package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Ability.AcidResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.ElectricResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.FireResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.IceResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.NoResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.Resistance;

public class SoldierNative extends AbstractNative {

	private int direction;
	private final int attackPower = 10;
	private int currentPosition = 1;
	private final int type = 0;
	private final int cooldownTime = 1000;
	private final Resistance resistance;

	public SoldierNative(int x, int y, World world, int nativeIndex) {
		super(x, y, world, nativeIndex);
		super.setLife(100);
		this.direction = 0;
		super.nativeAi = FindRoomAI.getInstance();
		int resistanceSelector = (int) (Math.random() * 4);
		switch (resistanceSelector) {

		case 1:
			resistance = new AcidResistance();
			break;
		case 2:
			resistance = new FireResistance();
			break;
		case 3:
			resistance = new ElectricResistance();
			break;
		case 4:
			resistance = new IceResistance();
			break;
		case 0:
		default:
			resistance = new NoResistance();
			break;
		}
		coolDownManager = new CoolDownManager(this, cooldownTime);
		coolDownManager.start();
	}

	@Override
	public void move(int direction) {
		super.move(direction);
		this.direction = direction;
		attack(attackPower);
	}

	@Override
	public int getCooldownTime() {
		return cooldownTime;
	}

	public int getDirection() {
		return direction;
	}

	@Override
	public int getResistance() {
		return resistance.getResistance();
	}

	public int getType() {
		return type;
	}

	@Override
	public void attack(int attackPower) {
		super.attack(attackPower);
		// if ( !this.cooldownManager.isAlive() )
		//
	}
}
