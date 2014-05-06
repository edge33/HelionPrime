package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;
import it.mat.unical.Helion_Prime.Logic.Character.BountyHunterNative;
import it.mat.unical.Helion_Prime.Logic.Character.SaboteurNative;
import it.mat.unical.Helion_Prime.Logic.Character.SoldierNative;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class WaveImpl implements Wave {

	private ConcurrentHashMap<Integer, AbstractNative> natives;
	private ConcurrentHashMap<Integer, AbstractNativeLite> nativesLite;
	private World world;
	private boolean add;
	private StaticObject nativeSpawner;
	private StaticObject playerSpawner;
	private int nativeIndex;
	private File fileWave;

	public WaveImpl(World world, File level, boolean b)
			throws FileNotCorrectlyFormattedException, FileNotFoundException {

		this.fileWave = new File("waves/" + level.getName());

		this.world = world;
		this.natives = new ConcurrentHashMap<Integer, AbstractNative>();

		// ci prendiamo le coordinate da dove dovranno uscire i Nativi
		this.nativeSpawner = world.getNativeSpawner();
		this.playerSpawner = world.getPlayerSpawner();

		this.nativeIndex = 0;

		if (b) {
			this.natives = new ConcurrentHashMap<Integer, AbstractNative>();
			this.init();

		} else {
			this.nativesLite = new ConcurrentHashMap<Integer, AbstractNativeLite>();
			initLite();

		}
	}

	@Override
	public void init() throws FileNotFoundException {

		int soldierNumber = 0;
		int bountyNumber = 0;
		int saboteurNumber = 0;
		int typeOfNative = 0;

		Scanner scanner = new Scanner(fileWave);

		while (scanner.hasNextLine()) {
			typeOfNative = scanner.nextInt();
			switch (typeOfNative) {
			case 0:
				soldierNumber = scanner.nextInt();
				// System.out.println("Ho istanziato " + soldierNumber +
				// " soldati ");
				break;
			case 1:
				bountyNumber = scanner.nextInt();
				// System.out.println("Ho istanziato " + bountyNumber +
				// " cacciatori ");
				break;
			case 2:
				saboteurNumber = scanner.nextInt();
				// System.out.println("Ho istanziato " + saboteurNumber +
				// " saboteur ");
				break;
			}
		}
		scanner.close();

		if (soldierNumber != 0)
			for (int i = 0; i < soldierNumber; i++) {
				this.natives.put(
						nativeIndex,
						new SoldierNative(nativeSpawner.getX(), nativeSpawner
								.getY(), world, nativeIndex++));
			}// soldier for

		if (bountyNumber != 0)
			for (int i = 0; i < bountyNumber; i++) {
				this.natives.put(nativeIndex, new BountyHunterNative(
						nativeSpawner.getX(), nativeSpawner.getY(), world,
						nativeIndex++));
			}// bounty for

		if (saboteurNumber != 0)
			for (int i = 0; i < saboteurNumber; i++) {
				this.natives.put(
						nativeIndex,
						new SaboteurNative(nativeSpawner.getX(), nativeSpawner
								.getY(), world, nativeIndex++));
			}// saboteur for

	}

	@Override
	public ConcurrentHashMap getNatives() {
		return this.natives;
	}

	@Override
	public void setNatives(ConcurrentHashMap<Integer, AbstractNative> natives) {
		this.natives = natives;

	}

	@Override
	public int getNativesNumber() {
		return this.natives.size();
	}

	@Override
	public void initLite() throws FileNotCorrectlyFormattedException,
			FileNotFoundException {

		int soldierNumber = 0;
		int bountyNumber = 0;
		int saboteurNumber = 0;
		int typeOfNative = 0;

		Scanner scanner = new Scanner(fileWave);

		while (scanner.hasNextLine()) {
			typeOfNative = scanner.nextInt();
			switch (typeOfNative) {
			case 0:
				soldierNumber = scanner.nextInt();
				// System.out.println("Ho istanziato " + soldierNumber +
				// " soldati ");
				break;
			case 1:
				bountyNumber = scanner.nextInt();
				// System.out.println("Ho istanziato " + bountyNumber +
				// " cacciatori ");
				break;
			case 2:
				saboteurNumber = scanner.nextInt();
				// System.out.println("Ho istanziato " + saboteurNumber +
				// " saboteur ");
				break;
			}
		}
		scanner.close();

		if (soldierNumber != 0)
			for (int i = 0; i < soldierNumber; i++) {
				this.nativesLite.put(nativeIndex, new AbstractNativeLite(
						nativeSpawner.getX(), nativeSpawner.getY(),
						nativeIndex++, 0));
			}// soldier for

		if (bountyNumber != 0)
			for (int i = 0; i < bountyNumber; i++) {
				this.nativesLite.put(nativeIndex, new AbstractNativeLite(
						nativeSpawner.getX(), nativeSpawner.getY(),
						nativeIndex++, 1));
			}// bounty for

		if (saboteurNumber != 0)
			for (int i = 0; i < saboteurNumber; i++) {
				this.nativesLite.put(nativeIndex, new AbstractNativeLite(
						nativeSpawner.getX(), nativeSpawner.getY(),
						nativeIndex++, 2));
			}// saboteur for

	}

	public ConcurrentHashMap<Integer, AbstractNativeLite> getNativesLite() {
		return nativesLite;
	}

	public void setNativesLite(
			ConcurrentHashMap<Integer, AbstractNativeLite> nativesLite) {
		this.nativesLite = nativesLite;
	}

}
