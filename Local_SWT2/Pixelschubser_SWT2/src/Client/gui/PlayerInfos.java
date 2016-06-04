package Client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SharedData.PlayerData;

public class PlayerInfos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8348601529424747225L;
	
	private final String playerID;
	
	private static final int preferredWidth = 150;
	private static final int preferredHeight = 100;

	private JLabel name;
	private JLabel proconsul;
	private JLabel mercenary;
	private JLabel building;
	private JLabel cards;

	public PlayerInfos(String playerID) {
		this.playerID = playerID;
		// create all gui elements
		setLayout(new BorderLayout());
		Color bgColor = getBackgroundColor();
		setBackground(bgColor);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setMinimumSize(new Dimension(preferredWidth, preferredHeight));
		setSize(preferredWidth, preferredHeight);
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
		// b1 = upper half
		JPanel b1 = new JPanel();
		b1.setBackground(bgColor);
		//b1.setLayout(new BorderLayout());
		
		// playername and proconsul icon
		name = new JLabel(playerID, JLabel.CENTER);
		java.net.URL imgUrl = getClass().getResource("/images/proconsul.png");
		ImageIcon icon = new ImageIcon(imgUrl);
		proconsul = new JLabel(icon);
		proconsul.setVerticalAlignment(JLabel.CENTER);
		proconsul.setHorizontalAlignment(JLabel.CENTER);
		proconsul.setVisible(false);
		//proconsul.setText("-");
		//b1.add();
		b1.add(name, BorderLayout.CENTER);
		b1.add(proconsul, BorderLayout.CENTER);
		
		add(b1, BorderLayout.NORTH);
		
		
		// b2 = lower half
		JPanel b2 = new JPanel();
		b2.setLayout(new GridLayout(1, 3));
		b2.setBackground(bgColor);
		
		// first Icon
		imgUrl = getClass().getResource("/images/mercenary_small.png");
		icon = new ImageIcon(imgUrl);
		mercenary = createImageLabel(icon);
		b2.add(mercenary, BorderLayout.WEST);
		
		// second Icon
		imgUrl = getClass().getResource("/images/building_small.png");
		icon = new ImageIcon(imgUrl);
		building = createImageLabel(icon);
		b2.add(building, BorderLayout.CENTER);
		
		// third Icon
		imgUrl = getClass().getResource("/images/cards_small.png");
		icon = new ImageIcon(imgUrl);
		cards = createImageLabel(icon);
		b2.add(cards, BorderLayout.EAST);
		
		add(b2, BorderLayout.CENTER);
	}

	/**
	 * @param icon
	 * @return 
	 */
	private JLabel createImageLabel(ImageIcon icon) {
		JLabel label = new JLabel(icon);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.TOP);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setText("-");
		return label;
	}

	public void updatePlayerInfos(PlayerData p) {
		name.setText(p.name);
		mercenary.setText(String.valueOf(p.numberOfMercenaries));
		building.setText(String.valueOf(p.numberOfBuildings));
		cards.setText(String.valueOf(p.getNumberOfCards()));
		proconsul.setVisible(p.isProconsul);
	}

	public String getPlayerID() {
		return playerID;
	}
	
	private static int nextColor = 0;
	private static Color[] backgroundColors = new Color[]{
			new Color(255,  0,255,50),
			new Color(255,  0,  0,50),
			new Color(  0,255,  0,50),
			new Color(  0,  0,255,50),
			new Color(255,255,  0,50),
			new Color(  0,255,255,50)
		};
	private static Color getBackgroundColor() {
		return backgroundColors[nextColor = (nextColor + 1) % backgroundColors.length];
	}
}