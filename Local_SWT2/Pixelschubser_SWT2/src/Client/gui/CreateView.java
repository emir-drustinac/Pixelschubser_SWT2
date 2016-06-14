package Client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateView extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1126851702412773874L;

	private MenuWindowLogic logic;
	
	private JButton btnCreate;
	private JButton btnBack;
	private JLabel gameNameLabel;
	private JLabel nameLabel;
	private JTextField gameName;
	private JTextField playerName;
	
	public CreateView(MenuWindowLogic logic) {

		this.logic = logic;
		
		setLayout(null);
		
		// init
		btnCreate = new JButton("Create");
		btnBack = new JButton("Back");
		gameNameLabel = new JLabel("GameName:");
		nameLabel = new JLabel("Player Name:");
		gameName = new JTextField();
		playerName = new JTextField();
		
		// set position
		gameNameLabel.setLocation(200, 100);
		gameName.setLocation(300, 100);
		
		nameLabel.setLocation(200, 150);
		playerName.setLocation(300, 150);
		
		btnCreate.setLocation(350, 200);
		btnBack.setLocation(350, 250);
		
		// size
		gameName.setSize(200, 25);
		playerName.setSize(200, 25);
		
		gameNameLabel.setSize(100, 25);
		nameLabel.setSize(100, 25);
		
		btnCreate.setSize(100, 25);
		btnBack.setSize(100, 25);
		
		// action command
		btnCreate.setActionCommand("Create");
		btnBack.setActionCommand("Back");
		
		// add action listener
		btnCreate.addActionListener(this);
		btnBack.addActionListener(this);
		
		
		this.add(btnCreate);
		this.add(btnBack);
		this.add(gameNameLabel);
		this.add(gameName);
		this.add(playerName);
		this.add(nameLabel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Create")){
			// TODO - input prüfen ODER erst in LOGIC prüfen???
			// TODO - fehlermeldung wenn falsch
			logic.createGame(gameName.getText(),playerName.getText());
		}else if(e.getActionCommand().equals("Back")){
			logic.back();
		}
	}

}
