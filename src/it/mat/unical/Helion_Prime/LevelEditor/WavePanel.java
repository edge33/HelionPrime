package it.mat.unical.Helion_Prime.LevelEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WavePanel extends JPanel{

	private JTextField bountyNumber;
	private JTextField soldierNumber;
	private JTextField saboteurNumber;

	private JButton bountyUp;
	private JButton soldierUp;
	private JButton saboteurUp;

	private JButton bountyDown;
	private JButton soldierDown;
	private JButton saboteurDown;
	
	private JLabel bountyPreview;
	private JLabel soldierPreview;
	private JLabel saboteurPreview;
	
	private JLabel bountyDescription;
	private JLabel soldierDescription;
	private JLabel saboteurDescription;
	
	private Image currentPreview;
	private int counter;

	public WavePanel()
	{
		bountyNumber = new JTextField();
		soldierNumber = new JTextField();
		saboteurNumber = new JTextField();

		bountyNumber.setText("0");
		soldierNumber.setText("0");
		saboteurNumber.setText("0");

		bountyUp = new JButton("+");
		soldierUp = new JButton("+");
		saboteurUp = new JButton("+");

		bountyDown= new JButton("-");
		soldierDown = new JButton("-");
		saboteurDown = new JButton("-");

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		createLabel();
		addListener();
		createButton();
		createGroup(layout);
	}
	
	public void addListener()
	{
		bountyPreview.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bountyDescription.setVisible(true);				
			}
			public void mouseExited(MouseEvent e) {
				bountyDescription.setVisible(false);
			}
		});
		
		soldierPreview.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				soldierDescription.setVisible(true);
			}
			public void mouseExited(MouseEvent e) {
				soldierDescription.setVisible(false);
			}
		});
		
		saboteurPreview.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				saboteurDescription.setVisible(true);
			}
			public void mouseExited(MouseEvent e) {
				saboteurDescription.setVisible(false);
			}
		});
		
		bountyDown.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent arg0) 
		    {
				counter = Integer.parseInt(bountyNumber.getText());
				counter --;
				if(counter<0)
			    bountyNumber.setText("0");
				else
				bountyNumber.setText(Integer.toString(counter));
			}
		});
		soldierDown.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent arg0) 
		    {
				counter = Integer.parseInt(soldierNumber.getText());
				counter --;
				if(counter<0)
			    soldierNumber.setText("0");
				else
				soldierNumber.setText(Integer.toString(counter));
			}
		});
		
		saboteurDown.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent arg0) 
		    {
				counter = Integer.parseInt(saboteurNumber.getText());
				counter --;
				if(counter<0)
			    saboteurNumber.setText("0");
				else
				saboteurNumber.setText(Integer.toString(counter));
			}
		});
		bountyUp.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent arg0) 
		    {
				counter = Integer.parseInt(bountyNumber.getText());
				counter ++;
				bountyNumber.setText(Integer.toString(counter));
			}
		});
		soldierUp.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent arg0) 
		    {
				counter = Integer.parseInt(soldierNumber.getText());
				counter ++;
				soldierNumber.setText(Integer.toString(counter));
			}
		});
		
		saboteurUp.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent arg0) 
		    {
				counter = Integer.parseInt(saboteurNumber.getText());
				counter ++;
				saboteurNumber.setText(Integer.toString(counter));
			}
		});
	}
	
	public void createButton()
	{
		
	}
	
	public void createLabel()
	{
		try {currentPreview = ImageIO.read(new File("Resources/Robo/RoboBack2.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);}
		catch (IOException e) {System.out.println("Errore - Sprites Mancante nel Wave editor ");}
		bountyPreview = new JLabel(new ImageIcon(currentPreview));
		try {currentPreview = ImageIO.read(new File("Resources/Random/RandomBack3.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);}
		catch (IOException e) {System.out.println("Errore - Sprites Mancante nel Wave editor ");}
		soldierPreview = new JLabel(new ImageIcon(currentPreview));
		try {currentPreview = ImageIO.read(new File("Resources/Native/NativeBack2.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);}
		catch (IOException e) {System.out.println("Errore - Sprites Mancante nel Wave editor ");}
		saboteurPreview = new JLabel(new ImageIcon(currentPreview));	
		
		bountyDescription = new JLabel();
		bountyDescription.setOpaque(true);
		bountyDescription.setVisible(false);
		bountyDescription.setBackground(Color.cyan);
		
		soldierDescription = new JLabel();
		soldierDescription.setOpaque(true);
		soldierDescription.setVisible(false);
		soldierDescription.setBackground(Color.cyan);
		
		saboteurDescription = new JLabel();
		saboteurDescription.setOpaque(true);
		saboteurDescription.setVisible(false);
		saboteurDescription.setBackground(Color.cyan);
		
		bountyDescription.setText("Bounty Hunter - Cerca il Player");// - Unità avanzata: cerca il player");
		soldierDescription.setText("Soldato - Cerca la Room");// - Unità base: cerca la room");
		saboteurDescription.setText("Sabotatore - Disativa le trappole");// - Disattiva le trappole");
	}
	
	public void createGroup(GroupLayout layout)
	{
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

		layout.setHorizontalGroup(layout
			    .createParallelGroup(GroupLayout.Alignment.LEADING)
			    .addGroup(layout.createSequentialGroup()
			    	.addComponent(soldierPreview, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(soldierDown, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(soldierNumber, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(soldierUp, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			    .addComponent(soldierDescription)
			    .addGroup(layout.createSequentialGroup()
				    .addComponent(saboteurPreview, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(saboteurDown, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(saboteurNumber, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(saboteurUp, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			    .addComponent(saboteurDescription)
			    .addGroup(layout.createSequentialGroup()
				    .addComponent(bountyPreview, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)	
			        .addComponent(bountyDown, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(bountyNumber, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(bountyUp, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				 .addComponent(bountyDescription));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	 .addComponent(soldierPreview).addComponent(soldierDown).addComponent(soldierNumber).addComponent(soldierUp))
			    .addComponent(soldierDescription)
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	 .addComponent(saboteurPreview).addComponent(saboteurDown).addComponent(saboteurNumber).addComponent(saboteurUp))
			    .addComponent(saboteurDescription)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					 .addComponent(bountyPreview).addComponent(bountyDown).addComponent(bountyNumber).addComponent(bountyUp))
				.addComponent(bountyDescription));
	}
	
	public String getBountyNumber()
	{
		return bountyNumber.getText();
	}
	
	public String getSoldierNumber()
	{
		return soldierNumber.getText();
	}

	public String getSaboteruNumber()
	{
		return saboteurNumber.getText();
	}

}

