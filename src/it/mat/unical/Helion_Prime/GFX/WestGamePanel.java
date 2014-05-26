package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WestGamePanel extends JPanel {
	private JButton backButton;
	private ClientManager clientManager;

	// private GameManagerImpl manager;

	public WestGamePanel() {
		// this.manager = GameManagerImpl.getInstance(id);
		this.addListener();
		this.createButton();
		this.add(backButton);
		CustomBorder b1 = new CustomBorder(Color.GREEN, 10);
		this.setBorder(b1);
	}

	public void createButton() {
		backButton.setBackground(Color.black);
		backButton.setForeground(Color.green);
		backButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		backButton.setFont(backButton.getFont().deriveFont(16.0f));
		backButton.setBorderPainted(false);
		backButton.setFocusPainted(false);
	}

	public void setClientManager(ClientManager clientManager) {

		this.clientManager = clientManager;
	}

	public void addListener() {
		this.backButton = new JButton("Main Menu"); // bottone che permette di
		// torna al back precedente
		setFocusable(true);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ClientManager.setFinishGame(true);
				try {
					clientManager.sendAllFinish();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				MainMenuFrame.getInstance().switchTo(
						MainMenuFrame.getInstance().getMainMenuPanel());
			}
		});
	}
}
