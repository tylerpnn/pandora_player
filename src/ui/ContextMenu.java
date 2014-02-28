package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import pandora.Song;

public class ContextMenu extends JPopupMenu {

	private Song song;
	private SongDisplay sd;
	
	private JMenuItem like;
	private JMenuItem dislike;
	
	public ContextMenu(Song song, SongDisplay display) {
		this.sd = display;
		this.song = song;
		like = new JMenuItem("Like");
		like.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFeedback(1);
			}
		});
		
		dislike = new JMenuItem("Dislike");
		dislike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFeedback(-1);
			}
		});
		this.add(like);
		this.add(dislike);
		this.setPreferredSize(new Dimension(100, getComponentCount() * 20));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	private void setFeedback(int feedback) {
		sd.setFeedback(feedback);
	}
}