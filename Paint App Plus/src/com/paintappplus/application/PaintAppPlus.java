/******************************************************************
 *
 *
 *                 Coding 11/12 Final Project:
 *                       Paint App Plus
 *                        By Jimmy Y.
 *                      On June. 9, 2018
 *
 *
 ******************************************************************/

/******************************************************************
 * This interactive Java application allows users to paint the screen with their mouse
 * 
 * The users can:
 * -Enjoy system look and feel
 * -Save and open PNG files
 * -Clear the paint board
 * -Change the background colour
 * -Erase the painted areas
 * -Perform freehand drawings with mouse
 * -Set the paintbrush size
 * -Users have various brush types
 * -Set the paintbrush colour
 * -Reset the paint board and the paintbrush
 * -Draw common shapes
 * -Do flood fill
 * -More features might be added...
 ******************************************************************/

/******************************************************************
 * TODO: Problems:
 * -When you load a saved .png file, JLabel is visible at the bottom of saved file
 * -When save file dialog opened, it adds unnecessary .png to previously saved file's name
 * -negative pixels
 ******************************************************************/

/******************************************************************
 * TODO:
 * -Instead of menubar UI, implement JTabbedPane UI, to make app more genuine
 * -Textbox to the list of shapes
 * -Resizable canvas, use JInternalPane
 * -If there's unsaved work when user closes window, show save confirm dialog box
 * -Title change
 * -Image manipulation tools
 * -PaintBrush, Bitmap class?
 * -Watercolour
 ******************************************************************/

package com.paintappplus.application;

import java.awt.Color;
import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.paintappplus.util.BrushType;
import com.paintappplus.util.ShapeType;

