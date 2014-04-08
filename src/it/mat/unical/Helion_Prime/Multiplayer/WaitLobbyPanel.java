package it.mat.unical.Helion_Prime.Multiplayer;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class WaitLobbyPanel extends JPanel
{
	private JButton connectButton;

	private JTextField f1,f2,f3,f4;
	private JTextField port;
	private LimitedTextField t1,t2,t3,t4;
	private LimitedTextField tPort;
	
	private JLabel separator;
	private JLabel separator1;
	private JLabel separator2;
	private JLabel separator3;
	
	private JPanel southPanel;
	private JPanel centerPanel;
	private JPanel northPanel;
	private Cursor cursor;
	private Font font;

	public WaitLobbyPanel()
	{
		this.northPanel = new JPanel();
		this.southPanel = new JPanel();
		this.centerPanel = new JPanel();
		this.northPanel.setOpaque(false);
		this.southPanel.setOpaque(false);
		this.centerPanel.setOpaque(false);
		
		this.setLayout(new BorderLayout());
		
		this.separator1 = new JLabel(" - ");
		this.separator2 = new JLabel(" - ");
		this.separator3 = new JLabel(" - ");
		this.separator = new JLabel(" / ");
		
		this.separator1.setHorizontalAlignment(JTextField.CENTER);
		this.separator2.setHorizontalAlignment(JTextField.CENTER);
		this.separator3.setHorizontalAlignment(JTextField.CENTER);
		this.separator.setHorizontalAlignment(JTextField.CENTER);
		
		
		this.t1 = new LimitedTextField(4);
		this.t2 = new LimitedTextField(4);
		this.t3 = new LimitedTextField(4);
		this.t4 = new LimitedTextField(4);
		this.tPort = new LimitedTextField(6);
		
		this.f1 = new JTextField(3);
		this.f1.setHorizontalAlignment(JTextField.CENTER);
		this.f1.setDocument(t1);
		
		this.f2 = new JTextField(3);
		this.f2.setHorizontalAlignment(JTextField.CENTER);
		this.f2.setDocument(t2);
		
		this.f3 = new JTextField(3);
		this.f3.setHorizontalAlignment(JTextField.CENTER);
		this.f3.setDocument(t3);
		
		this.f4 = new JTextField(3);
		this.f4.setHorizontalAlignment(JTextField.CENTER);
		this.f4.setDocument(t4);
		
		this.port = new JTextField(5);
		this.port.setHorizontalAlignment(JTextField.CENTER);
		this.port.setDocument(tPort);

		this.connectButton = new JButton("Connect");

		this.cursor = MainMenuFrame.getInstance().getMainMenuPanel().getCursor();
		this.setCursor(cursor);

		this.createButton();
		this.fillPanel();
		this.addListener();

		this.setBackground(Color.BLACK);

	}

	public void createButton()
	{
		separator.setBackground(Color.black);
		separator.setForeground(Color.green);
		separator.setOpaque(false);
		separator.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		separator.setFont(separator.getFont().deriveFont(25.0f));
		
		separator1.setBackground(Color.black);
		separator1.setForeground(Color.green);
		separator1.setOpaque(false);
		separator1.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		separator1.setFont(separator.getFont().deriveFont(25.0f));
		
		separator2.setBackground(Color.black);
		separator2.setForeground(Color.green);
		separator2.setOpaque(false);
		separator2.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		separator2.setFont(separator.getFont().deriveFont(25.0f));
		
		separator3.setBackground(Color.black);
		separator3.setForeground(Color.green);
		separator3.setOpaque(false);
		separator3.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		separator3.setFont(separator.getFont().deriveFont(25.0f));
		
		f1.setBackground(Color.gray);
		f1.setForeground(Color.green);
		f1.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		f1.setFont(f1.getFont().deriveFont(25.0f));
		f1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
		
		f2.setBackground(Color.gray);
		f2.setForeground(Color.green);
		f2.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		f2.setFont(f2.getFont().deriveFont(25.0f));
		f2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
		
		f3.setBackground(Color.gray);
		f3.setForeground(Color.green);
		f3.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		f3.setFont(f3.getFont().deriveFont(25.0f));
		f3.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
		
		f4.setBackground(Color.gray);
		f4.setForeground(Color.green);
		f4.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		f4.setFont(f4.getFont().deriveFont(25.0f));
		f4.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
		
		port.setBackground(Color.gray);
		port.setForeground(Color.green);
		port.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		port.setFont(f1.getFont().deriveFont(25.0f));
		port.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
		
		connectButton.setBackground(Color.black);
		connectButton.setForeground(Color.green);
		connectButton.setOpaque(false);
		connectButton.setFont(MainMenuFrame.getInstance().getMainMenuPanel().getFont());
		connectButton.setFont(connectButton.getFont().deriveFont(25.0f));
		connectButton.setBorderPainted(false);
		connectButton.setFocusPainted(false);
		connectButton.setBorderPainted(false);
	}

	public void fillPanel()
	{
		this.add(northPanel,BorderLayout.NORTH);
		this.add(southPanel,BorderLayout.SOUTH);
		this.add(centerPanel,BorderLayout.CENTER);
		this.centerPanel.add(f1);
		this.centerPanel.add(separator1);
		this.centerPanel.add(f2);
		this.centerPanel.add(separator2);
		this.centerPanel.add(f3);
		this.centerPanel.add(separator3);
		this.centerPanel.add(f4);
		this.centerPanel.add(separator);
		this.centerPanel.add(port);
		this.southPanel.add(connectButton);
	}
	


	public void addListener()
	{
		connectButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			}
		});
		
		
		

	}
}