package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import player.Audio;
import player.Player;

public class ToolBar extends JToolBar implements ActionListener {

	private JComboBox<String> stationCombo;
	private JButton play;
	private JButton next;
	private JSlider vol;
	private Frame parent;
	
	public ToolBar(Frame parent) {
		super(HORIZONTAL);
		this.parent = parent;
		play = new JButton("Play");
		play.setFocusable(false);
		play.addActionListener(this);
		next = new JButton("Next");
		next.setFocusable(false);
		next.addActionListener(this);
		stationCombo = new JComboBox<>();
		stationCombo.addActionListener(this);
		stationCombo.setFocusable(false);
		
		this.add(play);
		this.add(next);
//		this.add(volSlider());
		this.add(Box.createHorizontalGlue());
		this.add(stationCombo);
	}
	
	public void setSelectedStation(String stationName) {
		this.stationCombo.setSelectedItem(stationName);
	}
	
	public void setStations(String[] stations) {
		DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>(stations);
		this.stationCombo.setModel(m);
	}
	
	public JSlider volSlider() {
		vol = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		vol.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Audio.setMasterOutputVolume(vol.getValue() / 100f);				
			}
		});
		return vol;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == stationCombo) {
			String stationName = (String) stationCombo.getSelectedItem();
			if(stationName != null) {
				parent.chooseStation(stationName);
			}
		}
		if(e.getSource() == next) {
			parent.skipSong();
		}
		if(e.getSource() == play) {
			parent.playToggle();
		}
	}
}
