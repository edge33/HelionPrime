package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JLabel;

//semplice classe che carica le immaggini dal toolkit e le restituisce al paintComponent del "GamePane"
// Last id 119

public class ImageProvider extends Thread {

	private final Image bullet;

	private final Image wall;
	private final Image flippedWall;
	private final Image spawn;
	private final Image enemyS;
	private final Image floor;
	private final Image floor2;
	private final Image room;

	private final Image playerStanding;

	private final Image playerUpRunning;
	private final Image playerUpRunning2;
	private final Image playerUpRunning3;
	private final Image playerUpRunning4;

	private final Image playerDownRunning;
	private final Image playerDownRunning2;
	private final Image playerDownRunning3;
	private final Image playerDownRunning4;

	private final Image playerRightRunning;
	private final Image playerRightRunning2;
	private final Image playerRightRunning3;
	private final Image playerRightRunning4;

	private final Image playerLeftRunning;
	private final Image playerLeftRunning2;
	private final Image playerLeftRunning3;
	private final Image playerLeftRunning4;

	private final Image spikeTrap;
	private final Image acidTrap;
	private final Image fireTrap;
	private final Image electricTrap;
	private final Image powerTrap;
	private final Image decoyTrap;

	private Image imageSwitchedStanding;
	private Image imageSwitchedUpRunning;
	private Image imageSwitchedDownRunning;
	private Image imageSwitchedRightRunning;
	private Image imageSwitchedLeftRunning;

	private Image hunterDownRunning1;
	private Image hunterDownRunning2;
	private Image hunterDownRunning3;

	private Image hunterUpRunning1;
	private Image hunterUpRunning2;
	private Image hunterUpRunning3;

	private Image hunterLeftRunning1;
	private Image hunterLeftRunning2;
	private Image hunterLeftRunning3;

	private Image hunterRightRunning1;
	private Image hunterRightRunning2;
	private Image hunterRightRunning3;

	private Image saboteurDownRunning1;
	private Image saboteurDownRunning2;
	private Image saboteurDownRunning3;

	private Image saboteurUpRunning1;
	private Image saboteurUpRunning2;
	private Image saboteurUpRunning3;

	private Image saboteurLeftRunning1;
	private Image saboteurLeftRunning2;
	private Image saboteurLeftRunning3;

	private Image saboteurRightRunning1;
	private Image saboteurRightRunning2;
	private Image saboteurRightRunning3;

	private Image nativeDownRunning1;
	private Image nativeDownRunning2;
	private Image nativeDownRunning3;

	private Image nativeUpRunning1;
	private Image nativeUpRunning2;
	private Image nativeUpRunning3;

	private Image nativeLeftRunning1;
	private Image nativeLeftRunning2;
	private Image nativeLeftRunning3;

	private Image nativeRightRunning1;
	private Image nativeRightRunning2;
	private Image nativeRightRunning3;

	private Image nativeFireDownRunning1;
	private Image nativeFireDownRunning2;
	private Image nativeFireDownRunning3;

	private Image nativeFireUpRunning1;
	private Image nativeFireUpRunning2;
	private Image nativeFireUpRunning3;

	private Image nativeFireLeftRunning1;
	private Image nativeFireLeftRunning2;
	private Image nativeFireLeftRunning3;

	private Image nativeFireRightRunning1;
	private Image nativeFireRightRunning2;
	private Image nativeFireRightRunning3;

	private Image nativeAcidDownRunning1;
	private Image nativeAcidDownRunning2;
	private Image nativeAcidDownRunning3;

	private Image nativeAcidUpRunning1;
	private Image nativeAcidUpRunning2;
	private Image nativeAcidUpRunning3;

	private Image nativeAcidLeftRunning1;
	private Image nativeAcidLeftRunning2;
	private Image nativeAcidLeftRunning3;

	private Image nativeAcidRightRunning1;
	private Image nativeAcidRightRunning2;
	private Image nativeAcidRightRunning3;

	private Image nativeElectricDownRunning1;
	private Image nativeElectricDownRunning2;
	private Image nativeElectricDownRunning3;

	private Image nativeElectricUpRunning1;
	private Image nativeElectricUpRunning2;
	private Image nativeElectricUpRunning3;

	private Image nativeElectricLeftRunning1;
	private Image nativeElectricLeftRunning2;
	private Image nativeElectricLeftRunning3;

	private Image nativeElectricRightRunning1;
	private Image nativeElectricRightRunning2;
	private Image nativeElectricRightRunning3;

	private Image nativeIceDownRunning1;
	private Image nativeIceDownRunning2;
	private Image nativeIceDownRunning3;

	private Image nativeIceUpRunning1;
	private Image nativeIceUpRunning2;
	private Image nativeIceUpRunning3;

	private Image nativeIceLeftRunning1;
	private Image nativeIceLeftRunning2;
	private Image nativeIceLeftRunning3;

