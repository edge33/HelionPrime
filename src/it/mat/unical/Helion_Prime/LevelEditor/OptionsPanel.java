package it.mat.unical.Helion_Prime.LevelEditor;

import it.mat.unical.Helion_Prime.GFX.MainMenuFrame;
import it.mat.unical.Helion_Prime.GFX.MainMenuPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class OptionsPanel extends JPanel {

	private EditorMainPanel editorMainPanel;
	private JButton okButton;
	private JButton backButton;
	private JComboBox<Integer> rowsComboBox;
	private JComboBox<Integer> colsComboBox;
	private MainMenuPanel mainMenuPanel;

	public OptionsPanel(EditorMainPanel editorMainPanel,MainMenuPanel mainMenuPanel)
	{
		this.editorMainPanel = editorMainPanel;
		this.mainMenuPanel = mainMenuPanel;
		this.setVisible(true);
		this.setBackground(Color.black);
		this.okButton = new JButton("Ok");
		this.backButton = new JButton("Back to main menu");
		this.rowsComboBox = new JComboBox<Integer>();
		this.colsComboBox = new JComboBox<Integer>();

		int k = 1;
		for (int i = 0; i < 50; i++) {
			rowsComboBox.addItem(k);
			colsComboBox.addItem(k);
			k++;
		}

		rowsComboBox.setSelectedIndex(19);
		colsComboBox.setSelectedIndex(19);
		createButton();
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int rows = (int) OptionsPanel.this.rowsComboBox
						.getItemAt(OptionsPanel.this.rowsComboBox
								.getSelectedIndex());

				int cols = (int) OptionsPanel.this.colsComboBox
						.getItemAt(OptionsPanel.this.colsComboBox
								.getSelectedIndex());

				OptionsPanel.this.editorMainPanel
						.switchToGrid(rows, (int) cols);

			}
		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuFrame.getInstance().switchTo(
						OptionsPanel.this.mainMenuPanel);
			}
		});
		this.add(rowsComboBox);
		this.add(colsComboBox);
		this.add(okButton);
		this.add(backButton);

	}
	
	public void createButton()
	{
		rowsComboBox.setBackground(Color.black);
		rowsComboBox.setForeground(Color.green);
		rowsComboBox.setFont(mainMenuPanel.getFont());
		rowsComboBox.setFont(rowsComboBox.getFont().deriveFont(15.0f));
		
		colsComboBox.setBackground(Color.black);
		colsComboBox.setForeground(Color.green);
		colsComboBox.setFont(mainMenuPanel.getFont());
		colsComboBox.setFont(rowsComboBox.getFont().deriveFont(15.0f));
		
		backButton.setBackground(Color.black);
		backButton.setForeground(Color.green);
		backButton.setFont(mainMenuPanel.getFont());
		backButton.setFont(rowsComboBox.getFont().deriveFont(25.0f));
		backButton.setBorderPainted(false);
		backButton.setFocusPainted(false);
		
		okButton.setBackground(Color.black);
		okButton.setForeground(Color.green);
		okButton.setFont(mainMenuPanel.getFont());
		okButton.setFont(rowsComboBox.getFont().deriveFont(25.0f));
		okButton.setBorderPainted(false);
		okButton.setFocusPainted(false);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		
	}

}
