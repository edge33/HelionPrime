package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Logic.UserProfile;
import it.mat.unical.Helion_Prime.Online.Client;
import it.mat.unical.Helion_Prime.Online.Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class ContenitorPanel extends JLayeredPane {
	private static StoryPanel centerPanel;

	private UpperPanel upperPanel;
	private JPanel lowerPanel;
	private JButton back;
	private JPanel fillerPanelW;
	private JPanel fillerPanelE;
	private JButton startGameButton;
	private static Server server;
	private Cursor cursor;
	private PreviewPanel previewPaneL;
	private UserProfile profile;

	public ContenitorPanel() {

		this.previewPaneL = null;
		this.profile = new UserProfile("PROVA PROFILO");
		this.back = new JButton("Main Menu");
		this.startGameButton = new JButton("Start Level");

		this.lowerPanel = new JPanel();
		this.lowerPanel.setSize(400, 50);
		this.lowerPanel.add(startGameButton);
		this.lowerPanel.add(Box.createRigidArea(new Dimension(40, 40)));
		this.lowerPanel.add(back);
		this.lowerPanel.setBackground(Color.BLACK);

		createButton();
		addListener();

		this.upperPanel = new UpperPanel();
		this.upperPanel.setSize(400, 50);

		this.centerPanel = new StoryPanel(this);

		this.fillerPanelW = new JPanel();
		this.fillerPanelW.setBackground(Color.BLACK);

		this.fillerPanelE = new JPanel();
		this.fillerPanelE.setBackground(Color.BLACK);

		this.setSize(400, 400);
		this.setLayout(new BorderLayout());

		this.add(centerPanel, BorderLayout.CENTER);
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(lowerPanel, BorderLayout.SOUTH);
		this.add(fillerPanelW, BorderLayout.WEST);
		this.add(fillerPanelE, BorderLayout.EAST);

		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel()
				.getCursor();
		this.setCursor(cursor);
	}

	public void addListener() {

		this.back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuFrame.getInstance().switchTo(
						MainMenuFrame.getInstance().getMainMenuPanel());
			}
		});

		this.startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String choosenLevel = profile.getLevels().get(
						profile.getLastlevelComplete())
						+ ".txt";

				String name = "levels/" + choosenLevel;
				System.out.println("LevelSwitchPanel.LevelSwitchPanel    "
						+ name);
				File level = new File(name);
				System.out
						.println("------------------------------------------------");
				MainGamePanel mainGamePanel = null;

				if (!Server.isServerStarted)
					try {
						server = new Server(7777);
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				else {
					server = null;
					try {
						server = new Server(7777);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				server.start();
				GameManagerImpl.getInstance().setServer(server);
				Client client = new Client("localhost", false);
				client.sendMessage(choosenLevel);

				if (client.recieveMessage().equals("ready")) {

					System.out.println("SIAMO READY INIZIA IL GIOCO");
					mainGamePanel = new MainGamePanel(level, client,
							ContenitorPanel.this.profile);
					MainMenuFrame.getInstance().switchTo(mainGamePanel);
				}
				// try {
				// // mainGamePanel = new MainGamePanel(choosenLevel);
				// MainMenuFrame.getInstance().switchTo(mainGamePanel);
				// } catch (FileNotFoundException e1) {
				// JOptionPane.showMessageDialog(null,
				// "Wave per il livello selezionato, non presente!");
				// MainMenuFrame.getInstance().switchTo(LevelSwitchPanel.this);
				// }
			}
		});
	}

	public void createButton() {
		startGameButton.setBackground(Color.black);
		startGameButton.setForeground(Color.green);
		startGameButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel()
				.getFont());
		startGameButton.setFont(startGameButton.getFont().deriveFont(25.0f));
		startGameButton.setBorderPainted(false);
		startGameButton.setFocusPainted(false);
		startGameButton.setBorderPainted(false);

		back.setBackground(Color.black);
		back.setForeground(Color.green);
		back.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		back.setFont(back.getFont().deriveFont(25.0f));
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
	}

	public void setLevelName(String name) {
		name = name.replace(".txt", "");
		upperPanel.setLevelName(name);
		upperPanel.repaint();
		System.out.println(name);
	}

	public void showPanel(String name) {
		int frameWidth = MainMenuFrame.getInstance().getWidth();
		int frameHeight = MainMenuFrame.getInstance().getHeight();
		int prevPanelX;
		int prevPanelY;
		int x, y;

		if (frameWidth >= 300) {
			prevPanelX = (MainMenuFrame.getInstance().getWidth() - 200);
			x = (frameWidth - prevPanelX) / 2;
		} else {
			prevPanelX = frameHeight;
			x = 0;
		}

		if (frameHeight >= 300) {
			prevPanelY = (MainMenuFrame.getInstance().getHeight() - 200);
			y = (frameHeight - prevPanelY) / 2;
		} else {
			prevPanelY = frameHeight;
			y = 0;
		}

		System.out.println("Sto cliccando " + frameWidth);
		previewPaneL = new PreviewPanel(this, name);
		previewPaneL.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1,true));
		previewPaneL.setBounds(x, y, prevPanelX, prevPanelY);
		this.add(previewPaneL, BorderLayout.CENTER, new Integer(10));
		previewPaneL.setVisible(true);
		previewPaneL.repaint();
		previewPaneL.requestFocus();
		centerPanel.disableListener(true);
		startGameButton.setEnabled(false);
		back.setEnabled(false);

	}

	public void enableListener() {
		centerPanel.disableListener(false);
		startGameButton.setEnabled(true);
		back.setEnabled(true);

	}
}