	private Image nativeIceRightRunning1;
	private Image nativeIceRightRunning2;
	private Image nativeIceRightRunning3;

	private final Image i8;
	private final Image i9;
	private final Image i10;
	private final Image i11;

	private static int currentRunningUpPlayer = 0;
	private static int currentRunningDownPlayer = 0;
	private static int currentRunningRightPlayer = 0;
	private static int currentRunningLeftPlayer = 0;

	private static int enemyType;
	private static int direction;
	private static int currentPosition;
	private static int enemyResistance = 5;
	private Image back;

	public ImageProvider() {
		final Toolkit toolKit = Toolkit.getDefaultToolkit();

		this.bullet = toolKit.getImage("Resources/pallottoloBIll.png");

		this.wall = toolKit.getImage("Resources/wall100x100.jpg");
		this.room = toolKit.getImage("Resources/room100x100.jpg");
		this.floor = toolKit.getImage("Resources/floor100x100.png");
		this.floor2 = toolKit.getImage("Resources/floor2100x100.png");
		this.spawn = toolKit.getImage("Resources/spawn100x100.png");
		this.enemyS = toolKit.getImage("Resources/eSpawn100x100.jpg");
		this.flippedWall = toolKit.getImage("Resources/wallF100x100.jpg");

		this.playerStanding = toolKit
				.getImage("Resources/Char Resources/FilippoStand.gif");

		this.playerUpRunning = toolKit
				.getImage("Resources/Char Resources/FilippoFront1.png");
		this.playerUpRunning2 = toolKit
				.getImage("Resources/Char Resources/FilippoFront2.png");
		this.playerUpRunning3 = toolKit
				.getImage("Resources/Char Resources/FilippoFront3.png");
		this.playerUpRunning4 = toolKit
				.getImage("Resources/Char Resources/FilippoFront2.png");

		this.playerDownRunning = toolKit
				.getImage("Resources/Char Resources/FilippoBack1.png");
		this.playerDownRunning2 = toolKit
				.getImage("Resources/Char Resources/FilippoBack2.png");
		this.playerDownRunning3 = toolKit
				.getImage("Resources/Char Resources/FilippoBack3.png");
		this.playerDownRunning4 = toolKit
				.getImage("Resources/Char Resources/FilippoBack2.png");

		this.playerRightRunning = toolKit
				.getImage("Resources/Char Resources/FilippoRight2.png");
		this.playerRightRunning2 = toolKit
				.getImage("Resources/Char Resources/FilippoRight2.png");
		this.playerRightRunning3 = toolKit
				.getImage("Resources/Char Resources/FilippoRight3.png");
		this.playerRightRunning4 = toolKit
				.getImage("Resources/Char Resources/FilippoRight2.png");

		this.playerLeftRunning = toolKit
				.getImage("Resources/Char Resources/FilippoLeft1.png");
		this.playerLeftRunning2 = toolKit
				.getImage("Resources/Char Resources/FilippoLeft2.png");
		this.playerLeftRunning3 = toolKit
				.getImage("Resources/Char Resources/FilippoLeft3.png");
		this.playerLeftRunning4 = toolKit
				.getImage("Resources/Char Resources/FilippoLeft2.png");

		this.hunterDownRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboBack1.png");
		this.hunterDownRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboBack2.png");
		this.hunterDownRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboBack3.png");

		this.hunterUpRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboFront1.png");
		this.hunterUpRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboFront2.png");
		this.hunterUpRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboFront3.png");

		this.hunterLeftRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboLeft1.png");
		this.hunterLeftRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboLeft2.png");
		this.hunterLeftRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboLeft3.png");

		this.hunterRightRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboRight1.png");
		this.hunterRightRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboRight2.png");
		this.hunterRightRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboRight3.png");

		this.saboteurDownRunning1 = toolKit
				.getImage("Resources/Random/RandomBack1.png");
		this.saboteurDownRunning2 = toolKit
				.getImage("Resources/Random/RandomBack2.png");
		this.saboteurDownRunning3 = toolKit
				.getImage("Resources/Random/RandomBack3.png");

		this.saboteurUpRunning1 = toolKit
				.getImage("Resources/Random/RandomFront1.png");
		this.saboteurUpRunning2 = toolKit
				.getImage("Resources/Random/RandomFront2.png");
		this.saboteurUpRunning3 = toolKit
				.getImage("Resources/Random/RandomFront3.png");

		this.saboteurLeftRunning1 = toolKit
				.getImage("Resources/Random/RandomLeft1.png");
		this.saboteurLeftRunning2 = toolKit
				.getImage("Resources/Random/RandomLeft2.png");
		this.saboteurLeftRunning3 = toolKit
				.getImage("Resources/Random/RandomLeft3.png");

		this.saboteurRightRunning1 = toolKit
				.getImage("Resources/Random/RandomRight1.png");
		this.saboteurRightRunning2 = toolKit
				.getImage("Resources/Random/RandomRight2.png");
		this.saboteurRightRunning3 = toolKit
				.getImage("Resources/Random/RandomRight3.png");

		this.nativeDownRunning1 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayBack1.png");
		this.nativeDownRunning2 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayBack2.png");
		this.nativeDownRunning3 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayBack3.png");

		this.nativeUpRunning1 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayFront1.png");
		this.nativeUpRunning2 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayFront2.png");
		this.nativeUpRunning3 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayFront3.png");

		this.nativeLeftRunning1 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayLeft1.png");
		this.nativeLeftRunning2 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayLeft2.png");
		this.nativeLeftRunning3 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayLeft3.png");

		this.nativeRightRunning1 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayRight1.png");
		this.nativeRightRunning2 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayRight2.png");
		this.nativeRightRunning3 = toolKit
				.getImage("Resources/Robo/Gray/RoboGrayRight3.png");

		this.nativeFireDownRunning1 = toolKit
				.getImage("Resources/Robo/Red/RoboRedBack1.png");
		this.nativeFireDownRunning2 = toolKit
				.getImage("Resources/Robo/Red/RoboRedBack2.png");
		this.nativeFireDownRunning3 = toolKit
				.getImage("Resources/Robo/Red/RoboRedBack3.png");

		this.nativeFireUpRunning1 = toolKit
				.getImage("Resources/Robo/Red/RoboRedFront1.png");
		this.nativeFireUpRunning2 = toolKit
				.getImage("Resources/Robo/Red/RoboRedFront2.png");
		this.nativeFireUpRunning3 = toolKit
				.getImage("Resources/Robo/Red/RoboRedFront3.png");

		this.nativeFireLeftRunning1 = toolKit
				.getImage("Resources/Robo/Red/RoboRedLeft1.png");
		this.nativeFireLeftRunning2 = toolKit
				.getImage("Resources/Robo/Red/RoboRedLeft2.png");
		this.nativeFireLeftRunning3 = toolKit
				.getImage("Resources/Robo/Red/RoboRedLeft3.png");

		this.nativeFireRightRunning1 = toolKit
				.getImage("Resources/Robo/Red/RoboRedRight1.png");
		this.nativeFireRightRunning2 = toolKit
				.getImage("Resources/Robo/Red/RoboRedRight2.png");
		this.nativeFireRightRunning3 = toolKit
				.getImage("Resources/Robo/Red/RoboRedRight3.png");

		this.nativeElectricDownRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboBack1.png");
		this.nativeElectricDownRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboBack2.png");
		this.nativeElectricDownRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboBack3.png");

		this.nativeElectricUpRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboFront1.png");
		this.nativeElectricUpRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboFront2.png");
		this.nativeElectricUpRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboFront3.png");

		this.nativeElectricLeftRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboLeft1.png");
		this.nativeElectricLeftRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboLeft2.png");
		this.nativeElectricLeftRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboLeft3.png");

		this.nativeElectricRightRunning1 = toolKit
				.getImage("Resources/Robo/Yellow/RoboRight1.png");
		this.nativeElectricRightRunning2 = toolKit
				.getImage("Resources/Robo/Yellow/RoboRight2.png");
		this.nativeElectricRightRunning3 = toolKit
				.getImage("Resources/Robo/Yellow/RoboRight3.png");

		this.nativeAcidDownRunning1 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenBack1.png");
		this.nativeAcidDownRunning2 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenBack2.png");
		this.nativeAcidDownRunning3 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenBack3.png");

		this.nativeAcidUpRunning1 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenFront1.png");
		this.nativeAcidUpRunning2 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenFront2.png");
		this.nativeAcidUpRunning3 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenFront3.png");

		this.nativeAcidLeftRunning1 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenLeft1.png");
		this.nativeAcidLeftRunning2 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenLeft2.png");
		this.nativeAcidLeftRunning3 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenLeft3.png");

		this.nativeAcidRightRunning1 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenRight1.png");
		this.nativeAcidRightRunning2 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenRight2.png");
		this.nativeAcidRightRunning3 = toolKit
				.getImage("Resources/Robo/Green/RoboGreenRight3.png");

		this.nativeIceDownRunning1 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueBack1.png");
		this.nativeIceDownRunning2 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueBack2.png");
		this.nativeIceDownRunning3 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueBack3.png");

		this.nativeIceUpRunning1 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueFront1.png");
		this.nativeIceUpRunning2 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueFront2.png");
		this.nativeIceUpRunning3 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueFront3.png");

		this.nativeIceLeftRunning1 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueLeft1.png");
		this.nativeIceLeftRunning2 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueLeft2.png");
		this.nativeIceLeftRunning3 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueLeft3.png");

		this.nativeIceRightRunning1 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueRight1.png");
		this.nativeIceRightRunning2 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueRight2.png");
		this.nativeIceRightRunning3 = toolKit
				.getImage("Resources/Robo/Blue/RoboBlueRight3.png");

		this.spikeTrap = toolKit.getImage("Resources/SpikeTrap.png");
		this.fireTrap = toolKit.getImage("Resources/FireTrap.png");
		this.acidTrap = toolKit.getImage("Resources/AcidTrap.png");
		this.electricTrap = toolKit.getImage("Resources/ElectricTrap.png");
		this.powerTrap = toolKit.getImage("Resources/PowerTrap.png");
		this.decoyTrap = toolKit.getImage("Resources/Char Resources/FilippoBack2.png");
		this.i8 = toolKit.getImage("Resources/8.jpg");
		this.i9 = toolKit.getImage("Resources/9.jpg");
		this.i10 = toolKit.getImage("Resources/10.jpg");
		this.i11 = toolKit.getImage("Resources/11.jpg");

		final MediaTracker tracker = new MediaTracker(new JLabel());

		tracker.addImage(bullet, 63);

		tracker.addImage(wall, 0);
		tracker.addImage(flippedWall, 12);
		tracker.addImage(floor, 1);
		tracker.addImage(floor2, 62);
		tracker.addImage(spawn, 71);
		tracker.addImage(acidTrap, 64);
		tracker.addImage(fireTrap, 65);
		tracker.addImage(spikeTrap, 66);
		tracker.addImage(electricTrap, 67);
		tracker.addImage(powerTrap, 68);
		tracker.addImage(decoyTrap, 69);

		tracker.addImage(playerStanding, 2);

		tracker.addImage(playerUpRunning, 4);
		tracker.addImage(playerUpRunning2, 5);
		tracker.addImage(playerUpRunning3, 12);
		tracker.addImage(playerUpRunning4, 23);

		tracker.addImage(playerDownRunning, 13);
		tracker.addImage(playerDownRunning2, 14);
		tracker.addImage(playerDownRunning3, 15);
		tracker.addImage(playerDownRunning4, 24);

		tracker.addImage(playerRightRunning, 16);
		tracker.addImage(playerRightRunning2, 17);
		tracker.addImage(playerRightRunning3, 18);
		tracker.addImage(playerRightRunning3, 25);

		tracker.addImage(playerLeftRunning, 19);
		tracker.addImage(playerLeftRunning2, 20);
		tracker.addImage(playerLeftRunning3, 21);
		tracker.addImage(playerLeftRunning4, 26);

		tracker.addImage(hunterDownRunning1, 27);
		tracker.addImage(hunterDownRunning2, 22);
		tracker.addImage(hunterDownRunning3, 28);

		tracker.addImage(hunterUpRunning1, 29);
		tracker.addImage(hunterUpRunning2, 30);
		tracker.addImage(hunterUpRunning3, 31);

		tracker.addImage(hunterLeftRunning1, 32);
		tracker.addImage(hunterLeftRunning2, 33);
		tracker.addImage(hunterLeftRunning3, 34);

		tracker.addImage(hunterRightRunning1, 35);
		tracker.addImage(hunterRightRunning2, 36);
		tracker.addImage(hunterRightRunning3, 37);

		tracker.addImage(nativeDownRunning1, 38);
		tracker.addImage(nativeDownRunning2, 39);
		tracker.addImage(nativeDownRunning3, 40);

		tracker.addImage(nativeUpRunning1, 41);
		tracker.addImage(nativeUpRunning2, 42);
		tracker.addImage(nativeUpRunning3, 43);

		tracker.addImage(nativeLeftRunning1, 44);
		tracker.addImage(nativeLeftRunning2, 45);
		tracker.addImage(nativeLeftRunning3, 46);

		tracker.addImage(nativeRightRunning1, 47);
		tracker.addImage(nativeRightRunning2, 48);
		tracker.addImage(nativeRightRunning3, 49);

		tracker.addImage(nativeFireDownRunning1, 72);
		tracker.addImage(nativeFireDownRunning2, 73);
		tracker.addImage(nativeFireDownRunning3, 74);

		tracker.addImage(nativeFireUpRunning1, 75);
		tracker.addImage(nativeFireUpRunning2, 76);
		tracker.addImage(nativeFireUpRunning3, 77);

		tracker.addImage(nativeFireLeftRunning1, 78);
		tracker.addImage(nativeFireLeftRunning2, 79);
		tracker.addImage(nativeFireLeftRunning3, 80);

		tracker.addImage(nativeFireRightRunning1, 81);
		tracker.addImage(nativeFireRightRunning2, 82);
		tracker.addImage(nativeFireRightRunning3, 83);

		tracker.addImage(nativeAcidDownRunning1, 84);
		tracker.addImage(nativeAcidDownRunning2, 85);
		tracker.addImage(nativeAcidDownRunning3, 86);

		tracker.addImage(nativeAcidUpRunning1, 87);
		tracker.addImage(nativeAcidUpRunning2, 88);
		tracker.addImage(nativeAcidUpRunning3, 89);

		tracker.addImage(nativeAcidLeftRunning1, 90);
		tracker.addImage(nativeAcidLeftRunning2, 91);
		tracker.addImage(nativeAcidLeftRunning3, 92);

		tracker.addImage(nativeAcidRightRunning1, 93);
		tracker.addImage(nativeAcidRightRunning2, 94);
		tracker.addImage(nativeAcidRightRunning3, 95);

		tracker.addImage(nativeElectricDownRunning1, 96);
		tracker.addImage(nativeElectricDownRunning2, 97);
		tracker.addImage(nativeElectricDownRunning3, 98);

		tracker.addImage(nativeElectricUpRunning1, 99);
		tracker.addImage(nativeElectricUpRunning2, 100);
		tracker.addImage(nativeElectricUpRunning3, 101);

		tracker.addImage(nativeElectricLeftRunning1, 102);
		tracker.addImage(nativeElectricLeftRunning2, 103);
		tracker.addImage(nativeElectricLeftRunning3, 104);

		tracker.addImage(nativeElectricRightRunning1, 105);
		tracker.addImage(nativeElectricRightRunning2, 106);
		tracker.addImage(nativeElectricRightRunning3, 107);

		tracker.addImage(nativeIceDownRunning1, 108);
		tracker.addImage(nativeIceDownRunning2, 109);
		tracker.addImage(nativeIceDownRunning3, 110);

		tracker.addImage(nativeIceUpRunning1, 111);
		tracker.addImage(nativeIceUpRunning2, 112);
		tracker.addImage(nativeIceUpRunning3, 113);

		tracker.addImage(nativeIceLeftRunning1, 114);
		tracker.addImage(nativeIceLeftRunning2, 115);
		tracker.addImage(nativeIceLeftRunning3, 116);

		tracker.addImage(nativeIceRightRunning1, 117);
		tracker.addImage(nativeIceRightRunning2, 118);
		tracker.addImage(nativeIceRightRunning3, 119);

		tracker.addImage(saboteurDownRunning1, 50);
		tracker.addImage(saboteurDownRunning2, 51);
		tracker.addImage(saboteurDownRunning3, 52);

		tracker.addImage(saboteurUpRunning1, 53);
		tracker.addImage(saboteurUpRunning2, 54);
		tracker.addImage(saboteurUpRunning3, 55);

		tracker.addImage(saboteurLeftRunning1, 56);
		tracker.addImage(saboteurLeftRunning2, 57);
		tracker.addImage(saboteurLeftRunning3, 58);

		tracker.addImage(saboteurRightRunning1, 59);
		tracker.addImage(saboteurRightRunning2, 60);
		tracker.addImage(saboteurRightRunning3, 61);

		tracker.addImage(room, 6);
		tracker.addImage(enemyS, 7);
		tracker.addImage(i8, 8);
		tracker.addImage(i9, 9);
		tracker.addImage(i10, 10);
		tracker.addImage(i11, 11);
		imageSwitchedStanding = playerStanding;
		this.start();

		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Image getRoom() {
		return room;
	}

	public Image getWall() {
		return wall;
	}

	public Image getFloor() {
		return floor;
	}

	public Image getFloor2() {
		return floor2;
	}

	public Image getSpawn() {
		return spawn;
	}

	public Image getSpikeTrap() {
		return spikeTrap;
	}

	public Image getFireTrap() {
		return fireTrap;
	}

	public Image getAcidTrap() {
		return acidTrap;
	}

	public Image getElectricTrap() {
		return electricTrap;
	}

	public Image getPowerTrap() {
		return powerTrap;
	}

	public Image getDecoyTrap() {
		return decoyTrap;
	}

	public Image getPlayerStanding() {
		return imageSwitchedStanding;
	}

	public Image getPlayerUpRunning() {
		return imageSwitchedUpRunning;
	}

	public Image getPlayerDownRunning() {
		return imageSwitchedDownRunning;
	}

	public Image getPlayerRightRunning() {
		return imageSwitchedRightRunning;
	}

	public Image getPlayerLeftRunning() {
		return imageSwitchedLeftRunning;
	}

	public Image getHunterDownRunning1() {
		return hunterDownRunning1;
	}

	public Image getHunterDownRunning2() {
		return hunterDownRunning2;
	}

	public Image getHunterDownRunning3() {
		return hunterDownRunning3;
	}

	public Image getHunterUpRunning1() {
		return hunterUpRunning1;
	}

	public Image getHunterUpRunning2() {
		return hunterUpRunning2;
	}

	public Image getHunterUpRunning3() {
		return hunterUpRunning3;
	}

	public Image getHunterLeftRunning1() {
		return hunterLeftRunning1;
	}

	public Image getHunterLeftRunning2() {
		return hunterLeftRunning2;
	}

	public Image getHunterLeftRunning3() {
		return hunterLeftRunning3;
	}

	public Image getHunterRightRunning1() {
		return hunterRightRunning1;
	}

	public Image getHunterRightRunning2() {
		return hunterRightRunning2;
	}

	public Image getHunterRightRunning3() {
		return hunterRightRunning3;
	}

	public Image getNativeDownRunning1() {
		if (enemyResistance == 0)
			return nativeDownRunning1;
		else if (enemyResistance == 1)
			return nativeAcidDownRunning1;
		else if (enemyResistance == 2)
			return nativeFireDownRunning1;
		else if (enemyResistance == 3)
			return nativeElectricDownRunning1;
		else if (enemyResistance == 4)
			return nativeIceDownRunning1;

		return nativeDownRunning1;
	}

	public Image getNativeDownRunning2() {
		if (enemyResistance == 0)
			return nativeDownRunning2;
		else if (enemyResistance == 1)
			return nativeAcidDownRunning2;
		else if (enemyResistance == 2)
			return nativeFireDownRunning2;
		else if (enemyResistance == 3)
			return nativeElectricDownRunning2;
		else if (enemyResistance == 4)
			return nativeIceDownRunning2;

		return nativeDownRunning2;
	}

	public Image getNativeDownRunning3() {
		if (enemyResistance == 0)
			return nativeDownRunning3;
		else if (enemyResistance == 1)
			return nativeAcidDownRunning3;
		else if (enemyResistance == 2)
			return nativeFireDownRunning3;
		else if (enemyResistance == 3)
			return nativeElectricDownRunning3;
		else if (enemyResistance == 4)
			return nativeIceDownRunning3;

		return nativeDownRunning3;
	}

	public Image getNativeUpRunning1() {
		if (enemyResistance == 0)
			return nativeUpRunning1;
		else if (enemyResistance == 1)
			return nativeAcidUpRunning1;
		else if (enemyResistance == 2)
			return nativeFireUpRunning1;
		else if (enemyResistance == 3)
			return nativeElectricUpRunning1;
		else if (enemyResistance == 4)
			return nativeIceUpRunning1;

		return nativeUpRunning1;
	}

	public Image getNativeUpRunning2() {
		if (enemyResistance == 0)
			return nativeUpRunning2;
		else if (enemyResistance == 1)
			return nativeAcidUpRunning2;
		else if (enemyResistance == 2)
			return nativeFireUpRunning2;
		else if (enemyResistance == 3)
			return nativeElectricUpRunning2;
		else if (enemyResistance == 4)
			return nativeIceUpRunning2;

		return nativeUpRunning2;
	}

	public Image getNativeUpRunning3() {
		if (enemyResistance == 0)
			return nativeUpRunning3;
		else if (enemyResistance == 1)
			return nativeAcidUpRunning3;
		else if (enemyResistance == 2)
			return nativeFireUpRunning3;
		else if (enemyResistance == 3)
			return nativeElectricUpRunning3;
		else if (enemyResistance == 4)
			return nativeIceUpRunning3;

		return nativeUpRunning3;
	}

	public Image getNativeLeftRunning1() {
		if (enemyResistance == 0)
			return nativeLeftRunning1;
		else if (enemyResistance == 1)
			return nativeAcidLeftRunning1;
		else if (enemyResistance == 2)
			return nativeFireLeftRunning1;
		else if (enemyResistance == 3)
			return nativeElectricLeftRunning1;
		else if (enemyResistance == 4)
			return nativeIceLeftRunning1;

		return nativeLeftRunning1;
	}

	public Image getNativeLeftRunning2() {
		if (enemyResistance == 0)
			return nativeLeftRunning2;
		else if (enemyResistance == 1)
			return nativeAcidLeftRunning2;
		else if (enemyResistance == 2)
			return nativeFireLeftRunning2;
		else if (enemyResistance == 3)
			return nativeElectricLeftRunning2;
		else if (enemyResistance == 4)
			return nativeIceLeftRunning2;

		return nativeLeftRunning2;
	}

	public Image getNativeLeftRunning3() {
		if (enemyResistance == 0)
			return nativeLeftRunning3;
		else if (enemyResistance == 1)
			return nativeAcidLeftRunning3;
		else if (enemyResistance == 2)
			return nativeFireLeftRunning3;
		else if (enemyResistance == 3)
			return nativeElectricLeftRunning3;
		else if (enemyResistance == 4)
			return nativeIceLeftRunning3;

		return nativeLeftRunning3;
	}

	public Image getNativeRightRunning1() {
		if (enemyResistance == 0)
			return nativeRightRunning1;
		else if (enemyResistance == 1)
			return nativeAcidRightRunning1;
		else if (enemyResistance == 2)
			return nativeFireRightRunning1;
		else if (enemyResistance == 3)
			return nativeElectricRightRunning1;
		else if (enemyResistance == 4)
			return nativeIceRightRunning1;

		return nativeRightRunning1;
	}

	public Image getNativeRightRunning2() {
		if (enemyResistance == 0)
			return nativeRightRunning2;
		else if (enemyResistance == 1)
			return nativeAcidRightRunning2;
		else if (enemyResistance == 2)
			return nativeFireRightRunning2;
		else if (enemyResistance == 3)
			return nativeElectricRightRunning2;
		else if (enemyResistance == 4)
			return nativeIceRightRunning2;

		return nativeRightRunning2;
	}

	public Image getNativeRightRunning3() {
		if (enemyResistance == 0)
			return nativeRightRunning3;
		else if (enemyResistance == 1)
			return nativeAcidRightRunning3;
		else if (enemyResistance == 2)
			return nativeFireRightRunning3;
		else if (enemyResistance == 3)
			return nativeElectricRightRunning3;
		else if (enemyResistance == 4)
			return nativeIceRightRunning3;

		return nativeRightRunning3;
	}

	public Image getSaboteurDownRunning1() {
		return saboteurDownRunning1;
	}

	public Image getSaboteurDownRunning2() {
		return saboteurDownRunning2;
	}

	public Image getSaboteurDownRunning3() {
		return saboteurDownRunning3;
	}

	public Image getSaboteurUpRunning1() {
		return saboteurUpRunning1;
	}

	public Image getSaboteurUpRunning2() {
		return saboteurUpRunning2;
	}

	public Image getSaboteurUpRunning3() {
		return saboteurUpRunning3;
	}

	public Image getSaboteurLeftRunning1() {
		return saboteurLeftRunning1;
	}

	public Image getSaboteurLeftRunning2() {
		return saboteurLeftRunning2;
	}

	public Image getSaboteurLeftRunning3() {
		return saboteurLeftRunning3;
	}

	public Image getSaboteurRightRunning1() {
		return saboteurRightRunning1;
	}

	public Image getSaboteurRightRunning2() {
		return saboteurRightRunning2;
	}

	public Image getSaboteurRightRunning3() {
		return saboteurRightRunning3;
	}

	public Image getEnemyS() {
		return enemyS;
	}

	public Image getFlippedWall() {
		return flippedWall;
	}

	public Image getI8() {
		return i8;
	}

	public Image getI9() {
		return i9;
	}

	public Image getI10() {
		return i10;
	}

	public Image getI11() {
		return i11;
	}

	public Image getBullet() {
		return bullet;
	}

	public Image getCorrectNative(AbstractNative currentNative) {
		enemyType = currentNative.getType();
		direction = currentNative.getDirection();
		currentPosition = currentNative.getCurrentPosition();
		if (enemyType == 0) {
			enemyResistance = currentNative.getResistance();
			// System.out.println(enemyResistance);
		}
		switch (enemyType) {
		case 0: {
			switch (direction) {
			case 0:
				if (currentNative.getCurrentPosition() == 1) {
					back = getNativeUpRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getNativeUpRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getNativeUpRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getNativeUpRunning2();

				}
				break;
			case 1:
				if (currentNative.getCurrentPosition() == 1) {
					back = getNativeDownRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getNativeDownRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getNativeDownRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getNativeDownRunning2();

				}
				break;
			case 2:
				if (currentNative.getCurrentPosition() == 1) {
					back = getNativeRightRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getNativeRightRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getNativeRightRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getNativeRightRunning2();

				}
				break;
			case 3:
				if (currentNative.getCurrentPosition() == 1) {
					back = getNativeLeftRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getNativeLeftRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getNativeLeftRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getNativeLeftRunning2();

				}
				break;
			}
		}
			break;
		case 1: {
			switch (direction) {
			case 0:
				if (currentNative.getCurrentPosition() == 1) {
					back = getHunterUpRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getHunterUpRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getHunterUpRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getHunterUpRunning2();

				}
				break;
			case 1:
				if (currentNative.getCurrentPosition() == 1) {
					back = getHunterDownRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getHunterDownRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getHunterDownRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getHunterDownRunning2();

				}
				break;
			case 2:
				if (currentNative.getCurrentPosition() == 1) {
					back = getHunterRightRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getHunterRightRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getHunterRightRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getHunterRightRunning2();

				}
				break;
			case 3:
				if (currentNative.getCurrentPosition() == 1) {
					back = getHunterLeftRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getHunterLeftRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getHunterLeftRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getHunterLeftRunning2();

				}
				break;
			}
		}
			break;

		case 2: {
			switch (direction) {
			case 0:
				if (currentNative.getCurrentPosition() == 1) {
					back = getSaboteurUpRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getSaboteurUpRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getSaboteurUpRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getSaboteurUpRunning2();

				}
				break;
			case 1:
				if (currentNative.getCurrentPosition() == 1) {
					back = getSaboteurDownRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getSaboteurDownRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getSaboteurDownRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getSaboteurDownRunning2();

				}
				break;
			case 2:
				if (currentNative.getCurrentPosition() == 1) {
					back = getSaboteurRightRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getSaboteurRightRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getSaboteurRightRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getSaboteurRightRunning2();

				}
				break;
			case 3:
				if (currentNative.getCurrentPosition() == 1) {
					back = getSaboteurLeftRunning1();

				} else if (currentNative.getCurrentPosition() == 2) {
					back = getSaboteurLeftRunning2();

				} else if (currentNative.getCurrentPosition() == 3) {
					back = getSaboteurLeftRunning3();

				} else if (currentNative.getCurrentPosition() == 4) {
					back = getSaboteurLeftRunning2();

				}
				break;
			}
		}
			break;
		}
		enemyResistance = 5;
		return back;
	}

	@Override
	public void run() {
		while (true) {
			// thread che alterna le immagini per dare l'animazione al player

			while (GameManagerImpl.isPaused()) {
				System.out.println("Sono in pausa  - ImageProvider");
				GameManagerImpl.waitForCondition();
			}

			// //////////////////////////////////////////////

			if (ImageProvider.currentRunningUpPlayer == 0) {
				imageSwitchedUpRunning = playerUpRunning;
				currentRunningUpPlayer = 1;
			} else if (ImageProvider.currentRunningUpPlayer == 1) {
				imageSwitchedUpRunning = playerUpRunning2;
				currentRunningUpPlayer = 2;
			} else if (ImageProvider.currentRunningUpPlayer == 2) {
				imageSwitchedUpRunning = playerUpRunning3;
				currentRunningUpPlayer = 3;
			} else if (ImageProvider.currentRunningUpPlayer == 3) {
				imageSwitchedUpRunning = playerUpRunning4;
				currentRunningUpPlayer = 0;
			}// Up Running If-Block

			// //////////////////////////////////////////////

			if (ImageProvider.currentRunningDownPlayer == 0) {
				imageSwitchedDownRunning = playerDownRunning;
				currentRunningDownPlayer = 1;
			} else if (ImageProvider.currentRunningDownPlayer == 1) {
				imageSwitchedDownRunning = playerDownRunning2;
				currentRunningDownPlayer = 2;
			} else if (ImageProvider.currentRunningDownPlayer == 2) {
				imageSwitchedDownRunning = playerDownRunning3;
				currentRunningDownPlayer = 3;
			} else if (ImageProvider.currentRunningDownPlayer == 3) {
				imageSwitchedDownRunning = playerDownRunning4;
				currentRunningDownPlayer = 0;
			}// Down Running If-Block

			// ////////////////////////////////////////////////

			if (ImageProvider.currentRunningRightPlayer == 0) {
				imageSwitchedRightRunning = playerRightRunning;
				currentRunningRightPlayer = 1;
			} else if (ImageProvider.currentRunningRightPlayer == 1) {
				imageSwitchedRightRunning = playerRightRunning2;
				currentRunningRightPlayer = 2;
			} else if (ImageProvider.currentRunningRightPlayer == 2) {
				imageSwitchedRightRunning = playerRightRunning3;
				currentRunningRightPlayer = 3;
			} else if (ImageProvider.currentRunningRightPlayer == 3) {
				imageSwitchedRightRunning = playerRightRunning4;
				currentRunningRightPlayer = 0;
			}// Right Running If-Block

			// //////////////////////////////////////////////////

			if (ImageProvider.currentRunningLeftPlayer == 0) {
				imageSwitchedLeftRunning = playerLeftRunning;
				currentRunningLeftPlayer = 1;
			} else if (ImageProvider.currentRunningLeftPlayer == 1) {
				imageSwitchedLeftRunning = playerLeftRunning2;
				currentRunningLeftPlayer = 2;
			} else if (ImageProvider.currentRunningLeftPlayer == 2) {
				imageSwitchedLeftRunning = playerLeftRunning3;
				currentRunningLeftPlayer = 3;
			} else if (ImageProvider.currentRunningLeftPlayer == 3) {
				imageSwitchedLeftRunning = playerLeftRunning4;
				currentRunningLeftPlayer = 0;
			}// Left Running If-Block

			/*--------------------------------------------------------------------------------------*/

			try {
				// valore originario 350
				sleep(350);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
