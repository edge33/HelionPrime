package it.mat.unical.Helion_Prime.Logic;

import it.mat.unical.Helion_Prime.Logic.Character.AbstractNative;

import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;

public interface Wave {

	public ConcurrentHashMap getNatives();

	public void setNatives(ConcurrentHashMap<Integer, AbstractNative> natives);

	public void init() throws FileNotCorrectlyFormattedException,
			FileNotFoundException;

	public void initLite() throws FileNotCorrectlyFormattedException,
			FileNotFoundException;

	// Maida aggiungo un metodo per conoscere il numero di nativi rimasti

	public int getNativesNumber();

}
