package it.mat.unical.Helion_Prime.GFX;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class InformationPanel extends JPanel {

	private JProgressBar progressBar;
	private JTextField moneyField;
	private JTextField roomField;
	private Font font;

	// private BufferedImage background;

	public InformationPanel() {

		font = MainMenuFrame.getInstance().getMainMenuPanel().getFont();
		// try {
		// background = ImageIO.read(new File("Resources/Immagine.png"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		setLayout(new FlowLayout());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(200, 30));
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
		add(progressBar);
		add(roomField);
		add(moneyField);
		setFocusable(false);

	}

	@Override
	protected void paintComponent(Graphics g) {

		// g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(),
		// this);
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
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
}
