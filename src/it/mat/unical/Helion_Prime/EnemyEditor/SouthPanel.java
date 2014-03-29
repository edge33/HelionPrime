package it.mat.unical.Helion_Prime.EnemyEditor;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SouthPanel extends JPanel {
	
 private JButton backButton;
 private Font font;
 
 public SouthPanel(Font font)
 {		
	 	this.font=font;
	 	this.setOpaque(true);
		this.setBackground(new Color(0,0,0,64));
		this.setCursor(MainMenuFrame.getInstance().getMainMenuPanel().getCursor());
		backButton =  new JButton("Main Menu");
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("Button.select",Color.black);
				MainMenuFrame.getInstance().switchTo(MainMenuFrame.getInstance().getMainMenuPanel());
			}
		});
		backButton.setBackground(Color.black);
		backButton.setForeground(Color.green);
		backButton.setFont(font);
		backButton.setFont(backButton.getFont().deriveFont(20.0f));
		backButton.setBorderPainted(false);
		backButton.setFocusPainted(false);
		
		add(backButton);
		setVisible(true);
	}
	
 
}
