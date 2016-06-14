package Client.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import SharedData.GameData;

public class GameInfos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6191361517280553418L;

	private static final int preferredWidth = 200;
	private static final int preferredHeight = 300;
	
	private JTextArea log;
	
	public GameInfos(GameData gameData) {
		setLayout(new GridLayout(1, 1));
		//setBorder(BorderFactory.createLineBorder(Color.black));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,10,10,Color.white), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		setMinimumSize(new Dimension(preferredWidth, preferredHeight));
		setSize(preferredWidth, preferredHeight);
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
		// add textarea
		log = new JTextArea("Game Log:");
		log.setEditable(false);
		log.setAutoscrolls(true);
		log.setBackground(this.getBackground());
		add(log);
	}

	public void updateGameInfos(GameData g) {
		// TODO Auto-generated method stub
		
	}
}
