package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;
import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

//pannello corrispondente alla parte a destra dove risiedono le trappole , successivamente saranno aggiunte anche le armi

public class TrapPanel extends JPanel {

	
	
	private JLabel spikeTrapIcon; // label delle icone
	private JLabel fireTrapIcon;
	private JLabel acidTrapIcon;
	private JLabel powerTrapIcon;
	private JLabel electricTrapIcon;
	private JLabel decoyTrapIcon;
	private Image disabledDecoyTrapIcon;
	private Image enabledDecoyTrapIcon;
	private Image overlay;

	private Border border;
	private Border greenBorder;

	private JLabel simpleGunLabel;
	private JLabel uziGunLabel;
	private JLabel shootGunLabel;
	private JLabel heavyWeaponLabel;
	private JLabel gapLabel;
	
	private boolean decoy = true;
	private int currentTrapSelected; // intero che serve a sapere che "tipo" di
	private int currentGunSelected; // trappola si ha selezionato dalle
	private GridBagLayout layout;
									// label
	private GridBagConstraints c;


	public TrapPanel()
	{
		this.layout = new GridBagLayout();
		this.c = new GridBagConstraints();
		this.c.fill = GridBagConstraints.BOTH;
		this.c.weightx = 1.0;
		this.setOpaque(false);
		this.border = BorderFactory.createLineBorder(Color.BLUE, 2);
		this.greenBorder = BorderFactory.createLineBorder(Color.GREEN, 2);
		this.setPreferredSize(new Dimension(900,60));
		setBackground(Color.BLACK);
		Image currentLabelImage = null;
		currentTrapSelected = 0;

		try {
			overlay = ImageIO.read(new File ("Resources/Overlay/Overlay.png"));
			currentLabelImage = ImageIO.read(new File("Resources/SpikeTrapIcon.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("SpikeTrapIcon Mancante");
		}

		spikeTrapIcon = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/FireTrapIcon.png")).getScaledInstance(
					40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("FireTrapIcon Mancante");
		}

		fireTrapIcon = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/AcidTrapIcon.png")).getScaledInstance(
					40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("FireTrapIcon Mancante");
		}

		acidTrapIcon = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/PowerTrapIcon.png")).getScaledInstance(
					40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("PowerTrapIcon Mancante");
		}

