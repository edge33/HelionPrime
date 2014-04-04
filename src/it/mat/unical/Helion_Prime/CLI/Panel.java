package it.mat.unical.Helion_Prime.CLI;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Panel extends JPanel {

	private GameManagerImpl manager;
	private final Interface theInterface;

	public Panel(final GameManagerImpl manager, final Interface theInterface) {
		super();
		this.manager = manager;
		this.theInterface = theInterface;
		this.setLayout(new FlowLayout(FlowLayout.LEADING));

		this.setFocusable(true);

		final JButton trapButton = new JButton("Trappola");

		this.add(trapButton);

		trapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String a = JOptionPane
						.showInputDialog(
								null,
								"Dammi le coordinate e per ultimo il tipo della trappola che vuoi piazzare (0-1) ");

				Scanner scanner = new Scanner(a);
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				int TipoDiTrappola = scanner.nextInt();

				manager.placeTrap(x, y, TipoDiTrappola, manager.getPlayerOne());
				theInterface.draw();
				System.out.println("");
				manager.update();
				trapButton.setFocusable(false);

			}

		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent paramKeyEvent) {

				if (paramKeyEvent.getKeyCode() == KeyEvent.VK_UP) {
					manager.movePlayerOne(0);
					theInterface.draw();
					manager.update();
					System.out.println("");

				} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
					manager.movePlayerOne(1);
					theInterface.draw();
					manager.update();
					System.out.println("");

				} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
					manager.movePlayerOne(2);
					theInterface.draw();
					manager.update();
					System.out.println("");

				} else if (paramKeyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

					manager.movePlayerOne(3);
					theInterface.draw();
					manager.update();
					System.out.println("");
				}
			}
		});

	}

	@Override
	public void paintComponent(Graphics g) {
		// LA matrice la associamo al paint component
		// e con i due for e in base all oggetto che troviamo o al numero
		// disegniamo
		// con l oggetto graphics g

		super.paintComponent(g);
		// g.drawString("Prima", 50, 50);
		// g.drawLine(0,0,300, 300);
		// g.drawRect(10, 10, 100, 100);

	}
}
