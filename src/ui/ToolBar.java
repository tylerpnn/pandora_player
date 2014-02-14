package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import player.Audio;

public class ToolBar extends JToolBar implements ActionListener {

	private JComboBox<String> stationCombo;
	private JButton play;
	private JButton next;
	private JSlider vol;
	private Frame parent;
	
	public ToolBar(Frame parent) {
		super(HORIZONTAL);
		this.setPreferredSize(new Dimension(this.getWidth(), 30));
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
	
//	public JSlider volSlider() {
//		Class<?> sliderUIClass;
//		Field paintValue = null;
//		try {
//			sliderUIClass = Class.forName("javax.swing.plaf.synth.SynthSliderUI");
//	        paintValue = sliderUIClass.getDeclaredField("paintValue");
//	        paintValue.setAccessible(true);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		vol = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
//		Audio.setMasterOutputVolume(1f);
//		vol.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent e) {
//				Audio.setMasterOutputVolume(vol.getValue() / 100f);
//			}
//		});
//		vol.setPreferredSize(new Dimension(100, play.getPreferredSize().height));
//		try {
//            paintValue.set(vol.getUI(), false);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//		vol.setFocusable(false);
//		JPanel panel = new JPanel();
//		panel.setBackground(this.getBackground());
//		panel.add(new JLabel("Vol: "));
//		panel.add(vol);
//		return vol;
//	}

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
