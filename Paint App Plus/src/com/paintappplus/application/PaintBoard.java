package com.paintappplus.application;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.paintappplus.util.BrushType;
import com.paintappplus.util.GeometricShape;
import com.paintappplus.util.Shape;
import com.paintappplus.util.ShapeType;
import com.paintappplus.util.PNGFileFilter;

import static java.lang.Math.*;

public class PaintBoard extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int canvasWidth = 982;
	private static final int canvasHeight = 626;
	
	private BufferedImage canvas;
	private JLabel mouseCoordinates;
	private JFileChooser fileChooser;
	
	private volatile boolean painting;
	private int prevX, prevY, curX, curY;
	private Color canvasColour;
	private int brushSize;
	private BrushType brushType;
	private Color currentColour;
	
	private ShapeType shapeType;
	private Shape currentShape;
	private boolean filled;
	
	private boolean floodFill;
	private Color fillColour;
	
	public PaintBoard() {
		canvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
		mouseCoordinates = new JLabel("X: 0 pixels     Y: 0 pixels");
		fileChooser = null;
		canvasColour = Color.WHITE;
		brushSize = 6;
		brushType = BrushType.DEFAULT;
		currentColour = Color.BLACK;
		shapeType = null;
		currentShape = null;
		floodFill = false;
		fillColour = Color.BLACK;
		
		Graphics2D paintBrush = canvas.createGraphics();
		paintBrush.setColor(canvasColour);
		paintBrush.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		paintBrush.dispose();
		
		setLayout(new BorderLayout());
        add(mouseCoordinates, BorderLayout.SOUTH);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void setCanvasColour(Color canvasColour) {
		this.canvasColour = canvasColour;
	}
	
	public void setBrushColor(Color currentColour) {
		this.currentColour = currentColour;
	}
	
	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}
	
	public void setBrushType(BrushType brushType) {
		this.brushType = brushType;
	}
	
	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public Color getCanvasColour() {
		return canvasColour;
	}
	
	public Color getBrushColour() {
		return currentColour;
	}
	
	public int getBrushSize() {
		return brushSize;
	}
	
	public BrushType getBrushType() {
		return brushType;
	}
	
	@Override
	protected void paintComponent(Graphics board) {
		super.paintComponent(board);
		board.setColor(canvasColour);
		board.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		board.drawImage(canvas, getInsets().left, getInsets().top, canvasWidth, canvasHeight, this);
		if (currentShape != null) currentShape.sketch(board);
	}
	
	@Override
	public void updateUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		super.updateUI();
	}
	
	public void clearScreen() {
		Graphics2D paintBrush = canvas.createGraphics();
		paintBrush.setColor(canvasColour);
		paintBrush.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		paintBrush.dispose();
    }
    
    private JFileChooser getFileChooser() {
	    if (fileChooser == null) {
	        fileChooser = new JFileChooser();
	        fileChooser.setFileFilter(new PNGFileFilter());
	    }
	    
	    return fileChooser;
	}
	
	private BufferedImage getScreenShot(Component component) {
		BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
		component.paint(image.getGraphics());
		return image;
	}
	
	public void saveWork() {
		JFileChooser jFileChooser = getFileChooser();
        int result = jFileChooser.showSaveDialog(PaintAppPlus.paintboard);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = jFileChooser.getSelectedFile();
                selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
                BufferedImage img = getScreenShot(PaintAppPlus.paintboard);
                ImageIO.write(img, "png", selectedFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not save the file", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	
	public void openFile() {
	    JFileChooser ch = getFileChooser();
        int result = ch.showOpenDialog(PaintAppPlus.paintboard);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                canvas = ImageIO.read(ch.getSelectedFile());
                repaint();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not open file", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	
	public void setFloodFill(Color fillColour) {
		floodFill = true;
		this.fillColour = fillColour;
	}
	
    private void floodFill(final int initialX, final int initialY, final Color target, final Color previous) {
        Stack<StackItem> stack = new Stack<>();
        
        stack.push(new StackItem(initialX, initialY, previous));
        
        while (!stack.isEmpty()) {
            StackItem stackItem = stack.pop();
            
            if (stackItem.x >= canvas.getWidth() || stackItem.x < 1 || stackItem.y >= canvas.getHeight() || stackItem.y < 1)
                continue;
            
            if (canvas.getRGB(stackItem.x, stackItem.y) != stackItem.previous.getRGB())
                continue;
            
            Color previousColor = new Color(canvas.getRGB(stackItem.x, stackItem.y));
            
            canvas.setRGB(stackItem.x, stackItem.y, target.getRGB());
            
            stack.push(new StackItem(stackItem.x + 1, stackItem.y, previousColor));
            stack.push(new StackItem(stackItem.x, stackItem.y + 1, previousColor));
            stack.push(new StackItem(stackItem.x - 1, stackItem.y, previousColor));
            stack.push(new StackItem(stackItem.x, stackItem.y - 1, previousColor));
        }
    }
	
	private void updateBoard() {
	    Graphics2D paintBrush = canvas.createGraphics();
	    
	    paintBrush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    paintBrush.setPaint(currentColour);
	    
		switch (brushType) {
		
		case DEFAULT:
			paintBrush.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			paintBrush.drawLine(prevX, prevY, curX, curY);
			break;
		case DEFAULT_LIGHT_STROKE:
			int[] pixelPos = new int[2];
			
			for (int i = 0; i < ((brushSize*brushSize) / 10); i++) {
				pixelPos[0] = new Random().nextInt(brushSize);
				pixelPos[1] = new Random().nextInt(brushSize);
				
				paintBrush.drawRect(prevX + pixelPos[0], prevY + pixelPos[1], 1, 1);
			}
			
			break;
		case WATERCOLOUR:
			paintBrush.setPaint(new GradientPaint(0, 0, currentColour, 600, 0, Color.WHITE));
			paintBrush.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			paintBrush.drawLine(prevX, prevY, curX, curY);
			break;
		case TRIANGULAR:
			paintBrush.fillPolygon(new int[] {(int) round(prevX - brushSize * cos(toRadians(30))),
                                              prevX,
                                              (int) round(prevX + brushSize * cos(toRadians(30)))},
                                   new int[] {(int) round(prevY + brushSize * sin(toRadians(30))),
          		                              prevY - brushSize,
          		                              (int) round(prevY + brushSize * sin(toRadians(30)))},
                                   3);
            break;
		case SQUARE:
			paintBrush.fillRect(prevX - brushSize / 2, prevY - brushSize / 2, brushSize, brushSize);
			break;
		case HEXAGONAL:
		    paintBrush.fillPolygon(new int[] {prevX + brushSize / 2,
						                      prevX + brushSize,
						                      prevX + brushSize / 2,
						                      prevX - brushSize / 2,
						                      prevX - brushSize,
						                      prevX - brushSize / 2},
						           new int[] {(int) round(prevY - brushSize * cos(toRadians(30))),
						            		  prevY,
						            		  (int) round(prevY + brushSize * cos(toRadians(30))),
						            		  (int) round(prevY + brushSize * cos(toRadians(30))),
						            		  prevY,
						            		  (int) round(prevY - brushSize * cos(toRadians(30)))},
						           6);
			break;
		case STAR:
		    paintBrush.fillPolygon(new int[] {prevX,
						                      (int) round(prevX + (brushSize * 2 / 5) * sin(toRadians(36))),
						                      (int) round(prevX + brushSize * sin(toRadians(72))),
						                      (int) round(prevX + (brushSize * 2 / 5) * sin(toRadians(72))),
						                      (int) round(prevX + brushSize * sin(toRadians(36))),
						                      prevX,
						                      (int) round(prevX - brushSize * sin(toRadians(36))),
						                      (int) round(prevX - (brushSize * 2 / 5) * sin(toRadians(72))),
						                      (int) round(prevX - brushSize * sin(toRadians(72))),
						                      (int) round(prevX - (brushSize * 2 / 5) * sin(toRadians(36)))},
						           new int[] {prevY - brushSize,
						            		  (int) round(prevY - (brushSize * 2 / 5) * cos(toRadians(36))),
						            		  (int) round(prevY - (brushSize * 2 / 5) * cos(toRadians(36))),
						            	      (int) round(prevY + (brushSize * 2 / 5) * cos(toRadians(72))),
						            	      (int) round(prevY + brushSize * cos(toRadians(36))),
						            		  prevY + (brushSize / 2),
						            		  (int) round(prevY + brushSize * cos(toRadians(36))),
						            		  (int) round(prevY + (brushSize * 2 / 5) * cos(toRadians(72))),
						            		  (int) round(prevY - (brushSize * 2 / 5) * cos(toRadians(36))),
						            		  (int) round(prevY - (brushSize * 2 / 5) * cos(toRadians(36)))},
						           10);
			break;
		case HEART:
			paintBrush.fillArc(prevX - brushSize, prevY, brushSize, brushSize, 0, 180);
			paintBrush.fillArc(prevX, prevY, brushSize, brushSize, 0, 180);
			paintBrush.fillPolygon(new int[] {prevX - brushSize, prevX, prevX},
						           new int[] {prevY + brushSize / 2, prevY + brushSize / 2, prevY + brushSize*3 / 2},
						           3);
		    paintBrush.fillPolygon(new int[] {prevX + brushSize, prevX, prevX},
			                       new int[] {prevY + brushSize / 2, prevY + brushSize / 2, prevY + brushSize*3 / 2},
			                       3);
			break;
		}
		
		paintBrush.dispose();
	}
    
	public void mousePressed(MouseEvent e) {
		if (floodFill) {
			floodFill(e.getX(), e.getY(), fillColour, new Color(canvas.getRGB(e.getX(), e.getY())));
			repaint();
			
			floodFill = false;
			return;
		}
		
		if (shapeType == null) {
			if (painting) return;
			
			prevX = e.getX();
			prevY = e.getY();
			painting = true;
		} else {
			currentShape = new GeometricShape(e.getX(), e.getY(), e.getX(), e.getY(), brushSize, currentColour, shapeType, filled);
			painting = false;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (shapeType != null) {
			currentShape.setX2(e.getX());
			currentShape.setY2(e.getY());
			
			Graphics2D g2d = canvas.createGraphics();
			currentShape.sketch(g2d);
			g2d.dispose();
			
			currentShape = null;
			shapeType = null;
			repaint();
		} else {
			if (!painting) return;
			
			curX = e.getX();
			curY = e.getY();
			painting = false;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
	    curX = e.getX();
	    curY = e.getY();
	    
	    mouseCoordinates.setText(String.format("X: %d pixels     Y: %d pixels", curX, curY));
	    
	    if (shapeType != null) {
	    	currentShape.setX2(curX);
	    	currentShape.setY2(curY);
	    	repaint();
	    } else {
	    	if (!painting) return;
		    
		    updateBoard();
		    repaint();
			prevX = curX;
			prevY = curY;
	    }
	}
	
    public void mouseMoved(MouseEvent e) {
        mouseCoordinates.setText(String.format("X: %d pixels     Y: %d pixels", e.getX(), e.getY()));
    }
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	private class StackItem {
        private final int x;
        private final int y;
        private final Color previous;

        public StackItem(int x, int y, Color previous) {
            this.x = x;
            this.y = y;
            this.previous = previous;
        }
    }
}
