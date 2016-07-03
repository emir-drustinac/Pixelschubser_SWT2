package Client.gui;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	private String playerID;
	private int augenZahl;
	private JLabel[] diceLabel;
	
	public MercenaryGUI(String playerID, int augenZahl) {
		
		this.playerID = playerID;
		this.augenZahl = augenZahl;
		
		setBackground(Color.RED);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		
		setLayout(new CardLayout(0, 0));
		
		diceLabel = new JLabel[6];
		
		for (int i = 0; i < 6; i++) {
			String path = "/images/dice" + Integer.toString(i+1) + ".png";
			diceLabel[i] = getIconLabel(path);
			add(diceLabel[i]);
			diceLabel[i].setVisible(false);
		}
		
		setDice(augenZahl);

	}
	
	public void setDice(int zahl){
		for (int i = 0; i < diceLabel.length; i++) {
			diceLabel[i].setVisible(false);			
		}
		
		diceLabel[zahl-1].setVisible(true);
		
	}
	
	public int getDice() {
		return augenZahl;
	}
		
	public JLabel getIconLabel(String path) {
		java.net.URL imgUrl = MercenaryGUI.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl); 
		return new JLabel(icon);
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setSelected(boolean b){
		if(b){
			setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
		}else{
			setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		}
		
	}
	
}

