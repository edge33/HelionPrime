package it.mat.unical.Helion_Prime.EnemyEditor;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class EnemyEditorPanel extends JPanel{
	
	private JButton backButton;

	public EnemyEditorPanel() {
		
		backButton =  new JButton("Main Menu");
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("Button.select",Color.black);
				MainMenuFrame.getInstance().switchTo(MainMenuFrame.getInstance().getMainMenuPanel());
			}
		});
		
		
		add(backButton);
		
	
		setVisible(true);
	}
	
	protected void paintComponent(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.green);
		g.drawString("Work in progress", getWidth()/2, getHeight()/2);
	}
}
