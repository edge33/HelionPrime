package it.mat.unical.Helion_Prime.GFX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundTraker {
	private Integer integer;
	private static SoundTraker instance;
	private HashMap<Integer, Clip> track;
	private Clip tmp;
	private HashMap<Integer, AudioInputStream> audioStream;

	private SoundTraker() {

		track = new HashMap<Integer, Clip>();
		audioStream = new HashMap<Integer, AudioInputStream>();
		loadTrack();

	}

	public static SoundTraker getInstance() {
		if (instance == null) {
			instance = new SoundTraker();
		}

		return instance;
	}

	private void loadTrack() {

		try {

			ArrayList<String> nameSong = new ArrayList<>();

			nameSong.add("Ost/PlaceTrapPower.wav");
			nameSong.add("Ost/menuSong.wav");
			nameSong.add("Ost/uziGun.wav");
			nameSong.add("Ost/shoot.wav");
			nameSong.add("Ost/battleSong.wav");

			for (int i = 0; i < nameSong.size(); i++) {
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(new File(nameSong.get(i))
								.getAbsoluteFile());

				Clip clip = AudioSystem.getClip();

				clip.open(audioInputStream);

				audioStream.put(i, audioInputStream);
				track.put(i, clip);

			}

		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startClip(Integer integer) {

		// track.get(integer).stop();
		track.get(integer).setFramePosition(0);
		track.get(integer).start();

		// startThreadClip(track.get(integer));
	}

	public void stopClip(Integer integer) {
		track.get(integer).stop();

	}
}
