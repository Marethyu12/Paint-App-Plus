package com.paintappplus.application;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private ImageIcon icon = PaintAppPlus.icon;
	private JButton ok = new JButton("Ok");
	
	public AboutDialog(Frame owner) {
		super(owner, "About Paint App Plus", null);
		setSize(300, 240);
		setLocationRelativeTo(null);
		setIconImage(icon.getImage());
		setLayout(new BorderLayout());
		add(new GraphicsContext(), BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		ok.addActionListener(e -> dispose());
		buttonPanel.add(ok);
		add(buttonPanel, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setResizable(false);
		setVisible(true);
	}
	
	private class GraphicsContext extends Canvas {
		private static final long serialVersionUID = 1L;
		
		public void paint(Graphics g) {
			g.drawImage(icon.getImage(), getInsets().left, getInsets().top, 100, 100, this);
			g.setFont(new Font("consolas", Font.BOLD, 20));
			g.drawString("Paint App Plus", 110, 80);
			g.setFont(new Font("arial", Font.PLAIN, 10));
			g.drawString("Is a program created by Jimmy Y.", 110, 90);
			g.drawString("for Coding 11/12 final project.", 110, 100);
		}
	}
}
