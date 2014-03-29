package it.mat.unical.Helion_Prime.GFX;

import it.mat.unical.Helion_Prime.Logic.FileNotCorrectlyFormattedException;
import it.mat.unical.Helion_Prime.Logic.GameManagerImpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;





import Online.Client;
import Online.Server;

import org.w3c.dom.ls.LSParser;

public class LevelSwitchPanel extends JPanel {


private BufferedImage levelSwitchWallpaper;
private BufferedImage levelPreview;
private JComboBox<String> comboBox;
private JButton menuButton;
private JButton startGameButton;
private Font font;

private Server server;

private GameOverPanel gameOverPanel;

public LevelSwitchPanel() {
    try { levelSwitchWallpaper = ImageIO.read(new File("Resources/optionPanelImage.jpg")); }
    catch (IOException e) {}
    this.font = MainMenuFrame.getInstance().getMainMenuPanel().getFont();
    setVisible(true);

    File[] levels;

    File path = new File("levels");
    levels = path.listFiles();
    String currentLevelName;
    String levelExtension;
    this.comboBox = new JComboBox();
    for (File level : levels) 
    {
    	currentLevelName = level.getName();
    	levelExtension = currentLevelName.substring(currentLevelName.lastIndexOf(".")+1,currentLevelName.length());
    	if(!(levelExtension.equals("jpg")))
    	{
        	currentLevelName = currentLevelName.substring(0,currentLevelName.indexOf('.'));
    		this.comboBox.addItem(currentLevelName);
    	}
    }
    this.add(comboBox);
    this.menuButton = new JButton("Back To Menu");
    this.startGameButton = new JButton("Start Game");
    createButton();

    this.comboBox.addActionListener(new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e) {
          String levelName = String.valueOf(comboBox.getSelectedItem());
          levelName = levelName + (".jpg");
          createPreview(levelName);
          repaint();
        }

    });

	this.startGameButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			String choosenLevel = (String) LevelSwitchPanel.this.comboBox.getSelectedItem();
			String name = "levels/"+choosenLevel+".txt";
			System.out
					.println("LevelSwitchPanel.LevelSwitchPanel    " + name);
			File level = new File(name);
			System.out.println("------------------------------------------------");
			MainGamePanel mainGamePanel = null;

			try {
				server = new Server(7777);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			server.start();
			GameManagerImpl.getInstance().setServer(server);
			Client client = new Client();
			client.sendMessage(choosenLevel+".txt");

			if (client.recieveMessage().equals("ready")) {

				System.out.println("SIAMO READY INIZIA IL GIOCO");
				try {
					mainGamePanel = new MainGamePanel(level, client);

				} catch (FileNotCorrectlyFormattedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
	
    this.menuButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            MainMenuFrame.getInstance().switchTo(
                    MainMenuFrame.getInstance().getMainMenuPanel());

        }
    });

    this.add(startGameButton);
    this.add(menuButton);

}

public void createButton()
{
    comboBox.setBackground(Color.black);
    comboBox.setForeground(Color.green);
    comboBox.setFont(font);
    comboBox.setFont(comboBox.getFont().deriveFont(20.0f));

    menuButton.setBackground(Color.black);
    menuButton.setForeground(Color.green);
    menuButton.setFont(font);
    menuButton.setFont(menuButton.getFont().deriveFont(25.0f));
    menuButton.setBorderPainted(false);
    menuButton.setFocusPainted(false);

    startGameButton.setBackground(Color.black);
    startGameButton.setForeground(Color.green);
    startGameButton.setFont(font);
    startGameButton.setFont(startGameButton.getFont().deriveFont(25.0f));
    startGameButton.setBorderPainted(false);
    startGameButton.setFocusPainted(false);
}

public void createPreview(String previewName)
{
    previewName = ("levels/" + previewName);
    BufferedImage IOlevelPreview = null;
    try {IOlevelPreview = ImageIO.read(new File(previewName));}
    catch (IOException e)
    {
        try {IOlevelPreview = ImageIO.read(new File("Resources/missing.jpg"));}
        catch (IOException e1) {System.out.println("manca l'universo; smetti di giocare");}
    }
    levelPreview = IOlevelPreview;
    Graphics2D g = levelPreview.createGraphics();
}

@Override
public void paintComponent(Graphics g)
{
    boolean prima=false;
    if(prima==false)
    {
     g.drawImage(levelSwitchWallpaper, 0, 0, this.getWidth(), this.getHeight(),this);
     prima=true;
    }
    g.drawImage(levelPreview, 30, 50, 500, 500,this);
}
}