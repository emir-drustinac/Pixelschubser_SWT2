package Client.gui.gameview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;
import SharedData.GameData;
import SharedData.GameRules;
import SharedData.PlayerData;

/**
 * @author chris
 *
 */
public class GV_JoinGame extends GameView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6717144053155304020L;
	private static String lbl_ready = "Bereit";
	private static String lbl_wait = "Warte";
	private static Color col_ready = new Color(99, 255, 99);
	private static Color col_wait = new Color(255, 99, 99);
	private JButton btn_ready;
	private boolean isReady = false;
	private JLabel[] nameLabels = new JLabel[GameRules.MAX_NUMBER_OF_PLAYERS];
	private JLabel[] readyLabels = new JLabel[GameRules.MAX_NUMBER_OF_PLAYERS];
	
	public GV_JoinGame() {
		setLayout(new FlowLayout());
		
		// list of labels
		JPanel labels = new JPanel(new GridLayout(0, 2, 10, 5));
		for (int i = 0; i < GameRules.MAX_NUMBER_OF_PLAYERS; i++) {
			nameLabels[i] = new JLabel("Player " + i + ":", JLabel.RIGHT);
			readyLabels[i] = new JLabel(lbl_wait.toLowerCase());
			labels.add(nameLabels[i]);
			labels.add(readyLabels[i]);
		}
		labels.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 30));
		add(labels);
		
		// button ready / wait
		btn_ready = new JButton(isReady ? lbl_ready : lbl_wait);
		btn_ready.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				isReady = !isReady;
				btn_ready.setText(isReady ? lbl_ready : lbl_wait);
				btn_ready.setBackground(isReady ? col_ready : col_wait);
				Client.sendMessageToServer("client_ready:" + isReady);
			}
		});
		btn_ready.setPreferredSize(new Dimension(250, 50));
		btn_ready.setFont(btn_ready.getFont().deriveFont(30.0f));
		btn_ready.setBackground(isReady ? col_ready : col_wait);
		add(btn_ready);
	}

	@Override
	public void updateGameData(GameData g) {
		for (int i = 0; i < nameLabels.length; i++) {
			boolean visible = false;
			if (g.players.size() > i) {
				PlayerData p = g.players.get(i);
				nameLabels[i].setText(p.name + ":");
				readyLabels[i].setText(p.isReady ? lbl_ready.toLowerCase() : lbl_wait.toLowerCase());
				nameLabels[i].setForeground(p.isReady ? col_ready : col_wait);
				readyLabels[i].setForeground(p.isReady ? col_ready : col_wait);
				visible = true;
			}
			nameLabels[i].setVisible(visible);
			readyLabels[i].setVisible(visible);
		}
	}

	@Override
	public void activateView(GameData g) {
		updateGameData(g);
	}

	@Override
	public void deactivateView() {
		// nothing to clear up
	}

}
