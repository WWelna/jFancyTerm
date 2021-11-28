package com.occultusterra.jFancyTerm;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TerminalWindow extends JFrame {
	private static final long serialVersionUID = 2369539613762216135L;
	TerminalPanel t;
	
	public TerminalWindow(String title, int rows, int cols) {
		super(title);
		t = new TerminalPanel(rows, cols);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(t, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	public void append(String s) {
		t.append(s);
	}

}
