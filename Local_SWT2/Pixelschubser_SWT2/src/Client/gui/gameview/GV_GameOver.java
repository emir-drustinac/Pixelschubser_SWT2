package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;
import SharedData.GameData;
import SharedData.TopPlayer;

/**
 * @author chris
 *
 */
public class GV_GameOver extends GameView {
	
	private static final long serialVersionUID = 1829294331352754452L;
	private JPanel topList;
	
	public GV_GameOver() {
		setLayout(new BorderLayout());
		
		JLabel lbl = new JLabel("Top 10 Spieler");
		add(lbl, BorderLayout.NORTH);
		
		JPanel flow = new JPanel();
		topList = new JPanel(new GridLayout(0, 5));
		flow.add(topList);
		add(flow, BorderLayout.CENTER);
		
		// button to proceed in game
		JButton btn = new JButton("neues Spiel starten");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:game_over");
			}
		});
		this.add(btn, BorderLayout.SOUTH);
	}

	@Override
	public void updateGameData(GameData g) {
		topList.removeAll();
		//header
		topList.add(centeredLabel("Platz", true));
		topList.add(centeredLabel("Name", true));
		topList.add(centeredLabel("Punkte", true));
		topList.add(centeredLabel("Geld", true));
		topList.add(centeredLabel("Spiele", true));
		//players
		for (TopPlayer p : g.topPlayerList) {
			topList.add(centeredLabel(p.place, false));
			topList.add(centeredLabel(p.name, false));
			topList.add(centeredLabel(p.points, false));
			topList.add(centeredLabel(p.money, false));
			topList.add(centeredLabel(p.games, false));
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
