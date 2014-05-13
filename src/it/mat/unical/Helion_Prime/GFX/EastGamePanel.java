package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.RangedWeapon;
import it.mat.unical.Helion_Prime.Online.ClientManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EastGamePanel extends JPanel
{
	private ArrayList<RangedWeapon> army;
	
	private JLabel titleLabel;
	private JLabel bulletsGun1Desc;
	private JLabel bulletsGun2Desc;
	private JLabel bulletsGun3Desc;
	private JLabel bulletsGun4Desc;
	private JLabel bulletsGun1;
	private JLabel bulletsGun2;
	private JLabel bulletsGun3;
	private JLabel bulletsGun4;
	private JLabel score;
	private JLabel scoreDescr;	

	private GridBagLayout eastLayout;
	private GridBagConstraints eC;

	
	public EastGamePanel()
	{
		this.eastLayout = new GridBagLayout();
		this.eC = new GridBagConstraints();
		this.eC.fill = GridBagConstraints.BOTH;
		this.eC.weightx = 1.0;
		this.setLayout(eastLayout);
		this.setBackground(Color.black);
		this.titleLabel = new JLabel("Bullet:", JLabel.CENTER);
		this.bulletsGun1Desc = new JLabel(" Gun 1:");
		this.bulletsGun2Desc = new JLabel(" Gun 2:");
		this.bulletsGun3Desc = new JLabel(" Gun 3:");
		this.bulletsGun4Desc = new JLabel(" Gun 4:");
		this.score = new JLabel("0");
		this.bulletsGun1 = new JLabel("0");
		this.bulletsGun2 = new JLabel("0");
		this.bulletsGun3 = new JLabel("0");
		this.bulletsGun4 = new JLabel("0");
		this.createButton();
		this.getInfo();
		this.fillPanel();
		CustomBorder b1 = new CustomBorder(Color.GREEN, 10);
		this.setBorder(b1);
		
	}
	
	public void createButton()
	{
		titleLabel.setForeground(Color.green);
		titleLabel.setOpaque(false);
		titleLabel.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		titleLabel.setFont(titleLabel.getFont().deriveFont(25.0f));

		bulletsGun1Desc.setForeground(Color.green);
		bulletsGun1Desc.setOpaque(false);
		bulletsGun1Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun1Desc.setFont(bulletsGun1Desc.getFont().deriveFont(16.0f));

		bulletsGun2Desc.setForeground(Color.green);
		bulletsGun2Desc.setOpaque(false);
		bulletsGun2Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun2Desc.setFont(bulletsGun2Desc.getFont().deriveFont(16.0f));

		bulletsGun3Desc.setForeground(Color.green);
		bulletsGun3Desc.setOpaque(false);
		bulletsGun3Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun3Desc.setFont(bulletsGun3Desc.getFont().deriveFont(16.0f));

		bulletsGun4Desc.setForeground(Color.green);
		bulletsGun4Desc.setOpaque(false);
		bulletsGun4Desc.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun4Desc.setFont(bulletsGun4Desc.getFont().deriveFont(16.0f));

		bulletsGun1.setForeground(Color.green);
		bulletsGun1.setOpaque(false);
		bulletsGun1.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun1.setFont(bulletsGun1.getFont().deriveFont(10.0f));

		bulletsGun2.setForeground(Color.green);
		bulletsGun2.setOpaque(false);
		bulletsGun2.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun2.setFont(bulletsGun2.getFont().deriveFont(10.0f));

		bulletsGun3.setForeground(Color.green);
		bulletsGun3.setOpaque(false);
		bulletsGun3.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun3.setFont(bulletsGun3.getFont().deriveFont(10.0f));

		bulletsGun4.setForeground(Color.green);
		bulletsGun4.setOpaque(false);
		bulletsGun4.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		bulletsGun4.setFont(bulletsGun4.getFont().deriveFont(10.0f));

	}
	
	public void getInfo()
	{
		boolean multi =ClientManager.getInstance().isMultiplayerGame();
		if(!multi)
		{
			
		}
	}
	
	public void fillPanel()
	{
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(titleLabel, eC);
		this.add(titleLabel);

		this.eC.insets = new Insets(10,0,0,0); 
		this.eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun1Desc, eC);
		this.add(bulletsGun1Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun1, eC);
		this.add(bulletsGun1);
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun2Desc, eC);
		this.add(bulletsGun2Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun2, eC);
		this.add(bulletsGun2);
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun3Desc, eC);
		this.add(bulletsGun3Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun3, eC);
		this.add(bulletsGun3);
		eC.gridwidth = 1;

		this.eastLayout.setConstraints(bulletsGun4Desc, eC);
		this.add(bulletsGun4Desc);
		this.eC.gridwidth = GridBagConstraints.REMAINDER;
		this.eastLayout.setConstraints(bulletsGun4, eC);
		this.add(bulletsGun4);
		eC.gridwidth = 1;

		this.eC.insets = new Insets(10,0,0,0); 
		this.eC.gridwidth = 1;
	}
}