public class PaintAppPlus extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	
	public static PaintBoard paintboard;
	public static ImageIcon icon;
	
	private JMenuBar menuBar;
	
	private JMenu mainMenu;
	private JMenuItem toDefaultMode;
	private JMenuItem saveFile;
	private JMenuItem openFile;
	private JMenuItem exit;
	
	private JMenu boardSettings;
	private JMenuItem clearBoard;
	private JMenuItem toDefaultBoard;
	private JMenuItem bCustom;
	private JMenuItem floodFill;
	
	private JMenu brushSettings;
	private JMenuItem eraser;
	private JMenuItem toDefaultBrush;
	private JMenu setBrushSize;
	private JMenuItem Two;
	private JMenuItem Four;
	private JMenuItem Six;
	private JMenuItem Eight;
	private JMenuItem Ten;
	private JMenuItem Twelve;
	private JMenuItem Fourteen;
	private JMenuItem Sixteen;
	private JMenuItem Eighteen;
	private JMenuItem Twenty;
	private JMenuItem TwentyTwo;
	private JMenuItem TwentyFour;
	private JMenuItem TwentySix;
	private JMenu setBrushType;
	private JMenuItem defaultType1;
	private JMenuItem defaultType2;
	private JMenuItem waterColourBrush;
	private JMenuItem triangleType;
	private JMenuItem squareType;
	private JMenuItem hexagonType;
	private JMenuItem starType;
	private JMenuItem heartType;
	private JMenuItem Custom;
	
	private JMenu drawShapes;
	private JMenuItem StraightLine;
	private JMenuItem Rectangle;
	private JMenuItem Oval;
	private JMenuItem Triangle;
	private JMenuItem rTriangle;
	private JMenuItem Parallelogram;
	private JMenuItem filledRectangle;
	private JMenuItem filledOval;
	private JMenuItem filledTriangle;
	private JMenuItem filledRTriangle;
	private JMenuItem filledParallelogram;
	
	private JMenu help;
	private JMenuItem howToUse;
	private JMenuItem about;
	
	private int prevBrushSize;
	private Color prevBrushColour;
	private BrushType prevBrushType; /* For better user experience */
	
	private PaintAppPlus() {
		super("Paint App Plus");
		
	    paintboard = new PaintBoard();
	    icon = new ImageIcon(PaintAppPlus.class.getResource("/Images/Icon.png"));
	    
	    menuBar = new JMenuBar();
	    
	    mainMenu = new JMenu("File");
	    toDefaultMode = new JMenuItem("Return to default mode");
	    saveFile = new JMenuItem("Save");
	    openFile = new JMenuItem("Open");
	    exit = new JMenuItem("Exit");
	    
	    boardSettings = new JMenu("Paintboard settings");
	    clearBoard = new JMenuItem("Clear screen");
	    toDefaultBoard = new JMenuItem("Set to default paint board");
	    bCustom = new JMenuItem("Set background colour");
	    floodFill = new JMenuItem("Fill");
	    
	    brushSettings = new JMenu("Paintbrush settings");
	    eraser = new JMenuItem("Eraser");
	    toDefaultBrush = new JMenuItem("Set to default paintbrush");
	    setBrushSize = new JMenu("Set paintbrush size");
	    Two = new JMenuItem("2 pixels");
	    Four = new JMenuItem("4 pixels");
	    Six = new JMenuItem("6 pixels");
	    Eight = new JMenuItem("8 pixels");
	    Ten = new JMenuItem("10 pixels");
	    Twelve = new JMenuItem("12 pixels");
	    Fourteen = new JMenuItem("14 pixels");
	    Sixteen = new JMenuItem("16 pixels");
	    Eighteen = new JMenuItem("18 pixels");
	    Twenty = new JMenuItem("20 pixels");
	    TwentyTwo = new JMenuItem("22 pixels");
	    TwentyFour = new JMenuItem("24 pixels");
	    TwentySix = new JMenuItem("26 pixels");
	    setBrushType = new JMenu("Set paintbrush type");
	    defaultType1 = new JMenuItem("Default");
	    defaultType2 = new JMenuItem("Default (light stroke)");
	    waterColourBrush = new JMenuItem("Watercolour");
	    triangleType = new JMenuItem("Triangle");
	    squareType = new JMenuItem("Square");
	    hexagonType = new JMenuItem("Hexagon");
	    starType = new JMenuItem("Star");
	    heartType = new JMenuItem("Heart");
	    Custom = new JMenuItem("Set paintbrush colour");
	    
	    drawShapes = new JMenu("Draw shapes");
	    StraightLine = new JMenuItem("Straight line");
	    Rectangle = new JMenuItem("Rectangle");
	    Oval = new JMenuItem("Oval");
	    Triangle = new JMenuItem("Triangle");
	    rTriangle = new JMenuItem("Right angled triangle");
	    Parallelogram = new JMenuItem("Parallelogram");
	    filledRectangle = new JMenuItem("Filled rectangle");
	    filledOval = new JMenuItem("Filled oval");
	    filledTriangle = new JMenuItem("Filled triangle");
	    filledRTriangle = new JMenuItem("Filled right angled triangle");
	    filledParallelogram = new JMenuItem("Filled parallelogram");
	    
	    help = new JMenu("Help");
	    howToUse = new JMenuItem("How to use?");
	    about = new JMenuItem("About Paint App Plus");
	    
	    prevBrushSize = paintboard.getBrushSize();
	    prevBrushColour = paintboard.getBrushColour();
	    prevBrushType = paintboard.getBrushType();
		
	    mainMenu.add(toDefaultMode);
	    mainMenu.add(saveFile);
	    mainMenu.add(openFile);
	    mainMenu.add(exit);
	    
	    boardSettings.add(clearBoard);
	    boardSettings.add(toDefaultBoard);
	    boardSettings.add(bCustom);
	    boardSettings.add(floodFill);
	    
	    brushSettings.add(eraser);
	    brushSettings.add(toDefaultBrush);
	    setBrushSize.add(Two);
	    setBrushSize.add(Four);
	    setBrushSize.add(Six);
	    setBrushSize.add(Eight);
	    setBrushSize.add(Ten);
	    setBrushSize.add(Twelve);
	    setBrushSize.add(Fourteen);
	    setBrushSize.add(Sixteen);
	    setBrushSize.add(Eighteen);
	    setBrushSize.add(Twenty);
	    setBrushSize.add(TwentyTwo);
	    setBrushSize.add(TwentyFour);
	    setBrushSize.add(TwentySix);
	    brushSettings.add(setBrushSize);
	    setBrushType.add(defaultType1);
	    setBrushType.add(defaultType2);
	    setBrushType.add(waterColourBrush);
	    setBrushType.add(triangleType);
	    setBrushType.add(squareType);
	    setBrushType.add(hexagonType);
	    setBrushType.add(starType);
	    setBrushType.add(heartType);
	    brushSettings.add(setBrushType);
	    brushSettings.add(Custom);
	    
	    drawShapes.add(StraightLine);
	    drawShapes.add(Rectangle);
	    drawShapes.add(Oval);
	    drawShapes.add(Triangle);
	    drawShapes.add(rTriangle);
	    drawShapes.add(Parallelogram);
	    drawShapes.add(filledRectangle);
	    drawShapes.add(filledOval);
	    drawShapes.add(filledTriangle);
	    drawShapes.add(filledRTriangle);
	    drawShapes.add(filledParallelogram);
	    
	    help.add(howToUse);
	    help.add(about);
	    
	    menuBar.add(mainMenu);
	    menuBar.add(boardSettings);
	    menuBar.add(brushSettings);
	    menuBar.add(drawShapes);
	    menuBar.add(help);
	    
	    toDefaultMode.addActionListener(this);
	    saveFile.addActionListener(this);
	    openFile.addActionListener(this);
	    exit.addActionListener(this);
	    
	    clearBoard.addActionListener(this);
	    toDefaultBoard.addActionListener(this);
	    bCustom.addActionListener(this);
	    floodFill.addActionListener(this);
	    
	    eraser.addActionListener(this);
	    toDefaultBrush.addActionListener(this);
	    Two.addActionListener(this);
	    Four.addActionListener(this);
	    Six.addActionListener(this);
	    Eight.addActionListener(this);
	    Ten.addActionListener(this);
	    Twelve.addActionListener(this);
	    Fourteen.addActionListener(this);
	    Sixteen.addActionListener(this);
	    Eighteen.addActionListener(this);
	    Twenty.addActionListener(this);
	    TwentyTwo.addActionListener(this);
	    TwentyFour.addActionListener(this);
	    TwentySix.addActionListener(this);
	    defaultType1.addActionListener(this);
	    defaultType2.addActionListener(this);
	    waterColourBrush.addActionListener(this);
	    squareType.addActionListener(this);
	    hexagonType.addActionListener(this);
	    triangleType.addActionListener(this);
	    starType.addActionListener(this);
	    heartType.addActionListener(this);
	    Custom.addActionListener(this);
	    
	    StraightLine.addActionListener(this);
	    Rectangle.addActionListener(this);
	    Oval.addActionListener(this);
	    Triangle.addActionListener(this);
	    rTriangle.addActionListener(this);
	    Parallelogram.addActionListener(this);
	    filledRectangle.addActionListener(this);
	    filledOval.addActionListener(this);
	    filledTriangle.addActionListener(this);
	    filledRTriangle.addActionListener(this);
	    filledParallelogram.addActionListener(this);
	    
	    howToUse.addActionListener(this);
	    about.addActionListener(this);
	    
	    setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
	    setContentPane(paintboard);
	    setIconImage(icon.getImage());
	    setJMenuBar(menuBar);
	    setResizable(false);
	    setVisible(true);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == toDefaultMode) {
    		paintboard.setCanvasColour(Color.WHITE);
    		paintboard.setBrushSize(6);
    		prevBrushSize = 6;
    		paintboard.setBrushColor(Color.BLACK);
    		prevBrushColour = Color.BLACK;
    		paintboard.setBrushType(BrushType.DEFAULT);
    		prevBrushType = BrushType.DEFAULT;
    		paintboard.clearScreen();
    		repaint();
    	}
    	
    	if (e.getSource() == saveFile) {
    		paintboard.saveWork();
    	}
    	
    	if (e.getSource() == openFile) {
    		paintboard.openFile();
    	}
    	
    	if (e.getSource() == exit) {
    		System.exit(0);
    	}
    	
		if (e.getSource() == clearBoard) {
			paintboard.clearScreen();
			repaint();
		}
		
		if (e.getSource() == toDefaultBoard) {
			paintboard.setCanvasColour(Color.WHITE);
			paintboard.clearScreen();
			repaint();
		}
		
		if (e.getSource() == bCustom) {
			try {
				Color customColour = JColorChooser.showDialog(null, "Select colour:", null);
				
				paintboard.setCanvasColour(customColour);
				paintboard.clearScreen();
				repaint();
			} catch (NullPointerException ex) {
				
			}
		}
		
		if (e.getSource() == floodFill) {
			try {
				Color fillColour = JColorChooser.showDialog(null, "Select colour:", null);
				
				paintboard.setFloodFill(fillColour);
			} catch (NullPointerException ex) {
				
			}
		}
		
		if (e.getSource() == eraser) {
			prevBrushSize = paintboard.getBrushSize();
			prevBrushColour = paintboard.getBrushColour();
			prevBrushType = paintboard.getBrushType();
			paintboard.setBrushSize(44);
			paintboard.setBrushColor(paintboard.getCanvasColour());
			paintboard.setBrushType(BrushType.DEFAULT);
		}
		
		if (e.getSource() == toDefaultBrush) {
			paintboard.setBrushSize(6);
    		prevBrushSize = 6;
    		paintboard.setBrushColor(Color.BLACK);
    		prevBrushColour = Color.BLACK;
    		paintboard.setBrushType(BrushType.DEFAULT);
    		prevBrushType = BrushType.DEFAULT;
		}
		
		if (e.getSource() == Two) {
			paintboard.setBrushSize(2);
    		prevBrushSize = 2;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Four) {
			paintboard.setBrushSize(4);
    		prevBrushSize = 4;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Six) {
			paintboard.setBrushSize(6);
    		prevBrushSize = 6;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Eight) {
			paintboard.setBrushSize(8);
    		prevBrushSize = 8;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Ten) {
			paintboard.setBrushSize(10);
    		prevBrushSize = 10;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Twelve) {
			paintboard.setBrushSize(12);
    		prevBrushSize = 12;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Fourteen) {
			paintboard.setBrushSize(14);
    		prevBrushSize = 14;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Sixteen) {
			paintboard.setBrushSize(16);
    		prevBrushSize = 16;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Eighteen) {
			paintboard.setBrushSize(18);
    		prevBrushSize = 18;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == Twenty) {
			paintboard.setBrushSize(20);
    		prevBrushSize = 20;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == TwentyTwo) {
			paintboard.setBrushSize(22);
    		prevBrushSize = 22;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == TwentyFour) {
			paintboard.setBrushSize(24);
    		prevBrushSize = 24;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == TwentySix) {
			paintboard.setBrushSize(26);
    		prevBrushSize = 26;
    		paintboard.setBrushColor(prevBrushColour);
    		paintboard.setBrushType(prevBrushType);
		}
		
		if (e.getSource() == defaultType1) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.DEFAULT);
			prevBrushType = BrushType.DEFAULT;
		}
		
		if (e.getSource() == defaultType2) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.DEFAULT_LIGHT_STROKE);
			prevBrushType = BrushType.DEFAULT_LIGHT_STROKE;
		}
		
		if (e.getSource() == waterColourBrush) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.WATERCOLOUR);
			prevBrushType = BrushType.WATERCOLOUR;
		}
		
		if (e.getSource() == triangleType) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.TRIANGULAR);
			prevBrushType = BrushType.TRIANGULAR;
		}
		
		if (e.getSource() == squareType) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.SQUARE);
			prevBrushType = BrushType.SQUARE;
		}
		
		if (e.getSource() == hexagonType) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.HEXAGONAL);
			prevBrushType = BrushType.HEXAGONAL;
		}
		
		if (e.getSource() == starType) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.STAR);
			prevBrushType = BrushType.STAR;
		}
		
		if (e.getSource() == heartType) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setBrushType(BrushType.HEART);
			prevBrushType = BrushType.HEART;
		}
		
		if (e.getSource() == Custom) {
			try {
				Color customColour = JColorChooser.showDialog(null, "Select colour:", null);
				
				paintboard.setBrushSize(prevBrushSize);
				paintboard.setBrushColor(customColour);
				prevBrushColour = customColour;
				paintboard.setBrushType(prevBrushType);
			} catch (NullPointerException ex) {
				
			}
		}
		
		if (e.getSource() == StraightLine) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.STRAIGHT_LINE);
		}
		
		if (e.getSource() == Rectangle) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.RECTANGLE);
			paintboard.setFilled(false);
		}
		
		if (e.getSource() == Oval) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.OVAL);
			paintboard.setFilled(false);
		}
		
		if (e.getSource() == Triangle) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.TRIANGLE);
			paintboard.setFilled(false);
		}
		
		if (e.getSource() == rTriangle) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.RIGHT_ANGLED_TRIANGLE);
			paintboard.setFilled(false);
		}
		
		if (e.getSource() == Parallelogram) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.PARALLELOGRAM);
			paintboard.setFilled(false);
		}
		
		if (e.getSource() == filledRectangle) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.RECTANGLE);
			paintboard.setFilled(true);
		}
		
		if (e.getSource() == filledOval) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.OVAL);
			paintboard.setFilled(true);
		}
		
		if (e.getSource() == filledTriangle) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.TRIANGLE);
			paintboard.setFilled(true);
		}
		
		if (e.getSource() == filledRTriangle) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.RIGHT_ANGLED_TRIANGLE);
			paintboard.setFilled(true);
		}
		
		if (e.getSource() == filledParallelogram) {
			paintboard.setBrushSize(prevBrushSize);
			paintboard.setBrushColor(prevBrushColour);
			paintboard.setShapeType(ShapeType.PARALLELOGRAM);
			paintboard.setFilled(true);
		}
		
		if (e.getSource() == howToUse) {
    		JOptionPane.showMessageDialog(null, "Ask Jimmy", "How to use?", JOptionPane.INFORMATION_MESSAGE);
    	}
		
		if (e.getSource() == about) {
    		new AboutDialog(this);
    	}
	}
	
	public static void main(String... args) throws Exception {
		EventQueue.invokeLater(() -> {new PaintAppPlus();});
	}
}
