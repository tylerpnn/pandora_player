package com.tylerpnn.ui.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ContextMenu extends JPopupMenu implements ActionListener {

	private SongDisplay sd;
	
	private JMenuItem like;
	private JMenuItem dislike;
	private JMenuItem explain;
	
	public ContextMenu(SongDisplay display) {
		this.sd = display;
		like = new JMenuItem("Like");
		like.addActionListener(this);
		
		dislike = new JMenuItem("Dislike");
		dislike.addActionListener(this);
		
		explain = new JMenuItem("Explain track");
		explain.addActionListener(this);
		this.add(like);
		this.add(dislike);
		this.add(explain);
		this.setPreferredSize(new Dimension(125, getComponentCount() * 25));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == like) {
			sd.setFeedback(1);
		}
		if(e.getSource() == dislike) {
			sd.setFeedback(-1);
		}
		if(e.getSource() == explain) {
			sd.explainTrack();
		}
	}
}