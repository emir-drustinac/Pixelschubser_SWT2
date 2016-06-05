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

	private Color bgColor;
	public Color getBgColor() {
		return bgColor;
	}

	private JLabel name;
	private JLabel proconsul;
	private JLabel mercenary;
	private JLabel building;
	private JLabel cards;

	public PlayerInfos(String playerID) {
		this.playerID = playerID;
		// create all gui elements
		setLayout(new BorderLayout());
		bgColor = getBackgroundColor();
		setBackground(bgColor);
		//setBorder(BorderFactory.createLineBorder(Color.white, 5));
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
		b2.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));
		//b2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));

		
		// first Icon
		mercenary = createImageLabel("/images/mercenary_small.png");
		b2.add(mercenary, BorderLayout.WEST);
		
		// second Icon
		building = createImageLabel("/images/building_small.png");
		b2.add(building, BorderLayout.CENTER);
		
		// third Icon
		cards = createImageLabel("/images/cards_small.png");
		b2.add(cards, BorderLayout.EAST);
		
		add(b2, BorderLayout.CENTER);
	}

	/**
	 * @param string
	 * @return 
	 */
	private JLabel createImageLabel(String string) {
		java.net.URL imgUrl = getClass().getResource(string);
		ImageIcon icon = new ImageIcon(imgUrl);
		JLabel label = new JLabel(icon);
		// label is bound to the bottom
		label.setVerticalAlignment(JLabel.BOTTOM);
		label.setHorizontalAlignment(JLabel.CENTER);
		// with text beneath icon
		label.setVerticalTextPosition(JLabel.BOTTOM);
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
			//new Color(  0,255,  0,50),
			new Color(  0,  0,255,50),
			new Color(255,255,  0,50),
			new Color(  0,255,255,50)
		};
	private static Color getBackgroundColor() {
		return backgroundColors[nextColor = (nextColor + 1) % backgroundColors.length];
	}
}
