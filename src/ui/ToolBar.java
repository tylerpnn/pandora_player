package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import player.Player;
import player.Player.PlayerState;

public class ToolBar extends JToolBar implements ActionListener {

	private JComboBox<String> stationCombo;
	private JButton play;
	private JButton next;
	private Frame parent;
	private ImageIcon playIcon;
	private ImageIcon pauseIcon;
	
	public ToolBar(Frame parent) {
		super(HORIZONTAL);
		this.parent = parent;
		this.setFloatable(false);
		this.setPreferredSize(new Dimension(parent.getWidth(), 30));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		ClassLoader c = this.getClass().getClassLoader();
		playIcon = new ImageIcon(c.getResource("res/play.png"));
		pauseIcon = new ImageIcon(c.getResource("res/pause.png"));
		next = new JButton(new ImageIcon(c.getResource("res/next.png")));
		
		play = new JButton(playIcon);
		play.setFocusable(false);
		play.addActionListener(this);
		
		next.setFocusable(false);
		next.addActionListener(this);
		
		stationCombo = new JComboBox<>();
		stationCombo.addActionListener(this);
		stationCombo.setFocusable(false);
		
		this.add(play);
		this.add(next);
		this.add(volSlider());
//		this.add(Box.createHorizontalGlue());
		this.add(stationCombo);
	}
	
	private JSlider volSlider() {
		final JSlider vol = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		vol.setMinimumSize(new Dimension(75, 30));
		vol.setPreferredSize(vol.getMinimumSize());
		vol.setFocusable(false);

		vol.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				try {
					Player.setVolume(vol.getValue() / 100f);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		return vol;
	}
	
	public void setSelectedStation(String stationName) {
		this.stationCombo.setSelectedItem(stationName);
	}
	
	public void setStations(String[] stations) {
		DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>(stations);
		this.stationCombo.setModel(m);
	}
	
	public void buttonToggle(PlayerState state) {
		if(state == PlayerState.PLAYING || state == PlayerState.WAITING) {
			play.setIcon(pauseIcon);
		} else if(state == PlayerState.PAUSED) {
			play.setIcon(playIcon);
		}
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
			buttonToggle(Player.getStatus());
		}
	}
}
