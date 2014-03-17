package it.mat.unical.Helion_Prime.GFX;
import javax.print.attribute.standard.Media;

import it.mat.unical.Helion_Prime.LevelEditor.EditorMainPanel;
import it.mat.unical.Helion_Prime.LevelEditor.EditorOptionsPanel;
import it.mat.unical.Helion_Prime.LevelEditor.GridPanel;
import it.mat.unical.Helion_Prime.LevelEditor.LevelStruct;
import it.mat.unical.Helion_Prime.LevelEditor.MapElementsPanel;
import it.mat.unical.Helion_Prime.LevelEditor.OptionsPanel;
import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.SavesManager.H2DbManager;
import it.mat.unical.Helion_Prime.SavesManager.SaveManagerImpl;
import it.mat.unical.Helion_Prime.ScoreCharts.LoginInterface;
import it.mat.unical.Helion_Prime.ScoreCharts.LoginValidator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthMenuItemUI;

//frame principale contiene Il pannello di scelta di inizio gioco O editor

public class MainMenuFrame extends JFrame  {

	private static MainMenuFrame instance;
	
	private JPanel contentPanel;

	private MainMenuPanel mainMenuPanel;
		
	public static MainMenuFrame getInstance() {
		
		if ( instance == null ) {
			instance = new MainMenuFrame();
			System.out.println("instanzio il MainMenuFrame");
		}
			return instance;
	}
	
	private MainMenuFrame() {
	}
	
	public void start() {
		
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		contentPanel = new JPanel(new BorderLayout());
		add(contentPanel);
		
		contentPanel.setVisible(true);
		
		mainMenuPanel = new MainMenuPanel();
		switchTo(mainMenuPanel);
		
	}


	
	
	public void switchTo(final JPanel panel)
    {
        SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    contentPanel.removeAll();
                    contentPanel.add(panel, BorderLayout.CENTER);
                    contentPanel.updateUI();
                    panel.requestFocus(); 
                }
            });
    }
	
	

	
	public static void main(String[] args) {
		MainMenuFrame frame = MainMenuFrame.getInstance();
		frame.start();
	}

	public MainMenuPanel getMainMenuPanel() {
		return mainMenuPanel;
	}


	
}
