package it.mat.unical.Helion_Prime.LevelEditor;


import it.mat.unical.Helion_Prime.Logic.StaticObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GridPanel extends JPanel {

	private int TILE_SIZE=50;
	private EditorImageProvider editorImageProvider;				//Oggetto che si occupa di reperire le immagini necessarie 
	private int cornerType;
	private int wallType;
	private LevelStruct levelStruct;
	private int drawingPoint;
	private int rows, cols;


	public GridPanel(int rows,int cols)							//costruttore
	{		

		this.rows=rows;
		this.cols=cols;
		editorImageProvider = new EditorImageProvider();			//istanziazione degli oggetti dichiarati
		setLayout(null);
		levelStruct = new LevelStruct(rows,cols);

		setPreferredSize(new Dimension(800,600));	//settiamo la dimensione del pannello
		setMinimumSize(new Dimension(100, 50));		//settiamo la dimensione minima del pannello
		setBackground(Color.BLACK);					//settiamo il colore di sfondo

		//		this.setSize(MainMenuFrame.getInstance().getWidth(),MainMenuFrame.getInstance().getHeight());

		this.cornerType = 0;
		this.wallType = 0;

		this.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseReleased(final MouseEvent e)
			{
				int selectedItem;
				final int column = (int) (e.getX() / drawingPoint);
				final int row = (int) (e.getY() / drawingPoint);

				if(SwingUtilities.isLeftMouseButton(e))
				{
					if(row<=GridPanel.this.getLevelStruct().getRow() && column<=GridPanel.this.getLevelStruct().getColumn())
					{
						if(row<=GridPanel.this.getLevelStruct().getRow() && column<=GridPanel.this.getLevelStruct().getColumn())
						{
							selectedItem = GridPanel.this.getLevelStruct().getObjectID();
							if  ( !GridPanel.this.getLevelStruct().setElementAt(row, column,selectedItem) )
								JOptionPane.showMessageDialog(null, "Elemento gia presente!");
							repaint();
						}
					}
				}

				else if(SwingUtilities.isRightMouseButton(e))
				{
					if(row<=GridPanel.this.getLevelStruct().getRow() && column<=GridPanel.this.getLevelStruct().getColumn())
					{
						GridPanel.this.getLevelStruct().setElementAt(row, column, StaticObject.FLOOR);
						repaint();
					}
				}


			}
		});

		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int selectedItem;
				final int column = (int) (e.getX() / drawingPoint);
				final int row = (int) (e.getY() / drawingPoint);
				int notches = e.getWheelRotation();
				if (notches < 0 ) 
				{

					if(row<=GridPanel.this.getLevelStruct().getRow() && column<=GridPanel.this.getLevelStruct().getColumn())
					{
						if(GridPanel.this.getLevelStruct().getObjectID() == StaticObject.CORNER)
						{

							switch(cornerType)
							{
							case 0:
								GridPanel.this.getLevelStruct().setElementAt(row, column,  StaticObject.CORNER );
								repaint();
								cornerType++;
								break;
							case 1:
								GridPanel.this.getLevelStruct().setElementAt(row, column, StaticObject.CORNER_1);
								repaint();
								cornerType++;
								break;
							case 2:
								GridPanel.this.getLevelStruct().setElementAt(row, column,StaticObject.CORNER_2);
								repaint();
								cornerType++;
								break;
							case 3:
								GridPanel.this.getLevelStruct().setElementAt(row, column,StaticObject.CORNER_3);
								repaint();
								cornerType++;
								break;
							}
							if(cornerType==4)
							{
								cornerType=0;
							}
						}
						if(GridPanel.this.getLevelStruct().getObjectID()==1)
						{
							if(wallType == 0 )
							{ 
								GridPanel.this.getLevelStruct().setElementAt(row, column, StaticObject.WALL);
								repaint();
								wallType++;
							}
							else
							{ 
								GridPanel.this.getLevelStruct().setElementAt(row, column,StaticObject.WALL_2);
								repaint();
								wallType=0;
							}

						}
					}

				}
				
				

			}
		});


	}


	public void loadStructFromFile(File file) {

		try {
			this.levelStruct = new LevelStruct(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}


	@Override
	public void paintComponent(Graphics g) {	

		int row = GridPanel.this.getLevelStruct().getRow();						//attraverso il medoto Get instanziamo righe e colonne
		int colomn = GridPanel.this.getLevelStruct().getColumn();

		double scaleFactor;

		double hScale = this.getWidth()/ (double) (colomn*TILE_SIZE);
		double vScale = this.getHeight()/ (double) (row*TILE_SIZE);

		double min = Math.min(hScale, vScale);
		scaleFactor = min;


		drawingPoint = (int) (TILE_SIZE * scaleFactor);

		//il PaintComponent del pannello griglia
		g.setColor(Color.BLACK);					//sceglie il colore nero (sfondo) 
		g.fillRect(0, 0, getWidth(), getHeight());	//colora l'intero sfondo con il colore nero 
		g.setColor(Color.GRAY);					//sceglie il colore verde (contorni gliglia)
		for(int i=0;i<row;i++)						//scandaglia l'intera matrice 
		{											//e usa il valore della cella corrente come	
			for(int j=0;j<colomn;j++)				//chiave per uno swtich	
			{
				switch(GridPanel.this.getLevelStruct().getElementAt(i, j))	//lo switch chiamerà le funzioni che si occupano di
				{									//disegnare il componente corrispondente al valore
				case 0:
					g.drawRect(j*drawingPoint, i*drawingPoint,drawingPoint,drawingPoint); //cella vuota
					break;
				case 1:
					g.drawImage(editorImageProvider.getWall(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il muroOrizzontale
					break;
				case 2:
					g.drawImage(editorImageProvider.getSpawn(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il filippoSpawn
					break;
				case 3:
					g.drawImage(editorImageProvider.getEnemySpawn(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente lo enemySpawn
					break;
				case 4:
					g.drawImage(editorImageProvider.getFloor(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il pavimento
					break;
				case 6:
					g.drawImage(editorImageProvider.getRoom(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this);	//cella contenente la safeRoom
					break;
				case 8:
					g.drawImage(editorImageProvider.getUpDXcorner(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il CornerAD
					break;
				case 9:
					g.drawImage(editorImageProvider.getDownDXcorner(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il CornerBD
					break; 
				case 10:
					g.drawImage(editorImageProvider.getUpSXcorner(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il CornerAS
					break;
				case 11:
					g.drawImage(editorImageProvider.getDownSXcorner(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il CornerBS
					break;	
				case 12:
					g.drawImage(editorImageProvider.getFlippedWall(), j*drawingPoint , i*drawingPoint, drawingPoint, drawingPoint, this); //cella contenente il muroVerticale
					break;	
				}
			}
		}


	}

	public LevelStruct getLevelStruct() {
		return levelStruct;
	}

	public void setLevelStruct(LevelStruct struct) {
		levelStruct = struct;
	}


	public int getRows() {
		return rows;
	}


	public int getCols() {
		return cols;
	}
}
