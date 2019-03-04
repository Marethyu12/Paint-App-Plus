package com.paintappplus.util;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
	private int x1, y1, x2, y2;
	private int thickness;
	private Color shapeColour;
	private ShapeType shapeType;
	private boolean filled;
	
	public Shape() {
	    x1 = 0;
	    y1 = 0;
	    x2 = 0;
	    y2 = 0;
	    shapeColour = Color.BLACK;
	    shapeType = null;
		filled = false;
	}
	
	public Shape(int x1, int y1, int x2, int y2, int thickness, Color shapeColour, ShapeType shapeType, boolean filled) {
	    this.x1 = x1;
	    this.y1 = y1;
	    this.x2 = x2;
	    this.y2 = y2;
	    this.thickness = thickness;
	    this.shapeColour = shapeColour;
	    this.shapeType = shapeType;
	    this.filled = filled;
	}
	
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public void setY1(int y1) {
		this.y1 = y1;
	}
	
	public void setX2(int x2) {
		this.x2 = x2;
	}
	
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	
	public void setShapeColour(Color shapeColour) {
		this.shapeColour = shapeColour;
	}
	
	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public int getX1() {
		return x1;
	}
	
	public int getY1() {
		return y1;
	}
	
	public int getX2() {
		return x2;
	}
	
	public int getY2() {
		return y2;
	}
	
	public int getUpperLeftX() {
	    return Math.min(getX1(), getX2());
	}
	
	public int getUpperLeftY() {
	    return Math.min(getY1(), getY2());
	}
	
	public int getWidth() {
	    return Math.abs(getX1() - getX2());
	}
	
	public int getHeight() {
	    return Math.abs(getY1() - getY2());
	}
	
	public int getThickness() {
		return thickness;
	}
	
	public Color getShapeColour() {
		return shapeColour;
	}
	
	public ShapeType getShapeType() {
	    return shapeType;
	}
	
	public boolean getFilled() {
	    return filled;
	}
	
	public abstract void sketch(Graphics window);
}
