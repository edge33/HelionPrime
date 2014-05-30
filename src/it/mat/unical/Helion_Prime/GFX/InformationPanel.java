package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class InformationPanel extends JPanel {

	private JProgressBar progressBar;
	private JTextField moneyField;
	private JTextField roomField;
	private JTextField time;
	private Font font;
	private Image overlay;

	static int second;
	static int minute;
	static int hour;

	// private BufferedImage background;

	public InformationPanel() {

		try {
			overlay = ImageIO.read(new File(
					"Resources/Overlay/UpperOverlay.png"));
		} catch (IOException e) {
			System.out.println("UpperOverlay mancante");
		}

		second = 0;
		minute = 0;
		hour = 0;
		font = MainMenuFrame.getInstance().getMainMenuPanel().getFont();
		setLayout(new FlowLayout());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(900, 50));
		moneyField = new JTextField();
		moneyField.setEditable(false);
		moneyField.setFont(font);
		moneyField.setFont(moneyField.getFont().deriveFont(17.0f));
		moneyField.setBackground(Color.black);
		moneyField.setForeground(Color.green);
		moneyField.setBorder(null);
		roomField = new JTextField();
		roomField.setEditable(false);
		roomField.setFont(font);
		roomField.setFont(roomField.getFont().deriveFont(17.0f));
		roomField.setBackground(Color.black);
		roomField.setForeground(Color.yellow);
		roomField.setBorder(null);
		progressBar = new JProgressBar(0, 100);
		progressBar.setBackground(Color.BLACK);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBorderPainted(false);

		time = new JTextField();
		time.setEditable(false);
		time.setFont(font);
		time.setFont(roomField.getFont().deriveFont(17.0f));
		time.setBackground(Color.black);
		time.setForeground(Color.cyan);
		time.setText("0:0:0");
		time.setBorder(null);

		add(progressBar);
		add(roomField);
		add(moneyField);
		add(time);
		setFocusable(false);

	}

	@Override
	protected void paintComponent(Graphics g) {

		// g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(),
		// this);
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(overlay, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	public void setLife(int lifePoints) {
		progressBar.setValue(lifePoints);
	}

	public void setMoney(int money) {
		String dinero = Integer.toString(money);
		moneyField.setText(dinero);
	}

	public void setRoomLife(int roomLife) {
		String life = Integer.toString(roomLife);
		if (roomLife > 0)
			roomField.setText(life);
		else
			roomField.setText("0");
	}

	public void startTime() {
		new Thread() {
			public void run() {

				while (!ClientManager.isFinishGame()) {
					InformationPanel.this.repaint();
					if (second == 60) {
						second = 0;
						minute++;

					}

					if (minute == 60) {
						second = 0;
						minute = 0;
						hour++;

					}

					second++;

					time.setText(hour + ":" + minute + ":" + second + " ");

					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};

		}.start();

	}

	public static String getTime() {
		// TODO Auto-generated method stub
		return hour + ":" + minute + ":" + second;
	}
}
