package Client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SharedData.PlayerData;

public class MyInfos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590791567147096078L;
	
	private final String playerID;
	
	private static final int preferredWidth = 250;
	private static final int preferredHeight = 300;
	
	private JLabel[] mercenaries;
	private JLabel buildings;
	private JPanel cards;

	public MyInfos(String playerID) {
		this.playerID = playerID;
		// create all gui elements
		setLayout(new BorderLayout());
		Color bgColor = new Color(204,255,204);
		setBackground(bgColor);
		setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Color.white));
		setMinimumSize(new Dimension(preferredWidth, preferredHeight));
		setSize(preferredWidth, preferredHeight);
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
		// east for mercenaries
		JPanel mercs = new JPanel();
		mercs.setLayout(new GridLayout(4, 1));
		mercs.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
		mercs.setBackground(bgColor);
		mercenaries = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			java.net.URL imgUrl = getClass().getResource("/images/mercenary.png");
			ImageIcon icon = new ImageIcon(imgUrl);
			JLabel label = new JLabel(icon);
			label.setVerticalAlignment(JLabel.CENTER);
			mercenaries[i] = label;
			mercs.add(label);
		}
		add(mercs, BorderLayout.WEST);
		
		// center for buildings
		java.net.URL imgUrl = getClass().getResource("/images/building.png");
		ImageIcon icon = new ImageIcon(imgUrl);
		buildings = new JLabel(icon);
		// label is centered
		buildings.setVerticalAlignment(JLabel.CENTER);
		buildings.setHorizontalAlignment(JLabel.CENTER);
		// with text above icon
		buildings.setVerticalTextPosition(JLabel.TOP);
		buildings.setHorizontalTextPosition(JLabel.CENTER);
		buildings.setText("-");
		add(buildings, BorderLayout.CENTER);
		
		// south for visual card list
		cards = new JPanel();
		cards.setBackground(bgColor);
		for (int i = 0; i < 7; i++) {
			imgUrl = getClass().getResource("/images/card.png");
			icon = new ImageIcon(imgUrl);
			JLabel card = new JLabel(icon);
			cards.add(card);
		}
		add(cards, BorderLayout.SOUTH);
		
	}

	public void updateMyInfos(PlayerData p) {
		// show mercenaries
		for (int i = 0; i < mercenaries.length; i++) {
			mercenaries[i].setVisible(i < p.numberOfMercenaries);
		}
		
		// show number of buildings
		// TODO perhaps some visual buildings?
		buildings.setText(String.valueOf(p.numberOfBuildings));
		
		// show cards
		cards.removeAll();
		for (int i = 0; i < 9; i++) {
			URL imgUrl = getClass().getResource("/images/card.png");
			ImageIcon icon = new ImageIcon(imgUrl);
			JLabel card = new JLabel(icon);
			cards.add(card);
		}
		
		doLayout();
	}

	public String getPlayerID() {
		return playerID;
	}
}
