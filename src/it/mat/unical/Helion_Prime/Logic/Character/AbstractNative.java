package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.MaintenanceRoom;
import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Ability.AbilityInterface;
import it.mat.unical.Helion_Prime.Logic.Ability.Resistance;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractTrap;

import java.awt.Point;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractNative extends AbstractCharacter implements Resistance {

	private final int key;
	private final int type;
	private MaintenanceRoom room;
	private ConcurrentHashMap<Point, AbstractTrap> currentTrap;
	protected NativeAI nativeAi;
	protected Player player;
	private boolean canAttack;
	protected Thread coolDownManager;
	protected int currentPosition = 1;
	protected boolean firstMove = false;

	public AbstractNative(int x, int y, World world, int key) {
		super(x, y, world);
		this.key = key;
		this.setDirection(-1);
		this.canAttack = true;
		this.room = (MaintenanceRoom) world.getRoom();

		if (GameManagerImpl.getInstance().isMultiplayerGame()) {
			Random random = new Random();
			int oneOrTwo = random.nextInt(2);
			if (oneOrTwo == 0) {
				this.player = GameManagerImpl.getInstance().getPlayerOne();
			} else {
				this.player = GameManagerImpl.getInstance().getPlayerTwo();
			}

		} else {
			this.player = GameManagerImpl.getInstance().getPlayerOne();
		}

		type = 999;


	}

	public boolean getFisrtMove() {
		return this.firstMove;
	}

	public void setFisrtMove(boolean v) {
		this.firstMove = v;
	}

	public void attack(int attackPower) {


		// TODO: attacking method working on RN - Maida
		// System.out.println("Can-ATTACK" + canAttack);

		World innerWorld = player.getWorld();
		if (getX() == player.getX() && getY() == player.getY() && canAttack) {
			player.setLife(player.getLife() - attackPower);
			System.out.println("dopo attacco " + player.getLife());
			setAttack(false);
			if (GameManagerImpl.getInstance().isMultiplayerGame())
				GameManagerImpl.getInstance().getServerMuliplayer()
						.outBroadcast("life " + player.getLife());

		}

		else if (getX() == room.getX() && getY() == room.getY() && canAttack) {
			room.setLife(room.getLife() - attackPower);
			System.out.println("room dopo attacco " + room.getLife());
			world.setRoomLife(room.getLife());
			setAttack(false);
			if (GameManagerImpl.getInstance().isMultiplayerGame())
				GameManagerImpl.getInstance().getServerMuliplayer()
						.outBroadcast("lifeRoom " + room.getLife());
		}

	}

	private AbilityInterface ability = null;

	public AbilityInterface getAbility() {
		return ability;
	}

	@Override
	public int getResistance() {
		return 0;
	}

	public int getMove() { // Get move modifcata cosi da prevedere più ia;
							// basterà aggiungere le nuove chiamate al blocco di
							// if
							// può essere migliorata con la reflection
		int move = 0;
		if (nativeAi.getAiType() == 0)
			move = nativeAi.getDirectionFromPlayerAi(this, player, world);
		else if (nativeAi.getAiType() == 1)
			move = nativeAi
					.getDirectionFromRoomAi(this, world.getRoom(), world);
		else if (nativeAi.getAiType() == 2) {
			currentTrap = player.getTrap();
			if (currentTrap.size() > 0) {

				Collection<AbstractTrap> tmp = currentTrap.values();

				// System.out.println(((AbstractTrap) tmp.toArray()[0]).getY());

				move = nativeAi.getDirectionFromTrapAi(this, currentTrap
						.entrySet().iterator().next().getValue(), world);

				// System.out.println(((AbstractTrap) tmp.toArray()[0]).getX()
				// + " " + ((AbstractTrap) tmp.toArray()[0]).getY());

			} else
				move = nativeAi.getDirectionFromPlayerAi(this, player, world);

		}

		return move;
	}

	public int getKey() {
		return key;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

	public int getCooldownTime() {
		return 0;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public int getType() {
		return type;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public boolean isCanAttack() {
		return canAttack;
	}

	public void setAttack(boolean bool) {
		canAttack = bool;
	}
}
