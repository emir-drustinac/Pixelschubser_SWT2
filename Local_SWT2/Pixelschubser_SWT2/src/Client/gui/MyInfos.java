package Client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SharedData.PlayerData;

public class MyInfos extends JPanel {

	private static final int MAX_NUMBER_OF_MERCENARIES = 4;

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
	private JLabel proconsul;

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
		mercs.setLayout(new GridLayout(0, 1));
		mercs.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
		mercs.setBackground(bgColor);
		mercenaries = new JLabel[MAX_NUMBER_OF_MERCENARIES];
		for (int i = 0; i < MAX_NUMBER_OF_MERCENARIES; i++) {
			JLabel label;
			label = getIconLabel("/images/mercenary.png");
			label.setVerticalAlignment(JLabel.CENTER);
			mercenaries[i] = label;
			mercs.add(label);
		}
		add(mercs, BorderLayout.WEST);
		
		//mercenaries[0] = getIconLabel("/images/dice_side_1.png");
		mercenaries[0].addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int rnd = new Random().nextInt(6) + 1;
				ImageIcon diceIcon = new ImageIcon("/images/dice_side_" + rnd + ".png");
				mercenaries[0].setIcon(diceIcon);
				mercenaries[0].setVisible(true);
				
			}
		});		
				
		
		// center for buildings
		buildings = getIconLabel("/images/building.png");
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
//		for (int i = 0; i < 7; i++) {
//			JLabel card = getIconLabel("/images/card.png");
//			cards.add(card);
//		}
		add(cards, BorderLayout.SOUTH);
		
		//east for proconsul icon (valign top with 10 pixel padding)
		proconsul = getIconLabel("/images/proconsul.png");
		proconsul.setVerticalAlignment(JLabel.TOP);
		proconsul.setHorizontalAlignment(JLabel.CENTER);
		proconsul.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
		proconsul.setVisible(false);
		add(proconsul, BorderLayout.EAST);
	}

	/**
	 * @return
	 */
	private JLabel getIconLabel(String path) {
		java.net.URL imgUrl = getClass().getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl);
		return new JLabel(icon);
	}

	public void updateMyInfos(PlayerData p) {
		// show mercenaries
		for (int i = 0; i < mercenaries.length; i++) {
			mercenaries[i].setVisible(i < p.numberOfMercenaries);
		}
		
		// show number of buildings
		// TODO perhaps some visual buildings?
		buildings.setText(String.valueOf(p.numberOfBuildings));
		
		// proconsul icon
		proconsul.setVisible(p.isProconsul);
		
		// show cards
		cards.removeAll();
		for (int i = 0; i < p.getNumberOfCards(); i++) {
			JLabel card = getIconLabel("/images/card.png");
			cards.add(card);
		}
		// update visualization
		cards.invalidate();
		invalidate();
	}

	public String getPlayerID() {
		return playerID;
	}
}
