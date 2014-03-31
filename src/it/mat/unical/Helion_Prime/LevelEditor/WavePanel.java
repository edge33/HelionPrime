package it.mat.unical.Helion_Prime.LevelEditor;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.UIManager;

public class WavePanel extends JPanel{

	private JTextField bountyNumber;
	private JTextField soldierNumber;
	private JTextField saboteurNumber;
	
	private JLabel bountyPreview;
	private JLabel soldierPreview;
	private JLabel saboteurPreview;
	
	private JToolTip bountyDescription;
	private JToolTip  soldierDescription;
	private JToolTip  saboteurDescription;
	
	private JSlider bountySlider;
	private JSlider soldierSlider;
	private JSlider saboteurSlider;
	
	private WaveSliderListener bountySliderListener;
	private WaveSliderListener soldierSliderListener;
	private WaveSliderListener saboteurSliderListener;
	
	private Image currentPreview;
	private int counter;
	
	private Font font;

	public WavePanel()
	{
		bountyNumber = new JTextField();
		soldierNumber = new JTextField();
		saboteurNumber = new JTextField();

		bountyNumber.setText("0");
		soldierNumber.setText("0");
		saboteurNumber.setText("0");

		bountySliderListener = new WaveSliderListener(bountyNumber);
		soldierSliderListener = new WaveSliderListener(soldierNumber);
		saboteurSliderListener = new WaveSliderListener(saboteurNumber);
		
		bountySlider = new JSlider(JSlider.HORIZONTAL,  0, 10, 0);
		soldierSlider = new JSlider(JSlider.HORIZONTAL,  0, 10, 0);
		saboteurSlider = new JSlider(JSlider.HORIZONTAL,  0, 10, 0);
		
		font = MainMenuFrame.getInstance().getMainMenuPanel().getFont().deriveFont(13.0f);
		this.setCursor(MainMenuFrame.getInstance().getMainMenuPanel().getCursor());
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		createLabel();
		createSlider();
		createButton();
		createGroup(layout);
	}
	
	public void createSlider()
	{
		bountySlider.setMajorTickSpacing(10);
		bountySlider.setMinorTickSpacing(1);
		bountySlider.setPaintTicks(true);
		bountySlider.setPaintLabels(true);
		
		soldierSlider.setMajorTickSpacing(10);
		soldierSlider.setMinorTickSpacing(1);
		soldierSlider.setPaintTicks(true);
		soldierSlider.setPaintLabels(true);
		
		saboteurSlider.setMajorTickSpacing(10);
		saboteurSlider.setMinorTickSpacing(1);
		saboteurSlider.setPaintTicks(true);
		saboteurSlider.setPaintLabels(true);
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

		UIManager.put("ToolTip.font",font);
		UIManager.put("ToolTip.background", Color.BLACK);
		UIManager.put("ToolTip.foreground", Color.GREEN);
		
		bountyPreview.setToolTipText("Bounty Hunter - Cerca il Player");
		soldierPreview.setToolTipText("Soldato - Cerca la Room");
		saboteurPreview.setToolTipText("Sabotatore - Disativa le trappole");
		
	}
	
	public void createGroup(GroupLayout layout)
	{
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

		layout.setHorizontalGroup(layout
			    .createParallelGroup(GroupLayout.Alignment.LEADING)
			    .addGroup(layout.createSequentialGroup()
			    	.addComponent(soldierPreview, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			    	.addComponent(soldierSlider, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			    .addGroup(layout.createSequentialGroup()
				    .addComponent(saboteurPreview, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			        .addComponent(saboteurSlider, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			    .addGroup(layout.createSequentialGroup()
				    .addComponent(bountyPreview, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)	
			        .addComponent(bountySlider, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	 .addComponent(soldierPreview).addComponent(soldierSlider))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	 .addComponent(saboteurPreview).addComponent(saboteurSlider))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					 .addComponent(bountyPreview).addComponent(bountySlider)));
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

