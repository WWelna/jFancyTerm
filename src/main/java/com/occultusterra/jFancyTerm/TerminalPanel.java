package com.occultusterra.jFancyTerm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class TerminalPanel extends JPanel {
	private static final long serialVersionUID = -3460981913227145355L;
	private int Cols, Rows, border=5;
	private char [][] screen;
	private int x=0, y=0;
	private Timer refresh;
	private int fontDoubler=2;
	
	public boolean echo = true;
	
	public TerminalPanel(int rows, int cols) {
		this.Cols = cols;
		this.Rows = rows;
		this.screen = new char[cols][rows];
		setBackground(Color.white);
		setPreferredSize(new Dimension((cols*8)*fontDoubler, (rows*8)*fontDoubler));
		setFocusable(true);
		
		this.refresh = new Timer(0, (e) -> {
			repaint();
		});
		this.refresh.setDelay(33);
		this.refresh.start();
	}
	
	public void clear() {
		for(int y=0; y < this.Rows; ++y)
			for(int x=0; x < this.Cols; ++x)
				screen[x][y] = 0;
	}
	
	public void move_cursor_offset(int x, int y) {
		if((this.x + x) < this.Cols && (this.y + y) < this.Rows) {
			this.x = x;
			this.y = y;
		}
	}
	
	public void newline() {
		if(this.y >= this.Rows-1) {
			for(int y=1; y < this.Rows; ++y)
				for(int x=0; x < this.Cols; ++x)
					this.screen[x][y-1] = this.screen[x][y];
			for(int x=0; x < this.Cols; ++x)
				this.screen[x][this.Rows-1] = 0;
		} else this.y += 1;
		this.x = 0;
	}
	
	public void append(String s) {
		for(char c: s.toCharArray()) {
			switch(c) {
				case '\n':
					this.newline();
					break;
				case '\t':
					this.move_cursor_offset(3, 0);
					break;
				default:
					if(this.x+1 > this.Cols)
						this.newline();
					screen[this.x][this.y] = c;
					this.x += 1;
			}
		}
	}
	
	public void write_window(Graphics2D g, int off_x, int off_y) {
		for(int y=0; y < this.Rows; ++y)
			for(int x=0; x < this.Cols; ++x)
				g.drawImage(Fonts.get_glyph(this.screen[x][y]), (x*8)*fontDoubler, (y*8)*fontDoubler, 8*fontDoubler, 8*fontDoubler, null);
	}
	
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        write_window(g, this.border, this.border);
    }
	
	
}
