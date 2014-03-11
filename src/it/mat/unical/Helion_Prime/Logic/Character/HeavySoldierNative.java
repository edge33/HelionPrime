package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Ability.AcidResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.ElectricResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.FireResistance;
import it.mat.unical.Helion_Prime.Logic.Ability.Resistance;

import java.util.Random;

public class HeavySoldierNative extends ArmoredSoldierNative {

	private int direction;
	private Resistance resistance;
	private final int attackPower = 20;
	private final int type;

	private final int cooldownTime = 5000;

	public HeavySoldierNative(int x, int y, World world, int nativeIndex) {
		super(x, y, world, nativeIndex);
		super.setLife(200);

		this.nativeAi = FindRoomAI.getInstance();

		this.direction = 0;
		type = new Random().nextInt(3); /*
										 * Il random decide il tipo di danno che
										 * assorbe
										 */
		switch (type) {
		case 0:
			resistance = new AcidResistance();
			break;
		case 1:
			resistance = new FireResistance();
			break;
		case 2:
			resistance = new ElectricResistance();
			break;
		}

	}

	public int move() {
		return new Random().nextInt(4);
	}

	@Override
	public int getCooldownTime() {
		return cooldownTime;
	}

}
