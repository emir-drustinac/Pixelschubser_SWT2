package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;
import SharedData.GameData;
import SharedData.PlayerData;
import SharedData.PlayerList;

/**
 * @author chris
 *
 */
public class GV_DeclareWinner extends GameView {
	
	private static final long serialVersionUID = 1829294331352754452L;

	private JPanel playerTable;
	
	public GV_DeclareWinner() {
		// Table of player infos: name - points - money - place
		setLayout(new BorderLayout());
		JPanel pnl = new JPanel(new FlowLayout());
		playerTable = new JPanel(new GridLayout(0, 5, 0, 10));
		pnl.add(playerTable);
		this.add(pnl, BorderLayout.CENTER);
		
		// button to proceed in game
		JButton btn = new JButton("weiter");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:declare_winners");
			}
		});
		this.add(btn, BorderLayout.SOUTH);
	}

	@Override
	public void updateGameData(GameData g) {
		// Table of player infos: name - points - money - place
		// sort players by points and money
		//Vector<PlayerData> players = g.players.toArray();//new Vector<>();
		g.players.sort(new Comparator<PlayerData>() {

			@Override
			public int compare(PlayerData o1, PlayerData o2) {
				// sort descending by points and money
				int p1 = o1.getNumberOfVictoryPoints();
				int p2 = o2.getNumberOfVictoryPoints();
				if (p1 > p2) return -1;
				if (p1 < p2) return 1;
				int m1 = o1.getAmountOfMoney();
				int m2 = o2.getAmountOfMoney();
				if (m1 > m2) return -1;
				if (m1 < m2) return 1;
				return 0;
			}
		});
		int place = 0;
		int prevPoints = 0, prevMoney = 0;
		// playerTable headers
		playerTable.removeAll();
		playerTable.add(centeredLabel("Name", true));
		playerTable.add(centeredLabel("Punkte", true));
		playerTable.add(centeredLabel("Geld", true));
		playerTable.add(centeredLabel("Platz", true));
		playerTable.add(centeredLabel("", true));
		// player rows
		PlayerList winners = g.getWinnerList();
		for (PlayerData p : g.players) {
			int points = p.getNumberOfVictoryPoints();
			int money = p.getAmountOfMoney();
			if (prevPoints != points || prevMoney != money) {
				prevPoints = points;
				prevMoney = money;
				place++;
			}
			playerTable.add(centeredLabel(p.name, false));
			playerTable.add(centeredLabel(String.valueOf(p.getNumberOfVictoryPoints()), false));
			playerTable.add(centeredLabel(String.valueOf(p.getAmountOfMoney()), false));
			playerTable.add(centeredLabel(String.valueOf(place), false));
			playerTable.add(centeredLabel(winners.contains(p) ? "* GEWINNER *" : "", true));
		}
	}
	
	private JLabel centeredLabel(String txt, boolean bold) {
		JLabel lbl = new JLabel(txt);
		lbl.setHorizontalAlignment(JLabel.CENTER);
		if (!bold) lbl.setFont(lbl.getFont().deriveFont(Font.PLAIN));
		return lbl;
	}
	
	@Override
	public void activateView(GameData g) {
		updateGameData(g);
	}

	@Override
	public void deactivateView() {
		// TODO clear things up
	}

}
