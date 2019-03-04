package com.paintappplus.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GeometricShape extends Shape {
	public GeometricShape() {
		super();
	}
	
	public GeometricShape(int x1, int y1, int x2, int y2, int thickness, Color shapeColour, ShapeType shapeType, boolean filled) {
		super(x1, y1, x2, y2, thickness, shapeColour, shapeType, filled);
	}
	
	public void sketch(Graphics window) {
		window.setColor(getShapeColour());
		
		switch(getShapeType()) {
		
		case STRAIGHT_LINE:
			((Graphics2D) window).setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			window.drawLine(getX1(), getY1(), getX2(), getY2());
			break;
		case RECTANGLE:
			if (getFilled())
				window.fillRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
			else {
				((Graphics2D) window).setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				window.drawRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
			}
			
			break;
		case OVAL:
			if (getFilled())
				window.fillOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
			else {
				((Graphics2D) window).setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				window.drawOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
			}
			
			break;
		case TRIANGLE:
			if (getFilled())
				window.fillPolygon(new int[] {(getX1() + getX2()) / 2, getX1(), getX2()},
							       new int[] {getY1(), getY2(), getY2()},
							       3);
			else {
				((Graphics2D) window).setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				window.drawPolygon(new int[] {(getX1() + getX2()) / 2, getX1(), getX2()},
					               new int[] {getY1(), getY2(), getY2()},
					               3);
			}
			
			break;
		case RIGHT_ANGLED_TRIANGLE:
			if (getFilled())
			    window.fillPolygon(new int[] {getX1(), getX1(), getX2()},
							       new int[] {getY1(), getY2(), getY2()},
							       3);
			else {
				((Graphics2D) window).setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				window.drawPolygon(new int[] {getX1(), getX1(), getX2()},
					               new int[] {getY1(), getY2(), getY2()},
					               3);
			}
			
			break;
		case PARALLELOGRAM:
			if (getFilled())
				window.fillPolygon(new int[] {getX1() + 30, getX2(), getX2() - 30, getX1()},
							       new int[] {getY1(), getY1(), getY2(), getY2()},
							       4);
			else {
				((Graphics2D) window).setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				window.drawPolygon(new int[] {getX1() + 30, getX2(), getX2() - 30, getX1()},
					               new int[] {getY1(), getY1(), getY2(), getY2()},
					               4);
			}
			
			break;
		}
	}
}
