package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.MaintenanceRoom;
import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Ability.Resistance;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractTrap;

import java.awt.Point;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractNative extends AbstractCharacter implements Resistance {

	private final int key;
	private final int type;
	private MaintenanceRoom room;
	private ConcurrentHashMap<Point, AbstractTrap> currentTrap;
	protected NativeAI nativeAi;
	protected int attackPower;
	protected Player player;
	private AbstractTrap trapToFind;
	private boolean canAttack;
	protected Thread coolDownManager;
	protected int currentPosition = 1;
	protected boolean firstMove = false;
	private int id;

	public AbstractNative(int x, int y, World world, int key, int id) {
		super(x, y, world);
		this.id = id;
		this.key = key;
		trapToFind = null;
		this.setDirection(-1);
		this.canAttack = true;
		this.room = (MaintenanceRoom) world.getRoom();

		if (!GameManagerImpl.getInstance(id).isMultiplayerGame())
			this.player = GameManagerImpl.getInstance(id).getPlayerOne();
		else {
			Random random = new Random();
			int playerOneOrTwo = random.nextInt(2);

			if (playerOneOrTwo == 0) {
				this.player = GameManagerImpl.getInstance(id).getPlayerOne();
			} else
				this.player = GameManagerImpl.getInstance(id).getPlayerTwo();

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

		if (canAttack
				&& ((player.getX() == getX() - 1 && player.getY() == getY())
						|| (player.getX() == getX() + 1 && player.getY() == getY())
						|| (player.getX() == getX() && player.getY() == getY() - 1) || (player
						.getX() == getX() && player.getY() == getY() + 1))) {
			player.setLife(player.getLife() - attackPower);
			System.out.println("dopo attacco " + player.getLife());
			setAttack(false);
			if (GameManagerImpl.getInstance(id).isMultiplayerGame())
				GameManagerImpl.getInstance(id).getServerMuliplayer()
						.outBroadcast("life " + player.getLife());

		}

		else if (canAttack
				&& ((room.getX() == getX() - 1 && room.getY() == getY())
						|| (room.getX() == getX() + 1 && room.getY() == getY())
						|| (room.getX() == getX() && room.getY() == getY() - 1) || (room
						.getX() == getX() && room.getY() == getY() + 1))) {
			room.setLife(room.getLife() - attackPower);
			System.out.println("room dopo attacco " + room.getLife());
			world.setRoomLife(room.getLife());
			setAttack(false);
			if (GameManagerImpl.getInstance(id).isMultiplayerGame())
				GameManagerImpl.getInstance(id).getServerMuliplayer()
						.outBroadcast("lifeRoom " + room.getLife());
		}

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
		if (nativeAi.getAiType() == NativeAI.PLAYER_FINDER)
			move = nativeAi.getDirection(this, player, world);
		else if (nativeAi.getAiType() == NativeAI.ROOM_FINDER)
			move = nativeAi.getDirection(this, world.getRoom(), world);
		else if (nativeAi.getAiType() == NativeAI.TRAP_FINDER) {

			if (GameManagerImpl.getInstance(id).isMultiplayerGame())
				currentTrap = GameManagerImpl.getInstance(id)
						.getTotalPlacedTrap();
			else
				currentTrap = player.getTrap();
			if (currentTrap.size() > 0) {

				if (trapToFind == null)
					for (Point pointTrap : currentTrap.keySet()) {

						if (!currentTrap.get(pointTrap).isCaptured()) {
							trapToFind = currentTrap.get(pointTrap);
							currentTrap.get(pointTrap).setCaptured(true);
							move = nativeAi.getDirection(this,
									currentTrap.get(pointTrap), world);
							break;
						}

						if (trapToFind == null)
							move = nativeAi.getDirection(this, player, world);

					}
				else
					move = nativeAi.getDirection(this, trapToFind, world);

				// System.out.println(((AbstractTrap) tmp.toArray()[0]).getX()
				// + " " + ((AbstractTrap) tmp.toArray()[0]).getY());

			} else {
				move = nativeAi.getDirection(this, player, world);
			}
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

	public int getAttackPower() {
		return attackPower;
	}

	public AbstractTrap getTrapToFind() {
		return trapToFind;
	}

	public void setTrapToFind(AbstractTrap trapToFind) {
		this.trapToFind = trapToFind;
	}

}
