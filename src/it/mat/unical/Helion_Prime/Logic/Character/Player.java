package it.mat.unical.Helion_Prime.Logic.Character;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.HeavyWeapon;
import it.mat.unical.Helion_Prime.Logic.RangedWeapon;
import it.mat.unical.Helion_Prime.Logic.RifleGun;
import it.mat.unical.Helion_Prime.Logic.SimpleGun;
import it.mat.unical.Helion_Prime.Logic.UziGun;
import it.mat.unical.Helion_Prime.Logic.World;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractFactoryTrap;
import it.mat.unical.Helion_Prime.Logic.Trap.AbstractTrap;
import it.mat.unical.Helion_Prime.Logic.Trap.TrapPlacing;
import it.mat.unical.Helion_Prime.Logic.Trap.TrapPower;
import it.mat.unical.Helion_Prime.SavesManager.PlayerSaveState;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends AbstractCharacter implements TrapPlacing {

	// private StaticObject[][] WorldOfPlayer=super.getWorld().getWorld();
	// //aggiutno matrice di static object per piazzare le trappole sirettamente
	// sulla matrice;

	private int score;
	private int money;
	private int direction;
	private boolean hasPlacedTrap = false;
	private boolean shooter = false, shoot = false, threadAlive = false;
	private RangedWeapon currentGunSelected;
	private int bonusPoints;
	private World world;
	private Collection weapon = null;
	private ArrayList<RangedWeapon> army = new ArrayList<RangedWeapon>();
	private HashMap<RangedWeapon, Integer> bulletsArmy = new HashMap<RangedWeapon, Integer>();
	private ConcurrentHashMap<Point, AbstractTrap> placedTrap = new ConcurrentHashMap<Point, AbstractTrap>(); // array
	// delle
	// trappole
	// piazzate
	// ci
	// serve
	// poi
	// per
	// la
	// stampa
	// in
	// modo
	// tale
	// da
	// non
	// intaccare
	// fisicamente
	// la
	// mappa

	private AbstractFactoryTrap factoryTrap; // E
												// AabstractFactoryTrap
												// �
												// una
												// classe
												// che
												// restituisce
												// in
												// base
												// al
												// tipo
												// inserito
												// una
												// nuova
												// istanza
												// della
												// trappola
												// specificata
												// con
												// le
												// posizioni
												// x
												// e
												// y
												// ci
												// servira
												// per
												// poi
												// inserirle
												// nella
												// array
												// "placedTrap"
	private int id;

	public Player(int x, int y, World world, int id) {
		super(x, y, world);

		this.id = id;

		if (PlayerSaveState.getInstance().isSet())
			this.money = PlayerSaveState.getInstance().getScore();
		else {
			this.money = 1000;
		}

		factoryTrap = new AbstractFactoryTrap(id);
		// per ora assumiamo che abbiamo 10 tipi di
		// trappole parte la revisionare
		this.world = world;
		this.setBonusPoints(100);
		army.add(new SimpleGun(this.world));
		army.add(new UziGun(this.world));
		army.add(new RifleGun(this.world));
		army.add(new HeavyWeapon(this.world));

		bulletsArmy.put(army.get(0), 0);
		bulletsArmy.put(army.get(1), 0);
		bulletsArmy.put(army.get(2), 0);
		bulletsArmy.put(army.get(3), 0);

		currentGunSelected = army.get(0);

	}

	@Override
	public int getLife() {
		return super.getLife();
	}

	@Override
	public boolean canPlaceTrap(int numberOfTrapInArray, int positionXonMap,
			int positionYonMap) {

		// questa chiamata restituisce la trappola di tipo numberOfTrapInArray,
		if (world.getElementAt(positionXonMap, positionYonMap) != null)
			return false;

		AbstractTrap newTrap = null;
		if (numberOfTrapInArray != 6) {
			newTrap = factoryTrap.returnTrapForType(positionXonMap,
					positionYonMap, numberOfTrapInArray);
		}

		if (newTrap.getCost() <= GameManagerImpl.getInstance(id).getMoney()) { // prendo
																				// il
																				// costo
																				// della
																				// trappola
																				// che
			// sto per piazzare e lo contronto
			// con i soldi che ho

			Point pointOnMap = new Point(positionXonMap, positionYonMap);

			placedTrap.put(pointOnMap, newTrap);

			if (GameManagerImpl.getInstance(id).isMultiplayerGame()) {
				GameManagerImpl.getInstance(id).getTotalPlacedTrap()
						.put(pointOnMap, newTrap);
			}

			this.world.getWorld()[positionXonMap][positionYonMap] = placedTrap
					.get(pointOnMap);
			// setto
			// sulla
			// mappa
			// aggiungo la nuova trappola
			// nella
			// struttura delle trappole piazzate dal
			// player
			GameManagerImpl.getInstance(this.id).dimMoney(newTrap.getCost());
			if (newTrap instanceof TrapPower)
				((TrapPower) newTrap).start();
			// System.out.println("questa trappola costa " + newTrap.getCost());
			this.hasPlacedTrap = true;
			return true;
		} else {
			// System.err
			// .println("ATTENZIONE NON HAI ABBASTANZA SOLDI PER QUELLA RAPPOLA");
			// // SYSERR
			return false; // PROVISORIO
		}

	}

	// incremento lo score
	public void setScore(int newScore) {
		this.score += newScore;
	}

	public HashMap<RangedWeapon, Integer> getBulletsArmy() {
		return bulletsArmy;
	}

	@Override
	public void setX(int x) {
		super.setX(x);
	}

	@Override
	public void setY(int y) {
		super.setY(y);
	}

	@Override
	public int getX() {
		return super.getX();
	}

	@Override
	public int getY() {
		return super.getY();
	}

	// contiene tutte le armi a disposizione del player
	public Collection getWeapon() {
		return weapon;
	}

	public void SwitchGun(int TypeArmy) {
		currentGunSelected = army.get(TypeArmy);
		// System.out.println("Hai selezionato l'arma " + TypeArmy);

	}

	// restituisce tutte le trappole a disposizione del player
	@Override
	public ConcurrentHashMap<Point, AbstractTrap> getTrap() {
		return placedTrap;
	}

	// muove il player
	@Override
	public void move(int direction) {
		super.move(direction);
	}

	// restituisce i crediti acquisiti dal player
	public int getMoney() {
		return money;
	}

	// imposta i life points del player
	@Override
	public void setLife(int lifePoints) {
		super.setLife(lifePoints);
	}

	// restituisce la trappola piazzata con indice "index"
	@Override
	public AbstractTrap getTrapFromArray(int index) {
		return placedTrap.get(index);
	}

	// restuituisce true se almeno una trappola � stata piazzata
	@Override
	public boolean hasPlacedTrap() {
		return hasPlacedTrap;
	}

	// restituisce il numero di bonusPoints
	public int getBonusPoints() {
		return bonusPoints;
	}

	// fissa il numero di bonusPoints
	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	// dovrebbe sparare, aggiunge un bullet nella lista di bullets
	public Integer shoot() {

		return currentGunSelected.shoot(this.world, this);

	}

	public RangedWeapon getCurrentGunSelected() {
		return currentGunSelected;
	}

	// restituisce la lista di bullets

	@Override
	public boolean isAlive() {
		return super.isAlive();
	}

	public void shootForUziGun(final int player) {
		if (getCurrentGunSelected() instanceof UziGun) {

			if (!threadAlive) {
				new Thread() {

					public void run() {
						threadAlive = true;
						for (int i = 0; i < 5
								&& !GameManagerImpl.getInstance(id)
										.gameIsOver()
								&& !GameManagerImpl.getInstance(id)
										.isGameStopped(); i++) {

							if (!GameManagerImpl.getInstance(id)
									.isMultiplayerGame()) {
								GameManagerImpl
										.getInstance(id)
										.getServer()
										.sendMessage(
												"sh "
														+ String.valueOf(shoot()
																+ " ")
														+ getDirection()
														+ " "
														+ getArmy()
																.indexOf(
																		getCurrentGunSelected()));
							} else {

								int key = shoot();
								if (player == 1) {
									GameManagerImpl
											.getInstance(id)
											.getServerMuliplayer()
											.outToClientOne(
													"sh "
															+ String.valueOf(key)
															+ " 1 "
															+ getDirection()
															+ " "
															+ getArmy()
																	.indexOf(
																			getCurrentGunSelected()));

									GameManagerImpl
											.getInstance(id)
											.getServerMuliplayer()
											.outToClientTwo(
													"sh "
															+ String.valueOf(key)
															+ " 2 "
															+ getDirection()
															+ " "
															+ getArmy()
																	.indexOf(
																			getCurrentGunSelected()));
								} else {
									GameManagerImpl
											.getInstance(id)
											.getServerMuliplayer()
											.outToClientOne(
													"sh "
															+ String.valueOf(key)
															+ " 2 "
															+ getDirection()
															+ " "
															+ getArmy()
																	.indexOf(
																			getCurrentGunSelected()));

									GameManagerImpl
											.getInstance(id)
											.getServerMuliplayer()
											.outToClientTwo(
													"sh "
															+ String.valueOf(key)
															+ " 1 "
															+ getDirection()
															+ " "
															+ getArmy()
																	.indexOf(
																			getCurrentGunSelected()));

								}

							}

							try {
								sleep(100);
							} catch (InterruptedException e) {

								e.printStackTrace();
							}

						}
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// System.out.println("esco da thread shoot");
						threadAlive = false;

					}
				}.start();
			}

		}
	}

	public ArrayList<RangedWeapon> getArmy() {
		return army;
	}

	public int getId() {
		return this.id;
	}
}
