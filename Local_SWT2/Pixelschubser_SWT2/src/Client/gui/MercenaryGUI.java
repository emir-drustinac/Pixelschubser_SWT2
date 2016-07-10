package Client.gui;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SharedData.Mercenary;

/**
 * 
 * @author Alex
 * Class MercenaryGUi aka Dice
 * use CardLayout to show dice side
 * use setDice 1-6 to show dice side
 *
 */

public class MercenaryGUI extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2924473578897929872L;
	
	private static final ImageIcon[] dieIcons = new ImageIcon[6];
	private static final Random rnd = new Random();
	static {
		for (int i = 1; i <= 6; i++) {
			dieIcons[i-1] = getImageIcon("/images/dice_side_" + i + ".png", 23);
		}
	}
	
	private final String playerID;
	private final String mercID;
	private final int augenZahl;
	private final JLabel lbl;
	private boolean small;
	private int borderWidth;
	//private JLabel[] diceLabel;
	
	public MercenaryGUI(String playerID, String mercID, int augenZahl) {
		this(playerID, mercID, augenZahl, Color.RED, false, false);
	}
	public MercenaryGUI(String playerID, String mercID, int augenZahl, Color bgColor, boolean defend, boolean small) {
		
		this.playerID = playerID;
		this.mercID = mercID;
		this.augenZahl = Math.max(Math.min(augenZahl, 6), 1);
		this.small = small;
		this.borderWidth = small ? 2 : 5;
		
		setBackground(bgColor);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, borderWidth));
		
		//setLayout(new CardLayout(0, 0));
		
		//diceLabel = new JLabel[6];
		
//		for (int i = 0; i < 6; i++) {
//			String path = "/images/dice" + Integer.toString(i+1) + ".png";
//			diceLabel[i] = small ? getIconLabel(path, 38) : getIconLabel(path);
//			add(diceLabel[i]);
//			diceLabel[i].setVisible(false);
//		}
		
		String path = getImagePath(defend);
		lbl = /*small ? getIconLabel(path, 38) :*/ getIconLabel(path);
		add(lbl);
		
//		setDice(augenZahl);

	}
	
//	public void setDice(int zahl){
//		for (int i = 0; i < diceLabel.length; i++) {
//			diceLabel[i].setVisible(false);			
//		}
//		if (zahl > 0 && zahl <= diceLabel.length)
//		diceLabel[zahl-1].setVisible(true);
//		
//	}
	
	private String getImagePath(boolean defend) {
		return "/images/" + (defend ? "shield" : "mercenary") + (small ? "_small" : "") + ".png";
	}
	public int getDice() {
		return augenZahl;
	}
		
//	public static JLabel getIconLabel(String path, int width) {
//		java.net.URL imgUrl = MercenaryGUI.class.getResource(path);
//		ImageIcon icon = new ImageIcon(imgUrl);
//		int height = (int) ((float)icon.getIconHeight() * (float)width / (float)icon.getIconWidth());
//		icon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
//		return new JLabel(icon);
//	}
	public static JLabel getIconLabel(String path) {
		ImageIcon icon = getImageIcon(path);
		return new JLabel(icon);
	}
	/**
	 * @param path
	 * @return
	 */
	private static ImageIcon getImageIcon(String path, int width) {
		ImageIcon icon = getImageIcon(path);
		int height = (int) ((float)icon.getIconHeight() * (float)width / (float)icon.getIconWidth());
		icon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		return icon;
	}
	private static ImageIcon getImageIcon(String path) {
		java.net.URL imgUrl = MercenaryGUI.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl);
		return icon;
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setSelected(boolean b){
		if(b){
			setBorder(BorderFactory.createLineBorder(Color.GREEN, borderWidth));
		}else{
			setBorder(BorderFactory.createLineBorder(Color.WHITE, borderWidth));
		}
		
	}

	public String getMercID() {
		return mercID;
	}
	public void updateVisuals(Mercenary m) {
		String targetID = m.getTarget();
		boolean deff = targetID.isEmpty() || targetID.equals(playerID) || m.isDefendingProconsul();
		// set background color
		Color c = Presentation.getColorOfPlayer(targetID.isEmpty() ? playerID : targetID);
		setBackground(c);
		// set icon
		lbl.setIcon(getImageIcon(getImagePath(deff)));
	}
	public void showDie() {
		lbl.setIcon(dieIcons[augenZahl-1]);
	}
	public void animateDie() {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			private int nums = 0;
			private int del = 0;
			@Override
			public void run() {
				if (nums > 5) {
					timer.cancel();
					lbl.setIcon(dieIcons[augenZahl-1]);
				} else {
					del--;
					if (del < 0) {
						nums++;
						del = nums*5;
						lbl.setIcon(dieIcons[ rnd.nextInt(6) ]);
					}
				}
			}
		}, 100 + rnd.nextInt(200), 20);
	}
	
}

