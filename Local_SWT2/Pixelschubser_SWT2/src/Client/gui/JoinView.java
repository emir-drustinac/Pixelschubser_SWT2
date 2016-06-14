package Client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinView extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1857691018642354325L;

	private MenuWindowLogic logic;
	
	private JButton btnConnect;
	private JButton btnBack;
	private JLabel ipLabel;
	private JLabel nameLabel;
	private JTextField ip;
	private JTextField playerName;
	
	private JLabel status;
	
	public JoinView(MenuWindowLogic logic) {
		
		this.logic = logic;
		
		setLayout(null);
		
		// init
		btnConnect = new JButton("Connect");
		btnBack = new JButton("Back");
		ipLabel = new JLabel("IP:");
		nameLabel = new JLabel("Player Name:");
		ip = new JTextField();
		playerName = new JTextField();
		status =  new JLabel();
		
		// set position
		ipLabel.setLocation(200, 100);
		ip.setLocation(300, 100);
		
		nameLabel.setLocation(200, 150);
		playerName.setLocation(300, 150);
		
		btnConnect.setLocation(350, 200);
		btnBack.setLocation(350, 250);
		
		status.setLocation(325, 400);
		
		// size
		ip.setSize(200, 25);
		playerName.setSize(200, 25);
		
		ipLabel.setSize(100, 25);
		nameLabel.setSize(100, 25);
		
		btnConnect.setSize(100, 25);
		btnBack.setSize(100, 25);
		status.setSize(200, 25);
		
		status.setForeground(Color.RED);
		
		// action command
		btnConnect.setActionCommand("Connect");
		btnBack.setActionCommand("Back");
		
		// add action listener
		btnConnect.addActionListener(this);
		btnBack.addActionListener(this);
		
		
		this.add(btnConnect);
		this.add(btnBack);
		this.add(ipLabel);
		this.add(ip);
		this.add(playerName);
		this.add(nameLabel);
		this.add(status);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Connect")){
			// TODO - input prüfen ODER erst in LOGIC prüfen
			// TODO - fehlermeldung wenn falsch
			logic.connectToGame(ip.getText(),playerName.getText());
		}else if(e.getActionCommand().equals("Back")){
			logic.back();
		}
	}
	
	public void error(){
		status.setText("IPfalsch oder Name leer!!!");
	}
	
}