		powerTrapIcon = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/ElectricTrapIcon.png"))
					.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("ElectricTrapIcon Mancante");
		}

		electricTrapIcon = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/DecoyTrapIcon.png")).getScaledInstance(
					40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("DecoyTrapIcon  Mancante");
		}

		decoyTrapIcon = new JLabel(new ImageIcon(currentLabelImage));

		try {
			disabledDecoyTrapIcon = ImageIO.read(
					new File("Resources/DisabledDecoyTrapIcon.png"))
					.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("DisabledDecoyTrapIcon  Mancante");
		}

		try {
			enabledDecoyTrapIcon = ImageIO.read(
					new File("Resources/DecoyTrapIcon.png")).getScaledInstance(
					40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("DecoyTrapIcon  Mancante");
		}

		// ///////////////////////////////////////////////////////////////////////////////////
		
		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/Overlay/Gap.png"))
					.getScaledInstance(10, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("Gap Mancante");
		}
		
		gapLabel = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO
					.read(new File("Resources/guns/gun.png"))
					.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("Gun Mancante");
		}

		simpleGunLabel = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/guns/uziGun.png")).getScaledInstance(
					60, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("uziGun Mancante");
		}

		uziGunLabel = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/guns/shootGun.png")).getScaledInstance(
					60, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("shootGun Mancante");
		}

		shootGunLabel = new JLabel(new ImageIcon(currentLabelImage));

		try {
			currentLabelImage = ImageIO.read(
					new File("Resources/guns/HeavyWeapon.png"))
					.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("heavyWeapon Mancante");
		}


		heavyWeaponLabel = new JLabel(new ImageIcon(currentLabelImage));

		spikeTrapIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectSpikeTrap();
			}

		});

		fireTrapIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectFireTrap();
			}
		});

		acidTrapIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectAcidTrap();
			}
		});

		electricTrapIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectElectricTrap();
			}
		});

		decoyTrapIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (decoy == true)
					selectDecoyTrap();
			}
		});

		powerTrapIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectPowerTrap();
			}
		});

		simpleGunLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				selectGun();
				repaint();
			}
		});

		uziGunLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				selectUzi();
				repaint();
			}
		});

		shootGunLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				selectShootGun();
				repaint();
			}
		});

		heavyWeaponLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				selectHeavy();
				repaint();
			}
		});
		
		this.fillPanel();

		repaint();
	}
	
	public void fillPanel()
	{
		this.c.insets = new Insets(10,0,0,0); 
		this.c.gridwidth = 1;
		
		((FlowLayout)this.getLayout()).setVgap(11);
		this.add(spikeTrapIcon);
		this.add(fireTrapIcon);
		this.add(acidTrapIcon);
		this.add(electricTrapIcon);
		this.add(powerTrapIcon);
		this.add(decoyTrapIcon);
		
		this.add(gapLabel);
		
		this.add(simpleGunLabel);
		this.add(uziGunLabel);
		this.add(shootGunLabel);
		this.add(heavyWeaponLabel);

		simpleGunLabel.setBorder(greenBorder);
		spikeTrapIcon.setBorder(border);
	}

	public void selectHeavy() {
		if (currentGunSelected != 3) {
			// GameManagerImpl.getInstance().getPlayerOne().SwitchGun(3);
			heavyWeaponLabel.setBorder(greenBorder);
			shootGunLabel.setBorder(null);
			simpleGunLabel.setBorder(null);
			uziGunLabel.setBorder(null);
			currentGunSelected = 3;
		}
	}

	public void selectShootGun() {
		if (currentGunSelected != 2) {
			// GameManagerImpl.getInstance().getPlayerOne().SwitchGun(2);
			heavyWeaponLabel.setBorder(null);
			shootGunLabel.setBorder(greenBorder);
			simpleGunLabel.setBorder(null);
			uziGunLabel.setBorder(null);
			currentGunSelected = 2;
		}
	}

	public void selectUzi() {
		if (currentGunSelected != 1) {
			// GameManagerImpl.getInstance().getPlayerOne().SwitchGun(1);
			heavyWeaponLabel.setBorder(null);
			shootGunLabel.setBorder(null);
			simpleGunLabel.setBorder(null);
			uziGunLabel.setBorder(greenBorder);
			currentGunSelected = 1;
		}
	}

	public void selectGun() {
		if (currentGunSelected != 0) {
			// GameManagerImpl.getInstance().getPlayerOne().SwitchGun(0);
			heavyWeaponLabel.setBorder(null);
			shootGunLabel.setBorder(null);
			simpleGunLabel.setBorder(greenBorder);
			uziGunLabel.setBorder(null);
			currentGunSelected = 0;
		}
	}

	public void selectSpikeTrap() {
		TrapPanel.this.currentTrapSelected = 0;
		spikeTrapIcon.setBorder(border);
		acidTrapIcon.setBorder(null);
		fireTrapIcon.setBorder(null);
		electricTrapIcon.setBorder(null);
		powerTrapIcon.setBorder(null);
		decoyTrapIcon.setBorder(null);
	}

	public void selectFireTrap() {
		TrapPanel.this.currentTrapSelected = 1;
		fireTrapIcon.setBorder(border);
		acidTrapIcon.setBorder(null);
		powerTrapIcon.setBorder(null);
		electricTrapIcon.setBorder(null);
		spikeTrapIcon.setBorder(null);
		decoyTrapIcon.setBorder(null);
	}

	public void selectAcidTrap() {
		TrapPanel.this.currentTrapSelected = 3;
		acidTrapIcon.setBorder(border);
		powerTrapIcon.setBorder(null);
		fireTrapIcon.setBorder(null);
		electricTrapIcon.setBorder(null);
		spikeTrapIcon.setBorder(null);
		decoyTrapIcon.setBorder(null);
	}

	public void selectPowerTrap() {
		TrapPanel.this.currentTrapSelected = 5;
		powerTrapIcon.setBorder(border);
		acidTrapIcon.setBorder(null);
		fireTrapIcon.setBorder(null);
		electricTrapIcon.setBorder(null);
		spikeTrapIcon.setBorder(null);
		decoyTrapIcon.setBorder(null);
	}

	public void selectElectricTrap() {
		TrapPanel.this.currentTrapSelected = 4;
		electricTrapIcon.setBorder(border);
		acidTrapIcon.setBorder(null);
		fireTrapIcon.setBorder(null);
		powerTrapIcon.setBorder(null);
		spikeTrapIcon.setBorder(null);
		decoyTrapIcon.setBorder(null);
	}

	public void selectDecoyTrap() {
		TrapPanel.this.currentTrapSelected = 6;
		decoyTrapIcon.setBorder(border);
		electricTrapIcon.setBorder(null);
		acidTrapIcon.setBorder(null);
		fireTrapIcon.setBorder(null);
		powerTrapIcon.setBorder(null);
		spikeTrapIcon.setBorder(null);
	}

	public int getCurrentTrapSelected() {
		return currentTrapSelected;
	}

	public void setCurrentTrapSelected(int a) {
		currentTrapSelected = a;
	}

	public int getCurrentGunSelected() {
		return currentGunSelected;
	}

	public void disableDecoy() {
		decoy = false;
		decoyTrapIcon.setBorder(null);
		decoyTrapIcon.setFocusable(false);
		decoyTrapIcon.setIcon(new ImageIcon(disabledDecoyTrapIcon));
		spikeTrapIcon.setBorder(border);
	}

	public void enableDecoy() {
		decoy = true;
		decoyTrapIcon.setIcon(new ImageIcon(enabledDecoyTrapIcon));
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(overlay, 0, 0, this.getWidth(), this.getHeight(), this);
	}


}
